(set-option :produce-interpolants true)
(set-option :produce-unsat-cores true)
(set-option :simplify-interpolants true)
(set-logic QF_UFLIRA)
(set-option :verbosity 2)
(declare-fun $sig () Int)
(declare-fun $ignition () Bool)
(declare-fun $start_bt () Bool)
(declare-fun $launch_bt () Bool)
(declare-fun $reset_flag () Bool)
(declare-fun $p1 () Bool)
(declare-fun %init () Bool)
(declare-fun %assertions () Bool)
(declare-fun $sig- () Int)
(declare-fun $ignition- () Bool)
(declare-fun $start_bt- () Bool)
(declare-fun $launch_bt- () Bool)
(declare-fun $reset_flag- () Bool)
(declare-fun $p1- () Bool)
(declare-fun %init- () Bool)
(declare-fun %assertions- () Bool)
(declare-fun |$sig-'| () Int)
(declare-fun |$ignition-'| () Bool)
(declare-fun |$start_bt-'| () Bool)
(declare-fun |$launch_bt-'| () Bool)
(declare-fun |$reset_flag-'| () Bool)
(declare-fun |$p1-'| () Bool)
(declare-fun |%init-'| () Bool)
(declare-fun |%assertions-'| () Bool)
(declare-fun |$sig'| () Int)
(declare-fun |$ignition'| () Bool)
(declare-fun |$start_bt'| () Bool)
(declare-fun |$launch_bt'| () Bool)
(declare-fun |$reset_flag'| () Bool)
(declare-fun |$p1'| () Bool)
(declare-fun |%init'| () Bool)
(declare-fun |%assertions'| () Bool)
(define-fun T (($sig Int) ($ignition Bool) ($start_bt Bool) ($launch_bt Bool) ($reset_flag Bool) ($p1 Bool) (%init Bool) (%assertions Bool) (|$sig'| Int) (|$ignition'| Bool) (|$start_bt'| Bool) (|$launch_bt'| Bool) (|$reset_flag'| Bool) (|$p1'| Bool) (|%init'| Bool) (|%assertions'| Bool)) Bool (and (= |$start_bt'| (and (not %init) (not $reset_flag) (or (and (and (not $start_bt) (not $launch_bt)) (= |$sig'| 0)) $start_bt))) (= |$launch_bt'| (and (not %init) (not $reset_flag) (or (and (and $start_bt (not $launch_bt)) (= |$sig'| 1)) $launch_bt))) (= |$ignition'| (and (not %init) $launch_bt (and (not $ignition) (not $reset_flag)))) (= |$reset_flag'| (and (not %init) $ignition (not $reset_flag))) (= |$p1'| (=> |$ignition'| |$launch_bt'|)) (= |%assertions'| (or %init %assertions)) (<= 0 $sig) (<= $sig 2) (<= 0 |$sig'|) (<= |$sig'| 2) (not |%init'|)))
(assert (! (T $sig- $ignition- $start_bt- $launch_bt- $reset_flag- $p1- %init- %assertions- |$sig-'| |$ignition-'| |$start_bt-'| |$launch_bt-'| |$reset_flag-'| |$p1-'| |%init-'| |%assertions-'|) :named abstract0))
(echo "New predicate: %init")
(assert (! (= %init %init-) :named abstract1))
(assert (! (= |%init-'| |%init'|) :named abstract2))
(echo "New predicate: $p1")
(assert (! (= $p1 $p1-) :named abstract3))
(assert (! (= |$p1-'| |$p1'|) :named abstract4))
(echo "New predicate: %assertions")
(assert (! (= %assertions %assertions-) :named abstract5))
(assert (! (= |%assertions-'| |%assertions'|) :named abstract6))
(echo "Checking property: p1")
(push 1)
(assert (and %init (not (or $p1 (not %assertions) %init))))
(check-sat)
(pop 1)
(echo "Number of frames: 3")
(push 1)
(assert (not (or $p1 (not %assertions) %init)))
(check-sat)
(get-model)
(pop 1)
