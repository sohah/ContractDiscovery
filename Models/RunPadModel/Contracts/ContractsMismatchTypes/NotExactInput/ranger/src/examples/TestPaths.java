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

/**
 * example to demonstrate creation of test suites for path coverage
 * modified for veritesting
 */

import gov.nasa.jpf.symbc.Debug;

public class TestPaths {

  public static void main (String[] args){
   // testMe(42, false);
	System.out.println("!!!!!!!!!!!!!!! Start Testing! ");
	(new TestPaths()).testMe3(0,0);
  }

  public void testMe3 (int x, int y) {
    //System.out.println("x = " + x + ", y = " + y);
		// int a_final; // = Debug.makeSymbolicInteger("a_final");
		// int b_final; // = Debug.makeSymbolicInteger("b_final");
    int a=11, b=12;
  
		// Begin region for static unrolling
    if (x < 0 ) {
        a = -1;
        return;
    }
    else if (x  == 0 ) a = 0;
	  else a = 1;
    if (y < 0 ) b = -1;
		else if (y == 0 ) { b = 0; return; }
    else b = 1;
		// End region for static unrolling
   
    if (a == -1) System.out.println("a = -1");
    else if (a == 1) System.out.println("a = 1");
    else System.out.println("a != 1 && a != -1");
    if(b == -1) System.out.println("b = -1");
		else if (b == 1) System.out.println("b = 1");
    else System.out.println("b != 1 && b != 1");
    System.out.println("-x-x-x-x-");
  }

  // how many tests do we need to cover all paths?
  public static void testMe (int x, boolean b) {
    System.out.println("x = " + x);
    int y=0;
    if (x <= 1200){
      //System.out.println("  <= 1200");
      y=-1;
    }
    if(x >= 1200){
      //System.out.println("  >= 1200");
      y=1;
    }
  }

  public void testMe2 (int x, boolean b) {
	  System.out.println("!!!!!!!!!!!!!!! First step! ");
	    //System.out.println("x = " + x);
        if (b) {
        	if (x <= 1200){
        		System.out.println("  <= 1200");
        	}
        	if(x >= 1200){
        		System.out.println("  >= 1200");
        	}
        } else System.out.println("  b is false");
  }

}
