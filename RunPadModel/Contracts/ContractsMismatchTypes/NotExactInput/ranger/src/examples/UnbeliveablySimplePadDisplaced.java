
public class UnbeliveablySimplePadDisplaced {
    private boolean start_btn;
    private boolean launch_btn;
    private boolean ignition_r;
    private boolean reset_btn;

    /*enum PadState {
        IDLE,
        READY,
        LAUNCH,
        RESET,
        INVALIDSTATE
    }
*/

    final int IDLE = 1;
    final int READY = 2;
    final int LAUNCH = 3;
    final int IGNITION = 4;
    final int RESET1 = 5;
    final int RESET2 = 6;
    final int INVALIDSTATE = 7;


    public UnbeliveablySimplePadDisplaced() {
        start_btn = false;
        launch_btn = false;
    }

    public static void main(String[] args) {
        UnbeliveablySimplePadDisplaced pad = new UnbeliveablySimplePadDisplaced();
        int count = 1;
        boolean start_btn = false;
        boolean launch_btn = false;
        boolean ignition = false;
        boolean reset_btn = false;
        int sig = 1;

        pad.runPadSteps(sig, start_btn, launch_btn, ignition, reset_btn, true);

    }

    public void runPadSteps(int sig, boolean start_btn, boolean launch_btn, boolean ignition, boolean reset_btn, boolean symVar) {

        //make pad state symbolic.
        this.start_btn = start_btn;
        this.launch_btn = launch_btn;
        this.ignition_r = ignition;
        this.reset_btn = reset_btn;

        boolean rocketLaunched = false;
        //Scanner reader = new Scanner(System.in);
        //int k = 0;
        //while (k <= 7) {
            //System.out.println("Enter a signal number (0-emptySignal) (1-startSignal) (2-armSignal) (3-launchSignal): ");
            //int n = reader.nextInt();

            if (symVar) { // used to make this a veritesting region
                rocketLaunched = runPad(sig); //running it here one step, but should be enclosed in a loop in real program.
                //assert (rocketLaunched ? getState() == IGNITION : true);
            }
            if (rocketLaunched) {
                // resetPad(); this is another variant of resetting the pad, for now let's reset_btn the pad in the next step.
                System.out.println("Rocket launched successfully.");
            } else
                System.out.println("Rocket still not launched.");
          //  ++k;
        //}
        //}
    }

    private void resetPad1() { //resetting step 1, for launch_btn and ignition
        launch_btn = false;
        ignition_r = false;
    }

    private void resetPad2() { //resetting step 2, for start_btn and reset_btn btn
        start_btn = false;
        reset_btn = false;
    }

    /**
     * It takes the input signal n, which either can be start, launch or empty, it has no reset_btn. So basically, it must go fom "IDLE" to
     * "READY" to "LAUNCH", but it might stay indefinitely in any of the first two states. While it can only stay in the "LAUNCH" state for only one time slot.
     *
     * @param n
     * @return
     */
    public boolean runPad(int n) {

        int previousState = getState();

        /**** pre conditions ****/
/*
        assert (ignition_r ? launch_btn && start_btn : true);
        assert (launch_btn ? start_btn : true);
        assert (reset_btn ? ((previousState == RESET1) || (previousState == RESET2)) : true);
*/


        if (previousState == LAUNCH) { //state needs to change regardless of the signal.
            ignition_r = true;
        } else if (previousState == IGNITION)
            reset_btn = true;
        else if (previousState == RESET1 || (previousState == INVALIDSTATE))
            resetPad1();
        else if (previousState == RESET2) { //state needs to change regardless of the signal.
            resetPad2();
        } else {
            boolean startSignal;
            boolean launchSignal;
            boolean startOrLaunch;

            startSignal = (n == 0);
            launchSignal = (n == 1);
            startOrLaunch = startSignal || launchSignal; //we have a problem in summarizing complex conditions

            if (startOrLaunch) { //only proceed if a non-empty signal was received, otherwise remain in the same state, ignoring incoming signal
                if (previousState == IDLE) {
                    if (startSignal) {
                        start_btn = true;
                    }
                } else {
                    if (previousState == READY) {
                        if (launchSignal) {
                            launch_btn = true;
                        }
                    }
                }
            }
        }

        /*int currentState = getState();

        *//***PRECONDITIONS *********//*
        //First two dependent on the signal
        assert (previousState == IDLE ? currentState == IDLE || currentState == READY : true);
        assert (previousState == READY ? currentState == READY || currentState == LAUNCH : true);
        assert (previousState == LAUNCH ? currentState == IGNITION : true);
        assert (previousState == IGNITION ? currentState == RESET1 : true);
        assert (previousState == RESET1 ? currentState == RESET2 : true);
        assert (previousState == RESET2 ? currentState == IDLE : true);
*/
        return ignition_r;
    }

    public int getState() {
        int padState;
        boolean mystartBtn = this.start_btn;
        boolean myLaunchBtn = this.launch_btn;
        boolean myIgnition = this.ignition_r;
        boolean reset = this.reset_btn;

        if (!mystartBtn && !myLaunchBtn && !myIgnition && !reset) //IDLE State
            padState = IDLE;
        else if (mystartBtn && !myLaunchBtn && !myIgnition && !reset) //Ready State
            padState = READY;
        else if (mystartBtn && myLaunchBtn && !myIgnition && !reset) // Launch State
            padState = LAUNCH;
        else if (mystartBtn && myLaunchBtn && myIgnition && !reset)
            padState = IGNITION;
        else if (mystartBtn && myLaunchBtn && myIgnition && reset)
            padState = RESET1;
        else if (mystartBtn && !myLaunchBtn && !myIgnition && reset)
            padState = RESET2;
        else
            padState = INVALIDSTATE; // Invalid State
        return padState;
    }

}
