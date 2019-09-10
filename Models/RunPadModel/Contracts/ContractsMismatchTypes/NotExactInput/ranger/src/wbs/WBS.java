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


import com.ibm.wala.cast.tree.pattern.Alt;
import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.vm.Verify;


public class WBS {
	
	//Internal state
	private int WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE; 
	private int WBS_Node_WBS_BSCU_rlt_PRE1; 
	private int WBS_Node_WBS_rlt_PRE2;
//	boolean WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6; //10

	//Outputs
	private int Nor_Pressure;
	private int Alt_Pressure;
	private int Sys_Mode;

	@Override
	public boolean equals(Object obj) {
		if (WBS.class.isInstance(obj)) {
			WBS o = (WBS) obj;
			return (Nor_Pressure == o.Nor_Pressure) && (Alt_Pressure == o.Alt_Pressure) && (Sys_Mode == o.Sys_Mode) &&
					(WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == o.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE) &&
					(WBS_Node_WBS_BSCU_rlt_PRE1 == o.WBS_Node_WBS_BSCU_rlt_PRE1) &&
					(WBS_Node_WBS_rlt_PRE2 == o.WBS_Node_WBS_rlt_PRE2);// &&
//					(WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6 == o.WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6);
		}
		return false;
	}

	public WBS() {
		WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE = 0;	
		WBS_Node_WBS_BSCU_rlt_PRE1 = 0;
		WBS_Node_WBS_rlt_PRE2 = 100;
		Nor_Pressure = 0;
		Alt_Pressure = 0;
		Sys_Mode = 0;
	}
	
	public void update(int PedalPos, boolean AutoBrake, 
			boolean Skid) {
		int WBS_Node_WBS_AS_MeterValve_Switch; //4
		int WBS_Node_WBS_AccumulatorValve_Switch; //5
		int WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch; //6
		boolean WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator; //7
		int WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1; //8
		int WBS_Node_WBS_BSCU_Command_Switch; //9
		boolean WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6; //10
		int WBS_Node_WBS_BSCU_SystemModeSelCmd_Unit_Delay; //11
		int WBS_Node_WBS_BSCU_Switch2; //12
		int WBS_Node_WBS_BSCU_Switch3; //13
		int WBS_Node_WBS_BSCU_Unit_Delay1; //14
		int WBS_Node_WBS_Green_Pump_IsolationValve_Switch; //15
		int WBS_Node_WBS_SelectorValve_Switch; //16
		int WBS_Node_WBS_SelectorValve_Switch1; //17
		int WBS_Node_WBS_Unit_Delay2; //18
		
	   WBS_Node_WBS_Unit_Delay2 = WBS_Node_WBS_rlt_PRE2; 
	   WBS_Node_WBS_BSCU_Unit_Delay1 = WBS_Node_WBS_BSCU_rlt_PRE1; 
	   WBS_Node_WBS_BSCU_SystemModeSelCmd_Unit_Delay = WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE; 

	   WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator = (WBS_Node_WBS_BSCU_SystemModeSelCmd_Unit_Delay == 0); 

	   if ((PedalPos == 0)) {
		      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 0;
		   } else { 
			   if ((PedalPos == 1)) {
			      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 1;
			   }  else { 
				   if ((PedalPos == 2)) { 
				      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 2;
				   } else { 
					   if ((PedalPos == 3)) { 
					      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 3;
					   } else { 
						   if ((PedalPos == 4)) {
						      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 4;
						   }  else { 
						      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 0;
						   }
					   }
				   }
			   }
		   }
		//Debug.printPC("PC @fter(PadalPos == 0) [Region 1] = ");


		if ((AutoBrake &&
		         WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator)) {
		      WBS_Node_WBS_BSCU_Command_Switch = 1; 
		   }  else { 
		      WBS_Node_WBS_BSCU_Command_Switch = 0;
		   }
		//Debug.printPC("PC @fter(AutoBrake && WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator) [Region 2] = ");


	   WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6 = ((((!(WBS_Node_WBS_BSCU_Unit_Delay1 == 0)) &&
	         (WBS_Node_WBS_Unit_Delay2 <= 0)) && 
	         WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator) || 
	         (!WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator));

	//	Debug.printPC("PC @fter(WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6 = ((((!(WBS_Node_WBS_BSCU_Unit_Delay1 == 0)) &&) = ");

	   if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      if (Skid) 
	         WBS_Node_WBS_BSCU_Switch3 = 0; 
	      else 
	         WBS_Node_WBS_BSCU_Switch3 = 4; 
	   }
	   else { 
	      WBS_Node_WBS_BSCU_Switch3 = 4;
	    }

	//	Debug.printPC("PC @fter((WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6)) = ");


	   if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      WBS_Node_WBS_Green_Pump_IsolationValve_Switch = 0;
	   }  else { 
	      WBS_Node_WBS_Green_Pump_IsolationValve_Switch = 5; 
	    }

	//	Debug.printPC("PC @fter(WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) = ");

	   if ((WBS_Node_WBS_Green_Pump_IsolationValve_Switch >= 1)) {
	      WBS_Node_WBS_SelectorValve_Switch1 = 0; 
	   }
	   else { 
	      WBS_Node_WBS_SelectorValve_Switch1 = 5; 
	   }

	//	Debug.printPC("PC @fter(WBS_Node_WBS_Green_Pump_IsolationValve_Switch >= 1) = ");

	   if ((!WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6)) {
	      WBS_Node_WBS_AccumulatorValve_Switch = 0; 
	   }  else { 
		   if ((WBS_Node_WBS_SelectorValve_Switch1 >= 1)) { 
		      WBS_Node_WBS_AccumulatorValve_Switch = WBS_Node_WBS_SelectorValve_Switch1;
		   }
		   else { 
		      WBS_Node_WBS_AccumulatorValve_Switch = 5;
		   }
	   }

	//	Debug.printPC("PC @fter(!WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) = ");


		if ((WBS_Node_WBS_BSCU_Switch3 == 0)) {
	      WBS_Node_WBS_AS_MeterValve_Switch = 0;
	   }  else { 
		   if ((WBS_Node_WBS_BSCU_Switch3 == 1))  {
		      WBS_Node_WBS_AS_MeterValve_Switch = (WBS_Node_WBS_AccumulatorValve_Switch / 4);
		   }  else {  
			   if ((WBS_Node_WBS_BSCU_Switch3 == 2))  {
			      WBS_Node_WBS_AS_MeterValve_Switch = (WBS_Node_WBS_AccumulatorValve_Switch / 2);
			   }  else { 
				   if ((WBS_Node_WBS_BSCU_Switch3 == 3)) { 
				      WBS_Node_WBS_AS_MeterValve_Switch = ((WBS_Node_WBS_AccumulatorValve_Switch / 4) * 3);
				   }  else { 
					   if ((WBS_Node_WBS_BSCU_Switch3 == 4)) { 
					      WBS_Node_WBS_AS_MeterValve_Switch = WBS_Node_WBS_AccumulatorValve_Switch;
					   }  else { 
					      WBS_Node_WBS_AS_MeterValve_Switch = 0;
					   }
				   }
			   }
		   }
	   }
	//	Debug.printPC("PC @fter(WBS_Node_WBS_BSCU_Switch3 == 0) = ");



	   if (Skid) {
	      WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch = 0;
	   }  else { 
	      WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch = (WBS_Node_WBS_BSCU_Command_Switch+WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1);
	   }
		//Debug.printPC("PC @fter(Skid) [Region 3] = ");


	   if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      Sys_Mode = 1; 
	   }  else { 
	      Sys_Mode = 0;
	   }
	//	Debug.printPC("PC @fter(WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) = ");


		if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      WBS_Node_WBS_BSCU_Switch2 = 0; 
	   }  else { 
		   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 0) && 
		         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 1))) {					   
		      WBS_Node_WBS_BSCU_Switch2 = 0; 
		   } else { 
			   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 1) && 
			         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 2)))  {
			      WBS_Node_WBS_BSCU_Switch2 = 1; 
			   }  else { 
				   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 2) && 
				         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 3))) {
				      WBS_Node_WBS_BSCU_Switch2 = 2; 
				   } else { 
					   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 3) && 
					         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 4)))  {
					      WBS_Node_WBS_BSCU_Switch2 = 3; 
					   } else { 
					      WBS_Node_WBS_BSCU_Switch2 = 4;
					   }
				   }
			   }
		   }
	   }
		//Debug.printPC("PC @fter(WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) = ");



		if ((WBS_Node_WBS_Green_Pump_IsolationValve_Switch >= 1))  {
	      WBS_Node_WBS_SelectorValve_Switch = WBS_Node_WBS_Green_Pump_IsolationValve_Switch;
	   }  else { 
	      WBS_Node_WBS_SelectorValve_Switch = 0;
	   }
	//	Debug.printPC("PC @fter(WBS_Node_WBS_Green_Pump_IsolationValve_Switch >= 1) = ");



	   if ((WBS_Node_WBS_BSCU_Switch2 == 0)) {
	      Nor_Pressure = 0; 
	   }  else { 
		   if ((WBS_Node_WBS_BSCU_Switch2 == 1)) {
		      Nor_Pressure = (WBS_Node_WBS_SelectorValve_Switch / 4);
		   }  else {
			   if ((WBS_Node_WBS_BSCU_Switch2 == 2)) {
			      Nor_Pressure = (WBS_Node_WBS_SelectorValve_Switch / 2);
			   }  else { 
				   if ((WBS_Node_WBS_BSCU_Switch2 == 3)) {
				      Nor_Pressure = ((WBS_Node_WBS_SelectorValve_Switch / 4) * 3);
				   } else { 
					   if ((WBS_Node_WBS_BSCU_Switch2 == 4)) { 
					      Nor_Pressure = WBS_Node_WBS_SelectorValve_Switch;
					   } else { 
					      Nor_Pressure = 0;
					   }
				   }
			   }
		   }
	   }

	//	Debug.printPC("PC before(WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 0) [region 4] = ");

	   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 0)) {
	      Alt_Pressure = 0; 
	   }  else {
		   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 1)) {
		      Alt_Pressure = (WBS_Node_WBS_AS_MeterValve_Switch / 4); 
		   }  else { 
			   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 2)) {
			      Alt_Pressure = (WBS_Node_WBS_AS_MeterValve_Switch / 2);
			   } else { 
				   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 3)) {
				      Alt_Pressure = ((WBS_Node_WBS_AS_MeterValve_Switch / 4) * 3);
				   } else { 
					   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 4)) {
					      Alt_Pressure = WBS_Node_WBS_AS_MeterValve_Switch; 
					   } else { 
					      Alt_Pressure = 0;
					   }
				   }
			   }
		   }
	   }
	//	Debug.printPC("PC @fter(WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 0) [Region 4] = ");


	   WBS_Node_WBS_rlt_PRE2 = Nor_Pressure; 

	   WBS_Node_WBS_BSCU_rlt_PRE1 = WBS_Node_WBS_BSCU_Switch2; 

	   WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE = Sys_Mode;
//	   WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE += WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6 ? 1 : 0;

		// Assertions added by MWW: these are the truth values for "my" version of the model - may not be true for this code, but should be
		// consistent between SPF and Veritest-SPF.

		// I prefer ite to !a || b for implications.

		// This assertion should prove:
        //boolean myassert = (PedalPos > 0 && PedalPos <= 4 && !Skid) ? (Alt_Pressure > 0 || Nor_Pressure > 0) : true;
        //assert(myassert);
		//assert((PedalPos > 0 && PedalPos <= 4 && !Skid) ? (Alt_Pressure > 0 || Nor_Pressure > 0) : true);

		// This assertion should fail:
		 //assert((PedalPos > 0 && PedalPos <= 4 && !Skid) ? (Alt_Pressure > 0) : true);

		// This assertion may fail (depending on encoding):
		//Debug.printPC("PC before assertion ((PedalPos > 0 && PedalPos <= 4 && !Skid) ? (Nor_Pressure > 0) : true) = ");

		// assert((PedalPos > 0 && PedalPos <= 4 && !Skid) ? (Nor_Pressure > 0) : true);

		// This assertion should fail:
		 //assert((PedalPos > 0 && PedalPos <= 4) ? (Alt_Pressure > 0 || Nor_Pressure > 0) : true);

		// This assertion should also fail:
		//assert((PedalPos > 0 && !Skid) ? (Alt_Pressure > 0 || Nor_Pressure > 0) : true);
	}


	public static void launch(int pedal1, boolean auto1, boolean skid1, int pedal2, boolean auto2, boolean skid2, int pedal3, boolean auto3, boolean skid3) {
		WBS wbs = new WBS();
		wbs.update(pedal1, auto1, skid1);
		wbs.update(pedal2, auto2, skid2);
		wbs.update(pedal3, auto3, skid3);
	}
	
	public static void main(String[] args) {
		launch(0,false,false,0,false,false,0,false,false);
	}
}