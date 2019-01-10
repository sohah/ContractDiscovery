(set-option :produce-models true)
(set-option :produce-unsat-cores true)
(define-fun T ((%init Bool)
($sig$0 Int) ($ignition$0 Bool) ($reset_flag$0 Bool) ($p1$0 Bool)
  ($start_bt_val~0.start_bt$0 Bool) ($start_bt_val~0.launch_bt$0 Bool) ($start_bt_val~0.reset_flag$0 Bool) ($start_bt_val~0.start_bt_out$0 Bool)
  ($launch_bt_val~0.start_bt$0 Bool) ($launch_bt_val~0.launch_bt$0 Bool) ($launch_bt_val~0.reset_flag$0 Bool) ($launch_bt_val~0.launch_bt_out$0 Bool)
  ($Y~0.in$0 Bool) ($Y~0.out$0 Bool)
($sig$1 Int) ($ignition$1 Bool) ($reset_flag$1 Bool) ($p1$1 Bool)
  ($start_bt_val~0.start_bt$1 Bool) ($start_bt_val~0.launch_bt$1 Bool) ($start_bt_val~0.reset_flag$1 Bool) ($start_bt_val~0.start_bt_out$1 Bool)
  ($launch_bt_val~0.start_bt$1 Bool) ($launch_bt_val~0.launch_bt$1 Bool) ($launch_bt_val~0.reset_flag$1 Bool) ($launch_bt_val~0.launch_bt_out$1 Bool)
  ($Y~0.in$1 Bool) ($Y~0.out$1 Bool)) Bool

(and
  (= $ignition$1 (ite %init false $launch_bt_val~0.launch_bt_out$0))
  (= $reset_flag$1 (ite %init false $ignition$0))
  (= $p1$1 (=> $ignition$1 $Y~0.out$1))
  (= $start_bt_val~0.start_bt$1 (ite %init false $start_bt_val~0.start_bt_out$0))
  (= $start_bt_val~0.launch_bt$1 (ite %init false $launch_bt_val~0.launch_bt_out$0))
  (= $start_bt_val~0.reset_flag$1 (ite %init false $reset_flag$0))
  (= $start_bt_val~0.start_bt_out$1 (ite %init false (ite $start_bt_val~0.reset_flag$1 false (ite (and (and (not $start_bt_val~0.start_bt$0) (not $start_bt_val~0.launch_bt$0)) (= $sig$1 0)) true $start_bt_val~0.start_bt$0))))
  (= $launch_bt_val~0.start_bt$1 (ite %init false $start_bt_val~0.start_bt_out$0))
  (= $launch_bt_val~0.launch_bt$1 (ite %init false $launch_bt_val~0.launch_bt_out$0))
  (= $launch_bt_val~0.reset_flag$1 (ite %init false $reset_flag$0))
  (= $launch_bt_val~0.launch_bt_out$1 (ite %init false (ite $launch_bt_val~0.reset_flag$1 false (ite (and (and $launch_bt_val~0.start_bt$0 (not $launch_bt_val~0.launch_bt$0)) (= $sig$1 1)) true $launch_bt_val~0.launch_bt$0))))
  (= $Y~0.in$1 (and $launch_bt_val~0.launch_bt_out$1 $start_bt_val~0.start_bt_out$1))
  (= $Y~0.out$1 (ite %init false $Y~0.in$0))))

(declare-fun %init () Bool)
(declare-fun $sig$~1 () Int)
(declare-fun $ignition$~1 () Bool)
(declare-fun $reset_flag$~1 () Bool)
(declare-fun $p1$~1 () Bool)
(declare-fun $start_bt_val~0.start_bt$~1 () Bool)
(declare-fun $start_bt_val~0.launch_bt$~1 () Bool)
(declare-fun $start_bt_val~0.reset_flag$~1 () Bool)
(declare-fun $start_bt_val~0.start_bt_out$~1 () Bool)
(declare-fun $launch_bt_val~0.start_bt$~1 () Bool)
(declare-fun $launch_bt_val~0.launch_bt$~1 () Bool)
(declare-fun $launch_bt_val~0.reset_flag$~1 () Bool)
(declare-fun $launch_bt_val~0.launch_bt_out$~1 () Bool)
(declare-fun $Y~0.in$~1 () Bool)
(declare-fun $Y~0.out$~1 () Bool)
(assert (and (<= 0 $sig$~1) (<= $sig$~1 2)))

; K = 1
(declare-fun $sig$0 () Int)
(declare-fun $ignition$0 () Bool)
(declare-fun $reset_flag$0 () Bool)
(declare-fun $p1$0 () Bool)
(declare-fun $start_bt_val~0.start_bt$0 () Bool)
(declare-fun $start_bt_val~0.launch_bt$0 () Bool)
(declare-fun $start_bt_val~0.reset_flag$0 () Bool)
(declare-fun $start_bt_val~0.start_bt_out$0 () Bool)
(declare-fun $launch_bt_val~0.start_bt$0 () Bool)
(declare-fun $launch_bt_val~0.launch_bt$0 () Bool)
(declare-fun $launch_bt_val~0.reset_flag$0 () Bool)
(declare-fun $launch_bt_val~0.launch_bt_out$0 () Bool)
(declare-fun $Y~0.in$0 () Bool)
(declare-fun $Y~0.out$0 () Bool)
(assert (and (<= 0 $sig$0) (<= $sig$0 2)))

(assert (T true
  $sig$~1 $ignition$~1 $reset_flag$~1 $p1$~1 $start_bt_val~0.start_bt$~1 $start_bt_val~0.launch_bt$~1 $start_bt_val~0.reset_flag$~1 $start_bt_val~0.start_bt_out$~1
  $launch_bt_val~0.start_bt$~1 $launch_bt_val~0.launch_bt$~1 $launch_bt_val~0.reset_flag$~1 $launch_bt_val~0.launch_bt_out$~1
  $Y~0.in$~1 $Y~0.out$~1
  $sig$0 $ignition$0 $reset_flag$0 $p1$0
  $start_bt_val~0.start_bt$0 $start_bt_val~0.launch_bt$0 $start_bt_val~0.reset_flag$0 $start_bt_val~0.start_bt_out$0
  $launch_bt_val~0.start_bt$0 $launch_bt_val~0.launch_bt$0 $launch_bt_val~0.reset_flag$0 $launch_bt_val~0.launch_bt_out$0
  $Y~0.in$0 $Y~0.out$0))

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
(declare-fun $reset_flag$1 () Bool)
(declare-fun $p1$1 () Bool)
(declare-fun $start_bt_val~0.start_bt$1 () Bool)
(declare-fun $start_bt_val~0.launch_bt$1 () Bool)
(declare-fun $start_bt_val~0.reset_flag$1 () Bool)
(declare-fun $start_bt_val~0.start_bt_out$1 () Bool)
(declare-fun $launch_bt_val~0.start_bt$1 () Bool)
(declare-fun $launch_bt_val~0.launch_bt$1 () Bool)
(declare-fun $launch_bt_val~0.reset_flag$1 () Bool)
(declare-fun $launch_bt_val~0.launch_bt_out$1 () Bool)
(declare-fun $Y~0.in$1 () Bool)
(declare-fun $Y~0.out$1 () Bool)
(assert (and (<= 0 $sig$1) (<= $sig$1 2)))

(assert (T false
  $sig$0 $ignition$0 $reset_flag$0 $p1$0
  $start_bt_val~0.start_bt$0 $start_bt_val~0.launch_bt$0 $start_bt_val~0.reset_flag$0 $start_bt_val~0.start_bt_out$0
  $launch_bt_val~0.start_bt$0 $launch_bt_val~0.launch_bt$0 $launch_bt_val~0.reset_flag$0 $launch_bt_val~0.launch_bt_out$0
  $Y~0.in$0 $Y~0.out$0
  $sig$1 $ignition$1 $reset_flag$1 $p1$1
  $start_bt_val~0.start_bt$1 $start_bt_val~0.launch_bt$1 $start_bt_val~0.reset_flag$1 $start_bt_val~0.start_bt_out$1
  $launch_bt_val~0.start_bt$1 $launch_bt_val~0.launch_bt$1 $launch_bt_val~0.reset_flag$1 $launch_bt_val~0.launch_bt_out$1
  $Y~0.in$1 $Y~0.out$1))

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
(declare-fun $reset_flag$2 () Bool)
(declare-fun $p1$2 () Bool)
(declare-fun $start_bt_val~0.start_bt$2 () Bool)
(declare-fun $start_bt_val~0.launch_bt$2 () Bool)
(declare-fun $start_bt_val~0.reset_flag$2 () Bool)
(declare-fun $start_bt_val~0.start_bt_out$2 () Bool)
(declare-fun $launch_bt_val~0.start_bt$2 () Bool)
(declare-fun $launch_bt_val~0.launch_bt$2 () Bool)
(declare-fun $launch_bt_val~0.reset_flag$2 () Bool)
(declare-fun $launch_bt_val~0.launch_bt_out$2 () Bool)
(declare-fun $Y~0.in$2 () Bool)
(declare-fun $Y~0.out$2 () Bool)
(assert (and (<= 0 $sig$2) (<= $sig$2 2)))

(assert (T false
  $sig$1 $ignition$1 $reset_flag$1 $p1$1
  $start_bt_val~0.start_bt$1 $start_bt_val~0.launch_bt$1 $start_bt_val~0.reset_flag$1 $start_bt_val~0.start_bt_out$1
  $launch_bt_val~0.start_bt$1 $launch_bt_val~0.launch_bt$1 $launch_bt_val~0.reset_flag$1 $launch_bt_val~0.launch_bt_out$1
  $Y~0.in$1 $Y~0.out$1
  $sig$2 $ignition$2 $reset_flag$2 $p1$2
  $start_bt_val~0.start_bt$2 $start_bt_val~0.launch_bt$2 $start_bt_val~0.reset_flag$2 $start_bt_val~0.start_bt_out$2
  $launch_bt_val~0.start_bt$2 $launch_bt_val~0.launch_bt$2 $launch_bt_val~0.reset_flag$2 $launch_bt_val~0.launch_bt_out$2
  $Y~0.in$2 $Y~0.out$2))
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
(declare-fun $reset_flag$3 () Bool)
(declare-fun $p1$3 () Bool)
(declare-fun $start_bt_val~0.start_bt$3 () Bool)
(declare-fun $start_bt_val~0.launch_bt$3 () Bool)
(declare-fun $start_bt_val~0.reset_flag$3 () Bool)
(declare-fun $start_bt_val~0.start_bt_out$3 () Bool)
(declare-fun $launch_bt_val~0.start_bt$3 () Bool)
(declare-fun $launch_bt_val~0.launch_bt$3 () Bool)
(declare-fun $launch_bt_val~0.reset_flag$3 () Bool)
(declare-fun $launch_bt_val~0.launch_bt_out$3 () Bool)
(declare-fun $Y~0.in$3 () Bool)
(declare-fun $Y~0.out$3 () Bool)
(assert (and (<= 0 $sig$3) (<= $sig$3 2)))

(assert (T false
  $sig$2 $ignition$2 $reset_flag$2 $p1$2
  $start_bt_val~0.start_bt$2 $start_bt_val~0.launch_bt$2 $start_bt_val~0.reset_flag$2 $start_bt_val~0.start_bt_out$2
  $launch_bt_val~0.start_bt$2 $launch_bt_val~0.launch_bt$2 $launch_bt_val~0.reset_flag$2 $launch_bt_val~0.launch_bt_out$2
  $Y~0.in$2 $Y~0.out$2
  $sig$3 $ignition$3 $reset_flag$3 $p1$3
  $start_bt_val~0.start_bt$3 $start_bt_val~0.launch_bt$3 $start_bt_val~0.reset_flag$3 $start_bt_val~0.start_bt_out$3
  $launch_bt_val~0.start_bt$3 $launch_bt_val~0.launch_bt$3 $launch_bt_val~0.reset_flag$3 $launch_bt_val~0.launch_bt_out$3
  $Y~0.in$3 $Y~0.out$3))
(declare-fun act4 () Bool)
(assert (=> act4 (not $p1$3)))
(check-sat act4)
(echo "@DONE")
; Z3: unsat
; Z3: @DONE
(assert $p1$3)
