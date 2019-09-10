/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * Symbolic Pathfinder (jpf-symbc) is licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */


package gov.nasa.jpf.symbc;


import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.PropertyListenerAdapter;
import gov.nasa.jpf.jvm.JVMDirectCallStackFrame;
import gov.nasa.jpf.jvm.bytecode.GOTO;
import gov.nasa.jpf.report.ConsolePublisher;
import gov.nasa.jpf.report.Publisher;
import gov.nasa.jpf.report.PublisherExtension;
import gov.nasa.jpf.symbc.numeric.*;
import gov.nasa.jpf.symbc.veritesting.*;
import gov.nasa.jpf.symbc.veritesting.ChoiceGenerator.StaticBranchChoiceGenerator;
import gov.nasa.jpf.symbc.veritesting.ChoiceGenerator.StaticPCChoiceGenerator;
import gov.nasa.jpf.symbc.veritesting.RangerDiscovery.DiscoverContract;
import gov.nasa.jpf.symbc.veritesting.VeritestingUtil.FailEntry;
import gov.nasa.jpf.symbc.veritesting.VeritestingUtil.SpfUtil;
import gov.nasa.jpf.symbc.veritesting.VeritestingUtil.StatisticManager;
import gov.nasa.jpf.symbc.veritesting.ast.def.*;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.AstToGreen.AstToGreenVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.Environment.DynamicOutputTable;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.Environment.DynamicTable;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.Environment.SlotParamTable;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.SPFCases.SPFCaseList;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.SPFCases.SpfCasesPass1Visitor;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.SPFCases.SpfCasesPass2Visitor;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.SPFCases.SpfToGreenVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.Uniquness.UniqueRegion;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.arrayaccess.ArraySSAVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.constprop.SimplifyStmtVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.fieldaccess.FieldSSAVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.fieldaccess.SubstituteGetOutput;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.linearization.LinearizationTransformation;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.removeEarlyReturns.RemoveEarlyReturns;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.ssaToAst.CreateStaticRegions;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.ssaToAst.StaticRegion;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.Environment.DynamicRegion;
import gov.nasa.jpf.symbc.veritesting.ast.transformations.substitution.SubstitutionVisitor;

import gov.nasa.jpf.symbc.veritesting.ast.transformations.typepropagation.TypePropagationVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.visitors.FixedPointAstMapVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.visitors.PrettyPrintVisitor;
import gov.nasa.jpf.symbc.veritesting.ast.visitors.StmtPrintVisitor;
import gov.nasa.jpf.vm.*;
import gov.nasa.jpf.vm.Instruction;
import za.ac.sun.cs.green.expr.*;
import za.ac.sun.cs.green.expr.Expression;

import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static gov.nasa.jpf.symbc.veritesting.AdapterSynth.SPFAdapterSynth.runAdapterSynth;
import static gov.nasa.jpf.symbc.veritesting.ChoiceGenerator.StaticPCChoiceGenerator.getKind;
import static gov.nasa.jpf.symbc.veritesting.StaticRegionException.ExceptionPhase.INSTANTIATION;
import static gov.nasa.jpf.symbc.veritesting.StaticRegionException.throwException;
import static gov.nasa.jpf.symbc.veritesting.VeritestingMain.skipRegionStrings;
import static gov.nasa.jpf.symbc.veritesting.VeritestingMain.skipVeriRegions;
import static gov.nasa.jpf.symbc.veritesting.VeritestingUtil.ExprUtil.*;
import static gov.nasa.jpf.symbc.veritesting.VeritestingUtil.SpfUtil.isUnsupportedRegionEnd;
import static gov.nasa.jpf.symbc.veritesting.VeritestingUtil.StatisticManager.*;
import static gov.nasa.jpf.symbc.veritesting.ast.transformations.arrayaccess.ArrayUtil.doArrayStore;

public class VeritestingListener extends PropertyListenerAdapter implements PublisherExtension {


    //TODO: make these into configuration options
    public static int veritestingMode = 0;

    public static long totalSolverTime = 0, z3Time = 0;
    public static long parseTime = 0;
    public static long solverAllocTime = 0;
    public static long cleanupTime = 0;
    public static int solverCount = 0;
    public static final int maxStaticExplorationDepth = 1;
    public static boolean initializeTime = true;
    public static int veritestRegionCount = 0;
    private static long staticAnalysisDur;
    private final long runStartTime = System.nanoTime();
    public static StatisticManager statisticManager = new StatisticManager();
    private static int veritestRegionExpectedCount = -1;
    private static int instantiationLimit = -1;
    public static boolean simplify = true;

    public enum VeritestingMode {VANILLASPF, VERITESTING, HIGHORDER, SPFCASES, EARLYRETURNS}

    private static VeritestingMode runMode;
    public static boolean performanceMode = false;

    // reads in a exclusionsFile configuration option, set to ${jpf-symbc}/MyJava60RegressionExclusions.txt by default
    public static String exclusionsFile;

    // reads in an array of Strings, each of which is the name of a method whose regions we wish to report metrics for
    public static String[] interestingClassNames;

    public String[] regionKeys = {"replace.amatch([C[CI)I#160",
            "replace.amatch([C[CI)I#77",
            "replace.dodash(C[C[C)V#112",
            "replace.dodash(C[C[C)V#8",
            "replace.esc([C)C#7",
            "replace.getccl([C[C)Z#15",
            "replace.getccl([C[C)Z#84",
            "replace.makepat([C[C)I#305",
            "replace.makepat([C[C)I#458",
            "replace.makepat([C[C)I#474",
            "replace.makepat([C[C)I#491",
            "replace.makepat([C[C)I#507",
            "replace.makepat([C[C)I#521",
            "replace.makesub([C[C)I#111",
            "replace.makesub([C[C)I#124",
            "replace.makesub([C[C)I#23",
            "replace.makesub([C[C)I#34",
            "replace.makesub([C[C)I#64",
            "replace.makesub([C[C)I#7",
            "replace.omatch([C[CI)Z#108",
            "replace.omatch([C[CI)Z#132",
            "replace.omatch([C[CI)Z#241",
            "replace.omatch([C[CI)Z#64",
            "replace.patsize([CI)I#32"};

    public VeritestingListener(Config conf, JPF jpf) {
        if (conf.hasValue("veritestingMode")) {
            veritestingMode = conf.getInt("veritestingMode");
            runMode = veritestingMode == 5 ? VeritestingMode.EARLYRETURNS :
                    (veritestingMode == 4 ? VeritestingMode.SPFCASES :
                            ((veritestingMode == 3 ? VeritestingMode.HIGHORDER :
                                    (veritestingMode == 2 ? VeritestingMode.VERITESTING : VeritestingMode.VANILLASPF))));

            switch (runMode) {
                case VANILLASPF:
                    System.out.println("* Warning: either invalid or no veritestingMode specified.");
                    System.out.println("* Warning: set veritestingMode to 1 to use vanilla SPF with VeritestingListener");
                    System.out.println("* Warning: set veritestingMode to 2 to use veritesting");
                    System.out.println("* Warning: set veritestingMode to 3 to use veritesting with higher order regions");
                    System.out.println("* Warning: set veritestingMode to 4 to use veritesting with higher order regions and SPFCases");
                    System.out.println("* Warning: resetting veritestingMode to 0 (aka use vanilla SPF)");
                    veritestingMode = 0;
                    break;
                case VERITESTING:
                    System.out.println("* running veritesting without SPFCases.");
                    break;
                case SPFCASES:
                    System.out.println("* running veritesting with SPFCases.");
                    break;
                case EARLYRETURNS:
                    System.out.println("* running veritesting with SPFCases and Early Returns.");
                    break;
            }

            if (conf.hasValue("performanceMode"))
                performanceMode = conf.getBoolean("performanceMode");
            if (conf.hasValue("jpf-symbc")) {
                exclusionsFile = conf.getString("jpf-symbc") + "/MyJava60RegressionExclusions.txt";
            }
            if (conf.hasValue("exclusionsFile")) {
                exclusionsFile = conf.getString("exclusionsFile");
            }
            if (conf.hasValue("interestingClassNames")) {
                interestingClassNames = conf.getStringArray("interestingClassNames", new char[]{','});
            }

            if (conf.hasValue("veritestRegionExpectedCount"))
                veritestRegionExpectedCount = conf.getInt("veritestRegionExpectedCount");

            if (conf.hasValue("instantiationLimit"))
                instantiationLimit = conf.getInt("instantiationLimit");

            if (conf.hasValue("simplify"))
                simplify = conf.getBoolean("simplify");

            StatisticManager.veritestingRunning = true;
            jpf.addPublisherExtension(ConsolePublisher.class, this);
        }

        if (conf.hasValue("contractMethodName"))
            DiscoverContract.contractMethodName = conf.getString("contractMethodName");
    }

    public SymbolicInteger makeSymbolicInteger(String name) {
        //return new SymbolicInteger(name, MinMax.getVarMinInt(name), MinMax.getVarMaxInt(name));
        return new SymbolicInteger(name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Listener's main method that checks every instruction for being a potential veritesting region.
     *
     * @param vm                   Virtual Machine.
     * @param ti                   Current running Thread.
     * @param instructionToExecute instruction to be executed.
     */
    public void executeInstruction(VM vm, ThreadInfo ti, Instruction instructionToExecute) {
        DiscoverContract.rangerMode = true;

        StackFrame curr = ti.getTopFrame();
//        runAdapterSynth(ti, curr);
        if (runMode == VeritestingMode.VANILLASPF) return;
        if (instantiationLimit > 0 && statisticManager.getSuccInstantiations() > instantiationLimit) return;
        boolean noVeritestingFlag = false;
        noVeritestingFlag = isNoVeritesting(curr, noVeritestingFlag);
        if (noVeritestingFlag)
            return;
        // End equivalence checking code

        MethodInfo methodInfo = instructionToExecute.getMethodInfo();
        String className = methodInfo.getClassName();
        String methodName = methodInfo.getName();
        String methodSignature = methodInfo.getSignature();
        int offset = instructionToExecute.getPosition();
        String key = CreateStaticRegions.constructRegionIdentifier(className + "." + methodName + methodSignature, offset);

        StatisticManager.instructionToExec = key;

        if (initializeTime) {
            discoverRegions(ti); // static analysis to discover regions
            initializeTime = false;
        } else {
            try {
                HashMap<String, StaticRegion> regionsMap = VeritestingMain.veriRegions;
                StaticRegion staticRegion = regionsMap.get(key);
                if ((staticRegion != null) && !(staticRegion.isMethodRegion) && !skipVeriRegions.contains(key) &&
                        isAllowedRegion(key)) {
                    thisHighOrdCount = 0;
                    //if (SpfUtil.isSymCond(staticRegion.staticStmt)) {
                    if (SpfUtil.isSymCond(ti, staticRegion.staticStmt, (SlotParamTable) staticRegion.slotParamTable, instructionToExecute)) {
                        if (runMode != VeritestingMode.SPFCASES) {
                            // If region ends on a stack operand consuming instruction that isn't a store, then abort the region
                            Instruction regionEndInsn = isUnsupportedRegionEnd(staticRegion, instructionToExecute);
                            if (regionEndInsn != null) {
                                throwException(new StaticRegionException("Unsupported region end instruction: " + regionEndInsn), INSTANTIATION);
                            }

                            DynamicRegion dynRegion = runVeritesting(ti, instructionToExecute, staticRegion, key);
                            Instruction nextInstruction = setupSPF(ti, instructionToExecute, dynRegion, false);
                            ++veritestRegionCount;
                            ti.setNextPC(nextInstruction);
                            statisticManager.updateVeriSuccForRegion(key);

                            System.out.println("------------- Region was successfully veritested --------------- ");
                        } else {
                            runVeritestingWithSPF(ti, vm, instructionToExecute, staticRegion, key);
                        }
                    } else
                        statisticManager.updateConcreteHitStatForRegion(key);
                }
            } catch (IllegalArgumentException e) {
                statisticManager.updateSPFHitForRegion(key, e.getMessage());
                System.out.println("!!!!!!!! Aborting Veritesting !!!!!!!!!!!! " + "\n" + e.getMessage() + "\n");
                updateSkipRegions(e.getMessage(), key);
                DiscoverContract.rangerMode = false;
                return;
            } catch (InvalidClassFileException e) {
                statisticManager.updateSPFHitForRegion(key, e.getMessage());
                System.out.println("!!!!!!!! Aborting Veritesting !!!!!!!!!!!! " + "\n" + e.getMessage() + "\n");
                DiscoverContract.rangerMode = false;
                return;
            } catch (StaticRegionException sre) {
                statisticManager.updateSPFHitForRegion(key, sre.getMessage());
                System.out.println("!!!!!!!! Aborting Veritesting !!!!!!!!!!!! " + "\n" + sre.getMessage() + "\n");
                updateSkipRegions(sre.getMessage(), key);
                DiscoverContract.rangerMode = false;
                return;
            } catch (VisitorException greenEx) {
                statisticManager.updateSPFHitForRegion(key, greenEx.getMessage());
                System.out.println("!!!!!!!! Aborting Veritesting !!!!!!!!!!!! " + "\n" + greenEx.getMessage() + "\n");
                updateSkipRegions(greenEx.getMessage(), key);
                DiscoverContract.rangerMode = false;
                return;
            } catch (CloneNotSupportedException e) {
                System.out.println("!!!!!!!! Aborting Veritesting !!!!!!!!!!!! " + "\n" + e.getMessage() + "\n");
                e.printStackTrace();
                updateSkipRegions(e.getMessage(), key);
                DiscoverContract.rangerMode = false;
                return;
            } catch (Exception e) {
                System.out.println("!!!!!!!! Aborting Veritesting !!!!!!!!!!!! " + "\n" + e.getMessage() + "\n");
                e.printStackTrace();
                if (e.getMessage() != null) updateSkipRegions(e.getMessage(), key);
                DiscoverContract.rangerMode = false;
            }
        }
        DiscoverContract.rangerMode = false;
    }

    private boolean isNoVeritesting(StackFrame curr, boolean noVeritestingFlag) {
        String[] allowedFunctions = new String[]{"adapt", "f1", "f2"};
        for (String s : allowedFunctions)
            if (curr.getMethodInfo().getName().equals(s)) return false;
        while (!JVMDirectCallStackFrame.class.isInstance(curr)) {
            if (curr.getMethodInfo().getName().contains("NoVeritest")) {
                noVeritestingFlag = true;
                break;
            } else curr = curr.getPrevious();
        }
        return noVeritestingFlag;
    }

    private boolean isAllowedRegion(String key) {
        return true;
        /*int allowed_regions_bv = Integer.parseInt(System.getenv("REGION_BV"));
        for (int i = 0; i < regionKeys.length; i++) {
            if ((allowed_regions_bv & (1 << i)) != 0) {
                if (key.equals(regionKeys[i]))
                    return true;
            }
        }
        return false;*/
    }

    private void updateSkipRegions(String message, String key) {
        for (String skipString : skipRegionStrings) {
            if (message.toLowerCase().contains(skipString.toLowerCase()))
                skipVeriRegions.add(key);
        }
    }

    private void runVeritestingWithSPF(ThreadInfo ti, VM vm, Instruction instructionToExecute, StaticRegion staticRegion, String key) throws Exception {
        if (!ti.isFirstStepInsn()) { // first time around
            StaticPCChoiceGenerator newCG;
            DynamicRegion dynRegion = runVeritesting(ti, instructionToExecute, staticRegion, key);

            newCG = new StaticBranchChoiceGenerator(dynRegion, instructionToExecute);
            newCG.makeVeritestingCG(ti);

            SystemState systemState = vm.getSystemState();
            systemState.setNextChoiceGenerator(newCG);
            ti.setNextPC(instructionToExecute);
            hgOrdRegionInstance += thisHighOrdCount;
        } else {
            ChoiceGenerator<?> cg = ti.getVM().getSystemState().getChoiceGenerator();
            if (cg instanceof StaticPCChoiceGenerator) {
                StaticPCChoiceGenerator vcg = (StaticPCChoiceGenerator) cg;
                int choice = (Integer) cg.getNextChoice();
                Instruction nextInstruction = null;
                try {
                    nextInstruction = vcg.execute(ti, instructionToExecute, choice);
                } catch (StaticRegionException sre) {
                    System.out.println(sre.toString());
                    return;
                }

                ti.setNextPC(nextInstruction);
            }
        }
    }


    private DynamicRegion runVeritesting(ThreadInfo ti, Instruction instructionToExecute, StaticRegion staticRegion,
                                         String key) throws Exception {
        Exception transformationException = null;
        System.out.println("\n---------- STARTING Transformations for conditional region: " + key +
                "\n" + PrettyPrintVisitor.print(staticRegion.staticStmt) + "\n");
        staticRegion.slotParamTable.print();
        staticRegion.inputTable.print();
        staticRegion.outputTable.print();
        staticRegion.varTypeTable.print();

        /*-------------- EARLY RETURN TRANSFORMATION ---------------*/
        if (runMode == VeritestingMode.EARLYRETURNS) {
            staticRegion = RemoveEarlyReturns.removeEarlyReturns(staticRegion);
        }

        /*-------------- UNIQUENESS TRANSFORMATION ---------------*/
        DynamicRegion dynRegion = UniqueRegion.execute(staticRegion);

        boolean somethingChanged = true;
        FixedPointWrapper.resetWrapper();
        do {
            while (somethingChanged) {

            /*-------------- SUBSTITUTION & HIGH ORDER TRANSFORMATION ---------------*/
            /*--------------  FIELD TRANSFORMATION ---------------*/
            /*-------------- ARRAY TRANSFORMATION TRANSFORMATION ---------------*/
                dynRegion = FixedPointWrapper.executeFixedPointTransformations(ti, dynRegion);
                somethingChanged = FixedPointWrapper.isChangedFlag();

                assert (FixedPointWrapper.isChangedFlag() == !FixedPointWrapper.isEqualRegion());
            }
            /*-------------- HIGH ORDER TRANSFORMATION ---------------*/
            dynRegion = FixedPointWrapper.executeFixedPointHighOrder(ti, dynRegion);
            somethingChanged = FixedPointWrapper.isChangedFlag();
            transformationException = FixedPointWrapper.getFirstException();
            assert (FixedPointWrapper.isChangedFlag() == !FixedPointWrapper.isEqualRegion());
        }
        while (somethingChanged);


        if (transformationException != null) throw transformationException;

        TypePropagationVisitor.propagateTypes(dynRegion);

        dynRegion = UniqueRegion.execute(dynRegion);

        if (simplify) {
            Iterator<Map.Entry<Variable, Expression>> itr = dynRegion.constantsTable.table.entrySet().iterator();
        /*
        ArrayRefVarExpr, FieldRefVarExpr, WalaVarExpr should be unique at this point because UniqueRegion should have
        handled it
         */
            while (itr.hasNext()) {
                Map.Entry<Variable, Expression> entry = itr.next();
                if (entry.getKey() instanceof FieldRefVarExpr)
                    assert ((FieldRefVarExpr) entry.getKey()).uniqueNum != -1;
                if (entry.getKey() instanceof WalaVarExpr) assert ((WalaVarExpr) entry.getKey()).getUniqueNum() != -1;
                if (entry.getKey() instanceof ArrayRefVarExpr)
                    assert ((ArrayRefVarExpr) entry.getKey()).uniqueNum != -1;
            }
        }

        if (runMode == VeritestingMode.SPFCASES) {

        /*-------------- SPFCases TRANSFORMATION 1ST PASS ---------------*/
            dynRegion = SpfCasesPass1Visitor.execute(ti, dynRegion, null);

        /*-------------- SPFCases TRANSFORMATION 1ST PASS ---------------*/
            dynRegion = SpfCasesPass2Visitor.execute(dynRegion);
        }
        /*--------------- LINEARIZATION TRANSFORMATION ---------------*/
        LinearizationTransformation linearTrans = new LinearizationTransformation();
        dynRegion = linearTrans.execute(dynRegion);

        /*--------------- TO GREEN TRANSFORMATION ---------------*/
        dynRegion = AstToGreenVisitor.execute(dynRegion);

        if (runMode == VeritestingMode.SPFCASES) {
            SpfToGreenVisitor visitor = new SpfToGreenVisitor();
            dynRegion = visitor.execute(dynRegion);
        }

        return dynRegion;
    }

    /**
     * This populates the Output of the summarized region to SPF.
     *
     * @param ti        Currently running thread.
     * @param ins       Branch instruction that indicates beginning of the region.
     * @param dynRegion Dynamic region that has been summarized.
     * @throws StaticRegionException Exception to indicate a problem while setting SPF.
     */

    public static Instruction setupSPF(ThreadInfo ti, Instruction ins, DynamicRegion dynRegion, boolean earlyReturnSetup) throws StaticRegionException {

        DiscoverContract.dynRegion = dynRegion;
        if (canSetPC(ti, dynRegion.regionSummary)) {
            populateFieldOutputs(ti, dynRegion);
            populateArrayOutputs(ti, dynRegion);
            populateSlots(ti, dynRegion);
            clearStack(ti.getTopFrame(), ins);

            if (earlyReturnSetup) {//we are setting up an early return choice.
                pushReturnOnStack(ti.getTopFrame(), dynRegion);
            }
            return advanceSpf(ins, dynRegion, earlyReturnSetup);

        }
        return null;
    }

    private static void pushReturnOnStack(StackFrame sf, DynamicRegion dynRegion) throws StaticRegionException {
        String returnType = dynRegion.earlyReturnResult.retPosAndType.getSecond();
        if (returnType != null) {
            switch (returnType) {
                case "double":
                    sf.pushDouble(0);
                    break;
                case "float":
                    sf.pushFloat(0);
                    break;
                case "long":
                    sf.pushLong(0);
                    break;
                case "int":
                case "short":
                case "boolean":
                    sf.push(0);
                    break;
                default: //assumen int
                    sf.push(0);
                    break;
            }
            sf.setOperandAttr(greenToSPFExpression(dynRegion.earlyReturnResult.retVar));
        } else {
            System.out.println("SPF does not know the type, type is assumed int.");
            throw new StaticRegionException("Cannot push operand on stack for early return, operand type is unknown.!");
        }
    }

    /**
     * This method checks that the current PathCondition and after appending the summarized region is satisfiable.
     *
     * @param ti            Currently running thread.
     * @param regionSummary Finaly summary of the region, after all transformations has been successfully completed.
     * @return PathCondition is still satisfiable or not.
     * @throws StaticRegionException Exception to indicate a problem while checking SAT of the updated PathCondition.
     */
    private static boolean canSetPC(ThreadInfo ti, Expression regionSummary) throws StaticRegionException {
        PathCondition pc;

        if (ti.getVM().getSystemState().getChoiceGenerator() instanceof PCChoiceGenerator) {
            pc = ((PCChoiceGenerator) (ti.getVM().getSystemState().getChoiceGenerator())).getCurrentPC();
        } else {
            if (runMode.ordinal() < VeritestingMode.SPFCASES.ordinal())
                throwException(new IllegalArgumentException("cannot create new PCChoiceGenerator in non-SPFCases mode"), INSTANTIATION);
            pc = new PathCondition();
            pc._addDet(new GreenConstraint(Operation.TRUE));
        }
        if (runMode.ordinal() < VeritestingMode.SPFCASES.ordinal()) //only add region summary in non spfcases mode.
            pc._addDet(new GreenConstraint(regionSummary));

        // if we're trying to run fast, then assume that the region summary is satisfiable in any non-SPFCASES mode
        if ((performanceMode && (runMode == VeritestingMode.VERITESTING || runMode == VeritestingMode.HIGHORDER)) ||
                isPCSat(pc)) {
            ((PCChoiceGenerator) ti.getVM().getSystemState().getChoiceGenerator()).setCurrentPC(pc);
            return true;
        } else {
            if (runMode == VeritestingMode.SPFCASES) // this is where we ignore populating the output of the static choice
                ti.getVM().getSystemState().setIgnored(true); //to ignore counting of the current choice generator.
            throwException(new StaticRegionException("Path condition is unsat, no region is created."), INSTANTIATION);
            return false;
        }
    }


    /**
     * This pop up operands of the if instruction that begins the region.
     *
     * @param sf  Current StackFrame.
     * @param ins Current executing instruction
     * @throws StaticRegionException Exception to indicate a problem while clearning the stack.
     */

    private static void clearStack(StackFrame sf, Instruction ins) throws StaticRegionException {
        int numOperands = SpfUtil.getOperandNumber(ins.getMnemonic());
        while (numOperands > 0) {
            sf.pop();
            numOperands--;
        }
    }


    //TODO: I need to use the wala name not number here.

    /**
     * Populates SPF stack slot with the output of the veritesting region.
     *
     * @param ti        Current executing thread.
     * @param dynRegion Dynamic region that has been successfully transformed and summarized.
     */
    private static void populateSlots(ThreadInfo ti, DynamicRegion dynRegion) {
        StackFrame sf = ti.getModifiableTopFrame();
        DynamicOutputTable dynOutputTable = dynRegion.outputTable;
        List<Integer> slots = dynOutputTable.getKeys();

        Iterator slotItr = slots.iterator();

        while (slotItr.hasNext()) {
            Integer slot = (Integer) slotItr.next();
            Variable var = dynOutputTable.lookup(slot);
            assert (var instanceof WalaVarExpr);
            Expression symVar;
            if (simplify && dynRegion.constantsTable.lookup(var) != null) {
                symVar = dynRegion.constantsTable.lookup(var);
                if (symVar instanceof CloneableVariable)
                    symVar = createGreenVar((String) dynRegion.varTypeTable.lookup(var), symVar.toString()); // assumes toString() would return the same string as getSymName()
            } else
                symVar = createGreenVar((String) dynRegion.varTypeTable.lookup(var), ((WalaVarExpr) var).getSymName());
            //TODO: Dont write a local output as a symbolic expression attribute if it is a constant
            sf.setSlotAttr(slot, greenToSPFExpression(symVar));
        }
    }

    private static void populateFieldOutputs(ThreadInfo ti, DynamicRegion dynRegion) throws StaticRegionException {
        Iterator itr = dynRegion.psm.getUniqueFieldAccess().iterator();
        while (itr.hasNext()) {
            FieldRefVarExpr expr = (FieldRefVarExpr) itr.next();
            String type = dynRegion.fieldRefTypeTable.lookup(expr);
            Expression symVar;
            if (dynRegion.constantsTable.lookup(expr) != null) {
                symVar = dynRegion.constantsTable.lookup(expr);
                if (symVar instanceof CloneableVariable)
                    symVar = createGreenVar(type, symVar.toString()); // assumes toString() would return the same string as getSymName()
            } else symVar = createGreenVar(type, expr.getSymName());
            new SubstituteGetOutput(ti, expr.fieldRef, false, greenToSPFExpression(symVar)).invoke();
        }
    }

    private static void populateArrayOutputs(ThreadInfo ti, DynamicRegion dynRegion) throws StaticRegionException {
//        Iterator itr = dynRegion.arrayPSM.getUniqueArrayAccess().iterator();
//        while (itr.hasNext()) {
//            ArrayRefVarExpr expr = (ArrayRefVarExpr) itr.next();
//            Expression symVar = createGreenVar(dynRegion.fieldRefTypeTable.lookup(expr), expr.getSymName());
//            doArrayStore(ti, expr, symVar, dynRegion.fieldRefTypeTable.lookup(expr));
//        }
        doArrayStore(ti, dynRegion.arrayOutputs, dynRegion.constantsTable);
    }

    /**
     * Steps SPF to the end of the region.
     *
     * @param ins       Insturction to be executed.
     * @param dynRegion Dynamic region that has been successfully transformed and summarized.
     */
    private static Instruction advanceSpf(Instruction ins, DynamicRegion dynRegion, boolean earlyReturnSetup) {
        int endIns;
        if (!earlyReturnSetup) // going to first instruction after the region
            endIns = dynRegion.endIns;
        else //going to a return instruction
            endIns = dynRegion.earlyReturnResult.retPosAndType.getFirst();
        while (ins.getPosition() != endIns) {
            if (ins instanceof GOTO && (((GOTO) ins).getTarget().getPosition() <= endIns))
                ins = ((GOTO) ins).getTarget();
            else ins = ins.getNext();
        }

        if (ins.getMnemonic().contains("store")) {
            ins = ins.getNext();
            System.out.println("advancing beyond a store at end of region");
        }
        //ti.setNextPC(ins);
        return ins;
    }

    /**
     * This is tries to discover statically all potential regions that could be used as veritesting regions.
     *
     * @param ti Current running thread.
     */
    private void discoverRegions(ThreadInfo ti) {
        Config conf = ti.getVM().getConfig();
        String[] allClassPaths = conf.getStringArray("classpath");
        ArrayList<String> classPath = new ArrayList<>();
        for (String s : allClassPaths) {
            classPath.add(s);
            // These classpaths are (1) classpath in .jpf file, (2) SPF class paths, (3) JPF-core class paths, so we
            // want to run static analysis only on class paths in the .jpf file
//            if (!s.contains("jpf-symbc")) classPath.add(s);
//            else break;
        }
        String className = conf.getString("target");
        VeritestingMain veritestingMain = new VeritestingMain(ti, className + ".class");
        long startTime = System.nanoTime();
        veritestingMain.analyzeForVeritesting(classPath, className);
        long endTime = System.nanoTime();
        staticAnalysisDur = endTime - startTime;
        statisticManager.collectStaticAnalysisMetrics(VeritestingMain.veriRegions);
        StaticRegionException.staticAnalysisComplete();
    }


    public void publishFinished(Publisher publisher) {
        long runEndTime = System.nanoTime();
        PrintWriter pw = publisher.getOut();
        publisher.publishTopicStart("VeritestingListener report:");
        long dynRunTime = (runEndTime - runStartTime) - staticAnalysisDur;

//        pw.println(statisticManager.printAllRegionStatistics());
//        pw.println(statisticManager.printStaticAnalysisStatistics());
//        pw.println(statisticManager.printAllExceptionStatistics());

        pw.println("\n/************************ Printing Time Decomposition Statistics *****************");
        pw.println("static analysis time = " + TimeUnit.NANOSECONDS.toMillis(staticAnalysisDur) + " msec");
        pw.println("Veritesting Dyn Time = " + TimeUnit.NANOSECONDS.toMillis(dynRunTime) + " msec");

        pw.println("\n/************************ Printing Solver Statistics *****************");
        pw.println("Total Solver Queries Count = " + solverCount);
        pw.println("Total Solver Time = " + TimeUnit.NANOSECONDS.toMillis(totalSolverTime) + " msec");
        pw.println("Total Solver Parse Time = " + TimeUnit.NANOSECONDS.toMillis(parseTime) + " msec");
        pw.println("Total Solver Clean up Time = " + TimeUnit.NANOSECONDS.toMillis(cleanupTime) + " msec");
        pw.println("PCSatSolverCount = " + StatisticManager.PCSatSolverCount + " (makes sense only in SPFCases mode)");
        pw.println("PCSatSolverTime = " + TimeUnit.NANOSECONDS.toMillis(StatisticManager.PCSatSolverTime) + " msec" + " (makes sense only in SPFCases mode)");
        pw.println("Constant Propagation Time for PC sat. checks = " + TimeUnit.NANOSECONDS.toMillis(StatisticManager.constPropTime));
        pw.println("Array SPF Case count = " + StatisticManager.ArraySPFCaseCount);
        pw.println("If-removed count = " + StatisticManager.ifRemovedCount);

        pw.println(statisticManager.printAccumulativeStatistics());
        pw.println(statisticManager.printInstantiationStatistics());

        pw.println("Total number of Distinct regions = " + statisticManager.regionCount());
        pw.println("Number of Veritested Regions Instances = " + veritestRegionCount);
        /* Begin added for equivalence checking */
        if (veritestRegionExpectedCount != -1) {
            pw.println("Expected Number of Veritested Regions Instances = " + veritestRegionExpectedCount);
            assert (veritestRegionCount >= veritestRegionExpectedCount);
        }
        pw.println(statisticManager.getDistinctVeriRegionKeys());
        /* End added for equivalence checking */


        assert veritestRegionCount == statisticManager.getSuccInstantiations();
        pw.println((TimeUnit.NANOSECONDS.toMillis(staticAnalysisDur) + TimeUnit.NANOSECONDS.toMillis(dynRunTime)) + "," +
                TimeUnit.NANOSECONDS.toMillis(staticAnalysisDur) + "," +
                TimeUnit.NANOSECONDS.toMillis(dynRunTime) + "," +
                solverCount + "," +
                TimeUnit.NANOSECONDS.toMillis(totalSolverTime) + "," +
                TimeUnit.NANOSECONDS.toMillis(parseTime) + "," +
                TimeUnit.NANOSECONDS.toMillis(cleanupTime) + "," +
                statisticManager.getDistinctVeriRegionNum() + "," +
                statisticManager.getDistinctSpfRegionNum() + "," +
                statisticManager.getConcreteRegionNum() + "," +
                statisticManager.getFailNum(FailEntry.FailReason.FIELDREFERNCEINSTRUCTION) + "," +
                statisticManager.getFailNum(FailEntry.FailReason.SPFCASEINSTRUCTION) + "," +
                statisticManager.getFailNum(FailEntry.FailReason.OTHER) + "," +
                hgOrdRegionInstance + "," +
                statisticManager.regionCount() + "," +
//                veritestRegionCount + "," + // this number also reports the total number of successful instantiations
                // instantiation metrics
                statisticManager.getSuccInstantiations() + "," + statisticManager.getFailedInstantiations() + "," +
                statisticManager.getConcreteInstNum() + "," +
                statisticManager.getInstFailNum(FailEntry.FailReason.FIELDREFERNCEINSTRUCTION) + "," +
                statisticManager.getInstFailNum(FailEntry.FailReason.SPFCASEINSTRUCTION) + "," +
                statisticManager.getInstFailNum(FailEntry.FailReason.OTHER) + "," +
                // static analysis metrics
                interestingRegionCount + "," + numMethodSummaries + "," + maxBranchDepth + "," + maxExecPathCount + "," + avgExecPathCount + "," +
                // exception metrics
                staticPhaseEx + "," + instPhaseEx + "," + unknownPhaseEx);

    }
}
