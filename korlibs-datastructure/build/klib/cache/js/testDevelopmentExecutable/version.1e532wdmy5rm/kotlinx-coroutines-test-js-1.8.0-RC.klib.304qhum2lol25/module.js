(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'kotlinx-coroutines-test'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'kotlinx-coroutines-test'.");
    }
    root['kotlinx-coroutines-test'] = factory(typeof this['kotlinx-coroutines-test'] === 'undefined' ? {} : this['kotlinx-coroutines-test'], this['kotlin-kotlin-stdlib']);
  }
}(this, function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var Duration = kotlin_kotlin.$_$.wb;
  var Companion_getInstance = kotlin_kotlin.$_$.q2;
  var Companion_getInstance_0 = kotlin_kotlin.$_$.o2;
  var DurationUnit_SECONDS_getInstance = kotlin_kotlin.$_$.j;
  var toDuration = kotlin_kotlin.$_$.zb;
  var _Result___init__impl__xyqfz8 = kotlin_kotlin.$_$.e2;
  var createFailure = kotlin_kotlin.$_$.sc;
  //endregion
  //region block: pre-declaration
  //endregion
  function get_DEFAULT_TIMEOUT() {
    _init_properties_TestBuilders_kt__o1twne();
    return DEFAULT_TIMEOUT;
  }
  var DEFAULT_TIMEOUT;
  function systemProperty(name, parse, default_0) {
    _init_properties_TestBuilders_kt__o1twne();
    var tmp0_elvis_lhs = systemPropertyImpl(name);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return default_0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var value = tmp;
    return parse(value);
  }
  function Duration$Companion$parse$ref($boundThis) {
    var l = function (p0) {
      return new Duration($boundThis.parse_8aqxct_k$(p0));
    };
    l.callableName = 'parse';
    return l;
  }
  var properties_initialized_TestBuilders_kt_4e1btg;
  function _init_properties_TestBuilders_kt__o1twne() {
    if (!properties_initialized_TestBuilders_kt_4e1btg) {
      properties_initialized_TestBuilders_kt_4e1btg = true;
      // Inline function 'kotlin.runCatching' call
      var tmp;
      try {
        // Inline function 'kotlin.Companion.success' call
        Companion_getInstance();
        // Inline function 'kotlinx.coroutines.test.DEFAULT_TIMEOUT.<anonymous>' call
        var tmp_0 = Duration$Companion$parse$ref(Companion_getInstance_0());
        // Inline function 'kotlin.time.Companion.seconds' call
        Companion_getInstance_0();
        var tmp$ret$0 = toDuration(60, DurationUnit_SECONDS_getInstance());
        var tmp$ret$1 = systemProperty('kotlinx.coroutines.test.default_timeout', tmp_0, new Duration(tmp$ret$0)).rawValue_1;
        var value = new Duration(tmp$ret$1);
        tmp = _Result___init__impl__xyqfz8(value);
      } catch ($p) {
        var tmp_1;
        if ($p instanceof Error) {
          var e = $p;
          // Inline function 'kotlin.Companion.failure' call
          Companion_getInstance();
          tmp_1 = _Result___init__impl__xyqfz8(createFailure(e));
        } else {
          throw $p;
        }
        tmp = tmp_1;
      }
      DEFAULT_TIMEOUT = tmp;
    }
  }
  function set_catchNonTestRelatedExceptions(_set____db54di) {
    catchNonTestRelatedExceptions = _set____db54di;
  }
  function get_catchNonTestRelatedExceptions() {
    return catchNonTestRelatedExceptions;
  }
  var catchNonTestRelatedExceptions;
  function systemPropertyImpl(name) {
    return null;
  }
  //region block: init
  catchNonTestRelatedExceptions = true;
  //endregion
  return _;
}));
