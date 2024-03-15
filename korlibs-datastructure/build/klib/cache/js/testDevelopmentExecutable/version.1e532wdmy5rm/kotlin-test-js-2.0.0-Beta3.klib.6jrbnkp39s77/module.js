(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'kotlin-kotlin-test'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'kotlin-kotlin-test'.");
    }
    root['kotlin-kotlin-test'] = factory(typeof this['kotlin-kotlin-test'] === 'undefined' ? {} : this['kotlin-kotlin-test'], this['kotlin-kotlin-stdlib']);
  }
}(this, function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var Unit_getInstance = kotlin_kotlin.$_$.r2;
  var equals = kotlin_kotlin.$_$.u8;
  var toString = kotlin_kotlin.$_$.zc;
  var protoOf = kotlin_kotlin.$_$.x9;
  var initMetadataForInterface = kotlin_kotlin.$_$.g9;
  var VOID = kotlin_kotlin.$_$.d;
  var Companion_getInstance = kotlin_kotlin.$_$.q2;
  var _Result___init__impl__xyqfz8 = kotlin_kotlin.$_$.e2;
  var createFailure = kotlin_kotlin.$_$.sc;
  var Result__exceptionOrNull_impl_p6xea9 = kotlin_kotlin.$_$.f2;
  var _Result___get_value__impl__bjfvqg = kotlin_kotlin.$_$.g2;
  var THROW_CCE = kotlin_kotlin.$_$.oc;
  var contentToString = kotlin_kotlin.$_$.z3;
  var contentEquals = kotlin_kotlin.$_$.r3;
  var contentToString_0 = kotlin_kotlin.$_$.a4;
  var contentEquals_0 = kotlin_kotlin.$_$.u3;
  var Annotation = kotlin_kotlin.$_$.ac;
  var initMetadataForClass = kotlin_kotlin.$_$.d9;
  var AssertionError_init_$Create$ = kotlin_kotlin.$_$.z;
  var defineProp = kotlin_kotlin.$_$.s8;
  var initMetadataForObject = kotlin_kotlin.$_$.i9;
  var toString_0 = kotlin_kotlin.$_$.ba;
  var to = kotlin_kotlin.$_$.ad;
  var mapOf = kotlin_kotlin.$_$.j5;
  //endregion
  //region block: pre-declaration
  function assertTrue(lazyMessage, actual) {
    if (!actual) {
      this.fail_o3vfxl_k$(lazyMessage());
    }
  }
  function assertTrue_0(message, actual) {
    this.assertTrue_rpw5fg_k$(Asserter$assertTrue$lambda(message), actual);
  }
  function assertEquals(message, expected, actual) {
    this.assertTrue_rpw5fg_k$(Asserter$assertEquals$lambda(message, expected, actual), equals(actual, expected));
  }
  function assertNotEquals(message, illegal, actual) {
    this.assertTrue_rpw5fg_k$(Asserter$assertNotEquals$lambda(message, actual), !equals(actual, illegal));
  }
  function assertSame(message, expected, actual) {
    this.assertTrue_rpw5fg_k$(Asserter$assertSame$lambda(message, expected, actual), actual === expected);
  }
  function assertNotSame(message, illegal, actual) {
    this.assertTrue_rpw5fg_k$(Asserter$assertNotSame$lambda(message, actual), !(actual === illegal));
  }
  function assertNull(message, actual) {
    this.assertTrue_rpw5fg_k$(Asserter$assertNull$lambda(message, actual), actual == null);
  }
  function assertNotNull(message, actual) {
    this.assertTrue_rpw5fg_k$(Asserter$assertNotNull$lambda(message), !(actual == null));
  }
  initMetadataForInterface(Asserter, 'Asserter');
  initMetadataForClass(Test, 'Test', VOID, VOID, [Annotation]);
  initMetadataForClass(Ignore, 'Ignore', VOID, VOID, [Annotation]);
  initMetadataForClass(DefaultJsAsserter$invokeHook$1);
  initMetadataForObject(DefaultJsAsserter, 'DefaultJsAsserter', VOID, VOID, [Asserter]);
  initMetadataForClass(BareAdapter, 'BareAdapter', BareAdapter);
  initMetadataForClass(JasmineLikeAdapter, 'JasmineLikeAdapter', JasmineLikeAdapter);
  initMetadataForClass(QUnitAdapter, 'QUnitAdapter', QUnitAdapter);
  //endregion
  function assertArrayContentEquals(message, expected, actual, size, get, contentToString, contentEquals) {
    if (contentEquals(expected, actual))
      return Unit_getInstance();
    var typeName = 'Array';
    if (checkReferenceAndNullEquality(typeName, message, expected, actual, contentToString))
      return Unit_getInstance();
    var expectedSize = size(expected);
    var actualSize = size(actual);
    if (!(expectedSize === actualSize)) {
      var sizesDifferMessage = typeName + ' sizes differ. Expected size is ' + expectedSize + ', actual size is ' + actualSize + '.';
      var toString = 'Expected <' + contentToString(expected) + '>, actual <' + contentToString(actual) + '>.';
      fail(messagePrefix(message) + sizesDifferMessage + '\n' + toString);
    }
    var inductionVariable = 0;
    if (inductionVariable < expectedSize)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var expectedElement = get(expected, index);
        var actualElement = get(actual, index);
        if (!equals(expectedElement, actualElement)) {
          var elementsDifferMessage_0 = elementsDifferMessage(typeName, index, expectedElement, actualElement);
          var toString_0 = 'Expected <' + contentToString(expected) + '>, actual <' + contentToString(actual) + '>.';
          fail(messagePrefix(message) + elementsDifferMessage_0 + '\n' + toString_0);
        }
      }
       while (inductionVariable < expectedSize);
  }
  function checkReferenceAndNullEquality(typeName, message, expected, actual, contentToString) {
    // Inline function 'kotlin.contracts.contract' call
    if (expected === actual) {
      return true;
    }
    if (expected == null) {
      fail(messagePrefix(message) + ('Expected <null> ' + typeName + ', actual <' + contentToString(actual) + '>.'));
    }
    if (actual == null) {
      fail(messagePrefix(message) + ('Expected non-null ' + typeName + ' <' + contentToString(expected) + '>, actual <null>.'));
    }
    return false;
  }
  function elementsDifferMessage(typeName, index, expectedElement, actualElement) {
    return typeName + ' elements differ at index ' + index + '. Expected element <' + toString(expectedElement) + '>, actual element <' + toString(actualElement) + '>.';
  }
  function set__asserter(_set____db54di) {
    _asserter = _set____db54di;
  }
  function get__asserter() {
    return _asserter;
  }
  var _asserter;
  function Asserter$assertTrue$lambda($message) {
    return function () {
      return $message;
    };
  }
  function Asserter$assertEquals$lambda($message, $expected, $actual) {
    return function () {
      return messagePrefix($message) + ('Expected <' + toString($expected) + '>, actual <' + toString($actual) + '>.');
    };
  }
  function Asserter$assertNotEquals$lambda($message, $actual) {
    return function () {
      return messagePrefix($message) + ('Illegal value: <' + toString($actual) + '>.');
    };
  }
  function Asserter$assertSame$lambda($message, $expected, $actual) {
    return function () {
      return messagePrefix($message) + ('Expected <' + toString($expected) + '>, actual <' + toString($actual) + '> is not same.');
    };
  }
  function Asserter$assertNotSame$lambda($message, $actual) {
    return function () {
      return messagePrefix($message) + ('Expected not same as <' + toString($actual) + '>.');
    };
  }
  function Asserter$assertNull$lambda($message, $actual) {
    return function () {
      return messagePrefix($message) + ('Expected value to be null, but was: <' + toString($actual) + '>.');
    };
  }
  function Asserter$assertNotNull$lambda($message) {
    return function () {
      return messagePrefix($message) + 'Expected value to be not null.';
    };
  }
  function Asserter() {
  }
  function assertTrue_1(actual, message) {
    message = message === VOID ? null : message;
    // Inline function 'kotlin.contracts.contract' call
    var tmp = get_asserter();
    return tmp.assertTrue_dqb114_k$(message == null ? 'Expected value to be true.' : message, actual);
  }
  function get_asserter() {
    var tmp0_elvis_lhs = _asserter;
    return tmp0_elvis_lhs == null ? lookupAsserter() : tmp0_elvis_lhs;
  }
  function assertEquals_0(expected, actual, message) {
    message = message === VOID ? null : message;
    get_asserter().assertEquals_ldumo_k$(message, expected, actual);
  }
  function assertNotEquals_0(illegal, actual, message) {
    message = message === VOID ? null : message;
    get_asserter().assertNotEquals_xv90xj_k$(message, illegal, actual);
  }
  function assertTrue_2(message, block) {
    message = message === VOID ? null : message;
    // Inline function 'kotlin.contracts.contract' call
    assertTrue_1(block(), message);
  }
  function assertFails(block) {
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance();
      var value = block();
      tmp = _Result___init__impl__xyqfz8(value);
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    return checkResultIsFailure(null, tmp$ret$2);
  }
  function assertFalse(actual, message) {
    message = message === VOID ? null : message;
    // Inline function 'kotlin.contracts.contract' call
    var tmp = get_asserter();
    return tmp.assertTrue_dqb114_k$(message == null ? 'Expected value to be false.' : message, !actual);
  }
  function assertContentEquals(expected, actual, message) {
    message = message === VOID ? null : message;
    var tmp = assertContentEquals$lambda;
    var tmp_0 = Array$get$ref();
    var tmp_1 = contentToString$ref();
    assertArrayContentEquals(message, expected, actual, tmp, tmp_0, tmp_1, contentEquals$ref());
  }
  function assertContentEquals_0(expected, actual, message) {
    message = message === VOID ? null : message;
    var tmp = assertContentEquals$lambda_0;
    var tmp_0 = IntArray$get$ref();
    var tmp_1 = contentToString$ref_0();
    assertArrayContentEquals(message, expected, actual, tmp, tmp_0, tmp_1, contentEquals$ref_0());
  }
  function assertFailsWith(exceptionClass, message, block) {
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance();
      var value = block();
      tmp = _Result___init__impl__xyqfz8(value);
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    return checkResultIsFailure_0(exceptionClass, message, tmp$ret$2);
  }
  function checkResultIsFailure(message, blockResult) {
    // Inline function 'kotlin.fold' call
    // Inline function 'kotlin.contracts.contract' call
    var exception = Result__exceptionOrNull_impl_p6xea9(blockResult);
    var tmp;
    if (exception == null) {
      var tmp$ret$0;
      // Inline function 'kotlin.test.checkResultIsFailure.<anonymous>' call
      var tmp_0 = _Result___get_value__impl__bjfvqg(blockResult);
      (tmp_0 == null ? true : !(tmp_0 == null)) || THROW_CCE();
      get_asserter().fail_o3vfxl_k$(messagePrefix(message) + 'Expected an exception to be thrown, but was completed successfully.');
      tmp = tmp$ret$0;
    } else {
      return exception;
    }
  }
  function fail(message) {
    message = message === VOID ? null : message;
    get_asserter().fail_o3vfxl_k$(message);
  }
  function assertContentEquals$lambda(it) {
    return it.length;
  }
  function Array$get$ref() {
    var l = function (p0, p1) {
      return p0[p1];
    };
    l.callableName = 'get';
    return l;
  }
  function contentToString$ref() {
    var l = function (p0) {
      return contentToString(p0);
    };
    l.callableName = 'contentToString';
    return l;
  }
  function contentEquals$ref() {
    var l = function (p0, p1) {
      return contentEquals(p0, p1);
    };
    l.callableName = 'contentEquals';
    return l;
  }
  function assertContentEquals$lambda_0(it) {
    return it.length;
  }
  function IntArray$get$ref() {
    var l = function (p0, p1) {
      return p0[p1];
    };
    l.callableName = 'get';
    return l;
  }
  function contentToString$ref_0() {
    var l = function (p0) {
      return contentToString_0(p0);
    };
    l.callableName = 'contentToString';
    return l;
  }
  function contentEquals$ref_0() {
    var l = function (p0, p1) {
      return contentEquals_0(p0, p1);
    };
    l.callableName = 'contentEquals';
    return l;
  }
  function messagePrefix(message) {
    return message == null ? '' : '' + message + '. ';
  }
  function Test() {
  }
  protoOf(Test).equals = function (other) {
    if (!(other instanceof Test))
      return false;
    other instanceof Test || THROW_CCE();
    return true;
  };
  protoOf(Test).hashCode = function () {
    return 0;
  };
  protoOf(Test).toString = function () {
    return '@kotlin.test.Test()';
  };
  function Ignore() {
  }
  protoOf(Ignore).equals = function (other) {
    if (!(other instanceof Ignore))
      return false;
    other instanceof Ignore || THROW_CCE();
    return true;
  };
  protoOf(Ignore).hashCode = function () {
    return 0;
  };
  protoOf(Ignore).toString = function () {
    return '@kotlin.test.Ignore()';
  };
  function set_assertHook(_set____db54di) {
    _init_properties_DefaultJsAsserter_kt__dbqvm3();
    assertHook = _set____db54di;
  }
  function get_assertHook() {
    _init_properties_DefaultJsAsserter_kt__dbqvm3();
    return assertHook;
  }
  var assertHook;
  function _set_e__db55a8($this, _set____db54di) {
    $this.e_1 = _set____db54di;
  }
  function _get_e__7mlojw($this) {
    return $this.e_1;
  }
  function _set_a__db556s($this, _set____db54di) {
    $this.a_1 = _set____db54di;
  }
  function _get_a__7mlogg($this) {
    return $this.a_1;
  }
  function failWithMessage($this, lazyMessage, cause) {
    var message = lazyMessage();
    invokeHook($this, false, DefaultJsAsserter$failWithMessage$lambda(message));
    // Inline function 'kotlin.test.AssertionErrorWithCause' call
    throw AssertionError_init_$Create$(message, cause);
  }
  function invokeHook($this, result, lazyMessage) {
    try {
      var tmp = get_assertHook();
      tmp(new DefaultJsAsserter$invokeHook$1(result, lazyMessage));
    }finally {
      $this.e_1 = undefined;
      $this.a_1 = undefined;
    }
  }
  function DefaultJsAsserter$assertTrue$lambda($message) {
    return function () {
      return $message;
    };
  }
  function DefaultJsAsserter$assertTrue$lambda_0($message) {
    return function () {
      return $message;
    };
  }
  function DefaultJsAsserter$fail$lambda($message) {
    return function () {
      return $message;
    };
  }
  function DefaultJsAsserter$failWithMessage$lambda($message) {
    return function () {
      return $message;
    };
  }
  function DefaultJsAsserter$invokeHook$1($result, $lazyMessage) {
    this.result_1 = $result;
    this.expected_1 = DefaultJsAsserter_getInstance().e_1;
    this.actual_1 = DefaultJsAsserter_getInstance().a_1;
    this.lazyMessage_1 = $lazyMessage;
  }
  protoOf(DefaultJsAsserter$invokeHook$1).get_result_iyg5d2_k$ = function () {
    return this.result_1;
  };
  protoOf(DefaultJsAsserter$invokeHook$1).get_expected_77p56p_k$ = function () {
    return this.expected_1;
  };
  protoOf(DefaultJsAsserter$invokeHook$1).get_actual_avlm6v_k$ = function () {
    return this.actual_1;
  };
  protoOf(DefaultJsAsserter$invokeHook$1).get_lazyMessage_4a501i_k$ = function () {
    return this.lazyMessage_1;
  };
  function DefaultJsAsserter() {
    DefaultJsAsserter_instance = this;
    this.e_1 = undefined;
    this.a_1 = undefined;
  }
  protoOf(DefaultJsAsserter).assertEquals_ldumo_k$ = function (message, expected, actual) {
    this.e_1 = expected;
    this.a_1 = actual;
    assertEquals.call(this, message, expected, actual);
  };
  protoOf(DefaultJsAsserter).assertNotEquals_xv90xj_k$ = function (message, illegal, actual) {
    this.e_1 = illegal;
    this.a_1 = actual;
    assertNotEquals.call(this, message, illegal, actual);
  };
  protoOf(DefaultJsAsserter).assertSame_c5zpxl_k$ = function (message, expected, actual) {
    this.e_1 = expected;
    this.a_1 = actual;
    assertSame.call(this, message, expected, actual);
  };
  protoOf(DefaultJsAsserter).assertNotSame_yy9ia_k$ = function (message, illegal, actual) {
    this.e_1 = illegal;
    this.a_1 = actual;
    assertNotSame.call(this, message, illegal, actual);
  };
  protoOf(DefaultJsAsserter).assertNull_8hzwuv_k$ = function (message, actual) {
    this.a_1 = actual;
    assertNull.call(this, message, actual);
  };
  protoOf(DefaultJsAsserter).assertNotNull_i3opa6_k$ = function (message, actual) {
    this.a_1 = actual;
    assertNotNull.call(this, message, actual);
  };
  protoOf(DefaultJsAsserter).assertTrue_rpw5fg_k$ = function (lazyMessage, actual) {
    if (!actual) {
      // Inline function 'kotlin.test.DefaultJsAsserter.failWithMessage' call
      var message = lazyMessage();
      invokeHook(this, false, DefaultJsAsserter$assertTrue$lambda(message));
      // Inline function 'kotlin.test.AssertionErrorWithCause' call
      throw AssertionError_init_$Create$(message, null);
    } else {
      invokeHook(this, true, lazyMessage);
    }
  };
  protoOf(DefaultJsAsserter).assertTrue_dqb114_k$ = function (message, actual) {
    this.assertTrue_rpw5fg_k$(DefaultJsAsserter$assertTrue$lambda_0(message), actual);
  };
  protoOf(DefaultJsAsserter).fail_o3vfxl_k$ = function (message) {
    this.fail_zdvzi9_k$(message, null);
  };
  protoOf(DefaultJsAsserter).fail_zdvzi9_k$ = function (message, cause) {
    // Inline function 'kotlin.test.DefaultJsAsserter.failWithMessage' call
    // Inline function 'kotlin.test.DefaultJsAsserter.fail.<anonymous>' call
    var message_0 = message;
    invokeHook(this, false, DefaultJsAsserter$fail$lambda(message_0));
    // Inline function 'kotlin.test.AssertionErrorWithCause' call
    throw AssertionError_init_$Create$(message_0, cause);
  };
  var DefaultJsAsserter_instance;
  function DefaultJsAsserter_getInstance() {
    if (DefaultJsAsserter_instance == null)
      new DefaultJsAsserter();
    return DefaultJsAsserter_instance;
  }
  function assertHook$lambda(_anonymous_parameter_0__qggqh8) {
    _init_properties_DefaultJsAsserter_kt__dbqvm3();
    return Unit_getInstance();
  }
  var properties_initialized_DefaultJsAsserter_kt_jkw377;
  function _init_properties_DefaultJsAsserter_kt__dbqvm3() {
    if (!properties_initialized_DefaultJsAsserter_kt_jkw377) {
      properties_initialized_DefaultJsAsserter_kt_jkw377 = true;
      assertHook = assertHook$lambda;
    }
  }
  function lookupAsserter() {
    return DefaultJsAsserter_getInstance();
  }
  function AssertionErrorWithCause(message, cause) {
    return AssertionError_init_$Create$(message, cause);
  }
  function checkResultIsFailure_0(exceptionClass, message, blockResult) {
    // Inline function 'kotlin.fold' call
    // Inline function 'kotlin.contracts.contract' call
    var exception = Result__exceptionOrNull_impl_p6xea9(blockResult);
    var tmp;
    if (exception == null) {
      var tmp$ret$0;
      // Inline function 'kotlin.test.checkResultIsFailure.<anonymous>' call
      var tmp_0 = _Result___get_value__impl__bjfvqg(blockResult);
      (tmp_0 == null ? true : !(tmp_0 == null)) || THROW_CCE();
      get_asserter().fail_o3vfxl_k$(messagePrefix(message) + ('Expected an exception of ' + toString_0(exceptionClass) + ' to be thrown, but was completed successfully.'));
      tmp = tmp$ret$0;
    } else {
      var tmp$ret$1;
      // Inline function 'kotlin.test.checkResultIsFailure.<anonymous>' call
      if (exceptionClass.isInstance_6tn68w_k$(exception)) {
        return exception instanceof Error ? exception : THROW_CCE();
      }
      get_asserter().fail_zdvzi9_k$(messagePrefix(message) + ('Expected an exception of ' + toString_0(exceptionClass) + ' to be thrown, but was ' + exception.toString()), exception);
      tmp = tmp$ret$1;
    }
  }
  function set_currentAdapter(_set____db54di) {
    _init_properties_TestApi_kt__c5696e();
    currentAdapter = _set____db54di;
  }
  function get_currentAdapter() {
    _init_properties_TestApi_kt__c5696e();
    return currentAdapter;
  }
  var currentAdapter;
  function get_NAME_TO_ADAPTER() {
    _init_properties_TestApi_kt__c5696e();
    return NAME_TO_ADAPTER;
  }
  var NAME_TO_ADAPTER;
  function detectAdapter() {
    _init_properties_TestApi_kt__c5696e();
    var frameworkAdapter = isQUnit() ? new QUnitAdapter() : isJasmine() ? new JasmineLikeAdapter() : new BareAdapter();
    var tmp;
    if (!(typeof kotlinTest === 'undefined')) {
      var adapterTransform = kotlinTest.adapterTransformer;
      var tmp_0;
      if (!(adapterTransform === null)) {
        tmp_0 = adapterTransform(frameworkAdapter);
      } else {
        tmp_0 = frameworkAdapter;
      }
      tmp = tmp_0;
    } else {
      tmp = frameworkAdapter;
    }
    return tmp;
  }
  function suite(name, ignored, suiteFn) {
    _init_properties_TestApi_kt__c5696e();
    adapter().suite(name, ignored, suiteFn);
  }
  function adapter() {
    _init_properties_TestApi_kt__c5696e();
    var tmp0_elvis_lhs = get_currentAdapter();
    var result = tmp0_elvis_lhs == null ? detectAdapter() : tmp0_elvis_lhs;
    set_currentAdapter(result);
    return result;
  }
  function test(name, ignored, testFn) {
    _init_properties_TestApi_kt__c5696e();
    adapter().test(name, ignored, testFn);
  }
  function QUnitAdapter$_init_$ref_3ho991() {
    var l = function () {
      return new QUnitAdapter();
    };
    l.callableName = '<init>';
    return l;
  }
  function JasmineLikeAdapter$_init_$ref_hb6pdw() {
    var l = function () {
      return new JasmineLikeAdapter();
    };
    l.callableName = '<init>';
    return l;
  }
  function JasmineLikeAdapter$_init_$ref_hb6pdw_0() {
    var l = function () {
      return new JasmineLikeAdapter();
    };
    l.callableName = '<init>';
    return l;
  }
  function JasmineLikeAdapter$_init_$ref_hb6pdw_1() {
    var l = function () {
      return new JasmineLikeAdapter();
    };
    l.callableName = '<init>';
    return l;
  }
  function detectAdapter$ref() {
    var l = function () {
      return detectAdapter();
    };
    l.callableName = 'detectAdapter';
    return l;
  }
  var properties_initialized_TestApi_kt_44md0o;
  function _init_properties_TestApi_kt__c5696e() {
    if (!properties_initialized_TestApi_kt_44md0o) {
      properties_initialized_TestApi_kt_44md0o = true;
      currentAdapter = null;
      var tmp = to('qunit', QUnitAdapter$_init_$ref_3ho991());
      var tmp_0 = to('jasmine', JasmineLikeAdapter$_init_$ref_hb6pdw());
      var tmp_1 = to('mocha', JasmineLikeAdapter$_init_$ref_hb6pdw_0());
      var tmp_2 = to('jest', JasmineLikeAdapter$_init_$ref_hb6pdw_1());
      NAME_TO_ADAPTER = mapOf([tmp, tmp_0, tmp_1, tmp_2, to('auto', detectAdapter$ref())]);
    }
  }
  function BareAdapter() {
  }
  protoOf(BareAdapter).suite_crp1m3_k$ = function (name, ignored, suiteFn) {
    if (!ignored) {
      suiteFn();
    }
  };
  protoOf(BareAdapter).suite = function (name, ignored, suiteFn) {
    return this.suite_crp1m3_k$(name, ignored, suiteFn);
  };
  protoOf(BareAdapter).test_sdoelz_k$ = function (name, ignored, testFn) {
    if (!ignored) {
      testFn();
    }
  };
  protoOf(BareAdapter).test = function (name, ignored, testFn) {
    return this.test_sdoelz_k$(name, ignored, testFn);
  };
  function isQUnit() {
    return typeof QUnit !== 'undefined';
  }
  function isJasmine() {
    return typeof describe === 'function' && typeof it === 'function';
  }
  function JasmineLikeAdapter() {
  }
  protoOf(JasmineLikeAdapter).suite_crp1m3_k$ = function (name, ignored, suiteFn) {
    if (ignored) {
      xdescribe(name, suiteFn);
    } else {
      describe(name, suiteFn);
    }
  };
  protoOf(JasmineLikeAdapter).suite = function (name, ignored, suiteFn) {
    return this.suite_crp1m3_k$(name, ignored, suiteFn);
  };
  protoOf(JasmineLikeAdapter).test_sdoelz_k$ = function (name, ignored, testFn) {
    if (ignored) {
      xit(name, testFn);
    } else {
      it(name, testFn);
    }
  };
  protoOf(JasmineLikeAdapter).test = function (name, ignored, testFn) {
    return this.test_sdoelz_k$(name, ignored, testFn);
  };
  function wrapTest($this, testFn) {
    return QUnitAdapter$wrapTest$lambda(testFn);
  }
  function QUnitAdapter$wrapTest$lambda$lambda($assertionsHappened, $assert) {
    return function (testResult) {
      $assertionsHappened._v = true;
      $assert.ok(testResult.result, testResult.lazyMessage());
      return Unit_getInstance();
    };
  }
  function QUnitAdapter$wrapTest$lambda($testFn) {
    return function (assert) {
      var assertionsHappened = {_v: false};
      set_assertHook(QUnitAdapter$wrapTest$lambda$lambda(assertionsHappened, assert));
      var possiblePromise = $testFn();
      var tmp;
      if (!assertionsHappened._v) {
        assertTrue_1(true, 'A test with no assertions is considered successful');
        tmp = Unit_getInstance();
      }
      return possiblePromise;
    };
  }
  function QUnitAdapter() {
    this.ignoredSuite_1 = false;
  }
  protoOf(QUnitAdapter).set_ignoredSuite_2oo8xe_k$ = function (_set____db54di) {
    this.ignoredSuite_1 = _set____db54di;
  };
  protoOf(QUnitAdapter).get_ignoredSuite_dvl2mn_k$ = function () {
    return this.ignoredSuite_1;
  };
  protoOf(QUnitAdapter).suite_crp1m3_k$ = function (name, ignored, suiteFn) {
    var prevIgnore = this.ignoredSuite_1;
    this.ignoredSuite_1 = !!(this.ignoredSuite_1 | ignored);
    QUnit.module(name, suiteFn);
    this.ignoredSuite_1 = prevIgnore;
  };
  protoOf(QUnitAdapter).suite = function (name, ignored, suiteFn) {
    return this.suite_crp1m3_k$(name, ignored, suiteFn);
  };
  protoOf(QUnitAdapter).test_sdoelz_k$ = function (name, ignored, testFn) {
    if (!!(ignored | this.ignoredSuite_1)) {
      QUnit.skip(name, wrapTest(this, testFn));
    } else {
      QUnit.test(name, wrapTest(this, testFn));
    }
  };
  protoOf(QUnitAdapter).test = function (name, ignored, testFn) {
    return this.test_sdoelz_k$(name, ignored, testFn);
  };
  //region block: post-declaration
  defineProp(protoOf(DefaultJsAsserter$invokeHook$1), 'result', function () {
    return this.get_result_iyg5d2_k$();
  });
  defineProp(protoOf(DefaultJsAsserter$invokeHook$1), 'expected', function () {
    return this.get_expected_77p56p_k$();
  });
  defineProp(protoOf(DefaultJsAsserter$invokeHook$1), 'actual', function () {
    return this.get_actual_avlm6v_k$();
  });
  defineProp(protoOf(DefaultJsAsserter$invokeHook$1), 'lazyMessage', function () {
    return this.get_lazyMessage_4a501i_k$();
  });
  //endregion
  //region block: init
  _asserter = null;
  //endregion
  //region block: exports
  _.$_$ = _.$_$ || {};
  _.$_$.a = assertContentEquals_0;
  _.$_$.b = assertContentEquals;
  _.$_$.c = assertEquals_0;
  _.$_$.d = assertFalse;
  _.$_$.e = assertNotEquals_0;
  _.$_$.f = assertTrue_1;
  _.$_$.g = checkResultIsFailure;
  _.$_$.h = checkResultIsFailure_0;
  _.$_$.i = suite;
  _.$_$.j = test;
  //endregion
  return _;
}));
