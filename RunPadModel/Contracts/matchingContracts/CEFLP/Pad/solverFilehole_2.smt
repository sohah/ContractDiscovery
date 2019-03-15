(set-option :produce-models true)

(declare-fun constHole_1 () Bool)
(declare-fun constHole_2 () Bool)
(declare-fun constHole_3 () Int)
(declare-fun constHole_4 () Bool)
(declare-fun constHole_5 () Bool)
(declare-fun constHole_6 () Bool)
(declare-fun constHole_7 () Int)
(declare-fun constHole_8 () Bool)
(declare-fun constHole_9 () Bool)
(declare-fun constHole_10 () Bool)
(declare-fun constHole_11 () Bool)

(define-fun T (( %init Bool)( $sig$0 Int)( $ignition$0 Bool)( $start_bt$0 Bool)( $launch_bt$0 Bool)( $reset_flag$0 Bool)( $p1$0 Bool)( $H~0.in$0 Bool)( $H~0.out$0 Bool)( $Y~0.out$0 Bool)( $sig$1 Int)( $ignition$1 Bool)( $start_bt$1 Bool)( $launch_bt$1 Bool)( $reset_flag$1 Bool)( $p1$1 Bool)( $H~0.in$1 Bool)( $H~0.out$1 Bool)( $Y~0.out$1 Bool)) Bool
(and(= $start_bt$1(ite %init  constHole_1 (ite $reset_flag$0  constHole_2 (ite(and(and(not $start_bt$0)(not $launch_bt$0))(= $sig$1  constHole_3 ))  constHole_4  $start_bt$0))))(= $launch_bt$1(ite %init  constHole_5 (ite $reset_flag$0  constHole_6 (ite(and(and $start_bt$0(not $launch_bt$0))(= $sig$1  constHole_7 ))  constHole_8  $launch_bt$0))))(= $ignition$1(ite %init  constHole_9 (and $launch_bt$0(and(not $ignition$0)(not $reset_flag$0)))))(= $reset_flag$1(ite %init  constHole_10 (and $ignition$0(not $reset_flag$0))))(= $p1$1 $H~0.out$1)(= $H~0.in$1(=> $reset_flag$1 $Y~0.out$1))(= $H~0.out$1(ite %init $H~0.in$1(and $H~0.in$1 $H~0.out$0)))(= $Y~0.out$1(ite %init  constHole_11  $ignition$0))))
(declare-fun %init () Bool)
(declare-fun $sig$~1 () Int)
(declare-fun $ignition$~1 () Bool)
(declare-fun $start_bt$~1 () Bool)
(declare-fun $launch_bt$~1 () Bool)
(declare-fun $reset_flag$~1 () Bool)
(declare-fun $p1$~1 () Bool)
(declare-fun $H~0.in$~1 () Bool)
(declare-fun $H~0.out$~1 () Bool)
(declare-fun $Y~0.out$~1 () Bool)
(assert (and (<= 0 $sig$~1) (<= $sig$~1 2)))
(declare-fun $sig$0 () Int)
(declare-fun $ignition$0 () Bool)
(declare-fun $start_bt$0 () Bool)
(declare-fun $launch_bt$0 () Bool)
(declare-fun $reset_flag$0 () Bool)
(declare-fun $p1$0 () Bool)
(declare-fun $H~0.in$0 () Bool)
(declare-fun $H~0.out$0 () Bool)
(declare-fun $Y~0.out$0 () Bool)
(assert (and (<= 0 $sig$0) (<= $sig$0 2)))
(assert (T %init $sig$~1 $ignition$~1 $start_bt$~1 $launch_bt$~1 $reset_flag$~1 $p1$~1 $H~0.in$~1 $H~0.out$~1 $Y~0.out$~1 $sig$0 $ignition$0 $start_bt$0 $launch_bt$0 $reset_flag$0 $p1$0 $H~0.in$0 $H~0.out$0 $Y~0.out$0))
(assert true)
(declare-fun act1 () Bool)
(assert (=> act1 (not (=> true $p1$0))))
(declare-fun $sig$1 () Int)
(declare-fun $ignition$1 () Bool)
(declare-fun $start_bt$1 () Bool)
(declare-fun $launch_bt$1 () Bool)
(declare-fun $reset_flag$1 () Bool)
(declare-fun $p1$1 () Bool)
(declare-fun $H~0.in$1 () Bool)
(declare-fun $H~0.out$1 () Bool)
(declare-fun $Y~0.out$1 () Bool)
(assert (and (<= 0 $sig$1) (<= $sig$1 2)))
(assert (T false $sig$0 $ignition$0 $start_bt$0 $launch_bt$0 $reset_flag$0 $p1$0 $H~0.in$0 $H~0.out$0 $Y~0.out$0 $sig$1 $ignition$1 $start_bt$1 $launch_bt$1 $reset_flag$1 $p1$1 $H~0.in$1 $H~0.out$1 $Y~0.out$1))
(assert true)
(declare-fun act2 () Bool)
(assert (=> act2 (not (=> $p1$0 $p1$1))))
(define-fun R ((signal Int) (ignition Int) (launch_btn Int) (reset_btn Int) (start_btn Int) (r347.ignition_r.1.7.4 Int) (r347.launch_btn.1.17.4 Int) (r347.reset_btn.1.9.4 Int) (r347.start_btn.1.15.4 Int) (w14$3 Int) (w12$2 Int) (w13$2 Int) (w14$2 Int) (r347.start_btn.1.3.4 Int) (r347.launch_btn.1.3.4 Int) (r347.launch_btn.1.5.4 Int) (r347.start_btn.1.5.4 Int) (r347.launch_btn.1.7.4 Int) (r347.start_btn.1.7.4 Int) (r347.launch_btn.1.9.4 Int) (r347.start_btn.1.9.4 Int) (r347.launch_btn.1.11.4 Int) (r347.reset_btn.1.4.4 Int) (r347.ignition_r.1.4.4 Int) (r347.reset_btn.1.5.4 Int) (r347.start_btn.1.11.4 Int) (r347.launch_btn.1.13.4 Int) (r347.ignition_r.1.5.4 Int) (r347.start_btn.1.13.4 Int) (r347.launch_btn.1.15.4 Int) (r347.reset_btn.1.7.4 Int) (symVar Int) (w12$1 Int) ) Bool
(and
 	(= symVar 1)
	(>= start_btn 0)
	(<= start_btn 1)
	(>= launch_btn 0)
	(<= launch_btn 1)
	(>= ignition 0)
	(<= ignition 1)
	(>= reset_btn 0)
	(<= reset_btn 1)
	(>= w14$3 (- 2147483648))
	(<= w14$3 2147483647)
	(>= signal (- 2147483648))
	(<= signal 2147483647)
	(>= w12$2 (- 2147483648))
	(<= w12$2 2147483647)
	(>= w13$2 (- 2147483648))
	(<= w13$2 2147483647)
	(>= w14$2 (- 2147483648))
	(<= w14$2 2147483647)
	(>= r347.start_btn.1.3.4 (- 2147483648))
	(<= r347.start_btn.1.3.4 2147483647)
	(>= r347.launch_btn.1.3.4 (- 2147483648))
	(<= r347.launch_btn.1.3.4 2147483647)
	(>= r347.launch_btn.1.5.4 (- 2147483648))
	(<= r347.launch_btn.1.5.4 2147483647)
	(>= r347.start_btn.1.5.4 (- 2147483648))
	(<= r347.start_btn.1.5.4 2147483647)
	(>= r347.launch_btn.1.7.4 (- 2147483648))
	(<= r347.launch_btn.1.7.4 2147483647)
	(>= r347.start_btn.1.7.4 (- 2147483648))
	(<= r347.start_btn.1.7.4 2147483647)
	(>= r347.launch_btn.1.9.4 (- 2147483648))
	(<= r347.launch_btn.1.9.4 2147483647)
	(>= r347.start_btn.1.9.4 (- 2147483648))
	(<= r347.start_btn.1.9.4 2147483647)
	(>= r347.launch_btn.1.11.4 (- 2147483648))
	(<= r347.launch_btn.1.11.4 2147483647)
	(>= r347.reset_btn.1.4.4 (- 2147483648))
	(<= r347.reset_btn.1.4.4 2147483647)
	(>= r347.ignition_r.1.4.4 (- 2147483648))
	(<= r347.ignition_r.1.4.4 2147483647)
	(>= r347.reset_btn.1.5.4 (- 2147483648))
	(<= r347.reset_btn.1.5.4 2147483647)
	(>= r347.start_btn.1.11.4 (- 2147483648))
	(<= r347.start_btn.1.11.4 2147483647)
	(>= r347.launch_btn.1.13.4 (- 2147483648))
	(<= r347.launch_btn.1.13.4 2147483647)
	(>= r347.ignition_r.1.5.4 (- 2147483648))
	(<= r347.ignition_r.1.5.4 2147483647)
	(>= r347.start_btn.1.13.4 (- 2147483648))
	(<= r347.start_btn.1.13.4 2147483647)
	(>= r347.launch_btn.1.15.4 (- 2147483648))
	(<= r347.launch_btn.1.15.4 2147483647)
	(>= r347.reset_btn.1.7.4 (- 2147483648))
	(<= r347.reset_btn.1.7.4 2147483647)
	(>= symVar 0)
	(<= symVar 1)
	(>= r347.ignition_r.1.7.4 (- 2147483648))
	(<= r347.ignition_r.1.7.4 2147483647)
	(>= r347.start_btn.1.15.4 (- 2147483648))
	(<= r347.start_btn.1.15.4 2147483647)
	(>= r347.launch_btn.1.17.4 (- 2147483648))
	(<= r347.launch_btn.1.17.4 2147483647)
	(>= r347.reset_btn.1.9.4 (- 2147483648))
	(<= r347.reset_btn.1.9.4 2147483647)
	(>= w12$1 (- 2147483648))
	(<= w12$1 2147483647)
	(let ((a!1 (or (not (= start_btn 0))
                 (and (= start_btn 0) (not (= launch_btn 0)))
                 (and (and (= start_btn 0) (= launch_btn 0))
                      (not (= ignition 0)))
                 (and (and (= start_btn 0) (= launch_btn 0))
                      (= ignition 0)
                      (not (= reset_btn 0)))))
        (a!2 (and (and (not (= start_btn 0)) (= launch_btn 0))
                  (not (= ignition 0))))
        (a!3 (and (and (not (= start_btn 0)) (= launch_btn 0))
                  (= ignition 0)
                  (not (= reset_btn 0))))
        (a!5 (and (and (not (= start_btn 0)) (not (= launch_btn 0)))
                  (= ignition 0)))
        (a!8 (or (= start_btn 0) (and (not (= start_btn 0)) (= launch_btn 0))))
        (a!9 (and (and (not (= start_btn 0)) (not (= launch_btn 0)))
                  (not (= ignition 0))))
        (a!14 (and (= 0 0) (not (not (= signal 0))) (= w12$2 1)))
        (a!16 (and (= 0 0) (not (not (= signal 1))) (= w13$2 1)))
        (a!18 (or (not (= w12$2 0)) (and (= w12$2 0) (not (= w13$2 0)))))
        (a!19 (and (= 0 0)
                   (not (not (= w12$2 0)))
                   (= r347.start_btn.1.3.4 start_btn)))
        (a!21 (and (= 0 0)
                   (not (not (= w13$2 0)))
                   (= r347.launch_btn.1.3.4 launch_btn)))
        (a!23 (or (and (= 0 0)
                       (= w14$3 2)
                       (= r347.launch_btn.1.5.4 r347.launch_btn.1.3.4))
                  (and (= 0 0)
                       (not (= w14$3 2))
                       (= r347.launch_btn.1.5.4 launch_btn))))
        (a!24 (and (and (= 0 0) (not (= w14$3 1)))
                   (= r347.start_btn.1.5.4 start_btn)))
        (a!26 (and (and (= 0 0) (not (= w14$3 1)))
                   (= r347.launch_btn.1.7.4 r347.launch_btn.1.5.4)))
        (a!28 (and (and (= 0 0) (not (= w14$2 0)))
                   (= r347.start_btn.1.7.4 r347.start_btn.1.5.4)))
        (a!29 (and (= 0 0) (not (not (= w14$2 0)))))
        (a!30 (and (and (= 0 0) (not (= w14$2 0)))
                   (= r347.launch_btn.1.9.4 r347.launch_btn.1.7.4)))
        (a!31 (and (= 0 0) (and (not (= w14$3 5)) (not (= w14$3 7)))))
        (a!32 (not (and (not (= w14$3 5)) (not (= w14$3 7)))))
        (a!36 (and (and (= 0 0) (not (= w14$3 4)))
                   (= r347.ignition_r.1.4.4 ignition)))
        (a!38 (and (and (= 0 0) (not (= w14$3 4)))
                   (= r347.reset_btn.1.5.4 r347.reset_btn.1.4.4)))
        (a!40 (and (and (= 0 0) (not (= w14$3 4)))
                   (= r347.start_btn.1.11.4 r347.start_btn.1.9.4)))
        (a!42 (and (and (= 0 0) (not (= w14$3 4)))
                   (= r347.launch_btn.1.13.4 r347.launch_btn.1.11.4)))
        (a!44 (and (and (= 0 0) (not (= w14$3 3)))
                   (= r347.ignition_r.1.5.4 r347.ignition_r.1.4.4)))
        (a!46 (and (and (= 0 0) (not (= w14$3 3)))
                   (= r347.start_btn.1.13.4 r347.start_btn.1.11.4)))
        (a!48 (and (and (= 0 0) (not (= w14$3 3)))
                   (= r347.launch_btn.1.15.4 r347.launch_btn.1.13.4)))
        (a!50 (and (and (= 0 0) (not (= w14$3 3)))
                   (= r347.reset_btn.1.7.4 r347.reset_btn.1.5.4)))
        (a!52 (and (and (= 0 0) (not (= symVar 0)))
                   (= r347.ignition_r.1.7.4 r347.ignition_r.1.5.4)))
        (a!53 (and (= 0 0) (not (not (= symVar 0)))))
        (a!54 (and (and (= 0 0) (not (= symVar 0)))
                   (= r347.start_btn.1.15.4 r347.start_btn.1.13.4)))
        (a!55 (and (and (= 0 0) (not (= symVar 0)))
                   (= r347.launch_btn.1.17.4 r347.launch_btn.1.15.4)))
        (a!56 (and (and (= 0 0) (not (= symVar 0)))
                   (= r347.reset_btn.1.9.4 r347.reset_btn.1.7.4)))
        (a!57 (and (and (= 0 0) (not (= symVar 0)))
                   (= w12$1 r347.ignition_r.1.5.4))))
  (let ((a!4 (or (= start_btn 0)
                 (and (not (= start_btn 0)) (not (= launch_btn 0)))
                 a!2
                 a!3))
        (a!10 (or a!8 a!5 (and a!9 (not (= reset_btn 0)))))
        (a!12 (not (or a!8 a!9 (and a!5 (= reset_btn 0)))))
        (a!15 (or (and (= 0 0) (not (= signal 0)) (= w12$2 0)) a!14))
        (a!17 (or (and (= 0 0) (not (= signal 1)) (= w13$2 0)) a!16))
        (a!20 (or (and (= 0 0) (not (= w12$2 0)) (= r347.start_btn.1.3.4 1))
                  a!19))
        (a!22 (or (and (= 0 0) (not (= w13$2 0)) (= r347.launch_btn.1.3.4 1))
                  a!21))
        (a!25 (or (and (and (= 0 0) (= w14$3 1))
                       (= r347.start_btn.1.5.4 r347.start_btn.1.3.4))
                  a!24))
        (a!27 (or (and (and (= 0 0) (= w14$3 1))
                       (= r347.launch_btn.1.7.4 launch_btn))
                  a!26))
        (a!33 (or (and a!31 (= r347.start_btn.1.9.4 r347.start_btn.1.7.4))
                  (and (and (= 0 0) a!32) (= r347.start_btn.1.9.4 0))))
        (a!34 (or (and a!31 (= r347.launch_btn.1.11.4 r347.launch_btn.1.9.4))
                  (and (and (= 0 0) a!32) (= r347.launch_btn.1.11.4 0))))
        (a!35 (or (and a!31 (= r347.reset_btn.1.4.4 reset_btn))
                  (and (and (= 0 0) a!32) (= r347.reset_btn.1.4.4 0))))
        (a!37 (or (and (and (= 0 0) (= w14$3 4)) (= r347.ignition_r.1.4.4 0))
                  a!36))
        (a!39 (or (and (and (= 0 0) (= w14$3 4)) (= r347.reset_btn.1.5.4 1))
                  a!38))
        (a!41 (or (and (and (= 0 0) (= w14$3 4))
                       (= r347.start_btn.1.11.4 start_btn))
                  a!40))
        (a!43 (or (and (and (= 0 0) (= w14$3 4))
                       (= r347.launch_btn.1.13.4 launch_btn))
                  a!42))
        (a!45 (or (and (and (= 0 0) (= w14$3 3)) (= r347.ignition_r.1.5.4 1))
                  a!44))
        (a!47 (or (and (and (= 0 0) (= w14$3 3))
                       (= r347.start_btn.1.13.4 start_btn))
                  a!46))
        (a!49 (or (and (and (= 0 0) (= w14$3 3))
                       (= r347.launch_btn.1.15.4 launch_btn))
                  a!48))
        (a!51 (or (and (and (= 0 0) (= w14$3 3))
                       (= r347.reset_btn.1.7.4 reset_btn))
                  a!50)))
  (let ((a!6 (and (and (and (= 0 0) a!1) a!4)
                  (and a!5 (= reset_btn 0))
                  (= w14$3 3)))
        (a!7 (and (and (and (= 0 0) a!1) a!4) (not (and a!5 (= reset_btn 0))))))
  (let ((a!11 (and (and a!7 a!10)
                   (or a!8 a!9 (and a!5 (= reset_btn 0)))
                   (= w14$3 7))))
  (let ((a!13 (or a!6
                  a!11
                  (and (and a!7 a!10) a!12 (= w14$3 5))
                  (and a!7 (not a!10) (= w14$3 4))
                  (and (and (= 0 0) a!1) (not a!4) (= w14$3 2))
                  (and (= 0 0) (not a!1) (= w14$3 1)))))
    (and a!13
         a!15
         a!17
         (or (and (= 0 0) a!18 (= w14$2 1))
             (and (= 0 0) (not a!18) (= w14$2 0)))
         a!20
         a!22
         a!23
         a!25
         a!27
         (or a!28 (and a!29 (= r347.start_btn.1.7.4 start_btn)))
         (or a!30 (and a!29 (= r347.launch_btn.1.9.4 launch_btn)))
         a!33
         a!34
         a!35
         a!37
         a!39
         a!41
         a!43
         a!45
         a!47
         a!49
         a!51
         (or a!52 (and a!53 (= r347.ignition_r.1.7.4 ignition)))
         (or a!54 (and a!53 (= r347.start_btn.1.15.4 start_btn)))
         (or a!55 (and a!53 (= r347.launch_btn.1.17.4 launch_btn)))
         (or a!56 (and a!53 (= r347.reset_btn.1.9.4 reset_btn)))
         (or a!57 (and a!53 (= w12$1 0)))))))))
))
(declare-fun signal$r0 () Int)
(declare-fun ignition$r0 () Int)
(declare-fun launch_btn$r0 () Int)
(declare-fun reset_btn$r0 () Int)
(declare-fun start_btn$r0 () Int)
(declare-fun r347.ignition_r.1.7.4$r0 () Int)
(declare-fun r347.launch_btn.1.17.4$r0 () Int)
(declare-fun r347.reset_btn.1.9.4$r0 () Int)
(declare-fun r347.start_btn.1.15.4$r0 () Int)
(declare-fun w14$3$r0 () Int)
(declare-fun w12$2$r0 () Int)
(declare-fun w13$2$r0 () Int)
(declare-fun w14$2$r0 () Int)
(declare-fun r347.start_btn.1.3.4$r0 () Int)
(declare-fun r347.launch_btn.1.3.4$r0 () Int)
(declare-fun r347.launch_btn.1.5.4$r0 () Int)
(declare-fun r347.start_btn.1.5.4$r0 () Int)
(declare-fun r347.launch_btn.1.7.4$r0 () Int)
(declare-fun r347.start_btn.1.7.4$r0 () Int)
(declare-fun r347.launch_btn.1.9.4$r0 () Int)
(declare-fun r347.start_btn.1.9.4$r0 () Int)
(declare-fun r347.launch_btn.1.11.4$r0 () Int)
(declare-fun r347.reset_btn.1.4.4$r0 () Int)
(declare-fun r347.ignition_r.1.4.4$r0 () Int)
(declare-fun r347.reset_btn.1.5.4$r0 () Int)
(declare-fun r347.start_btn.1.11.4$r0 () Int)
(declare-fun r347.launch_btn.1.13.4$r0 () Int)
(declare-fun r347.ignition_r.1.5.4$r0 () Int)
(declare-fun r347.start_btn.1.13.4$r0 () Int)
(declare-fun r347.launch_btn.1.15.4$r0 () Int)
(declare-fun r347.reset_btn.1.7.4$r0 () Int)
(declare-fun symVar$r0 () Int)
(declare-fun w12$1$r0 () Int)

(assert (R  signal$r0 ignition$r0 launch_btn$r0 reset_btn$r0 start_btn$r0 r347.ignition_r.1.7.4$r0 r347.launch_btn.1.17.4$r0 r347.reset_btn.1.9.4$r0 r347.start_btn.1.15.4$r0 w14$3$r0 w12$2$r0 w13$2$r0 w14$2$r0 r347.start_btn.1.3.4$r0 r347.launch_btn.1.3.4$r0 r347.launch_btn.1.5.4$r0 r347.start_btn.1.5.4$r0 r347.launch_btn.1.7.4$r0 r347.start_btn.1.7.4$r0 r347.launch_btn.1.9.4$r0 r347.start_btn.1.9.4$r0 r347.launch_btn.1.11.4$r0 r347.reset_btn.1.4.4$r0 r347.ignition_r.1.4.4$r0 r347.reset_btn.1.5.4$r0 r347.start_btn.1.11.4$r0 r347.launch_btn.1.13.4$r0 r347.ignition_r.1.5.4$r0 r347.start_btn.1.13.4$r0 r347.launch_btn.1.15.4$r0 r347.reset_btn.1.7.4$r0 1 w12$1$r0 ))

(declare-fun signal$r1 () Int)
(declare-fun r347.ignition_r.1.7.4$r1 () Int)
(declare-fun r347.launch_btn.1.17.4$r1 () Int)
(declare-fun r347.reset_btn.1.9.4$r1 () Int)
(declare-fun r347.start_btn.1.15.4$r1 () Int)
(declare-fun w14$3$r1 () Int)
(declare-fun w12$2$r1 () Int)
(declare-fun w13$2$r1 () Int)
(declare-fun w14$2$r1 () Int)
(declare-fun r347.start_btn.1.3.4$r1 () Int)
(declare-fun r347.launch_btn.1.3.4$r1 () Int)
(declare-fun r347.launch_btn.1.5.4$r1 () Int)
(declare-fun r347.start_btn.1.5.4$r1 () Int)
(declare-fun r347.launch_btn.1.7.4$r1 () Int)
(declare-fun r347.start_btn.1.7.4$r1 () Int)
(declare-fun r347.launch_btn.1.9.4$r1 () Int)
(declare-fun r347.start_btn.1.9.4$r1 () Int)
(declare-fun r347.launch_btn.1.11.4$r1 () Int)
(declare-fun r347.reset_btn.1.4.4$r1 () Int)
(declare-fun r347.ignition_r.1.4.4$r1 () Int)
(declare-fun r347.reset_btn.1.5.4$r1 () Int)
(declare-fun r347.start_btn.1.11.4$r1 () Int)
(declare-fun r347.launch_btn.1.13.4$r1 () Int)
(declare-fun r347.ignition_r.1.5.4$r1 () Int)
(declare-fun r347.start_btn.1.13.4$r1 () Int)
(declare-fun r347.launch_btn.1.15.4$r1 () Int)
(declare-fun r347.reset_btn.1.7.4$r1 () Int)
(declare-fun symVar$r1 () Int)
(declare-fun w12$1$r1 () Int)

(assert (R  signal$r1 r347.ignition_r.1.7.4$r0 r347.launch_btn.1.17.4$r0 r347.reset_btn.1.9.4$r0 r347.start_btn.1.15.4$r0 r347.ignition_r.1.7.4$r1 r347.launch_btn.1.17.4$r1 r347.reset_btn.1.9.4$r1 r347.start_btn.1.15.4$r1 w14$3$r1 w12$2$r1 w13$2$r1 w14$2$r1 r347.start_btn.1.3.4$r1 r347.launch_btn.1.3.4$r1 r347.launch_btn.1.5.4$r1 r347.start_btn.1.5.4$r1 r347.launch_btn.1.7.4$r1 r347.start_btn.1.7.4$r1 r347.launch_btn.1.9.4$r1 r347.start_btn.1.9.4$r1 r347.launch_btn.1.11.4$r1 r347.reset_btn.1.4.4$r1 r347.ignition_r.1.4.4$r1 r347.reset_btn.1.5.4$r1 r347.start_btn.1.11.4$r1 r347.launch_btn.1.13.4$r1 r347.ignition_r.1.5.4$r1 r347.start_btn.1.13.4$r1 r347.launch_btn.1.15.4$r1 r347.reset_btn.1.7.4$r1 1 w12$1$r1 ))
(assert (=>(and(= signal$r0 1)(= start_btn$r0 1)(= reset_btn$r0 0)(= ignition$r0 1)(= launch_btn$r0 1)(= signal$r1 2)(= r347.reset_btn.1.9.4$r0 1)(= r347.ignition_r.1.7.4$r0 0)(= r347.launch_btn.1.17.4$r0 1)(= r347.start_btn.1.15.4$r0 1))(and(= r347.reset_btn.1.9.4$r1 0)(= r347.ignition_r.1.7.4$r1 0)(= r347.launch_btn.1.17.4$r1 0)(= r347.start_btn.1.15.4$r1 0))))
(assert (=>(and(= signal$r0 0)(= start_btn$r0 1)(= reset_btn$r0 0)(= ignition$r0 0)(= launch_btn$r0 0)(= signal$r1 1)(= r347.reset_btn.1.9.4$r0 0)(= r347.ignition_r.1.7.4$r0 0)(= r347.launch_btn.1.17.4$r0 0)(= r347.start_btn.1.15.4$r0 1))(and(= r347.reset_btn.1.9.4$r1 0)(= r347.ignition_r.1.7.4$r1 0)(= r347.launch_btn.1.17.4$r1 1)(= r347.start_btn.1.15.4$r1 1))))
; ---------- joining contract begins here -------------
(declare-fun contract_match$() bool)

(assert(= contract_match$
	(let (	( input_match~1
	(and
			(= $sig$0 signal$r0)
			(= $ignition$~1 (= ignition$r0 1 ))
			(= $launch_bt$~1 (= launch_btn$r0 1 ))
			(= $reset_flag$~1 (= reset_btn$r0 1 ))
			(= $start_bt$~1 (= start_btn$r0 1 ))
))
	( output_match~1
	(and
			(= $ignition$0 (= r347.ignition_r.1.7.4$r0 1 ))
			(= $launch_bt$0 (= r347.launch_btn.1.17.4$r0 1 ))
			(= $reset_flag$0 (= r347.reset_btn.1.9.4$r0 1 ))
			(= $start_bt$0 (= r347.start_btn.1.15.4$r0 1 ))
))
( input_match$1
	(and
			(= $sig$1 signal$r1)
			(= $ignition$0 (= r347.ignition_r.1.7.4$r0 1 ))
			(= $launch_bt$0 (= r347.launch_btn.1.17.4$r0 1 ))
			(= $reset_flag$0 (= r347.reset_btn.1.9.4$r0 1 ))
			(= $start_bt$0 (= r347.start_btn.1.15.4$r0 1 ))
))

	(output_match$1
		( and
			(= $ignition$1(= r347.ignition_r.1.7.4$r1 1))
			(= $launch_bt$1(= r347.launch_btn.1.17.4$r1 1))
			(= $reset_flag$1(= r347.reset_btn.1.9.4$r1 1))
			(= $start_bt$1(= r347.start_btn.1.15.4$r1 1))
))
)

(=> (and input_match~1 output_match~1 input_match$1) output_match$1)
)))
; ---------- joining contract ends here -------------
(assert contract_match$)
