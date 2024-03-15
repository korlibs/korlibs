(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'korge-root-korlibs-concurrent'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'korge-root-korlibs-concurrent'.");
    }
    root['korge-root-korlibs-concurrent'] = factory(typeof this['korge-root-korlibs-concurrent'] === 'undefined' ? {} : this['korge-root-korlibs-concurrent'], this['kotlin-kotlin-stdlib']);
  }
}(this, function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var protoOf = kotlin_kotlin.$_$.x9;
  var Unit_getInstance = kotlin_kotlin.$_$.r2;
  var VOID = kotlin_kotlin.$_$.d;
  var initMetadataForInterface = kotlin_kotlin.$_$.g9;
  var toString = kotlin_kotlin.$_$.ba;
  var IllegalStateException_init_$Create$ = kotlin_kotlin.$_$.f1;
  var initMetadataForClass = kotlin_kotlin.$_$.d9;
  var println = kotlin_kotlin.$_$.f8;
  var lazy = kotlin_kotlin.$_$.uc;
  var Monotonic_getInstance = kotlin_kotlin.$_$.p2;
  var ValueTimeMark__elapsedNow_impl_eonqvs = kotlin_kotlin.$_$.y1;
  var Duration__compareTo_impl_pchp0f = kotlin_kotlin.$_$.r1;
  var Long = kotlin_kotlin.$_$.jc;
  var initMetadataForCompanion = kotlin_kotlin.$_$.e9;
  var NotImplementedError = kotlin_kotlin.$_$.lc;
  var KProperty1 = kotlin_kotlin.$_$.qa;
  var getPropertyCallableRef = kotlin_kotlin.$_$.a9;
  //endregion
  //region block: pre-declaration
  function notify$default(unit, $super) {
    unit = unit === VOID ? Unit_getInstance() : unit;
    var tmp;
    if ($super === VOID) {
      this.notify_q5q66h_k$(unit);
      tmp = Unit_getInstance();
    } else {
      var tmp_0 = $super.notify_q5q66h_k$;
      tmp = tmp_0.call(this, Unit_getInstance());
    }
    return tmp;
  }
  initMetadataForInterface(BaseLock, 'BaseLock');
  initMetadataForClass(Lock, 'Lock', Lock, VOID, [BaseLock]);
  initMetadataForClass(NonRecursiveLock, 'NonRecursiveLock', NonRecursiveLock, VOID, [BaseLock]);
  initMetadataForCompanion(Companion);
  initMetadataForClass(NativeThread, 'NativeThread');
  //endregion
  function BaseLock() {
  }
  function nativeThread(start, isDaemon, name, priority, block) {
    start = start === VOID ? true : start;
    isDaemon = isDaemon === VOID ? false : isDaemon;
    name = name === VOID ? null : name;
    priority = priority === VOID ? -1 : priority;
    var thread = new NativeThread(block);
    if (isDaemon) {
      thread.set_isDaemon_c79owm_k$(true);
    }
    if (priority > 0) {
      thread.set_priority_9imq2v_k$(priority);
    }
    if (!(name == null)) {
      thread.set_name_wkmnld_k$(name);
    }
    if (start) {
      thread.start_fyv442_k$();
    }
    return thread;
  }
  function Lock() {
    this.locked_1 = false;
  }
  protoOf(Lock).set_locked_3md9le_k$ = function (_set____db54di) {
    this.locked_1 = _set____db54di;
  };
  protoOf(Lock).get_locked_g9dxjn_k$ = function () {
    return this.locked_1;
  };
  protoOf(Lock).invoke_qktov2_k$ = function (callback) {
    this.locked_1 = true;
    try {
      return callback();
    }finally {
      this.locked_1 = false;
    }
  };
  protoOf(Lock).notify_q5q66h_k$ = function (unit) {
    if (!this.locked_1) {
      // Inline function 'kotlin.error' call
      var message = 'Must lock before notifying';
      throw IllegalStateException_init_$Create$(toString(message));
    }
  };
  protoOf(Lock).wait_yik34g_k$ = function (time) {
    if (!this.locked_1) {
      // Inline function 'kotlin.error' call
      var message = 'Must lock before waiting';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    return false;
  };
  function NonRecursiveLock() {
  }
  protoOf(NonRecursiveLock).invoke_qktov2_k$ = function (callback) {
    return callback();
  };
  protoOf(NonRecursiveLock).notify_q5q66h_k$ = function (unit) {
  };
  protoOf(NonRecursiveLock).wait_yik34g_k$ = function (time) {
    return false;
  };
  function NativeThread$Companion$warnSleep$delegate$lambda() {
    println('!!! Sync sleeping on JS');
    return Unit_getInstance();
  }
  function Companion() {
    Companion_instance = this;
    var tmp = this;
    tmp.warnSleep$delegate_1 = lazy(NativeThread$Companion$warnSleep$delegate$lambda);
  }
  protoOf(Companion).get_isSupported_x20f97_k$ = function () {
    return false;
  };
  protoOf(Companion).get_warnSleep_vsajqw_k$ = function () {
    // Inline function 'kotlin.getValue' call
    var this_0 = this.warnSleep$delegate_1;
    warnSleep$factory();
    return this_0.get_value_j01efc_k$();
  };
  protoOf(Companion).gc_a8h0g7_k$ = function (full) {
  };
  protoOf(Companion).sleep_sn34jm_k$ = function (time) {
    this.get_warnSleep_vsajqw_k$();
    var start = Monotonic_getInstance().markNow_ns2ype_k$();
    // Inline function 'korlibs.concurrent.thread.Companion.spinWhile' call
    $l$loop: while (true) {
      // Inline function 'korlibs.concurrent.thread.Companion.sleep.<anonymous>' call
      if (!(Duration__compareTo_impl_pchp0f(ValueTimeMark__elapsedNow_impl_eonqvs(start), time) < 0)) {
        break $l$loop;
      }
    }
  };
  protoOf(Companion).spinWhile_u3tpey_k$ = function (cond) {
    while (cond()) {
    }
  };
  protoOf(Companion).get_currentThreadId_uce3qt_k$ = function () {
    return new Long(1, 0);
  };
  protoOf(Companion).get_currentThreadName_wwpdwb_k$ = function () {
    return 'Thread-' + this.get_currentThreadId_uce3qt_k$().toString();
  };
  var Companion_instance;
  function Companion_getInstance() {
    if (Companion_instance == null)
      new Companion();
    return Companion_instance;
  }
  function NativeThread(code) {
    Companion_getInstance();
    this.code_1 = code;
    this.userData_1 = null;
    this.isDaemon_1 = false;
    this.threadSuggestRunning_1 = true;
    this.name_1 = 'Thread-JS';
  }
  protoOf(NativeThread).get_code_wok7xy_k$ = function () {
    return this.code_1;
  };
  protoOf(NativeThread).set_userData_i5zsl9_k$ = function (_set____db54di) {
    this.userData_1 = _set____db54di;
  };
  protoOf(NativeThread).get_userData_yt8c7y_k$ = function () {
    return this.userData_1;
  };
  protoOf(NativeThread).set_isDaemon_c79owm_k$ = function (_set____db54di) {
    this.isDaemon_1 = _set____db54di;
  };
  protoOf(NativeThread).get_isDaemon_s827mf_k$ = function () {
    return this.isDaemon_1;
  };
  protoOf(NativeThread).set_threadSuggestRunning_i445xv_k$ = function (_set____db54di) {
    this.threadSuggestRunning_1 = _set____db54di;
  };
  protoOf(NativeThread).get_threadSuggestRunning_fvva7m_k$ = function () {
    return this.threadSuggestRunning_1;
  };
  protoOf(NativeThread).start_fyv442_k$ = function () {
    this.threadSuggestRunning_1 = true;
    // Inline function 'kotlin.TODO' call
    throw new NotImplementedError();
  };
  protoOf(NativeThread).interrupt_6jhqqb_k$ = function () {
    this.threadSuggestRunning_1 = false;
    // Inline function 'kotlin.TODO' call
    throw new NotImplementedError();
  };
  protoOf(NativeThread).set_priority_9imq2v_k$ = function (value) {
  };
  protoOf(NativeThread).get_priority_jyafsd_k$ = function () {
    return 0;
  };
  protoOf(NativeThread).set_name_wkmnld_k$ = function (_set____db54di) {
    this.name_1 = _set____db54di;
  };
  protoOf(NativeThread).get_name_woqyms_k$ = function () {
    return this.name_1;
  };
  function warnSleep$factory() {
    return getPropertyCallableRef('warnSleep', 1, KProperty1, function (receiver) {
      receiver.get_warnSleep_vsajqw_k$();
      return Unit_getInstance();
    }, null);
  }
  //region block: post-declaration
  protoOf(Lock).notify$default_c7e85j_k$ = notify$default;
  protoOf(NonRecursiveLock).notify$default_c7e85j_k$ = notify$default;
  //endregion
  //region block: exports
  _.$_$ = _.$_$ || {};
  _.$_$.a = Companion_getInstance;
  _.$_$.b = Lock;
  _.$_$.c = NonRecursiveLock;
  _.$_$.d = NativeThread;
  _.$_$.e = nativeThread;
  //endregion
  return _;
}));
