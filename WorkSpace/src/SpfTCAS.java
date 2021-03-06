
public class SpfTCAS {

    //free input
    public static int Cur_Vertical_Sep;
    public static int Own_Tracked_Alt;
    public static int Own_Tracked_Alt_Rate;
    public static int Other_Tracked_Alt;
    public static int Alt_Layer_Value;
    public static int Up_Separation;
    public static int Down_Separation;
    public static int Other_RAC;
    public static int Other_Capability;
    public static int Climb_Inhibit;


    //all state input
    public static int OLEV = 600;
    public static int MAXALTDIFF = 300;
    public static int MINSEP = 600;
    public static int NOZCROSS = 100;
    public static boolean High_Confidence;
    public static boolean Two_of_Three_Reports_Valid;
    static int Positive_RA_Alt_Thresh_0;
    static int Positive_RA_Alt_Thresh_1;
    static int Positive_RA_Alt_Thresh_2;
    static int Positive_RA_Alt_Thresh_3;
    public static int NO_INTENT = 0;
    public static int DO_NOT_CLIMB = 1;
    public static int DO_NOT_DESCEND = 2;
    public static int TCAS_TA = 1;
    public static int OTHER = 2;
    public static int UNRESOLVED = 0;
    public static int UPWARD_RA = 1;
    public static int DOWNWARD_RA = 2;


    //created field for output
    private static int result_alt_sep_test = 0;
    private static int alim_res = 0;

    public static void initialize() {
        Positive_RA_Alt_Thresh_0 = 400;
        Positive_RA_Alt_Thresh_1 = 500;
        Positive_RA_Alt_Thresh_2 = 640;
        Positive_RA_Alt_Thresh_3 = 740;
    }

    public static int ALIM() {
        if (Alt_Layer_Value == 0) {
            return Positive_RA_Alt_Thresh_0;
        } else if (Alt_Layer_Value == 1) {
            return Positive_RA_Alt_Thresh_1;
        } else if (Alt_Layer_Value == 2) {
            return Positive_RA_Alt_Thresh_2;
        } else {
            return Positive_RA_Alt_Thresh_3;
        }
    }

    public static int Inhibit_Biased_Climb() {
        if (Climb_Inhibit > 0) {
            int ret = Up_Separation + NOZCROSS;
            return ret;
        } else {
            return Up_Separation;
        }
    }

    public static boolean Non_Crossing_Biased_Climb() {
        int upward_preferred;
        int inhibit_biased_climb = Inhibit_Biased_Climb();
        if (inhibit_biased_climb > Down_Separation) {
            upward_preferred = 1;
        } else {
            upward_preferred = 0;
        }
        if (upward_preferred != 0) {
            int alim = ALIM();
            if (!(Down_Separation >= alim)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (!(Cur_Vertical_Sep >= MINSEP)) {
                return false;
            } else {
                int alim = ALIM();
                if (!(Up_Separation >= alim)) {
                    return false;
                } else {
                    boolean own_above_thread = Own_Above_Threat();
                    if (!own_above_thread) {
                        return false;
                    } else {
                        return true;
                    }
                }

            }
        }
    }

    public static boolean Non_Crossing_Biased_Descend() {
        int upward_preferred;
        int inhibit_biased_climb = Inhibit_Biased_Climb();
        if (inhibit_biased_climb > Down_Separation) {
            upward_preferred = 1;
        } else {
            upward_preferred = 0;
        }
        if (upward_preferred != 0) {
            int alim = ALIM();
            boolean own_below_threat = Own_Below_Threat();
            // reduction source
            if (!(Cur_Vertical_Sep >= MINSEP)) {
                return false;
            } else if (!(Down_Separation >= alim)) {
                return false;
            } else if (!own_below_threat) {
                return false;
            } else {
                return true;
            }
        } else {
            int alim = ALIM();
            boolean own_above_threat = Own_Above_Threat();
            // reduction source
            if (!(Up_Separation >= alim)) {
                return false;
            } else if (!own_above_threat) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean Own_Below_Threat() {
        boolean ret = false;
        if (Own_Tracked_Alt < Other_Tracked_Alt) {
            ret = true;
        }
        return ret;
    }

    public static boolean Own_Above_Threat() {
        boolean ret = false;
        if (Other_Tracked_Alt < Own_Tracked_Alt) {
            ret = true;
        }
        return ret;
    }

    public static int alt_assign() {
        int alt_sep = UNRESOLVED;
        boolean need_upward_RA = false;
        boolean non_crossing_biased_climb = Non_Crossing_Biased_Climb();
        if (non_crossing_biased_climb) {
            boolean own_below_threat = Own_Below_Threat(); //return symbolic temp variable
            if (own_below_threat) {
                need_upward_RA = true; //is symbolic
            }
        }
        boolean need_downward_RA = false;
        boolean non_crossing_biased_descend = Non_Crossing_Biased_Descend();
        if (non_crossing_biased_descend) {
            boolean own_above_threat = Own_Above_Threat();
            if (own_above_threat) {
                need_downward_RA = true;
            }
        }
        if (need_upward_RA) {
            if (need_downward_RA) {
                alt_sep = UNRESOLVED;
            } else {
                alt_sep = UPWARD_RA;
            }
        } else {
            if (need_downward_RA) {
                alt_sep = DOWNWARD_RA;
            } else {
                alt_sep = UNRESOLVED;
            }
        }

		/*if(need_upward_RA && need_downward_RA) alt_sep = 0;
		if(need_upward_RA && !need_downward_RA) alt_sep = 1;
		if(!need_upward_RA && need_downward_RA) alt_sep = 2;
		if(!need_upward_RA && !need_downward_RA) alt_sep = 0;*/

        return alt_sep;
    }

    public static int alt_sep_test() {
        boolean enabled = false;
        boolean tcas_equipped = false;
        boolean intent_not_known = false;
        int alt_sep = UNRESOLVED;

        if (High_Confidence) {
            if (Own_Tracked_Alt_Rate <= OLEV) {
                if (Cur_Vertical_Sep > MAXALTDIFF) {
                    enabled = true;
                }
            }
        }

        if (enabled) {
            if (Other_Capability == TCAS_TA) {
                tcas_equipped = true;
            }
            if (tcas_equipped) {
                if (Two_of_Three_Reports_Valid) {
                    if (Other_RAC == NO_INTENT) {
                        intent_not_known = true;
                    }
                }
                if (intent_not_known) {
                    alt_sep = alt_assign();
                }
            } else {
                alt_sep = alt_assign();
            }
        }

        return alt_sep;
    }

    public static void mainProcess(int Cur_Vertical_Sep, int High_Confidence_flag, int Two_of_Three_Reports_Valid_flag,
                                   int Own_Tracked_Alt, int Own_Tracked_Alt_Rate, int Other_Tracked_Alt,
                                   int Alt_Layer_Value, int Up_Separation, int Down_Separation, int Other_RAC, int Other_Capability, int Climb_Inhibit) {
        initialize();
        SpfTCAS.Cur_Vertical_Sep = Cur_Vertical_Sep;
        if (High_Confidence_flag == 0) {
            SpfTCAS.High_Confidence = false;
        } else {
            SpfTCAS.High_Confidence = true;
        }
        if (Two_of_Three_Reports_Valid_flag == 0) {
            SpfTCAS.Two_of_Three_Reports_Valid = false;
        } else {
            SpfTCAS.Two_of_Three_Reports_Valid = true;
        }

        SpfTCAS.Own_Tracked_Alt = Own_Tracked_Alt;
        SpfTCAS.Own_Tracked_Alt_Rate = Own_Tracked_Alt_Rate;
        SpfTCAS.Other_Tracked_Alt = Other_Tracked_Alt;
        SpfTCAS.Alt_Layer_Value = Alt_Layer_Value;
        SpfTCAS.Up_Separation = Up_Separation;
        SpfTCAS.Down_Separation = Down_Separation;
        SpfTCAS.Other_RAC = Other_RAC;
        SpfTCAS.Other_Capability = Other_Capability;
        SpfTCAS.Climb_Inhibit = Climb_Inhibit;

        result_alt_sep_test = alt_sep_test();
        SpfTCAS.alim_res = ALIM();

        System.out.println("value of Down_Separation is = " + Down_Separation);
        System.out.println("value of alim_res is = " + alim_res);
        // MWW assertions.  These come from ACSL safety property paper: http://people.rennes.inria.fr/Arnaud.Gotlieb/CT_ATM_gotlieb.pdf


        // passes(1)
		/*assert((Up_Separation < alim_res &&
				Down_Separation < alim_res) ?
				result_alt_sep_test != DOWNWARD_RA : true);
*/
        //passes(2)
		/*assert((Up_Separation < alim_res &&
				Down_Separation >= alim_res) ?
				result_alt_sep_test != UPWARD_RA : true);
*/
        // fails (1)
		/*assert((Up_Separation > alim_res &&
				Down_Separation >= alim_res &&
				Own_Tracked_Alt > Other_Tracked_Alt) ?
				result_alt_sep_test != DOWNWARD_RA : true);
*/
        // fails(2)
		/*assert((Up_Separation >= alim_res &&
				Down_Separation < alim_res) ?
				result_alt_sep_test != DOWNWARD_RA: true);
*/

        /***************** assertions from repairing**************/
        //assert(alim_res > 399);
    }

    public static void main(String[] argv) {
        mainProcess(546, 1, 0, 1, 600, 0, 0, 400, 500, 0, 2, 1);
    }
}
