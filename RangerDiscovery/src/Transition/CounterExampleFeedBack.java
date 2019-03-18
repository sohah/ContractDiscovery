package Transition;

import ast.Passes.HoleGenerator;
import ast.Passes.RemoveHolesVisitor;
import ast.Passes.ToAstPass;
import ast.Passes.ToConstantHoleVisitor;
import ast.def.*;
import com.microsoft.z3.*;
import ref.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static Transition.TransitionT.holeTransitionT;
import static Transition.TransitionT.transitionT;
import static Transition.TransitionT.transitionTprime;
import static ast.def.BoolConst.*;


public class CounterExampleFeedBack {

    public static boolean printContracts;

    ContractInput contractInput;
    HashMap<String, String> cfg;

    Solver solver;
    public Context ctx;

    public String solverFile = "../RunPadModel/Contracts/matchingContracts/CEFLP/Pad/solverFile";
    public static int fileSequence = 1;

    public CounterExampleFeedBack() {
        clearSolverContext();
    }

    public static CounterExampleFeedBack counterExampleFeedBack = new CounterExampleFeedBack();

    public void executeConstantCEFL(ContractInput contractInput) throws DiscoveryException, IOException {
        this.contractInput = contractInput;

        Pair<LinkedHashMap<String, Var>, Ast> contextAndBody = ToAstPass.execute(transitionT.extractTransitionT(contractInput.mergedFileName));

        transitionT.tContext.putAll(contextAndBody.getFirst());
        transitionTprime.tContext.putAll(contextAndBody.getFirst()); //Tprime at least until now will have the same context

        transitionT.tBody = (Exp) contextAndBody.getSecond();

        HashMap<Hole, Ast> instantiatedHoles;

        boolean sat = checkSat(transitionT, false, printContracts, "firstTime");
        if (!sat) {
            System.out.println("Contract and Implementation already match, no repair is needed, aborting.");
            return;
        } else {// collect counter example
            System.out.println("Contract and Implementation not matching, collecting counter example and repairing");
            holeTransitionT.collectCounterExample(contractInput, solver.getModel());
        }
        boolean firstTime = true;

        while (sat) {
            /*********************** synthesis step ******************************/
            if (firstTime) { // i do not really need this, but it is easier to look at holes and their changes on the tprime rather than the t.
                contextAndBody = ToConstantHoleVisitor.execute(transitionT.tBody);
                firstTime = false;
            } else
                contextAndBody = ToConstantHoleVisitor.execute(transitionTprime.tBody);

            holeTransitionT.tContext.putAll(transitionT.tContext);
            holeTransitionT.tContext.putAll(contextAndBody.getFirst());
            holeTransitionT.tBody = (Exp) contextAndBody.getSecond();

            if (printContracts)
                System.out.println("**************** Checking SAT for holeContract:\n" + holeTransitionT.declare_Hole_Constants() + holeTransitionT.define_fun_T());
            else
                System.out.println("**************** Checking SAT for holeContract:\n");

            boolean synthesisSat = checkSat(holeTransitionT, true, true, ("hole_" + fileSequence));
            if (!synthesisSat) {
                System.out.println("Cannot find a repair!");
                return;
            } else
                System.out.println("SAT: synthesis for a possible repair is found");

            /********************* checking candidate repair step *********************************/
            instantiatedHoles = getModelForHoles();
            transitionTprime.tBody = (Exp) RemoveHolesVisitor.execute(instantiatedHoles, holeTransitionT.tBody);

            if (printContracts)
                System.out.println("*************** Checking SAT for the repaired Contract T': \n" + transitionTprime.define_fun_T());
            else
                System.out.println("*************** Checking SAT for the repaired Contract T': \n");

            sat = checkSat(transitionTprime, false, false, ("repair_" + fileSequence++));
            if (sat) {
                System.out.println("SAT: repair is no good, collecting counter example");
                holeTransitionT.collectCounterExample(contractInput, solver.getModel());
            } else {
                System.out.println("UNSAT ---- repair is found ^-^ , printing repaired tPrime");
                System.out.println(transitionTprime.define_fun_T());
            }
        }
    }


    private HashMap<Hole, Ast> getModelForHoles() throws DiscoveryException {

        HashMap<Hole, Ast> instantiatedHolesMap = new HashMap<>();

        Model model = solver.getModel();
        //System.out.println("printing models for holes for step number : " + fileSequence);
        //System.out.println(model.toString());


        FuncDecl[] constDecl = model.getConstDecls();

        for (FuncDecl decl : constDecl) {
            Var var = holeTransitionT.tContext.get(decl.getName().toString());
            if (var != null && var.toString().contains("Hole")) {
                Expr interpetation = model.getConstInterp(decl);
                if (interpetation.isInt())
                    instantiatedHolesMap.put(HoleGenerator.varHoleHashMap.get(var),
                            new IntConst(Integer.valueOf(interpetation.toString())));
                else if (interpetation.isTrue())
                    instantiatedHolesMap.put(HoleGenerator.varHoleHashMap.get(var),
                            TRUE);
                else if (interpetation.isFalse())
                    instantiatedHolesMap.put(HoleGenerator.varHoleHashMap.get(var),
                            FALSE);
                else throw new DiscoveryException("unexpected interpretation");
            }
        }
        return instantiatedHolesMap;
    }

    private boolean checkSat(TransitionT atransitionT, boolean isHoleT, boolean printContracts, String extension) throws IOException {
        StringBuilder newTransitionT;
        if (isHoleT) {
            newTransitionT = new StringBuilder(atransitionT.declare_Hole_Constants());
            newTransitionT.append(atransitionT.define_fun_T());
        } else
            newTransitionT = new StringBuilder(atransitionT.define_fun_T());

        String newMergedContract = transitionT.mergedContract.getFirst();
        StringBuilder stringBuilder = new StringBuilder(newMergedContract);
        int startT = stringBuilder.indexOf("(define-fun T");
        int endT = stringBuilder.indexOf("(declare-fun %init () Bool)");
        stringBuilder = stringBuilder.replace(startT, endT, newTransitionT.toString());
        int contactMatchStart = stringBuilder.indexOf("; ---------- joining contract begins here -------------");

        if (isHoleT) {
            StringBuilder assertionBuilder = new StringBuilder(atransitionT.counterExampleAssertionsToString());
            stringBuilder.insert(contactMatchStart, "(assert (not act1))\n(assert (not act2))\n");
            int newContactMatchStart = stringBuilder.indexOf("(assert (not act1))");
            stringBuilder.insert(newContactMatchStart, assertionBuilder);
            stringBuilder.append("\n(=> (and input_match~1 output_match~1 input_match$1) output_match$1)\n" +
                    ")))\n" +
                    "; ---------- joining contract ends here -------------\n");
        } else {
            //stringBuilder.insert(contactMatchStart,"(assert act1)\n(assert act2)\n");
            stringBuilder.append("\n( and input_match~1 output_match~1 input_match$1 (not output_match$1))\n" +
                    ")))\n" +
                    "; ---------- joining contract ends here -------------\n");
            System.out.println(stringBuilder.toString());
        }
        if (printContracts) {
            System.out.println("|-|-|-|-|-|-|-|- checking SAT and dumping file for |-|-|-|-|-|-|-|-\n" + stringBuilder.toString());
        } else
            System.out.println("|-|-|-|-|-|-|-|- checking SAT |-|-|-|-|-|-|-|-\n");


        clearSolverContext();
        solver = ctx.mkSolver();
        solver.fromString(stringBuilder.toString());
        saveToSolverFile(stringBuilder.toString(), extension);

        //solver.fromFile((this.solverFile+"firstTime.smt"));
        assert((solver.getAssertions()[solver.getNumAssertions() - 1]).getArgs()[0] instanceof BoolExpr);
        BoolExpr contract_match = (BoolExpr) (solver.getAssertions()[solver.getNumAssertions() - 1]).getArgs()[0];
        BoolExpr contract_match_assertion = ctx.mkBoolConst("contract_match_assertion");
        solver.assertAndTrack(contract_match_assertion, contract_match);

        Status status = solver.check(contract_match_assertion); //means the same thing as check, unless the assumptions are not added to the solver already
      //  Status status = solver.check();
        if (status == Status.SATISFIABLE)
            return true;
        else {
            ArrayList unsatCore = new ArrayList<BoolExpr>( Arrays.asList(solver.getUnsatCore()));
            assert (unsatCore.contains(contract_match));
            return false;
        }
    }

    private void clearSolverContext() {
        this.cfg = new HashMap<String, String>();
        cfg.put("model", "true");
        cfg.put("proof", "true");
        ctx = new Context(cfg);
    }

    public void saveToSolverFile(String string, String extension) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter((this.solverFile + extension + ".smt")))) {
            bufferedWriter.write(string);
            bufferedWriter.close();
        }
    }

}
