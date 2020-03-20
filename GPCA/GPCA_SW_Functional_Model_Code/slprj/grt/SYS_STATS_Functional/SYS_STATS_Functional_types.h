/*
 * SYS_STATS_Functional_types.h
 *
 * Code generation for model "SYS_STATS_Functional".
 *
 * Model version              : 1.1404
 * Simulink Coder version : 8.4 (R2013a) 13-Feb-2013
 * C source code generated on : Thu Sep 12 09:50:15 2013
 *
 * Target selection: grt.tlc
 * Note: GRT includes extra infrastructure and instrumentation for prototyping
 * Embedded hardware selection: 32-bit Generic
 * Code generation objective: Debugging
 * Validation result: Not run
 */
#ifndef RTW_HEADER_SYS_STATS_Functional_types_h_
#define RTW_HEADER_SYS_STATS_Functional_types_h_
#include "rtwtypes.h"
#ifndef _DEFINED_TYPEDEF_FOR_Infusion_Manager_Outputs_
#define _DEFINED_TYPEDEF_FOR_Infusion_Manager_Outputs_

typedef struct {
  uint8_T Commanded_Flow_Rate;
  uint8_T Current_System_Mode;
  boolean_T New_Infusion;
  uint8_T Log_Message_ID;
  uint8_T Actual_Infusion_Duration;
} Infusion_Manager_Outputs;

#endif

#ifndef _DEFINED_TYPEDEF_FOR_Top_Level_Mode_Outputs_
#define _DEFINED_TYPEDEF_FOR_Top_Level_Mode_Outputs_

typedef struct {
  boolean_T System_On;
  boolean_T Request_Confirm_Stop;
  uint8_T Log_Message_ID;
} Top_Level_Mode_Outputs;

#endif

#ifndef _DEFINED_TYPEDEF_FOR_Device_Sensor_Inputs_
#define _DEFINED_TYPEDEF_FOR_Device_Sensor_Inputs_

typedef struct {
  uint8_T Flow_Rate;
  boolean_T Flow_Rate_Not_Stable;
  boolean_T Air_In_Line;
  boolean_T Occlusion;
  boolean_T Door_Open;
  boolean_T Temp;
  boolean_T Air_Pressure;
  boolean_T Humidity;
  boolean_T Battery_Depleted;
  boolean_T Battery_Low;
  boolean_T Battery_Unable_To_Charge;
  boolean_T Supply_Voltage;
  boolean_T CPU_In_Error;
  boolean_T RTC_In_Error;
  boolean_T Watchdog_Interrupted;
  boolean_T Memory_Corrupted;
  boolean_T Pump_Too_Hot;
  boolean_T Pump_Overheated;
  boolean_T Pump_Primed;
  boolean_T Post_Successful;
} Device_Sensor_Inputs;

#endif

#ifndef _DEFINED_TYPEDEF_FOR_Device_Configuration_Inputs_
#define _DEFINED_TYPEDEF_FOR_Device_Configuration_Inputs_

typedef struct {
  uint8_T Audio_Enable_Duration;
  uint8_T Audio_Level;
  uint8_T Config_Warning_Duration;
  uint8_T Empty_Reservoir;
  uint8_T Low_Reservoir;
  uint8_T Max_Config_Duration;
  uint8_T Max_Duration_Over_Infusion;
  uint8_T Max_Duration_Under_Infusion;
  uint8_T Max_Paused_Duration;
  uint8_T Max_Idle_Duration;
  uint8_T Tolerance_Max;
  uint8_T Tolerance_Min;
  uint8_T Log_Interval;
  uint8_T System_Test_Interval;
  uint8_T Max_Display_Duration;
  uint8_T Max_Confirm_Stop_Duration;
} Device_Configuration_Inputs;

#endif

#ifndef _DEFINED_TYPEDEF_FOR_Config_Outputs_
#define _DEFINED_TYPEDEF_FOR_Config_Outputs_

typedef struct {
  uint8_T Patient_ID;
  uint8_T Drug_Name;
  uint8_T Drug_Concentration;
  uint8_T Infusion_Total_Duration;
  uint8_T VTBI_Total;
  uint8_T Flow_Rate_Basal;
  uint8_T Flow_Rate_Intermittent_Bolus;
  uint8_T Duration_Intermittent_Bolus;
  uint8_T Interval_Intermittent_Bolus;
  uint8_T Flow_Rate_Patient_Bolus;
  uint8_T Duration_Patient_Bolus;
  uint8_T Lockout_Period_Patient_Bolus;
  uint8_T Max_Number_of_Patient_Bolus;
  uint8_T Flow_Rate_KVO;
  uint8_T Entered_Reservoir_Volume;
  uint8_T Reservoir_Volume;
  uint8_T Configured;
  uint8_T Error_Message_ID;
  boolean_T Request_Config_Type;
  boolean_T Request_Confirm_Infusion_Initiate;
  boolean_T Request_Patient_Drug_Info;
  boolean_T Request_Infusion_Info;
  uint8_T Log_Message_ID;
  uint8_T Config_Timer;
  uint8_T Config_Mode;
} Config_Outputs;

#endif

#ifndef _DEFINED_TYPEDEF_FOR_System_Status_Outputs_
#define _DEFINED_TYPEDEF_FOR_System_Status_Outputs_

typedef struct {
  boolean_T Reservoir_Empty;
  uint8_T Reservoir_Volume;
  uint8_T Volume_Infused;
  uint8_T Log_Message_ID;
  boolean_T In_Therapy;
} System_Status_Outputs;

#endif

/* Forward declaration for rtModel */
typedef struct tag_RTM_SYS_STATS_Functional_T RT_MODEL_SYS_STATS_Functional_T;

#endif                                 /* RTW_HEADER_SYS_STATS_Functional_types_h_ */
