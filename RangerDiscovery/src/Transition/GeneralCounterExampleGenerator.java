package Transition;

import ast.def.*;
import com.microsoft.z3.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ast.def.BoolConst.FALSE;
import static ast.def.BoolConst.TRUE;
import static ast.def.Operator.OperatorKind.*;

public class GeneralCounterExampleGenerator extends CounterExampleGenerator{

    public static GeneralCounterExampleGenerator counterExampleGenerator = new GeneralCounterExampleGenerator();

    private HashMap<Var, Ast> inputMap = new HashMap<>();

    private HashMap<Var, Ast> outputMap = new HashMap<>();

    private ContractInput contractInput;


    /**
     * Attempts to create an assertion for the counter example for the whole variables in R, instead of constraining it to a set of input and output of R.
     * Initially it populates the outputMap with the expected output of R, then in later steps it will populate those.
     *
     * @param contractInput
     * @param model
     * @return
     * @throws IOException
     * @throws DiscoveryException
     */
    public Exp generateCounterExample(ContractInput contractInput, Model model) throws IOException, DiscoveryException {
        this.contractInput = contractInput;
        this.model = model;


        if (outputMap.size() == 0)
            populateOutputMapKeys();

        clearModelMap();
        populateModelMap();
        return createCounterExampleAssertion();
    }

    private Exp createCounterExampleAssertion() {
        ArrayList<Exp> antecedent = generatTestValues(inputMap);
        //antecedent.add(new NExp(new Operator(EQ)), )

        Exp antecedentExp = new NExp(new Operator(AND), antecedent);

        ArrayList<Exp> consequent = generatTestValues(outputMap);

        Exp consequentExp = new NExp(new Operator(AND), consequent);

        ArrayList<Exp> implicationOperands = new ArrayList<Exp>();
        implicationOperands.add(antecedentExp);
        implicationOperands.add(consequentExp);

        return new NExp(new Operator(IMPLIES), implicationOperands);
    }



    /**
     * has the side effect of populating values to the 4 maps in this class with the right test case values.
     */
    private void populateModelMap() throws DiscoveryException {
        FuncDecl[] constDecl = model.getConstDecls();

        for (FuncDecl decl : constDecl) {
            Exp expValue;
            Expr interpretation = model.getConstInterp(decl);
            expValue = translateToAst(interpretation);
            if (outputMap.containsKey(decl.getName().toString()))
                updateMap(outputMap, decl.getName().toString(), expValue);
            else{
                Var var = translateToAstVar(decl);
                inputMap.put(var, expValue);
            }
        }
    }



    private boolean updateMap(HashMap<Var, Ast> map, String varName, Exp expValue) {
        for (Map.Entry<Var, Ast> entry : map.entrySet()) {
            if (entry.getKey().name.equals(varName)) {
                entry.setValue(expValue);
                return true;
            }
        }
        return false;
    }

    /**
     * has the side effect of populating inputModelMap and outputModelMap.
     */
    private void populateOutputMapKeys() throws IOException, DiscoveryException {

        /****** populating output ******/
        try (BufferedReader br = new BufferedReader(new FileReader(contractInput.outVarFileName))) {
            for (String line; (line = br.readLine()) != null; ) {
                // process the line.
                Var var = findInModel(line + "$r1");
                outputMap.put(var, null);
            }
        }
    }


    private void clearModelMap() {
        this.inputMap.clear();
        for (Map.Entry<Var, Ast> entry : outputMap.entrySet())
            entry.setValue(null);
    }
}
