/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.init;

/**
 *
 * @author Erika
 */
public class Combination {
    /**
     * Checks whether the combination given as parameter is valid in the number base represented by caseNumber
     * @param scenario the current scenario
     * @param caseNumber the base number
     * @return true if and only if the combination is valid
     */
    private static boolean isValidCombination(int[] scenario, int caseNumber)
    {
        //Extreme case
        if (caseNumber == 1)
        {
            return true;
        }
        //Initialization of boolean array with false values (except the very first element)
        boolean[] expectedElements = new boolean[caseNumber];
        for (int expectedElementIndex = 0; expectedElementIndex < expectedElements.length; expectedElementIndex++)
        {
            expectedElements[expectedElementIndex] = expectedElementIndex == 0;
        }
        //Iterating the scenario and stopping if the scenario is valid
        for (int scenarioElementIndex = 0; scenarioElementIndex < scenario.length; scenarioElementIndex++)
        {
            //If the number was not registered yet, then we register it and check the validity of the scenario
            if (!expectedElements[scenario[scenarioElementIndex]])//ha meg nem tal oan szamjegyet
            {
                //Registering the value
                expectedElements[scenario[scenarioElementIndex]] = true;
                //Initializing validity to a value of true
                boolean valid = true;
                //Checking validity
                for (int validationIndex = 0; (valid) && (validationIndex < expectedElements.length); validationIndex++)
                {
                    valid = expectedElements[validationIndex];
                }
                //Valid
                if (valid)
                {
                    return true;
                }
            }
        }
        //Invalid
        return false;
    }
    
    /**
     * Useful to generate combinations
     * @param currentScenario the current scenario
     * @param caseNumber base number
     * @return the next valid scenario. If currentScenario is the last valid scenario, then the method will return null
     */
    public static int[] getNextScenario(int[] currentScenario, int caseNumber)//esetek szam=szamrendszer
    {
        //We need this variable for later checks
        int currentScenarioIndex = -1;
        //Apparently infinite loop, but in fact we will have a return value in any case
        while ( ( currentScenario.length > 0 ) && (currentScenario[0] < caseNumber) )//az a lenyeg hogy ez igaz legyen
        {
            //We cannot stop before we start ;)
            boolean stopIteration = false;
            //We represent the scenario as a number, because we are sooo tricky
            for (currentScenarioIndex = currentScenario.length - 1; ((!stopIteration) && (currentScenarioIndex >= 0)); currentScenarioIndex--)
            {
                //Storing the next value of the "digit"
                currentScenario[currentScenarioIndex] = (currentScenario[currentScenarioIndex] + 1) % caseNumber;
                //If the digit is not 0, then of course we must stop (for mathematical reasons)
                stopIteration = currentScenario[currentScenarioIndex] != 0;//hozza adunk egyet s ha a maradek nem nulla akkor nem kell az elozo szamjegyhez is hozza adjunk egyet
            }
            //Full stop
            if ((currentScenarioIndex < 0) && (!stopIteration))
            {
                return null;
            }
            //We have got the next scenario, the job of the method is completed
            else if (isValidCombination(currentScenario, caseNumber))
            {
                return currentScenario;
            }
        }
        //This return is not needed, but Java compiler is not smart enough to understand semantically our algorithm
        return null;
    }
    
    public static int[] getNextScenario(int numberOfDigits, int caseNumber ){
        int[] initialScenario = new int[numberOfDigits];
        for(int initialScenarioIndex = 0; initialScenarioIndex < initialScenario.length; initialScenarioIndex++){
            initialScenario[initialScenarioIndex] = 0;
        }
        return getNextScenario(initialScenario, caseNumber);
    }
}
