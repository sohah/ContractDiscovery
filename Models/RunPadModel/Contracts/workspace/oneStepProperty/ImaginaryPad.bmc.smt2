(set-option :produce-models true)
(set-option :produce-unsat-cores true)
(define-fun T ((%init Bool) ($sig$0 Int) ($ignition$0 Bool) ($start_bt$0 Bool) ($launch_bt$0 Bool) ($reset_flag$0 Bool) ($p1$0 Bool) ($sig$1 Int) ($ignition$1 Bool) ($start_bt$1 Bool) ($launch_bt$1 Bool) ($reset_flag$1 Bool) ($p1$1 Bool)) Bool (and (= $start_bt$1 (ite %init false (ite $reset_flag$0 false (ite (and (and (not $start_bt$0) (not $launch_bt$0)) (= $sig$1 0)) true $start_bt$0)))) (= $launch_bt$1 (ite %init false (ite $reset_flag$0 false (ite (and (and $start_bt$0 (not $launch_bt$0)) (= $sig$1 1)) true $launch_bt$0)))) (= $ignition$1 (ite %init false (and $launch_bt$0 (and (not $ignition$0) (not $reset_flag$0))))) (= $reset_flag$1 (ite %init false (and $ignition$0 (not $reset_flag$0)))) (= $p1$1 (=> $ignition$1 $launch_bt$1))))
(declare-fun %init () Bool)
(declare-fun $sig$~1 () Int)
(declare-fun $ignition$~1 () Bool)
(declare-fun $start_bt$~1 () Bool)
(declare-fun $launch_bt$~1 () Bool)
(declare-fun $reset_flag$~1 () Bool)
(declare-fun $p1$~1 () Bool)
(assert (and (<= 0 $sig$~1) (<= $sig$~1 2)))
; K = 1
(declare-fun $sig$0 () Int)
(declare-fun $ignition$0 () Bool)
(declare-fun $start_bt$0 () Bool)
(declare-fun $launch_bt$0 () Bool)
(declare-fun $reset_flag$0 () Bool)
(declare-fun $p1$0 () Bool)
(assert (and (<= 0 $sig$0) (<= $sig$0 2)))
(assert (T true $sig$~1 $ignition$~1 $start_bt$~1 $launch_bt$~1 $reset_flag$~1 $p1$~1 $sig$0 $ignition$0 $start_bt$0 $launch_bt$0 $reset_flag$0 $p1$0))
(declare-fun act1 () Bool)
(assert (=> act1 (not $p1$0)))
(check-sat act1)
(echo "@DONE")
; Z3: unsat
; Z3: @DONE
(assert $p1$0)
; K = 2
(declare-fun $sig$1 () Int)
(declare-fun $ignition$1 () Bool)
(declare-fun $start_bt$1 () Bool)
(declare-fun $launch_bt$1 () Bool)
(declare-fun $reset_flag$1 () Bool)
(declare-fun $p1$1 () Bool)
(assert (and (<= 0 $sig$1) (<= $sig$1 2)))
(assert (T false $sig$0 $ignition$0 $start_bt$0 $launch_bt$0 $reset_flag$0 $p1$0 $sig$1 $ignition$1 $start_bt$1 $launch_bt$1 $reset_flag$1 $p1$1))
(declare-fun act2 () Bool)
(assert (=> act2 (not $p1$1)))
(check-sat act2)
(echo "@DONE")
; Z3: unsat
; Z3: @DONE
(assert $p1$1)
; K = 3
(declare-fun $sig$2 () Int)
(declare-fun $ignition$2 () Bool)
(declare-fun $start_bt$2 () Bool)
(declare-fun $launch_bt$2 () Bool)
(declare-fun $reset_flag$2 () Bool)
(declare-fun $p1$2 () Bool)
(assert (and (<= 0 $sig$2) (<= $sig$2 2)))
(assert (T false $sig$1 $ignition$1 $start_bt$1 $launch_bt$1 $reset_flag$1 $p1$1 $sig$2 $ignition$2 $start_bt$2 $launch_bt$2 $reset_flag$2 $p1$2))
(declare-fun act3 () Bool)
(assert (=> act3 (not $p1$2)))
(check-sat act3)
(echo "@DONE")
; Z3: unsat
; Z3: @DONE
(assert $p1$2)
; K = 4
(declare-fun $sig$3 () Int)
(declare-fun $ignition$3 () Bool)
(declare-fun $start_bt$3 () Bool)
(declare-fun $launch_bt$3 () Bool)
(declare-fun $reset_flag$3 () Bool)
(declare-fun $p1$3 () Bool)
(assert (and (<= 0 $sig$3) (<= $sig$3 2)))
(assert (T false $sig$2 $ignition$2 $start_bt$2 $launch_bt$2 $reset_flag$2 $p1$2 $sig$3 $ignition$3 $start_bt$3 $launch_bt$3 $reset_flag$3 $p1$3))
(declare-fun act4 () Bool)
(assert (=> act4 (not $p1$3)))
(check-sat act4)
(echo "@DONE")
; Z3: unsat
; Z3: @DONE
(assert $p1$3)
; K = 5
(declare-fun $sig$4 () Int)
(declare-fun $ignition$4 () Bool)
(declare-fun $start_bt$4 () Bool)
(declare-fun $launch_bt$4 () Bool)
(declare-fun $reset_flag$4 () Bool)
(declare-fun $p1$4 () Bool)
(assert (and (<= 0 $sig$4) (<= $sig$4 2)))
(assert (T false $sig$3 $ignition$3 $start_bt$3 $launch_bt$3 $reset_flag$3 $p1$3 $sig$4 $ignition$4 $start_bt$4 $launch_bt$4 $reset_flag$4 $p1$4))
(declare-fun act5 () Bool)
(assert (=> act5 (not $p1$4)))
(check-sat act5)
(echo "@DONE")
; Z3: unsat
; Z3: @DONE
(assert $p1$4)
; K = 6
(declare-fun $sig$5 () Int)
(declare-fun $ignition$5 () Bool)
(declare-fun $start_bt$5 () Bool)
(declare-fun $launch_bt$5 () Bool)
(declare-fun $reset_flag$5 () Bool)
(declare-fun $p1$5 () Bool)
(assert (and (<= 0 $sig$5) (<= $sig$5 2)))
(assert (T false $sig$4 $ignition$4 $start_bt$4 $launch_bt$4 $reset_flag$4 $p1$4 $sig$5 $ignition$5 $start_bt$5 $launch_bt$5 $reset_flag$5 $p1$5))
(declare-fun act6 () Bool)
(assert (=> act6 (not $p1$5)))
(check-sat act6)
(echo "@DONE")
; Z3: unsat
; Z3: @DONE
(assert $p1$5)
; K = 7
