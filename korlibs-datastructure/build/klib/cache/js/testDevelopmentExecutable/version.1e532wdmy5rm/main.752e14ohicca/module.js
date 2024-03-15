(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js', './korge-root-korlibs-concurrent.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'), require('./korge-root-korlibs-concurrent.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'korge-root-korlibs-datastructure'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'korge-root-korlibs-datastructure'.");
    }
    if (typeof this['korge-root-korlibs-concurrent'] === 'undefined') {
      throw new Error("Error loading module 'korge-root-korlibs-datastructure'. Its dependency 'korge-root-korlibs-concurrent' was not found. Please, check whether 'korge-root-korlibs-concurrent' is loaded prior to 'korge-root-korlibs-datastructure'.");
    }
    root['korge-root-korlibs-datastructure'] = factory(typeof this['korge-root-korlibs-datastructure'] === 'undefined' ? {} : this['korge-root-korlibs-datastructure'], this['kotlin-kotlin-stdlib'], this['korge-root-korlibs-concurrent']);
  }
}(this, function (_, kotlin_kotlin, kotlin_com_soywiz_korge_korlibs_concurrent) {
  'use strict';
  //region block: imports
  var imul = Math.imul;
  var clz32 = Math.clz32;
  var log2 = Math.log2;
  var fillArrayVal = kotlin_kotlin.$_$.v8;
  var THROW_CCE = kotlin_kotlin.$_$.oc;
  var isArray = kotlin_kotlin.$_$.k9;
  var protoOf = kotlin_kotlin.$_$.x9;
  var lines = kotlin_kotlin.$_$.fb;
  var collectionSizeOrDefault = kotlin_kotlin.$_$.q3;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.l;
  var isCharSequence = kotlin_kotlin.$_$.l9;
  var trim = kotlin_kotlin.$_$.vb;
  var toString = kotlin_kotlin.$_$.ba;
  var startsWith = kotlin_kotlin.$_$.nb;
  var ArrayList_init_$Create$_0 = kotlin_kotlin.$_$.m;
  var charSequenceLength = kotlin_kotlin.$_$.q8;
  var maxOrNull = kotlin_kotlin.$_$.k5;
  var getOrNull = kotlin_kotlin.$_$.t4;
  var getOrNull_0 = kotlin_kotlin.$_$.cb;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.z1;
  var Char = kotlin_kotlin.$_$.bc;
  var initMetadataForCompanion = kotlin_kotlin.$_$.e9;
  var print = kotlin_kotlin.$_$.h8;
  var equals = kotlin_kotlin.$_$.u8;
  var contentEquals = kotlin_kotlin.$_$.r3;
  var contentHashCode = kotlin_kotlin.$_$.w3;
  var arrayIterator = kotlin_kotlin.$_$.j8;
  var VOID = kotlin_kotlin.$_$.d;
  var initMetadataForClass = kotlin_kotlin.$_$.d9;
  var isFloatArray = kotlin_kotlin.$_$.o9;
  var contentEquals_0 = kotlin_kotlin.$_$.s3;
  var contentHashCode_0 = kotlin_kotlin.$_$.y3;
  var floatArrayIterator = kotlin_kotlin.$_$.w8;
  var isIntArray = kotlin_kotlin.$_$.p9;
  var contentEquals_1 = kotlin_kotlin.$_$.u3;
  var contentHashCode_1 = kotlin_kotlin.$_$.v3;
  var intArrayIterator = kotlin_kotlin.$_$.j9;
  var isDoubleArray = kotlin_kotlin.$_$.n9;
  var contentEquals_2 = kotlin_kotlin.$_$.t3;
  var contentHashCode_2 = kotlin_kotlin.$_$.x3;
  var doubleArrayIterator = kotlin_kotlin.$_$.t8;
  var Unit_getInstance = kotlin_kotlin.$_$.r2;
  var println = kotlin_kotlin.$_$.f8;
  var IllegalStateException_init_$Create$ = kotlin_kotlin.$_$.f1;
  var Long = kotlin_kotlin.$_$.jc;
  var toLong = kotlin_kotlin.$_$.z9;
  var toMutableList = kotlin_kotlin.$_$.d7;
  var copyOf = kotlin_kotlin.$_$.c4;
  var copyOf_0 = kotlin_kotlin.$_$.d4;
  var reverse = kotlin_kotlin.$_$.j6;
  var reverse_0 = kotlin_kotlin.$_$.g6;
  var reverse_1 = kotlin_kotlin.$_$.c6;
  var reverse_2 = kotlin_kotlin.$_$.i6;
  var reverse_3 = kotlin_kotlin.$_$.f6;
  var reverse_4 = kotlin_kotlin.$_$.e6;
  var reverse_5 = kotlin_kotlin.$_$.d6;
  var reverse_6 = kotlin_kotlin.$_$.h6;
  var IndexOutOfBoundsException_init_$Create$ = kotlin_kotlin.$_$.i1;
  var IllegalArgumentException_init_$Create$ = kotlin_kotlin.$_$.c1;
  var objectCreate = kotlin_kotlin.$_$.w9;
  var copyOf_1 = kotlin_kotlin.$_$.i4;
  var IndexOutOfBoundsException_init_$Create$_0 = kotlin_kotlin.$_$.h1;
  var toShort = kotlin_kotlin.$_$.aa;
  var take = kotlin_kotlin.$_$.r6;
  var asList = kotlin_kotlin.$_$.n3;
  var List = kotlin_kotlin.$_$.a3;
  var isInterface = kotlin_kotlin.$_$.q9;
  var StringBuilder_init_$Create$ = kotlin_kotlin.$_$.x;
  var Collection = kotlin_kotlin.$_$.w2;
  var copyOf_2 = kotlin_kotlin.$_$.g4;
  var take_0 = kotlin_kotlin.$_$.q6;
  var copyOf_3 = kotlin_kotlin.$_$.f4;
  var take_1 = kotlin_kotlin.$_$.s6;
  var initMetadataForInterface = kotlin_kotlin.$_$.g9;
  var coerceAtLeast = kotlin_kotlin.$_$.ha;
  var THROW_IAE = kotlin_kotlin.$_$.pc;
  var getProgressionLastElement = kotlin_kotlin.$_$.e8;
  var compareTo = kotlin_kotlin.$_$.r8;
  var initMetadataForObject = kotlin_kotlin.$_$.i9;
  var MutableMap = kotlin_kotlin.$_$.g3;
  var Map_0 = kotlin_kotlin.$_$.b3;
  var AbstractList = kotlin_kotlin.$_$.s2;
  var toByte = kotlin_kotlin.$_$.y9;
  var until = kotlin_kotlin.$_$.ma;
  var ensureNotNull = kotlin_kotlin.$_$.tc;
  var LinkedHashMap_init_$Create$ = kotlin_kotlin.$_$.r;
  var first = kotlin_kotlin.$_$.q4;
  var toList = kotlin_kotlin.$_$.y6;
  var toList_0 = kotlin_kotlin.$_$.a7;
  var putAll = kotlin_kotlin.$_$.o5;
  var hashCode = kotlin_kotlin.$_$.c9;
  var NonRecursiveLock = kotlin_com_soywiz_korge_korlibs_concurrent.$_$.c;
  var copyOfRange = kotlin_kotlin.$_$.b4;
  var copyOf_4 = kotlin_kotlin.$_$.e4;
  var MutableEntry = kotlin_kotlin.$_$.f3;
  var get_indices = kotlin_kotlin.$_$.w4;
  var to = kotlin_kotlin.$_$.ad;
  var emptyList = kotlin_kotlin.$_$.m4;
  var addAll = kotlin_kotlin.$_$.k3;
  var toMutableSet = kotlin_kotlin.$_$.g7;
  var toSet = kotlin_kotlin.$_$.h7;
  var MutableIterator = kotlin_kotlin.$_$.d3;
  var StringBuilder_init_$Create$_0 = kotlin_kotlin.$_$.y;
  var MutableCollection = kotlin_kotlin.$_$.c3;
  var removeLast = kotlin_kotlin.$_$.a6;
  var addAll_0 = kotlin_kotlin.$_$.j3;
  var MutableList = kotlin_kotlin.$_$.e3;
  var AbstractMutableSet = kotlin_kotlin.$_$.u2;
  var LinkedHashSet_init_$Create$ = kotlin_kotlin.$_$.s;
  var toRawBits = kotlin_kotlin.$_$.yc;
  var FloatCompanionObject_getInstance = kotlin_kotlin.$_$.l2;
  var floatFromBits = kotlin_kotlin.$_$.x8;
  var getNumberHashCode = kotlin_kotlin.$_$.z8;
  var Iterable = kotlin_kotlin.$_$.x2;
  var throwUninitializedPropertyAccessException = kotlin_kotlin.$_$.xc;
  var NoSuchElementException_init_$Create$ = kotlin_kotlin.$_$.j1;
  var ListIterator = kotlin_kotlin.$_$.z2;
  var coerceAtMost = kotlin_kotlin.$_$.ia;
  var joinToString = kotlin_kotlin.$_$.z4;
  var asSequence = kotlin_kotlin.$_$.sa;
  var Pair = kotlin_kotlin.$_$.mc;
  var println_0 = kotlin_kotlin.$_$.g8;
  var charArray = kotlin_kotlin.$_$.o8;
  var concatToString = kotlin_kotlin.$_$.ya;
  var IllegalStateException_init_$Create$_0 = kotlin_kotlin.$_$.d1;
  var numberToInt = kotlin_kotlin.$_$.u9;
  var copyOf_5 = kotlin_kotlin.$_$.h4;
  var toString_0 = kotlin_kotlin.$_$.zc;
  var mapCapacity = kotlin_kotlin.$_$.h5;
  var LinkedHashMap_init_$Create$_0 = kotlin_kotlin.$_$.q;
  var MutableSet = kotlin_kotlin.$_$.h3;
  var Iterator = kotlin_kotlin.$_$.y2;
  var last = kotlin_kotlin.$_$.e5;
  var firstOrNull = kotlin_kotlin.$_$.n4;
  var lastOrNull = kotlin_kotlin.$_$.d5;
  var Comparator = kotlin_kotlin.$_$.dc;
  var reversed = kotlin_kotlin.$_$.l7;
  var NotImplementedError = kotlin_kotlin.$_$.lc;
  var ArrayList_init_$Create$_1 = kotlin_kotlin.$_$.n;
  var firstOrNull_0 = kotlin_kotlin.$_$.o4;
  var putAll_0 = kotlin_kotlin.$_$.p5;
  var HashMap_init_$Create$ = kotlin_kotlin.$_$.o;
  var lastOrNull_0 = kotlin_kotlin.$_$.c5;
  var first_0 = kotlin_kotlin.$_$.r4;
  var Annotation = kotlin_kotlin.$_$.ac;
  var isComparable = kotlin_kotlin.$_$.m9;
  var getStringHashCode = kotlin_kotlin.$_$.b9;
  var lazy = kotlin_kotlin.$_$.uc;
  var CoroutineImpl = kotlin_kotlin.$_$.b8;
  var SequenceScope = kotlin_kotlin.$_$.ra;
  var get_COROUTINE_SUSPENDED = kotlin_kotlin.$_$.n7;
  var initMetadataForLambda = kotlin_kotlin.$_$.h9;
  var iterator = kotlin_kotlin.$_$.ta;
  var arrayCopy = kotlin_kotlin.$_$.l3;
  var Char__plus_impl_qi7pgj = kotlin_kotlin.$_$.b2;
  var joinToString_0 = kotlin_kotlin.$_$.x4;
  var KProperty1 = kotlin_kotlin.$_$.qa;
  var getPropertyCallableRef = kotlin_kotlin.$_$.a9;
  var SuspendFunction1 = kotlin_kotlin.$_$.c8;
  var Default_getInstance = kotlin_kotlin.$_$.m2;
  var Lock = kotlin_com_soywiz_korge_korlibs_concurrent.$_$.b;
  var Random = kotlin_kotlin.$_$.ga;
  var random = kotlin_kotlin.$_$.y5;
  var random_0 = kotlin_kotlin.$_$.q5;
  var random_1 = kotlin_kotlin.$_$.t5;
  var random_2 = kotlin_kotlin.$_$.w5;
  var random_3 = kotlin_kotlin.$_$.x5;
  var random_4 = kotlin_kotlin.$_$.s5;
  var random_5 = kotlin_kotlin.$_$.r5;
  var random_6 = kotlin_kotlin.$_$.u5;
  var random_7 = kotlin_kotlin.$_$.v5;
  var nativeThread = kotlin_com_soywiz_korge_korlibs_concurrent.$_$.e;
  var toMutableMap = kotlin_kotlin.$_$.f7;
  var coerceIn = kotlin_kotlin.$_$.ja;
  var toMap = kotlin_kotlin.$_$.c7;
  var step = kotlin_kotlin.$_$.la;
  var max = kotlin_kotlin.$_$.gb;
  var Char__toInt_impl_vasixd = kotlin_kotlin.$_$.c2;
  var charSequenceGet = kotlin_kotlin.$_$.p8;
  var get_lastIndex = kotlin_kotlin.$_$.db;
  var _UInt___init__impl__l7qpdl = kotlin_kotlin.$_$.h2;
  var maxOf = kotlin_kotlin.$_$.k7;
  var _UInt___get_data__impl__f0vqqw = kotlin_kotlin.$_$.i2;
  var round = kotlin_kotlin.$_$.da;
  var numberToLong = kotlin_kotlin.$_$.v9;
  var copyToArray = kotlin_kotlin.$_$.j4;
  var AbstractMutableList = kotlin_kotlin.$_$.t2;
  var get_lastIndex_0 = kotlin_kotlin.$_$.b5;
  var joinToString_1 = kotlin_kotlin.$_$.a5;
  var RandomAccess = kotlin_kotlin.$_$.i3;
  //endregion
  //region block: pre-declaration
  initMetadataForCompanion(Companion);
  function get_size() {
    return imul(this.get_width_j0q4yl_k$(), this.get_height_e7t92o_k$());
  }
  function inside(x, y) {
    return ((x >= 0 ? y >= 0 : false) ? x < this.get_width_j0q4yl_k$() : false) ? y < this.get_height_e7t92o_k$() : false;
  }
  function printAt(x, y) {
    return this.printAt_mw6ng8_k$(index(this, x, y));
  }
  function getAt(x, y) {
    return this.getAt_k8n1td_k$(index(this, x, y));
  }
  function setAt(x, y, value) {
    this.setAt_tlf2vp_k$(index(this, x, y), value);
  }
  function set(rows) {
    var n = 0;
    var inductionVariable = 0;
    var last = rows.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var y = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var row = rows.get_c1px32_k$(y);
        var inductionVariable_0 = 0;
        var last_0 = row.get_size_woubt6_k$() - 1 | 0;
        if (inductionVariable_0 <= last_0)
          do {
            var x = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            var _unary__edvuaz = n;
            n = _unary__edvuaz + 1 | 0;
            this.setAt_tlf2vp_k$(_unary__edvuaz, row.get_c1px32_k$(x));
          }
           while (inductionVariable_0 <= last_0);
      }
       while (inductionVariable <= last);
  }
  function contains(v) {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.sequences.any' call
      var tmp0_iterator = asSequence(this.iterator_jk1svi_k$()).iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IArray2.contains.<anonymous>' call
        if (equals(element, v)) {
          tmp$ret$1 = true;
          break $l$block;
        }
      }
      tmp$ret$1 = false;
    }
    return tmp$ret$1;
  }
  function getPositionsWithValue(value) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.filterTo' call
    var this_0 = until(0, this.get_size_woubt6_k$());
    var destination = ArrayList_init_$Create$_0();
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.IArray2.getPositionsWithValue.<anonymous>' call
      if (this.equalsAt_31cqaw_k$(element, value)) {
        destination.add_utx5q5_k$(element);
      }
    }
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(destination, 10));
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.IArray2.getPositionsWithValue.<anonymous>' call
      var tmp$ret$3 = new Pair(item % this.get_width_j0q4yl_k$() | 0, item / this.get_width_j0q4yl_k$() | 0);
      destination_0.add_utx5q5_k$(tmp$ret$3);
    }
    return destination_0;
  }
  function dump() {
    var inductionVariable = 0;
    var last = this.get_height_e7t92o_k$();
    if (inductionVariable < last)
      do {
        var y = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var inductionVariable_0 = 0;
        var last_0 = this.get_width_j0q4yl_k$();
        if (inductionVariable_0 < last_0)
          do {
            var x = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            this.printAt_8yatbk_k$(x, y);
          }
           while (inductionVariable_0 < last_0);
        println_0();
      }
       while (inductionVariable < last);
  }
  function toStringList(charMap, margin) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, this.get_height_e7t92o_k$());
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.IArray2.toStringList.<anonymous>' call
      var tmp = 0;
      var tmp_0 = this.get_width_j0q4yl_k$();
      var tmp_1 = charArray(tmp_0);
      while (tmp < tmp_0) {
        var tmp_2 = tmp;
        tmp_1[tmp_2] = charMap(this.getAt_1svvdj_k$(tmp_2, item)).value_1;
        tmp = tmp + 1 | 0;
      }
      var tmp$ret$1 = margin + concatToString(tmp_1);
      destination.add_utx5q5_k$(tmp$ret$1);
    }
    return destination;
  }
  function toStringList$default(charMap, margin, $super) {
    margin = margin === VOID ? '' : margin;
    return $super === VOID ? this.toStringList_lj7ccw_k$(charMap, margin) : toStringList(charMap, margin);
  }
  function asString(margin, charMap) {
    return joinToString(this.toStringList_lj7ccw_k$(charMap, margin), '\n');
  }
  function asString$default(margin, charMap, $super) {
    margin = margin === VOID ? '' : margin;
    return $super === VOID ? this.asString_df7bct_k$(margin, charMap) : asString(margin, charMap);
  }
  function asString_0(map, margin) {
    return this.asString_df7bct_k$(margin, IArray2$asString$lambda(map));
  }
  function asString$default_0(map, margin, $super) {
    margin = margin === VOID ? '' : margin;
    return $super === VOID ? this.asString_iq9yx0_k$(map, margin) : asString_0(map, margin);
  }
  function asString_1() {
    var tmp = until(0, this.get_height_e7t92o_k$());
    return joinToString(tmp, '\n', VOID, VOID, VOID, VOID, IArray2$asString$lambda_0(this));
  }
  initMetadataForInterface(IArray2, 'IArray2', VOID, VOID, [Iterable]);
  initMetadataForClass(Array2, 'Array2', VOID, VOID, [IArray2]);
  initMetadataForCompanion(Companion_0);
  initMetadataForClass(FloatArray2, 'FloatArray2', VOID, VOID, [IArray2]);
  initMetadataForCompanion(Companion_1);
  initMetadataForClass(IntArray2, 'IntArray2', VOID, VOID, [IArray2]);
  initMetadataForCompanion(Companion_2);
  initMetadataForClass(DoubleArray2, 'DoubleArray2', VOID, VOID, [IArray2]);
  initMetadataForClass(ByteArrayDeque, 'ByteArrayDeque', ByteArrayDeque);
  initMetadataForCompanion(Companion_3);
  initMetadataForClass(IntArrayList, 'IntArrayList', IntArrayList, VOID, [Collection]);
  initMetadataForCompanion(Companion_4);
  function indexOf$default(value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? this.get_size_woubt6_k$() : end;
    return $super === VOID ? this.indexOf_ysf2cx_k$(value, start, end) : $super.indexOf_ysf2cx_k$.call(this, value, start, end);
  }
  function lastIndexOf$default(value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? this.get_size_woubt6_k$() : end;
    return $super === VOID ? this.lastIndexOf_hwuzut_k$(value, start, end) : $super.lastIndexOf_hwuzut_k$.call(this, value, start, end);
  }
  function indexOf(element) {
    return this.indexOf_ysf2cx_k$(element, 0, this.get_size_woubt6_k$());
  }
  function lastIndexOf(element) {
    return this.lastIndexOf_hwuzut_k$(element, 0, this.get_size_woubt6_k$());
  }
  function listIterator() {
    return this.listIterator_70e65o_k$(0);
  }
  function isAlmostEquals(other, epsilon) {
    if (!(this.get_size_woubt6_k$() === other.get_size_woubt6_k$()))
      return false;
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!isAlmostEquals_1(this.getAt_k8n1td_k$(n), other.getAt_k8n1td_k$(n), epsilon))
          return false;
      }
       while (inductionVariable <= last);
    return true;
  }
  initMetadataForInterface(FloatList, 'FloatList', VOID, VOID, [Collection]);
  initMetadataForClass(FloatArrayList, 'FloatArrayList', FloatArrayList, VOID, [FloatList]);
  initMetadataForCompanion(Companion_5);
  function indexOf$default_0(value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? this.get_size_woubt6_k$() : end;
    return $super === VOID ? this.indexOf_nnxgkd_k$(value, start, end) : $super.indexOf_nnxgkd_k$.call(this, value, start, end);
  }
  function lastIndexOf$default_0(value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? this.get_size_woubt6_k$() : end;
    return $super === VOID ? this.lastIndexOf_pg7gb7_k$(value, start, end) : $super.lastIndexOf_pg7gb7_k$.call(this, value, start, end);
  }
  function indexOf_0(element) {
    return this.indexOf_nnxgkd_k$(element, 0, this.get_size_woubt6_k$());
  }
  function lastIndexOf_0(element) {
    return this.lastIndexOf_pg7gb7_k$(element, 0, this.get_size_woubt6_k$());
  }
  function listIterator_0() {
    return this.listIterator_70e65o_k$(0);
  }
  function isAlmostEquals_0(other, epsilon) {
    if (!(this.get_size_woubt6_k$() === other.get_size_woubt6_k$()))
      return false;
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!isAlmostEquals_2(this.getAt_k8n1td_k$(n), other.getAt_k8n1td_k$(n), epsilon))
          return false;
      }
       while (inductionVariable <= last);
    return true;
  }
  initMetadataForInterface(DoubleList, 'DoubleList', VOID, VOID, [Collection]);
  initMetadataForClass(DoubleArrayList, 'DoubleArrayList', DoubleArrayList, VOID, [DoubleList]);
  initMetadataForClass(SortOps, 'SortOps');
  initMetadataForObject(IntArrayListSortOps, 'IntArrayListSortOps', VOID, SortOps);
  function isEmpty() {
    return this.get_size_woubt6_k$() === 0;
  }
  function containsKey(key) {
    return this.get_keys_wop4xp_k$().contains_aljjnj_k$(key);
  }
  function containsValue(value) {
    return this.get_values_ksazhn_k$().contains_aljjnj_k$(value);
  }
  initMetadataForInterface(BaseMap, 'BaseMap', VOID, VOID, [Map_0]);
  function putAll_1(from) {
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = from.get_entries_p20ztl_k$().iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      // Inline function 'kotlin.collections.component1' call
      var k = _destruct__k2r9zo.get_key_18j28a_k$();
      // Inline function 'kotlin.collections.component2' call
      var v = _destruct__k2r9zo.get_value_j01efc_k$();
      this.put_4fpzoq_k$(k, v);
    }
  }
  initMetadataForInterface(BaseMutableMap, 'BaseMutableMap', VOID, VOID, [BaseMap, MutableMap]);
  initMetadataForCompanion(Companion_6);
  initMetadataForClass(BitArray, 'BitArray', VOID, AbstractList, [AbstractList, Collection]);
  initMetadataForClass(BitSet, 'BitSet', VOID, VOID, [Collection]);
  initMetadataForClass(BaseCacheMap, 'BaseCacheMap', BaseCacheMap, VOID, [BaseMutableMap]);
  initMetadataForClass(CacheMap, 'CacheMap', CacheMap, BaseCacheMap);
  initMetadataForClass(CaseInsensitiveStringMap, 'CaseInsensitiveStringMap', CaseInsensitiveStringMap_init_$Create$, VOID, [MutableMap]);
  initMetadataForClass(ChunkedByteDeque, 'ChunkedByteDeque', ChunkedByteDeque);
  initMetadataForClass(Bucket, 'Bucket');
  initMetadataForClass(CustomHashMap$entries$2$1, VOID, VOID, VOID, [MutableEntry]);
  initMetadataForClass(CustomHashMap, 'CustomHashMap', VOID, VOID, [MutableMap]);
  initMetadataForClass(TGenDeque$iterator$1, VOID, VOID, VOID, [MutableIterator]);
  initMetadataForClass(TGenDeque, 'TGenDeque', TGenDeque_init_$Create$, VOID, [MutableCollection]);
  initMetadataForClass(IntDeque$iterator$1, VOID, VOID, VOID, [MutableIterator]);
  initMetadataForClass(IntDeque, 'IntDeque', IntDeque_init_$Create$, VOID, [MutableCollection]);
  function addAll_1(elements) {
    return this.addAll_4lagoh_k$(isInterface(elements, Collection) ? elements : THROW_CCE());
  }
  function setAddAll(index, elements, offset, size) {
    // Inline function 'kotlin.math.min' call
    var b = this.get_size_woubt6_k$() - index | 0;
    var setCount = Math.min(size, b);
    this.setAll_lwy6my_k$(index, elements, offset, setCount);
    this.addAll_qvfgz7_k$(elements, offset + setCount | 0, size - setCount | 0);
  }
  function setAddAll$default(index, elements, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? elements.get_size_woubt6_k$() - offset | 0 : size;
    var tmp;
    if ($super === VOID) {
      this.setAddAll_nj37h9_k$(index, elements, offset, size);
      tmp = Unit_getInstance();
    } else {
      setAddAll(index, elements, offset, size);
      tmp = Unit_getInstance();
    }
    return tmp;
  }
  function setAll(index, elements, offset, size) {
    var inductionVariable = 0;
    if (inductionVariable < size)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this.set_82063s_k$(index + n | 0, elements.get_c1px32_k$(offset + n | 0));
      }
       while (inductionVariable < size);
  }
  function setAll$default(index, elements, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? elements.get_size_woubt6_k$() - offset | 0 : size;
    var tmp;
    if ($super === VOID) {
      this.setAll_lwy6my_k$(index, elements, offset, size);
      tmp = Unit_getInstance();
    } else {
      setAll(index, elements, offset, size);
      tmp = Unit_getInstance();
    }
    return tmp;
  }
  function addAll_2(elements, offset, size) {
    var inductionVariable = 0;
    if (inductionVariable < size)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this.add_utx5q5_k$(elements.get_c1px32_k$(offset + n | 0));
      }
       while (inductionVariable < size);
  }
  function addAll$default(elements, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? elements.get_size_woubt6_k$() - offset | 0 : size;
    var tmp;
    if ($super === VOID) {
      this.addAll_qvfgz7_k$(elements, offset, size);
      tmp = Unit_getInstance();
    } else {
      addAll_2(elements, offset, size);
      tmp = Unit_getInstance();
    }
    return tmp;
  }
  function removeToSize(size) {
    this.removeRange_sm1kzt_k$(size, this.get_size_woubt6_k$());
  }
  initMetadataForInterface(MutableListEx, 'MutableListEx', VOID, VOID, [MutableList]);
  initMetadataForClass(FastIdentityCacheMap, 'FastIdentityCacheMap', FastIdentityCacheMap);
  initMetadataForClass(FastSmallSet, 'FastSmallSet', FastSmallSet, AbstractMutableSet);
  initMetadataForCompanion(Companion_7);
  initMetadataForClass(Entry, 'Entry');
  initMetadataForClass(Iterator_0, 'Iterator');
  initMetadataForClass(_no_name_provided__qut3iv, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(_no_name_provided__qut3iv_0, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(_no_name_provided__qut3iv_1, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(IntFloatMap, 'IntFloatMap', IntFloatMap_init_$Create$);
  initMetadataForClass(GenericSubList, 'GenericSubList', VOID, VOID, [List]);
  initMetadataForClass(GenericListIterator, 'GenericListIterator', VOID, VOID, [ListIterator]);
  initMetadataForClass(HistoryStack, 'HistoryStack', HistoryStack);
  initMetadataForCompanion(Companion_8);
  initMetadataForClass(IndexedTable, 'IndexedTable', IndexedTable, VOID, [Iterable]);
  initMetadataForCompanion(Companion_9);
  initMetadataForClass(Entry_0, 'Entry');
  initMetadataForClass(Iterator_1, 'Iterator');
  initMetadataForClass(_no_name_provided__qut3iv_2, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(_no_name_provided__qut3iv_3, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(_no_name_provided__qut3iv_4, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(IntIntMap, 'IntIntMap', IntIntMap_init_$Create$);
  initMetadataForCompanion(Companion_10);
  initMetadataForClass(Entry_1, 'Entry');
  initMetadataForClass(Iterator_2, 'Iterator');
  initMetadataForClass(_no_name_provided__qut3iv_5, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(_no_name_provided__qut3iv_6, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(_no_name_provided__qut3iv_7, VOID, VOID, VOID, [Iterable]);
  initMetadataForClass(IntMap, 'IntMap', IntMap_init_$Create$);
  initMetadataForClass(IntSet, 'IntSet', IntSet, VOID, [MutableSet]);
  initMetadataForClass(Iterator$1, VOID, VOID, VOID, [Iterator]);
  initMetadataForClass(LRUCache, 'LRUCache', LRUCache, BaseCacheMap);
  initMetadataForClass(ListReader, 'ListReader');
  initMetadataForClass(flatMapIterable$1, VOID, VOID, VOID, [Iterable]);
  initMetadataForCompanion(Companion_11);
  initMetadataForInterface(Poolable, 'Poolable');
  initMetadataForClass(Pool, 'Pool');
  initMetadataForClass(sam$kotlin_Comparator$0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForCompanion(Companion_12);
  initMetadataForClass(TGenPriorityQueue$iterator$1, VOID, VOID, VOID, [MutableIterator]);
  initMetadataForClass(TGenPriorityQueue, 'TGenPriorityQueue', VOID, VOID, [MutableCollection]);
  initMetadataForClass(sam$kotlin_Comparator$0_0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForCompanion(Companion_13);
  initMetadataForClass(IntPriorityQueue$iterator$1, VOID, VOID, VOID, [MutableIterator]);
  initMetadataForClass(IntPriorityQueue, 'IntPriorityQueue', VOID, VOID, [MutableCollection]);
  initMetadataForClass(PriorityQueueNode, 'PriorityQueueNode');
  initMetadataForClass(TGenQueue, 'TGenQueue', TGenQueue, VOID, [Collection]);
  initMetadataForClass(IntQueue, 'IntQueue', IntQueue, VOID, [Collection]);
  initMetadataForClass(ByteRingBuffer, 'ByteRingBuffer');
  initMetadataForClass(RingBuffer, 'RingBuffer', VOID, ByteRingBuffer);
  initMetadataForClass(SlowIdentityHashMap, 'SlowIdentityHashMap', SlowIdentityHashMap, CustomHashMap);
  initMetadataForCompanion(Companion_14);
  initMetadataForObject(Sorting, 'Sorting', VOID, SortOps);
  function isEmpty_0() {
    return this.get_size_woubt6_k$() === 0;
  }
  function putAll_2(from) {
    // Inline function 'kotlin.collections.forEach' call
    // Inline function 'kotlin.collections.iterator' call
    var tmp0_iterator = from.get_entries_p20ztl_k$().iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.map.MutableMapExt.putAll.<anonymous>' call
      // Inline function 'kotlin.collections.set' call
      var key = element.get_key_18j28a_k$();
      var value = element.get_value_j01efc_k$();
      this.put_4fpzoq_k$(key, value);
    }
  }
  initMetadataForInterface(MutableMapExt, 'MutableMapExt', VOID, VOID, [MutableMap]);
  initMetadataForClass(SortedMap, 'SortedMap', VOID, VOID, [MutableMapExt]);
  function setFirst(x, y, value) {
    this.set_6f0u4y_k$(x, y, 0, value);
  }
  function getFirst(x, y) {
    var level = this.getStackLevel_20g9m6_k$(x, y);
    if (level === 0)
      return this.get_empty_iqwn50_k$();
    return this.get_ky6wce_k$(x, y, 0);
  }
  function getLast(x, y) {
    var level = this.getStackLevel_20g9m6_k$(x, y);
    if (level === 0)
      return this.get_empty_iqwn50_k$();
    return this.get_ky6wce_k$(x, y, level - 1 | 0);
  }
  function inside_0(x, y) {
    return ((x >= 0 ? y >= 0 : false) ? x < this.get_width_j0q4yl_k$() : false) ? y < this.get_height_e7t92o_k$() : false;
  }
  initMetadataForInterface(IStackedIntArray2, 'IStackedIntArray2');
  initMetadataForClass(SparseChunkedStackedIntArray2, 'SparseChunkedStackedIntArray2', SparseChunkedStackedIntArray2, VOID, [IStackedIntArray2]);
  initMetadataForCompanion(Companion_15);
  initMetadataForClass(IntStack, 'IntStack', VOID, VOID, [Collection]);
  initMetadataForCompanion(Companion_16);
  initMetadataForClass(TGenStack, 'TGenStack', VOID, VOID, [Collection]);
  initMetadataForCompanion(Companion_17);
  initMetadataForClass(StackedIntArray2, 'StackedIntArray2', VOID, VOID, [IStackedIntArray2]);
  initMetadataForCompanion(Companion_18);
  initMetadataForClass(Historiogram, 'Historiogram', Historiogram);
  initMetadataForCompanion(Companion_19);
  initMetadataForClass(RLE, 'RLE', RLE);
  initMetadataForClass(Template, 'Template', VOID, VOID, [Annotation]);
  initMetadataForObject(ComparatorComparable_0, 'ComparatorComparable', VOID, VOID, [Comparator]);
  initMetadataForClass(Comparators, 'Comparators', Comparators);
  initMetadataForCompanion(Companion_20, Comparators);
  initMetadataForClass(Node, 'Node');
  initMetadataForClass(RemoveSubtreeRetObject, 'RemoveSubtreeRetObject');
  initMetadataForClass(IntersectResult, 'IntersectResult');
  initMetadataForLambda(BVH$iterator$slambda, CoroutineImpl, [CoroutineImpl], [1]);
  initMetadataForClass(BVH, 'BVH', VOID, VOID, [Iterable]);
  initMetadataForCompanion(Companion_21);
  initMetadataForClass(BVHIntervals, 'BVHIntervals');
  initMetadataForClass(BVHRect, 'BVHRect');
  initMetadataForClass(BVHRay, 'BVHRay');
  initMetadataForCompanion(Companion_22);
  initMetadataForClass(BVHVector, 'BVHVector');
  initMetadataForClass(FakeMutableIterator, 'FakeMutableIterator', VOID, VOID, [MutableIterator, Iterator]);
  initMetadataForClass(KdsInternalApi, 'KdsInternalApi', VOID, VOID, [Annotation]);
  initMetadataForCompanion(Companion_23);
  initMetadataForClass(MutableEntryExt, 'MutableEntryExt', VOID, VOID, [MutableEntry]);
  initMetadataForClass(FastRandom, 'FastRandom', FastRandom_init_$Create$_1, Random);
  initMetadataForCompanion(Companion_24, FastRandom);
  initMetadataForInterface(WithParent, 'WithParent');
  initMetadataForClass(Computed, 'Computed');
  initMetadataForInterface(Extra, 'Extra');
  initMetadataForClass(Mixin, 'Mixin', Mixin, VOID, [Extra]);
  initMetadataForCompanion(Companion_25);
  initMetadataForClass(Property, 'Property');
  initMetadataForClass(PropertyThis, 'PropertyThis');
  initMetadataForClass(ExtraObject, 'ExtraObject', ExtraObject, VOID, [MutableMap]);
  initMetadataForClass(WeakProperty, 'WeakProperty');
  initMetadataForClass(WeakPropertyThis, 'WeakPropertyThis');
  initMetadataForClass(BSearchResult, 'BSearchResult');
  initMetadataForClass(sam$kotlin_Comparator$0_1, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForObject(SortOpsComparable, 'SortOpsComparable', VOID, SortOps);
  initMetadataForObject(Math_0, 'Math');
  initMetadataForObject(Memory, 'Memory');
  initMetadataForClass(FastArrayList, 'FastArrayList', FastArrayList_init_$Create$, AbstractMutableList, [AbstractMutableList, MutableListEx, RandomAccess]);
  initMetadataForClass(FastIdentityMap, 'FastIdentityMap');
  initMetadataForClass(FastIntMap, 'FastIntMap');
  initMetadataForClass(FastStringMap, 'FastStringMap');
  initMetadataForClass(WeakMap_0, 'WeakMap', WeakMap_0);
  //endregion
  function Companion() {
    Companion_instance = this;
  }
  protoOf(Companion).invoke_abanw9_k$ = function (width, height, fill) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_1 = fillArrayVal(Array(tmp_0), null);
    while (tmp < tmp_0) {
      tmp_1[tmp] = fill;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    return new Array2(width, height, isArray(tmp_2) ? tmp_2 : THROW_CCE());
  };
  protoOf(Companion).invoke_7ry2hq_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_1 = fillArrayVal(Array(tmp_0), null);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new Array2(width, height, isArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion).withGen_foqa83_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_1 = fillArrayVal(Array(tmp_0), null);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2 % width | 0, tmp_2 / width | 0);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new Array2(width, height, isArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion).invoke_5mwosr_k$ = function (rows) {
    var width = rows.get_c1px32_k$(0).get_size_woubt6_k$();
    var height = rows.get_size_woubt6_k$();
    var anyCell = rows.get_c1px32_k$(0).get_c1px32_k$(0);
    // Inline function 'kotlin.apply' call
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance();
    var tmp = 0;
    var tmp_0 = imul(width, height);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_1 = fillArrayVal(Array(tmp_0), null);
    while (tmp < tmp_0) {
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      tmp_1[tmp] = anyCell;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    var this_0 = new Array2(width, height, isArray(tmp_2) ? tmp_2 : THROW_CCE());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    this_0.set_pxv0t5_k$(rows);
    return this_0;
  };
  protoOf(Companion).invoke_eg9kj3_k$ = function (map, marginChar, gen) {
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_2 = fillArrayVal(Array(tmp_1), null);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      var tmp0_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp0_safe_receiver == null ? null : getOrNull_0(tmp0_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      tmp_2[tmp_3] = gen(new Char(tmp_4), x, y);
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new Array2(width, height, isArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion).invoke_nthpyt_k$ = function (map, default_0, transform) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    var marginChar = _Char___init__impl__6a9atx(0);
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_2 = fillArrayVal(Array(tmp_1), null);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp0_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp0_safe_receiver == null ? null : getOrNull_0(tmp0_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = transform.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new Array2(width, height, isArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion).fromString_ov2vgp_k$ = function (maps, default_0, code, marginChar) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(code);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_2 = fillArrayVal(Array(tmp_1), null);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.fromString.<anonymous>' call
      var tmp0_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp0_safe_receiver == null ? null : getOrNull_0(tmp0_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = maps.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new Array2(width, height, isArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  var Companion_instance;
  function Companion_getInstance() {
    if (Companion_instance == null)
      new Companion();
    return Companion_instance;
  }
  function Array2(width, height, data) {
    Companion_getInstance();
    this.width_1 = width;
    this.height_1 = height;
    this.data_1 = data;
    Companion_getInstance_8().checkArraySize_6zyia2_k$(this.width_1, this.height_1, this.data_1.length);
  }
  protoOf(Array2).get_width_j0q4yl_k$ = function () {
    return this.width_1;
  };
  protoOf(Array2).get_height_e7t92o_k$ = function () {
    return this.height_1;
  };
  protoOf(Array2).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(Array2).setAt_h8dyhw_k$ = function (idx, value) {
    this.data_1[idx] = value;
  };
  protoOf(Array2).setAt_tlf2vp_k$ = function (idx, value) {
    return this.setAt_h8dyhw_k$(idx, (value == null ? true : !(value == null)) ? value : THROW_CCE());
  };
  protoOf(Array2).printAt_mw6ng8_k$ = function (idx) {
    print(this.data_1[idx]);
  };
  protoOf(Array2).equalsAt_7du3an_k$ = function (idx, value) {
    var tmp1_safe_receiver = this.data_1[idx];
    var tmp0_elvis_lhs = tmp1_safe_receiver == null ? null : equals(tmp1_safe_receiver, value);
    return tmp0_elvis_lhs == null ? false : tmp0_elvis_lhs;
  };
  protoOf(Array2).equalsAt_31cqaw_k$ = function (idx, value) {
    return this.equalsAt_7du3an_k$(idx, (value == null ? true : !(value == null)) ? value : THROW_CCE());
  };
  protoOf(Array2).getAt_k8n1td_k$ = function (idx) {
    return this.data_1[idx];
  };
  protoOf(Array2).equals = function (other) {
    var tmp;
    var tmp_0;
    var tmp_1;
    if (other instanceof Array2) {
      tmp_1 = this.width_1 === other.width_1;
    } else {
      tmp_1 = false;
    }
    if (tmp_1) {
      tmp_0 = this.height_1 === other.height_1;
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = contentEquals(this.data_1, other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(Array2).get_bzg6vq_k$ = function (x, y) {
    return this.data_1[index(this, x, y)];
  };
  protoOf(Array2).set_vf9317_k$ = function (x, y, value) {
    this.data_1[index(this, x, y)] = value;
  };
  protoOf(Array2).tryGet_wlchwl_k$ = function (x, y) {
    return this.inside_c6756s_k$(x, y) ? this.data_1[index(this, x, y)] : null;
  };
  protoOf(Array2).trySet_rjckn4_k$ = function (x, y, value) {
    if (this.inside_c6756s_k$(x, y)) {
      this.data_1[index(this, x, y)] = value;
    }
  };
  protoOf(Array2).hashCode = function () {
    return (this.width_1 + this.height_1 | 0) + contentHashCode(this.data_1) | 0;
  };
  protoOf(Array2).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1.slice();
    return new Array2(this.width_1, this.height_1, tmp$ret$1);
  };
  protoOf(Array2).iterator_jk1svi_k$ = function () {
    return arrayIterator(this.data_1);
  };
  protoOf(Array2).toString = function () {
    return this.asString_l3f38d_k$();
  };
  protoOf(Array2).component1_7eebsc_k$ = function () {
    return this.width_1;
  };
  protoOf(Array2).component2_7eebsb_k$ = function () {
    return this.height_1;
  };
  protoOf(Array2).component3_7eebsa_k$ = function () {
    return this.data_1;
  };
  protoOf(Array2).copy_h67wqv_k$ = function (width, height, data) {
    return new Array2(width, height, data);
  };
  protoOf(Array2).copy$default_w9z5rg_k$ = function (width, height, data, $super) {
    width = width === VOID ? this.width_1 : width;
    height = height === VOID ? this.height_1 : height;
    data = data === VOID ? this.data_1 : data;
    return $super === VOID ? this.copy_h67wqv_k$(width, height, data) : $super.copy_h67wqv_k$.call(this, width, height, data);
  };
  function Companion_0() {
    Companion_instance_0 = this;
  }
  protoOf(Companion_0).invoke_jr8a9s_k$ = function (width, height, fill) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float32Array(tmp_0);
    while (tmp < tmp_0) {
      tmp_1[tmp] = fill;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    return new FloatArray2(width, height, isFloatArray(tmp_2) ? tmp_2 : THROW_CCE());
  };
  protoOf(Companion_0).invoke_z4fe5k_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float32Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new FloatArray2(width, height, isFloatArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion_0).withGen_nr6mx9_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float32Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2 % width | 0, tmp_2 / width | 0);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new FloatArray2(width, height, isFloatArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion_0).invoke_2z2ne7_k$ = function (rows) {
    var width = rows.get_c1px32_k$(0).get_size_woubt6_k$();
    var height = rows.get_size_woubt6_k$();
    var anyCell = rows.get_c1px32_k$(0).get_c1px32_k$(0);
    // Inline function 'kotlin.apply' call
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float32Array(tmp_0);
    while (tmp < tmp_0) {
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      tmp_1[tmp] = anyCell;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    var this_0 = new FloatArray2(width, height, isFloatArray(tmp_2) ? tmp_2 : THROW_CCE());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    this_0.set_pxv0t5_k$(rows);
    return this_0;
  };
  protoOf(Companion_0).invoke_81m6uz_k$ = function (map, marginChar, gen) {
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Float32Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      var tmp6_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp6_safe_receiver == null ? null : getOrNull_0(tmp6_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      tmp_2[tmp_3] = gen(new Char(tmp_4), x, y);
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new FloatArray2(width, height, isFloatArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion_0).invoke_bl1jsr_k$ = function (map, default_0, transform) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    var marginChar = _Char___init__impl__6a9atx(0);
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Float32Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp6_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp6_safe_receiver == null ? null : getOrNull_0(tmp6_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = transform.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new FloatArray2(width, height, isFloatArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion_0).fromString_m211md_k$ = function (maps, default_0, code, marginChar) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(code);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Float32Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.fromString.<anonymous>' call
      var tmp6_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp6_safe_receiver == null ? null : getOrNull_0(tmp6_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = maps.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new FloatArray2(width, height, isFloatArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  var Companion_instance_0;
  function Companion_getInstance_0() {
    if (Companion_instance_0 == null)
      new Companion_0();
    return Companion_instance_0;
  }
  function FloatArray2(width, height, data) {
    Companion_getInstance_0();
    this.width_1 = width;
    this.height_1 = height;
    this.data_1 = data;
    Companion_getInstance_8().checkArraySize_6zyia2_k$(this.width_1, this.height_1, this.data_1.length);
  }
  protoOf(FloatArray2).get_width_j0q4yl_k$ = function () {
    return this.width_1;
  };
  protoOf(FloatArray2).get_height_e7t92o_k$ = function () {
    return this.height_1;
  };
  protoOf(FloatArray2).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(FloatArray2).setAt_s1uke3_k$ = function (idx, value) {
    this.data_1[idx] = value;
  };
  protoOf(FloatArray2).setAt_tlf2vp_k$ = function (idx, value) {
    return this.setAt_s1uke3_k$(idx, (!(value == null) ? typeof value === 'number' : false) ? value : THROW_CCE());
  };
  protoOf(FloatArray2).printAt_mw6ng8_k$ = function (idx) {
    print(this.data_1[idx]);
  };
  protoOf(FloatArray2).equalsAt_w6ph4i_k$ = function (idx, value) {
    var tmp7_safe_receiver = this.data_1[idx];
    var tmp0_elvis_lhs = tmp7_safe_receiver == null ? null : equals(tmp7_safe_receiver, value);
    return tmp0_elvis_lhs == null ? false : tmp0_elvis_lhs;
  };
  protoOf(FloatArray2).equalsAt_31cqaw_k$ = function (idx, value) {
    return this.equalsAt_w6ph4i_k$(idx, (!(value == null) ? typeof value === 'number' : false) ? value : THROW_CCE());
  };
  protoOf(FloatArray2).getAt_k8n1td_k$ = function (idx) {
    return this.data_1[idx];
  };
  protoOf(FloatArray2).equals = function (other) {
    var tmp;
    var tmp_0;
    var tmp_1;
    if (other instanceof FloatArray2) {
      tmp_1 = this.width_1 === other.width_1;
    } else {
      tmp_1 = false;
    }
    if (tmp_1) {
      tmp_0 = this.height_1 === other.height_1;
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = contentEquals_0(this.data_1, other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(FloatArray2).get_bzg6vq_k$ = function (x, y) {
    return this.data_1[index(this, x, y)];
  };
  protoOf(FloatArray2).set_hk1aii_k$ = function (x, y, value) {
    this.data_1[index(this, x, y)] = value;
  };
  protoOf(FloatArray2).tryGet_wlchwl_k$ = function (x, y) {
    return this.inside_c6756s_k$(x, y) ? this.data_1[index(this, x, y)] : null;
  };
  protoOf(FloatArray2).trySet_n4qc4h_k$ = function (x, y, value) {
    if (this.inside_c6756s_k$(x, y)) {
      this.data_1[index(this, x, y)] = value;
    }
  };
  protoOf(FloatArray2).hashCode = function () {
    return (this.width_1 + this.height_1 | 0) + contentHashCode_0(this.data_1) | 0;
  };
  protoOf(FloatArray2).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1.slice();
    return new FloatArray2(this.width_1, this.height_1, tmp$ret$1);
  };
  protoOf(FloatArray2).iterator_jk1svi_k$ = function () {
    return floatArrayIterator(this.data_1);
  };
  protoOf(FloatArray2).toString = function () {
    return this.asString_l3f38d_k$();
  };
  protoOf(FloatArray2).component1_7eebsc_k$ = function () {
    return this.width_1;
  };
  protoOf(FloatArray2).component2_7eebsb_k$ = function () {
    return this.height_1;
  };
  protoOf(FloatArray2).component3_7eebsa_k$ = function () {
    return this.data_1;
  };
  protoOf(FloatArray2).copy_qzjwu5_k$ = function (width, height, data) {
    return new FloatArray2(width, height, data);
  };
  protoOf(FloatArray2).copy$default_fq8qer_k$ = function (width, height, data, $super) {
    width = width === VOID ? this.width_1 : width;
    height = height === VOID ? this.height_1 : height;
    data = data === VOID ? this.data_1 : data;
    return $super === VOID ? this.copy_qzjwu5_k$(width, height, data) : $super.copy_qzjwu5_k$.call(this, width, height, data);
  };
  function Companion_1() {
    Companion_instance_1 = this;
  }
  protoOf(Companion_1).invoke_rugahs_k$ = function (width, height, fill) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Int32Array(tmp_0);
    while (tmp < tmp_0) {
      tmp_1[tmp] = fill;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    return new IntArray2(width, height, isIntArray(tmp_2) ? tmp_2 : THROW_CCE());
  };
  protoOf(Companion_1).invoke_wftv1n_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Int32Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new IntArray2(width, height, isIntArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion_1).withGen_w2y8ee_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Int32Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2 % width | 0, tmp_2 / width | 0);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new IntArray2(width, height, isIntArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion_1).invoke_kq9xwi_k$ = function (rows) {
    var width = rows.get_c1px32_k$(0).get_size_woubt6_k$();
    var height = rows.get_size_woubt6_k$();
    var anyCell = rows.get_c1px32_k$(0).get_c1px32_k$(0);
    // Inline function 'kotlin.apply' call
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_1();
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Int32Array(tmp_0);
    while (tmp < tmp_0) {
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      tmp_1[tmp] = anyCell;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    var this_0 = new IntArray2(width, height, isIntArray(tmp_2) ? tmp_2 : THROW_CCE());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    this_0.set_pxv0t5_k$(rows);
    return this_0;
  };
  protoOf(Companion_1).invoke_xiq4he_k$ = function (map, marginChar, gen) {
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_1();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Int32Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      var tmp2_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp2_safe_receiver == null ? null : getOrNull_0(tmp2_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      tmp_2[tmp_3] = gen(new Char(tmp_4), x, y);
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new IntArray2(width, height, isIntArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion_1).invoke_nes44u_k$ = function (map, default_0, transform) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    var marginChar = _Char___init__impl__6a9atx(0);
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_1();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Int32Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp2_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp2_safe_receiver == null ? null : getOrNull_0(tmp2_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = transform.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new IntArray2(width, height, isIntArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion_1).fromString_casqyw_k$ = function (maps, default_0, code, marginChar) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(code);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_1();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Int32Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.fromString.<anonymous>' call
      var tmp2_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp2_safe_receiver == null ? null : getOrNull_0(tmp2_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = maps.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new IntArray2(width, height, isIntArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  var Companion_instance_1;
  function Companion_getInstance_1() {
    if (Companion_instance_1 == null)
      new Companion_1();
    return Companion_instance_1;
  }
  function IntArray2(width, height, data) {
    Companion_getInstance_1();
    this.width_1 = width;
    this.height_1 = height;
    this.data_1 = data;
    Companion_getInstance_8().checkArraySize_6zyia2_k$(this.width_1, this.height_1, this.data_1.length);
  }
  protoOf(IntArray2).get_width_j0q4yl_k$ = function () {
    return this.width_1;
  };
  protoOf(IntArray2).get_height_e7t92o_k$ = function () {
    return this.height_1;
  };
  protoOf(IntArray2).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(IntArray2).setAt_kft8wr_k$ = function (idx, value) {
    this.data_1[idx] = value;
  };
  protoOf(IntArray2).setAt_tlf2vp_k$ = function (idx, value) {
    return this.setAt_kft8wr_k$(idx, (!(value == null) ? typeof value === 'number' : false) ? value : THROW_CCE());
  };
  protoOf(IntArray2).printAt_mw6ng8_k$ = function (idx) {
    print(this.data_1[idx]);
  };
  protoOf(IntArray2).equalsAt_kl0u9a_k$ = function (idx, value) {
    var tmp3_safe_receiver = this.data_1[idx];
    var tmp0_elvis_lhs = tmp3_safe_receiver == null ? null : equals(tmp3_safe_receiver, value);
    return tmp0_elvis_lhs == null ? false : tmp0_elvis_lhs;
  };
  protoOf(IntArray2).equalsAt_31cqaw_k$ = function (idx, value) {
    return this.equalsAt_kl0u9a_k$(idx, (!(value == null) ? typeof value === 'number' : false) ? value : THROW_CCE());
  };
  protoOf(IntArray2).getAt_k8n1td_k$ = function (idx) {
    return this.data_1[idx];
  };
  protoOf(IntArray2).equals = function (other) {
    var tmp;
    var tmp_0;
    var tmp_1;
    if (other instanceof IntArray2) {
      tmp_1 = this.width_1 === other.width_1;
    } else {
      tmp_1 = false;
    }
    if (tmp_1) {
      tmp_0 = this.height_1 === other.height_1;
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = contentEquals_1(this.data_1, other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(IntArray2).get_bzg6vq_k$ = function (x, y) {
    return this.data_1[index(this, x, y)];
  };
  protoOf(IntArray2).set_7qmx5y_k$ = function (x, y, value) {
    this.data_1[index(this, x, y)] = value;
  };
  protoOf(IntArray2).tryGet_wlchwl_k$ = function (x, y) {
    return this.inside_c6756s_k$(x, y) ? this.data_1[index(this, x, y)] : null;
  };
  protoOf(IntArray2).trySet_jqgwqn_k$ = function (x, y, value) {
    if (this.inside_c6756s_k$(x, y)) {
      this.data_1[index(this, x, y)] = value;
    }
  };
  protoOf(IntArray2).hashCode = function () {
    return (this.width_1 + this.height_1 | 0) + contentHashCode_1(this.data_1) | 0;
  };
  protoOf(IntArray2).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1.slice();
    return new IntArray2(this.width_1, this.height_1, tmp$ret$1);
  };
  protoOf(IntArray2).iterator_jk1svi_k$ = function () {
    return intArrayIterator(this.data_1);
  };
  protoOf(IntArray2).toString = function () {
    return this.asString_l3f38d_k$();
  };
  protoOf(IntArray2).component1_7eebsc_k$ = function () {
    return this.width_1;
  };
  protoOf(IntArray2).component2_7eebsb_k$ = function () {
    return this.height_1;
  };
  protoOf(IntArray2).component3_7eebsa_k$ = function () {
    return this.data_1;
  };
  protoOf(IntArray2).copy_4zhvl6_k$ = function (width, height, data) {
    return new IntArray2(width, height, data);
  };
  protoOf(IntArray2).copy$default_b0l8bk_k$ = function (width, height, data, $super) {
    width = width === VOID ? this.width_1 : width;
    height = height === VOID ? this.height_1 : height;
    data = data === VOID ? this.data_1 : data;
    return $super === VOID ? this.copy_4zhvl6_k$(width, height, data) : $super.copy_4zhvl6_k$.call(this, width, height, data);
  };
  function Companion_2() {
    Companion_instance_2 = this;
  }
  protoOf(Companion_2).invoke_uqcjy_k$ = function (width, height, fill) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      tmp_1[tmp] = fill;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    return new DoubleArray2(width, height, isDoubleArray(tmp_2) ? tmp_2 : THROW_CCE());
  };
  protoOf(Companion_2).invoke_s793cp_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new DoubleArray2(width, height, isDoubleArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion_2).withGen_uy9x2s_k$ = function (width, height, gen) {
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = gen(tmp_2 % width | 0, tmp_2 / width | 0);
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    return new DoubleArray2(width, height, isDoubleArray(tmp_3) ? tmp_3 : THROW_CCE());
  };
  protoOf(Companion_2).invoke_7g4szc_k$ = function (rows) {
    var width = rows.get_c1px32_k$(0).get_size_woubt6_k$();
    var height = rows.get_size_woubt6_k$();
    var anyCell = rows.get_c1px32_k$(0).get_c1px32_k$(0);
    // Inline function 'kotlin.apply' call
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_2();
    var tmp = 0;
    var tmp_0 = imul(width, height);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      tmp_1[tmp] = anyCell;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    var this_0 = new DoubleArray2(width, height, isDoubleArray(tmp_2) ? tmp_2 : THROW_CCE());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    this_0.set_pxv0t5_k$(rows);
    return this_0;
  };
  protoOf(Companion_2).invoke_vg30jo_k$ = function (map, marginChar, gen) {
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_2();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Float64Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      var tmp4_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp4_safe_receiver == null ? null : getOrNull_0(tmp4_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      tmp_2[tmp_3] = gen(new Char(tmp_4), x, y);
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new DoubleArray2(width, height, isDoubleArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion_2).invoke_a2p7zi_k$ = function (map, default_0, transform) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    var marginChar = _Char___init__impl__6a9atx(0);
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(map);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_2();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Float64Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp4_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp4_safe_receiver == null ? null : getOrNull_0(tmp4_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = transform.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new DoubleArray2(width, height, isDoubleArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  protoOf(Companion_2).fromString_tz0faa_k$ = function (maps, default_0, code, marginChar) {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = lines(code);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.trim' call
      var res = toString(trim(isCharSequence(item) ? item : THROW_CCE()));
      var tmp;
      if (startsWith(res, marginChar)) {
        // Inline function 'kotlin.text.substring' call
        var endIndex = res.length;
        // Inline function 'kotlin.js.asDynamic' call
        tmp = res.substring(0, endIndex);
      } else {
        tmp = res;
      }
      var tmp$ret$3 = tmp;
      destination.add_utx5q5_k$(tmp$ret$3);
    }
    // Inline function 'kotlin.collections.filterTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    var tmp0_iterator_0 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      // Inline function 'kotlin.text.isNotEmpty' call
      if (charSequenceLength(element) > 0) {
        destination_0.add_utx5q5_k$(element);
      }
    }
    var lines_0 = destination_0;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(lines_0, 10));
    var tmp0_iterator_1 = lines_0.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var tmp$ret$10 = item_0.length;
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp0_elvis_lhs = maxOrNull(destination_1);
    var width = tmp0_elvis_lhs == null ? 0 : tmp0_elvis_lhs;
    var height = lines_0.get_size_woubt6_k$();
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_2();
    var tmp_0 = 0;
    var tmp_1 = imul(width, height);
    var tmp_2 = new Float64Array(tmp_1);
    while (tmp_0 < tmp_1) {
      var tmp_3 = tmp_0;
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      var x = tmp_3 % width | 0;
      var y = tmp_3 / width | 0;
      // Inline function 'korlibs.datastructure.Companion.fromString.<anonymous>' call
      var tmp4_safe_receiver = getOrNull(lines_0, y);
      var tmp0_elvis_lhs_0 = tmp4_safe_receiver == null ? null : getOrNull_0(tmp4_safe_receiver, x);
      var tmp_4;
      var tmp_5 = tmp0_elvis_lhs_0;
      if ((tmp_5 == null ? null : new Char(tmp_5)) == null) {
        tmp_4 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_4 = tmp0_elvis_lhs_0;
      }
      var c = tmp_4;
      var tmp0_elvis_lhs_1 = maps.get_wei43m_k$(new Char(c));
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? default_0 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    return new DoubleArray2(width, height, isDoubleArray(tmp_6) ? tmp_6 : THROW_CCE());
  };
  var Companion_instance_2;
  function Companion_getInstance_2() {
    if (Companion_instance_2 == null)
      new Companion_2();
    return Companion_instance_2;
  }
  function DoubleArray2(width, height, data) {
    Companion_getInstance_2();
    this.width_1 = width;
    this.height_1 = height;
    this.data_1 = data;
    Companion_getInstance_8().checkArraySize_6zyia2_k$(this.width_1, this.height_1, this.data_1.length);
  }
  protoOf(DoubleArray2).get_width_j0q4yl_k$ = function () {
    return this.width_1;
  };
  protoOf(DoubleArray2).get_height_e7t92o_k$ = function () {
    return this.height_1;
  };
  protoOf(DoubleArray2).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(DoubleArray2).setAt_x27sib_k$ = function (idx, value) {
    this.data_1[idx] = value;
  };
  protoOf(DoubleArray2).setAt_tlf2vp_k$ = function (idx, value) {
    return this.setAt_x27sib_k$(idx, (!(value == null) ? typeof value === 'number' : false) ? value : THROW_CCE());
  };
  protoOf(DoubleArray2).printAt_mw6ng8_k$ = function (idx) {
    print(this.data_1[idx]);
  };
  protoOf(DoubleArray2).equalsAt_ctp2tc_k$ = function (idx, value) {
    var tmp5_safe_receiver = this.data_1[idx];
    var tmp0_elvis_lhs = tmp5_safe_receiver == null ? null : equals(tmp5_safe_receiver, value);
    return tmp0_elvis_lhs == null ? false : tmp0_elvis_lhs;
  };
  protoOf(DoubleArray2).equalsAt_31cqaw_k$ = function (idx, value) {
    return this.equalsAt_ctp2tc_k$(idx, (!(value == null) ? typeof value === 'number' : false) ? value : THROW_CCE());
  };
  protoOf(DoubleArray2).getAt_k8n1td_k$ = function (idx) {
    return this.data_1[idx];
  };
  protoOf(DoubleArray2).equals = function (other) {
    var tmp;
    var tmp_0;
    var tmp_1;
    if (other instanceof DoubleArray2) {
      tmp_1 = this.width_1 === other.width_1;
    } else {
      tmp_1 = false;
    }
    if (tmp_1) {
      tmp_0 = this.height_1 === other.height_1;
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = contentEquals_2(this.data_1, other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(DoubleArray2).get_bzg6vq_k$ = function (x, y) {
    return this.data_1[index(this, x, y)];
  };
  protoOf(DoubleArray2).set_w6oo44_k$ = function (x, y, value) {
    this.data_1[index(this, x, y)] = value;
  };
  protoOf(DoubleArray2).tryGet_wlchwl_k$ = function (x, y) {
    return this.inside_c6756s_k$(x, y) ? this.data_1[index(this, x, y)] : null;
  };
  protoOf(DoubleArray2).trySet_4ou91t_k$ = function (x, y, value) {
    if (this.inside_c6756s_k$(x, y)) {
      this.data_1[index(this, x, y)] = value;
    }
  };
  protoOf(DoubleArray2).hashCode = function () {
    return (this.width_1 + this.height_1 | 0) + contentHashCode_2(this.data_1) | 0;
  };
  protoOf(DoubleArray2).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1.slice();
    return new DoubleArray2(this.width_1, this.height_1, tmp$ret$1);
  };
  protoOf(DoubleArray2).iterator_jk1svi_k$ = function () {
    return doubleArrayIterator(this.data_1);
  };
  protoOf(DoubleArray2).toString = function () {
    return this.asString_l3f38d_k$();
  };
  protoOf(DoubleArray2).component1_7eebsc_k$ = function () {
    return this.width_1;
  };
  protoOf(DoubleArray2).component2_7eebsb_k$ = function () {
    return this.height_1;
  };
  protoOf(DoubleArray2).component3_7eebsa_k$ = function () {
    return this.data_1;
  };
  protoOf(DoubleArray2).copy_rutazm_k$ = function (width, height, data) {
    return new DoubleArray2(width, height, data);
  };
  protoOf(DoubleArray2).copy$default_s8p9t0_k$ = function (width, height, data, $super) {
    width = width === VOID ? this.width_1 : width;
    height = height === VOID ? this.height_1 : height;
    data = data === VOID ? this.data_1 : data;
    return $super === VOID ? this.copy_rutazm_k$(width, height, data) : $super.copy_rutazm_k$.call(this, width, height, data);
  };
  function _set_ring__9tcg87($this, _set____db54di) {
    $this.ring_1 = _set____db54di;
  }
  function _get_ring__dd4ftn($this) {
    return $this.ring_1;
  }
  function _get_tempBuffer__u548nr($this) {
    return $this.tempBuffer_1;
  }
  function _set_written__u8xiry($this, _set____db54di) {
    $this.written_1 = _set____db54di;
  }
  function _set_read__9t9mkh($this, _set____db54di) {
    $this.read_1 = _set____db54di;
  }
  function ensureWrite($this, count) {
    if (count <= $this.ring_1.get_availableWrite_qb4gx_k$())
      return Unit_getInstance();
    if (!$this.allowGrow_1) {
      var message = "Can't grow ByteArrayDeque. Need to write " + count + ', but only ' + $this.ring_1.get_availableWrite_qb4gx_k$() + ' is available';
      println('ERROR: ' + message);
      // Inline function 'kotlin.error' call
      throw IllegalStateException_init_$Create$(toString(message));
    }
    var minNewSize = $this.ring_1.get_availableRead_tre4t2_k$() + count | 0;
    var tmp = $this;
    // Inline function 'kotlin.also' call
    var this_0 = new RingBuffer(Math_getInstance().ilog2_j1avof_k$(minNewSize) + 2 | 0);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ByteArrayDeque.ensureWrite.<anonymous>' call
    this_0.write_b9o6iq_k$($this.ring_1);
    tmp.ring_1 = this_0;
  }
  function ByteArrayDeque(initialBits, allowGrow) {
    initialBits = initialBits === VOID ? 10 : initialBits;
    allowGrow = allowGrow === VOID ? true : allowGrow;
    this.initialBits_1 = initialBits;
    this.allowGrow_1 = allowGrow;
    this.ring_1 = new RingBuffer(this.initialBits_1);
    this.tempBuffer_1 = new Int8Array(1024);
    this.written_1 = new Long(0, 0);
    this.read_1 = new Long(0, 0);
  }
  protoOf(ByteArrayDeque).get_initialBits_ya7c3l_k$ = function () {
    return this.initialBits_1;
  };
  protoOf(ByteArrayDeque).get_allowGrow_ensfo3_k$ = function () {
    return this.allowGrow_1;
  };
  protoOf(ByteArrayDeque).get_written_rry7fy_k$ = function () {
    return this.written_1;
  };
  protoOf(ByteArrayDeque).get_read_wotl9b_k$ = function () {
    return this.read_1;
  };
  protoOf(ByteArrayDeque).get_availableWriteWithoutAllocating_m040u5_k$ = function () {
    return this.ring_1.get_availableWrite_qb4gx_k$();
  };
  protoOf(ByteArrayDeque).get_availableRead_tre4t2_k$ = function () {
    return this.ring_1.get_availableRead_tre4t2_k$();
  };
  protoOf(ByteArrayDeque).get_bufferSize_bco2y2_k$ = function () {
    return this.ring_1.get_totalSize_116z44_k$();
  };
  protoOf(ByteArrayDeque).writeHead_fjpvnj_k$ = function (buffer, offset, size) {
    ensureWrite(this, size);
    var out = this.ring_1.writeHead_fjpvnj_k$(buffer, offset, size);
    if (out > 0) {
      var tmp = this;
      // Inline function 'kotlin.Long.plus' call
      tmp.written_1 = this.written_1.plus_r93sks_k$(toLong(out));
    }
    return out;
  };
  protoOf(ByteArrayDeque).writeHead$default_mr1qdd_k$ = function (buffer, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? buffer.length - offset | 0 : size;
    return $super === VOID ? this.writeHead_fjpvnj_k$(buffer, offset, size) : $super.writeHead_fjpvnj_k$.call(this, buffer, offset, size);
  };
  protoOf(ByteArrayDeque).write_owzzlt_k$ = function (buffer, offset, size) {
    ensureWrite(this, size);
    var out = this.ring_1.write_owzzlt_k$(buffer, offset, size);
    if (out > 0) {
      var tmp = this;
      // Inline function 'kotlin.Long.plus' call
      tmp.written_1 = this.written_1.plus_r93sks_k$(toLong(out));
    }
    return out;
  };
  protoOf(ByteArrayDeque).write$default_81zfb5_k$ = function (buffer, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? buffer.length - offset | 0 : size;
    return $super === VOID ? this.write_owzzlt_k$(buffer, offset, size) : $super.write_owzzlt_k$.call(this, buffer, offset, size);
  };
  protoOf(ByteArrayDeque).read_7zpyie_k$ = function (buffer, offset, size) {
    var out = this.ring_1.read_7zpyie_k$(buffer, offset, size);
    if (out > 0) {
      var tmp = this;
      // Inline function 'kotlin.Long.plus' call
      tmp.read_1 = this.read_1.plus_r93sks_k$(toLong(out));
    }
    return out;
  };
  protoOf(ByteArrayDeque).read$default_dy7kkq_k$ = function (buffer, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? buffer.length - offset | 0 : size;
    return $super === VOID ? this.read_7zpyie_k$(buffer, offset, size) : $super.read_7zpyie_k$.call(this, buffer, offset, size);
  };
  protoOf(ByteArrayDeque).skip_7luint_k$ = function (count) {
    return this.ring_1.skip_7luint_k$(count);
  };
  protoOf(ByteArrayDeque).peek_1y1zlx_k$ = function (buffer, offset, size) {
    return this.ring_1.peek_1y1zlx_k$(buffer, offset, size);
  };
  protoOf(ByteArrayDeque).peek$default_kcanqz_k$ = function (buffer, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? buffer.length - offset | 0 : size;
    return $super === VOID ? this.peek_1y1zlx_k$(buffer, offset, size) : $super.peek_1y1zlx_k$.call(this, buffer, offset, size);
  };
  protoOf(ByteArrayDeque).readByte_ectjk2_k$ = function () {
    return this.ring_1.readByte_ectjk2_k$();
  };
  protoOf(ByteArrayDeque).writeByte_3m2t4h_k$ = function (v) {
    ensureWrite(this, 1);
    return this.ring_1.writeByte_3m2t4h_k$(v);
  };
  protoOf(ByteArrayDeque).clear_j9egeb_k$ = function () {
    this.ring_1.clear_j9egeb_k$();
  };
  protoOf(ByteArrayDeque).get_hasMoreToWrite_7uuj1e_k$ = function () {
    return this.ring_1.get_availableWrite_qb4gx_k$() > 0;
  };
  protoOf(ByteArrayDeque).get_hasMoreToRead_b77tdj_k$ = function () {
    return this.ring_1.get_availableRead_tre4t2_k$() > 0;
  };
  protoOf(ByteArrayDeque).readOne_hv8hdc_k$ = function () {
    this.read_7zpyie_k$(this.tempBuffer_1, 0, 1);
    return this.tempBuffer_1[0];
  };
  protoOf(ByteArrayDeque).writeOne_v4cpnj_k$ = function (value) {
    this.tempBuffer_1[0] = value;
    this.write_owzzlt_k$(this.tempBuffer_1, 0, 1);
  };
  protoOf(ByteArrayDeque).hashCode = function () {
    return this.ring_1.contentHashCode_si71ak_k$();
  };
  protoOf(ByteArrayDeque).equals = function (other) {
    var tmp;
    if (other instanceof ByteArrayDeque) {
      tmp = this.ring_1.equals(other.ring_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  function swap(_this__u8e3s4, lIndex, rIndex) {
    var temp = _this__u8e3s4[lIndex];
    _this__u8e3s4[lIndex] = _this__u8e3s4[rIndex];
    _this__u8e3s4[rIndex] = temp;
  }
  function swap_0(_this__u8e3s4, lIndex, rIndex) {
    var temp = _this__u8e3s4.get_c1px32_k$(lIndex);
    _this__u8e3s4.set_82063s_k$(lIndex, _this__u8e3s4.get_c1px32_k$(rIndex));
    _this__u8e3s4.set_82063s_k$(rIndex, temp);
  }
  function rotatedRight(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight(this_0, offset);
    return this_0;
  }
  function rotatedLeft(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft(this_0, offset);
    return this_0;
  }
  function rotatedRight_0(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    var this_0 = toMutableList(_this__u8e3s4);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_0(this_0, offset);
    return this_0;
  }
  function rotatedRight_1(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_1(this_0, offset);
    return this_0;
  }
  function rotatedRight_2(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_2(this_0, offset);
    return this_0;
  }
  function rotatedRight_3(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    var this_0 = copyOf(_this__u8e3s4);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_3(this_0, offset);
    return this_0;
  }
  function rotatedRight_4(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_4(this_0, offset);
    return this_0;
  }
  function rotatedRight_5(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    var this_0 = copyOf_0(_this__u8e3s4);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_5(this_0, offset);
    return this_0;
  }
  function rotatedRight_6(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_6(this_0, offset);
    return this_0;
  }
  function rotatedRight_7(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedRight.<anonymous>' call
    rotateRight_7(this_0, offset);
    return this_0;
  }
  function rotatedLeft_0(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    var this_0 = toMutableList(_this__u8e3s4);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_0(this_0, offset);
    return this_0;
  }
  function rotatedLeft_1(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_1(this_0, offset);
    return this_0;
  }
  function rotatedLeft_2(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_2(this_0, offset);
    return this_0;
  }
  function rotatedLeft_3(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    var this_0 = copyOf(_this__u8e3s4);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_3(this_0, offset);
    return this_0;
  }
  function rotatedLeft_4(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_4(this_0, offset);
    return this_0;
  }
  function rotatedLeft_5(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    var this_0 = copyOf_0(_this__u8e3s4);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_5(this_0, offset);
    return this_0;
  }
  function rotatedLeft_6(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_6(this_0, offset);
    return this_0;
  }
  function rotatedLeft_7(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotatedLeft.<anonymous>' call
    rotateLeft_7(this_0, offset);
    return this_0;
  }
  function rotateRight(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateLeft(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight(_this__u8e3s4, -offset | 0);
  }
  function rotateRight_0(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.get_size_woubt6_k$();
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_7(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_7(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_7(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateRight_1(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_0(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_0(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_0(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateRight_2(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_1(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_1(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_1(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateRight_3(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_2(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_2(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_2(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateRight_4(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_3(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_3(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_3(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateRight_5(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_4(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_4(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_4(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateRight_6(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_5(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_5(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_5(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateRight_7(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure._rotateRight' call
      var size = _this__u8e3s4.length;
      var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
      if (offset_0 === 0) {
        tmp$ret$0 = Unit_getInstance();
        break $l$block;
      }
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.check' call
      // Inline function 'kotlin.contracts.contract' call
      if (!(1 <= offset_0 ? offset_0 < size : false)) {
        // Inline function 'kotlin.check.<anonymous>' call
        var message = 'Check failed.';
        throw IllegalStateException_init_$Create$(toString(message));
      }
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_6(_this__u8e3s4, 0, size);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_6(_this__u8e3s4, 0, offset_0);
      // Inline function 'korlibs.datastructure.rotateRight.<anonymous>' call
      reverse_6(_this__u8e3s4, offset_0, size);
    }
    return tmp$ret$0;
  }
  function rotateLeft_0(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_0(_this__u8e3s4, -offset | 0);
  }
  function rotateLeft_1(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_1(_this__u8e3s4, -offset | 0);
  }
  function rotateLeft_2(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_2(_this__u8e3s4, -offset | 0);
  }
  function rotateLeft_3(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_3(_this__u8e3s4, -offset | 0);
  }
  function rotateLeft_4(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_4(_this__u8e3s4, -offset | 0);
  }
  function rotateLeft_5(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_5(_this__u8e3s4, -offset | 0);
  }
  function rotateLeft_6(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_6(_this__u8e3s4, -offset | 0);
  }
  function rotateLeft_7(_this__u8e3s4, offset) {
    offset = offset === VOID ? 1 : offset;
    return rotateRight_7(_this__u8e3s4, -offset | 0);
  }
  function _rotateRight(size, offset, reverse) {
    var offset_0 = Math_getInstance().umod_uagn7p_k$(offset, size);
    if (offset_0 === 0)
      return Unit_getInstance();
    // Inline function 'kotlin.check' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.check' call
    // Inline function 'kotlin.contracts.contract' call
    if (!(1 <= offset_0 ? offset_0 < size : false)) {
      // Inline function 'kotlin.check.<anonymous>' call
      var message = 'Check failed.';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    reverse(0, size);
    reverse(0, offset_0);
    reverse(offset_0, size);
  }
  function reverse_7(_this__u8e3s4, fromIndex, toIndex) {
    if (fromIndex < 0 ? true : toIndex > _this__u8e3s4.get_size_woubt6_k$()) {
      throw IndexOutOfBoundsException_init_$Create$('fromIndex: ' + fromIndex + ', toIndex: ' + toIndex + ', size: ' + _this__u8e3s4.get_size_woubt6_k$());
    }
    if (fromIndex > toIndex) {
      throw IllegalArgumentException_init_$Create$('fromIndex: ' + fromIndex + ' > toIndex: ' + toIndex);
    }
    var midPoint = (fromIndex + toIndex | 0) / 2 | 0;
    if (fromIndex === midPoint)
      return Unit_getInstance();
    var reverseIndex = toIndex - 1 | 0;
    var inductionVariable = fromIndex;
    if (inductionVariable < midPoint)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = _this__u8e3s4.get_c1px32_k$(index);
        _this__u8e3s4.set_82063s_k$(index, _this__u8e3s4.get_c1px32_k$(reverseIndex));
        _this__u8e3s4.set_82063s_k$(reverseIndex, tmp);
        reverseIndex = reverseIndex - 1 | 0;
      }
       while (inductionVariable < midPoint);
  }
  function Companion_3() {
    Companion_instance_3 = this;
  }
  var Companion_instance_3;
  function Companion_getInstance_3() {
    if (Companion_instance_3 == null)
      new Companion_3();
    return Companion_instance_3;
  }
  function _set_data__9licbx($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _set_length__xo12bz($this, _set____db54di) {
    $this.length_1 = _set____db54di;
  }
  function _get_length__w7ahp7($this) {
    return $this.length_1;
  }
  function IntArrayList_init_$Init$(other, $this) {
    IntArrayList.call($this);
    $this.add_50s29d_k$(other);
    return $this;
  }
  function IntArrayList_init_$Create$(other) {
    return IntArrayList_init_$Init$(other, objectCreate(protoOf(IntArrayList)));
  }
  function IntArrayList_init_$Init$_0(other, $this) {
    IntArrayList.call($this);
    $this.add$default_su5b58_k$(other);
    return $this;
  }
  function IntArrayList_init_$Create$_0(other) {
    return IntArrayList_init_$Init$_0(other, objectCreate(protoOf(IntArrayList)));
  }
  function IntArrayList(capacity) {
    Companion_getInstance_3();
    capacity = capacity === VOID ? 7 : capacity;
    var tmp = this;
    var tmp_0 = new Int32Array(capacity);
    tmp.data_1 = isIntArray(tmp_0) ? tmp_0 : THROW_CCE();
    this.length_1 = 0;
  }
  protoOf(IntArrayList).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(IntArrayList).get_capacity_tq6ug8_k$ = function () {
    return this.data_1.length;
  };
  protoOf(IntArrayList).set_size_e2677a_k$ = function (value) {
    this.ensure_uwiv7a_k$(value);
    this.length_1 = value;
  };
  protoOf(IntArrayList).get_size_woubt6_k$ = function () {
    return this.length_1;
  };
  protoOf(IntArrayList).ensure_uwiv7a_k$ = function (count) {
    if ((this.length_1 + count | 0) > this.data_1.length) {
      var tmp = this;
      var tmp_0 = this.data_1;
      // Inline function 'kotlin.math.max' call
      var a = this.length_1 + count | 0;
      var b = imul(this.data_1.length, 3);
      var tmp$ret$0 = Math.max(a, b);
      var tmp_1 = copyOf_1(tmp_0, tmp$ret$0);
      tmp.data_1 = isIntArray(tmp_1) ? tmp_1 : THROW_CCE();
    }
  };
  protoOf(IntArrayList).clear_j9egeb_k$ = function () {
    this.length_1 = 0;
  };
  protoOf(IntArrayList).add_c9dakn_k$ = function (v0) {
    this.ensure_uwiv7a_k$(1);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
  };
  protoOf(IntArrayList).add_6jazo1_k$ = function (v0, v1) {
    this.ensure_uwiv7a_k$(2);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
  };
  protoOf(IntArrayList).add_krfrlz_k$ = function (v0, v1, v2) {
    this.ensure_uwiv7a_k$(3);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
  };
  protoOf(IntArrayList).add_ib6hjl_k$ = function (v0, v1, v2, v3) {
    this.ensure_uwiv7a_k$(4);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
  };
  protoOf(IntArrayList).add_ybbs8p_k$ = function (v0, v1, v2, v3, v4) {
    this.ensure_uwiv7a_k$(5);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
    var tmp_3 = this.data_1;
    var _unary__edvuaz_3 = this.length_1;
    this.length_1 = _unary__edvuaz_3 + 1 | 0;
    tmp_3[_unary__edvuaz_3] = v4;
  };
  protoOf(IntArrayList).add_m8t30x_k$ = function (v0, v1, v2, v3, v4, v5) {
    this.ensure_uwiv7a_k$(6);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
    var tmp_3 = this.data_1;
    var _unary__edvuaz_3 = this.length_1;
    this.length_1 = _unary__edvuaz_3 + 1 | 0;
    tmp_3[_unary__edvuaz_3] = v4;
    var tmp_4 = this.data_1;
    var _unary__edvuaz_4 = this.length_1;
    this.length_1 = _unary__edvuaz_4 + 1 | 0;
    tmp_4[_unary__edvuaz_4] = v5;
  };
  protoOf(IntArrayList).plusAssign_8mmvnl_k$ = function (value) {
    return this.add_c9dakn_k$(value);
  };
  protoOf(IntArrayList).plusAssign_tg7l76_k$ = function (value) {
    return this.add$default_su5b58_k$(value);
  };
  protoOf(IntArrayList).plusAssign_cbt9l_k$ = function (value) {
    return this.add_50s29d_k$(value);
  };
  protoOf(IntArrayList).plusAssign_7xvbv3_k$ = function (value) {
    return this.add_w9y0tl_k$(value);
  };
  protoOf(IntArrayList).add_yblziy_k$ = function (values, offset, length) {
    this.ensure_uwiv7a_k$(length);
    var tmp = Memory_getInstance();
    tmp.arraycopy_7oxh5s_k$(isIntArray(values) ? values : THROW_CCE(), offset, this.data_1, this.get_size_woubt6_k$(), length);
    this.set_size_e2677a_k$(this.get_size_woubt6_k$() + length | 0);
  };
  protoOf(IntArrayList).add$default_su5b58_k$ = function (values, offset, length, $super) {
    offset = offset === VOID ? 0 : offset;
    length = length === VOID ? values.length : length;
    var tmp;
    if ($super === VOID) {
      this.add_yblziy_k$(values, offset, length);
      tmp = Unit_getInstance();
    } else {
      tmp = $super.add_yblziy_k$.call(this, values, offset, length);
    }
    return tmp;
  };
  protoOf(IntArrayList).add_50s29d_k$ = function (values) {
    return this.add_yblziy_k$(values.data_1, 0, values.get_size_woubt6_k$());
  };
  protoOf(IntArrayList).add_w9y0tl_k$ = function (values) {
    var _iterator__ex2g4s = values.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var v = _iterator__ex2g4s.next_20eer_k$();
      this.add_c9dakn_k$(v);
    }
  };
  protoOf(IntArrayList).get_c1px32_k$ = function (index) {
    return this.data_1[index];
  };
  protoOf(IntArrayList).getAt_k8n1td_k$ = function (index) {
    return this.data_1[index];
  };
  protoOf(IntArrayList).setAt_nhlrij_k$ = function (index, value) {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntArrayList.setAt.<anonymous>' call
    this.set_g707pu_k$(index, value);
    return value;
  };
  protoOf(IntArrayList).set_g707pu_k$ = function (index, value) {
    if (index >= this.length_1) {
      this.ensure_uwiv7a_k$(index + 1 | 0);
      this.length_1 = index + 1 | 0;
    }
    this.data_1[index] = value;
  };
  protoOf(IntArrayList).iterator_jk1svi_k$ = function () {
    return this.listIterator_70e65o_k$(0);
  };
  protoOf(IntArrayList).contains_7q95ev_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.length_1;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.data_1[n] === element)
          return true;
      }
       while (inductionVariable < last);
    return false;
  };
  protoOf(IntArrayList).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.contains_7q95ev_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntArrayList).containsAll_tftgie_k$ = function (elements) {
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var e = _iterator__ex2g4s.next_20eer_k$();
      if (!this.contains_7q95ev_k$(e))
        return false;
    }
    return true;
  };
  protoOf(IntArrayList).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_tftgie_k$(elements);
  };
  protoOf(IntArrayList).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(IntArrayList).isNotEmpty_cjxbwk_k$ = function () {
    return !(this.get_size_woubt6_k$() === 0);
  };
  protoOf(IntArrayList).first_1m0hio_k$ = function () {
    var tmp;
    if (this.isEmpty_y1axqb_k$()) {
      throw IndexOutOfBoundsException_init_$Create$_0();
    } else {
      tmp = this.data_1[0];
    }
    return tmp;
  };
  protoOf(IntArrayList).last_1z1cm_k$ = function () {
    var tmp;
    if (this.isEmpty_y1axqb_k$()) {
      throw IndexOutOfBoundsException_init_$Create$_0();
    } else {
      tmp = this.data_1[this.length_1 - 1 | 0];
    }
    return tmp;
  };
  protoOf(IntArrayList).indexOf_hf89qp_k$ = function (value, start, end) {
    var inductionVariable = start;
    if (inductionVariable < end)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.data_1[n] === value)
          return n;
      }
       while (inductionVariable < end);
    return -1;
  };
  protoOf(IntArrayList).indexOf$default_hn7by7_k$ = function (value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? this.get_size_woubt6_k$() : end;
    return $super === VOID ? this.indexOf_hf89qp_k$(value, start, end) : $super.indexOf_hf89qp_k$.call(this, value, start, end);
  };
  protoOf(IntArrayList).lastIndexOf_ly1y1n_k$ = function (value, start, end) {
    var inductionVariable = end - 1 | 0;
    if (start <= inductionVariable)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        if (this.data_1[n] === value)
          return n;
      }
       while (!(n === start));
    return -1;
  };
  protoOf(IntArrayList).lastIndexOf$default_x6mif_k$ = function (value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? this.get_size_woubt6_k$() : end;
    return $super === VOID ? this.lastIndexOf_ly1y1n_k$(value, start, end) : $super.lastIndexOf_ly1y1n_k$.call(this, value, start, end);
  };
  protoOf(IntArrayList).insertAt_21d2vg_k$ = function (index, value) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntArrayList.insertAt.<anonymous>' call
    this.ensure_uwiv7a_k$(1);
    if (this.isNotEmpty_cjxbwk_k$()) {
      Memory_getInstance().arraycopy_7oxh5s_k$(this.data_1, index, this.data_1, index + 1 | 0, this.length_1 - index | 0);
    }
    this.data_1[index] = value;
    this.length_1 = this.length_1 + 1 | 0;
    return this;
  };
  protoOf(IntArrayList).insertAt_r9ihgf_k$ = function (index, value, start, end) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntArrayList.insertAt.<anonymous>' call
    var count = end - start | 0;
    this.ensure_uwiv7a_k$(count);
    if (this.isNotEmpty_cjxbwk_k$()) {
      Memory_getInstance().arraycopy_7oxh5s_k$(this.data_1, index, this.data_1, index + count | 0, this.length_1 - index | 0);
    }
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this.data_1[index + n | 0] = value[start + n | 0];
      }
       while (inductionVariable < count);
    this.length_1 = this.length_1 + count | 0;
    return this;
  };
  protoOf(IntArrayList).insertAt$default_sru7m3_k$ = function (index, value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? value.length : end;
    return $super === VOID ? this.insertAt_r9ihgf_k$(index, value, start, end) : $super.insertAt_r9ihgf_k$.call(this, index, value, start, end);
  };
  protoOf(IntArrayList).swap_e7b6gt_k$ = function (indexA, indexB) {
    var l = this.getAt_k8n1td_k$(indexA);
    var r = this.getAt_k8n1td_k$(indexB);
    this.set_g707pu_k$(indexA, r);
    this.set_g707pu_k$(indexB, l);
  };
  protoOf(IntArrayList).removeAt_6niowx_k$ = function (index) {
    return this.removeAt_eg4l0p_k$(index, 1);
  };
  protoOf(IntArrayList).removeAt_eg4l0p_k$ = function (index, count) {
    if ((index < 0 ? true : index >= this.length_1) ? true : (index + count | 0) > this.length_1)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var out = this.data_1[index];
    if (count > 0) {
      if (index < (this.length_1 - count | 0)) {
        Memory_getInstance().arraycopy_7oxh5s_k$(this.data_1, index + count | 0, this.data_1, index, (this.length_1 - index | 0) - count | 0);
      }
      this.length_1 = this.length_1 - count | 0;
    }
    return out;
  };
  protoOf(IntArrayList).toIntArray_8jv8ed_k$ = function () {
    return copyOf_1(this.data_1, this.length_1);
  };
  protoOf(IntArrayList).toShortArray_9dzld4_k$ = function () {
    var tmp = 0;
    var tmp_0 = this.length_1;
    var tmp_1 = new Int16Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = toShort(this.data_1[tmp_2]);
      tmp = tmp + 1 | 0;
    }
    return tmp_1;
  };
  protoOf(IntArrayList).indexOf_h7efip_k$ = function (element) {
    return this.indexOf_hf89qp_k$(element, 0, this.get_size_woubt6_k$());
  };
  protoOf(IntArrayList).lastIndexOf_r2wvdn_k$ = function (element) {
    return this.lastIndexOf_ly1y1n_k$(element, 0, this.get_size_woubt6_k$());
  };
  protoOf(IntArrayList).listIterator_xjshxw_k$ = function () {
    return this.listIterator_70e65o_k$(0);
  };
  protoOf(IntArrayList).listIterator_70e65o_k$ = function (index) {
    return take(this.data_1, this.length_1).listIterator_70e65o_k$(index);
  };
  protoOf(IntArrayList).subList_xle3r2_k$ = function (fromIndex, toIndex) {
    // Inline function 'kotlin.collections.asList' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1;
    return asList(tmp$ret$1).subList_xle3r2_k$(fromIndex, toIndex);
  };
  protoOf(IntArrayList).hashCode = function () {
    return contentHashCode_3(this.data_1, 0, this.get_size_woubt6_k$());
  };
  protoOf(IntArrayList).equals = function (other) {
    if (other instanceof IntArrayList)
      return this.get_size_woubt6_k$() === other.get_size_woubt6_k$() ? contentEquals_3(this.data_1, other.data_1, 0, this.get_size_woubt6_k$()) : false;
    if (!(other == null) ? isInterface(other, List) : false)
      return equals(other, this);
    return false;
  };
  protoOf(IntArrayList).toString = function () {
    // Inline function 'kotlin.also' call
    var this_0 = StringBuilder_init_$Create$(2 + imul(5, this.get_size_woubt6_k$()) | 0);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntArrayList.toString.<anonymous>' call
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(91));
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(n === 0)) {
          this_0.append_22ad7x_k$(', ');
        }
        this_0.append_uppzia_k$(this.getAt_k8n1td_k$(n));
      }
       while (inductionVariable < last);
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(93));
    return this_0.toString();
  };
  function intArrayListOf(values) {
    return IntArrayList_init_$Create$_0(values.slice());
  }
  function Companion_4() {
    Companion_instance_4 = this;
  }
  var Companion_instance_4;
  function Companion_getInstance_4() {
    if (Companion_instance_4 == null)
      new Companion_4();
    return Companion_instance_4;
  }
  function _set_data__9licbx_0($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _set_length__xo12bz_0($this, _set____db54di) {
    $this.length_1 = _set____db54di;
  }
  function _get_length__w7ahp7_0($this) {
    return $this.length_1;
  }
  function FloatArrayList_init_$Init$(other, $this) {
    FloatArrayList.call($this);
    $this.add_ppi54v_k$(other);
    return $this;
  }
  function FloatArrayList_init_$Create$(other) {
    return FloatArrayList_init_$Init$(other, objectCreate(protoOf(FloatArrayList)));
  }
  function FloatArrayList_init_$Init$_0(other, $this) {
    FloatArrayList.call($this);
    $this.add$default_nmt89t_k$(other);
    return $this;
  }
  function FloatArrayList_init_$Create$_0(other) {
    return FloatArrayList_init_$Init$_0(other, objectCreate(protoOf(FloatArrayList)));
  }
  function ensure($this, count) {
    if (($this.length_1 + count | 0) > $this.data_1.length) {
      var tmp = $this;
      var tmp_0 = $this.data_1;
      // Inline function 'kotlin.math.max' call
      var a = $this.length_1 + count | 0;
      var b = imul($this.data_1.length, 3);
      var tmp$ret$0 = Math.max(a, b);
      var tmp_1 = copyOf_2(tmp_0, tmp$ret$0);
      tmp.data_1 = isFloatArray(tmp_1) ? tmp_1 : THROW_CCE();
    }
  }
  function createHoleAt($this, index, count) {
    ensure($this, count);
    // Inline function 'kotlin.collections.isNotEmpty' call
    if (!$this.isEmpty_y1axqb_k$()) {
      Memory_getInstance().arraycopy_1hjksm_k$($this.data_1, index, $this.data_1, index + count | 0, $this.length_1 - index | 0);
    }
  }
  function FloatArrayList(capacity) {
    Companion_getInstance_4();
    capacity = capacity === VOID ? 7 : capacity;
    var tmp = this;
    var tmp_0 = new Float32Array(capacity);
    tmp.data_1 = isFloatArray(tmp_0) ? tmp_0 : THROW_CCE();
    this.length_1 = 0;
  }
  protoOf(FloatArrayList).clone_1keycd_k$ = function () {
    return FloatArrayList_init_$Create$(this);
  };
  protoOf(FloatArrayList).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(FloatArrayList).get_capacity_tq6ug8_k$ = function () {
    return this.data_1.length;
  };
  protoOf(FloatArrayList).set_size_e2677a_k$ = function (value) {
    ensure(this, value);
    this.length_1 = value;
  };
  protoOf(FloatArrayList).get_size_woubt6_k$ = function () {
    return this.length_1;
  };
  protoOf(FloatArrayList).clear_j9egeb_k$ = function () {
    this.length_1 = 0;
  };
  protoOf(FloatArrayList).add_8nv8gp_k$ = function (value) {
    ensure(this, 1);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = value;
  };
  protoOf(FloatArrayList).add_b9sj69_k$ = function (v0, v1) {
    ensure(this, 2);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
  };
  protoOf(FloatArrayList).add_l23j8n_k$ = function (v0, v1, v2) {
    ensure(this, 3);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
  };
  protoOf(FloatArrayList).add_sk5f0x_k$ = function (v0, v1, v2, v3) {
    ensure(this, 4);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
  };
  protoOf(FloatArrayList).add_7kk8h3_k$ = function (v0, v1, v2, v3, v4) {
    ensure(this, 5);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
    var tmp_3 = this.data_1;
    var _unary__edvuaz_3 = this.length_1;
    this.length_1 = _unary__edvuaz_3 + 1 | 0;
    tmp_3[_unary__edvuaz_3] = v4;
  };
  protoOf(FloatArrayList).add_5d8nf5_k$ = function (v0, v1, v2, v3, v4, v5) {
    ensure(this, 6);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
    var tmp_3 = this.data_1;
    var _unary__edvuaz_3 = this.length_1;
    this.length_1 = _unary__edvuaz_3 + 1 | 0;
    tmp_3[_unary__edvuaz_3] = v4;
    var tmp_4 = this.data_1;
    var _unary__edvuaz_4 = this.length_1;
    this.length_1 = _unary__edvuaz_4 + 1 | 0;
    tmp_4[_unary__edvuaz_4] = v5;
  };
  protoOf(FloatArrayList).plusAssign_xjcc2p_k$ = function (value) {
    return this.add_8nv8gp_k$(value);
  };
  protoOf(FloatArrayList).plusAssign_2l4if3_k$ = function (value) {
    return this.add$default_nmt89t_k$(value);
  };
  protoOf(FloatArrayList).plusAssign_uc2ak7_k$ = function (value) {
    return this.add_ppi54v_k$(value);
  };
  protoOf(FloatArrayList).plusAssign_2vopok_k$ = function (value) {
    return this.add_aoyhks_k$(value);
  };
  protoOf(FloatArrayList).add_qejtdz_k$ = function (values, offset, length) {
    ensure(this, length);
    var tmp = Memory_getInstance();
    tmp.arraycopy_1hjksm_k$(isFloatArray(values) ? values : THROW_CCE(), offset, this.data_1, this.get_size_woubt6_k$(), length);
    this.set_size_e2677a_k$(this.get_size_woubt6_k$() + length | 0);
  };
  protoOf(FloatArrayList).add$default_nmt89t_k$ = function (values, offset, length, $super) {
    offset = offset === VOID ? 0 : offset;
    length = length === VOID ? values.length : length;
    var tmp;
    if ($super === VOID) {
      this.add_qejtdz_k$(values, offset, length);
      tmp = Unit_getInstance();
    } else {
      tmp = $super.add_qejtdz_k$.call(this, values, offset, length);
    }
    return tmp;
  };
  protoOf(FloatArrayList).add_ppi54v_k$ = function (values) {
    return this.add_qejtdz_k$(values.data_1, 0, values.get_size_woubt6_k$());
  };
  protoOf(FloatArrayList).add_aoyhks_k$ = function (values) {
    var _iterator__ex2g4s = values.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var v = _iterator__ex2g4s.next_20eer_k$();
      this.add_8nv8gp_k$(v);
    }
  };
  protoOf(FloatArrayList).get_c1px32_k$ = function (index) {
    return this.data_1[index];
  };
  protoOf(FloatArrayList).getAt_k8n1td_k$ = function (index) {
    return this.data_1[index];
  };
  protoOf(FloatArrayList).setAt_9t1hxx_k$ = function (index, value) {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FloatArrayList.setAt.<anonymous>' call
    this.set_4eugj6_k$(index, value);
    return value;
  };
  protoOf(FloatArrayList).set_4eugj6_k$ = function (index, value) {
    if (index >= this.length_1) {
      ensure(this, index + 1 | 0);
      this.length_1 = index + 1 | 0;
    }
    this.data_1[index] = value;
  };
  protoOf(FloatArrayList).iterator_jk1svi_k$ = function () {
    return this.listIterator_70e65o_k$(0);
  };
  protoOf(FloatArrayList).contains_8bpbll_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.length_1;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.data_1[n] === element)
          return true;
      }
       while (inductionVariable < last);
    return false;
  };
  protoOf(FloatArrayList).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.contains_8bpbll_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(FloatArrayList).containsAll_tyxzct_k$ = function (elements) {
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var e = _iterator__ex2g4s.next_20eer_k$();
      if (!this.contains_8bpbll_k$(e))
        return false;
    }
    return true;
  };
  protoOf(FloatArrayList).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_tyxzct_k$(elements);
  };
  protoOf(FloatArrayList).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(FloatArrayList).indexOf_ysf2cx_k$ = function (value, start, end) {
    var inductionVariable = start;
    if (inductionVariable < end)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.data_1[n] === value)
          return n;
      }
       while (inductionVariable < end);
    return -1;
  };
  protoOf(FloatArrayList).lastIndexOf_hwuzut_k$ = function (value, start, end) {
    var inductionVariable = end - 1 | 0;
    if (start <= inductionVariable)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        if (this.data_1[n] === value)
          return n;
      }
       while (!(n === start));
    return -1;
  };
  protoOf(FloatArrayList).insertAt_zcy19o_k$ = function (index, value) {
    createHoleAt(this, index, 1);
    this.data_1[index] = value;
    this.length_1 = this.length_1 + 1 | 0;
    return this;
  };
  protoOf(FloatArrayList).insertAt_78um0k_k$ = function (index, v0, v1) {
    createHoleAt(this, index, 2);
    this.data_1[index + 0 | 0] = v0;
    this.data_1[index + 1 | 0] = v1;
    this.length_1 = this.length_1 + 2 | 0;
    return this;
  };
  protoOf(FloatArrayList).insertAt_lq4zck_k$ = function (index, value, start, end) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FloatArrayList.insertAt.<anonymous>' call
    var count = end - start | 0;
    ensure(this, count);
    // Inline function 'kotlin.collections.isNotEmpty' call
    if (!this.isEmpty_y1axqb_k$()) {
      Memory_getInstance().arraycopy_1hjksm_k$(this.data_1, index, this.data_1, index + count | 0, this.length_1 - index | 0);
    }
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this.data_1[index + n | 0] = value[start + n | 0];
      }
       while (inductionVariable < count);
    this.length_1 = this.length_1 + count | 0;
    return this;
  };
  protoOf(FloatArrayList).insertAt$default_x235ts_k$ = function (index, value, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? value.length : end;
    return $super === VOID ? this.insertAt_lq4zck_k$(index, value, start, end) : $super.insertAt_lq4zck_k$.call(this, index, value, start, end);
  };
  protoOf(FloatArrayList).swap_e7b6gt_k$ = function (indexA, indexB) {
    var l = this.getAt_k8n1td_k$(indexA);
    var r = this.getAt_k8n1td_k$(indexB);
    this.set_4eugj6_k$(indexA, r);
    this.set_4eugj6_k$(indexB, l);
  };
  protoOf(FloatArrayList).removeAt_6niowx_k$ = function (index) {
    return this.removeAt_eg4l0p_k$(index, 1);
  };
  protoOf(FloatArrayList).removeAt_eg4l0p_k$ = function (index, count) {
    if ((index < 0 ? true : index >= this.length_1) ? true : (index + count | 0) > this.length_1)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var out = this.data_1[index];
    if (count > 0) {
      if (index < (this.length_1 - count | 0)) {
        Memory_getInstance().arraycopy_1hjksm_k$(this.data_1, index + count | 0, this.data_1, index, (this.length_1 - index | 0) - count | 0);
      }
      this.length_1 = this.length_1 - count | 0;
    }
    return out;
  };
  protoOf(FloatArrayList).toFloatArray_ixdbug_k$ = function () {
    return copyOf_2(this.data_1, this.length_1);
  };
  protoOf(FloatArrayList).indexOf_p3nhjj_k$ = function (element) {
    return this.indexOf_ysf2cx_k$(element, 0, this.get_size_woubt6_k$());
  };
  protoOf(FloatArrayList).lastIndexOf_pknb9h_k$ = function (element) {
    return this.lastIndexOf_hwuzut_k$(element, 0, this.get_size_woubt6_k$());
  };
  protoOf(FloatArrayList).listIterator_xjshxw_k$ = function () {
    return this.listIterator_70e65o_k$(0);
  };
  protoOf(FloatArrayList).listIterator_70e65o_k$ = function (index) {
    return take_0(this.data_1, this.length_1).listIterator_70e65o_k$(index);
  };
  protoOf(FloatArrayList).subList_xle3r2_k$ = function (fromIndex, toIndex) {
    // Inline function 'kotlin.collections.asList' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1;
    return asList(tmp$ret$1).subList_xle3r2_k$(fromIndex, toIndex);
  };
  protoOf(FloatArrayList).hashCode = function () {
    return contentHashCode_4(this.data_1, 0, this.get_size_woubt6_k$());
  };
  protoOf(FloatArrayList).equals = function (other) {
    if (other instanceof FloatArrayList)
      return this.get_size_woubt6_k$() === other.get_size_woubt6_k$() ? contentEquals_4(this.data_1, other.data_1, 0, this.get_size_woubt6_k$()) : false;
    if (!(other == null) ? isInterface(other, List) : false)
      return equals(other, this);
    return false;
  };
  protoOf(FloatArrayList).toString = function () {
    // Inline function 'kotlin.also' call
    var this_0 = StringBuilder_init_$Create$(2 + imul(5, this.get_size_woubt6_k$()) | 0);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FloatArrayList.toString.<anonymous>' call
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(91));
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(n === 0)) {
          this_0.append_22ad7x_k$(', ');
        }
        this_0.append_g7wmaq_k$(this.getAt_k8n1td_k$(n));
      }
       while (inductionVariable < last);
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(93));
    return this_0.toString();
  };
  function Companion_5() {
    Companion_instance_5 = this;
  }
  var Companion_instance_5;
  function Companion_getInstance_5() {
    if (Companion_instance_5 == null)
      new Companion_5();
    return Companion_instance_5;
  }
  function _set_data__9licbx_1($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _set_length__xo12bz_1($this, _set____db54di) {
    $this.length_1 = _set____db54di;
  }
  function _get_length__w7ahp7_1($this) {
    return $this.length_1;
  }
  function DoubleArrayList_init_$Init$(other, $this) {
    DoubleArrayList.call($this);
    $this.add_btq1hv_k$(other);
    return $this;
  }
  function DoubleArrayList_init_$Create$(other) {
    return DoubleArrayList_init_$Init$(other, objectCreate(protoOf(DoubleArrayList)));
  }
  function DoubleArrayList_init_$Init$_0(other, $this) {
    DoubleArrayList.call($this);
    $this.add$default_jtk68_k$(other);
    return $this;
  }
  function DoubleArrayList_init_$Create$_0(other) {
    return DoubleArrayList_init_$Init$_0(other, objectCreate(protoOf(DoubleArrayList)));
  }
  function ensure_0($this, count) {
    if (($this.length_1 + count | 0) > $this.data_1.length) {
      var tmp = $this;
      var tmp_0 = $this.data_1;
      // Inline function 'kotlin.math.max' call
      var a = $this.length_1 + count | 0;
      var b = imul($this.data_1.length, 3);
      var tmp$ret$0 = Math.max(a, b);
      var tmp_1 = copyOf_3(tmp_0, tmp$ret$0);
      tmp.data_1 = isDoubleArray(tmp_1) ? tmp_1 : THROW_CCE();
    }
  }
  function createHoleAt_0($this, index, count) {
    ensure_0($this, count);
    // Inline function 'kotlin.collections.isNotEmpty' call
    if (!$this.isEmpty_y1axqb_k$()) {
      Memory_getInstance().arraycopy_wqviw6_k$($this.data_1, index, $this.data_1, index + count | 0, $this.length_1 - index | 0);
    }
  }
  function DoubleArrayList(capacity) {
    Companion_getInstance_5();
    capacity = capacity === VOID ? 7 : capacity;
    var tmp = this;
    var tmp_0 = new Float64Array(capacity);
    tmp.data_1 = isDoubleArray(tmp_0) ? tmp_0 : THROW_CCE();
    this.length_1 = 0;
  }
  protoOf(DoubleArrayList).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(DoubleArrayList).get_capacity_tq6ug8_k$ = function () {
    return this.data_1.length;
  };
  protoOf(DoubleArrayList).set_size_e2677a_k$ = function (value) {
    ensure_0(this, value);
    this.length_1 = value;
  };
  protoOf(DoubleArrayList).get_size_woubt6_k$ = function () {
    return this.length_1;
  };
  protoOf(DoubleArrayList).clear_j9egeb_k$ = function () {
    this.length_1 = 0;
  };
  protoOf(DoubleArrayList).add_fu98p7_k$ = function (v0) {
    ensure_0(this, 1);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
  };
  protoOf(DoubleArrayList).add_4cczhr_k$ = function (v0, v1) {
    ensure_0(this, 2);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
  };
  protoOf(DoubleArrayList).add_ell1or_k$ = function (v0, v1, v2) {
    ensure_0(this, 3);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
  };
  protoOf(DoubleArrayList).add_jzqvcf_k$ = function (v0, v1, v2, v3) {
    ensure_0(this, 4);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
  };
  protoOf(DoubleArrayList).add_nv5hl7_k$ = function (v0, v1, v2, v3, v4) {
    ensure_0(this, 5);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
    var tmp_3 = this.data_1;
    var _unary__edvuaz_3 = this.length_1;
    this.length_1 = _unary__edvuaz_3 + 1 | 0;
    tmp_3[_unary__edvuaz_3] = v4;
  };
  protoOf(DoubleArrayList).add_xoihm7_k$ = function (v0, v1, v2, v3, v4, v5) {
    ensure_0(this, 6);
    var tmp = this.data_1;
    var _unary__edvuaz = this.length_1;
    this.length_1 = _unary__edvuaz + 1 | 0;
    tmp[_unary__edvuaz] = v0;
    var tmp_0 = this.data_1;
    var _unary__edvuaz_0 = this.length_1;
    this.length_1 = _unary__edvuaz_0 + 1 | 0;
    tmp_0[_unary__edvuaz_0] = v1;
    var tmp_1 = this.data_1;
    var _unary__edvuaz_1 = this.length_1;
    this.length_1 = _unary__edvuaz_1 + 1 | 0;
    tmp_1[_unary__edvuaz_1] = v2;
    var tmp_2 = this.data_1;
    var _unary__edvuaz_2 = this.length_1;
    this.length_1 = _unary__edvuaz_2 + 1 | 0;
    tmp_2[_unary__edvuaz_2] = v3;
    var tmp_3 = this.data_1;
    var _unary__edvuaz_3 = this.length_1;
    this.length_1 = _unary__edvuaz_3 + 1 | 0;
    tmp_3[_unary__edvuaz_3] = v4;
    var tmp_4 = this.data_1;
    var _unary__edvuaz_4 = this.length_1;
    this.length_1 = _unary__edvuaz_4 + 1 | 0;
    tmp_4[_unary__edvuaz_4] = v5;
  };
  protoOf(DoubleArrayList).plusAssign_hd7up9_k$ = function (value) {
    return this.add_fu98p7_k$(value);
  };
  protoOf(DoubleArrayList).plusAssign_a72fjy_k$ = function (value) {
    return this.add$default_jtk68_k$(value);
  };
  protoOf(DoubleArrayList).plusAssign_uh7ecl_k$ = function (value) {
    return this.add_btq1hv_k$(value);
  };
  protoOf(DoubleArrayList).plusAssign_1u8i9n_k$ = function (value) {
    return this.add_r9ucjx_k$(value);
  };
  protoOf(DoubleArrayList).add_faehqe_k$ = function (values, offset, length) {
    ensure_0(this, length);
    var tmp = Memory_getInstance();
    tmp.arraycopy_wqviw6_k$(isDoubleArray(values) ? values : THROW_CCE(), offset, this.data_1, this.get_size_woubt6_k$(), length);
    this.set_size_e2677a_k$(this.get_size_woubt6_k$() + length | 0);
  };
  protoOf(DoubleArrayList).add$default_jtk68_k$ = function (values, offset, length, $super) {
    offset = offset === VOID ? 0 : offset;
    length = length === VOID ? values.length : length;
    var tmp;
    if ($super === VOID) {
      this.add_faehqe_k$(values, offset, length);
      tmp = Unit_getInstance();
    } else {
      tmp = $super.add_faehqe_k$.call(this, values, offset, length);
    }
    return tmp;
  };
  protoOf(DoubleArrayList).add_btq1hv_k$ = function (values) {
    return this.add_faehqe_k$(values.data_1, 0, values.get_size_woubt6_k$());
  };
  protoOf(DoubleArrayList).add_r9ucjx_k$ = function (values) {
    var _iterator__ex2g4s = values.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var v = _iterator__ex2g4s.next_20eer_k$();
      this.add_fu98p7_k$(v);
    }
  };
  protoOf(DoubleArrayList).get_c1px32_k$ = function (index) {
    return this.data_1[index];
  };
  protoOf(DoubleArrayList).getAt_k8n1td_k$ = function (index) {
    return this.data_1[index];
  };
  protoOf(DoubleArrayList).setAt_sdrilf_k$ = function (index, value) {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.DoubleArrayList.setAt.<anonymous>' call
    this.set_mvap1c_k$(index, value);
    return value;
  };
  protoOf(DoubleArrayList).set_mvap1c_k$ = function (index, value) {
    if (index >= this.length_1) {
      ensure_0(this, index + 1 | 0);
      this.length_1 = index + 1 | 0;
    }
    this.data_1[index] = value;
  };
  protoOf(DoubleArrayList).iterator_jk1svi_k$ = function () {
    return this.listIterator_70e65o_k$(0);
  };
  protoOf(DoubleArrayList).contains_p5zavb_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.length_1;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.data_1[n] === element)
          return true;
      }
       while (inductionVariable < last);
    return false;
  };
  protoOf(DoubleArrayList).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.contains_p5zavb_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(DoubleArrayList).containsAll_u6apas_k$ = function (elements) {
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var e = _iterator__ex2g4s.next_20eer_k$();
      if (!this.contains_p5zavb_k$(e))
        return false;
    }
    return true;
  };
  protoOf(DoubleArrayList).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_u6apas_k$(elements);
  };
  protoOf(DoubleArrayList).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(DoubleArrayList).indexOf_nnxgkd_k$ = function (value, start, end) {
    var inductionVariable = start;
    if (inductionVariable < end)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.data_1[n] === value)
          return n;
      }
       while (inductionVariable < end);
    return -1;
  };
  protoOf(DoubleArrayList).lastIndexOf_pg7gb7_k$ = function (value, start, end) {
    var inductionVariable = end - 1 | 0;
    if (start <= inductionVariable)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        if (this.data_1[n] === value)
          return n;
      }
       while (!(n === start));
    return -1;
  };
  protoOf(DoubleArrayList).insertAt_80cfsq_k$ = function (index, value) {
    createHoleAt_0(this, index, 1);
    this.data_1[index] = value;
    this.length_1 = this.length_1 + 1 | 0;
    return this;
  };
  protoOf(DoubleArrayList).insertAt_rh35mc_k$ = function (index, v0, v1) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.DoubleArrayList.insertAt.<anonymous>' call
    createHoleAt_0(this, index, 2);
    this.data_1[index + 0 | 0] = v0;
    this.data_1[index + 1 | 0] = v1;
    this.length_1 = this.length_1 + 2 | 0;
    return this;
  };
  protoOf(DoubleArrayList).insertAt_izhp4l_k$ = function (index, values, start, end) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.DoubleArrayList.insertAt.<anonymous>' call
    var count = end - start | 0;
    createHoleAt_0(this, index, count);
    Memory_getInstance().arraycopy_wqviw6_k$(values, start, this.data_1, index, count);
    this.length_1 = this.length_1 + count | 0;
    return this;
  };
  protoOf(DoubleArrayList).insertAt$default_5wymv5_k$ = function (index, values, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? values.length : end;
    return $super === VOID ? this.insertAt_izhp4l_k$(index, values, start, end) : $super.insertAt_izhp4l_k$.call(this, index, values, start, end);
  };
  protoOf(DoubleArrayList).swap_e7b6gt_k$ = function (indexA, indexB) {
    var l = this.getAt_k8n1td_k$(indexA);
    var r = this.getAt_k8n1td_k$(indexB);
    this.set_mvap1c_k$(indexA, r);
    this.set_mvap1c_k$(indexB, l);
  };
  protoOf(DoubleArrayList).removeAt_6niowx_k$ = function (index) {
    return this.removeAt_eg4l0p_k$(index, 1);
  };
  protoOf(DoubleArrayList).removeAt_eg4l0p_k$ = function (index, count) {
    if ((index < 0 ? true : index >= this.length_1) ? true : (index + count | 0) > this.length_1)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var out = this.data_1[index];
    if (count > 0) {
      if (index < (this.length_1 - count | 0)) {
        Memory_getInstance().arraycopy_wqviw6_k$(this.data_1, index + count | 0, this.data_1, index, (this.length_1 - index | 0) - count | 0);
      }
      this.length_1 = this.length_1 - count | 0;
    }
    return out;
  };
  protoOf(DoubleArrayList).toDoubleArray_tm3pu5_k$ = function () {
    return copyOf_3(this.data_1, this.length_1);
  };
  protoOf(DoubleArrayList).listIterator_70e65o_k$ = function (index) {
    return take_1(this.data_1, this.length_1).listIterator_70e65o_k$(index);
  };
  protoOf(DoubleArrayList).subList_xle3r2_k$ = function (fromIndex, toIndex) {
    // Inline function 'kotlin.collections.asList' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1;
    return asList(tmp$ret$1).subList_xle3r2_k$(fromIndex, toIndex);
  };
  protoOf(DoubleArrayList).clone_1keycd_k$ = function () {
    return DoubleArrayList_init_$Create$(this);
  };
  protoOf(DoubleArrayList).hashCode = function () {
    return contentHashCode_5(this.data_1, 0, this.get_size_woubt6_k$());
  };
  protoOf(DoubleArrayList).equals = function (other) {
    if (other instanceof DoubleArrayList)
      return this.get_size_woubt6_k$() === other.get_size_woubt6_k$() ? contentEquals_5(this.data_1, other.data_1, 0, this.get_size_woubt6_k$()) : false;
    if (!(other == null) ? isInterface(other, List) : false)
      return equals(other, this);
    return false;
  };
  protoOf(DoubleArrayList).toString = function () {
    // Inline function 'kotlin.also' call
    var this_0 = StringBuilder_init_$Create$(2 + imul(5, this.get_size_woubt6_k$()) | 0);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.DoubleArrayList.toString.<anonymous>' call
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(91));
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(n === 0)) {
          this_0.append_22ad7x_k$(', ');
        }
        this_0.append_jynnak_k$(this.getAt_k8n1td_k$(n));
      }
       while (inductionVariable < last);
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(93));
    return this_0.toString();
  };
  function floatArrayListOf(values) {
    return FloatArrayList_init_$Create$_0(values.slice());
  }
  function doubleArrayListOf(values) {
    return DoubleArrayList_init_$Create$_0(values.slice());
  }
  function toIntArrayList(_this__u8e3s4) {
    return IntArrayList_init_$Create$_0(_this__u8e3s4.slice());
  }
  function FloatList() {
  }
  function DoubleList() {
  }
  function reverse_8(_this__u8e3s4, start, end) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? _this__u8e3s4.get_size_woubt6_k$() : end;
    return IntArrayListSortOps_getInstance().reverse_v7yb7h_k$(_this__u8e3s4, start, end - 1 | 0);
  }
  function sort(_this__u8e3s4, start, end, reversed) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? _this__u8e3s4.get_size_woubt6_k$() : end;
    reversed = reversed === VOID ? false : reversed;
    return genericSort_0(_this__u8e3s4, start, end - 1 | 0, IntArrayListSortOps_getInstance(), reversed);
  }
  function rotated(_this__u8e3s4, offset) {
    // Inline function 'kotlin.also' call
    var this_0 = ArrayList_init_$Create$(_this__u8e3s4.get_size_woubt6_k$());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.rotated.<anonymous>' call
    var inductionVariable = 0;
    var last = _this__u8e3s4.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this_0.add_utx5q5_k$(getCyclic(_this__u8e3s4, n - offset | 0));
      }
       while (inductionVariable < last);
    return this_0;
  }
  function filter(_this__u8e3s4, callback) {
    // Inline function 'kotlin.also' call
    var this_0 = new IntArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.filter.<anonymous>' call
    var _iterator__ex2g4s = _this__u8e3s4.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var v = _iterator__ex2g4s.next_20eer_k$();
      if (callback(v)) {
        this_0.add_c9dakn_k$(v);
      }
    }
    return this_0;
  }
  function mapInt(_this__u8e3s4, callback) {
    // Inline function 'kotlin.also' call
    var this_0 = new IntArrayList((coerceAtLeast(_this__u8e3s4.get_endInclusive_r07xpi_k$() - _this__u8e3s4.get_start_iypx6h_k$() | 0, 0) / _this__u8e3s4.get_step_woujh1_k$() | 0) + 1 | 0);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapInt.<anonymous>' call
    var nestedFirst = _this__u8e3s4.get_start_iypx6h_k$();
    var nestedLast = _this__u8e3s4.get_endInclusive_r07xpi_k$();
    var stepArg = _this__u8e3s4.get_step_woujh1_k$();
    if (stepArg <= 0)
      THROW_IAE('Step must be positive, was: ' + stepArg + '.');
    var inductionVariable = nestedFirst;
    var last = getProgressionLastElement(nestedFirst, nestedLast, stepArg);
    if (inductionVariable <= last)
      do {
        var v = inductionVariable;
        inductionVariable = inductionVariable + stepArg | 0;
        this_0.add_c9dakn_k$(callback(v));
      }
       while (!(v === last));
    return this_0;
  }
  function toIntArrayList_0(_this__u8e3s4) {
    return IntArrayList_init_$Create$(_this__u8e3s4);
  }
  function mapInt_0(_this__u8e3s4, callback) {
    // Inline function 'kotlin.also' call
    var this_0 = new IntArrayList(_this__u8e3s4.get_size_woubt6_k$());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapInt.<anonymous>' call
    // Inline function 'korlibs.datastructure.iterators.fastForEach' call
    var n = 0;
    while (n < _this__u8e3s4.get_size_woubt6_k$()) {
      // Inline function 'korlibs.datastructure.mapInt.<anonymous>.<anonymous>' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      var it = _this__u8e3s4.getAt_k8n1td_k$(_unary__edvuaz);
      this_0.add_c9dakn_k$(callback(it));
    }
    return this_0;
  }
  function IntArrayListSortOps() {
    IntArrayListSortOps_instance = this;
    SortOps.call(this);
  }
  protoOf(IntArrayListSortOps).compare_dxrl4t_k$ = function (subject, l, r) {
    return compareTo(subject.getAt_k8n1td_k$(l), subject.getAt_k8n1td_k$(r));
  };
  protoOf(IntArrayListSortOps).compare_nik356_k$ = function (subject, l, r) {
    return this.compare_dxrl4t_k$(subject instanceof IntArrayList ? subject : THROW_CCE(), l, r);
  };
  protoOf(IntArrayListSortOps).swap_ilc5tr_k$ = function (subject, indexL, indexR) {
    var l = subject.getAt_k8n1td_k$(indexL);
    var r = subject.getAt_k8n1td_k$(indexR);
    subject.set_g707pu_k$(indexR, l);
    subject.set_g707pu_k$(indexL, r);
  };
  protoOf(IntArrayListSortOps).swap_nx7218_k$ = function (subject, indexL, indexR) {
    return this.swap_ilc5tr_k$(subject instanceof IntArrayList ? subject : THROW_CCE(), indexL, indexR);
  };
  var IntArrayListSortOps_instance;
  function IntArrayListSortOps_getInstance() {
    if (IntArrayListSortOps_instance == null)
      new IntArrayListSortOps();
    return IntArrayListSortOps_instance;
  }
  function mapDouble(_this__u8e3s4, callback) {
    // Inline function 'kotlin.also' call
    var this_0 = new Float64Array(_this__u8e3s4.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapDouble.<anonymous>' call
    var inductionVariable = 0;
    var last = _this__u8e3s4.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this_0[n] = callback(_this__u8e3s4[n]);
      }
       while (inductionVariable < last);
    return this_0;
  }
  function mapDouble_0(_this__u8e3s4, callback) {
    // Inline function 'kotlin.also' call
    var this_0 = new Float64Array(_this__u8e3s4.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapDouble.<anonymous>' call
    var inductionVariable = 0;
    var last = _this__u8e3s4.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this_0[n] = callback(_this__u8e3s4[n]);
      }
       while (inductionVariable < last);
    return this_0;
  }
  function BaseMutableMap() {
  }
  function BaseMap() {
  }
  function _get_BITS_PER_WORD_SHIFT__1mz9ah($this) {
    return $this.BITS_PER_WORD_SHIFT_1;
  }
  function _get_BITS_PER_WORD__hx4pf8($this) {
    return $this.BITS_PER_WORD_1;
  }
  function _get_BITS_MASK__b8jmac($this) {
    return $this.BITS_MASK_1;
  }
  function BitArray_init_$Init$(size, $this) {
    BitArray.call($this, new Int8Array(Math_getInstance().divCeil_mqwqpw_k$(size, 8)), size);
    return $this;
  }
  function BitArray_init_$Create$(size) {
    return BitArray_init_$Init$(size, objectCreate(protoOf(BitArray)));
  }
  function checkIndexBounds($this, index) {
    if (index < 0 ? true : index >= $this.size_1)
      throw IndexOutOfBoundsException_init_$Create$_0();
  }
  function wordIndex($this, index) {
    return index >>> 3 | 0;
  }
  function indexInWord($this, index) {
    return index & 7;
  }
  function Companion_6() {
    Companion_instance_6 = this;
    this.BITS_PER_WORD_SHIFT_1 = 3;
    this.BITS_PER_WORD_1 = 8;
    this.BITS_MASK_1 = 7;
  }
  protoOf(Companion_6).invoke_fl8zpg_k$ = function (size, init) {
    // Inline function 'kotlin.also' call
    var this_0 = BitArray_init_$Create$(size);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    var inductionVariable = 0;
    if (inductionVariable < size)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this_0.set_jlo0rj_k$(n, init(n));
      }
       while (inductionVariable < size);
    return this_0;
  };
  var Companion_instance_6;
  function Companion_getInstance_6() {
    if (Companion_instance_6 == null)
      new Companion_6();
    return Companion_instance_6;
  }
  function BitArray$iterator$lambda($pos, this$0) {
    return function () {
      return $pos._v < this$0.size_1;
    };
  }
  function BitArray$iterator$lambda_0(this$0, $pos) {
    return function () {
      var _unary__edvuaz = $pos._v;
      $pos._v = _unary__edvuaz + 1 | 0;
      return this$0.get_c1px32_k$(_unary__edvuaz);
    };
  }
  function BitArray(data, size) {
    Companion_getInstance_6();
    AbstractList.call(this);
    this.data_1 = data;
    this.size_1 = size;
  }
  protoOf(BitArray).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(BitArray).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(BitArray).iterator_jk1svi_k$ = function () {
    var pos = {_v: 0};
    var tmp = BitArray$iterator$lambda(pos, this);
    return Iterator_3(tmp, BitArray$iterator$lambda_0(this, pos));
  };
  protoOf(BitArray).set_jlo0rj_k$ = function (index, value) {
    checkIndexBounds(this, index);
    // Inline function 'korlibs.datastructure.BitArray.wordIndex' call
    var wordIndex = index >>> 3 | 0;
    // Inline function 'korlibs.datastructure.BitArray.indexInWord' call
    var indexInWord = index & 7;
    if (value) {
      // Inline function 'kotlin.experimental.or' call
      var this_0 = this.data_1[wordIndex];
      var other = toByte(1 << indexInWord);
      var tmp$ret$2 = toByte(this_0 | other);
      this.data_1[wordIndex] = tmp$ret$2;
    } else {
      // Inline function 'kotlin.experimental.and' call
      var this_1 = this.data_1[wordIndex];
      var other_0 = toByte(~(1 << indexInWord));
      var tmp$ret$3 = toByte(this_1 & other_0);
      this.data_1[wordIndex] = tmp$ret$3;
    }
  };
  protoOf(BitArray).get_c1px32_k$ = function (index) {
    checkIndexBounds(this, index);
    // Inline function 'korlibs.datastructure.BitArray.wordIndex' call
    var tmp$ret$0 = index >>> 3 | 0;
    var tmp = this.data_1[tmp$ret$0];
    // Inline function 'korlibs.datastructure.BitArray.indexInWord' call
    return !(((tmp >>> (index & 7) | 0) & 1) === 0);
  };
  function _get_data__d5abxd($this) {
    return $this.data_1;
  }
  function part($this, index) {
    return index >>> 5 | 0;
  }
  function bit($this, index) {
    return index & 31;
  }
  function BitSet(size) {
    this.size_1 = size;
    this.data_1 = new Int32Array(Math_getInstance().divCeil_mqwqpw_k$(this.size_1, 32));
  }
  protoOf(BitSet).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(BitSet).get_c1px32_k$ = function (index) {
    return !(((this.data_1[part(this, index)] >>> bit(this, index) | 0) & 1) === 0);
  };
  protoOf(BitSet).set_jlo0rj_k$ = function (index, value) {
    var i = part(this, index);
    var b = bit(this, index);
    if (value) {
      this.data_1[i] = this.data_1[i] | 1 << b;
    } else {
      this.data_1[i] = this.data_1[i] & ~(1 << b);
    }
  };
  protoOf(BitSet).set_3dek9m_k$ = function (index) {
    return this.set_jlo0rj_k$(index, true);
  };
  protoOf(BitSet).unset_pvpgsf_k$ = function (index) {
    return this.set_jlo0rj_k$(index, false);
  };
  protoOf(BitSet).clear_j9egeb_k$ = function () {
    return fill_0(this.data_1, 0);
  };
  protoOf(BitSet).contains_4d1uhm_k$ = function (element) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var this_0 = until(0, this.size_1);
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element_0 = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.BitSet.contains.<anonymous>' call
        if (this.get_c1px32_k$(element_0) === element) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(BitSet).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'boolean' : false))
      return false;
    return this.contains_4d1uhm_k$((!(element == null) ? typeof element === 'boolean' : false) ? element : THROW_CCE());
  };
  protoOf(BitSet).containsAll_2db5rl_k$ = function (elements) {
    return (elements.contains_aljjnj_k$(true) ? !this.contains_4d1uhm_k$(true) : false) ? false : (elements.contains_aljjnj_k$(false) ? !this.contains_4d1uhm_k$(false) : false) ? false : true;
  };
  protoOf(BitSet).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_2db5rl_k$(elements);
  };
  protoOf(BitSet).isEmpty_y1axqb_k$ = function () {
    return this.size_1 === 0;
  };
  protoOf(BitSet).iterator_jk1svi_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, this.size_1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.BitSet.iterator.<anonymous>' call
      var tmp$ret$0 = this.get_c1px32_k$(item);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    return destination.iterator_jk1svi_k$();
  };
  protoOf(BitSet).hashCode = function () {
    return contentHashCode_1(this.data_1) + this.size_1 | 0;
  };
  protoOf(BitSet).equals = function (other) {
    var tmp;
    var tmp_0;
    if (other instanceof BitSet) {
      tmp_0 = this.size_1 === other.size_1;
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = contentEquals_1(this.data_1, other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  function cacheLazyNullable(field, gen) {
    var result = field.get();
    if (result == null) {
      result = gen();
      field.set(result);
    }
    return ensureNotNull(result);
  }
  function CacheMap(maxSize) {
    maxSize = maxSize === VOID ? 16 : maxSize;
    BaseCacheMap.call(this);
    this.maxSize_1 = maxSize;
  }
  protoOf(CacheMap).get_maxSize_f83j4s_k$ = function () {
    return this.maxSize_1;
  };
  protoOf(CacheMap).mustFree_18mgb4_k$ = function (key, value) {
    return this.get_size_woubt6_k$() > this.maxSize_1;
  };
  function BaseCacheMap() {
    this.map_1 = LinkedHashMap_init_$Create$();
  }
  protoOf(BaseCacheMap).get_map_18j0ul_k$ = function () {
    return this.map_1;
  };
  protoOf(BaseCacheMap).mustFree_18mgb4_k$ = function (key, value) {
    return false;
  };
  protoOf(BaseCacheMap).keyToRemove_f8wptl_k$ = function (key, value) {
    return first(this.map_1.get_keys_wop4xp_k$());
  };
  protoOf(BaseCacheMap).freed_lk3m6b_k$ = function (key, value) {
  };
  protoOf(BaseCacheMap).get_size_woubt6_k$ = function () {
    return this.map_1.get_size_woubt6_k$();
  };
  protoOf(BaseCacheMap).remove_gppy8k_k$ = function (key) {
    var value = this.map_1.remove_gppy8k_k$(key);
    if (!(value == null)) {
      this.freed_lk3m6b_k$(key, value);
    }
    return value;
  };
  protoOf(BaseCacheMap).putAll_wgg6cj_k$ = function (from) {
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = from.get_entries_p20ztl_k$().iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      // Inline function 'kotlin.collections.component1' call
      var k = _destruct__k2r9zo.get_key_18j28a_k$();
      // Inline function 'kotlin.collections.component2' call
      var v = _destruct__k2r9zo.get_value_j01efc_k$();
      this.put_4fpzoq_k$(k, v);
    }
  };
  protoOf(BaseCacheMap).put_4fpzoq_k$ = function (key, value) {
    var oldValue = this.map_1.get_wei43m_k$(key);
    if (!equals(oldValue, value)) {
      this.remove_gppy8k_k$(key);
      // Inline function 'kotlin.collections.set' call
      this.map_1.put_4fpzoq_k$(key, value);
      this.putNew_qs4n4s_k$(key, value);
    }
    $l$loop: while (true) {
      var tmp;
      // Inline function 'kotlin.collections.isNotEmpty' call
      if (!this.isEmpty_y1axqb_k$()) {
        tmp = this.mustFree_18mgb4_k$(key, value);
      } else {
        tmp = false;
      }
      if (!tmp) {
        break $l$loop;
      }
      var keyToRemove = this.keyToRemove_f8wptl_k$(key, value);
      this.remove_gppy8k_k$(keyToRemove);
    }
    return oldValue;
  };
  protoOf(BaseCacheMap).putNew_qs4n4s_k$ = function (key, value) {
  };
  protoOf(BaseCacheMap).clear_j9egeb_k$ = function () {
    var keys = toList(this.map_1.get_keys_wop4xp_k$());
    var _iterator__ex2g4s = keys.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var key = _iterator__ex2g4s.next_20eer_k$();
      this.remove_gppy8k_k$(key);
    }
  };
  protoOf(BaseCacheMap).get_entries_p20ztl_k$ = function () {
    return this.map_1.get_entries_p20ztl_k$();
  };
  protoOf(BaseCacheMap).get_keys_wop4xp_k$ = function () {
    return this.map_1.get_keys_wop4xp_k$();
  };
  protoOf(BaseCacheMap).get_values_ksazhn_k$ = function () {
    return this.map_1.get_values_ksazhn_k$();
  };
  protoOf(BaseCacheMap).get_wei43m_k$ = function (key) {
    return this.map_1.get_wei43m_k$(key);
  };
  protoOf(BaseCacheMap).toString = function () {
    return this.map_1.toString();
  };
  protoOf(BaseCacheMap).equals = function (other) {
    var tmp;
    if (other instanceof BaseCacheMap) {
      tmp = this.map_1.equals(other.map_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(BaseCacheMap).hashCode = function () {
    return this.map_1.hashCode();
  };
  function toCaseInsensitiveMap(_this__u8e3s4) {
    // Inline function 'kotlin.also' call
    var this_0 = CaseInsensitiveStringMap_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.toCaseInsensitiveMap.<anonymous>' call
    this_0.putAll_njbwjy_k$(_this__u8e3s4);
    return this_0;
  }
  function _get_mapOrig__x5k6j0($this) {
    return $this.mapOrig_1;
  }
  function _get_lcToOrig__czdd2w($this) {
    return $this.lcToOrig_1;
  }
  function _get_mapLC__edgfxe($this) {
    return $this.mapLC_1;
  }
  function CaseInsensitiveStringMap_init_$Init$($this) {
    CaseInsensitiveStringMap.call($this, LinkedHashMap_init_$Create$(), LinkedHashMap_init_$Create$(), LinkedHashMap_init_$Create$());
    return $this;
  }
  function CaseInsensitiveStringMap_init_$Create$() {
    return CaseInsensitiveStringMap_init_$Init$(objectCreate(protoOf(CaseInsensitiveStringMap)));
  }
  function CaseInsensitiveStringMap_init_$Init$_0(data, $this) {
    CaseInsensitiveStringMap_init_$Init$($this);
    $this.putAll_njbwjy_k$(data);
    return $this;
  }
  function CaseInsensitiveStringMap_init_$Create$_0(data) {
    return CaseInsensitiveStringMap_init_$Init$_0(data, objectCreate(protoOf(CaseInsensitiveStringMap)));
  }
  function CaseInsensitiveStringMap_init_$Init$_1(items, $this) {
    CaseInsensitiveStringMap_init_$Init$($this);
    putAll($this, toList_0(items));
    return $this;
  }
  function CaseInsensitiveStringMap_init_$Create$_1(items) {
    return CaseInsensitiveStringMap_init_$Init$_1(items, objectCreate(protoOf(CaseInsensitiveStringMap)));
  }
  function CaseInsensitiveStringMap(mapOrig, lcToOrig, mapLC) {
    this.mapOrig_1 = mapOrig;
    this.lcToOrig_1 = lcToOrig;
    this.mapLC_1 = mapLC;
  }
  protoOf(CaseInsensitiveStringMap).containsValue_qwnszh_k$ = function (value) {
    return this.mapOrig_1.containsValue_yf2ykl_k$(value);
  };
  protoOf(CaseInsensitiveStringMap).containsValue_yf2ykl_k$ = function (value) {
    if (!(value == null ? true : !(value == null)))
      return false;
    return this.containsValue_qwnszh_k$((value == null ? true : !(value == null)) ? value : THROW_CCE());
  };
  protoOf(CaseInsensitiveStringMap).isEmpty_y1axqb_k$ = function () {
    return this.mapOrig_1.isEmpty_y1axqb_k$();
  };
  protoOf(CaseInsensitiveStringMap).get_entries_p20ztl_k$ = function () {
    return this.mapOrig_1.get_entries_p20ztl_k$();
  };
  protoOf(CaseInsensitiveStringMap).get_keys_wop4xp_k$ = function () {
    return this.mapOrig_1.get_keys_wop4xp_k$();
  };
  protoOf(CaseInsensitiveStringMap).get_values_ksazhn_k$ = function () {
    return this.mapOrig_1.get_values_ksazhn_k$();
  };
  protoOf(CaseInsensitiveStringMap).get_size_woubt6_k$ = function () {
    return this.mapOrig_1.get_size_woubt6_k$();
  };
  protoOf(CaseInsensitiveStringMap).containsKey_w445h6_k$ = function (key) {
    // Inline function 'kotlin.text.toLowerCase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = key.toLowerCase();
    return this.mapLC_1.containsKey_aw81wo_k$(tmp$ret$1);
  };
  protoOf(CaseInsensitiveStringMap).containsKey_aw81wo_k$ = function (key) {
    if (!(!(key == null) ? typeof key === 'string' : false))
      return false;
    return this.containsKey_w445h6_k$((!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE());
  };
  protoOf(CaseInsensitiveStringMap).clear_j9egeb_k$ = function () {
    this.mapOrig_1.clear_j9egeb_k$();
    this.mapLC_1.clear_j9egeb_k$();
    this.lcToOrig_1.clear_j9egeb_k$();
  };
  protoOf(CaseInsensitiveStringMap).get_6bo4tg_k$ = function (key) {
    // Inline function 'kotlin.text.toLowerCase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = key.toLowerCase();
    return this.mapLC_1.get_wei43m_k$(tmp$ret$1);
  };
  protoOf(CaseInsensitiveStringMap).get_wei43m_k$ = function (key) {
    if (!(!(key == null) ? typeof key === 'string' : false))
      return null;
    return this.get_6bo4tg_k$((!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE());
  };
  protoOf(CaseInsensitiveStringMap).put_ar1cqs_k$ = function (key, value) {
    this.remove_z05dva_k$(key);
    // Inline function 'kotlin.collections.set' call
    this.mapOrig_1.put_4fpzoq_k$(key, value);
    // Inline function 'kotlin.collections.set' call
    var this_0 = this.lcToOrig_1;
    // Inline function 'kotlin.text.toLowerCase' call
    // Inline function 'kotlin.js.asDynamic' call
    var key_0 = key.toLowerCase();
    this_0.put_4fpzoq_k$(key_0, key);
    // Inline function 'kotlin.text.toLowerCase' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$3 = key.toLowerCase();
    return this.mapLC_1.put_4fpzoq_k$(tmp$ret$3, value);
  };
  protoOf(CaseInsensitiveStringMap).put_4fpzoq_k$ = function (key, value) {
    var tmp = (!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE();
    return this.put_ar1cqs_k$(tmp, (value == null ? true : !(value == null)) ? value : THROW_CCE());
  };
  protoOf(CaseInsensitiveStringMap).putAll_njbwjy_k$ = function (from) {
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = from.get_entries_p20ztl_k$().iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var v = _iterator__ex2g4s.next_20eer_k$();
      this.put_ar1cqs_k$(v.get_key_18j28a_k$(), v.get_value_j01efc_k$());
    }
  };
  protoOf(CaseInsensitiveStringMap).putAll_wgg6cj_k$ = function (from) {
    return this.putAll_njbwjy_k$(from);
  };
  protoOf(CaseInsensitiveStringMap).remove_z05dva_k$ = function (key) {
    // Inline function 'kotlin.text.toLowerCase' call
    // Inline function 'kotlin.js.asDynamic' call
    var lkey = key.toLowerCase();
    var okey = this.lcToOrig_1.get_wei43m_k$(lkey);
    // Inline function 'kotlin.collections.remove' call
    var this_0 = this.mapOrig_1;
    (isInterface(this_0, MutableMap) ? this_0 : THROW_CCE()).remove_gppy8k_k$(okey);
    var res = this.mapLC_1.remove_gppy8k_k$(lkey);
    this.lcToOrig_1.remove_gppy8k_k$(lkey);
    return res;
  };
  protoOf(CaseInsensitiveStringMap).remove_gppy8k_k$ = function (key) {
    if (!(!(key == null) ? typeof key === 'string' : false))
      return null;
    return this.remove_z05dva_k$((!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE());
  };
  protoOf(CaseInsensitiveStringMap).equals = function (other) {
    var tmp;
    if (other instanceof CaseInsensitiveStringMap) {
      tmp = equals(this.mapLC_1, other.mapLC_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(CaseInsensitiveStringMap).hashCode = function () {
    return hashCode(this.mapLC_1);
  };
  function _get_lock__d9xa4g($this) {
    return $this.lock_1;
  }
  function _get_chunks__kpj7x7($this) {
    return $this.chunks_1;
  }
  function _set_chunkPos__681yo($this, _set____db54di) {
    $this.chunkPos_1 = _set____db54di;
  }
  function _get_chunkPos__cz4dfg($this) {
    return $this.chunkPos_1;
  }
  function _set_availableRead__a9qlsq($this, _set____db54di) {
    $this.availableRead_1 = _set____db54di;
  }
  function writeFullNoCopy($this, data) {
    // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
    $this.lock_1;
    $this.chunks_1.add_2y5tg6_k$(data);
    $this.availableRead_1 = $this.availableRead_1 + data.length | 0;
  }
  function consumeChunkSize(_this__u8e3s4, $this, size) {
    var chunk = _this__u8e3s4;
    // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
    $this.lock_1;
    $this.chunkPos_1 = $this.chunkPos_1 + size | 0;
    $this.availableRead_1 = $this.availableRead_1 - size | 0;
    var tmp;
    if ($this.chunkPos_1 >= chunk.length) {
      $this.chunks_1.removeFirst_58pi0k_k$();
      $this.chunkPos_1 = 0;
      tmp = Unit_getInstance();
    }
  }
  function ChunkedByteDeque() {
    this.lock_1 = new NonRecursiveLock();
    this.chunks_1 = TGenDeque_init_$Create$();
    this.chunkPos_1 = 0;
    this.availableRead_1 = 0;
  }
  protoOf(ChunkedByteDeque).get_availableRead_tre4t2_k$ = function () {
    return this.availableRead_1;
  };
  protoOf(ChunkedByteDeque).write_ti570x_k$ = function (data, offset, size) {
    writeFullNoCopy(this, copyOfRange(data, offset, offset + size | 0));
  };
  protoOf(ChunkedByteDeque).write$default_m2hpbm_k$ = function (data, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? data.length - offset | 0 : size;
    var tmp;
    if ($super === VOID) {
      this.write_ti570x_k$(data, offset, size);
      tmp = Unit_getInstance();
    } else {
      tmp = $super.write_ti570x_k$.call(this, data, offset, size);
    }
    return tmp;
  };
  protoOf(ChunkedByteDeque).write_mg8e2f_k$ = function (byte) {
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$0 = new Int8Array([toByte(byte)]);
    writeFullNoCopy(this, tmp$ret$0);
  };
  protoOf(ChunkedByteDeque).read_7zpyie_k$ = function (data, offset, size) {
    if (offset < 0 ? true : (offset + size | 0) > data.length)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var coffset = offset;
    var pending = size;
    var readTotal = 0;
    $l$loop_0: while (pending > 0) {
      // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
      this.lock_1;
      // Inline function 'korlibs.datastructure.ChunkedByteDeque.read.<anonymous>' call
      var tmp;
      // Inline function 'kotlin.collections.isNotEmpty' call
      if (!this.chunks_1.isEmpty_y1axqb_k$()) {
        tmp = this.chunks_1.get_first_irdx8n_k$();
      } else {
        tmp = null;
      }
      var tmp0_elvis_lhs = tmp;
      var tmp_0;
      if (tmp0_elvis_lhs == null) {
        break $l$loop_0;
      } else {
        tmp_0 = tmp0_elvis_lhs;
      }
      var chunk = tmp_0;
      var availableInChunk = chunk.length - this.chunkPos_1 | 0;
      // Inline function 'kotlin.math.min' call
      var b = pending;
      var toCopy = Math.min(availableInChunk, b);
      if (toCopy <= 0)
        break $l$loop_0;
      Memory_getInstance().arraycopy_o9i58a_k$(chunk, this.chunkPos_1, data, coffset, toCopy);
      coffset = coffset + toCopy | 0;
      pending = pending - toCopy | 0;
      readTotal = readTotal + toCopy | 0;
      consumeChunkSize(chunk, this, toCopy);
    }
    return readTotal;
  };
  protoOf(ChunkedByteDeque).read$default_trt2rd_k$ = function (data, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? data.length - offset | 0 : size;
    return $super === VOID ? this.read_7zpyie_k$(data, offset, size) : $super.read_7zpyie_k$.call(this, data, offset, size);
  };
  protoOf(ChunkedByteDeque).read_22xsm_k$ = function () {
    // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
    this.lock_1;
    // Inline function 'korlibs.datastructure.ChunkedByteDeque.read.<anonymous>' call
    var tmp;
    // Inline function 'kotlin.collections.isNotEmpty' call
    if (!this.chunks_1.isEmpty_y1axqb_k$()) {
      tmp = this.chunks_1.get_first_irdx8n_k$();
    } else {
      tmp = null;
    }
    var tmp0_elvis_lhs = tmp;
    var tmp_0;
    if (tmp0_elvis_lhs == null) {
      return -1;
    } else {
      tmp_0 = tmp0_elvis_lhs;
    }
    var chunk = tmp_0;
    // Inline function 'kotlin.also' call
    var this_0 = chunk[this.chunkPos_1];
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ChunkedByteDeque.read.<anonymous>' call
    consumeChunkSize(chunk, this, 1);
    return this_0 & 255;
  };
  protoOf(ChunkedByteDeque).read_yyte66_k$ = function (count) {
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ChunkedByteDeque.read.<anonymous>' call
    var it = new Int8Array(count);
    return copyOf_4(it, this.read$default_trt2rd_k$(it));
  };
  function Bucket($outer) {
    this.$this_1 = $outer;
    this.keys_1 = fastArrayListOf([]);
    this.values_1 = fastArrayListOf([]);
  }
  protoOf(Bucket).get_keys_wop4xp_k$ = function () {
    return this.keys_1;
  };
  protoOf(Bucket).get_values_ksazhn_k$ = function () {
    return this.values_1;
  };
  protoOf(Bucket).getKeyIndex_ek2t01_k$ = function (key) {
    var inductionVariable = 0;
    var last = this.keys_1.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.$this_1.equalKey_1(this.keys_1.get_c1px32_k$(n), key))
          return n;
      }
       while (inductionVariable <= last);
    return -1;
  };
  protoOf(Bucket).getValueIndex_k6a60m_k$ = function (value) {
    var inductionVariable = 0;
    var last = this.values_1.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.$this_1.equalValue_1(this.values_1.get_c1px32_k$(n), value))
          return n;
      }
       while (inductionVariable <= last);
    return -1;
  };
  function getOrCreateBucket($this, key) {
    // Inline function 'korlibs.datastructure.IntMap.getOrPut' call
    var this_0 = $this.buckets_1;
    var key_0 = $this.hasher_1(key);
    var res = this_0.get_c1px32_k$(key_0);
    if (res == null) {
      // Inline function 'korlibs.datastructure.CustomHashMap.getOrCreateBucket.<anonymous>' call
      var tmp$ret$0 = new Bucket($this);
      this_0.set_8cx7ps_k$(key_0, tmp$ret$0);
    }
    return ensureNotNull(this_0.get_c1px32_k$(key_0));
  }
  function getBucketOrNull($this, key) {
    return $this.buckets_1.get_c1px32_k$($this.hasher_1(key));
  }
  function CustomHashMap$entries$2$1($bucket, $index) {
    this.$bucket_1 = $bucket;
    this.$index_1 = $index;
    this.key_1 = $bucket.keys_1.get_c1px32_k$($index);
    this.value_1 = $bucket.values_1.get_c1px32_k$($index);
  }
  protoOf(CustomHashMap$entries$2$1).get_key_18j28a_k$ = function () {
    return this.key_1;
  };
  protoOf(CustomHashMap$entries$2$1).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  protoOf(CustomHashMap$entries$2$1).setValue_9cjski_k$ = function (newValue) {
    // Inline function 'kotlin.also' call
    var this_0 = this.$bucket_1.values_1.get_c1px32_k$(this.$index_1);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.<no name provided>.setValue.<anonymous>' call
    this.$bucket_1.values_1.set_82063s_k$(this.$index_1, newValue);
    return this_0;
  };
  function CustomHashMap(hasher, equalKey, equalValue, initialCapacity) {
    initialCapacity = initialCapacity === VOID ? 16 : initialCapacity;
    this.hasher_1 = hasher;
    this.equalKey_1 = equalKey;
    this.equalValue_1 = equalValue;
    this.initialCapacity_1 = initialCapacity;
    this.buckets_1 = IntMap_init_$Create$(this.initialCapacity_1);
    this.size_1 = 0;
  }
  protoOf(CustomHashMap).get_hasher_e5sh8k_k$ = function () {
    return this.hasher_1;
  };
  protoOf(CustomHashMap).get_equalKey_m6syb0_k$ = function () {
    return this.equalKey_1;
  };
  protoOf(CustomHashMap).get_equalValue_e74b8a_k$ = function () {
    return this.equalValue_1;
  };
  protoOf(CustomHashMap).get_initialCapacity_2re023_k$ = function () {
    return this.initialCapacity_1;
  };
  protoOf(CustomHashMap).get_entries_p20ztl_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.flatMap' call
    // Inline function 'kotlin.collections.flatMapTo' call
    var this_0 = this.buckets_1.get_values_ksazhn_k$();
    var destination = ArrayList_init_$Create$_0();
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.CustomHashMap.<get-entries>.<anonymous>' call
      var tmp19_safe_receiver = element == null ? null : element.keys_1;
      var tmp20_safe_receiver = tmp19_safe_receiver == null ? null : get_indices(tmp19_safe_receiver);
      var tmp;
      if (tmp20_safe_receiver == null) {
        tmp = null;
      } else {
        // Inline function 'kotlin.collections.map' call
        // Inline function 'kotlin.collections.mapTo' call
        var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(tmp20_safe_receiver, 10));
        var tmp0_iterator_0 = tmp20_safe_receiver.iterator_jk1svi_k$();
        while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
          var item = tmp0_iterator_0.next_20eer_k$();
          // Inline function 'korlibs.datastructure.CustomHashMap.<get-entries>.<anonymous>.<anonymous>' call
          var tmp$ret$0 = to(item, element);
          destination_0.add_utx5q5_k$(tmp$ret$0);
        }
        tmp = destination_0;
      }
      var tmp0_elvis_lhs = tmp;
      var tmp_0;
      if (tmp0_elvis_lhs == null) {
        // Inline function 'kotlin.collections.listOf' call
        tmp_0 = emptyList();
      } else {
        tmp_0 = tmp0_elvis_lhs;
      }
      var list = tmp_0;
      addAll(destination, list);
    }
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(destination, 10));
    var tmp0_iterator_1 = destination.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.CustomHashMap.<get-entries>.<anonymous>' call
      var index = item_0.component1_7eebsc_k$();
      var bucket = item_0.component2_7eebsb_k$();
      var tmp$ret$7 = new CustomHashMap$entries$2$1(bucket, index);
      destination_1.add_utx5q5_k$(tmp$ret$7);
    }
    return toMutableSet(destination_1);
  };
  protoOf(CustomHashMap).get_keys_wop4xp_k$ = function () {
    // Inline function 'kotlin.collections.flatMap' call
    // Inline function 'kotlin.collections.flatMapTo' call
    var this_0 = this.buckets_1.get_values_ksazhn_k$();
    var destination = ArrayList_init_$Create$_0();
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.CustomHashMap.<get-keys>.<anonymous>' call
      var tmp0_elvis_lhs = element == null ? null : element.keys_1;
      var tmp;
      if (tmp0_elvis_lhs == null) {
        // Inline function 'kotlin.collections.listOf' call
        tmp = emptyList();
      } else {
        tmp = tmp0_elvis_lhs;
      }
      var list = tmp;
      addAll(destination, list);
    }
    return toMutableSet(destination);
  };
  protoOf(CustomHashMap).get_values_ksazhn_k$ = function () {
    // Inline function 'kotlin.collections.flatMap' call
    // Inline function 'kotlin.collections.flatMapTo' call
    var this_0 = this.buckets_1.get_values_ksazhn_k$();
    var destination = ArrayList_init_$Create$_0();
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.CustomHashMap.<get-values>.<anonymous>' call
      var tmp0_elvis_lhs = element == null ? null : element.values_1;
      var tmp;
      if (tmp0_elvis_lhs == null) {
        // Inline function 'kotlin.collections.listOf' call
        tmp = emptyList();
      } else {
        tmp = tmp0_elvis_lhs;
      }
      var list = tmp;
      addAll(destination, list);
    }
    return toMutableList(destination);
  };
  protoOf(CustomHashMap).get_buckets_5qa06z_k$ = function () {
    return this.buckets_1;
  };
  protoOf(CustomHashMap).set_size_e2677a_k$ = function (_set____db54di) {
    this.size_1 = _set____db54di;
  };
  protoOf(CustomHashMap).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(CustomHashMap).clear_j9egeb_k$ = function () {
    this.set_size_e2677a_k$(0);
    this.buckets_1.clear_j9egeb_k$();
  };
  protoOf(CustomHashMap).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(CustomHashMap).get_wei43m_k$ = function (key) {
    var tmp0_elvis_lhs = getBucketOrNull(this, key);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bucket = tmp;
    var keys = bucket.keys_1;
    var inductionVariable = 0;
    var last = keys.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.equalKey_1(keys.get_c1px32_k$(n), key))
          return bucket.values_1.get_c1px32_k$(n);
      }
       while (inductionVariable <= last);
    return null;
  };
  protoOf(CustomHashMap).containsValue_yf2ykl_k$ = function (value) {
    // Inline function 'korlibs.datastructure.IntMap.fastForEach' call
    var this_0 = this.buckets_1;
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this_0.get_hasZero_7r5aaq_k$() ? 2147483647 : this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this_0.get__keys_z80zht_k$()[index];
          break;
      }
      // Inline function 'korlibs.datastructure.CustomHashMap.containsValue.<anonymous>' call
      if (ensureNotNull(this_0.get_c1px32_k$(it)).getValueIndex_k6a60m_k$(value) >= 0)
        return true;
      index = this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), index === 2147483647 ? 0 : index + 1 | 0);
    }
    return false;
  };
  protoOf(CustomHashMap).containsKey_aw81wo_k$ = function (key) {
    var tmp0_elvis_lhs = getBucketOrNull(this, key);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return false;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bucket = tmp;
    return bucket.getKeyIndex_ek2t01_k$(key) >= 0;
  };
  protoOf(CustomHashMap).remove_gppy8k_k$ = function (key) {
    var bucketKey = this.hasher_1(key);
    var tmp0_elvis_lhs = this.buckets_1.get_c1px32_k$(bucketKey);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bucket = tmp;
    // Inline function 'kotlin.takeIf' call
    var this_0 = bucket.getKeyIndex_ek2t01_k$(key);
    // Inline function 'kotlin.contracts.contract' call
    var tmp_0;
    // Inline function 'korlibs.datastructure.CustomHashMap.remove.<anonymous>' call
    if (this_0 >= 0) {
      tmp_0 = this_0;
    } else {
      tmp_0 = null;
    }
    var tmp1_elvis_lhs = tmp_0;
    var tmp_1;
    if (tmp1_elvis_lhs == null) {
      return null;
    } else {
      tmp_1 = tmp1_elvis_lhs;
    }
    var index = tmp_1;
    var _unary__edvuaz = this.get_size_woubt6_k$();
    this.set_size_e2677a_k$(_unary__edvuaz - 1 | 0);
    bucket.keys_1.removeAt_6niowx_k$(index);
    try {
      return bucket.values_1.removeAt_6niowx_k$(index);
    }finally {
      if (bucket.keys_1.isEmpty_y1axqb_k$()) {
        this.buckets_1.remove_cqondg_k$(bucketKey);
      }
    }
  };
  protoOf(CustomHashMap).put_4fpzoq_k$ = function (key, value) {
    var bucket = getOrCreateBucket(this, key);
    var index = bucket.getKeyIndex_ek2t01_k$(key);
    var tmp;
    if (index >= 0) {
      var oldValue = bucket.values_1.get_c1px32_k$(index);
      bucket.values_1.set_82063s_k$(index, value);
      tmp = oldValue;
    } else {
      var _unary__edvuaz = this.get_size_woubt6_k$();
      this.set_size_e2677a_k$(_unary__edvuaz + 1 | 0);
      bucket.keys_1.add_utx5q5_k$(key);
      bucket.values_1.add_utx5q5_k$(value);
      tmp = null;
    }
    return tmp;
  };
  protoOf(CustomHashMap).putAll_wgg6cj_k$ = function (from) {
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = from.get_entries_p20ztl_k$().iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      // Inline function 'kotlin.collections.component1' call
      var k = _destruct__k2r9zo.get_key_18j28a_k$();
      // Inline function 'kotlin.collections.component2' call
      var v = _destruct__k2r9zo.get_value_j01efc_k$();
      this.put_4fpzoq_k$(k, v);
    }
  };
  function _set__start__4o0zcc($this, _set____db54di) {
    $this._start_1 = _set____db54di;
  }
  function _get__start__37aepk($this) {
    return $this._start_1;
  }
  function _set__size__bau3qd($this, _set____db54di) {
    $this._size_1 = _set____db54di;
  }
  function _get__size__kqacr3($this) {
    return $this._size_1;
  }
  function _set_data__9licbx_2($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _get_data__d5abxd_0($this) {
    return $this.data_1;
  }
  function _get__data__kyoi3c($this) {
    // Inline function 'korlibs.datastructure.fastCastTo' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return $this.data_1;
  }
  function _get_capacity__a9k9f3($this) {
    return $this.data_1.length;
  }
  function TGenDeque_init_$Init$($this) {
    TGenDeque.call($this, 16);
    return $this;
  }
  function TGenDeque_init_$Create$() {
    return TGenDeque_init_$Init$(objectCreate(protoOf(TGenDeque)));
  }
  function resizeIfRequiredFor($this, count) {
    if (($this.get_size_woubt6_k$() + count | 0) > _get_capacity__a9k9f3($this)) {
      var i = $this.data_1;
      var istart = $this._start_1;
      // Inline function 'kotlin.arrayOfNulls' call
      // Inline function 'kotlin.comparisons.maxOf' call
      var a = $this.data_1.length + 7 | 0;
      // Inline function 'kotlin.comparisons.maxOf' call
      var a_0 = $this.get_size_woubt6_k$() + count | 0;
      var b = imul($this.data_1.length, 2);
      var b_0 = Math.max(a_0, b);
      var size = Math.max(a, b_0);
      var _o = fillArrayVal(Array(size), null);
      var o = isArray(_o) ? _o : THROW_CCE();
      copyCyclic($this, i, istart, o, $this._size_1);
      $this.data_1 = o;
      $this._start_1 = 0;
    }
  }
  function copyCyclic($this, i, istart, o, count) {
    // Inline function 'kotlin.math.min' call
    var a = i.length - istart | 0;
    var size1 = Math.min(a, count);
    var size2 = count - size1 | 0;
    Memory_getInstance().arraycopy_vhn1fy_k$(i, istart, o, 0, size1);
    if (size2 > 0) {
      Memory_getInstance().arraycopy_vhn1fy_k$(i, 0, o, size1, size2);
    }
  }
  function _addAll($this, count, block) {
    resizeIfRequiredFor($this, count);
    var base = $this._start_1 + $this._size_1 | 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        $this.data_1[(base + n | 0) % _get_capacity__a9k9f3($this) | 0] = block(n);
      }
       while (inductionVariable < count);
    $this._size_1 = $this._size_1 + count | 0;
    return true;
  }
  function _addAllFirst($this, count, block) {
    resizeIfRequiredFor($this, count);
    $this._start_1 = Math_getInstance().umod_uagn7p_k$($this._start_1 - count | 0, _get_capacity__a9k9f3($this));
    $this._size_1 = $this._size_1 + count | 0;
    var pos = $this._start_1;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = $this.data_1;
        var tmp_0 = Math_getInstance();
        var _unary__edvuaz = pos;
        pos = _unary__edvuaz + 1 | 0;
        tmp[tmp_0.umod_uagn7p_k$(_unary__edvuaz, _get_capacity__a9k9f3($this))] = block(n);
      }
       while (inductionVariable < count);
    return true;
  }
  function _addLast($this, item) {
    $this.data_1[($this._start_1 + $this._size_1 | 0) % _get_capacity__a9k9f3($this) | 0] = item;
    $this._size_1 = $this._size_1 + 1 | 0;
  }
  function nullify($this, index) {
    _get__data__kyoi3c($this)[index] = null;
  }
  function _removeRetainAll($this, elements, retain) {
    var eset = toSet(elements);
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var _temp = $this.data_1.slice();
    var tsize = 0;
    var osize = $this.get_size_woubt6_k$();
    var inductionVariable = 0;
    var last = $this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var c = $this.get_c1px32_k$(n);
        if (eset.contains_aljjnj_k$(c) === retain) {
          var _unary__edvuaz = tsize;
          tsize = _unary__edvuaz + 1 | 0;
          _temp[_unary__edvuaz] = c;
        }
      }
       while (inductionVariable < last);
    $this.data_1 = _temp;
    $this._start_1 = 0;
    $this._size_1 = tsize;
    return !(tsize === osize);
  }
  function internalIndex($this, index) {
    return Math_getInstance().umod_uagn7p_k$($this._start_1 + index | 0, _get_capacity__a9k9f3($this));
  }
  function TGenDeque$iterator$1($that, this$0) {
    this.$that_1 = $that;
    this.this$0__1 = this$0;
    this.index_1 = 0;
  }
  protoOf(TGenDeque$iterator$1).set_index_69f5xp_k$ = function (_set____db54di) {
    this.index_1 = _set____db54di;
  };
  protoOf(TGenDeque$iterator$1).get_index_it478p_k$ = function () {
    return this.index_1;
  };
  protoOf(TGenDeque$iterator$1).next_20eer_k$ = function () {
    var _unary__edvuaz = this.index_1;
    this.index_1 = _unary__edvuaz + 1 | 0;
    return this.$that_1.get_c1px32_k$(_unary__edvuaz);
  };
  protoOf(TGenDeque$iterator$1).hasNext_bitz1p_k$ = function () {
    return this.index_1 < this.this$0__1.get_size_woubt6_k$();
  };
  protoOf(TGenDeque$iterator$1).remove_ldkf9o_k$ = function () {
    this.index_1 = this.index_1 - 1 | 0;
    this.this$0__1.removeAt_6niowx_k$(this.index_1);
  };
  function TGenDeque(initialCapacity) {
    this._start_1 = 0;
    this._size_1 = 0;
    var tmp = this;
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_0 = fillArrayVal(Array(initialCapacity), null);
    tmp.data_1 = isArray(tmp_0) ? tmp_0 : THROW_CCE();
  }
  protoOf(TGenDeque).get_size_woubt6_k$ = function () {
    return this._size_1;
  };
  protoOf(TGenDeque).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(TGenDeque).addLast_s9bxg0_k$ = function (item) {
    resizeIfRequiredFor(this, 1);
    _addLast(this, item);
  };
  protoOf(TGenDeque).addFirst_hbgi1k_k$ = function (item) {
    resizeIfRequiredFor(this, 1);
    this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 - 1 | 0, _get_capacity__a9k9f3(this));
    this._size_1 = this._size_1 + 1 | 0;
    this.data_1[this._start_1] = item;
  };
  protoOf(TGenDeque).addAll_o7wslu_k$ = function (array) {
    // Inline function 'korlibs.datastructure.TGenDeque._addAll' call
    var count = array.length;
    resizeIfRequiredFor(this, count);
    var base = this._start_1 + this._size_1 | 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = (base + n | 0) % _get_capacity__a9k9f3(this) | 0;
        // Inline function 'korlibs.datastructure.TGenDeque.addAll.<anonymous>' call
        tmp[tmp_0] = array[n];
      }
       while (inductionVariable < count);
    this._size_1 = this._size_1 + count | 0;
    return true;
  };
  protoOf(TGenDeque).addAll_66t59i_k$ = function (list) {
    // Inline function 'korlibs.datastructure.TGenDeque._addAll' call
    var count = list.get_size_woubt6_k$();
    resizeIfRequiredFor(this, count);
    var base = this._start_1 + this._size_1 | 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = (base + n | 0) % _get_capacity__a9k9f3(this) | 0;
        // Inline function 'korlibs.datastructure.TGenDeque.addAll.<anonymous>' call
        tmp[tmp_0] = list.get_c1px32_k$(n);
      }
       while (inductionVariable < count);
    this._size_1 = this._size_1 + count | 0;
    return true;
  };
  protoOf(TGenDeque).addAll_yaj5cy_k$ = function (items) {
    return this.addAll_66t59i_k$(toList(items));
  };
  protoOf(TGenDeque).addAll_dmwg7m_k$ = function (elements) {
    return this.addAll_66t59i_k$(toList(elements));
  };
  protoOf(TGenDeque).addAll_4lagoh_k$ = function (elements) {
    return this.addAll_dmwg7m_k$(elements);
  };
  protoOf(TGenDeque).addAllFirst_fwu2hq_k$ = function (items) {
    // Inline function 'korlibs.datastructure.TGenDeque._addAllFirst' call
    var count = items.length;
    resizeIfRequiredFor(this, count);
    this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 - count | 0, _get_capacity__a9k9f3(this));
    this._size_1 = this._size_1 + count | 0;
    var pos = this._start_1;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = Math_getInstance();
        var _unary__edvuaz = pos;
        pos = _unary__edvuaz + 1 | 0;
        var tmp_1 = tmp_0.umod_uagn7p_k$(_unary__edvuaz, _get_capacity__a9k9f3(this));
        // Inline function 'korlibs.datastructure.TGenDeque.addAllFirst.<anonymous>' call
        tmp[tmp_1] = items[n];
      }
       while (inductionVariable < count);
    return true;
  };
  protoOf(TGenDeque).addAllFirst_b84nh2_k$ = function (items) {
    // Inline function 'korlibs.datastructure.TGenDeque._addAllFirst' call
    var count = items.get_size_woubt6_k$();
    resizeIfRequiredFor(this, count);
    this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 - count | 0, _get_capacity__a9k9f3(this));
    this._size_1 = this._size_1 + count | 0;
    var pos = this._start_1;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = Math_getInstance();
        var _unary__edvuaz = pos;
        pos = _unary__edvuaz + 1 | 0;
        var tmp_1 = tmp_0.umod_uagn7p_k$(_unary__edvuaz, _get_capacity__a9k9f3(this));
        // Inline function 'korlibs.datastructure.TGenDeque.addAllFirst.<anonymous>' call
        tmp[tmp_1] = items.get_c1px32_k$(n);
      }
       while (inductionVariable < count);
    return true;
  };
  protoOf(TGenDeque).addAllFirst_93htci_k$ = function (items) {
    return this.addAllFirst_b84nh2_k$(toList(items));
  };
  protoOf(TGenDeque).addAllFirst_c26uiq_k$ = function (items) {
    return this.addAllFirst_b84nh2_k$(toList(items));
  };
  protoOf(TGenDeque).removeFirst_58pi0k_k$ = function () {
    if (this._size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var out = this.get_first_irdx8n_k$();
    nullify(this, this._start_1);
    this._start_1 = (this._start_1 + 1 | 0) % _get_capacity__a9k9f3(this) | 0;
    this._size_1 = this._size_1 - 1 | 0;
    return out;
  };
  protoOf(TGenDeque).removeLast_i5wx8a_k$ = function () {
    if (this._size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var out = this.get_last_wopotb_k$();
    nullify(this, internalIndex(this, this.get_size_woubt6_k$() - 1 | 0));
    this._size_1 = this._size_1 - 1 | 0;
    return out;
  };
  protoOf(TGenDeque).removeAt_6niowx_k$ = function (index) {
    if (index < 0 ? true : index >= this.get_size_woubt6_k$())
      throw IndexOutOfBoundsException_init_$Create$_0();
    if (index === 0)
      return this.removeFirst_58pi0k_k$();
    if (index === (this.get_size_woubt6_k$() - 1 | 0))
      return this.removeLast_i5wx8a_k$();
    var old = this.get_c1px32_k$(index);
    if (index < (this.get_size_woubt6_k$() / 2 | 0)) {
      var inductionVariable = index;
      if (1 <= inductionVariable)
        do {
          var n = inductionVariable;
          inductionVariable = inductionVariable + -1 | 0;
          this.set_zerswh_k$(n, this.get_c1px32_k$(n - 1 | 0));
        }
         while (1 <= inductionVariable);
      this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 + 1 | 0, _get_capacity__a9k9f3(this));
    } else {
      var inductionVariable_0 = index;
      var last = this.get_size_woubt6_k$() - 1 | 0;
      if (inductionVariable_0 < last)
        do {
          var n_0 = inductionVariable_0;
          inductionVariable_0 = inductionVariable_0 + 1 | 0;
          this.set_zerswh_k$(n_0, this.get_c1px32_k$(n_0 + 1 | 0));
        }
         while (inductionVariable_0 < last);
    }
    this._size_1 = this._size_1 - 1 | 0;
    return old;
  };
  protoOf(TGenDeque).add_2y5tg6_k$ = function (element) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.TGenDeque.add.<anonymous>' call
    this.addLast_s9bxg0_k$(element);
    return true;
  };
  protoOf(TGenDeque).add_utx5q5_k$ = function (element) {
    return this.add_2y5tg6_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(TGenDeque).clear_j9egeb_k$ = function () {
    this._size_1 = 0;
  };
  protoOf(TGenDeque).remove_tetxhv_k$ = function (element) {
    var index = this.indexOf_jnvfzi_k$(element);
    if (index >= 0) {
      this.removeAt_6niowx_k$(index);
    }
    return index >= 0;
  };
  protoOf(TGenDeque).remove_cedx0m_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.remove_tetxhv_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(TGenDeque).removeAll_eizctx_k$ = function (elements) {
    return _removeRetainAll(this, elements, false);
  };
  protoOf(TGenDeque).removeAll_y0z8pe_k$ = function (elements) {
    return this.removeAll_eizctx_k$(elements);
  };
  protoOf(TGenDeque).retainAll_ta6v7a_k$ = function (elements) {
    return _removeRetainAll(this, elements, true);
  };
  protoOf(TGenDeque).retainAll_9fhiib_k$ = function (elements) {
    return this.retainAll_ta6v7a_k$(elements);
  };
  protoOf(TGenDeque).get_first_irdx8n_k$ = function () {
    return this.data_1[this._start_1];
  };
  protoOf(TGenDeque).get_last_wopotb_k$ = function () {
    return this.data_1[internalIndex(this, this.get_size_woubt6_k$() - 1 | 0)];
  };
  protoOf(TGenDeque).set_zerswh_k$ = function (index, value) {
    this.data_1[internalIndex(this, index)] = value;
  };
  protoOf(TGenDeque).get_c1px32_k$ = function (index) {
    return this.data_1[internalIndex(this, index)];
  };
  protoOf(TGenDeque).getOrNull_3jmqaw_k$ = function (index) {
    return (0 <= index ? index <= (this.get_size_woubt6_k$() - 1 | 0) : false) ? this.get_c1px32_k$(index) : null;
  };
  protoOf(TGenDeque).contains_c6f694_k$ = function (element) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var this_0 = until(0, this.get_size_woubt6_k$());
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element_0 = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.TGenDeque.contains.<anonymous>' call
        if (equals(this.get_c1px32_k$(element_0), element)) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(TGenDeque).contains_aljjnj_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.contains_c6f694_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(TGenDeque).indexOf_jnvfzi_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (equals(this.get_c1px32_k$(n), element))
          return n;
      }
       while (inductionVariable < last);
    return -1;
  };
  protoOf(TGenDeque).containsAll_nns6wg_k$ = function (elements) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(elements, 10));
    var tmp0_iterator = elements.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.TGenDeque.containsAll.<anonymous>' call
      var tmp$ret$0 = to(item, 0);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    var emap = toLinkedMap(destination);
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var it = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var e = this.get_c1px32_k$(it);
        // Inline function 'kotlin.collections.contains' call
        // Inline function 'kotlin.collections.containsKey' call
        if ((isInterface(emap, Map_0) ? emap : THROW_CCE()).containsKey_aw81wo_k$(e)) {
          // Inline function 'kotlin.collections.set' call
          emap.put_4fpzoq_k$(e, 1);
        }
      }
       while (inductionVariable < last);
    var tmp$ret$5;
    $l$block_0: {
      // Inline function 'kotlin.collections.all' call
      var this_0 = emap.get_values_ksazhn_k$();
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$5 = true;
        break $l$block_0;
      }
      var tmp0_iterator_0 = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator_0.next_20eer_k$();
        // Inline function 'korlibs.datastructure.TGenDeque.containsAll.<anonymous>' call
        if (!(element === 1)) {
          tmp$ret$5 = false;
          break $l$block_0;
        }
      }
      tmp$ret$5 = true;
    }
    return tmp$ret$5;
  };
  protoOf(TGenDeque).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_nns6wg_k$(elements);
  };
  protoOf(TGenDeque).iterator_jk1svi_k$ = function () {
    var that = this;
    return new TGenDeque$iterator$1(that, this);
  };
  protoOf(TGenDeque).hashCode = function () {
    // Inline function 'korlibs.datastructure.internal.contentHashCode' call
    var size = this.get_size_woubt6_k$();
    var result = 1;
    var inductionVariable = 0;
    if (inductionVariable < size)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = imul(31, result);
        // Inline function 'kotlin.hashCode' call
        // Inline function 'korlibs.datastructure.TGenDeque.hashCode.<anonymous>' call
        var tmp0_safe_receiver = this.get_c1px32_k$(n);
        var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
        result = tmp + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0;
      }
       while (inductionVariable < size);
    return result;
  };
  protoOf(TGenDeque).equals = function (other) {
    if (!(other instanceof TGenDeque))
      return false;
    if (!(other.get_size_woubt6_k$() === this.get_size_woubt6_k$()))
      return false;
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!equals(this.get_c1px32_k$(n), other.get_c1px32_k$(n)))
          return false;
      }
       while (inductionVariable < last);
    return true;
  };
  protoOf(TGenDeque).toString = function () {
    var sb = StringBuilder_init_$Create$_0();
    sb.append_am5a4z_k$(_Char___init__impl__6a9atx(91));
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        sb.append_t8pm91_k$(this.get_c1px32_k$(n));
        if (!(n === (this.get_size_woubt6_k$() - 1 | 0))) {
          sb.append_22ad7x_k$(', ');
        }
      }
       while (inductionVariable < last);
    sb.append_am5a4z_k$(_Char___init__impl__6a9atx(93));
    return sb.toString();
  };
  function _set__start__4o0zcc_0($this, _set____db54di) {
    $this._start_1 = _set____db54di;
  }
  function _get__start__37aepk_0($this) {
    return $this._start_1;
  }
  function _set__size__bau3qd_0($this, _set____db54di) {
    $this._size_1 = _set____db54di;
  }
  function _get__size__kqacr3_0($this) {
    return $this._size_1;
  }
  function _set_data__9licbx_3($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _get_data__d5abxd_1($this) {
    return $this.data_1;
  }
  function _get__data__kyoi3c_0($this) {
    // Inline function 'korlibs.datastructure.fastCastTo' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return $this.data_1;
  }
  function _get_capacity__a9k9f3_0($this) {
    return $this.data_1.length;
  }
  function IntDeque_init_$Init$($this) {
    IntDeque.call($this, 16);
    return $this;
  }
  function IntDeque_init_$Create$() {
    return IntDeque_init_$Init$(objectCreate(protoOf(IntDeque)));
  }
  function resizeIfRequiredFor_0($this, count) {
    if (($this.get_size_woubt6_k$() + count | 0) > _get_capacity__a9k9f3_0($this)) {
      var i = $this.data_1;
      var istart = $this._start_1;
      // Inline function 'kotlin.comparisons.maxOf' call
      var a = $this.data_1.length + 7 | 0;
      // Inline function 'kotlin.comparisons.maxOf' call
      var a_0 = $this.get_size_woubt6_k$() + count | 0;
      var b = imul($this.data_1.length, 2);
      var b_0 = Math.max(a_0, b);
      var tmp$ret$1 = Math.max(a, b_0);
      var _o = new Int32Array(tmp$ret$1);
      var o = isIntArray(_o) ? _o : THROW_CCE();
      copyCyclic_0($this, i, istart, o, $this._size_1);
      $this.data_1 = o;
      $this._start_1 = 0;
    }
  }
  function copyCyclic_0($this, i, istart, o, count) {
    // Inline function 'kotlin.math.min' call
    var a = i.length - istart | 0;
    var size1 = Math.min(a, count);
    var size2 = count - size1 | 0;
    Memory_getInstance().arraycopy_7oxh5s_k$(i, istart, o, 0, size1);
    if (size2 > 0) {
      Memory_getInstance().arraycopy_7oxh5s_k$(i, 0, o, size1, size2);
    }
  }
  function _addAll_0($this, count, block) {
    resizeIfRequiredFor_0($this, count);
    var base = $this._start_1 + $this._size_1 | 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        $this.data_1[(base + n | 0) % _get_capacity__a9k9f3_0($this) | 0] = block(n);
      }
       while (inductionVariable < count);
    $this._size_1 = $this._size_1 + count | 0;
    return true;
  }
  function _addAllFirst_0($this, count, block) {
    resizeIfRequiredFor_0($this, count);
    $this._start_1 = Math_getInstance().umod_uagn7p_k$($this._start_1 - count | 0, _get_capacity__a9k9f3_0($this));
    $this._size_1 = $this._size_1 + count | 0;
    var pos = $this._start_1;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = $this.data_1;
        var tmp_0 = Math_getInstance();
        var _unary__edvuaz = pos;
        pos = _unary__edvuaz + 1 | 0;
        tmp[tmp_0.umod_uagn7p_k$(_unary__edvuaz, _get_capacity__a9k9f3_0($this))] = block(n);
      }
       while (inductionVariable < count);
    return true;
  }
  function _addLast_0($this, item) {
    $this.data_1[($this._start_1 + $this._size_1 | 0) % _get_capacity__a9k9f3_0($this) | 0] = item;
    $this._size_1 = $this._size_1 + 1 | 0;
  }
  function nullify_0($this, index) {
    _get__data__kyoi3c_0($this)[index] = 0;
  }
  function _removeRetainAll_0($this, elements, retain) {
    var eset = toSet(elements);
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var _temp = $this.data_1.slice();
    var tsize = 0;
    var osize = $this.get_size_woubt6_k$();
    var inductionVariable = 0;
    var last = $this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var c = $this.get_c1px32_k$(n);
        if (eset.contains_aljjnj_k$(c) === retain) {
          var _unary__edvuaz = tsize;
          tsize = _unary__edvuaz + 1 | 0;
          _temp[_unary__edvuaz] = c;
        }
      }
       while (inductionVariable < last);
    $this.data_1 = _temp;
    $this._start_1 = 0;
    $this._size_1 = tsize;
    return !(tsize === osize);
  }
  function internalIndex_0($this, index) {
    return Math_getInstance().umod_uagn7p_k$($this._start_1 + index | 0, _get_capacity__a9k9f3_0($this));
  }
  function IntDeque$iterator$1($that, this$0) {
    this.$that_1 = $that;
    this.this$0__1 = this$0;
    this.index_1 = 0;
  }
  protoOf(IntDeque$iterator$1).set_index_69f5xp_k$ = function (_set____db54di) {
    this.index_1 = _set____db54di;
  };
  protoOf(IntDeque$iterator$1).get_index_it478p_k$ = function () {
    return this.index_1;
  };
  protoOf(IntDeque$iterator$1).next_20eer_k$ = function () {
    var _unary__edvuaz = this.index_1;
    this.index_1 = _unary__edvuaz + 1 | 0;
    return this.$that_1.get_c1px32_k$(_unary__edvuaz);
  };
  protoOf(IntDeque$iterator$1).hasNext_bitz1p_k$ = function () {
    return this.index_1 < this.this$0__1.get_size_woubt6_k$();
  };
  protoOf(IntDeque$iterator$1).remove_ldkf9o_k$ = function () {
    this.index_1 = this.index_1 - 1 | 0;
    this.this$0__1.removeAt_6niowx_k$(this.index_1);
  };
  function IntDeque(initialCapacity) {
    this._start_1 = 0;
    this._size_1 = 0;
    var tmp = this;
    var tmp_0 = new Int32Array(initialCapacity);
    tmp.data_1 = isIntArray(tmp_0) ? tmp_0 : THROW_CCE();
  }
  protoOf(IntDeque).get_size_woubt6_k$ = function () {
    return this._size_1;
  };
  protoOf(IntDeque).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(IntDeque).addLast_551qun_k$ = function (item) {
    resizeIfRequiredFor_0(this, 1);
    _addLast_0(this, item);
  };
  protoOf(IntDeque).addFirst_eag9gn_k$ = function (item) {
    resizeIfRequiredFor_0(this, 1);
    this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 - 1 | 0, _get_capacity__a9k9f3_0(this));
    this._size_1 = this._size_1 + 1 | 0;
    this.data_1[this._start_1] = item;
  };
  protoOf(IntDeque).addAll_k6ueej_k$ = function (array) {
    // Inline function 'korlibs.datastructure.IntDeque._addAll' call
    var count = array.length;
    resizeIfRequiredFor_0(this, count);
    var base = this._start_1 + this._size_1 | 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = (base + n | 0) % _get_capacity__a9k9f3_0(this) | 0;
        // Inline function 'korlibs.datastructure.IntDeque.addAll.<anonymous>' call
        tmp[tmp_0] = array[n];
      }
       while (inductionVariable < count);
    this._size_1 = this._size_1 + count | 0;
    return true;
  };
  protoOf(IntDeque).addAll_u5nlws_k$ = function (list) {
    // Inline function 'korlibs.datastructure.IntDeque._addAll' call
    var count = list.get_size_woubt6_k$();
    resizeIfRequiredFor_0(this, count);
    var base = this._start_1 + this._size_1 | 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = (base + n | 0) % _get_capacity__a9k9f3_0(this) | 0;
        // Inline function 'korlibs.datastructure.IntDeque.addAll.<anonymous>' call
        tmp[tmp_0] = list.get_c1px32_k$(n);
      }
       while (inductionVariable < count);
    this._size_1 = this._size_1 + count | 0;
    return true;
  };
  protoOf(IntDeque).addAll_jzcfmw_k$ = function (items) {
    return this.addAll_u5nlws_k$(toList(items));
  };
  protoOf(IntDeque).addAll_c9pvhk_k$ = function (elements) {
    return this.addAll_u5nlws_k$(toList(elements));
  };
  protoOf(IntDeque).addAll_4lagoh_k$ = function (elements) {
    return this.addAll_c9pvhk_k$(elements);
  };
  protoOf(IntDeque).addAllFirst_f2j6w5_k$ = function (items) {
    // Inline function 'korlibs.datastructure.IntDeque._addAllFirst' call
    var count = items.length;
    resizeIfRequiredFor_0(this, count);
    this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 - count | 0, _get_capacity__a9k9f3_0(this));
    this._size_1 = this._size_1 + count | 0;
    var pos = this._start_1;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = Math_getInstance();
        var _unary__edvuaz = pos;
        pos = _unary__edvuaz + 1 | 0;
        var tmp_1 = tmp_0.umod_uagn7p_k$(_unary__edvuaz, _get_capacity__a9k9f3_0(this));
        // Inline function 'korlibs.datastructure.IntDeque.addAllFirst.<anonymous>' call
        tmp[tmp_1] = items[n];
      }
       while (inductionVariable < count);
    return true;
  };
  protoOf(IntDeque).addAllFirst_7xkpzw_k$ = function (items) {
    // Inline function 'korlibs.datastructure.IntDeque._addAllFirst' call
    var count = items.get_size_woubt6_k$();
    resizeIfRequiredFor_0(this, count);
    this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 - count | 0, _get_capacity__a9k9f3_0(this));
    this._size_1 = this._size_1 + count | 0;
    var pos = this._start_1;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = this.data_1;
        var tmp_0 = Math_getInstance();
        var _unary__edvuaz = pos;
        pos = _unary__edvuaz + 1 | 0;
        var tmp_1 = tmp_0.umod_uagn7p_k$(_unary__edvuaz, _get_capacity__a9k9f3_0(this));
        // Inline function 'korlibs.datastructure.IntDeque.addAllFirst.<anonymous>' call
        tmp[tmp_1] = items.get_c1px32_k$(n);
      }
       while (inductionVariable < count);
    return true;
  };
  protoOf(IntDeque).addAllFirst_6s2x9k_k$ = function (items) {
    return this.addAllFirst_7xkpzw_k$(toList(items));
  };
  protoOf(IntDeque).addAllFirst_mgblq0_k$ = function (items) {
    return this.addAllFirst_7xkpzw_k$(toList(items));
  };
  protoOf(IntDeque).removeFirst_58pi0k_k$ = function () {
    if (this._size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var out = this.get_first_irdx8n_k$();
    nullify_0(this, this._start_1);
    this._start_1 = (this._start_1 + 1 | 0) % _get_capacity__a9k9f3_0(this) | 0;
    this._size_1 = this._size_1 - 1 | 0;
    return out;
  };
  protoOf(IntDeque).removeLast_i5wx8a_k$ = function () {
    if (this._size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    var out = this.get_last_wopotb_k$();
    nullify_0(this, internalIndex_0(this, this.get_size_woubt6_k$() - 1 | 0));
    this._size_1 = this._size_1 - 1 | 0;
    return out;
  };
  protoOf(IntDeque).removeAt_6niowx_k$ = function (index) {
    if (index < 0 ? true : index >= this.get_size_woubt6_k$())
      throw IndexOutOfBoundsException_init_$Create$_0();
    if (index === 0)
      return this.removeFirst_58pi0k_k$();
    if (index === (this.get_size_woubt6_k$() - 1 | 0))
      return this.removeLast_i5wx8a_k$();
    var old = this.get_c1px32_k$(index);
    if (index < (this.get_size_woubt6_k$() / 2 | 0)) {
      var inductionVariable = index;
      if (1 <= inductionVariable)
        do {
          var n = inductionVariable;
          inductionVariable = inductionVariable + -1 | 0;
          this.set_g707pu_k$(n, this.get_c1px32_k$(n - 1 | 0));
        }
         while (1 <= inductionVariable);
      this._start_1 = Math_getInstance().umod_uagn7p_k$(this._start_1 + 1 | 0, _get_capacity__a9k9f3_0(this));
    } else {
      var inductionVariable_0 = index;
      var last = this.get_size_woubt6_k$() - 1 | 0;
      if (inductionVariable_0 < last)
        do {
          var n_0 = inductionVariable_0;
          inductionVariable_0 = inductionVariable_0 + 1 | 0;
          this.set_g707pu_k$(n_0, this.get_c1px32_k$(n_0 + 1 | 0));
        }
         while (inductionVariable_0 < last);
    }
    this._size_1 = this._size_1 - 1 | 0;
    return old;
  };
  protoOf(IntDeque).add_lnluon_k$ = function (element) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntDeque.add.<anonymous>' call
    this.addLast_551qun_k$(element);
    return true;
  };
  protoOf(IntDeque).add_utx5q5_k$ = function (element) {
    return this.add_lnluon_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntDeque).clear_j9egeb_k$ = function () {
    this._size_1 = 0;
  };
  protoOf(IntDeque).remove_cqondg_k$ = function (element) {
    var index = this.indexOf_h7efip_k$(element);
    if (index >= 0) {
      this.removeAt_6niowx_k$(index);
    }
    return index >= 0;
  };
  protoOf(IntDeque).remove_cedx0m_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.remove_cqondg_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntDeque).removeAll_bjaog5_k$ = function (elements) {
    return _removeRetainAll_0(this, elements, false);
  };
  protoOf(IntDeque).removeAll_y0z8pe_k$ = function (elements) {
    return this.removeAll_bjaog5_k$(elements);
  };
  protoOf(IntDeque).retainAll_jfkdrw_k$ = function (elements) {
    return _removeRetainAll_0(this, elements, true);
  };
  protoOf(IntDeque).retainAll_9fhiib_k$ = function (elements) {
    return this.retainAll_jfkdrw_k$(elements);
  };
  protoOf(IntDeque).get_first_irdx8n_k$ = function () {
    return this.data_1[this._start_1];
  };
  protoOf(IntDeque).get_last_wopotb_k$ = function () {
    return this.data_1[internalIndex_0(this, this.get_size_woubt6_k$() - 1 | 0)];
  };
  protoOf(IntDeque).set_g707pu_k$ = function (index, value) {
    this.data_1[internalIndex_0(this, index)] = value;
  };
  protoOf(IntDeque).get_c1px32_k$ = function (index) {
    return this.data_1[internalIndex_0(this, index)];
  };
  protoOf(IntDeque).getOrNull_3jmqaw_k$ = function (index) {
    return (0 <= index ? index <= (this.get_size_woubt6_k$() - 1 | 0) : false) ? this.get_c1px32_k$(index) : null;
  };
  protoOf(IntDeque).contains_7q95ev_k$ = function (element) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var this_0 = until(0, this.get_size_woubt6_k$());
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element_0 = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntDeque.contains.<anonymous>' call
        if (this.get_c1px32_k$(element_0) === element) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(IntDeque).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.contains_7q95ev_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntDeque).indexOf_h7efip_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.get_c1px32_k$(n) === element)
          return n;
      }
       while (inductionVariable < last);
    return -1;
  };
  protoOf(IntDeque).containsAll_tftgie_k$ = function (elements) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(elements, 10));
    var tmp0_iterator = elements.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.IntDeque.containsAll.<anonymous>' call
      var tmp$ret$0 = to(item, 0);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    var emap = toLinkedMap(destination);
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var it = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var e = this.get_c1px32_k$(it);
        // Inline function 'kotlin.collections.contains' call
        // Inline function 'kotlin.collections.containsKey' call
        if ((isInterface(emap, Map_0) ? emap : THROW_CCE()).containsKey_aw81wo_k$(e)) {
          // Inline function 'kotlin.collections.set' call
          emap.put_4fpzoq_k$(e, 1);
        }
      }
       while (inductionVariable < last);
    var tmp$ret$5;
    $l$block_0: {
      // Inline function 'kotlin.collections.all' call
      var this_0 = emap.get_values_ksazhn_k$();
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$5 = true;
        break $l$block_0;
      }
      var tmp0_iterator_0 = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator_0.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntDeque.containsAll.<anonymous>' call
        if (!(element === 1)) {
          tmp$ret$5 = false;
          break $l$block_0;
        }
      }
      tmp$ret$5 = true;
    }
    return tmp$ret$5;
  };
  protoOf(IntDeque).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_tftgie_k$(elements);
  };
  protoOf(IntDeque).iterator_jk1svi_k$ = function () {
    var that = this;
    return new IntDeque$iterator$1(that, this);
  };
  protoOf(IntDeque).hashCode = function () {
    // Inline function 'korlibs.datastructure.internal.contentHashCode' call
    var size = this.get_size_woubt6_k$();
    var result = 1;
    var inductionVariable = 0;
    if (inductionVariable < size)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = imul(31, result);
        // Inline function 'kotlin.hashCode' call
        // Inline function 'korlibs.datastructure.IntDeque.hashCode.<anonymous>' call
        var tmp0_safe_receiver = this.get_c1px32_k$(n);
        var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
        result = tmp + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0;
      }
       while (inductionVariable < size);
    return result;
  };
  protoOf(IntDeque).equals = function (other) {
    if (!(other instanceof IntDeque))
      return false;
    if (!(other.get_size_woubt6_k$() === this.get_size_woubt6_k$()))
      return false;
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(this.get_c1px32_k$(n) === other.get_c1px32_k$(n)))
          return false;
      }
       while (inductionVariable < last);
    return true;
  };
  protoOf(IntDeque).toString = function () {
    var sb = StringBuilder_init_$Create$_0();
    sb.append_am5a4z_k$(_Char___init__impl__6a9atx(91));
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        sb.append_uppzia_k$(this.get_c1px32_k$(n));
        if (!(n === (this.get_size_woubt6_k$() - 1 | 0))) {
          sb.append_22ad7x_k$(', ');
        }
      }
       while (inductionVariable < last);
    sb.append_am5a4z_k$(_Char___init__impl__6a9atx(93));
    return sb.toString();
  };
  function toFastList(_this__u8e3s4, out) {
    out = out === VOID ? FastArrayList_init_$Create$() : out;
    // Inline function 'kotlin.math.min' call
    var a = _this__u8e3s4.get_size_woubt6_k$();
    var b = out.get_size_woubt6_k$();
    var minSize = Math.min(a, b);
    var inductionVariable = 0;
    if (inductionVariable < minSize)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out.set_82063s_k$(n, _this__u8e3s4.get_c1px32_k$(n));
      }
       while (inductionVariable < minSize);
    var inductionVariable_0 = minSize;
    var last = _this__u8e3s4.get_size_woubt6_k$();
    if (inductionVariable_0 < last)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        out.add_utx5q5_k$(_this__u8e3s4.get_c1px32_k$(n_0));
      }
       while (inductionVariable_0 < last);
    while (out.get_size_woubt6_k$() > _this__u8e3s4.get_size_woubt6_k$()) {
      removeLast(out);
    }
    return out;
  }
  function fastArrayListOf(values) {
    // Inline function 'kotlin.also' call
    var this_0 = FastArrayList_init_$Create$_0(values.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.fastArrayListOf.<anonymous>' call
    addAll_0(this_0, values);
    return this_0;
  }
  function MutableListEx() {
  }
  function toFastList_0(_this__u8e3s4, out) {
    out = out === VOID ? FastArrayList_init_$Create$() : out;
    out.setAddAll$default_tbb3em_k$(0, _this__u8e3s4);
    out.removeToSize_jfdn0o_k$(_this__u8e3s4.get_size_woubt6_k$());
    return out;
  }
  function FastIdentityCacheMap() {
    this.fast1K_1 = null;
    this.fast1V_1 = null;
    this.fast2K_1 = null;
    this.fast2V_1 = null;
    this.cache_1 = FastIdentityMap_0();
  }
  protoOf(FastIdentityCacheMap).set_fast1K_sa9wg7_k$ = function (_set____db54di) {
    this.fast1K_1 = _set____db54di;
  };
  protoOf(FastIdentityCacheMap).get_fast1K_1hx0as_k$ = function () {
    return this.fast1K_1;
  };
  protoOf(FastIdentityCacheMap).set_fast1V_iyt20h_k$ = function (_set____db54di) {
    this.fast1V_1 = _set____db54di;
  };
  protoOf(FastIdentityCacheMap).get_fast1V_nrz92p_k$ = function () {
    return this.fast1V_1;
  };
  protoOf(FastIdentityCacheMap).set_fast2K_sibyfc_k$ = function (_set____db54di) {
    this.fast2K_1 = _set____db54di;
  };
  protoOf(FastIdentityCacheMap).get_fast2K_i2m8bh_k$ = function () {
    return this.fast2K_1;
  };
  protoOf(FastIdentityCacheMap).set_fast2V_j6v3zm_k$ = function (_set____db54di) {
    this.fast2V_1 = _set____db54di;
  };
  protoOf(FastIdentityCacheMap).get_fast2V_rolka6_k$ = function () {
    return this.fast2V_1;
  };
  protoOf(FastIdentityCacheMap).set_cache_xzvrmq_k$ = function (_set____db54di) {
    this.cache_1 = _set____db54di;
  };
  protoOf(FastIdentityCacheMap).get_cache_w0nr7m_k$ = function () {
    return this.cache_1;
  };
  protoOf(FastIdentityCacheMap).get_size_woubt6_k$ = function () {
    return get_size_0(this.cache_1);
  };
  protoOf(FastIdentityCacheMap).getOrPut_f7bjsi_k$ = function (key, gen) {
    if (key === this.fast1K_1)
      return ensureNotNull(this.fast1V_1);
    if (key === this.fast2K_1)
      return ensureNotNull(this.fast2V_1);
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure.getOrPut' call
      var this_0 = this.cache_1;
      var res = get(this_0, key);
      if (!(res == null)) {
        tmp$ret$0 = res;
        break $l$block;
      }
      // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut.<anonymous>' call
      var out = gen(key);
      set_0(this_0, key, out);
      tmp$ret$0 = out;
    }
    var value = tmp$ret$0;
    this.fast2K_1 = this.fast1K_1;
    this.fast2V_1 = this.fast1V_1;
    this.fast1K_1 = key;
    this.fast1V_1 = value;
    return value;
  };
  protoOf(FastIdentityCacheMap).get_wei43m_k$ = function (key) {
    if (key === this.fast1K_1)
      return this.fast1V_1;
    if (key === this.fast2K_1)
      return this.fast2V_1;
    return get(this.cache_1, key);
  };
  protoOf(FastIdentityCacheMap).remove_cykeqk_k$ = function (key) {
    this.clearFast_53bfi_k$();
    remove(this.cache_1, key);
  };
  protoOf(FastIdentityCacheMap).clear_j9egeb_k$ = function () {
    this.clearFast_53bfi_k$();
    clear_1(this.cache_1);
  };
  protoOf(FastIdentityCacheMap).clearFast_53bfi_k$ = function () {
    this.fast1K_1 = null;
    this.fast1V_1 = null;
    this.fast2K_1 = null;
    this.fast2V_1 = null;
  };
  function get_values(_this__u8e3s4) {
    return values_0(_this__u8e3s4);
  }
  function get_keys(_this__u8e3s4) {
    return keys(_this__u8e3s4);
  }
  function values(_this__u8e3s4) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = keys(_this__u8e3s4);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.values.<anonymous>' call
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$2 = _this__u8e3s4.get(item);
      destination.add_utx5q5_k$(tmp$ret$2);
    }
    var tmp = destination;
    return isInterface(tmp, List) ? tmp : THROW_CCE();
  }
  function get_keys_0(_this__u8e3s4) {
    return keys_0(_this__u8e3s4);
  }
  function get_values_0(_this__u8e3s4) {
    return values_1(_this__u8e3s4);
  }
  function getOrPut(_this__u8e3s4, key, callback) {
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var res = _this__u8e3s4.get(key);
    if (!(res == null))
      return res;
    var out = callback();
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.set(key, out);
    return out;
  }
  function fastForEach(_this__u8e3s4, callback) {
    // Inline function 'korlibs.datastructure.fastKeyForEach' call
    // Inline function 'kotlin.js.asDynamic' call
    var mapIterator = _this__u8e3s4.keys();
    $l$loop: while (true) {
      var v = mapIterator.next();
      if (v.done)
        break $l$loop;
      // Inline function 'korlibs.datastructure.fastForEach.<anonymous>' call
      var it = v.value;
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$2 = _this__u8e3s4.get(it);
      callback(it, ensureNotNull(tmp$ret$2));
    }
  }
  function fastForEach_0(_this__u8e3s4, callback) {
    // Inline function 'korlibs.datastructure.fastKeyForEach' call
    // Inline function 'kotlin.js.asDynamic' call
    var mapIterator = _this__u8e3s4.keys();
    $l$loop: while (true) {
      var v = mapIterator.next();
      if (v.done)
        break $l$loop;
      // Inline function 'korlibs.datastructure.fastForEach.<anonymous>' call
      var it = v.value;
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$2 = _this__u8e3s4.get(it);
      callback(it, ensureNotNull(tmp$ret$2));
    }
  }
  function getOrPut_0(_this__u8e3s4, key, callback) {
    var res = get(_this__u8e3s4, key);
    if (!(res == null))
      return res;
    var out = callback(key);
    set_0(_this__u8e3s4, key, out);
    return out;
  }
  function values_0(_this__u8e3s4) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = keys_1(_this__u8e3s4);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.values.<anonymous>' call
      var tmp$ret$0 = get(_this__u8e3s4, item);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    var tmp = destination;
    return isInterface(tmp, List) ? tmp : THROW_CCE();
  }
  function values_1(_this__u8e3s4) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = keys_0(_this__u8e3s4);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.values.<anonymous>' call
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$2 = _this__u8e3s4.get(item);
      destination.add_utx5q5_k$(tmp$ret$2);
    }
    var tmp = destination;
    return isInterface(tmp, List) ? tmp : THROW_CCE();
  }
  function _set_fast0__ecfdyp($this, _set____db54di) {
    $this.fast0__1 = _set____db54di;
  }
  function _get_fast0__hop2ir($this) {
    return $this.fast0__1;
  }
  function _set_fast1__ecfdzk($this, _set____db54di) {
    $this.fast1__1 = _set____db54di;
  }
  function _get_fast1__hop2hw($this) {
    return $this.fast1__1;
  }
  function _set_fast2__ecfe0f($this, _set____db54di) {
    $this.fast2__1 = _set____db54di;
  }
  function _get_fast2__hop2h1($this) {
    return $this.fast2__1;
  }
  function FastSmallSet() {
    AbstractMutableSet.call(this);
    this._items_1 = LinkedHashSet_init_$Create$();
    this.fast0__1 = null;
    this.fast1__1 = null;
    this.fast2__1 = null;
  }
  protoOf(FastSmallSet).get__items_qb94rz_k$ = function () {
    return this._items_1;
  };
  protoOf(FastSmallSet).get_size_woubt6_k$ = function () {
    return this._items_1.get_size_woubt6_k$();
  };
  protoOf(FastSmallSet).iterator_jk1svi_k$ = function () {
    return this._items_1.iterator_jk1svi_k$();
  };
  protoOf(FastSmallSet).add_wl2rvy_k$ = function (element) {
    if (this.contains_ccp5tc_k$(element))
      return false;
    this._items_1.add_utx5q5_k$(element);
    return true;
  };
  protoOf(FastSmallSet).add_utx5q5_k$ = function (element) {
    return this.add_wl2rvy_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(FastSmallSet).remove_an8aut_k$ = function (element) {
    this.fast0__1 = null;
    this.fast1__1 = null;
    this.fast2__1 = null;
    this._items_1.remove_cedx0m_k$(element);
    return true;
  };
  protoOf(FastSmallSet).remove_cedx0m_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.remove_an8aut_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(FastSmallSet).contains_ccp5tc_k$ = function (element) {
    if ((element === this.fast0__1 ? true : element === this.fast1__1) ? true : element === this.fast0__1)
      return true;
    var result = this._items_1.contains_aljjnj_k$(element);
    if (result) {
      this.fast1__1 = this.fast0__1;
      this.fast2__1 = this.fast1__1;
      this.fast0__1 = element;
    }
    return result;
  };
  protoOf(FastSmallSet).contains_aljjnj_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.contains_ccp5tc_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(FastSmallSet).clear_j9egeb_k$ = function () {
    this._items_1.clear_j9egeb_k$();
    this.fast0__1 = null;
    this.fast1__1 = null;
    this.fast2__1 = null;
  };
  protoOf(FastSmallSet).fastForEach_2p7l2l_k$ = function (block) {
    // Inline function 'kotlin.collections.forEach' call
    var tmp0_iterator = this._items_1.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastSmallSet.fastForEach.<anonymous>' call
      block(element);
    }
  };
  function IntFloatMap$_get_keys_$o$iterator$lambda_jjtwb4($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntFloatMap$_get_keys_$o$iterator$lambda_jjtwb4_0($it) {
    return function () {
      return $it.nextKey_ujosq4_k$();
    };
  }
  function IntFloatMap$_get_values_$o$iterator$lambda_de4lz2($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntFloatMap$_get_values_$o$iterator$lambda_de4lz2_0($it) {
    return function () {
      return $it.nextValue_jzf51a_k$();
    };
  }
  function IntFloatMap$_get_entries_$o$iterator$lambda_alj7e8($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntFloatMap$_get_entries_$o$iterator$lambda_alj7e8_0($it) {
    return function () {
      return $it.nextEntry_jqb3nj_k$();
    };
  }
  function _set_nbits__i55tg1($this, _set____db54di) {
    $this.nbits_1 = _set____db54di;
  }
  function _get_nbits__dvyn1f($this) {
    return $this.nbits_1;
  }
  function _get_loadFactor__daomdy($this) {
    return $this.loadFactor_1;
  }
  function IntFloatMap_init_$Init$(loadFactor, $this) {
    loadFactor = loadFactor === VOID ? 0.75 : loadFactor;
    IntFloatMap.call($this, 4, loadFactor);
    return $this;
  }
  function IntFloatMap_init_$Create$(loadFactor) {
    return IntFloatMap_init_$Init$(loadFactor, objectCreate(protoOf(IntFloatMap)));
  }
  function Companion_7() {
    Companion_instance_7 = this;
  }
  protoOf(Companion_7).adapt_j9w6kd_k$ = function (_this__u8e3s4) {
    return toRawBits(_this__u8e3s4);
  };
  protoOf(Companion_7).adapt_ng2okd_k$ = function (_this__u8e3s4) {
    // Inline function 'kotlin.fromBits' call
    FloatCompanionObject_getInstance();
    return floatFromBits(_this__u8e3s4);
  };
  var Companion_instance_7;
  function Companion_getInstance_7() {
    if (Companion_instance_7 == null)
      new Companion_7();
    return Companion_instance_7;
  }
  function Entry(key, value) {
    this.key_1 = key;
    this.value_1 = value;
  }
  protoOf(Entry).set_key_oop0sm_k$ = function (_set____db54di) {
    this.key_1 = _set____db54di;
  };
  protoOf(Entry).get_key_18j28a_k$ = function () {
    return this.key_1;
  };
  protoOf(Entry).set_value_bgphas_k$ = function (_set____db54di) {
    this.value_1 = _set____db54di;
  };
  protoOf(Entry).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  protoOf(Entry).component1_7eebsc_k$ = function () {
    return this.key_1;
  };
  protoOf(Entry).component2_7eebsb_k$ = function () {
    return this.value_1;
  };
  protoOf(Entry).copy_wulmej_k$ = function (key, value) {
    return new Entry(key, value);
  };
  protoOf(Entry).copy$default_v4ss2p_k$ = function (key, value, $super) {
    key = key === VOID ? this.key_1 : key;
    value = value === VOID ? this.value_1 : value;
    return $super === VOID ? this.copy_wulmej_k$(key, value) : $super.copy_wulmej_k$.call(this, key, value);
  };
  protoOf(Entry).toString = function () {
    return 'Entry(key=' + this.key_1 + ', value=' + this.value_1 + ')';
  };
  protoOf(Entry).hashCode = function () {
    var result = this.key_1;
    result = imul(result, 31) + getNumberHashCode(this.value_1) | 0;
    return result;
  };
  protoOf(Entry).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Entry))
      return false;
    var tmp0_other_with_cast = other instanceof Entry ? other : THROW_CCE();
    if (!(this.key_1 === tmp0_other_with_cast.key_1))
      return false;
    if (!equals(this.value_1, tmp0_other_with_cast.value_1))
      return false;
    return true;
  };
  function Iterator_0(map) {
    this.map_1 = map;
    this.intIterator_1 = new Iterator_1(this.map_1.map_1);
  }
  protoOf(Iterator_0).get_map_18j0ul_k$ = function () {
    return this.map_1;
  };
  protoOf(Iterator_0).get_intIterator_kvbugc_k$ = function () {
    return this.intIterator_1;
  };
  protoOf(Iterator_0).hasNext_bitz1p_k$ = function () {
    return this.intIterator_1.hasNext_bitz1p_k$();
  };
  protoOf(Iterator_0).nextEntry_jqb3nj_k$ = function () {
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Iterator.nextEntry.<anonymous>' call
    var it = this.intIterator_1.nextEntry_jqb3nj_k$();
    return new Entry(it.get_key_18j28a_k$(), Companion_getInstance_7().adapt_ng2okd_k$(it.get_value_j01efc_k$()));
  };
  protoOf(Iterator_0).nextKey_ujosq4_k$ = function () {
    return Companion_getInstance_7().adapt_ng2okd_k$(this.intIterator_1.nextKey_ujosq4_k$());
  };
  protoOf(Iterator_0).nextValue_jzf51a_k$ = function () {
    return Companion_getInstance_7().adapt_ng2okd_k$(this.intIterator_1.nextValue_jzf51a_k$());
  };
  function IntFloatMap$getOrPut$lambda($callback) {
    return function () {
      return Companion_getInstance_7().adapt_j9w6kd_k$($callback());
    };
  }
  function _no_name_provided__qut3iv(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntFloatMap.<get-keys>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntFloatMap.<get-keys>.<anonymous>.<anonymous>' call
    var it = new Iterator_0(this.this$0__1);
    var tmp = IntFloatMap$_get_keys_$o$iterator$lambda_jjtwb4(it);
    return Iterator_3(tmp, IntFloatMap$_get_keys_$o$iterator$lambda_jjtwb4_0(it));
  };
  function _no_name_provided__qut3iv_0(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_0).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntFloatMap.<get-values>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntFloatMap.<get-values>.<anonymous>.<anonymous>' call
    var it = new Iterator_0(this.this$0__1);
    var tmp = IntFloatMap$_get_values_$o$iterator$lambda_de4lz2(it);
    return Iterator_3(tmp, IntFloatMap$_get_values_$o$iterator$lambda_de4lz2_0(it));
  };
  function _no_name_provided__qut3iv_1(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_1).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntFloatMap.<get-entries>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntFloatMap.<get-entries>.<anonymous>.<anonymous>' call
    var it = new Iterator_0(this.this$0__1);
    var tmp = IntFloatMap$_get_entries_$o$iterator$lambda_alj7e8(it);
    return Iterator_3(tmp, IntFloatMap$_get_entries_$o$iterator$lambda_alj7e8_0(it));
  };
  function IntFloatMap(nbits, loadFactor) {
    Companion_getInstance_7();
    this.nbits_1 = nbits;
    this.loadFactor_1 = loadFactor;
    this.map_1 = IntIntMap_init_$Create$(this.nbits_1, this.loadFactor_1);
  }
  protoOf(IntFloatMap).get_map_vsme3s_k$ = function () {
    return this.map_1;
  };
  protoOf(IntFloatMap).get_size_woubt6_k$ = function () {
    return this.map_1.get_size_woubt6_k$();
  };
  protoOf(IntFloatMap).contains_7q95ev_k$ = function (key) {
    return this.map_1.contains_7q95ev_k$(key);
  };
  protoOf(IntFloatMap).remove_cqondg_k$ = function (key) {
    return this.map_1.remove_cqondg_k$(key);
  };
  protoOf(IntFloatMap).clear_j9egeb_k$ = function () {
    return this.map_1.clear_j9egeb_k$();
  };
  protoOf(IntFloatMap).get_c1px32_k$ = function (key) {
    return Companion_getInstance_7().adapt_ng2okd_k$(this.map_1.get_c1px32_k$(key));
  };
  protoOf(IntFloatMap).set_g2yi9q_k$ = function (key, value) {
    return Companion_getInstance_7().adapt_ng2okd_k$(this.map_1.set_tq3pjy_k$(key, Companion_getInstance_7().adapt_j9w6kd_k$(value)));
  };
  protoOf(IntFloatMap).getOrPut_po64dp_k$ = function (key, callback) {
    var tmp = Companion_getInstance_7();
    return tmp.adapt_ng2okd_k$(this.map_1.getOrPut_5k9000_k$(key, IntFloatMap$getOrPut$lambda(callback)));
  };
  protoOf(IntFloatMap).get_keys_wop4xp_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv(this);
  };
  protoOf(IntFloatMap).get_values_ksazhn_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_0(this);
  };
  protoOf(IntFloatMap).get_entries_p20ztl_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_1(this);
  };
  protoOf(IntFloatMap).get_pooledKeys_4ra1ig_k$ = function () {
    return this.get_keys_wop4xp_k$();
  };
  protoOf(IntFloatMap).get_pooledValues_l2sdtm_k$ = function () {
    return this.get_values_ksazhn_k$();
  };
  protoOf(IntFloatMap).get_pooledEntries_qr7l0s_k$ = function () {
    return this.get_entries_p20ztl_k$();
  };
  protoOf(IntFloatMap).fastKeyForEach_srzx1i_k$ = function (callback) {
    var this_0 = this.map_1;
    var index = this_0.get_hasZero_7r5aaq_k$() ? 2147483647 : this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntFloatMap.fastKeyForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this_0.get__keys_z80zht_k$()[index];
          break;
      }
      callback(it);
      index = this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), index === 2147483647 ? 0 : index + 1 | 0);
    }
    return Unit_getInstance();
  };
  protoOf(IntFloatMap).fastValueForEach_nukeqd_k$ = function (callback) {
    var this_0 = this.map_1;
    var index = this_0.get_hasZero_7r5aaq_k$() ? 2147483647 : this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastValueForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this_0.get__keys_z80zht_k$()[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntFloatMap.fastValueForEach.<anonymous>' call
      var it_0 = this_0.get_c1px32_k$(it);
      callback(Companion_getInstance_7().adapt_ng2okd_k$(it_0));
      index = this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), index === 2147483647 ? 0 : index + 1 | 0);
    }
    return Unit_getInstance();
  };
  protoOf(IntFloatMap).fastForEach_w1kswp_k$ = function (callback) {
    var this_0 = this.map_1;
    var index = this_0.get_hasZero_7r5aaq_k$() ? 2147483647 : this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this_0.get__keys_z80zht_k$()[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntFloatMap.fastForEach.<anonymous>' call
      var key = it;
      var value = this_0.get_c1px32_k$(it);
      callback(key, Companion_getInstance_7().adapt_ng2okd_k$(value));
      index = this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), index === 2147483647 ? 0 : index + 1 | 0);
    }
    return Unit_getInstance();
  };
  protoOf(IntFloatMap).equals = function (other) {
    var tmp;
    if (other instanceof IntFloatMap) {
      tmp = this.map_1.equals(other.map_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(IntFloatMap).hashCode = function () {
    return this.map_1.hashCode();
  };
  function memoize(_this__u8e3s4) {
    var func = _this__u8e3s4;
    var set = {_v: false};
    var cached = {_v: null};
    return memoize$lambda(set, cached, func);
  }
  function memoize$lambda($set, $cached, $func) {
    return function () {
      var tmp;
      if (!$set._v) {
        $cached._v = $func();
        $set._v = true;
        tmp = Unit_getInstance();
      }
      var tmp_0;
      if ($cached._v == null) {
        throwUninitializedPropertyAccessException('cached');
      } else {
        tmp_0 = $cached._v;
      }
      return tmp_0;
    };
  }
  function translateIndex(_this__u8e3s4, $this) {
    if (!(0 <= _this__u8e3s4 ? _this__u8e3s4 < $this.get_size_woubt6_k$() : false))
      throw IndexOutOfBoundsException_init_$Create$('' + _this__u8e3s4);
    return $this.start_1 + _this__u8e3s4 | 0;
  }
  function GenericSubList(base, start, end) {
    this.base_1 = base;
    this.start_1 = start;
    this.end_1 = end;
    var containsUpper = this.base_1.get_size_woubt6_k$();
    var containsArg = this.start_1;
    if (!(0 <= containsArg ? containsArg <= containsUpper : false))
      throw IndexOutOfBoundsException_init_$Create$('' + this.start_1);
    var containsUpper_0 = this.base_1.get_size_woubt6_k$();
    var containsArg_0 = this.end_1;
    if (!(0 <= containsArg_0 ? containsArg_0 <= containsUpper_0 : false))
      throw IndexOutOfBoundsException_init_$Create$('' + this.end_1);
  }
  protoOf(GenericSubList).get_base_wojaxm_k$ = function () {
    return this.base_1;
  };
  protoOf(GenericSubList).get_start_iypx6h_k$ = function () {
    return this.start_1;
  };
  protoOf(GenericSubList).get_end_18j6ha_k$ = function () {
    return this.end_1;
  };
  protoOf(GenericSubList).get_size_woubt6_k$ = function () {
    return this.end_1 - this.start_1 | 0;
  };
  protoOf(GenericSubList).contains_ccp5tc_k$ = function (element) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var this_0 = until(0, this.get_size_woubt6_k$());
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element_0 = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.GenericSubList.contains.<anonymous>' call
        if (equals(this.get_c1px32_k$(element_0), element)) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(GenericSubList).contains_aljjnj_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.contains_ccp5tc_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(GenericSubList).containsAll_70schq_k$ = function (elements) {
    var elementsSet = toMutableSet(elements);
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'kotlin.collections.minusAssign' call
        var element = this.get_c1px32_k$(n);
        elementsSet.remove_cedx0m_k$(element);
      }
       while (inductionVariable < last);
    return elementsSet.isEmpty_y1axqb_k$();
  };
  protoOf(GenericSubList).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_70schq_k$(elements);
  };
  protoOf(GenericSubList).get_c1px32_k$ = function (index) {
    return this.base_1.get_c1px32_k$(translateIndex(index, this));
  };
  protoOf(GenericSubList).indexOf_u97212_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (equals(this.get_c1px32_k$(n), element))
          return n;
      }
       while (inductionVariable < last);
    return -1;
  };
  protoOf(GenericSubList).indexOf_si1fv9_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return -1;
    return this.indexOf_u97212_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(GenericSubList).lastIndexOf_wtunlo_k$ = function (element) {
    var inductionVariable = this.get_size_woubt6_k$() - 1 | 0;
    if (0 <= inductionVariable)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        if (equals(this.get_c1px32_k$(n), element))
          return n;
      }
       while (0 <= inductionVariable);
    return -1;
  };
  protoOf(GenericSubList).lastIndexOf_v2p1fv_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return -1;
    return this.lastIndexOf_wtunlo_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(GenericSubList).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(GenericSubList).iterator_jk1svi_k$ = function () {
    return new GenericListIterator(this);
  };
  protoOf(GenericSubList).listIterator_xjshxw_k$ = function () {
    return new GenericListIterator(this);
  };
  protoOf(GenericSubList).listIterator_70e65o_k$ = function (index) {
    return new GenericListIterator(this, index);
  };
  protoOf(GenericSubList).subList_xle3r2_k$ = function (fromIndex, toIndex) {
    return new GenericSubList(this, fromIndex, toIndex);
  };
  protoOf(GenericSubList).toString = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, this.get_size_woubt6_k$());
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.GenericSubList.toString.<anonymous>' call
      var tmp$ret$0 = this.get_c1px32_k$(item);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    return toString(destination);
  };
  protoOf(GenericSubList).equals = function (other) {
    var tmp;
    if (other instanceof GenericSubList) {
      var tmp$ret$1;
      $l$block: {
        // Inline function 'korlibs.datastructure.internal.equaler' call
        var count = this.get_size_woubt6_k$();
        var inductionVariable = 0;
        if (inductionVariable < count)
          do {
            var n = inductionVariable;
            inductionVariable = inductionVariable + 1 | 0;
            // Inline function 'korlibs.datastructure.GenericSubList.equals.<anonymous>' call
            if (!equals(this.get_c1px32_k$(n), other.get_c1px32_k$(n))) {
              tmp$ret$1 = false;
              break $l$block;
            }
          }
           while (inductionVariable < count);
        tmp$ret$1 = true;
      }
      tmp = tmp$ret$1;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(GenericSubList).hashCode = function () {
    // Inline function 'korlibs.datastructure.internal.hashCoder' call
    var count = this.get_size_woubt6_k$();
    var out = 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out = imul(out, 7);
        var tmp = out;
        // Inline function 'korlibs.datastructure.GenericSubList.hashCode.<anonymous>' call
        // Inline function 'kotlin.hashCode' call
        var tmp0_safe_receiver = this.get_c1px32_k$(n);
        var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
        out = tmp + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0;
      }
       while (inductionVariable < count);
    return out;
  };
  function _set_index__fyfqnn($this, _set____db54di) {
    $this.index_1 = _set____db54di;
  }
  function _get_index__g2optt($this) {
    return $this.index_1;
  }
  function GenericListIterator(list, iindex) {
    iindex = iindex === VOID ? 0 : iindex;
    this.list_1 = list;
    this.iindex_1 = iindex;
    var containsUpper = this.list_1.get_size_woubt6_k$();
    var containsArg = this.iindex_1;
    if (!(0 <= containsArg ? containsArg < containsUpper : false))
      throw IndexOutOfBoundsException_init_$Create$('' + this.iindex_1);
    this.index_1 = this.iindex_1;
  }
  protoOf(GenericListIterator).get_list_wopuqv_k$ = function () {
    return this.list_1;
  };
  protoOf(GenericListIterator).get_iindex_er56he_k$ = function () {
    return this.iindex_1;
  };
  protoOf(GenericListIterator).hasNext_bitz1p_k$ = function () {
    return this.index_1 < this.list_1.get_size_woubt6_k$();
  };
  protoOf(GenericListIterator).next_20eer_k$ = function () {
    if (!this.hasNext_bitz1p_k$())
      throw NoSuchElementException_init_$Create$();
    var _unary__edvuaz = this.index_1;
    this.index_1 = _unary__edvuaz + 1 | 0;
    return this.list_1.get_c1px32_k$(_unary__edvuaz);
  };
  protoOf(GenericListIterator).hasPrevious_qh0629_k$ = function () {
    return this.index_1 > 0;
  };
  protoOf(GenericListIterator).nextIndex_jshxun_k$ = function () {
    return this.index_1;
  };
  protoOf(GenericListIterator).previous_l2dfd5_k$ = function () {
    if (!this.hasPrevious_qh0629_k$())
      throw NoSuchElementException_init_$Create$();
    this.index_1 = this.index_1 - 1 | 0;
    return this.list_1.get_c1px32_k$(this.index_1);
  };
  protoOf(GenericListIterator).previousIndex_4qtyw5_k$ = function () {
    return this.index_1 - 1 | 0;
  };
  protoOf(GenericListIterator).equals = function (other) {
    var tmp;
    var tmp_0;
    if (other instanceof GenericListIterator) {
      tmp_0 = equals(this.list_1, other.list_1);
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = this.index_1 === other.index_1;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(GenericListIterator).hashCode = function () {
    return hashCode(this.list_1) + this.index_1 | 0;
  };
  function _get_deque__ikmcwf($this) {
    return $this.deque_1;
  }
  function _set_position__5hlfea($this, _set____db54di) {
    $this.position_1 = _set____db54di;
  }
  function _get_position__iahqv2($this) {
    return $this.position_1;
  }
  function HistoryStack(maxLength, initialCapacity) {
    maxLength = maxLength === VOID ? 2147483637 : maxLength;
    initialCapacity = initialCapacity === VOID ? 7 : initialCapacity;
    this.maxLength_1 = maxLength;
    this.deque_1 = new TGenDeque(initialCapacity);
    this.position_1 = 0;
  }
  protoOf(HistoryStack).set_maxLength_s35aed_k$ = function (_set____db54di) {
    this.maxLength_1 = _set____db54di;
  };
  protoOf(HistoryStack).get_maxLength_4knn0f_k$ = function () {
    return this.maxLength_1;
  };
  protoOf(HistoryStack).push_rwg0cl_k$ = function (value) {
    $l$loop: while (true) {
      var tmp;
      // Inline function 'kotlin.collections.isNotEmpty' call
      if (!this.deque_1.isEmpty_y1axqb_k$()) {
        tmp = this.deque_1.get_size_woubt6_k$() > this.position_1;
      } else {
        tmp = false;
      }
      if (!tmp) {
        break $l$loop;
      }
      this.deque_1.removeLast_i5wx8a_k$();
    }
    this.deque_1.add_2y5tg6_k$(value);
    this.position_1 = this.deque_1.get_size_woubt6_k$();
    $l$loop_0: while (true) {
      var tmp_0;
      // Inline function 'kotlin.collections.isNotEmpty' call
      if (!this.deque_1.isEmpty_y1axqb_k$()) {
        tmp_0 = this.deque_1.get_size_woubt6_k$() > this.maxLength_1;
      } else {
        tmp_0 = false;
      }
      if (!tmp_0) {
        break $l$loop_0;
      }
      this.deque_1.removeFirst_58pi0k_k$();
      this.position_1 = this.position_1 - 1 | 0;
    }
  };
  protoOf(HistoryStack).current_imy8hl_k$ = function () {
    return this.deque_1.getOrNull_3jmqaw_k$(this.position_1 - 1 | 0);
  };
  protoOf(HistoryStack).undo_251ic_k$ = function () {
    this.position_1 = coerceAtLeast(this.position_1 - 1 | 0, 0);
    return this.deque_1.getOrNull_3jmqaw_k$(this.position_1 - 1 | 0);
  };
  protoOf(HistoryStack).redo_22xvi_k$ = function () {
    // Inline function 'kotlin.also' call
    var this_0 = this.deque_1.getOrNull_3jmqaw_k$(this.position_1);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.HistoryStack.redo.<anonymous>' call
    this.position_1 = coerceAtMost(this.position_1 + 1 | 0, this.deque_1.get_size_woubt6_k$() + 1 | 0);
    return this_0;
  };
  function each(_this__u8e3s4, callback) {
    var inductionVariable = 0;
    var last = _this__u8e3s4.get_height_e7t92o_k$();
    if (inductionVariable < last)
      do {
        var y = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var inductionVariable_0 = 0;
        var last_0 = _this__u8e3s4.get_width_j0q4yl_k$();
        if (inductionVariable_0 < last_0)
          do {
            var x = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            callback(x, y, _this__u8e3s4.getAt_1svvdj_k$(x, y));
          }
           while (inductionVariable_0 < last_0);
      }
       while (inductionVariable < last);
  }
  function Companion_8() {
    Companion_instance_8 = this;
  }
  protoOf(Companion_8).checkArraySize_6zyia2_k$ = function (width, height, arraySize) {
    // Inline function 'kotlin.check' call
    // Inline function 'kotlin.contracts.contract' call
    if (!(arraySize >= imul(width, height))) {
      // Inline function 'korlibs.datastructure.Companion.checkArraySize.<anonymous>' call
      var message = 'backing array of size=' + arraySize + ', has less elements than ' + width + ' * ' + height;
      throw IllegalStateException_init_$Create$(toString(message));
    }
  };
  var Companion_instance_8;
  function Companion_getInstance_8() {
    if (Companion_instance_8 == null)
      new Companion_8();
    return Companion_instance_8;
  }
  function IArray2$asString$lambda($map) {
    return function (it) {
      var tmp = $map.get_wei43m_k$(it);
      var tmp0_elvis_lhs = tmp == null ? null : tmp.value_1;
      var tmp_0;
      var tmp_1 = tmp0_elvis_lhs;
      if ((tmp_1 == null ? null : new Char(tmp_1)) == null) {
        tmp_0 = _Char___init__impl__6a9atx(32);
      } else {
        tmp_0 = tmp0_elvis_lhs;
      }
      return new Char(tmp_0);
    };
  }
  function IArray2$asString$lambda_0(this$0) {
    return function (y) {
      // Inline function 'kotlin.collections.map' call
      var this_0 = until(0, this$0.get_width_j0q4yl_k$());
      // Inline function 'kotlin.collections.mapTo' call
      var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var item = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IArray2.asString.<anonymous>.<anonymous>' call
        var tmp$ret$0 = this$0.getAt_1svvdj_k$(item, y);
        destination.add_utx5q5_k$(tmp$ret$0);
      }
      return joinToString(destination, ', ');
    };
  }
  function IArray2() {
  }
  function index(_this__u8e3s4, x, y) {
    if (!(0 <= x ? x < _this__u8e3s4.get_width_j0q4yl_k$() : false) ? true : !(0 <= y ? y < _this__u8e3s4.get_height_e7t92o_k$() : false))
      throw IndexOutOfBoundsException_init_$Create$_0();
    return imul(y, _this__u8e3s4.get_width_j0q4yl_k$()) + x | 0;
  }
  function fill(_this__u8e3s4, gen) {
    var n = 0;
    var inductionVariable = 0;
    var last = _this__u8e3s4.get_height_e7t92o_k$();
    if (inductionVariable < last)
      do {
        var y = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var inductionVariable_0 = 0;
        var last_0 = _this__u8e3s4.get_width_j0q4yl_k$();
        if (inductionVariable_0 < last_0)
          do {
            var x = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            _this__u8e3s4.setAt_tlf2vp_k$(n, gen(_this__u8e3s4.getAt_k8n1td_k$(n)));
            n = n + 1 | 0;
          }
           while (inductionVariable_0 < last_0);
      }
       while (inductionVariable < last);
  }
  function identityHashCode(_this__u8e3s4) {
    return anyIdentityHashCode(_this__u8e3s4);
  }
  function _get_instanceToIndex__sd98j5($this) {
    return $this.instanceToIndex_1;
  }
  function IndexedTable() {
    var tmp = this;
    // Inline function 'kotlin.collections.arrayListOf' call
    tmp.instances_1 = ArrayList_init_$Create$_0();
    this.instanceToIndex_1 = LinkedHashMap_init_$Create$();
  }
  protoOf(IndexedTable).get_instances_8zuh1x_k$ = function () {
    return this.instances_1;
  };
  protoOf(IndexedTable).get_size_woubt6_k$ = function () {
    return this.instances_1.get_size_woubt6_k$();
  };
  protoOf(IndexedTable).add_jcyd1a_k$ = function (str) {
    this.get_51jv09_k$(str);
  };
  protoOf(IndexedTable).get_51jv09_k$ = function (str) {
    // Inline function 'kotlin.collections.getOrPut' call
    var this_0 = this.instanceToIndex_1;
    var value = this_0.get_wei43m_k$(str);
    var tmp;
    if (value == null) {
      // Inline function 'korlibs.datastructure.IndexedTable.get.<anonymous>' call
      // Inline function 'kotlin.also' call
      var this_1 = this.instances_1.get_size_woubt6_k$();
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'korlibs.datastructure.IndexedTable.get.<anonymous>.<anonymous>' call
      // Inline function 'kotlin.collections.plusAssign' call
      this.instances_1.add_utx5q5_k$(str);
      var answer = this_1;
      this_0.put_4fpzoq_k$(str, answer);
      tmp = answer;
    } else {
      tmp = value;
    }
    return tmp;
  };
  protoOf(IndexedTable).iterator_jk1svi_k$ = function () {
    return this.instances_1.iterator_jk1svi_k$();
  };
  protoOf(IndexedTable).equals = function (other) {
    var tmp;
    if (other instanceof IndexedTable) {
      tmp = this.instances_1.equals(other.instances_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(IndexedTable).hashCode = function () {
    return this.instances_1.hashCode();
  };
  function _set_index__fyfqnn_0($this, _set____db54di) {
    $this.index_1 = _set____db54di;
  }
  function _get_index__g2optt_0($this) {
    return $this.index_1;
  }
  function _set_entry__e2jqj7($this, _set____db54di) {
    $this.entry_1 = _set____db54di;
  }
  function _get_entry__hykpy9($this) {
    return $this.entry_1;
  }
  function currentEntry($this) {
    $this.entry_1.key_1 = currentKey($this);
    $this.entry_1.value_1 = currentValue($this);
    return $this.entry_1;
  }
  function currentKey($this) {
    switch ($this.index_1) {
      case 2147483647:
      case 2147483646:
        return 0;
      default:
        return $this.map_1._keys_1[$this.index_1];
    }
  }
  function currentValue($this) {
    switch ($this.index_1) {
      case 2147483647:
        return $this.map_1.zeroValue_1;
      case 2147483646:
        return 0;
      default:
        return $this.map_1._values_1[$this.index_1];
    }
  }
  function nextNonEmptyIndex($this, keys, offset) {
    var inductionVariable = offset;
    var last = keys.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(keys[n] === 0))
          return n;
      }
       while (inductionVariable < last);
    return 2147483646;
  }
  function next($this) {
    if (!($this.index_1 === 2147483646)) {
      $this.index_1 = nextNonEmptyIndex($this, $this.map_1._keys_1, $this.index_1 === 2147483647 ? 0 : $this.index_1 + 1 | 0);
    }
  }
  function IntIntMap$_get_keys_$o$iterator$lambda_kpp059($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntIntMap$_get_keys_$o$iterator$lambda_kpp059_0($it) {
    return function () {
      return $it.nextKey_ujosq4_k$();
    };
  }
  function IntIntMap$_get_values_$o$iterator$lambda_5kau39($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntIntMap$_get_values_$o$iterator$lambda_5kau39_0($it) {
    return function () {
      return $it.nextValue_jzf51a_k$();
    };
  }
  function IntIntMap$_get_entries_$o$iterator$lambda_8sv6sd($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntIntMap$_get_entries_$o$iterator$lambda_8sv6sd_0($it) {
    return function () {
      return $it.nextEntry_jqb3nj_k$();
    };
  }
  function _set_nbits__i55tg1_0($this, _set____db54di) {
    $this.nbits_1 = _set____db54di;
  }
  function _get_nbits__dvyn1f_0($this) {
    return $this.nbits_1;
  }
  function _get_loadFactor__daomdy_0($this) {
    return $this.loadFactor_1;
  }
  function IntIntMap_init_$Init$(initialCapacity, loadFactor, $this) {
    initialCapacity = initialCapacity === VOID ? 16 : initialCapacity;
    loadFactor = loadFactor === VOID ? 0.75 : loadFactor;
    // Inline function 'kotlin.math.max' call
    var b = Math_getInstance().ilog2Ceil_kf6ask_k$(initialCapacity);
    var tmp$ret$0 = Math.max(4, b);
    IntIntMap.call($this, tmp$ret$0, loadFactor, true);
    return $this;
  }
  function IntIntMap_init_$Create$(initialCapacity, loadFactor) {
    return IntIntMap_init_$Init$(initialCapacity, loadFactor, objectCreate(protoOf(IntIntMap)));
  }
  function Companion_9() {
    Companion_instance_9 = this;
    this.EOF_1 = 2147483646;
    this.ZERO_INDEX_1 = 2147483647;
    this.EMPTY_1 = 0;
  }
  protoOf(Companion_9).get_EOF_f3ys14_k$ = function () {
    return this.EOF_1;
  };
  protoOf(Companion_9).get_ZERO_INDEX_wjtv93_k$ = function () {
    return this.ZERO_INDEX_1;
  };
  protoOf(Companion_9).get_EMPTY_q5vcyf_k$ = function () {
    return this.EMPTY_1;
  };
  var Companion_instance_9;
  function Companion_getInstance_9() {
    if (Companion_instance_9 == null)
      new Companion_9();
    return Companion_instance_9;
  }
  function _set_capacity__2jc21p($this, _set____db54di) {
    $this.capacity_1 = _set____db54di;
  }
  function _get_capacity__a9k9f3_1($this) {
    return $this.capacity_1;
  }
  function _set_zeroValue__vlcck($this, _set____db54di) {
    $this.zeroValue_1 = _set____db54di;
  }
  function _get_zeroValue__sf9420($this) {
    return $this.zeroValue_1;
  }
  function _set_mask__9qgh63($this, _set____db54di) {
    $this.mask_1 = _set____db54di;
  }
  function _get_mask__da8grj($this) {
    return $this.mask_1;
  }
  function _set_stashSize__2w43fz($this, _set____db54di) {
    $this.stashSize_1 = _set____db54di;
  }
  function _set__values__wkt36s($this, _set____db54di) {
    $this._values_1 = _set____db54di;
  }
  function _get__values__6yksts($this) {
    return $this._values_1;
  }
  function _get_stashStart__3psegu($this) {
    return $this._keys_1.length - $this.stashSize_1 | 0;
  }
  function _set_growSize__daqjz7($this, _set____db54di) {
    $this.growSize_1 = _set____db54di;
  }
  function _get_growSize__hu8if($this) {
    return $this.growSize_1;
  }
  function _set_size__9twho6($this, _set____db54di) {
    $this.size_1 = _set____db54di;
  }
  function grow($this) {
    var inc = $this.nbits_1 < 20 ? 3 : 1;
    var newnbits = $this.nbits_1 + inc | 0;
    var new_0 = new IntIntMap(newnbits, $this.loadFactor_1, true);
    var inductionVariable = 0;
    var last = $this._keys_1.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var k = $this._keys_1[n];
        if (!(k === 0)) {
          new_0.set_tq3pjy_k$(k, $this._values_1[n]);
        }
      }
       while (inductionVariable <= last);
    $this.nbits_1 = new_0.nbits_1;
    $this.capacity_1 = new_0.capacity_1;
    $this.mask_1 = new_0.mask_1;
    $this.stashSize_1 = new_0.stashSize_1;
    $this._keys_1 = new_0._keys_1;
    $this._values_1 = new_0._values_1;
    $this.growSize_1 = new_0.growSize_1;
  }
  function growStash($this) {
    $this.stashSize_1 = imul($this.stashSize_1, 2);
    $this._keys_1 = copyOf_1($this._keys_1, $this.get_backSize_s4jbt6_k$());
    $this._values_1 = copyOf_1($this._values_1, $this.get_backSize_s4jbt6_k$());
  }
  function _getKeyIndex($this, key) {
    if (key === 0)
      return $this.hasZero_1 ? 2147483647 : -1;
    var index1 = hash1($this, key);
    if ($this._keys_1[index1] === key)
      return index1;
    var index2 = hash2($this, key);
    if ($this._keys_1[index2] === key)
      return index2;
    var index3 = hash3($this, key);
    if ($this._keys_1[index3] === key)
      return index3;
    var inductionVariable = _get_stashStart__3psegu($this);
    var last = $this._keys_1.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if ($this._keys_1[n] === key)
          return n;
      }
       while (inductionVariable < last);
    return -1;
  }
  function setEmptySlot($this, index, key, value) {
    if (!($this._keys_1[index] === 0))
      throw IllegalStateException_init_$Create$_0();
    $this._keys_1[index] = key;
    $this._values_1[index] = value;
    $this.size_1 = $this.size_1 + 1 | 0;
    return 0;
  }
  function hash1($this, key) {
    return _hash1(key, $this.mask_1);
  }
  function hash2($this, key) {
    return _hash2(key, $this.mask_1);
  }
  function hash3($this, key) {
    return _hash3(key, $this.mask_1);
  }
  function Entry_0(key, value) {
    this.key_1 = key;
    this.value_1 = value;
  }
  protoOf(Entry_0).set_key_oop0sm_k$ = function (_set____db54di) {
    this.key_1 = _set____db54di;
  };
  protoOf(Entry_0).get_key_18j28a_k$ = function () {
    return this.key_1;
  };
  protoOf(Entry_0).set_value_h293uc_k$ = function (_set____db54di) {
    this.value_1 = _set____db54di;
  };
  protoOf(Entry_0).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  protoOf(Entry_0).component1_7eebsc_k$ = function () {
    return this.key_1;
  };
  protoOf(Entry_0).component2_7eebsb_k$ = function () {
    return this.value_1;
  };
  protoOf(Entry_0).copy_fhtu3_k$ = function (key, value) {
    return new Entry_0(key, value);
  };
  protoOf(Entry_0).copy$default_ptf4i4_k$ = function (key, value, $super) {
    key = key === VOID ? this.key_1 : key;
    value = value === VOID ? this.value_1 : value;
    return $super === VOID ? this.copy_fhtu3_k$(key, value) : $super.copy_fhtu3_k$.call(this, key, value);
  };
  protoOf(Entry_0).toString = function () {
    return 'Entry(key=' + this.key_1 + ', value=' + this.value_1 + ')';
  };
  protoOf(Entry_0).hashCode = function () {
    var result = this.key_1;
    result = imul(result, 31) + this.value_1 | 0;
    return result;
  };
  protoOf(Entry_0).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Entry_0))
      return false;
    var tmp0_other_with_cast = other instanceof Entry_0 ? other : THROW_CCE();
    if (!(this.key_1 === tmp0_other_with_cast.key_1))
      return false;
    if (!(this.value_1 === tmp0_other_with_cast.value_1))
      return false;
    return true;
  };
  function Iterator_1(map) {
    this.map_1 = map;
    this.index_1 = this.map_1.hasZero_1 ? 2147483647 : nextNonEmptyIndex(this, this.map_1._keys_1, 0);
    this.entry_1 = new Entry_0(0, 0);
  }
  protoOf(Iterator_1).get_map_18j0ul_k$ = function () {
    return this.map_1;
  };
  protoOf(Iterator_1).hasNext_bitz1p_k$ = function () {
    return !(this.index_1 === 2147483646);
  };
  protoOf(Iterator_1).nextEntry_jqb3nj_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = currentEntry(this);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Iterator.nextEntry.<anonymous>' call
    next(this);
    return this_0;
  };
  protoOf(Iterator_1).nextKey_ujosq4_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = currentKey(this);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Iterator.nextKey.<anonymous>' call
    next(this);
    return this_0;
  };
  protoOf(Iterator_1).nextValue_jzf51a_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = currentValue(this);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Iterator.nextValue.<anonymous>' call
    next(this);
    return this_0;
  };
  function _no_name_provided__qut3iv_2(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_2).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntIntMap.<get-keys>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntIntMap.<get-keys>.<anonymous>.<anonymous>' call
    var it = new Iterator_1(this.this$0__1);
    var tmp = IntIntMap$_get_keys_$o$iterator$lambda_kpp059(it);
    return Iterator_3(tmp, IntIntMap$_get_keys_$o$iterator$lambda_kpp059_0(it));
  };
  function _no_name_provided__qut3iv_3(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_3).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntIntMap.<get-values>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntIntMap.<get-values>.<anonymous>.<anonymous>' call
    var it = new Iterator_1(this.this$0__1);
    var tmp = IntIntMap$_get_values_$o$iterator$lambda_5kau39(it);
    return Iterator_3(tmp, IntIntMap$_get_values_$o$iterator$lambda_5kau39_0(it));
  };
  function _no_name_provided__qut3iv_4(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_4).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntIntMap.<get-entries>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntIntMap.<get-entries>.<anonymous>.<anonymous>' call
    var it = new Iterator_1(this.this$0__1);
    var tmp = IntIntMap$_get_entries_$o$iterator$lambda_8sv6sd(it);
    return Iterator_3(tmp, IntIntMap$_get_entries_$o$iterator$lambda_8sv6sd_0(it));
  };
  function IntIntMap(nbits, loadFactor, dummy) {
    Companion_getInstance_9();
    this.nbits_1 = nbits;
    this.loadFactor_1 = loadFactor;
    this.capacity_1 = 1 << this.nbits_1;
    this.hasZero_1 = false;
    this.zeroValue_1 = 0;
    this.mask_1 = this.capacity_1 - 1 | 0;
    this.stashSize_1 = 1 + imul(this.nbits_1, this.nbits_1) | 0;
    this._keys_1 = new Int32Array(this.get_backSize_s4jbt6_k$());
    this._values_1 = new Int32Array(this.get_backSize_s4jbt6_k$());
    this.growSize_1 = numberToInt(this.capacity_1 * this.loadFactor_1);
    this.size_1 = 0;
  }
  protoOf(IntIntMap).toString = function () {
    return toString(this.toMap$default_tldzuf_k$());
  };
  protoOf(IntIntMap).set_hasZero_ycglm3_k$ = function (_set____db54di) {
    this.hasZero_1 = _set____db54di;
  };
  protoOf(IntIntMap).get_hasZero_7r5aaq_k$ = function () {
    return this.hasZero_1;
  };
  protoOf(IntIntMap).get_stashSize_plkaky_k$ = function () {
    return this.stashSize_1;
  };
  protoOf(IntIntMap).get_backSize_s4jbt6_k$ = function () {
    return this.capacity_1 + this.stashSize_1 | 0;
  };
  protoOf(IntIntMap).set__keys_ujhduo_k$ = function (_set____db54di) {
    this._keys_1 = _set____db54di;
  };
  protoOf(IntIntMap).get__keys_z80zht_k$ = function () {
    return this._keys_1;
  };
  protoOf(IntIntMap).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(IntIntMap).clone_1keycd_k$ = function () {
    var out = IntIntMap_init_$Create$();
    out.capacity_1 = this.capacity_1;
    out.hasZero_1 = this.hasZero_1;
    out.mask_1 = this.mask_1;
    out.stashSize_1 = this.stashSize_1;
    var tmp = out;
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp._keys_1 = this._keys_1.slice();
    var tmp_0 = out;
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp_0._values_1 = this._values_1.slice();
    out.growSize_1 = this.growSize_1;
    out.size_1 = this.size_1;
    return out;
  };
  protoOf(IntIntMap).toMap_6ifj0l_k$ = function (out) {
    // Inline function 'korlibs.datastructure.IntIntMap.fastForEach' call
    // Inline function 'korlibs.datastructure.IntIntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntIntMap.toMap.<anonymous>' call
      var key = it;
      // Inline function 'kotlin.collections.set' call
      var value = this.get_c1px32_k$(it);
      out.put_4fpzoq_k$(key, value);
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
    return out;
  };
  protoOf(IntIntMap).toMap$default_tldzuf_k$ = function (out, $super) {
    out = out === VOID ? linkedHashMapOf([]) : out;
    return $super === VOID ? this.toMap_6ifj0l_k$(out) : $super.toMap_6ifj0l_k$.call(this, out);
  };
  protoOf(IntIntMap).contains_7q95ev_k$ = function (key) {
    return _getKeyIndex(this, key) >= 0;
  };
  protoOf(IntIntMap).remove_cqondg_k$ = function (key) {
    var index = _getKeyIndex(this, key);
    if (index < 0)
      return false;
    if (index === 2147483647) {
      this.hasZero_1 = false;
      this.zeroValue_1 = 0;
    } else {
      this._keys_1[index] = 0;
    }
    this.size_1 = this.size_1 - 1 | 0;
    return true;
  };
  protoOf(IntIntMap).clear_j9egeb_k$ = function () {
    this.hasZero_1 = false;
    this.zeroValue_1 = 0;
    fill_0(this._keys_1, 0);
    fill_0(this._values_1, 0);
    this.size_1 = 0;
  };
  protoOf(IntIntMap).get_c1px32_k$ = function (key) {
    var index = _getKeyIndex(this, key);
    if (index < 0)
      return 0;
    if (index === 2147483647)
      return this.zeroValue_1;
    return this._values_1[index];
  };
  protoOf(IntIntMap).set_tq3pjy_k$ = function (key, value) {
    retry: while (true) {
      var index = _getKeyIndex(this, key);
      if (index < 0) {
        if (key === 0) {
          this.hasZero_1 = true;
          this.zeroValue_1 = value;
          this.size_1 = this.size_1 + 1 | 0;
          return 0;
        }
        if (this.size_1 >= this.growSize_1) {
          grow(this);
        }
        var index1 = hash1(this, key);
        if (this._keys_1[index1] === 0)
          return setEmptySlot(this, index1, key, value);
        var index2 = hash2(this, key);
        if (this._keys_1[index2] === 0)
          return setEmptySlot(this, index2, key, value);
        var index3 = hash3(this, key);
        if (this._keys_1[index3] === 0)
          return setEmptySlot(this, index3, key, value);
        var inductionVariable = _get_stashStart__3psegu(this);
        var last = this._keys_1.length;
        if (inductionVariable < last)
          do {
            var n = inductionVariable;
            inductionVariable = inductionVariable + 1 | 0;
            if (this._keys_1[n] === 0)
              return setEmptySlot(this, n, key, value);
          }
           while (inductionVariable < last);
        if (this.stashSize_1 > 512) {
          grow(this);
        } else {
          growStash(this);
        }
        continue retry;
      } else if (index === 2147483647) {
        // Inline function 'kotlin.apply' call
        var this_0 = this.zeroValue_1;
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.IntIntMap.set.<anonymous>' call
        this.zeroValue_1 = value;
        return this_0;
      } else {
        // Inline function 'kotlin.apply' call
        var this_1 = this._values_1[index];
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.IntIntMap.set.<anonymous>' call
        this._values_1[index] = value;
        return this_1;
      }
    }
  };
  protoOf(IntIntMap).getOrPut_5k9000_k$ = function (key, callback) {
    if (!this.contains_7q95ev_k$(key)) {
      this.set_tq3pjy_k$(key, callback());
    }
    return this.get_c1px32_k$(key);
  };
  protoOf(IntIntMap).get_keys_wop4xp_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_2(this);
  };
  protoOf(IntIntMap).get_values_ksazhn_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_3(this);
  };
  protoOf(IntIntMap).get_entries_p20ztl_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_4(this);
  };
  protoOf(IntIntMap).get_pooledKeys_4ra1ig_k$ = function () {
    return this.get_keys_wop4xp_k$();
  };
  protoOf(IntIntMap).get_pooledValues_l2sdtm_k$ = function () {
    return this.get_values_ksazhn_k$();
  };
  protoOf(IntIntMap).get_pooledEntries_qr7l0s_k$ = function () {
    return this.get_entries_p20ztl_k$();
  };
  protoOf(IntIntMap).nextNonEmptyIndex_qs84fl_k$ = function (keys, offset) {
    var inductionVariable = offset;
    var last = keys.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(keys[n] === 0))
          return n;
      }
       while (inductionVariable < last);
    return 2147483646;
  };
  protoOf(IntIntMap).fastKeyForEach_srzx1i_k$ = function (callback) {
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      var tmp29_subject = index;
      callback((tmp29_subject === 2147483647 ? true : tmp29_subject === 2147483646) ? 0 : this._keys_1[index]);
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntIntMap).fastValueForEach_1fj0ig_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.IntIntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastValueForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      callback(this.get_c1px32_k$(it));
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntIntMap).fastForEach_lxmagc_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.IntIntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      callback(it, this.get_c1px32_k$(it));
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntIntMap).equals = function (other) {
    if (!(other instanceof IntIntMap))
      return false;
    // Inline function 'korlibs.datastructure.IntIntMap.fastForEach' call
    // Inline function 'korlibs.datastructure.IntIntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntIntMap.equals.<anonymous>' call
      var key = it;
      var value = this.get_c1px32_k$(it);
      if (!(other.get_c1px32_k$(key) === value))
        return false;
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
    return true;
  };
  protoOf(IntIntMap).hashCode = function () {
    var out = 0;
    // Inline function 'korlibs.datastructure.IntIntMap.fastForEach' call
    // Inline function 'korlibs.datastructure.IntIntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntIntMap.hashCode.<anonymous>' call
      out = out + (it + this.get_c1px32_k$(it) | 0) | 0;
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
    return out;
  };
  function _set_index__fyfqnn_1($this, _set____db54di) {
    $this.index_1 = _set____db54di;
  }
  function _get_index__g2optt_1($this) {
    return $this.index_1;
  }
  function _set_entry__e2jqj7_0($this, _set____db54di) {
    $this.entry_1 = _set____db54di;
  }
  function _get_entry__hykpy9_0($this) {
    return $this.entry_1;
  }
  function currentEntry_0($this) {
    $this.entry_1.key_1 = currentKey_0($this);
    $this.entry_1.value_1 = currentValue_0($this);
    return $this.entry_1;
  }
  function currentKey_0($this) {
    switch ($this.index_1) {
      case 2147483647:
      case 2147483646:
        return 0;
      default:
        return $this.map_1._keys_1[$this.index_1];
    }
  }
  function currentValue_0($this) {
    switch ($this.index_1) {
      case 2147483647:
        return $this.map_1.zeroValue_1;
      case 2147483646:
        return null;
      default:
        return $this.map_1._values_1[$this.index_1];
    }
  }
  function nextNonEmptyIndex_0($this, keys, offset) {
    var inductionVariable = offset;
    var last = keys.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(keys[n] === 0))
          return n;
      }
       while (inductionVariable < last);
    return 2147483646;
  }
  function next_0($this) {
    if (!($this.index_1 === 2147483646)) {
      $this.index_1 = nextNonEmptyIndex_0($this, $this.map_1._keys_1, $this.index_1 === 2147483647 ? 0 : $this.index_1 + 1 | 0);
    }
  }
  function IntMap$_get_keys_$o$iterator$lambda_bn8atq($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntMap$_get_keys_$o$iterator$lambda_bn8atq_0($it) {
    return function () {
      return $it.nextKey_ujosq4_k$();
    };
  }
  function IntMap$_get_values_$o$iterator$lambda_ggo058($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntMap$_get_values_$o$iterator$lambda_ggo058_0($it) {
    return function () {
      return $it.nextValue_jzf51a_k$();
    };
  }
  function IntMap$_get_entries_$o$iterator$lambda_yr4cqa($it) {
    return function () {
      return $it.hasNext_bitz1p_k$();
    };
  }
  function IntMap$_get_entries_$o$iterator$lambda_yr4cqa_0($it) {
    return function () {
      return $it.nextEntry_jqb3nj_k$();
    };
  }
  function _set_nbits__i55tg1_1($this, _set____db54di) {
    $this.nbits_1 = _set____db54di;
  }
  function _get_nbits__dvyn1f_1($this) {
    return $this.nbits_1;
  }
  function _get_loadFactor__daomdy_1($this) {
    return $this.loadFactor_1;
  }
  function IntMap_init_$Init$(initialCapacity, loadFactor, $this) {
    initialCapacity = initialCapacity === VOID ? 16 : initialCapacity;
    loadFactor = loadFactor === VOID ? 0.75 : loadFactor;
    // Inline function 'kotlin.math.max' call
    var b = Math_getInstance().ilog2Ceil_kf6ask_k$(initialCapacity);
    var tmp$ret$0 = Math.max(4, b);
    IntMap.call($this, tmp$ret$0, loadFactor, true);
    return $this;
  }
  function IntMap_init_$Create$(initialCapacity, loadFactor) {
    return IntMap_init_$Init$(initialCapacity, loadFactor, objectCreate(protoOf(IntMap)));
  }
  function Companion_10() {
    Companion_instance_10 = this;
    this.EOF_1 = 2147483646;
    this.ZERO_INDEX_1 = 2147483647;
    this.EMPTY_1 = 0;
  }
  protoOf(Companion_10).get_EOF_f3ys14_k$ = function () {
    return this.EOF_1;
  };
  protoOf(Companion_10).get_ZERO_INDEX_wjtv93_k$ = function () {
    return this.ZERO_INDEX_1;
  };
  protoOf(Companion_10).get_EMPTY_q5vcyf_k$ = function () {
    return this.EMPTY_1;
  };
  var Companion_instance_10;
  function Companion_getInstance_10() {
    if (Companion_instance_10 == null)
      new Companion_10();
    return Companion_instance_10;
  }
  function _set_capacity__2jc21p_0($this, _set____db54di) {
    $this.capacity_1 = _set____db54di;
  }
  function _get_capacity__a9k9f3_2($this) {
    return $this.capacity_1;
  }
  function _set_zeroValue__vlcck_0($this, _set____db54di) {
    $this.zeroValue_1 = _set____db54di;
  }
  function _get_zeroValue__sf9420_0($this) {
    return $this.zeroValue_1;
  }
  function _set_mask__9qgh63_0($this, _set____db54di) {
    $this.mask_1 = _set____db54di;
  }
  function _get_mask__da8grj_0($this) {
    return $this.mask_1;
  }
  function _set_stashSize__2w43fz_0($this, _set____db54di) {
    $this.stashSize_1 = _set____db54di;
  }
  function _set__values__wkt36s_0($this, _set____db54di) {
    $this._values_1 = _set____db54di;
  }
  function _get__values__6yksts_0($this) {
    return $this._values_1;
  }
  function _get_stashStart__3psegu_0($this) {
    return $this._keys_1.length - $this.stashSize_1 | 0;
  }
  function _set_growSize__daqjz7_0($this, _set____db54di) {
    $this.growSize_1 = _set____db54di;
  }
  function _get_growSize__hu8if_0($this) {
    return $this.growSize_1;
  }
  function _set_size__9twho6_0($this, _set____db54di) {
    $this.size_1 = _set____db54di;
  }
  function grow_0($this) {
    var inc = $this.nbits_1 < 20 ? 3 : 1;
    var newnbits = $this.nbits_1 + inc | 0;
    var new_0 = new IntMap(newnbits, $this.loadFactor_1, true);
    var inductionVariable = 0;
    var last = $this._keys_1.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var k = $this._keys_1[n];
        if (!(k === 0)) {
          new_0.set_8cx7ps_k$(k, $this._values_1[n]);
        }
      }
       while (inductionVariable <= last);
    $this.nbits_1 = new_0.nbits_1;
    $this.capacity_1 = new_0.capacity_1;
    $this.mask_1 = new_0.mask_1;
    $this.stashSize_1 = new_0.stashSize_1;
    $this._keys_1 = new_0._keys_1;
    $this._values_1 = new_0._values_1;
    $this.growSize_1 = new_0.growSize_1;
  }
  function growStash_0($this) {
    $this.stashSize_1 = imul($this.stashSize_1, 2);
    $this._keys_1 = copyOf_1($this._keys_1, $this.get_backSize_s4jbt6_k$());
    $this._values_1 = copyOf_5($this._values_1, $this.get_backSize_s4jbt6_k$());
  }
  function _getKeyIndex_0($this, key) {
    if (key === 0)
      return $this.hasZero_1 ? 2147483647 : -1;
    var index1 = hash1_0($this, key);
    if ($this._keys_1[index1] === key)
      return index1;
    var index2 = hash2_0($this, key);
    if ($this._keys_1[index2] === key)
      return index2;
    var index3 = hash3_0($this, key);
    if ($this._keys_1[index3] === key)
      return index3;
    var inductionVariable = _get_stashStart__3psegu_0($this);
    var last = $this._keys_1.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if ($this._keys_1[n] === key)
          return n;
      }
       while (inductionVariable < last);
    return -1;
  }
  function setEmptySlot_0($this, index, key, value) {
    if (!($this._keys_1[index] === 0))
      throw IllegalStateException_init_$Create$_0();
    $this._keys_1[index] = key;
    $this._values_1[index] = value;
    $this.size_1 = $this.size_1 + 1 | 0;
    return null;
  }
  function hash1_0($this, key) {
    return _hash1(key, $this.mask_1);
  }
  function hash2_0($this, key) {
    return _hash2(key, $this.mask_1);
  }
  function hash3_0($this, key) {
    return _hash3(key, $this.mask_1);
  }
  function Entry_1(key, value) {
    this.key_1 = key;
    this.value_1 = value;
  }
  protoOf(Entry_1).set_key_oop0sm_k$ = function (_set____db54di) {
    this.key_1 = _set____db54di;
  };
  protoOf(Entry_1).get_key_18j28a_k$ = function () {
    return this.key_1;
  };
  protoOf(Entry_1).set_value_usdbpe_k$ = function (_set____db54di) {
    this.value_1 = _set____db54di;
  };
  protoOf(Entry_1).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  protoOf(Entry_1).component1_7eebsc_k$ = function () {
    return this.key_1;
  };
  protoOf(Entry_1).component2_7eebsb_k$ = function () {
    return this.value_1;
  };
  protoOf(Entry_1).copy_2cb1c3_k$ = function (key, value) {
    return new Entry_1(key, value);
  };
  protoOf(Entry_1).copy$default_p0t9wl_k$ = function (key, value, $super) {
    key = key === VOID ? this.key_1 : key;
    value = value === VOID ? this.value_1 : value;
    return $super === VOID ? this.copy_2cb1c3_k$(key, value) : $super.copy_2cb1c3_k$.call(this, key, value);
  };
  protoOf(Entry_1).toString = function () {
    return 'Entry(key=' + this.key_1 + ', value=' + toString_0(this.value_1) + ')';
  };
  protoOf(Entry_1).hashCode = function () {
    var result = this.key_1;
    result = imul(result, 31) + (this.value_1 == null ? 0 : hashCode(this.value_1)) | 0;
    return result;
  };
  protoOf(Entry_1).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Entry_1))
      return false;
    var tmp0_other_with_cast = other instanceof Entry_1 ? other : THROW_CCE();
    if (!(this.key_1 === tmp0_other_with_cast.key_1))
      return false;
    if (!equals(this.value_1, tmp0_other_with_cast.value_1))
      return false;
    return true;
  };
  function Iterator_2(map) {
    this.map_1 = map;
    this.index_1 = this.map_1.hasZero_1 ? 2147483647 : nextNonEmptyIndex_0(this, this.map_1._keys_1, 0);
    this.entry_1 = new Entry_1(0, null);
  }
  protoOf(Iterator_2).get_map_18j0ul_k$ = function () {
    return this.map_1;
  };
  protoOf(Iterator_2).hasNext_bitz1p_k$ = function () {
    return !(this.index_1 === 2147483646);
  };
  protoOf(Iterator_2).nextEntry_jqb3nj_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = currentEntry_0(this);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Iterator.nextEntry.<anonymous>' call
    next_0(this);
    return this_0;
  };
  protoOf(Iterator_2).nextKey_ujosq4_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = currentKey_0(this);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Iterator.nextKey.<anonymous>' call
    next_0(this);
    return this_0;
  };
  protoOf(Iterator_2).nextValue_jzf51a_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = currentValue_0(this);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Iterator.nextValue.<anonymous>' call
    next_0(this);
    return this_0;
  };
  function _no_name_provided__qut3iv_5(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_5).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntMap.<get-keys>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntMap.<get-keys>.<anonymous>.<anonymous>' call
    var it = new Iterator_2(this.this$0__1);
    var tmp = IntMap$_get_keys_$o$iterator$lambda_bn8atq(it);
    return Iterator_3(tmp, IntMap$_get_keys_$o$iterator$lambda_bn8atq_0(it));
  };
  function _no_name_provided__qut3iv_6(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_6).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntMap.<get-values>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntMap.<get-values>.<anonymous>.<anonymous>' call
    var it = new Iterator_2(this.this$0__1);
    var tmp = IntMap$_get_values_$o$iterator$lambda_ggo058(it);
    return Iterator_3(tmp, IntMap$_get_values_$o$iterator$lambda_ggo058_0(it));
  };
  function _no_name_provided__qut3iv_7(this$0) {
    this.this$0__1 = this$0;
  }
  protoOf(_no_name_provided__qut3iv_7).iterator_jk1svi_k$ = function () {
    // Inline function 'korlibs.datastructure.IntMap.<get-entries>.<anonymous>' call
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntMap.<get-entries>.<anonymous>.<anonymous>' call
    var it = new Iterator_2(this.this$0__1);
    var tmp = IntMap$_get_entries_$o$iterator$lambda_yr4cqa(it);
    return Iterator_3(tmp, IntMap$_get_entries_$o$iterator$lambda_yr4cqa_0(it));
  };
  function IntMap(nbits, loadFactor, dummy) {
    Companion_getInstance_10();
    dummy = dummy === VOID ? false : dummy;
    this.nbits_1 = nbits;
    this.loadFactor_1 = loadFactor;
    this.capacity_1 = 1 << this.nbits_1;
    this.hasZero_1 = false;
    this.zeroValue_1 = null;
    this.mask_1 = this.capacity_1 - 1 | 0;
    this.stashSize_1 = 1 + imul(this.nbits_1, this.nbits_1) | 0;
    this._keys_1 = new Int32Array(this.get_backSize_s4jbt6_k$());
    var tmp = this;
    // Inline function 'kotlin.arrayOfNulls' call
    var size = this.get_backSize_s4jbt6_k$();
    var tmp_0 = fillArrayVal(Array(size), null);
    tmp._values_1 = isArray(tmp_0) ? tmp_0 : THROW_CCE();
    this.growSize_1 = numberToInt(this.capacity_1 * this.loadFactor_1);
    this.size_1 = 0;
  }
  protoOf(IntMap).set_hasZero_ycglm3_k$ = function (_set____db54di) {
    this.hasZero_1 = _set____db54di;
  };
  protoOf(IntMap).get_hasZero_7r5aaq_k$ = function () {
    return this.hasZero_1;
  };
  protoOf(IntMap).get_stashSize_plkaky_k$ = function () {
    return this.stashSize_1;
  };
  protoOf(IntMap).get_backSize_s4jbt6_k$ = function () {
    return this.capacity_1 + this.stashSize_1 | 0;
  };
  protoOf(IntMap).set__keys_ujhduo_k$ = function (_set____db54di) {
    this._keys_1 = _set____db54di;
  };
  protoOf(IntMap).get__keys_z80zht_k$ = function () {
    return this._keys_1;
  };
  protoOf(IntMap).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(IntMap).contains_7q95ev_k$ = function (key) {
    return _getKeyIndex_0(this, key) >= 0;
  };
  protoOf(IntMap).remove_cqondg_k$ = function (key) {
    var index = _getKeyIndex_0(this, key);
    if (index < 0)
      return false;
    if (index === 2147483647) {
      this.hasZero_1 = false;
      this.zeroValue_1 = null;
    } else {
      this._keys_1[index] = 0;
    }
    this.size_1 = this.size_1 - 1 | 0;
    return true;
  };
  protoOf(IntMap).clear_j9egeb_k$ = function () {
    this.hasZero_1 = false;
    this.zeroValue_1 = null;
    fill_0(this._keys_1, 0);
    fill_1(this._values_1, null);
    this.size_1 = 0;
  };
  protoOf(IntMap).get_c1px32_k$ = function (key) {
    var index = _getKeyIndex_0(this, key);
    if (index < 0)
      return null;
    if (index === 2147483647)
      return this.zeroValue_1;
    return this._values_1[index];
  };
  protoOf(IntMap).set_8cx7ps_k$ = function (key, value) {
    retry: while (true) {
      var index = _getKeyIndex_0(this, key);
      if (index < 0) {
        if (key === 0) {
          this.hasZero_1 = true;
          this.zeroValue_1 = value;
          this.size_1 = this.size_1 + 1 | 0;
          return null;
        }
        if (this.size_1 >= this.growSize_1) {
          grow_0(this);
        }
        var index1 = hash1_0(this, key);
        if (this._keys_1[index1] === 0)
          return setEmptySlot_0(this, index1, key, value);
        var index2 = hash2_0(this, key);
        if (this._keys_1[index2] === 0)
          return setEmptySlot_0(this, index2, key, value);
        var index3 = hash3_0(this, key);
        if (this._keys_1[index3] === 0)
          return setEmptySlot_0(this, index3, key, value);
        var inductionVariable = _get_stashStart__3psegu_0(this);
        var last = this._keys_1.length;
        if (inductionVariable < last)
          do {
            var n = inductionVariable;
            inductionVariable = inductionVariable + 1 | 0;
            if (this._keys_1[n] === 0)
              return setEmptySlot_0(this, n, key, value);
          }
           while (inductionVariable < last);
        if (this.stashSize_1 > 512) {
          grow_0(this);
        } else {
          growStash_0(this);
        }
        continue retry;
      } else if (index === 2147483647) {
        // Inline function 'kotlin.apply' call
        var this_0 = this.zeroValue_1;
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.IntMap.set.<anonymous>' call
        this.zeroValue_1 = value;
        return this_0;
      } else {
        // Inline function 'kotlin.apply' call
        var this_1 = this._values_1[index];
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.IntMap.set.<anonymous>' call
        this._values_1[index] = value;
        return this_1;
      }
    }
  };
  protoOf(IntMap).getOrPut_a3gc07_k$ = function (key, callback) {
    var res = this.get_c1px32_k$(key);
    if (res == null) {
      this.set_8cx7ps_k$(key, callback(key));
    }
    return ensureNotNull(this.get_c1px32_k$(key));
  };
  protoOf(IntMap).removeRange_sm1kzt_k$ = function (src, dst) {
    if ((src <= 0 ? 0 <= dst : false) ? this.hasZero_1 : false) {
      this.size_1 = this.size_1 - 1 | 0;
      this.hasZero_1 = false;
      this.zeroValue_1 = null;
    }
    var inductionVariable = 0;
    var last = this._keys_1.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var key = this._keys_1[n];
        if (!(key === 0) ? src <= key ? key <= dst : false : false) {
          this._keys_1[n] = 0;
          this._values_1[n] = null;
          this.size_1 = this.size_1 - 1 | 0;
        }
      }
       while (inductionVariable <= last);
  };
  protoOf(IntMap).get_keys_wop4xp_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_5(this);
  };
  protoOf(IntMap).get_values_ksazhn_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_6(this);
  };
  protoOf(IntMap).get_entries_p20ztl_k$ = function () {
    // Inline function 'kotlin.collections.Iterable' call
    return new _no_name_provided__qut3iv_7(this);
  };
  protoOf(IntMap).get_pooledKeys_4ra1ig_k$ = function () {
    return this.get_keys_wop4xp_k$();
  };
  protoOf(IntMap).get_pooledValues_l2sdtm_k$ = function () {
    return this.get_values_ksazhn_k$();
  };
  protoOf(IntMap).get_pooledEntries_qr7l0s_k$ = function () {
    return this.get_entries_p20ztl_k$();
  };
  protoOf(IntMap).nextNonEmptyIndex_qs84fl_k$ = function (keys, offset) {
    var inductionVariable = offset;
    var last = keys.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(keys[n] === 0))
          return n;
      }
       while (inductionVariable < last);
    return 2147483646;
  };
  protoOf(IntMap).fastKeyForEach_srzx1i_k$ = function (callback) {
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      var tmp26_subject = index;
      callback((tmp26_subject === 2147483647 ? true : tmp26_subject === 2147483646) ? 0 : this._keys_1[index]);
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntMap).fastValueForEachNullable_xo7lbm_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastValueForEachNullable.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      callback(this.get_c1px32_k$(it));
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntMap).fastForEachNullable_zagyqc_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastForEachNullable.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      callback(it, this.get_c1px32_k$(it));
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntMap).fastValueForEach_gp8ju4_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastValueForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      callback(ensureNotNull(this.get_c1px32_k$(it)));
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntMap).fastForEach_dflnfc_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      callback(it, ensureNotNull(this.get_c1px32_k$(it)));
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntMap).equals = function (other) {
    if (!(other instanceof IntMap))
      return false;
    // Inline function 'korlibs.datastructure.IntMap.fastForEachNullable' call
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastForEachNullable.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntMap.equals.<anonymous>' call
      var key = it;
      var value = this.get_c1px32_k$(it);
      if (!equals(other.get_c1px32_k$(key), value))
        return false;
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
    return true;
  };
  protoOf(IntMap).hashCode = function () {
    var out = 0;
    // Inline function 'korlibs.datastructure.IntMap.fastForEachNullable' call
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastForEachNullable.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntMap.hashCode.<anonymous>' call
      var tmp = out;
      var tmp_0 = it;
      // Inline function 'kotlin.hashCode' call
      var tmp0_safe_receiver = this.get_c1px32_k$(it);
      var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
      out = tmp + (tmp_0 + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0) | 0;
      index = this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
    return out;
  };
  protoOf(IntMap).putAll_b9pyps_k$ = function (other) {
    // Inline function 'korlibs.datastructure.IntMap.fastForEach' call
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = other.hasZero_1 ? 2147483647 : other.nextNonEmptyIndex_qs84fl_k$(other._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = other._keys_1[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntMap.putAll.<anonymous>' call
      var key = it;
      var value = ensureNotNull(other.get_c1px32_k$(it));
      this.set_8cx7ps_k$(key, value);
      index = other.nextNonEmptyIndex_qs84fl_k$(other._keys_1, index === 2147483647 ? 0 : index + 1 | 0);
    }
  };
  protoOf(IntMap).firstKey_93ef01_k$ = function () {
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.firstKey.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      return it;
    }
    // Inline function 'kotlin.error' call
    var message = 'firstKey on empty IntMap';
    throw IllegalStateException_init_$Create$(toString(message));
  };
  protoOf(IntMap).firstValue_2t0oyn_k$ = function () {
    // Inline function 'korlibs.datastructure.IntMap.fastValueForEach' call
    // Inline function 'korlibs.datastructure.IntMap.fastKeyForEach' call
    var index = this.hasZero_1 ? 2147483647 : this.nextNonEmptyIndex_qs84fl_k$(this._keys_1, 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntMap.fastValueForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this._keys_1[index];
          break;
      }
      // Inline function 'korlibs.datastructure.IntMap.firstValue.<anonymous>' call
      return ensureNotNull(this.get_c1px32_k$(it));
    }
    // Inline function 'kotlin.error' call
    var message = 'firstValue on empty IntMap';
    throw IllegalStateException_init_$Create$(toString(message));
  };
  protoOf(IntMap).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.also' call
    var this_0 = new IntMap(this.nbits_1, this.loadFactor_1, false);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.IntMap.clone.<anonymous>' call
    this_0.putAll_b9pyps_k$(this);
    return this_0;
  };
  function intMapOf(pairs) {
    // Inline function 'kotlin.also' call
    var this_0 = IntMap_init_$Create$(pairs.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.intMapOf.<anonymous>' call
    // Inline function 'korlibs.datastructure.iterators.fastForEach' call
    var n = 0;
    while (n < pairs.length) {
      // Inline function 'korlibs.datastructure.intMapOf.<anonymous>.<anonymous>' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      var it = pairs[_unary__edvuaz];
      this_0.set_8cx7ps_k$(it.get_first_irdx8n_k$(), it.get_second_jf7fjx_k$());
    }
    return this_0;
  }
  function toMap_0(_this__u8e3s4) {
    // Inline function 'kotlin.collections.associateWith' call
    var this_0 = _this__u8e3s4.get_keys_wop4xp_k$();
    var result = LinkedHashMap_init_$Create$_0(coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_0, 10)), 16));
    // Inline function 'kotlin.collections.associateWithTo' call
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.toMap.<anonymous>' call
      // Inline function 'korlibs.datastructure.fastCastTo' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$3 = _this__u8e3s4.get_c1px32_k$(element);
      result.put_4fpzoq_k$(element, tmp$ret$3);
    }
    return result;
  }
  function intIntMapOf(pairs) {
    // Inline function 'kotlin.also' call
    var this_0 = IntIntMap_init_$Create$(pairs.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.intIntMapOf.<anonymous>' call
    // Inline function 'korlibs.datastructure.iterators.fastForEach' call
    var n = 0;
    while (n < pairs.length) {
      // Inline function 'korlibs.datastructure.intIntMapOf.<anonymous>.<anonymous>' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      var it = pairs[_unary__edvuaz];
      this_0.set_tq3pjy_k$(it.get_first_irdx8n_k$(), it.get_second_jf7fjx_k$());
    }
    return this_0;
  }
  function _hash1(key, mask) {
    return _mask(key, mask);
  }
  function _hash2(key, mask) {
    return _mask(imul(key, 1294968109), mask);
  }
  function _hash3(key, mask) {
    return _mask(imul(key, 294969449), mask);
  }
  function _mask(value, mask) {
    return (((value + ((value >>> 8 | 0) & 255) | 0) + ((value >>> 16 | 0) & 255) | 0) + (value >> 24 & 255) | 0) & mask;
  }
  function _set_data__9licbx_4($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _get_data__d5abxd_2($this) {
    return $this.data_1;
  }
  function IntSet() {
    this.data_1 = IntMap_init_$Create$();
  }
  protoOf(IntSet).get_size_woubt6_k$ = function () {
    return this.data_1.get_size_woubt6_k$();
  };
  protoOf(IntSet).containsAll_tftgie_k$ = function (elements) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.all' call
      var tmp;
      if (isInterface(elements, Collection)) {
        tmp = elements.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = true;
        break $l$block_0;
      }
      var tmp0_iterator = elements.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntSet.containsAll.<anonymous>' call
        if (!this.data_1.contains_7q95ev_k$(element)) {
          tmp$ret$0 = false;
          break $l$block_0;
        }
      }
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  };
  protoOf(IntSet).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_tftgie_k$(elements);
  };
  protoOf(IntSet).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(IntSet).iterator_jk1svi_k$ = function () {
    return asFakeMutable(this.data_1.get_keys_wop4xp_k$().iterator_jk1svi_k$());
  };
  protoOf(IntSet).clear_ihvbz9_k$ = function (maxCapacity) {
    return this.clear_j9egeb_k$();
  };
  protoOf(IntSet).clear_j9egeb_k$ = function () {
    this.data_1.clear_j9egeb_k$();
  };
  protoOf(IntSet).add_lnluon_k$ = function (element) {
    return this.data_1.set_8cx7ps_k$(element, Unit_getInstance()) == null;
  };
  protoOf(IntSet).add_utx5q5_k$ = function (element) {
    return this.add_lnluon_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntSet).addAll_c9pvhk_k$ = function (elements) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var tmp;
      if (isInterface(elements, Collection)) {
        tmp = elements.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = elements.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntSet.addAll.<anonymous>' call
        if (this.add_lnluon_k$(element)) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(IntSet).addAll_4lagoh_k$ = function (elements) {
    return this.addAll_c9pvhk_k$(elements);
  };
  protoOf(IntSet).removeAll_bjaog5_k$ = function (elements) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var tmp;
      if (isInterface(elements, Collection)) {
        tmp = elements.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = elements.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntSet.removeAll.<anonymous>' call
        if (this.remove_cqondg_k$(element)) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(IntSet).removeAll_y0z8pe_k$ = function (elements) {
    return this.removeAll_bjaog5_k$(elements);
  };
  protoOf(IntSet).retainAll_jfkdrw_k$ = function (elements) {
    var oldSize = this.get_size_woubt6_k$();
    var other = IntMap_init_$Create$();
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var it = _iterator__ex2g4s.next_20eer_k$();
      if (this.contains_7q95ev_k$(it)) {
        other.set_8cx7ps_k$(it, Unit_getInstance());
      }
    }
    this.data_1 = other;
    return oldSize === this.get_size_woubt6_k$();
  };
  protoOf(IntSet).retainAll_9fhiib_k$ = function (elements) {
    return this.retainAll_jfkdrw_k$(elements);
  };
  protoOf(IntSet).addAll_3cme1n_k$ = function (elements) {
    var inductionVariable = 0;
    var last = elements.length;
    while (inductionVariable < last) {
      var item = elements[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      this.add_lnluon_k$(item);
    }
  };
  protoOf(IntSet).addAll_5q0gq0_k$ = function (elements) {
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var item = _iterator__ex2g4s.next_20eer_k$();
      this.add_lnluon_k$(item);
    }
  };
  protoOf(IntSet).contains_7q95ev_k$ = function (element) {
    return this.data_1.contains_7q95ev_k$(element);
  };
  protoOf(IntSet).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.contains_7q95ev_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntSet).remove_cqondg_k$ = function (element) {
    return this.data_1.remove_cqondg_k$(element);
  };
  protoOf(IntSet).remove_cedx0m_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.remove_cqondg_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntSet).plusAssign_8mmvnl_k$ = function (value) {
    this.add_lnluon_k$(value);
  };
  protoOf(IntSet).minusAssign_p980fd_k$ = function (value) {
    this.remove_cqondg_k$(value);
  };
  protoOf(IntSet).toString = function () {
    return '[' + joinToString(this.data_1.get_keys_wop4xp_k$(), ', ') + ']';
  };
  protoOf(IntSet).equals = function (other) {
    var tmp;
    if (other instanceof IntSet) {
      tmp = this.data_1.equals(other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(IntSet).hashCode = function () {
    return this.data_1.hashCode();
  };
  function intSetOf(values) {
    // Inline function 'kotlin.apply' call
    var this_0 = new IntSet();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.intSetOf.<anonymous>' call
    var inductionVariable = 0;
    var last = values.length;
    while (inductionVariable < last) {
      var value = values[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      this_0.add_lnluon_k$(value);
    }
    return this_0;
  }
  function Iterator_3(hasNext, next) {
    return new Iterator$1(hasNext, next);
  }
  function Iterator$1($hasNext, $next) {
    this.$hasNext_1 = $hasNext;
    this.$next_1 = $next;
  }
  protoOf(Iterator$1).hasNext_bitz1p_k$ = function () {
    return this.$hasNext_1();
  };
  protoOf(Iterator$1).next_20eer_k$ = function () {
    return this.$next_1();
  };
  function _set_computedMemory__wnwup5($this, _set____db54di) {
    $this.computedMemory_1 = _set____db54di;
  }
  function LRUCache$_init_$lambda_wrqm0(it) {
    return 1;
  }
  function LRUCache(maxSize, maxMemory, atLeastOne, getElementMemory) {
    maxSize = maxSize === VOID ? 2147483647 : maxSize;
    maxMemory = maxMemory === VOID ? new Long(-1, 2147483647) : maxMemory;
    atLeastOne = atLeastOne === VOID ? true : atLeastOne;
    var tmp;
    if (getElementMemory === VOID) {
      tmp = LRUCache$_init_$lambda_wrqm0;
    } else {
      tmp = getElementMemory;
    }
    getElementMemory = tmp;
    BaseCacheMap.call(this);
    this.maxSize_1 = maxSize;
    this.maxMemory_1 = maxMemory;
    this.atLeastOne_1 = atLeastOne;
    this.getElementMemory_1 = getElementMemory;
    this.computedMemory_1 = new Long(0, 0);
  }
  protoOf(LRUCache).get_maxSize_f83j4s_k$ = function () {
    return this.maxSize_1;
  };
  protoOf(LRUCache).get_maxMemory_43mhpg_k$ = function () {
    return this.maxMemory_1;
  };
  protoOf(LRUCache).get_atLeastOne_b4s5if_k$ = function () {
    return this.atLeastOne_1;
  };
  protoOf(LRUCache).get_getElementMemory_cpankg_k$ = function () {
    return this.getElementMemory_1;
  };
  protoOf(LRUCache).get_computedMemory_nuxn61_k$ = function () {
    return this.computedMemory_1;
  };
  protoOf(LRUCache).mustFree_18mgb4_k$ = function (key, value) {
    if (this.atLeastOne_1 ? this.get_size_woubt6_k$() <= 1 : false)
      return false;
    if (this.get_size_woubt6_k$() > this.maxSize_1)
      return true;
    if (this.computedMemory_1.compareTo_9jj042_k$(this.maxMemory_1) > 0)
      return true;
    return false;
  };
  protoOf(LRUCache).putNew_qs4n4s_k$ = function (key, value) {
    var tmp = this;
    // Inline function 'kotlin.Long.plus' call
    var this_0 = this.computedMemory_1;
    var other = this.getElementMemory_1(value);
    tmp.computedMemory_1 = this_0.plus_r93sks_k$(toLong(other));
  };
  protoOf(LRUCache).freed_lk3m6b_k$ = function (key, value) {
    var tmp = this;
    // Inline function 'kotlin.Long.minus' call
    var this_0 = this.computedMemory_1;
    var other = this.getElementMemory_1(value);
    tmp.computedMemory_1 = this_0.minus_mfbszm_k$(toLong(other));
  };
  protoOf(LRUCache).get_wei43m_k$ = function (key) {
    var tmp;
    // Inline function 'kotlin.collections.isNotEmpty' call
    if (!this.get_map_18j0ul_k$().isEmpty_y1axqb_k$()) {
      tmp = equals(last(this.get_map_18j0ul_k$().get_keys_wop4xp_k$()), key);
    } else {
      tmp = false;
    }
    if (tmp)
      return this.get_map_18j0ul_k$().get_wei43m_k$(key);
    var value = this.get_map_18j0ul_k$().remove_gppy8k_k$(key);
    if (!(value == null)) {
      // Inline function 'kotlin.collections.set' call
      this.get_map_18j0ul_k$().put_4fpzoq_k$(key, value);
    }
    return value;
  };
  function ListReader(list) {
    this.list_1 = list;
    this.position_1 = 0;
  }
  protoOf(ListReader).get_list_wopuqv_k$ = function () {
    return this.list_1;
  };
  protoOf(ListReader).set_position_h4ktwi_k$ = function (_set____db54di) {
    this.position_1 = _set____db54di;
  };
  protoOf(ListReader).get_position_jfponi_k$ = function () {
    return this.position_1;
  };
  protoOf(ListReader).get_size_woubt6_k$ = function () {
    return this.list_1.get_size_woubt6_k$();
  };
  protoOf(ListReader).get_eof_18j6gd_k$ = function () {
    return this.position_1 >= this.list_1.get_size_woubt6_k$();
  };
  protoOf(ListReader).get_hasMore_csdhd2_k$ = function () {
    return this.position_1 < this.list_1.get_size_woubt6_k$();
  };
  protoOf(ListReader).peekOrNull_187wat_k$ = function () {
    return getOrNull(this.list_1, this.position_1);
  };
  protoOf(ListReader).peek_21nx7_k$ = function () {
    return this.list_1.get_c1px32_k$(this.position_1);
  };
  protoOf(ListReader).peek_6g603_k$ = function (offset) {
    return this.list_1.get_c1px32_k$(this.position_1 + offset | 0);
  };
  protoOf(ListReader).skip_7luint_k$ = function (count) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ListReader.skip.<anonymous>' call
    this.position_1 = this.position_1 + count | 0;
    return this;
  };
  protoOf(ListReader).skip$default_chl0di_k$ = function (count, $super) {
    count = count === VOID ? 1 : count;
    return $super === VOID ? this.skip_7luint_k$(count) : $super.skip_7luint_k$.call(this, count);
  };
  protoOf(ListReader).read_22xsm_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = this.peek_21nx7_k$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ListReader.read.<anonymous>' call
    this.skip_7luint_k$(1);
    return this_0;
  };
  protoOf(ListReader).toString = function () {
    return 'ListReader(' + toString(this.list_1) + ')';
  };
  protoOf(ListReader).equals = function (other) {
    var tmp;
    var tmp_0;
    if (other instanceof ListReader) {
      tmp_0 = equals(this.list_1, other.list_1);
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = this.position_1 === other.position_1;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(ListReader).hashCode = function () {
    return hashCode(this.list_1);
  };
  function reader(_this__u8e3s4) {
    return new ListReader(_this__u8e3s4);
  }
  function expect(_this__u8e3s4, value) {
    var v = _this__u8e3s4.read_22xsm_k$();
    if (!equals(v, value)) {
      // Inline function 'kotlin.error' call
      var message = "Expecting '" + toString_0(value) + "' but found '" + toString_0(v) + "'";
      throw IllegalStateException_init_$Create$(toString(message));
    }
    return v;
  }
  function linkedHashMapListOf(items) {
    // Inline function 'kotlin.apply' call
    var this_0 = LinkedHashMap_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.linkedHashMapListOf.<anonymous>' call
    var inductionVariable = 0;
    var last = items.length;
    while (inductionVariable < last) {
      var _destruct__k2r9zo = items[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      var k = _destruct__k2r9zo.component1_7eebsc_k$();
      var v = _destruct__k2r9zo.component2_7eebsb_k$();
      append(this_0, k, v);
    }
    return this_0;
  }
  function getFirst_0(_this__u8e3s4, key) {
    var tmp30_safe_receiver = _this__u8e3s4.get_wei43m_k$(key);
    return tmp30_safe_receiver == null ? null : firstOrNull(tmp30_safe_receiver);
  }
  function getLast_0(_this__u8e3s4, key) {
    var tmp31_safe_receiver = _this__u8e3s4.get_wei43m_k$(key);
    return tmp31_safe_receiver == null ? null : lastOrNull(tmp31_safe_receiver);
  }
  function flatten(_this__u8e3s4) {
    return toList(flatMapIterable(_this__u8e3s4));
  }
  function append(_this__u8e3s4, key, value) {
    // Inline function 'kotlin.collections.getOrPut' call
    var value_0 = _this__u8e3s4.get_wei43m_k$(key);
    var tmp;
    if (value_0 == null) {
      // Inline function 'korlibs.datastructure.append.<anonymous>' call
      // Inline function 'kotlin.collections.arrayListOf' call
      var answer = ArrayList_init_$Create$_0();
      _this__u8e3s4.put_4fpzoq_k$(key, answer);
      tmp = answer;
    } else {
      tmp = value_0;
    }
    // Inline function 'kotlin.collections.plusAssign' call
    ensureNotNull(_this__u8e3s4.get_wei43m_k$(key)).add_utx5q5_k$(value);
    return _this__u8e3s4;
  }
  function flatMapIterable(_this__u8e3s4) {
    return new flatMapIterable$1(_this__u8e3s4);
  }
  function flatMapIterator(_this__u8e3s4) {
    // Inline function 'kotlin.collections.flatMap' call
    // Inline function 'kotlin.collections.flatMapTo' call
    var this_0 = _this__u8e3s4.get_entries_p20ztl_k$();
    var destination = ArrayList_init_$Create$_0();
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.flatMapIterator.<anonymous>' call
      // Inline function 'kotlin.collections.map' call
      var this_1 = element.get_value_j01efc_k$();
      // Inline function 'kotlin.collections.mapTo' call
      var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(this_1, 10));
      var tmp0_iterator_0 = this_1.iterator_jk1svi_k$();
      while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
        var item = tmp0_iterator_0.next_20eer_k$();
        // Inline function 'korlibs.datastructure.flatMapIterator.<anonymous>.<anonymous>' call
        var tmp$ret$0 = to(element.get_key_18j28a_k$(), item);
        destination_0.add_utx5q5_k$(tmp$ret$0);
      }
      var list = destination_0;
      addAll(destination, list);
    }
    return destination.iterator_jk1svi_k$();
  }
  function flatMapIterable$1($this_flatMapIterable) {
    this.$this_flatMapIterable_1 = $this_flatMapIterable;
  }
  protoOf(flatMapIterable$1).iterator_jk1svi_k$ = function () {
    return flatMapIterator(this.$this_flatMapIterable_1);
  };
  function Pool$Companion$fromPoolable$lambda(it) {
    it.reset_5u6xz3_k$();
    return Unit_getInstance();
  }
  function _get_reset__bxyowu($this) {
    return $this.reset_1;
  }
  function _get_gen__e68x1d($this) {
    return $this.gen_1;
  }
  function Companion_11() {
    Companion_instance_11 = this;
  }
  protoOf(Companion_11).fromPoolable_dwy6r2_k$ = function (preallocate, gen) {
    return new Pool(Pool$Companion$fromPoolable$lambda, preallocate, gen);
  };
  protoOf(Companion_11).fromPoolable$default_927qu0_k$ = function (preallocate, gen, $super) {
    preallocate = preallocate === VOID ? 0 : preallocate;
    return $super === VOID ? this.fromPoolable_dwy6r2_k$(preallocate, gen) : $super.fromPoolable_dwy6r2_k$.call(this, preallocate, gen);
  };
  var Companion_instance_11;
  function Companion_getInstance_11() {
    if (Companion_instance_11 == null)
      new Companion_11();
    return Companion_instance_11;
  }
  function Pool_init_$Init$(preallocate, gen, $this) {
    preallocate = preallocate === VOID ? 0 : preallocate;
    Pool.call($this, Pool$_init_$lambda_q3cbhr_0, preallocate, gen);
    return $this;
  }
  function Pool_init_$Create$(preallocate, gen) {
    return Pool_init_$Init$(preallocate, gen, objectCreate(protoOf(Pool)));
  }
  function _get_items__fzd5gv($this) {
    return $this.items_1;
  }
  function _set_lastId__zh963e($this, _set____db54di) {
    $this.lastId_1 = _set____db54di;
  }
  function _get_lastId__y0ilgm($this) {
    return $this.lastId_1;
  }
  function Poolable() {
  }
  function Pool$_init_$lambda_q3cbhr(it) {
    return Unit_getInstance();
  }
  function Pool$_init_$lambda_q3cbhr_0(it) {
    return Unit_getInstance();
  }
  function Pool(reset, preallocate, gen) {
    Companion_getInstance_11();
    var tmp;
    if (reset === VOID) {
      tmp = Pool$_init_$lambda_q3cbhr;
    } else {
      tmp = reset;
    }
    reset = tmp;
    preallocate = preallocate === VOID ? 0 : preallocate;
    this.reset_1 = reset;
    this.gen_1 = gen;
    this.items_1 = _TGenStack___init__impl__yynjgt();
    this.lastId_1 = 0;
    var inductionVariable = 0;
    if (inductionVariable < preallocate)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var _unary__edvuaz = this.lastId_1;
        this.lastId_1 = _unary__edvuaz + 1 | 0;
        TGenStack__push_impl_9yh33t(this.items_1, this.gen_1(_unary__edvuaz));
      }
       while (inductionVariable < preallocate);
  }
  protoOf(Pool).get_totalAllocatedItems_iu4zw_k$ = function () {
    return this.lastId_1;
  };
  protoOf(Pool).get_totalItemsInUse_aly2f1_k$ = function () {
    return this.get_totalAllocatedItems_iu4zw_k$() - this.get_itemsInPool_2zl6so_k$() | 0;
  };
  protoOf(Pool).get_itemsInPool_2zl6so_k$ = function () {
    return _TGenStack___get_size__impl__cvg66t(this.items_1);
  };
  protoOf(Pool).alloc_1jbayd_k$ = function () {
    var tmp;
    // Inline function 'kotlin.collections.isNotEmpty' call
    if (!(new TGenStack(this.items_1)).isEmpty_y1axqb_k$()) {
      tmp = TGenStack__pop_impl_qisxa0(this.items_1);
    } else {
      var _unary__edvuaz = this.lastId_1;
      this.lastId_1 = _unary__edvuaz + 1 | 0;
      tmp = this.gen_1(_unary__edvuaz);
    }
    return tmp;
  };
  protoOf(Pool).clear_j9egeb_k$ = function () {
    TGenStack__clear_impl_pali4s(this.items_1);
    this.lastId_1 = 0;
  };
  protoOf(Pool).free_6u7ei6_k$ = function (element) {
    this.reset_1(element);
    TGenStack__push_impl_9yh33t(this.items_1, element);
  };
  protoOf(Pool).freeNotNull_i2wdef_k$ = function (element) {
    if (element == null)
      return Unit_getInstance();
    this.free_6u7ei6_k$(element);
  };
  protoOf(Pool).free_4ujfp6_k$ = function (elements) {
    // Inline function 'korlibs.datastructure.iterators.fastForEach' call
    var n = 0;
    while (n < elements.length) {
      // Inline function 'korlibs.datastructure.Pool.free.<anonymous>' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      var it = elements[_unary__edvuaz];
      this.free_6u7ei6_k$(it);
    }
  };
  protoOf(Pool).free_e7iuvo_k$ = function (elements) {
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var element = _iterator__ex2g4s.next_20eer_k$();
      this.free_6u7ei6_k$(element);
    }
  };
  protoOf(Pool).free_1n6er4_k$ = function (elements) {
    // Inline function 'korlibs.datastructure.iterators.fastForEach' call
    var n = 0;
    while (n < elements.get_size_woubt6_k$()) {
      // Inline function 'korlibs.datastructure.Pool.free.<anonymous>' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      var it = elements.get_c1px32_k$(_unary__edvuaz);
      this.free_6u7ei6_k$(it);
    }
  };
  protoOf(Pool).invoke_f89h4l_k$ = function (callback) {
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure.Pool.alloc' call
      var temp = this.alloc_1jbayd_k$();
      try {
        tmp$ret$0 = callback(temp);
        break $l$block;
      }finally {
        this.free_6u7ei6_k$(temp);
      }
    }
    return tmp$ret$0;
  };
  protoOf(Pool).alloc_uk7szc_k$ = function (callback) {
    var temp = this.alloc_1jbayd_k$();
    try {
      return callback(temp);
    }finally {
      this.free_6u7ei6_k$(temp);
    }
  };
  protoOf(Pool).alloc2_cpb57d_k$ = function (callback) {
    var temp1 = this.alloc_1jbayd_k$();
    var temp2 = this.alloc_1jbayd_k$();
    try {
      return callback(temp1, temp2);
    }finally {
      this.free_6u7ei6_k$(temp2);
      this.free_6u7ei6_k$(temp1);
    }
  };
  protoOf(Pool).alloc3_r5cgzh_k$ = function (callback) {
    var temp1 = this.alloc_1jbayd_k$();
    var temp2 = this.alloc_1jbayd_k$();
    var temp3 = this.alloc_1jbayd_k$();
    try {
      return callback(temp1, temp2, temp3);
    }finally {
      this.free_6u7ei6_k$(temp3);
      this.free_6u7ei6_k$(temp2);
      this.free_6u7ei6_k$(temp1);
    }
  };
  protoOf(Pool).allocMultiple_sv113k_k$ = function (count, temp, callback) {
    temp.clear_j9egeb_k$();
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        temp.add_utx5q5_k$(this.alloc_1jbayd_k$());
      }
       while (inductionVariable < count);
    try {
      return callback(temp);
    }finally {
      $l$loop: while (true) {
        // Inline function 'kotlin.collections.isNotEmpty' call
        if (!!temp.isEmpty_y1axqb_k$()) {
          break $l$loop;
        }
        this.free_6u7ei6_k$(removeLast(temp));
      }
    }
  };
  protoOf(Pool).allocThis_g0z9s6_k$ = function (callback) {
    var temp = this.alloc_1jbayd_k$();
    try {
      return callback(temp);
    }finally {
      this.free_6u7ei6_k$(temp);
    }
  };
  protoOf(Pool).hashCode = function () {
    return TGenStack__hashCode_impl_l04zom(this.items_1);
  };
  protoOf(Pool).equals = function (other) {
    var tmp;
    var tmp_0;
    if (other instanceof Pool) {
      tmp_0 = equals(this.items_1, other.items_1);
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = this.get_itemsInPool_2zl6so_k$() === other.get_itemsInPool_2zl6so_k$();
    } else {
      tmp = false;
    }
    return tmp;
  };
  function sam$kotlin_Comparator$0(function_0) {
    this.function_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0).compare_bczr_k$ = function (a, b) {
    return this.function_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0).compare = function (a, b) {
    return this.compare_bczr_k$(a, b);
  };
  function _set_data__9licbx_5($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _get_data__d5abxd_3($this) {
    return $this.data_1;
  }
  function Companion_12() {
    Companion_instance_12 = this;
  }
  protoOf(Companion_12).invoke_48sq11_k$ = function (initialCapacity, comparator, reversed_0) {
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp = fillArrayVal(Array(initialCapacity), null);
    return new TGenPriorityQueue(isArray(tmp) ? tmp : THROW_CCE(), reversed_0 ? reversed(comparator) : comparator);
  };
  protoOf(Companion_12).invoke$default_tza95a_k$ = function (initialCapacity, comparator, reversed, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_48sq11_k$(initialCapacity, comparator, reversed) : $super.invoke_48sq11_k$.call(this, initialCapacity, comparator, reversed);
  };
  protoOf(Companion_12).invoke_9k12xf_k$ = function (comparator, reversed_0) {
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp = fillArrayVal(Array(16), null);
    return new TGenPriorityQueue(isArray(tmp) ? tmp : THROW_CCE(), reversed_0 ? reversed(comparator) : comparator);
  };
  protoOf(Companion_12).invoke$default_rvw0kw_k$ = function (comparator, reversed, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_9k12xf_k$(comparator, reversed) : $super.invoke_9k12xf_k$.call(this, comparator, reversed);
  };
  protoOf(Companion_12).invoke_umv7sv_k$ = function (reversed, comparator) {
    return Companion_getInstance_12().invoke_9k12xf_k$(new sam$kotlin_Comparator$0(comparator), reversed);
  };
  protoOf(Companion_12).invoke$default_f3xc9e_k$ = function (reversed, comparator, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_umv7sv_k$(reversed, comparator) : $super.invoke_umv7sv_k$.call(this, reversed, comparator);
  };
  protoOf(Companion_12).invoke_gur30p_k$ = function (reversed) {
    return Companion_getInstance_12().invoke_9k12xf_k$(comparator(), reversed);
  };
  protoOf(Companion_12).invoke$default_o6axds_k$ = function (reversed, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_gur30p_k$(reversed) : $super.invoke_gur30p_k$.call(this, reversed);
  };
  var Companion_instance_12;
  function Companion_getInstance_12() {
    if (Companion_instance_12 == null)
      new Companion_12();
    return Companion_instance_12;
  }
  function _set_value__lx0xdg(_this__u8e3s4, $this, value) {
    $this.data_1[_PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)] = value;
  }
  function _get_value__a43j40(_this__u8e3s4, $this) {
    return $this.data_1[_PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)];
  }
  function _get_isRoot__1006zj(_this__u8e3s4, $this) {
    return _PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4) === 0;
  }
  function _get_parent__oo9xup(_this__u8e3s4, $this) {
    return _PriorityQueueNode___init__impl__34dlfu((_PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4) - 1 | 0) / 2 | 0);
  }
  function _get_left__d9qyp0(_this__u8e3s4, $this) {
    return _PriorityQueueNode___init__impl__34dlfu(imul(2, _PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)) + 1 | 0);
  }
  function _get_right__bvz45n(_this__u8e3s4, $this) {
    return _PriorityQueueNode___init__impl__34dlfu(imul(2, _PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)) + 2 | 0);
  }
  function gt($this, a, b) {
    return $this.comparator_1.compare(a, b) > 0;
  }
  function lt($this, a, b) {
    return $this.comparator_1.compare(a, b) < 0;
  }
  function _get_capacity__a9k9f3_3($this) {
    return $this.data_1.length;
  }
  function _set_size__9twho6_1($this, _set____db54di) {
    $this.size_1 = _set____db54di;
  }
  function ensure_1($this, index) {
    if (index >= _get_capacity__a9k9f3_3($this)) {
      var tmp = $this;
      var tmp_0 = copyOf_5($this.data_1, 2 + imul(_get_capacity__a9k9f3_3($this), 2) | 0);
      tmp.data_1 = isArray(tmp_0) ? tmp_0 : THROW_CCE();
    }
  }
  function minHeapify($this, index) {
    var i = _PriorityQueueNode___init__impl__34dlfu(index);
    $l$loop: while (true) {
      var left = _get_left__d9qyp0(i, $this);
      var right = _get_right__bvz45n(i, $this);
      var smallest = i;
      if (_PriorityQueueNode___get_index__impl__b6r7i5(left) < $this.size_1 ? lt($this, _get_value__a43j40(left, $this), _get_value__a43j40(i, $this)) : false) {
        smallest = left;
      }
      if (_PriorityQueueNode___get_index__impl__b6r7i5(right) < $this.size_1 ? lt($this, _get_value__a43j40(right, $this), _get_value__a43j40(smallest, $this)) : false) {
        smallest = right;
      }
      if (!(smallest === i)) {
        swap_1($this, i, smallest);
        i = smallest;
      } else {
        break $l$loop;
      }
    }
  }
  function swap_1($this, l, r) {
    var temp = _get_value__a43j40(r, $this);
    _set_value__lx0xdg(r, $this, _get_value__a43j40(l, $this));
    _set_value__lx0xdg(l, $this, temp);
  }
  function TGenPriorityQueue$iterator$1($index, this$0) {
    this.$index_1 = $index;
    this.this$0__1 = this$0;
  }
  protoOf(TGenPriorityQueue$iterator$1).hasNext_bitz1p_k$ = function () {
    return this.$index_1._v < this.this$0__1.size_1;
  };
  protoOf(TGenPriorityQueue$iterator$1).next_20eer_k$ = function () {
    var _unary__edvuaz = this.$index_1._v;
    this.$index_1._v = _unary__edvuaz + 1 | 0;
    return _get_value__a43j40(_PriorityQueueNode___init__impl__34dlfu(_unary__edvuaz), this.this$0__1);
  };
  protoOf(TGenPriorityQueue$iterator$1).remove_fgfybg_k$ = function () {
    throw new NotImplementedError();
  };
  protoOf(TGenPriorityQueue$iterator$1).remove_ldkf9o_k$ = function () {
    return this.remove_fgfybg_k$();
  };
  function TGenPriorityQueue(data, comparator) {
    Companion_getInstance_12();
    this.data_1 = data;
    this.comparator_1 = comparator;
    this.size_1 = 0;
  }
  protoOf(TGenPriorityQueue).get_comparator_y55d41_k$ = function () {
    return this.comparator_1;
  };
  protoOf(TGenPriorityQueue).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(TGenPriorityQueue).get_head_won7e1_k$ = function () {
    if (this.size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    return this.data_1[0];
  };
  protoOf(TGenPriorityQueue).add_2y5tg6_k$ = function (element) {
    this.size_1 = this.size_1 + 1 | 0;
    ensure_1(this, this.size_1);
    var i = _PriorityQueueNode___init__impl__34dlfu(this.size_1 - 1 | 0);
    _set_value__lx0xdg(i, this, element);
    while (!_get_isRoot__1006zj(i, this) ? gt(this, _get_value__a43j40(_get_parent__oo9xup(i, this), this), _get_value__a43j40(i, this)) : false) {
      swap_1(this, i, _get_parent__oo9xup(i, this));
      i = _get_parent__oo9xup(i, this);
    }
    return true;
  };
  protoOf(TGenPriorityQueue).add_utx5q5_k$ = function (element) {
    return this.add_2y5tg6_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(TGenPriorityQueue).removeHead_i5uft0_k$ = function () {
    if (this.size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    if (this.size_1 === 1) {
      this.size_1 = this.size_1 - 1 | 0;
      return _get_value__a43j40(_PriorityQueueNode___init__impl__34dlfu(0), this);
    }
    var root = _get_value__a43j40(_PriorityQueueNode___init__impl__34dlfu(0), this);
    _set_value__lx0xdg(_PriorityQueueNode___init__impl__34dlfu(0), this, _get_value__a43j40(_PriorityQueueNode___init__impl__34dlfu(this.size_1 - 1 | 0), this));
    this.size_1 = this.size_1 - 1 | 0;
    minHeapify(this, 0);
    return root;
  };
  protoOf(TGenPriorityQueue).indexOf_jnvfzi_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.size_1;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (equals(this.data_1[n], element))
          return n;
      }
       while (inductionVariable < last);
    return -1;
  };
  protoOf(TGenPriorityQueue).updateObject_g477of_k$ = function (element) {
    var index = this.indexOf_jnvfzi_k$(element);
    if (index >= 0) {
      this.updateAt_zcdcyc_k$(index);
    }
  };
  protoOf(TGenPriorityQueue).updateAt_zcdcyc_k$ = function (index) {
    var value = _get_value__a43j40(_PriorityQueueNode___init__impl__34dlfu(index), this);
    this.removeAt_rql1ap_k$(index);
    this.add_2y5tg6_k$(value);
  };
  protoOf(TGenPriorityQueue).remove_tetxhv_k$ = function (element) {
    var index = this.indexOf_jnvfzi_k$(element);
    if (index >= 0) {
      this.removeAt_rql1ap_k$(index);
    }
    return index >= 0;
  };
  protoOf(TGenPriorityQueue).remove_cedx0m_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.remove_tetxhv_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(TGenPriorityQueue).removeAt_rql1ap_k$ = function (index) {
    var i = _PriorityQueueNode___init__impl__34dlfu(index);
    while (!(_PriorityQueueNode___get_index__impl__b6r7i5(i) === 0)) {
      swap_1(this, i, _get_parent__oo9xup(i, this));
      i = _get_parent__oo9xup(i, this);
    }
    this.removeHead_i5uft0_k$();
  };
  protoOf(TGenPriorityQueue).contains_c6f694_k$ = function (element) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var this_0 = until(0, this.size_1);
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element_0 = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.TGenPriorityQueue.contains.<anonymous>' call
        if (equals(_get_value__a43j40(_PriorityQueueNode___init__impl__34dlfu(element_0), this), element)) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(TGenPriorityQueue).contains_aljjnj_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.contains_c6f694_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(TGenPriorityQueue).containsAll_nns6wg_k$ = function (elements) {
    var thisSet = toSet(this);
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.all' call
      var tmp;
      if (isInterface(elements, Collection)) {
        tmp = elements.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = true;
        break $l$block_0;
      }
      var tmp0_iterator = elements.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.TGenPriorityQueue.containsAll.<anonymous>' call
        if (!thisSet.contains_aljjnj_k$(element)) {
          tmp$ret$0 = false;
          break $l$block_0;
        }
      }
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  };
  protoOf(TGenPriorityQueue).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_nns6wg_k$(elements);
  };
  protoOf(TGenPriorityQueue).isEmpty_y1axqb_k$ = function () {
    return this.size_1 === 0;
  };
  protoOf(TGenPriorityQueue).addAll_dmwg7m_k$ = function (elements) {
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var e = _iterator__ex2g4s.next_20eer_k$();
      this.add_2y5tg6_k$(e);
    }
    // Inline function 'kotlin.collections.isNotEmpty' call
    return !elements.isEmpty_y1axqb_k$();
  };
  protoOf(TGenPriorityQueue).addAll_4lagoh_k$ = function (elements) {
    return this.addAll_dmwg7m_k$(elements);
  };
  protoOf(TGenPriorityQueue).clear_j9egeb_k$ = function () {
    this.size_1 = 0;
  };
  protoOf(TGenPriorityQueue).removeAll_eizctx_k$ = function (elements) {
    var temp = ArrayList_init_$Create$_1(toList(this));
    var res = temp.removeAll_y0z8pe_k$(elements);
    this.clear_j9egeb_k$();
    this.addAll_dmwg7m_k$(temp);
    return res;
  };
  protoOf(TGenPriorityQueue).removeAll_y0z8pe_k$ = function (elements) {
    return this.removeAll_eizctx_k$(elements);
  };
  protoOf(TGenPriorityQueue).retainAll_ta6v7a_k$ = function (elements) {
    var temp = ArrayList_init_$Create$_1(toList(this));
    var res = temp.retainAll_9fhiib_k$(elements);
    this.clear_j9egeb_k$();
    this.addAll_dmwg7m_k$(temp);
    return res;
  };
  protoOf(TGenPriorityQueue).retainAll_9fhiib_k$ = function (elements) {
    return this.retainAll_ta6v7a_k$(elements);
  };
  protoOf(TGenPriorityQueue).iterator_jk1svi_k$ = function () {
    var index = {_v: 0};
    return new TGenPriorityQueue$iterator$1(index, this);
  };
  protoOf(TGenPriorityQueue).toArraySorted_2aj2th_k$ = function () {
    // Inline function 'kotlin.arrayOfNulls' call
    var size = this.size_1;
    var tmp = fillArrayVal(Array(size), null);
    var out = isArray(tmp) ? tmp : THROW_CCE();
    var inductionVariable = 0;
    var last = this.size_1;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out[n] = this.removeHead_i5uft0_k$();
      }
       while (inductionVariable < last);
    var inductionVariable_0 = 0;
    var last_0 = out.length;
    while (inductionVariable_0 < last_0) {
      var v = out[inductionVariable_0];
      inductionVariable_0 = inductionVariable_0 + 1 | 0;
      this.add_2y5tg6_k$(v);
    }
    return out;
  };
  protoOf(TGenPriorityQueue).toString = function () {
    return toString(toList(this));
  };
  protoOf(TGenPriorityQueue).equals = function (other) {
    var tmp;
    var tmp_0;
    if (other instanceof TGenPriorityQueue) {
      tmp_0 = contentEquals(this.data_1, other.data_1);
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = equals(this.comparator_1, other.comparator_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(TGenPriorityQueue).hashCode = function () {
    return contentHashCode(this.data_1);
  };
  function sam$kotlin_Comparator$0_0(function_0) {
    this.function_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_0).compare_bczr_k$ = function (a, b) {
    return this.function_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_0).compare = function (a, b) {
    return this.compare_bczr_k$(a, b);
  };
  function _set_data__9licbx_6($this, _set____db54di) {
    $this.data_1 = _set____db54di;
  }
  function _get_data__d5abxd_4($this) {
    return $this.data_1;
  }
  function Companion_13() {
    Companion_instance_13 = this;
  }
  protoOf(Companion_13).invoke_9aewah_k$ = function (initialCapacity, comparator, reversed_0) {
    var tmp = new Int32Array(initialCapacity);
    return new IntPriorityQueue(isIntArray(tmp) ? tmp : THROW_CCE(), reversed_0 ? reversed(comparator) : comparator);
  };
  protoOf(Companion_13).invoke$default_aun0cd_k$ = function (initialCapacity, comparator, reversed, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_9aewah_k$(initialCapacity, comparator, reversed) : $super.invoke_9aewah_k$.call(this, initialCapacity, comparator, reversed);
  };
  protoOf(Companion_13).invoke_56gqsf_k$ = function (comparator, reversed_0) {
    var tmp = new Int32Array(16);
    return new IntPriorityQueue(isIntArray(tmp) ? tmp : THROW_CCE(), reversed_0 ? reversed(comparator) : comparator);
  };
  protoOf(Companion_13).invoke$default_wycrnv_k$ = function (comparator, reversed, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_56gqsf_k$(comparator, reversed) : $super.invoke_56gqsf_k$.call(this, comparator, reversed);
  };
  protoOf(Companion_13).invoke_y4o6o9_k$ = function (reversed, comparator) {
    return Companion_getInstance_13().invoke_56gqsf_k$(new sam$kotlin_Comparator$0_0(comparator), reversed);
  };
  protoOf(Companion_13).invoke$default_jys7t9_k$ = function (reversed, comparator, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_y4o6o9_k$(reversed, comparator) : $super.invoke_y4o6o9_k$.call(this, reversed, comparator);
  };
  protoOf(Companion_13).invoke_83c4kt_k$ = function (reversed) {
    return Companion_getInstance_13().invoke_56gqsf_k$(comparator(), reversed);
  };
  protoOf(Companion_13).invoke$default_wa1xyh_k$ = function (reversed, $super) {
    reversed = reversed === VOID ? false : reversed;
    return $super === VOID ? this.invoke_83c4kt_k$(reversed) : $super.invoke_83c4kt_k$.call(this, reversed);
  };
  var Companion_instance_13;
  function Companion_getInstance_13() {
    if (Companion_instance_13 == null)
      new Companion_13();
    return Companion_instance_13;
  }
  function _set_value__lx0xdg_0(_this__u8e3s4, $this, value) {
    $this.data_1[_PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)] = value;
  }
  function _get_value__a43j40_0(_this__u8e3s4, $this) {
    return $this.data_1[_PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)];
  }
  function _get_isRoot__1006zj_0(_this__u8e3s4, $this) {
    return _PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4) === 0;
  }
  function _get_parent__oo9xup_0(_this__u8e3s4, $this) {
    return _PriorityQueueNode___init__impl__34dlfu((_PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4) - 1 | 0) / 2 | 0);
  }
  function _get_left__d9qyp0_0(_this__u8e3s4, $this) {
    return _PriorityQueueNode___init__impl__34dlfu(imul(2, _PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)) + 1 | 0);
  }
  function _get_right__bvz45n_0(_this__u8e3s4, $this) {
    return _PriorityQueueNode___init__impl__34dlfu(imul(2, _PriorityQueueNode___get_index__impl__b6r7i5(_this__u8e3s4)) + 2 | 0);
  }
  function gt_0($this, a, b) {
    return $this.comparator_1.compare(a, b) > 0;
  }
  function lt_0($this, a, b) {
    return $this.comparator_1.compare(a, b) < 0;
  }
  function _get_capacity__a9k9f3_4($this) {
    return $this.data_1.length;
  }
  function _set_size__9twho6_2($this, _set____db54di) {
    $this.size_1 = _set____db54di;
  }
  function ensure_2($this, index) {
    if (index >= _get_capacity__a9k9f3_4($this)) {
      var tmp = $this;
      var tmp_0 = copyOf_1($this.data_1, 2 + imul(_get_capacity__a9k9f3_4($this), 2) | 0);
      tmp.data_1 = isIntArray(tmp_0) ? tmp_0 : THROW_CCE();
    }
  }
  function minHeapify_0($this, index) {
    var i = _PriorityQueueNode___init__impl__34dlfu(index);
    $l$loop: while (true) {
      var left = _get_left__d9qyp0_0(i, $this);
      var right = _get_right__bvz45n_0(i, $this);
      var smallest = i;
      if (_PriorityQueueNode___get_index__impl__b6r7i5(left) < $this.size_1 ? lt_0($this, _get_value__a43j40_0(left, $this), _get_value__a43j40_0(i, $this)) : false) {
        smallest = left;
      }
      if (_PriorityQueueNode___get_index__impl__b6r7i5(right) < $this.size_1 ? lt_0($this, _get_value__a43j40_0(right, $this), _get_value__a43j40_0(smallest, $this)) : false) {
        smallest = right;
      }
      if (!(smallest === i)) {
        swap_2($this, i, smallest);
        i = smallest;
      } else {
        break $l$loop;
      }
    }
  }
  function swap_2($this, l, r) {
    var temp = _get_value__a43j40_0(r, $this);
    _set_value__lx0xdg_0(r, $this, _get_value__a43j40_0(l, $this));
    _set_value__lx0xdg_0(l, $this, temp);
  }
  function IntPriorityQueue$iterator$1($index, this$0) {
    this.$index_1 = $index;
    this.this$0__1 = this$0;
  }
  protoOf(IntPriorityQueue$iterator$1).hasNext_bitz1p_k$ = function () {
    return this.$index_1._v < this.this$0__1.size_1;
  };
  protoOf(IntPriorityQueue$iterator$1).next_20eer_k$ = function () {
    var _unary__edvuaz = this.$index_1._v;
    this.$index_1._v = _unary__edvuaz + 1 | 0;
    return _get_value__a43j40_0(_PriorityQueueNode___init__impl__34dlfu(_unary__edvuaz), this.this$0__1);
  };
  protoOf(IntPriorityQueue$iterator$1).remove_fgfybg_k$ = function () {
    throw new NotImplementedError();
  };
  protoOf(IntPriorityQueue$iterator$1).remove_ldkf9o_k$ = function () {
    return this.remove_fgfybg_k$();
  };
  function IntPriorityQueue(data, comparator) {
    Companion_getInstance_13();
    this.data_1 = data;
    this.comparator_1 = comparator;
    this.size_1 = 0;
  }
  protoOf(IntPriorityQueue).get_comparator_y55d41_k$ = function () {
    return this.comparator_1;
  };
  protoOf(IntPriorityQueue).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(IntPriorityQueue).get_head_won7e1_k$ = function () {
    if (this.size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    return this.data_1[0];
  };
  protoOf(IntPriorityQueue).add_lnluon_k$ = function (element) {
    this.size_1 = this.size_1 + 1 | 0;
    ensure_2(this, this.size_1);
    var i = _PriorityQueueNode___init__impl__34dlfu(this.size_1 - 1 | 0);
    _set_value__lx0xdg_0(i, this, element);
    while (!_get_isRoot__1006zj_0(i, this) ? gt_0(this, _get_value__a43j40_0(_get_parent__oo9xup_0(i, this), this), _get_value__a43j40_0(i, this)) : false) {
      swap_2(this, i, _get_parent__oo9xup_0(i, this));
      i = _get_parent__oo9xup_0(i, this);
    }
    return true;
  };
  protoOf(IntPriorityQueue).add_utx5q5_k$ = function (element) {
    return this.add_lnluon_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntPriorityQueue).removeHead_i5uft0_k$ = function () {
    if (this.size_1 <= 0)
      throw IndexOutOfBoundsException_init_$Create$_0();
    if (this.size_1 === 1) {
      this.size_1 = this.size_1 - 1 | 0;
      return _get_value__a43j40_0(_PriorityQueueNode___init__impl__34dlfu(0), this);
    }
    var root = _get_value__a43j40_0(_PriorityQueueNode___init__impl__34dlfu(0), this);
    _set_value__lx0xdg_0(_PriorityQueueNode___init__impl__34dlfu(0), this, _get_value__a43j40_0(_PriorityQueueNode___init__impl__34dlfu(this.size_1 - 1 | 0), this));
    this.size_1 = this.size_1 - 1 | 0;
    minHeapify_0(this, 0);
    return root;
  };
  protoOf(IntPriorityQueue).indexOf_h7efip_k$ = function (element) {
    var inductionVariable = 0;
    var last = this.size_1;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (this.data_1[n] === element)
          return n;
      }
       while (inductionVariable < last);
    return -1;
  };
  protoOf(IntPriorityQueue).updateObject_glkb8g_k$ = function (element) {
    var index = this.indexOf_h7efip_k$(element);
    if (index >= 0) {
      this.updateAt_zcdcyc_k$(index);
    }
  };
  protoOf(IntPriorityQueue).updateAt_zcdcyc_k$ = function (index) {
    var value = _get_value__a43j40_0(_PriorityQueueNode___init__impl__34dlfu(index), this);
    this.removeAt_rql1ap_k$(index);
    this.add_lnluon_k$(value);
  };
  protoOf(IntPriorityQueue).remove_cqondg_k$ = function (element) {
    var index = this.indexOf_h7efip_k$(element);
    if (index >= 0) {
      this.removeAt_rql1ap_k$(index);
    }
    return index >= 0;
  };
  protoOf(IntPriorityQueue).remove_cedx0m_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.remove_cqondg_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntPriorityQueue).removeAt_rql1ap_k$ = function (index) {
    var i = _PriorityQueueNode___init__impl__34dlfu(index);
    while (!(_PriorityQueueNode___get_index__impl__b6r7i5(i) === 0)) {
      swap_2(this, i, _get_parent__oo9xup_0(i, this));
      i = _get_parent__oo9xup_0(i, this);
    }
    this.removeHead_i5uft0_k$();
  };
  protoOf(IntPriorityQueue).contains_7q95ev_k$ = function (element) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var this_0 = until(0, this.size_1);
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element_0 = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntPriorityQueue.contains.<anonymous>' call
        if (_get_value__a43j40_0(_PriorityQueueNode___init__impl__34dlfu(element_0), this) === element) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  };
  protoOf(IntPriorityQueue).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.contains_7q95ev_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntPriorityQueue).containsAll_tftgie_k$ = function (elements) {
    var thisSet = toSet(this);
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.all' call
      var tmp;
      if (isInterface(elements, Collection)) {
        tmp = elements.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = true;
        break $l$block_0;
      }
      var tmp0_iterator = elements.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntPriorityQueue.containsAll.<anonymous>' call
        if (!thisSet.contains_aljjnj_k$(element)) {
          tmp$ret$0 = false;
          break $l$block_0;
        }
      }
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  };
  protoOf(IntPriorityQueue).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_tftgie_k$(elements);
  };
  protoOf(IntPriorityQueue).isEmpty_y1axqb_k$ = function () {
    return this.size_1 === 0;
  };
  protoOf(IntPriorityQueue).addAll_c9pvhk_k$ = function (elements) {
    var _iterator__ex2g4s = elements.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var e = _iterator__ex2g4s.next_20eer_k$();
      this.add_lnluon_k$(e);
    }
    // Inline function 'kotlin.collections.isNotEmpty' call
    return !elements.isEmpty_y1axqb_k$();
  };
  protoOf(IntPriorityQueue).addAll_4lagoh_k$ = function (elements) {
    return this.addAll_c9pvhk_k$(elements);
  };
  protoOf(IntPriorityQueue).clear_j9egeb_k$ = function () {
    this.size_1 = 0;
  };
  protoOf(IntPriorityQueue).removeAll_bjaog5_k$ = function (elements) {
    var temp = ArrayList_init_$Create$_1(toList(this));
    var res = temp.removeAll_y0z8pe_k$(elements);
    this.clear_j9egeb_k$();
    this.addAll_c9pvhk_k$(temp);
    return res;
  };
  protoOf(IntPriorityQueue).removeAll_y0z8pe_k$ = function (elements) {
    return this.removeAll_bjaog5_k$(elements);
  };
  protoOf(IntPriorityQueue).retainAll_jfkdrw_k$ = function (elements) {
    var temp = ArrayList_init_$Create$_1(toList(this));
    var res = temp.retainAll_9fhiib_k$(elements);
    this.clear_j9egeb_k$();
    this.addAll_c9pvhk_k$(temp);
    return res;
  };
  protoOf(IntPriorityQueue).retainAll_9fhiib_k$ = function (elements) {
    return this.retainAll_jfkdrw_k$(elements);
  };
  protoOf(IntPriorityQueue).iterator_jk1svi_k$ = function () {
    var index = {_v: 0};
    return new IntPriorityQueue$iterator$1(index, this);
  };
  protoOf(IntPriorityQueue).toArraySorted_2aj2th_k$ = function () {
    var tmp = new Int32Array(this.size_1);
    var out = isIntArray(tmp) ? tmp : THROW_CCE();
    var inductionVariable = 0;
    var last = this.size_1;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out[n] = this.removeHead_i5uft0_k$();
      }
       while (inductionVariable < last);
    var inductionVariable_0 = 0;
    var last_0 = out.length;
    while (inductionVariable_0 < last_0) {
      var v = out[inductionVariable_0];
      inductionVariable_0 = inductionVariable_0 + 1 | 0;
      this.add_lnluon_k$(v);
    }
    return out;
  };
  protoOf(IntPriorityQueue).toString = function () {
    return toString(toList(this));
  };
  protoOf(IntPriorityQueue).equals = function (other) {
    var tmp;
    var tmp_0;
    if (other instanceof IntPriorityQueue) {
      tmp_0 = contentEquals_1(this.data_1, other.data_1);
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = equals(this.comparator_1, other.comparator_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(IntPriorityQueue).hashCode = function () {
    return contentHashCode_1(this.data_1);
  };
  function _PriorityQueueNode___init__impl__34dlfu(index) {
    return index;
  }
  function _PriorityQueueNode___get_index__impl__b6r7i5($this) {
    return $this;
  }
  function PriorityQueueNode__toString_impl_10nhgu($this) {
    return 'PriorityQueueNode(index=' + $this + ')';
  }
  function PriorityQueueNode__hashCode_impl_mrte9p($this) {
    return $this;
  }
  function PriorityQueueNode__equals_impl_wtpuu9($this, other) {
    if (!(other instanceof PriorityQueueNode))
      return false;
    if (!($this === (other instanceof PriorityQueueNode ? other.index_1 : THROW_CCE())))
      return false;
    return true;
  }
  function PriorityQueueNode(index) {
    this.index_1 = index;
  }
  protoOf(PriorityQueueNode).toString = function () {
    return PriorityQueueNode__toString_impl_10nhgu(this.index_1);
  };
  protoOf(PriorityQueueNode).hashCode = function () {
    return PriorityQueueNode__hashCode_impl_mrte9p(this.index_1);
  };
  protoOf(PriorityQueueNode).equals = function (other) {
    return PriorityQueueNode__equals_impl_wtpuu9(this.index_1, other);
  };
  function _get_items__fzd5gv_0($this) {
    return $this.items_1;
  }
  function TGenQueue_init_$Init$(items, $this) {
    TGenQueue.call($this);
    var inductionVariable = 0;
    var last = items.length;
    while (inductionVariable < last) {
      var item = items[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      $this.enqueue_iirzep_k$(item);
    }
    return $this;
  }
  function TGenQueue_init_$Create$(items) {
    return TGenQueue_init_$Init$(items, objectCreate(protoOf(TGenQueue)));
  }
  function TGenQueue() {
    this.items_1 = TGenDeque_init_$Create$();
  }
  protoOf(TGenQueue).get_size_woubt6_k$ = function () {
    return this.items_1.get_size_woubt6_k$();
  };
  protoOf(TGenQueue).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(TGenQueue).enqueue_iirzep_k$ = function (v) {
    this.items_1.addLast_s9bxg0_k$(v);
  };
  protoOf(TGenQueue).peek_21nx7_k$ = function () {
    return firstOrNull_0(this.items_1);
  };
  protoOf(TGenQueue).dequeue_pq4oxc_k$ = function () {
    return this.items_1.removeFirst_58pi0k_k$();
  };
  protoOf(TGenQueue).remove_jlou0z_k$ = function (v) {
    this.items_1.remove_tetxhv_k$(v);
  };
  protoOf(TGenQueue).toList_edfyo7_k$ = function () {
    return toList(this.items_1);
  };
  protoOf(TGenQueue).clear_j9egeb_k$ = function () {
    return this.items_1.clear_j9egeb_k$();
  };
  protoOf(TGenQueue).contains_c6f694_k$ = function (element) {
    return this.items_1.contains_c6f694_k$(element);
  };
  protoOf(TGenQueue).contains_aljjnj_k$ = function (element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return this.contains_c6f694_k$((element == null ? true : !(element == null)) ? element : THROW_CCE());
  };
  protoOf(TGenQueue).containsAll_nns6wg_k$ = function (elements) {
    return this.items_1.containsAll_nns6wg_k$(elements);
  };
  protoOf(TGenQueue).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_nns6wg_k$(elements);
  };
  protoOf(TGenQueue).iterator_jk1svi_k$ = function () {
    return this.items_1.iterator_jk1svi_k$();
  };
  protoOf(TGenQueue).hashCode = function () {
    return this.items_1.hashCode();
  };
  protoOf(TGenQueue).equals = function (other) {
    var tmp;
    if (other instanceof TGenQueue) {
      tmp = this.items_1.equals(other.items_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  function _get_items__fzd5gv_1($this) {
    return $this.items_1;
  }
  function IntQueue_init_$Init$(items, $this) {
    IntQueue.call($this);
    var inductionVariable = 0;
    var last = items.length;
    while (inductionVariable < last) {
      var item = items[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      $this.enqueue_mi7o8w_k$(item);
    }
    return $this;
  }
  function IntQueue_init_$Create$(items) {
    return IntQueue_init_$Init$(items, objectCreate(protoOf(IntQueue)));
  }
  function IntQueue() {
    this.items_1 = IntDeque_init_$Create$();
  }
  protoOf(IntQueue).get_size_woubt6_k$ = function () {
    return this.items_1.get_size_woubt6_k$();
  };
  protoOf(IntQueue).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(IntQueue).enqueue_mi7o8w_k$ = function (v) {
    this.items_1.addLast_551qun_k$(v);
  };
  protoOf(IntQueue).peek_21nx7_k$ = function () {
    return firstOrNull_0(this.items_1);
  };
  protoOf(IntQueue).dequeue_pq4oxc_k$ = function () {
    return this.items_1.removeFirst_58pi0k_k$();
  };
  protoOf(IntQueue).remove_v58o70_k$ = function (v) {
    this.items_1.remove_cqondg_k$(v);
  };
  protoOf(IntQueue).toList_edfyo7_k$ = function () {
    return toList(this.items_1);
  };
  protoOf(IntQueue).clear_j9egeb_k$ = function () {
    return this.items_1.clear_j9egeb_k$();
  };
  protoOf(IntQueue).contains_7q95ev_k$ = function (element) {
    return this.items_1.contains_7q95ev_k$(element);
  };
  protoOf(IntQueue).contains_aljjnj_k$ = function (element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return this.contains_7q95ev_k$((!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  };
  protoOf(IntQueue).containsAll_tftgie_k$ = function (elements) {
    return this.items_1.containsAll_tftgie_k$(elements);
  };
  protoOf(IntQueue).containsAll_xk45sd_k$ = function (elements) {
    return this.containsAll_tftgie_k$(elements);
  };
  protoOf(IntQueue).iterator_jk1svi_k$ = function () {
    return this.items_1.iterator_jk1svi_k$();
  };
  protoOf(IntQueue).hashCode = function () {
    return this.items_1.hashCode();
  };
  protoOf(IntQueue).equals = function (other) {
    var tmp;
    if (other instanceof IntQueue) {
      tmp = this.items_1.equals(other.items_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  function RingBuffer(bits) {
    ByteRingBuffer.call(this, bits);
  }
  function _get_mask__da8grj_1($this) {
    return $this.mask_1;
  }
  function _get_buffer__tgqkad($this) {
    return $this.buffer_1;
  }
  function _set_readPos__olos61($this, _set____db54di) {
    $this.readPos_1 = _set____db54di;
  }
  function _get_readPos__kt6zg3($this) {
    return $this.readPos_1;
  }
  function _set_writePos__e1s6te($this, _set____db54di) {
    $this.writePos_1 = _set____db54di;
  }
  function _get_writePos__18vvcm($this) {
    return $this.writePos_1;
  }
  function _set_availableWrite__voxnen($this, _set____db54di) {
    $this.availableWrite_1 = _set____db54di;
  }
  function _set_availableRead__a9qlsq_0($this, _set____db54di) {
    $this.availableRead_1 = _set____db54di;
  }
  function ByteRingBuffer(bits) {
    this.bits_1 = bits;
    this.totalSize_1 = 1 << this.bits_1;
    this.mask_1 = this.totalSize_1 - 1 | 0;
    this.buffer_1 = new Int8Array(this.totalSize_1);
    this.readPos_1 = 0;
    this.writePos_1 = 0;
    this.availableWrite_1 = this.totalSize_1;
    this.availableRead_1 = 0;
  }
  protoOf(ByteRingBuffer).get_bits_wojgwf_k$ = function () {
    return this.bits_1;
  };
  protoOf(ByteRingBuffer).get_totalSize_116z44_k$ = function () {
    return this.totalSize_1;
  };
  protoOf(ByteRingBuffer).get_availableWrite_qb4gx_k$ = function () {
    return this.availableWrite_1;
  };
  protoOf(ByteRingBuffer).get_availableRead_tre4t2_k$ = function () {
    return this.availableRead_1;
  };
  protoOf(ByteRingBuffer).get_internalBuffer_wr0sx2_k$ = function () {
    return this.buffer_1;
  };
  protoOf(ByteRingBuffer).get_internalReadPos_yxlvnc_k$ = function () {
    return this.readPos_1 & this.mask_1;
  };
  protoOf(ByteRingBuffer).get_internalWritePos_1ado63_k$ = function () {
    return this.writePos_1 & this.mask_1;
  };
  protoOf(ByteRingBuffer).internalWriteSkip_ps7x3t_k$ = function (count) {
    if (count < 0 ? true : count > this.availableWrite_1) {
      // Inline function 'kotlin.error' call
      var message = 'Try to write more than available';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    this.writePos_1 = this.writePos_1 + count | 0;
    this.availableRead_1 = this.availableRead_1 + count | 0;
    this.availableWrite_1 = this.availableWrite_1 - count | 0;
  };
  protoOf(ByteRingBuffer).internalReadSkip_ufgrm2_k$ = function (count) {
    if (count < 0 ? true : count > this.availableRead_1) {
      // Inline function 'kotlin.error' call
      var message = 'Try to write more than available';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    this.readPos_1 = this.readPos_1 + count | 0;
    this.availableRead_1 = this.availableRead_1 - count | 0;
    this.availableWrite_1 = this.availableWrite_1 + count | 0;
  };
  protoOf(ByteRingBuffer).get_availableReadBeforeWrap_3bxtsx_k$ = function () {
    // Inline function 'kotlin.math.min' call
    var a = this.availableRead_1;
    var b = this.totalSize_1 - (this.readPos_1 & this.mask_1) | 0;
    return Math.min(a, b);
  };
  protoOf(ByteRingBuffer).get_availableWriteBeforeWrap_702fmg_k$ = function () {
    // Inline function 'kotlin.math.min' call
    var a = this.availableWrite_1;
    var b = this.totalSize_1 - (this.writePos_1 & this.mask_1) | 0;
    return Math.min(a, b);
  };
  protoOf(ByteRingBuffer).write_b9o6iq_k$ = function (consume) {
    while (consume.availableRead_1 > 0) {
      // Inline function 'kotlin.math.min' call
      var a = consume.get_availableReadBeforeWrap_3bxtsx_k$();
      var b = this.get_availableWriteBeforeWrap_702fmg_k$();
      var copySize = Math.min(a, b);
      Memory_getInstance().arraycopy_o9i58a_k$(consume.get_internalBuffer_wr0sx2_k$(), consume.get_internalReadPos_yxlvnc_k$(), this.get_internalBuffer_wr0sx2_k$(), this.get_internalWritePos_1ado63_k$(), copySize);
      consume.internalReadSkip_ufgrm2_k$(copySize);
      this.internalWriteSkip_ps7x3t_k$(copySize);
    }
  };
  protoOf(ByteRingBuffer).writeHead_fjpvnj_k$ = function (data, offset, size) {
    // Inline function 'kotlin.math.min' call
    var a = this.availableWrite_1;
    var toWrite = Math.min(a, size);
    var inductionVariable = 0;
    if (inductionVariable < toWrite)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this.readPos_1 = (this.readPos_1 - 1 | 0) & this.mask_1;
        this.buffer_1[this.readPos_1] = data[((offset + size | 0) - n | 0) - 1 | 0];
      }
       while (inductionVariable < toWrite);
    this.availableRead_1 = this.availableRead_1 + toWrite | 0;
    this.availableWrite_1 = this.availableWrite_1 - toWrite | 0;
    return toWrite;
  };
  protoOf(ByteRingBuffer).writeHead$default_go0utm_k$ = function (data, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? data.length - offset | 0 : size;
    return $super === VOID ? this.writeHead_fjpvnj_k$(data, offset, size) : $super.writeHead_fjpvnj_k$.call(this, data, offset, size);
  };
  protoOf(ByteRingBuffer).write_owzzlt_k$ = function (data, offset, size) {
    // Inline function 'kotlin.math.min' call
    var a = this.availableWrite_1;
    var remaining = Math.min(a, size);
    var coffset = offset;
    var totalWrite = 0;
    $l$loop: while (remaining > 0) {
      // Inline function 'kotlin.math.min' call
      var a_0 = remaining;
      var b = this.get_availableWriteBeforeWrap_702fmg_k$();
      var chunkSize = Math.min(a_0, b);
      if (chunkSize <= 0)
        break $l$loop;
      Memory_getInstance().arraycopy_o9i58a_k$(data, coffset, this.buffer_1, this.get_internalWritePos_1ado63_k$(), chunkSize);
      this.internalWriteSkip_ps7x3t_k$(chunkSize);
      coffset = coffset + chunkSize | 0;
      remaining = remaining - chunkSize | 0;
      totalWrite = totalWrite + chunkSize | 0;
    }
    return totalWrite;
  };
  protoOf(ByteRingBuffer).write$default_1yyjre_k$ = function (data, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? data.length - offset | 0 : size;
    return $super === VOID ? this.write_owzzlt_k$(data, offset, size) : $super.write_owzzlt_k$.call(this, data, offset, size);
  };
  protoOf(ByteRingBuffer).read_7zpyie_k$ = function (data, offset, size) {
    return this.skip_7luint_k$(this.peek_1y1zlx_k$(data, offset, size));
  };
  protoOf(ByteRingBuffer).read$default_7v6p0z_k$ = function (data, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? data.length - offset | 0 : size;
    return $super === VOID ? this.read_7zpyie_k$(data, offset, size) : $super.read_7zpyie_k$.call(this, data, offset, size);
  };
  protoOf(ByteRingBuffer).skip_7luint_k$ = function (size) {
    // Inline function 'kotlin.math.min' call
    var a = this.availableRead_1;
    var toRead = Math.min(a, size);
    this.readPos_1 = (this.readPos_1 + toRead | 0) & this.mask_1;
    this.availableWrite_1 = this.availableWrite_1 + toRead | 0;
    this.availableRead_1 = this.availableRead_1 - toRead | 0;
    return toRead;
  };
  protoOf(ByteRingBuffer).peek_1y1zlx_k$ = function (data, offset, size) {
    // Inline function 'kotlin.math.min' call
    var a = this.availableRead_1;
    var toRead = Math.min(a, size);
    var readCount = 0;
    var buffer = this.buffer_1;
    var mask = this.mask_1;
    var coffset = offset;
    var lReadPos = this.readPos_1;
    $l$loop: while (true) {
      // Inline function 'kotlin.math.min' call
      var a_0 = toRead;
      var b = this.get_availableReadBeforeWrap_3bxtsx_k$();
      var toReadChunk = Math.min(a_0, b);
      if (toReadChunk <= 0)
        break $l$loop;
      Memory_getInstance().arraycopy_o9i58a_k$(buffer, lReadPos & mask, data, coffset, toReadChunk);
      toRead = toRead - toReadChunk | 0;
      coffset = coffset + toReadChunk | 0;
      lReadPos = lReadPos + toReadChunk | 0;
      readCount = readCount + toReadChunk | 0;
    }
    return readCount;
  };
  protoOf(ByteRingBuffer).peek$default_qfbjaq_k$ = function (data, offset, size, $super) {
    offset = offset === VOID ? 0 : offset;
    size = size === VOID ? data.length - offset | 0 : size;
    return $super === VOID ? this.peek_1y1zlx_k$(data, offset, size) : $super.peek_1y1zlx_k$.call(this, data, offset, size);
  };
  protoOf(ByteRingBuffer).readBytes_do0jwd_k$ = function (count) {
    var out = new Int8Array(count);
    return copyOf_4(out, this.read$default_7v6p0z_k$(out));
  };
  protoOf(ByteRingBuffer).readByte_ectjk2_k$ = function () {
    if (this.availableRead_1 <= 0)
      return -1;
    var out = this.buffer_1[this.readPos_1] & 255;
    this.readPos_1 = (this.readPos_1 + 1 | 0) & this.mask_1;
    this.availableRead_1 = this.availableRead_1 - 1 | 0;
    this.availableWrite_1 = this.availableWrite_1 + 1 | 0;
    return out;
  };
  protoOf(ByteRingBuffer).writeByte_3m2t4h_k$ = function (v) {
    if (this.availableWrite_1 <= 0)
      return false;
    this.buffer_1[this.writePos_1] = toByte(v);
    this.writePos_1 = (this.writePos_1 + 1 | 0) & this.mask_1;
    this.availableWrite_1 = this.availableWrite_1 - 1 | 0;
    this.availableRead_1 = this.availableRead_1 + 1 | 0;
    return true;
  };
  protoOf(ByteRingBuffer).clear_j9egeb_k$ = function () {
    this.readPos_1 = 0;
    this.writePos_1 = 0;
    this.availableRead_1 = 0;
    this.availableWrite_1 = this.totalSize_1;
  };
  protoOf(ByteRingBuffer).peek_6g603_k$ = function (offset) {
    return this.buffer_1[(this.readPos_1 + offset | 0) & this.mask_1];
  };
  protoOf(ByteRingBuffer).peek$default_lytuob_k$ = function (offset, $super) {
    offset = offset === VOID ? 0 : offset;
    return $super === VOID ? this.peek_6g603_k$(offset) : $super.peek_6g603_k$.call(this, offset);
  };
  protoOf(ByteRingBuffer).equals = function (other) {
    var tmp;
    var tmp_0;
    if (other instanceof ByteRingBuffer) {
      tmp_0 = this.availableRead_1 === other.availableRead_1;
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      var tmp$ret$1;
      $l$block: {
        // Inline function 'korlibs.datastructure.internal.equaler' call
        var count = this.availableRead_1;
        var inductionVariable = 0;
        if (inductionVariable < count)
          do {
            var n = inductionVariable;
            inductionVariable = inductionVariable + 1 | 0;
            // Inline function 'korlibs.datastructure.ByteRingBuffer.equals.<anonymous>' call
            if (!(this.peek_6g603_k$(n) === other.peek_6g603_k$(n))) {
              tmp$ret$1 = false;
              break $l$block;
            }
          }
           while (inductionVariable < count);
        tmp$ret$1 = true;
      }
      tmp = tmp$ret$1;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(ByteRingBuffer).hashCode = function () {
    return this.contentHashCode_si71ak_k$();
  };
  protoOf(ByteRingBuffer).contentHashCode_si71ak_k$ = function () {
    // Inline function 'korlibs.datastructure.internal.hashCoder' call
    var count = this.availableRead_1;
    var out = 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out = imul(out, 7);
        var tmp = out;
        // Inline function 'korlibs.datastructure.ByteRingBuffer.contentHashCode.<anonymous>' call
        out = tmp + this.peek_6g603_k$(n) | 0;
      }
       while (inductionVariable < count);
    return out;
  };
  function SlowIdentityHashMap$_init_$lambda_x9r5c4(it) {
    return identityHashCode(it);
  }
  function SlowIdentityHashMap$_init_$lambda_x9r5c4_0(a, b) {
    return a === b;
  }
  function SlowIdentityHashMap$_init_$lambda_x9r5c4_1(a, b) {
    return a === b;
  }
  function SlowIdentityHashMap(initialCapacity) {
    initialCapacity = initialCapacity === VOID ? 16 : initialCapacity;
    var tmp = SlowIdentityHashMap$_init_$lambda_x9r5c4;
    var tmp_0 = SlowIdentityHashMap$_init_$lambda_x9r5c4_0;
    CustomHashMap.call(this, tmp, tmp_0, SlowIdentityHashMap$_init_$lambda_x9r5c4_1, initialCapacity);
  }
  function slowIdentityHashMapOf(pairs) {
    // Inline function 'kotlin.also' call
    var this_0 = new SlowIdentityHashMap();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.slowIdentityHashMapOf.<anonymous>' call
    putAll_0(this_0, pairs);
    return this_0;
  }
  function _get_keysToIndex__3vc2vm($this) {
    return $this.keysToIndex_1;
  }
  function _get__keys__kur9uq($this) {
    return $this._keys_1;
  }
  function _get__values__6yksts_1($this) {
    return $this._values_1;
  }
  function _set_isSorted__5rfefk($this, _set____db54di) {
    $this.isSorted_1 = _set____db54di;
  }
  function _get_isSorted__ikbpwc($this) {
    return $this.isSorted_1;
  }
  function Companion_14() {
    Companion_instance_14 = this;
  }
  protoOf(Companion_14).invoke_g2l8vv_k$ = function () {
    return new SortedMap(ComparatorComparable());
  };
  var Companion_instance_14;
  function Companion_getInstance_14() {
    if (Companion_instance_14 == null)
      new Companion_14();
    return Companion_instance_14;
  }
  function Sorting() {
    Sorting_instance = this;
    SortOps.call(this);
  }
  protoOf(Sorting).compare_enm9a0_k$ = function (subject, l, r) {
    return subject.comparator_1.compare(subject._keys_1.get_c1px32_k$(l), subject._keys_1.get_c1px32_k$(r));
  };
  protoOf(Sorting).compare_nik356_k$ = function (subject, l, r) {
    return this.compare_enm9a0_k$(subject instanceof SortedMap ? subject : THROW_CCE(), l, r);
  };
  protoOf(Sorting).swap_qriwwq_k$ = function (subject, indexL, indexR) {
    subject.swap_e7b6gt_k$(indexL, indexR);
  };
  protoOf(Sorting).swap_nx7218_k$ = function (subject, indexL, indexR) {
    return this.swap_qriwwq_k$(subject instanceof SortedMap ? subject : THROW_CCE(), indexL, indexR);
  };
  var Sorting_instance;
  function Sorting_getInstance() {
    if (Sorting_instance == null)
      new Sorting();
    return Sorting_instance;
  }
  function SortedMap(comparator) {
    Companion_getInstance_14();
    this.comparator_1 = comparator;
    var tmp = this;
    // Inline function 'kotlin.collections.hashMapOf' call
    tmp.keysToIndex_1 = HashMap_init_$Create$();
    this._keys_1 = FastArrayList_init_$Create$();
    this._values_1 = FastArrayList_init_$Create$();
    this.isSorted_1 = true;
  }
  protoOf(SortedMap).get_comparator_y55d41_k$ = function () {
    return this.comparator_1;
  };
  protoOf(SortedMap).get_size_woubt6_k$ = function () {
    return this._keys_1.get_size_woubt6_k$();
  };
  protoOf(SortedMap).containsKey_aw81wo_k$ = function (key) {
    // Inline function 'kotlin.collections.contains' call
    // Inline function 'kotlin.collections.containsKey' call
    var this_0 = this.keysToIndex_1;
    return (isInterface(this_0, Map_0) ? this_0 : THROW_CCE()).containsKey_aw81wo_k$(key);
  };
  protoOf(SortedMap).containsValue_yf2ykl_k$ = function (value) {
    return this._values_1.contains_aljjnj_k$(value);
  };
  protoOf(SortedMap).get_keys_wop4xp_k$ = function () {
    this.ensureSorted_xyrndc_k$();
    return toMutableSet(this._keys_1);
  };
  protoOf(SortedMap).get_values_ksazhn_k$ = function () {
    this.ensureSorted_xyrndc_k$();
    return toMutableList(this._values_1);
  };
  protoOf(SortedMap).get_entries_p20ztl_k$ = function () {
    this.ensureSorted_xyrndc_k$();
    return Companion_getInstance_23().fromMap_u240j2_k$(this, this._keys_1);
  };
  protoOf(SortedMap).clear_j9egeb_k$ = function () {
    this.keysToIndex_1.clear_j9egeb_k$();
    this._keys_1.clear_j9egeb_k$();
    this._values_1.clear_j9egeb_k$();
    this.isSorted_1 = true;
  };
  protoOf(SortedMap).put_4fpzoq_k$ = function (key, value) {
    var oldValue = this.get_wei43m_k$(key);
    this.set_b9w55f_k$(key, value);
    return oldValue;
  };
  protoOf(SortedMap).swap_e7b6gt_k$ = function (indexL, indexR) {
    var keyL = this._keys_1.get_c1px32_k$(indexL);
    var keyR = this._keys_1.get_c1px32_k$(indexR);
    swap_0(this._keys_1, indexL, indexR);
    swap_0(this._values_1, indexL, indexR);
    // Inline function 'kotlin.collections.set' call
    this.keysToIndex_1.put_4fpzoq_k$(keyL, indexR);
    // Inline function 'kotlin.collections.set' call
    this.keysToIndex_1.put_4fpzoq_k$(keyR, indexL);
  };
  protoOf(SortedMap).removeAt_rql1ap_k$ = function (index) {
    this.isSorted_1 = false;
    var key = this._keys_1.get_c1px32_k$(index);
    this.swap_e7b6gt_k$(index, this.get_size_woubt6_k$() - 1 | 0);
    this._keys_1.removeAt_6niowx_k$(this.get_size_woubt6_k$() - 1 | 0);
    this._values_1.removeAt_6niowx_k$(this.get_size_woubt6_k$() - 1 | 0);
    this.keysToIndex_1.remove_gppy8k_k$(key);
  };
  protoOf(SortedMap).remove_gppy8k_k$ = function (key) {
    var tmp0_elvis_lhs = this.keysToIndex_1.get_wei43m_k$(key);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var index = tmp;
    var value = this._values_1.get_c1px32_k$(index);
    this.removeAt_rql1ap_k$(index);
    return value;
  };
  protoOf(SortedMap).ensureSorted_xyrndc_k$ = function () {
    if (this.isSorted_1)
      return Unit_getInstance();
    var tmp = this.get_size_woubt6_k$() - 1 | 0;
    var tmp_0 = Sorting_getInstance();
    genericSort(this, 0, tmp, tmp_0 instanceof SortOps ? tmp_0 : THROW_CCE());
    this.isSorted_1 = true;
  };
  protoOf(SortedMap).set_b9w55f_k$ = function (key, value) {
    var index = this.keysToIndex_1.get_wei43m_k$(key);
    if (!(index == null)) {
      this._values_1.set_82063s_k$(index, value);
    } else {
      this.isSorted_1 = false;
      // Inline function 'kotlin.collections.set' call
      var this_0 = this.keysToIndex_1;
      var value_0 = this._keys_1.get_size_woubt6_k$();
      this_0.put_4fpzoq_k$(key, value_0);
      this._keys_1.add_utx5q5_k$(key);
      this._values_1.add_utx5q5_k$(value);
    }
  };
  protoOf(SortedMap).get_wei43m_k$ = function (key) {
    var tmp0_elvis_lhs = this.keysToIndex_1.get_wei43m_k$(key);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return this._values_1.get_c1px32_k$(tmp);
  };
  protoOf(SortedMap).getKeyAt_fjx0tg_k$ = function (index) {
    this.ensureSorted_xyrndc_k$();
    return this._keys_1.get_c1px32_k$(index);
  };
  protoOf(SortedMap).getValueAt_u3x9km_k$ = function (index) {
    this.ensureSorted_xyrndc_k$();
    return this._values_1.get_c1px32_k$(index);
  };
  protoOf(SortedMap).fastForEach_ukfuzs_k$ = function (block) {
    var inductionVariable = 0;
    var last = this.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        block(n, this.getKeyAt_fjx0tg_k$(n), this.getValueAt_u3x9km_k$(n));
      }
       while (inductionVariable < last);
  };
  protoOf(SortedMap).nearestLowHighIndex_rgyzs3_k$ = function (key, doHigh) {
    this.ensureSorted_xyrndc_k$();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.datastructure.genericBinarySearch' call
      var low = 0;
      var high = this._keys_1.get_size_woubt6_k$() - 1 | 0;
      while (low <= high) {
        var mid = (low + high | 0) / 2 | 0;
        // Inline function 'korlibs.datastructure.SortedMap.nearestLowHighIndex.<anonymous>' call
        var mval = this.comparator_1.compare(this._keys_1.get_c1px32_k$(mid), key);
        if (mval < 0) {
          low = mid + 1 | 0;
        } else if (mval > 0) {
          high = mid - 1 | 0;
        } else {
          tmp$ret$1 = mid;
          break $l$block;
        }
      }
      // Inline function 'korlibs.datastructure.SortedMap.nearestLowHighIndex.<anonymous>' call
      var low_0 = low;
      var high_0 = high;
      var tmp;
      if (doHigh) {
        // Inline function 'kotlin.math.max' call
        tmp = Math.max(low_0, high_0);
      } else {
        // Inline function 'kotlin.math.min' call
        tmp = Math.min(low_0, high_0);
      }
      tmp$ret$1 = tmp;
    }
    return tmp$ret$1;
  };
  protoOf(SortedMap).nearestLowIndex_wxnbk4_k$ = function (key) {
    return this.nearestLowHighIndex_rgyzs3_k$(key, false);
  };
  protoOf(SortedMap).nearestHighIndex_127m6e_k$ = function (key) {
    return this.nearestLowHighIndex_rgyzs3_k$(key, true);
  };
  protoOf(SortedMap).nearestLow_8aspz6_k$ = function (key) {
    return getOrNull(this._keys_1, this.nearestLowIndex_wxnbk4_k$(key));
  };
  protoOf(SortedMap).nearestHigh_x4li4o_k$ = function (key) {
    return getOrNull(this._keys_1, this.nearestHighIndex_127m6e_k$(key));
  };
  protoOf(SortedMap).nearestLowExcludingExact_a51ah2_k$ = function (key) {
    var bindex = this.nearestLowIndex_wxnbk4_k$(key);
    var tmp;
    // Inline function 'kotlin.collections.contains' call
    // Inline function 'kotlin.collections.containsKey' call
    var this_0 = this.keysToIndex_1;
    if ((isInterface(this_0, Map_0) ? this_0 : THROW_CCE()).containsKey_aw81wo_k$(key)) {
      tmp = bindex - 1 | 0;
    } else {
      tmp = bindex;
    }
    var index = tmp;
    return getOrNull(this._keys_1, index);
  };
  protoOf(SortedMap).nearestHighExcludingExact_q0wc6o_k$ = function (key) {
    var bindex = this.nearestHighIndex_127m6e_k$(key);
    var tmp;
    // Inline function 'kotlin.collections.contains' call
    // Inline function 'kotlin.collections.containsKey' call
    var this_0 = this.keysToIndex_1;
    if ((isInterface(this_0, Map_0) ? this_0 : THROW_CCE()).containsKey_aw81wo_k$(key)) {
      tmp = bindex + 1 | 0;
    } else {
      tmp = bindex;
    }
    var index = tmp;
    return getOrNull(this._keys_1, index);
  };
  protoOf(SortedMap).keysToList_3da4zx_k$ = function () {
    this.ensureSorted_xyrndc_k$();
    return toFastList_0(this._keys_1);
  };
  protoOf(SortedMap).valuesToList_8870cb_k$ = function () {
    this.ensureSorted_xyrndc_k$();
    return toFastList_0(this._values_1);
  };
  protoOf(SortedMap).toList_edfyo7_k$ = function () {
    this.ensureSorted_xyrndc_k$();
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, this.get_size_woubt6_k$());
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.SortedMap.toList.<anonymous>' call
      var tmp$ret$0 = to(this._keys_1.get_c1px32_k$(item), this._values_1.get_c1px32_k$(item));
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    return destination;
  };
  protoOf(SortedMap).toMap_1tsnvl_k$ = function () {
    this.ensureSorted_xyrndc_k$();
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, this.get_size_woubt6_k$());
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.SortedMap.toMap.<anonymous>' call
      var tmp$ret$0 = to(this._keys_1.get_c1px32_k$(item), this._values_1.get_c1px32_k$(item));
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    return toLinkedMap(destination);
  };
  function sortedMapOf(values) {
    // Inline function 'kotlin.also' call
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_14();
    var this_0 = new SortedMap(ComparatorComparable());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.sortedMapOf.<anonymous>' call
    putAll_0(this_0, values);
    return this_0;
  }
  function SparseChunkedStackedIntArray2_init_$Init$(layers, empty, $this) {
    empty = empty === VOID ? -1 : empty;
    SparseChunkedStackedIntArray2.call($this, empty);
    // Inline function 'korlibs.datastructure.iterators.fastForEach' call
    var n = 0;
    while (n < layers.length) {
      // Inline function 'korlibs.datastructure.SparseChunkedStackedIntArray2.<init>.<anonymous>' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      var it = layers[_unary__edvuaz];
      $this.putChunk_ovl62r_k$(it);
    }
    return $this;
  }
  function SparseChunkedStackedIntArray2_init_$Create$(layers, empty) {
    return SparseChunkedStackedIntArray2_init_$Init$(layers, empty, objectCreate(protoOf(SparseChunkedStackedIntArray2)));
  }
  function _set_lastSearchChunk__7bw20m($this, _set____db54di) {
    $this.lastSearchChunk_1 = _set____db54di;
  }
  function _get_lastSearchChunk__hfm9j6($this) {
    return $this.lastSearchChunk_1;
  }
  function chunkX(_this__u8e3s4, $this, x) {
    return x - _this__u8e3s4.get_startX_jnf4u7_k$() | 0;
  }
  function chunkY(_this__u8e3s4, $this, y) {
    return y - _this__u8e3s4.get_startY_jnf4u8_k$() | 0;
  }
  function containsChunk(_this__u8e3s4, $this, x, y) {
    var tmp;
    var containsLower = _this__u8e3s4.get_startX_jnf4u7_k$();
    if (x < get_endX(_this__u8e3s4) ? containsLower <= x : false) {
      var containsLower_0 = _this__u8e3s4.get_startY_jnf4u8_k$();
      tmp = y < get_endY(_this__u8e3s4) ? containsLower_0 <= y : false;
    } else {
      tmp = false;
    }
    return tmp;
  }
  function SparseChunkedStackedIntArray2(empty) {
    empty = empty === VOID ? -1 : empty;
    this.empty_1 = empty;
    this.minX_1 = 0;
    this.minY_1 = 0;
    this.maxX_1 = 0;
    this.maxY_1 = 0;
    this.maxLevel_1 = 0;
    this.bvh_1 = new BVH(2);
    this.first_1 = null;
    this.last_1 = null;
    this.lastSearchChunk_1 = null;
  }
  protoOf(SparseChunkedStackedIntArray2).set_empty_6ahpp4_k$ = function (_set____db54di) {
    this.empty_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_empty_iqwn50_k$ = function () {
    return this.empty_1;
  };
  protoOf(SparseChunkedStackedIntArray2).set_minX_pa1wbf_k$ = function (_set____db54di) {
    this.minX_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_minX_woqhlb_k$ = function () {
    return this.minX_1;
  };
  protoOf(SparseChunkedStackedIntArray2).set_minY_ave96s_k$ = function (_set____db54di) {
    this.minY_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_minY_woqhlc_k$ = function () {
    return this.minY_1;
  };
  protoOf(SparseChunkedStackedIntArray2).set_maxX_swk5vt_k$ = function (_set____db54di) {
    this.maxX_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_maxX_woqbwd_k$ = function () {
    return this.maxX_1;
  };
  protoOf(SparseChunkedStackedIntArray2).set_maxY_78vzme_k$ = function (_set____db54di) {
    this.maxY_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_maxY_woqbwe_k$ = function () {
    return this.maxY_1;
  };
  protoOf(SparseChunkedStackedIntArray2).set_maxLevel_bcv8wl_k$ = function (_set____db54di) {
    this.maxLevel_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_maxLevel_pco3p3_k$ = function () {
    return this.maxLevel_1;
  };
  protoOf(SparseChunkedStackedIntArray2).get_bvh_18j8id_k$ = function () {
    return this.bvh_1;
  };
  protoOf(SparseChunkedStackedIntArray2).set_first_ofrcqh_k$ = function (_set____db54di) {
    this.first_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_first_irdx8n_k$ = function () {
    return this.first_1;
  };
  protoOf(SparseChunkedStackedIntArray2).set_last_isbdaf_k$ = function (_set____db54di) {
    this.last_1 = _set____db54di;
  };
  protoOf(SparseChunkedStackedIntArray2).get_last_wopotb_k$ = function () {
    return this.last_1;
  };
  protoOf(SparseChunkedStackedIntArray2).putChunk_ovl62r_k$ = function (chunk) {
    if (this.first_1 == null) {
      this.first_1 = chunk;
      this.empty_1 = chunk.get_empty_iqwn50_k$();
      this.minX_1 = 2147483647;
      this.minY_1 = 2147483647;
      this.maxX_1 = -2147483648;
      this.maxY_1 = -2147483648;
    }
    this.last_1 = chunk;
    this.bvh_1.insertOrUpdate_tsybz1_k$(Companion_getInstance_21().invoke_r4hpo3_k$(new Int32Array([chunk.get_startX_jnf4u7_k$(), chunk.get_width_j0q4yl_k$(), chunk.get_startY_jnf4u8_k$(), chunk.get_height_e7t92o_k$()])), chunk);
    var tmp = this;
    // Inline function 'kotlin.math.min' call
    var a = this.minX_1;
    var b = chunk.get_startX_jnf4u7_k$();
    tmp.minX_1 = Math.min(a, b);
    var tmp_0 = this;
    // Inline function 'kotlin.math.min' call
    var a_0 = this.minY_1;
    var b_0 = chunk.get_startY_jnf4u8_k$();
    tmp_0.minY_1 = Math.min(a_0, b_0);
    var tmp_1 = this;
    // Inline function 'kotlin.math.max' call
    var a_1 = this.maxX_1;
    var b_1 = get_endX(chunk);
    tmp_1.maxX_1 = Math.max(a_1, b_1);
    var tmp_2 = this;
    // Inline function 'kotlin.math.max' call
    var a_2 = this.maxY_1;
    var b_2 = get_endY(chunk);
    tmp_2.maxY_1 = Math.max(a_2, b_2);
    var tmp_3 = this;
    // Inline function 'kotlin.math.max' call
    var a_3 = this.maxLevel_1;
    var b_3 = chunk.get_maxLevel_pco3p3_k$();
    tmp_3.maxLevel_1 = Math.max(a_3, b_3);
  };
  protoOf(SparseChunkedStackedIntArray2).get_startX_jnf4u7_k$ = function () {
    return this.minX_1;
  };
  protoOf(SparseChunkedStackedIntArray2).get_startY_jnf4u8_k$ = function () {
    return this.minY_1;
  };
  protoOf(SparseChunkedStackedIntArray2).get_width_j0q4yl_k$ = function () {
    return this.maxX_1 - this.minX_1 | 0;
  };
  protoOf(SparseChunkedStackedIntArray2).get_height_e7t92o_k$ = function () {
    return this.maxY_1 - this.minY_1 | 0;
  };
  protoOf(SparseChunkedStackedIntArray2).findAllChunks_avlqmq_k$ = function () {
    return this.bvh_1.findAllValues_1zr34m_k$();
  };
  protoOf(SparseChunkedStackedIntArray2).getChunkAt_7vb48m_k$ = function (x, y) {
    var tmp32_safe_receiver = this.lastSearchChunk_1;
    if (tmp32_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.contracts.contract' call
      var tmp;
      if (containsChunk(tmp32_safe_receiver, this, x, y)) {
        return tmp32_safe_receiver;
      }
    }
    this.lastSearchChunk_1 = firstOrNull(this.bvh_1.searchValues$default_6yvp2t_k$(Companion_getInstance_21().invoke_r4hpo3_k$(new Int32Array([x, 1, y, 1]))));
    return this.lastSearchChunk_1;
  };
  protoOf(SparseChunkedStackedIntArray2).inside_c6756s_k$ = function (x, y) {
    return !(this.getChunkAt_7vb48m_k$(x, y) == null);
  };
  protoOf(SparseChunkedStackedIntArray2).set_6f0u4y_k$ = function (x, y, level, value) {
    var tmp33_safe_receiver = this.getChunkAt_7vb48m_k$(x, y);
    if (tmp33_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.contracts.contract' call
      tmp33_safe_receiver.set_6f0u4y_k$(chunkX(tmp33_safe_receiver, this, x), chunkY(tmp33_safe_receiver, this, y), level, value);
    }
  };
  protoOf(SparseChunkedStackedIntArray2).get_ky6wce_k$ = function (x, y, level) {
    var tmp34_safe_receiver = this.getChunkAt_7vb48m_k$(x, y);
    if (tmp34_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.contracts.contract' call
      return tmp34_safe_receiver.get_ky6wce_k$(chunkX(tmp34_safe_receiver, this, x), chunkY(tmp34_safe_receiver, this, y), level);
    }
    return this.empty_1;
  };
  protoOf(SparseChunkedStackedIntArray2).getStackLevel_20g9m6_k$ = function (x, y) {
    var tmp35_safe_receiver = this.getChunkAt_7vb48m_k$(x, y);
    if (tmp35_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.contracts.contract' call
      return tmp35_safe_receiver.getStackLevel_20g9m6_k$(chunkX(tmp35_safe_receiver, this, x), chunkY(tmp35_safe_receiver, this, y));
    }
    return 0;
  };
  protoOf(SparseChunkedStackedIntArray2).push_96pvvy_k$ = function (x, y, value) {
    var tmp36_safe_receiver = this.getChunkAt_7vb48m_k$(x, y);
    if (tmp36_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.contracts.contract' call
      tmp36_safe_receiver.push_96pvvy_k$(chunkX(tmp36_safe_receiver, this, x), chunkY(tmp36_safe_receiver, this, y), value);
    }
  };
  protoOf(SparseChunkedStackedIntArray2).removeLast_2ln5zq_k$ = function (x, y) {
    var tmp37_safe_receiver = this.getChunkAt_7vb48m_k$(x, y);
    if (tmp37_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      // Inline function 'kotlin.contracts.contract' call
      tmp37_safe_receiver.removeLast_2ln5zq_k$(chunkX(tmp37_safe_receiver, this, x), chunkY(tmp37_safe_receiver, this, y));
    }
  };
  protoOf(SparseChunkedStackedIntArray2).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.also' call
    var this_0 = new SparseChunkedStackedIntArray2(this.empty_1);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.SparseChunkedStackedIntArray2.clone.<anonymous>' call
    // Inline function 'korlibs.datastructure.iterators.fastForEach' call
    var this_1 = this.findAllChunks_avlqmq_k$();
    var n = 0;
    while (n < this_1.get_size_woubt6_k$()) {
      // Inline function 'korlibs.datastructure.SparseChunkedStackedIntArray2.clone.<anonymous>.<anonymous>' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      var it = this_1.get_c1px32_k$(_unary__edvuaz);
      this_0.putChunk_ovl62r_k$(it.clone_1keycd_k$());
    }
    return this_0;
  };
  function _IntStack___init__impl__t6qo2g(items) {
    items = items === VOID ? new IntArrayList() : items;
    return items;
  }
  function _get_items__fzd5gv_2($this) {
    return $this;
  }
  function _IntStack___get_size__impl__uebm0g($this) {
    return _get_items__fzd5gv_2($this).get_size_woubt6_k$();
  }
  function IntStack__isEmpty_impl_t9ever($this) {
    return _IntStack___get_size__impl__uebm0g($this) === 0;
  }
  function Companion_15() {
    Companion_instance_15 = this;
  }
  protoOf(Companion_15).invoke_soxitb_k$ = function (items) {
    var out = _IntStack___init__impl__t6qo2g(new IntArrayList(items.length));
    var inductionVariable = 0;
    var last = items.length;
    while (inductionVariable < last) {
      var item = items[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      IntStack__push_impl_ipqpa2(out, item);
    }
    return out;
  };
  var Companion_instance_15;
  function Companion_getInstance_15() {
    if (Companion_instance_15 == null)
      new Companion_15();
    return Companion_instance_15;
  }
  function IntStack__push_impl_ipqpa2($this, v) {
    _get_items__fzd5gv_2($this).add_c9dakn_k$(v);
  }
  function IntStack__pop_impl_e52gy3($this) {
    return _get_items__fzd5gv_2($this).removeAt_6niowx_k$(_get_items__fzd5gv_2($this).get_size_woubt6_k$() - 1 | 0);
  }
  function IntStack__peek_impl_78nhuv($this) {
    return lastOrNull_0(_get_items__fzd5gv_2($this));
  }
  function IntStack__clear_impl_995rlz($this) {
    _get_items__fzd5gv_2($this).clear_j9egeb_k$();
  }
  function IntStack__contains_impl_rap6nf($this, element) {
    return _get_items__fzd5gv_2($this).contains_7q95ev_k$(element);
  }
  function IntStack__contains_impl_rap6nf_0($this, element) {
    if (!(!(element == null) ? typeof element === 'number' : false))
      return false;
    return IntStack__contains_impl_rap6nf($this.items_1, (!(element == null) ? typeof element === 'number' : false) ? element : THROW_CCE());
  }
  function IntStack__containsAll_impl_1da5yc($this, elements) {
    return _get_items__fzd5gv_2($this).containsAll_tftgie_k$(elements);
  }
  function IntStack__containsAll_impl_1da5yc_0($this, elements) {
    return IntStack__containsAll_impl_1da5yc($this.items_1, elements);
  }
  function IntStack__iterator_impl_8ri3fe($this) {
    return _get_items__fzd5gv_2($this).iterator_jk1svi_k$();
  }
  function IntStack__toString_impl_ybxqmw($this) {
    return 'IntStack(items=' + $this.toString() + ')';
  }
  function IntStack__hashCode_impl_ey0ejd($this) {
    return $this.hashCode();
  }
  function IntStack__equals_impl_qppir9($this, other) {
    if (!(other instanceof IntStack))
      return false;
    var tmp0_other_with_cast = other instanceof IntStack ? other.items_1 : THROW_CCE();
    if (!$this.equals(tmp0_other_with_cast))
      return false;
    return true;
  }
  function IntStack(items) {
    Companion_getInstance_15();
    this.items_1 = items;
  }
  protoOf(IntStack).get_size_woubt6_k$ = function () {
    return _IntStack___get_size__impl__uebm0g(this.items_1);
  };
  protoOf(IntStack).isEmpty_y1axqb_k$ = function () {
    return IntStack__isEmpty_impl_t9ever(this.items_1);
  };
  protoOf(IntStack).contains_7q95ev_k$ = function (element) {
    return IntStack__contains_impl_rap6nf(this.items_1, element);
  };
  protoOf(IntStack).contains_aljjnj_k$ = function (element) {
    return IntStack__contains_impl_rap6nf_0(this, element);
  };
  protoOf(IntStack).containsAll_tftgie_k$ = function (elements) {
    return IntStack__containsAll_impl_1da5yc(this.items_1, elements);
  };
  protoOf(IntStack).containsAll_xk45sd_k$ = function (elements) {
    return IntStack__containsAll_impl_1da5yc_0(this, elements);
  };
  protoOf(IntStack).iterator_jk1svi_k$ = function () {
    return IntStack__iterator_impl_8ri3fe(this.items_1);
  };
  protoOf(IntStack).toString = function () {
    return IntStack__toString_impl_ybxqmw(this.items_1);
  };
  protoOf(IntStack).hashCode = function () {
    return IntStack__hashCode_impl_ey0ejd(this.items_1);
  };
  protoOf(IntStack).equals = function (other) {
    return IntStack__equals_impl_qppir9(this.items_1, other);
  };
  function _TGenStack___init__impl__yynjgt(items) {
    items = items === VOID ? FastArrayList_init_$Create$() : items;
    return items;
  }
  function _get_items__fzd5gv_3($this) {
    return $this;
  }
  function _TGenStack___get_size__impl__cvg66t($this) {
    return _get_items__fzd5gv_3($this).get_size_woubt6_k$();
  }
  function TGenStack__isEmpty_impl_bsbpyi($this) {
    return _TGenStack___get_size__impl__cvg66t($this) === 0;
  }
  function Companion_16() {
    Companion_instance_16 = this;
  }
  protoOf(Companion_16).invoke_r8h3s5_k$ = function (items) {
    var out = _TGenStack___init__impl__yynjgt(FastArrayList_init_$Create$_0(items.length));
    var inductionVariable = 0;
    var last = items.length;
    while (inductionVariable < last) {
      var item = items[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      TGenStack__push_impl_9yh33t(out, item);
    }
    return out;
  };
  var Companion_instance_16;
  function Companion_getInstance_16() {
    if (Companion_instance_16 == null)
      new Companion_16();
    return Companion_instance_16;
  }
  function TGenStack__push_impl_9yh33t($this, v) {
    _get_items__fzd5gv_3($this).add_utx5q5_k$(v);
  }
  function TGenStack__pop_impl_qisxa0($this) {
    return _get_items__fzd5gv_3($this).removeAt_6niowx_k$(_get_items__fzd5gv_3($this).get_size_woubt6_k$() - 1 | 0);
  }
  function TGenStack__peek_impl_z48rqe($this) {
    return lastOrNull(_get_items__fzd5gv_3($this));
  }
  function TGenStack__clear_impl_pali4s($this) {
    _get_items__fzd5gv_3($this).clear_j9egeb_k$();
  }
  function TGenStack__contains_impl_l8kli6($this, element) {
    return _get_items__fzd5gv_3($this).contains_aljjnj_k$(element);
  }
  function TGenStack__contains_impl_l8kli6_0($this, element) {
    if (!(element == null ? true : !(element == null)))
      return false;
    return TGenStack__contains_impl_l8kli6($this.items_1, (element == null ? true : !(element == null)) ? element : THROW_CCE());
  }
  function TGenStack__containsAll_impl_ic9i7r($this, elements) {
    return _get_items__fzd5gv_3($this).containsAll_xk45sd_k$(elements);
  }
  function TGenStack__containsAll_impl_ic9i7r_0($this, elements) {
    return TGenStack__containsAll_impl_ic9i7r($this.items_1, elements);
  }
  function TGenStack__iterator_impl_2pdia5($this) {
    return _get_items__fzd5gv_3($this).iterator_jk1svi_k$();
  }
  function TGenStack__toString_impl_s9t5hn($this) {
    return 'TGenStack(items=' + $this.toString() + ')';
  }
  function TGenStack__hashCode_impl_l04zom($this) {
    return $this.hashCode();
  }
  function TGenStack__equals_impl_w9i35u($this, other) {
    if (!(other instanceof TGenStack))
      return false;
    var tmp0_other_with_cast = other instanceof TGenStack ? other.items_1 : THROW_CCE();
    if (!$this.equals(tmp0_other_with_cast))
      return false;
    return true;
  }
  function TGenStack(items) {
    Companion_getInstance_16();
    this.items_1 = items;
  }
  protoOf(TGenStack).get_size_woubt6_k$ = function () {
    return _TGenStack___get_size__impl__cvg66t(this.items_1);
  };
  protoOf(TGenStack).isEmpty_y1axqb_k$ = function () {
    return TGenStack__isEmpty_impl_bsbpyi(this.items_1);
  };
  protoOf(TGenStack).contains_c6f694_k$ = function (element) {
    return TGenStack__contains_impl_l8kli6(this.items_1, element);
  };
  protoOf(TGenStack).contains_aljjnj_k$ = function (element) {
    return TGenStack__contains_impl_l8kli6_0(this, element);
  };
  protoOf(TGenStack).containsAll_nns6wg_k$ = function (elements) {
    return TGenStack__containsAll_impl_ic9i7r(this.items_1, elements);
  };
  protoOf(TGenStack).containsAll_xk45sd_k$ = function (elements) {
    return TGenStack__containsAll_impl_ic9i7r_0(this, elements);
  };
  protoOf(TGenStack).iterator_jk1svi_k$ = function () {
    return TGenStack__iterator_impl_2pdia5(this.items_1);
  };
  protoOf(TGenStack).toString = function () {
    return TGenStack__toString_impl_s9t5hn(this.items_1);
  };
  protoOf(TGenStack).hashCode = function () {
    return TGenStack__hashCode_impl_l04zom(this.items_1);
  };
  protoOf(TGenStack).equals = function (other) {
    return TGenStack__equals_impl_w9i35u(this.items_1, other);
  };
  function Companion_17() {
    Companion_instance_17 = this;
  }
  protoOf(Companion_17).invoke_p7tc4f_k$ = function (layers, width, height, empty, startX, startY) {
    var stacked = new StackedIntArray2(width, height, empty, startX, startY);
    // Inline function 'korlibs.datastructure.fill' call
    var this_0 = stacked.level_1;
    var n = 0;
    var inductionVariable = 0;
    var last = this_0.get_height_e7t92o_k$();
    if (inductionVariable < last)
      do {
        var y = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var inductionVariable_0 = 0;
        var last_0 = this_0.get_width_j0q4yl_k$();
        if (inductionVariable_0 < last_0)
          do {
            var x = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            var tmp = n;
            // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
            this_0.getAt_k8n1td_k$(n);
            var tmp$ret$0 = layers.length;
            this_0.setAt_tlf2vp_k$(tmp, tmp$ret$0);
            n = n + 1 | 0;
          }
           while (inductionVariable_0 < last_0);
      }
       while (inductionVariable < last);
    addAll_0(stacked.data_1, layers);
    return stacked;
  };
  protoOf(Companion_17).invoke$default_o6rois_k$ = function (layers, width, height, empty, startX, startY, $super) {
    width = width === VOID ? first_0(layers).get_width_j0q4yl_k$() : width;
    height = height === VOID ? first_0(layers).get_height_e7t92o_k$() : height;
    empty = empty === VOID ? -1 : empty;
    startX = startX === VOID ? 0 : startX;
    startY = startY === VOID ? 0 : startY;
    return $super === VOID ? this.invoke_p7tc4f_k$(layers, width, height, empty, startX, startY) : $super.invoke_p7tc4f_k$.call(this, layers, width, height, empty, startX, startY);
  };
  var Companion_instance_17;
  function Companion_getInstance_17() {
    if (Companion_instance_17 == null)
      new Companion_17();
    return Companion_instance_17;
  }
  function StackedIntArray2(width, height, empty, startX, startY) {
    Companion_getInstance_17();
    empty = empty === VOID ? -1 : empty;
    startX = startX === VOID ? 0 : startX;
    startY = startY === VOID ? 0 : startY;
    this.width_1 = width;
    this.height_1 = height;
    this.empty_1 = empty;
    this.startX_1 = startX;
    this.startY_1 = startY;
    var tmp = this;
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_1();
    var width_0 = this.width_1;
    var height_0 = this.height_1;
    var tmp_0 = 0;
    var tmp_1 = imul(width_0, height_0);
    var tmp_2 = new Int32Array(tmp_1);
    while (tmp_0 < tmp_1) {
      tmp_2[tmp_0] = 0;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_3 = tmp_2;
    tmp.level_1 = new IntArray2(width_0, height_0, isIntArray(tmp_3) ? tmp_3 : THROW_CCE());
    this.data_1 = fastArrayListOf([]);
  }
  protoOf(StackedIntArray2).get_width_j0q4yl_k$ = function () {
    return this.width_1;
  };
  protoOf(StackedIntArray2).get_height_e7t92o_k$ = function () {
    return this.height_1;
  };
  protoOf(StackedIntArray2).get_empty_iqwn50_k$ = function () {
    return this.empty_1;
  };
  protoOf(StackedIntArray2).get_startX_jnf4u7_k$ = function () {
    return this.startX_1;
  };
  protoOf(StackedIntArray2).get_startY_jnf4u8_k$ = function () {
    return this.startY_1;
  };
  protoOf(StackedIntArray2).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.also' call
    var this_0 = new StackedIntArray2(this.width_1, this.height_1, this.empty_1, this.startX_1, this.startY_1);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.StackedIntArray2.clone.<anonymous>' call
    Memory_getInstance().arraycopy_7oxh5s_k$(this.level_1.get_data_wokkxf_k$(), 0, this_0.level_1.get_data_wokkxf_k$(), 0, this_0.level_1.get_data_wokkxf_k$().length);
    // Inline function 'kotlin.collections.map' call
    var this_1 = this.data_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_1, 10));
    var tmp0_iterator = this_1.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.StackedIntArray2.clone.<anonymous>.<anonymous>' call
      var tmp$ret$0 = item.clone_1keycd_k$();
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    this_0.data_1.addAll_4lagoh_k$(destination);
    return this_0;
  };
  protoOf(StackedIntArray2).get_level_ium7h7_k$ = function () {
    return this.level_1;
  };
  protoOf(StackedIntArray2).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(StackedIntArray2).get_maxLevel_pco3p3_k$ = function () {
    return this.data_1.get_size_woubt6_k$();
  };
  protoOf(StackedIntArray2).ensureLevel_z25ixq_k$ = function (level) {
    while (level >= this.data_1.get_size_woubt6_k$()) {
      // Inline function 'korlibs.datastructure.Companion.invoke' call
      Companion_getInstance_1();
      var width = this.width_1;
      var height = this.height_1;
      var fill = this.empty_1;
      var tmp = 0;
      var tmp_0 = imul(width, height);
      var tmp_1 = new Int32Array(tmp_0);
      while (tmp < tmp_0) {
        tmp_1[tmp] = fill;
        tmp = tmp + 1 | 0;
      }
      var tmp_2 = tmp_1;
      var tmp$ret$1 = new IntArray2(width, height, isIntArray(tmp_2) ? tmp_2 : THROW_CCE());
      this.data_1.add_utx5q5_k$(tmp$ret$1);
    }
  };
  protoOf(StackedIntArray2).setLayer_vhpfn2_k$ = function (level, data) {
    this.ensureLevel_z25ixq_k$(level);
    this.data_1.set_82063s_k$(level, data);
  };
  protoOf(StackedIntArray2).set_6f0u4y_k$ = function (x, y, level, value) {
    this.ensureLevel_z25ixq_k$(level);
    this.data_1.get_c1px32_k$(level).set_7qmx5y_k$(x, y, value);
  };
  protoOf(StackedIntArray2).get_ky6wce_k$ = function (x, y, level) {
    if (level > this.level_1.get_bzg6vq_k$(x, y))
      return this.empty_1;
    return this.data_1.get_c1px32_k$(level).get_bzg6vq_k$(x, y);
  };
  protoOf(StackedIntArray2).getStackLevel_20g9m6_k$ = function (x, y) {
    return this.level_1.get_bzg6vq_k$(x, y);
  };
  protoOf(StackedIntArray2).push_96pvvy_k$ = function (x, y, value) {
    var _array__4zh2yp = this.level_1;
    var _unary__edvuaz = _array__4zh2yp.get_bzg6vq_k$(x, y);
    _array__4zh2yp.set_7qmx5y_k$(x, y, _unary__edvuaz + 1 | 0);
    this.set_6f0u4y_k$(x, y, _unary__edvuaz, value);
  };
  protoOf(StackedIntArray2).removeLast_2ln5zq_k$ = function (x, y) {
    this.level_1.set_7qmx5y_k$(x, y, coerceAtLeast(this.level_1.get_bzg6vq_k$(x, y) - 1 | 0, 0));
  };
  function IStackedIntArray2() {
  }
  function get_endX(_this__u8e3s4) {
    return _this__u8e3s4.get_startX_jnf4u7_k$() + _this__u8e3s4.get_width_j0q4yl_k$() | 0;
  }
  function get_endY(_this__u8e3s4) {
    return _this__u8e3s4.get_startY_jnf4u8_k$() + _this__u8e3s4.get_height_e7t92o_k$() | 0;
  }
  function getOrPut_1(_this__u8e3s4, key, value) {
    if (!_this__u8e3s4.contains_7vtym0_k$(key)) {
      _this__u8e3s4.set_c2d6a0_k$(key, value(key));
    }
    return ensureNotNull(_this__u8e3s4.get_h31hzz_k$(key));
  }
  function _get_out__e6ecxv($this) {
    return $this.out_1;
  }
  function _get_lock__d9xa4g_0($this) {
    return $this.lock_1;
  }
  function Companion_18() {
    Companion_instance_18 = this;
  }
  protoOf(Companion_18).values_ajscjb_k$ = function (values, start, end) {
    // Inline function 'kotlin.also' call
    var this_0 = new Historiogram();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.algo.Companion.values.<anonymous>' call
    this_0.addArray_11qt99_k$(values, start, end);
    return this_0.out_1;
  };
  protoOf(Companion_18).values$default_wdzule_k$ = function (values, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? values.length : end;
    return $super === VOID ? this.values_ajscjb_k$(values, start, end) : $super.values_ajscjb_k$.call(this, values, start, end);
  };
  var Companion_instance_18;
  function Companion_getInstance_18() {
    if (Companion_instance_18 == null)
      new Companion_18();
    return Companion_instance_18;
  }
  function Historiogram$add$lambda() {
    return 0;
  }
  function Historiogram$addArray$lambda() {
    return 0;
  }
  function Historiogram(out) {
    Companion_getInstance_18();
    out = out === VOID ? IntIntMap_init_$Create$() : out;
    this.out_1 = out;
    this.lock_1 = new NonRecursiveLock();
  }
  protoOf(Historiogram).add_c9dakn_k$ = function (value) {
    // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
    this.lock_1;
    // Inline function 'korlibs.datastructure.algo.Historiogram.add.<anonymous>' call
    this.out_1.getOrPut_5k9000_k$(value, Historiogram$add$lambda);
    var _array__4zh2yp = this.out_1;
    var _unary__edvuaz = _array__4zh2yp.get_c1px32_k$(value);
    _array__4zh2yp.set_tq3pjy_k$(value, _unary__edvuaz + 1 | 0);
  };
  protoOf(Historiogram).addArray_11qt99_k$ = function (values, start, end) {
    // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
    this.lock_1;
    var inductionVariable = start;
    var tmp;
    if (inductionVariable < end) {
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var value = values[n];
        this.out_1.getOrPut_5k9000_k$(value, Historiogram$addArray$lambda);
        var _array__4zh2yp = this.out_1;
        var _unary__edvuaz = _array__4zh2yp.get_c1px32_k$(value);
        _array__4zh2yp.set_tq3pjy_k$(value, _unary__edvuaz + 1 | 0);
      }
       while (inductionVariable < end);
      tmp = Unit_getInstance();
    }
  };
  protoOf(Historiogram).addArray$default_siqiri_k$ = function (values, start, end, $super) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? values.length : end;
    var tmp;
    if ($super === VOID) {
      this.addArray_11qt99_k$(values, start, end);
      tmp = Unit_getInstance();
    } else {
      tmp = $super.addArray_11qt99_k$.call(this, values, start, end);
    }
    return tmp;
  };
  protoOf(Historiogram).getMapCopy_uz6ncr_k$ = function () {
    var map = IntIntMap_init_$Create$();
    // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
    this.lock_1;
    var this_0 = this.out_1;
    var index = this_0.get_hasZero_7r5aaq_k$() ? 2147483647 : this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), 0);
    while (!(index === 2147483646)) {
      // Inline function 'korlibs.datastructure.IntIntMap.fastForEach.<anonymous>' call
      var it;
      switch (index) {
        case 2147483647:
        case 2147483646:
          it = 0;
          break;
        default:
          it = this_0.get__keys_z80zht_k$()[index];
          break;
      }
      // Inline function 'korlibs.datastructure.algo.Historiogram.getMapCopy.<anonymous>.<anonymous>' call
      var key = it;
      var value = this_0.get_c1px32_k$(it);
      map.set_tq3pjy_k$(key, value);
      index = this_0.nextNonEmptyIndex_qs84fl_k$(this_0.get__keys_z80zht_k$(), index === 2147483647 ? 0 : index + 1 | 0);
    }
    return map;
  };
  protoOf(Historiogram).clone_1keycd_k$ = function () {
    return new Historiogram(this.getMapCopy_uz6ncr_k$());
  };
  protoOf(Historiogram).toString = function () {
    return 'Historiogram(' + toString(this.getMapCopy_uz6ncr_k$().toMap$default_tldzuf_k$()) + ')';
  };
  function RLE$Companion$compute$lambda(it) {
    return true;
  }
  function Companion_19() {
    Companion_instance_19 = this;
  }
  protoOf(Companion_19).compute_yx8rv4_k$ = function (data, start, end, out) {
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count = end - start | 0;
    out.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue = 0;
    var currentStart = 0;
    var nchunk = 0;
    var inductionVariable = 0;
    var last = count + 1 | 0;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp;
        if (n === count) {
          tmp = lastValue + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp = data[start + n | 0];
        }
        var value = tmp;
        if (n === 0 ? true : !(value === lastValue)) {
          if (!(currentStart === n)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk = nchunk + 1 | 0;
            var start_0 = currentStart;
            var count_0 = n - currentStart | 0;
            var value_0 = lastValue;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out.emit_qlraj_k$(start_0, count_0, value_0);
            }
          }
          currentStart = n;
        }
        lastValue = value;
      }
       while (inductionVariable < last);
    return out;
  };
  protoOf(Companion_19).compute_6l7ahw_k$ = function (count, out, filter, gen) {
    out.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue = 0;
    var currentStart = 0;
    var nchunk = 0;
    var inductionVariable = 0;
    var last = count + 1 | 0;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var value = n === count ? lastValue + 1 | 0 : gen(n);
        if (n === 0 ? true : !(value === lastValue)) {
          if (!(currentStart === n)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk = nchunk + 1 | 0;
            var start = currentStart;
            var count_0 = n - currentStart | 0;
            var value_0 = lastValue;
            if (filter(value_0)) {
              out.emit_qlraj_k$(start, count_0, value_0);
            }
          }
          currentStart = n;
        }
        lastValue = value;
      }
       while (inductionVariable < last);
    return out;
  };
  protoOf(Companion_19).emit_91tc98_k$ = function (count, emit, gen) {
    var lastValue = 0;
    var currentStart = 0;
    var nchunk = 0;
    var inductionVariable = 0;
    var last = count + 1 | 0;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var value = n === count ? lastValue + 1 | 0 : gen(n);
        if (n === 0 ? true : !(value === lastValue)) {
          if (!(currentStart === n)) {
            var _unary__edvuaz = nchunk;
            nchunk = _unary__edvuaz + 1 | 0;
            emit(_unary__edvuaz, currentStart, n - currentStart | 0, lastValue);
          }
          currentStart = n;
        }
        lastValue = value;
      }
       while (inductionVariable < last);
  };
  var Companion_instance_19;
  function Companion_getInstance_19() {
    if (Companion_instance_19 == null)
      new Companion_19();
    return Companion_instance_19;
  }
  function RLE(capacity) {
    Companion_getInstance_19();
    capacity = capacity === VOID ? 7 : capacity;
    this.capacity_1 = capacity;
    this.data_1 = new IntArrayList(this.capacity_1);
  }
  protoOf(RLE).get_capacity_wxbgcd_k$ = function () {
    return this.capacity_1;
  };
  protoOf(RLE).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(RLE).get_count_ipufhi_k$ = function () {
    return this.data_1.get_size_woubt6_k$() / 3 | 0;
  };
  protoOf(RLE).emit_qlraj_k$ = function (start, count, value) {
    this.data_1.add_krfrlz_k$(start, count, value);
  };
  protoOf(RLE).clear_j9egeb_k$ = function () {
    this.data_1.clear_j9egeb_k$();
  };
  protoOf(RLE).fastForEach_y5na3y_k$ = function (block) {
    var inductionVariable = 0;
    var last = this.get_count_ipufhi_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        block(n, this.data_1.get_c1px32_k$(imul(n, 3) + 0 | 0), this.data_1.get_c1px32_k$(imul(n, 3) + 1 | 0), this.data_1.get_c1px32_k$(imul(n, 3) + 2 | 0));
      }
       while (inductionVariable < last);
  };
  protoOf(RLE).toString = function () {
    // Inline function 'kotlin.text.buildString' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.apply' call
    var this_0 = StringBuilder_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.algo.RLE.toString.<anonymous>' call
    this_0.append_22ad7x_k$('RLE(');
    // Inline function 'korlibs.datastructure.algo.RLE.fastForEach' call
    var inductionVariable = 0;
    var last = this.get_count_ipufhi_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.algo.RLE.toString.<anonymous>.<anonymous>' call
        var start = this.data_1.get_c1px32_k$(imul(n, 3) + 0 | 0);
        var end = this.data_1.get_c1px32_k$(imul(n, 3) + 1 | 0);
        var value = this.data_1.get_c1px32_k$(imul(n, 3) + 2 | 0);
        if (!(n === 0)) {
          this_0.append_22ad7x_k$(', ');
        }
        this_0.append_22ad7x_k$('[');
        this_0.append_22ad7x_k$('(');
        this_0.append_uppzia_k$(value);
        this_0.append_22ad7x_k$('),');
        this_0.append_uppzia_k$(start);
        this_0.append_22ad7x_k$(',');
        this_0.append_uppzia_k$(end);
        this_0.append_22ad7x_k$(']');
      }
       while (inductionVariable < last);
    this_0.append_22ad7x_k$(')');
    return this_0.toString();
  };
  function Template() {
  }
  protoOf(Template).equals = function (other) {
    if (!(other instanceof Template))
      return false;
    other instanceof Template || THROW_CCE();
    return true;
  };
  protoOf(Template).hashCode = function () {
    return 0;
  };
  protoOf(Template).toString = function () {
    return '@korlibs.datastructure.annotations.Template()';
  };
  function ComparatorComparable() {
    var tmp = ComparatorComparable_getInstance();
    return isInterface(tmp, Comparator) ? tmp : THROW_CCE();
  }
  function ComparatorComparable_0() {
    ComparatorComparable_instance = this;
  }
  protoOf(ComparatorComparable_0).compare_ftmrut_k$ = function (a, b) {
    return compareTo(a, b);
  };
  protoOf(ComparatorComparable_0).compare = function (a, b) {
    var tmp = (!(a == null) ? isComparable(a) : false) ? a : THROW_CCE();
    return this.compare_ftmrut_k$(tmp, (!(b == null) ? isComparable(b) : false) ? b : THROW_CCE());
  };
  var ComparatorComparable_instance;
  function ComparatorComparable_getInstance() {
    if (ComparatorComparable_instance == null)
      new ComparatorComparable_0();
    return ComparatorComparable_instance;
  }
  function BVH$IntersectResult$point$delegate$lambda(this$0) {
    return function () {
      var tmp = 0;
      var tmp_0 = _BVHRay___get_dimensions__impl__ba0c7z(this$0.ray_1);
      var tmp_1 = new Float64Array(tmp_0);
      while (tmp < tmp_0) {
        var tmp_2 = tmp;
        tmp_1[tmp_2] = BVHRay__pos_impl_rqkdin(this$0.ray_1, tmp_2) + BVHRay__dir_impl_jibowm(this$0.ray_1, tmp_2) * this$0.intersect_1;
        tmp = tmp + 1 | 0;
      }
      return new BVHVector(tmp_1);
    };
  }
  function BVH$IntersectResult$normal$delegate$lambda(this$0) {
    return function () {
      var bounds = this$0.obj_1.d_1;
      var tmp = 0;
      var tmp_0 = _BVHRay___get_dimensions__impl__ba0c7z(this$0.ray_1);
      var tmp_1 = new Float64Array(tmp_0);
      while (tmp < tmp_0) {
        var tmp_2 = tmp;
        var bmin = BVHRect__min_impl_5ss93n(bounds, tmp_2);
        var bmax = BVHRect__max_impl_z6ab7l(bounds, tmp_2);
        var p = this$0.get_point_iwziav_k$().get_c1px32_k$(tmp_2);
        tmp_1[tmp_2] = (isAlmostEquals_2(bmin, p) ? true : p < bmin) ? -1.0 : (isAlmostEquals_2(bmax, p) ? true : p > bmax) ? 1.0 : 0.0;
        tmp = tmp + 1 | 0;
      }
      return new BVHVector(tmp_1);
    };
  }
  function Companion_20() {
    Companion_instance_20 = this;
    Comparators.call(this);
  }
  var Companion_instance_20;
  function Companion_getInstance_20() {
    if (Companion_instance_20 == null)
      new Companion_20();
    return Companion_instance_20;
  }
  function _get_maxWidth__golao9($this) {
    return $this.maxWidth_1;
  }
  function _get_minWidth__tgi6yf($this) {
    return $this.minWidth_1;
  }
  function Node(d, id, nodes, value) {
    id = id === VOID ? null : id;
    nodes = nodes === VOID ? null : nodes;
    value = value === VOID ? null : value;
    this.d_1 = d;
    this.id_1 = id;
    this.nodes_1 = nodes;
    this.value_1 = value;
  }
  protoOf(Node).set_d_v6n58q_k$ = function (_set____db54di) {
    this.d_1 = _set____db54di;
  };
  protoOf(Node).get_d_89sr20_k$ = function () {
    return this.d_1;
  };
  protoOf(Node).set_id_wyyipr_k$ = function (_set____db54di) {
    this.id_1 = _set____db54di;
  };
  protoOf(Node).get_id_kntnx8_k$ = function () {
    return this.id_1;
  };
  protoOf(Node).set_nodes_bpxpk_k$ = function (_set____db54di) {
    this.nodes_1 = _set____db54di;
  };
  protoOf(Node).get_nodes_ivvt6w_k$ = function () {
    return this.nodes_1;
  };
  protoOf(Node).set_value_usdbpe_k$ = function (_set____db54di) {
    this.value_1 = _set____db54di;
  };
  protoOf(Node).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  protoOf(Node).component1_i30fb5_k$ = function () {
    return this.d_1;
  };
  protoOf(Node).component2_7eebsb_k$ = function () {
    return this.id_1;
  };
  protoOf(Node).component3_7eebsa_k$ = function () {
    return this.nodes_1;
  };
  protoOf(Node).component4_7eebs9_k$ = function () {
    return this.value_1;
  };
  protoOf(Node).copy_raib9r_k$ = function (d, id, nodes, value) {
    return new Node(d, id, nodes, value);
  };
  protoOf(Node).copy$default_vo7qow_k$ = function (d, id, nodes, value, $super) {
    d = d === VOID ? this.d_1 : d;
    id = id === VOID ? this.id_1 : id;
    nodes = nodes === VOID ? this.nodes_1 : nodes;
    value = value === VOID ? this.value_1 : value;
    return $super === VOID ? this.copy_raib9r_k$(d, id, nodes, value) : $super.copy_raib9r_k$.call(this, new BVHRect(d), id, nodes, value);
  };
  protoOf(Node).toString = function () {
    return 'Node(d=' + BVHRect__toString_impl_z8yavd(this.d_1) + ', id=' + this.id_1 + ', nodes=' + toString_0(this.nodes_1) + ', value=' + toString_0(this.value_1) + ')';
  };
  protoOf(Node).hashCode = function () {
    var result = BVHRect__hashCode_impl_dhse2i(this.d_1);
    result = imul(result, 31) + (this.id_1 == null ? 0 : getStringHashCode(this.id_1)) | 0;
    result = imul(result, 31) + (this.nodes_1 == null ? 0 : this.nodes_1.hashCode()) | 0;
    result = imul(result, 31) + (this.value_1 == null ? 0 : hashCode(this.value_1)) | 0;
    return result;
  };
  protoOf(Node).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Node))
      return false;
    var tmp0_other_with_cast = other instanceof Node ? other : THROW_CCE();
    if (!equals(this.d_1, tmp0_other_with_cast.d_1))
      return false;
    if (!(this.id_1 == tmp0_other_with_cast.id_1))
      return false;
    if (!equals(this.nodes_1, tmp0_other_with_cast.nodes_1))
      return false;
    if (!equals(this.value_1, tmp0_other_with_cast.value_1))
      return false;
    return true;
  };
  function RemoveSubtreeRetObject(d, target, nodes) {
    target = target === VOID ? null : target;
    nodes = nodes === VOID ? null : nodes;
    this.d_1 = d;
    this.target_1 = target;
    this.nodes_1 = nodes;
  }
  protoOf(RemoveSubtreeRetObject).set_d_v6n58q_k$ = function (_set____db54di) {
    this.d_1 = _set____db54di;
  };
  protoOf(RemoveSubtreeRetObject).get_d_89sr20_k$ = function () {
    return this.d_1;
  };
  protoOf(RemoveSubtreeRetObject).set_target_wt6zyg_k$ = function (_set____db54di) {
    this.target_1 = _set____db54di;
  };
  protoOf(RemoveSubtreeRetObject).get_target_juba8q_k$ = function () {
    return this.target_1;
  };
  protoOf(RemoveSubtreeRetObject).set_nodes_bpxpk_k$ = function (_set____db54di) {
    this.nodes_1 = _set____db54di;
  };
  protoOf(RemoveSubtreeRetObject).get_nodes_ivvt6w_k$ = function () {
    return this.nodes_1;
  };
  function IntersectResult(ray, intersect, obj) {
    this.ray_1 = ray;
    this.intersect_1 = intersect;
    this.obj_1 = obj;
    var tmp = this;
    tmp.point$delegate_1 = lazy(BVH$IntersectResult$point$delegate$lambda(this));
    var tmp_0 = this;
    tmp_0.normal$delegate_1 = lazy(BVH$IntersectResult$normal$delegate$lambda(this));
  }
  protoOf(IntersectResult).get_ray_g60x9g_k$ = function () {
    return this.ray_1;
  };
  protoOf(IntersectResult).get_intersect_gu05ti_k$ = function () {
    return this.intersect_1;
  };
  protoOf(IntersectResult).get_obj_18izci_k$ = function () {
    return this.obj_1;
  };
  protoOf(IntersectResult).get_point_iwziav_k$ = function () {
    // Inline function 'kotlin.getValue' call
    var this_0 = this.point$delegate_1;
    point$factory();
    return this_0.get_value_j01efc_k$();
  };
  protoOf(IntersectResult).get_normal_h7qsj4_k$ = function () {
    // Inline function 'kotlin.getValue' call
    var this_0 = this.normal$delegate_1;
    normal$factory();
    return this_0.get_value_j01efc_k$();
  };
  protoOf(IntersectResult).component1_d5xa9b_k$ = function () {
    return this.ray_1;
  };
  protoOf(IntersectResult).component2_7eebsb_k$ = function () {
    return this.intersect_1;
  };
  protoOf(IntersectResult).component3_7eebsa_k$ = function () {
    return this.obj_1;
  };
  protoOf(IntersectResult).copy_vo68on_k$ = function (ray, intersect, obj) {
    return new IntersectResult(ray, intersect, obj);
  };
  protoOf(IntersectResult).copy$default_2ujmcb_k$ = function (ray, intersect, obj, $super) {
    ray = ray === VOID ? this.ray_1 : ray;
    intersect = intersect === VOID ? this.intersect_1 : intersect;
    obj = obj === VOID ? this.obj_1 : obj;
    return $super === VOID ? this.copy_vo68on_k$(ray, intersect, obj) : $super.copy_vo68on_k$.call(this, new BVHRay(ray), intersect, obj);
  };
  protoOf(IntersectResult).toString = function () {
    return 'IntersectResult(ray=' + BVHRay__toString_impl_otz8id(this.ray_1) + ', intersect=' + this.intersect_1 + ', obj=' + this.obj_1.toString() + ')';
  };
  protoOf(IntersectResult).hashCode = function () {
    var result = BVHRay__hashCode_impl_ofywnw(this.ray_1);
    result = imul(result, 31) + getNumberHashCode(this.intersect_1) | 0;
    result = imul(result, 31) + this.obj_1.hashCode() | 0;
    return result;
  };
  protoOf(IntersectResult).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof IntersectResult))
      return false;
    var tmp0_other_with_cast = other instanceof IntersectResult ? other : THROW_CCE();
    if (!equals(this.ray_1, tmp0_other_with_cast.ray_1))
      return false;
    if (!equals(this.intersect_1, tmp0_other_with_cast.intersect_1))
      return false;
    if (!this.obj_1.equals(tmp0_other_with_cast.obj_1))
      return false;
    return true;
  };
  function Comparators() {
    Companion_getInstance_20();
  }
  protoOf(Comparators).overlap_intervals_9lu6pw_k$ = function (a, b) {
    if (!(_BVHRect___get_length__impl__sgc79u(a) === _BVHRect___get_length__impl__sgc79u(b))) {
      // Inline function 'kotlin.error' call
      var message = 'Not matching dimensions';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    var inductionVariable = 0;
    var last = _BVHRect___get_length__impl__sgc79u(a);
    if (inductionVariable < last)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(BVHRect__min_impl_5ss93n(a, i) < BVHRect__max_impl_z6ab7l(b, i) ? BVHRect__max_impl_z6ab7l(a, i) > BVHRect__min_impl_5ss93n(b, i) : false))
          return false;
      }
       while (inductionVariable < last);
    return true;
  };
  protoOf(Comparators).contains_intervals_fksk7o_k$ = function (a, b) {
    if (!(_BVHRect___get_length__impl__sgc79u(a) === _BVHRect___get_length__impl__sgc79u(b))) {
      // Inline function 'kotlin.error' call
      var message = 'Not matching dimensions';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    var inductionVariable = 0;
    var last = _BVHRect___get_length__impl__sgc79u(a);
    if (inductionVariable < last)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(BVHRect__max_impl_z6ab7l(a, i) <= BVHRect__max_impl_z6ab7l(b, i) ? BVHRect__min_impl_5ss93n(a, i) >= BVHRect__min_impl_5ss93n(b, i) : false))
          return false;
      }
       while (inductionVariable < last);
    return true;
  };
  function _make_Empty($this) {
    return _BVHRect___init__impl__pawpsv(BVHIntervals_init_$Create$($this.dimensions_1));
  }
  function _make_Intervals($this, other, out) {
    return BVHRect__copyFrom_impl_kjg73e(out, other);
  }
  function _make_Intervals$default($this, other, out, $super) {
    out = out === VOID ? _BVHRect___init__impl__pawpsv_0($this.dimensions_1) : out;
    return _make_Intervals($this, other, out);
  }
  function _expand_intervals($this, a, b) {
    var inductionVariable = 0;
    var last = $this.dimensions_1;
    if (inductionVariable < last)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var a_a = BVHRect__min_impl_5ss93n(a, i);
        var b_a = BVHRect__min_impl_5ss93n(b, i);
        var a_b = BVHRect__size_impl_axghd8(a, i);
        var b_b = BVHRect__size_impl_axghd8(b, i);
        // Inline function 'kotlin.math.min' call
        var n = Math.min(a_a, b_a);
        // Inline function 'kotlin.math.max' call
        var a_0 = a_a + a_b;
        var b_0 = b_a + b_b;
        var tmp$ret$1 = Math.max(a_0, b_0);
        BVHRect__size_impl_axghd8_0(a, i, tmp$ret$1 - n);
        BVHRect__min_impl_5ss93n_0(a, i, n);
      }
       while (inductionVariable < last);
    return a;
  }
  function _make_MBV($this, nodes, intervals) {
    if (nodes.isEmpty_y1axqb_k$())
      return _make_Empty($this);
    var tmp;
    var tmp_0 = intervals;
    if ((tmp_0 == null ? null : new BVHRect(tmp_0)) == null) {
      tmp = null;
    } else {
      tmp = BVHRect__copyFrom_impl_kjg73e(intervals, nodes.get_c1px32_k$(0).d_1);
    }
    var tmp0_elvis_lhs = tmp;
    var tmp_1;
    var tmp_2 = tmp0_elvis_lhs;
    if ((tmp_2 == null ? null : new BVHRect(tmp_2)) == null) {
      tmp_1 = BVHRect__clone_impl_weisiw(nodes.get_c1px32_k$(0).d_1);
    } else {
      tmp_1 = tmp0_elvis_lhs;
    }
    var ints = tmp_1;
    var inductionVariable = nodes.get_size_woubt6_k$() - 1 | 0;
    if (1 <= inductionVariable)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        _expand_intervals($this, ints, nodes.get_c1px32_k$(i).d_1);
      }
       while (1 <= inductionVariable);
    return ints;
  }
  function _jons_ratio($this, intervals, count) {
    var dims = _BVHRect___get_length__impl__sgc79u(intervals);
    var sum = _BVHRect___get_intervals__impl__r54djc(intervals).bSum_1sd55_k$();
    var mul = _BVHRect___get_intervals__impl__r54djc(intervals).bMult_1jbhki_k$();
    sum = sum / dims;
    // Inline function 'kotlin.math.pow' call
    var this_0 = sum;
    var lgeo = mul / Math.pow(this_0, dims);
    return mul * count / lgeo;
  }
  function checkDimensions(_this__u8e3s4, $this) {
    return BVHRect__checkDimensions_impl_tlq5ao(_this__u8e3s4, $this.dimensions_1);
  }
  function checkDimensions_0(_this__u8e3s4, $this) {
    return BVHRay__checkDimensions_impl_mg9z0y(_this__u8e3s4, $this.dimensions_1);
  }
  function checkDimensions_1(_this__u8e3s4, $this) {
    return _this__u8e3s4.checkDimensions_c7bvg3_k$($this.dimensions_1);
  }
  function _remove_subtree($this, intervals, obj, root, comparators) {
    var hit_stack = fastArrayListOf([]);
    var count_stack = fastArrayListOf([]);
    var ret_array = fastArrayListOf([]);
    var current_depth = 1;
    var tmp;
    var tmp_0 = intervals;
    if ((tmp_0 == null ? null : new BVHRect(tmp_0)) == null) {
      tmp = true;
    } else {
      tmp = !comparators.overlap_intervals_9lu6pw_k$(intervals, root.d_1);
    }
    if (tmp)
      return ret_array;
    var ret_obj = new RemoveSubtreeRetObject(BVHRect__clone_impl_weisiw(intervals), obj);
    count_stack.add_utx5q5_k$(ensureNotNull(root.nodes_1).get_size_woubt6_k$());
    hit_stack.add_utx5q5_k$(root);
    $l$1: do {
      $l$0: do {
        var tree = removeLast(hit_stack);
        var i = removeLast(count_stack) - 1 | 0;
        if (!(ret_obj.target_1 == null)) {
          $l$loop: while (i >= 0) {
            var ltree = ensureNotNull(tree.nodes_1).get_c1px32_k$(i);
            if (comparators.overlap_intervals_9lu6pw_k$(ret_obj.d_1, ltree.d_1)) {
              if (((!(ret_obj.target_1 == null) ? !(ltree.value_1 == null) : false) ? ltree.value_1 === ret_obj.target_1 : false) ? true : ret_obj.target_1 == null ? !(ltree.value_1 == null) ? true : comparators.contains_intervals_fksk7o_k$(ltree.d_1, ret_obj.d_1) : false) {
                if (!(ltree.nodes_1 == null)) {
                  var tmp39_intervals = ltree.d_1;
                  ret_array = _search_subtree$default($this, tmp39_intervals, comparators, VOID, ltree);
                  var tmp40_safe_receiver = tree.nodes_1;
                  if (tmp40_safe_receiver == null)
                    null;
                  else
                    tmp40_safe_receiver.removeAt_6niowx_k$(i);
                } else {
                  ret_array = fastArrayListOf([ensureNotNull(tree.nodes_1).removeAt_6niowx_k$(i)]);
                }
                _make_MBV($this, ensureNotNull(tree.nodes_1), tree.d_1);
                ret_obj.target_1 = null;
                if (ensureNotNull(tree.nodes_1).get_size_woubt6_k$() < $this.minWidth_1) {
                  var tmp_1 = ret_obj;
                  var tmp41_intervals = tree.d_1;
                  var tmp42_root = tree;
                  tmp_1.nodes_1 = _search_subtree$default($this, tmp41_intervals, comparators, VOID, tmp42_root);
                }
                break $l$loop;
              } else if (!(ltree.nodes_1 == null)) {
                current_depth = current_depth + 1 | 0;
                count_stack.add_utx5q5_k$(i);
                hit_stack.add_utx5q5_k$(tree);
                tree = ltree;
                i = ensureNotNull(ltree.nodes_1).get_size_woubt6_k$();
              }
            }
            i = i - 1 | 0;
          }
        } else if (!(ret_obj.nodes_1 == null)) {
          if (ensureNotNull(tree.nodes_1).get_size_woubt6_k$() >= (i + 1 | 0)) {
            ensureNotNull(tree.nodes_1).removeAt_6niowx_k$(i + 1 | 0);
          }
          // Inline function 'kotlin.collections.isNotEmpty' call
          if (!ensureNotNull(tree.nodes_1).isEmpty_y1axqb_k$()) {
            _make_MBV($this, ensureNotNull(tree.nodes_1), tree.d_1);
          }
          var inductionVariable = 0;
          var last = ensureNotNull(ret_obj.nodes_1).get_size_woubt6_k$();
          if (inductionVariable < last)
            do {
              var t = inductionVariable;
              inductionVariable = inductionVariable + 1 | 0;
              _insert_subtree($this, tree, ensureNotNull(ret_obj.nodes_1).get_c1px32_k$(t));
            }
             while (inductionVariable < last);
          ensureNotNull(ret_obj.nodes_1).clear_j9egeb_k$();
          if (hit_stack.isEmpty_y1axqb_k$() ? ensureNotNull(tree.nodes_1).get_size_woubt6_k$() <= 1 : false) {
            var tmp_2 = ret_obj;
            var tmp43_intervals = tree.d_1;
            var tmp44_return_array = ensureNotNull(ret_obj.nodes_1);
            var tmp45_root = tree;
            tmp_2.nodes_1 = _search_subtree($this, tmp43_intervals, comparators, tmp44_return_array, tmp45_root);
            ensureNotNull(tree.nodes_1).clear_j9egeb_k$();
            hit_stack.add_utx5q5_k$(tree);
            count_stack.add_utx5q5_k$(1);
          } else {
            var tmp_3;
            // Inline function 'kotlin.collections.isNotEmpty' call
            if (!hit_stack.isEmpty_y1axqb_k$()) {
              tmp_3 = ensureNotNull(tree.nodes_1).get_size_woubt6_k$() < $this.minWidth_1;
            } else {
              tmp_3 = false;
            }
            if (tmp_3) {
              var tmp_4 = ret_obj;
              var tmp46_intervals = tree.d_1;
              var tmp47_return_array = ensureNotNull(ret_obj.nodes_1);
              var tmp48_root = tree;
              tmp_4.nodes_1 = _search_subtree($this, tmp46_intervals, comparators, tmp47_return_array, tmp48_root);
              ensureNotNull(tree.nodes_1).clear_j9egeb_k$();
            } else {
              ret_obj.nodes_1 = null;
            }
          }
        } else {
          new BVHRect(_make_MBV($this, ensureNotNull(tree.nodes_1), tree.d_1));
        }
        current_depth = current_depth - 1 | 0;
      }
       while (false);
      // Inline function 'kotlin.collections.isNotEmpty' call
      var tmp$ret$2 = !hit_stack.isEmpty_y1axqb_k$();
    }
     while (tmp$ret$2);
    return ret_array;
  }
  function _remove_subtree$default($this, intervals, obj, root, comparators, $super) {
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    return _remove_subtree($this, intervals, obj, root, comparators);
  }
  function _choose_leaf_subtree($this, intervals, root) {
    var best_choice_index = -1;
    var best_choice_stack = fastArrayListOf([]);
    var best_choice_area = 0.0;
    best_choice_stack.add_utx5q5_k$(root);
    var nodes = ensureNotNull(root.nodes_1);
    do {
      if (!(best_choice_index === -1)) {
        best_choice_stack.add_utx5q5_k$(nodes.get_c1px32_k$(best_choice_index));
        nodes = ensureNotNull(nodes.get_c1px32_k$(best_choice_index).nodes_1);
        best_choice_index = -1;
      }
      var inductionVariable = nodes.get_size_woubt6_k$() - 1 | 0;
      if (0 <= inductionVariable)
        $l$loop: do {
          var i = inductionVariable;
          inductionVariable = inductionVariable + -1 | 0;
          var ltree = nodes.get_c1px32_k$(i);
          if (!(ltree.value_1 == null)) {
            best_choice_index = -1;
            break $l$loop;
          }
          var old_lratio = _jons_ratio($this, ltree.d_1, ensureNotNull(ltree.nodes_1).get_size_woubt6_k$() + 1 | 0);
          var copy_of_intervals = BVHRect__clone_impl_weisiw(ltree.d_1);
          _expand_intervals($this, copy_of_intervals, intervals);
          var lratio = _jons_ratio($this, copy_of_intervals, ensureNotNull(ltree.nodes_1).get_size_woubt6_k$() + 2 | 0);
          var tmp;
          if (best_choice_index < 0) {
            tmp = true;
          } else {
            // Inline function 'kotlin.math.abs' call
            var x = lratio - old_lratio;
            tmp = Math.abs(x) < best_choice_area;
          }
          if (tmp) {
            // Inline function 'kotlin.math.abs' call
            var x_0 = lratio - old_lratio;
            best_choice_area = Math.abs(x_0);
            best_choice_index = i;
          }
        }
         while (0 <= inductionVariable);
    }
     while (!(best_choice_index === -1));
    return best_choice_stack;
  }
  function _linear_split($this, nodes) {
    var n = _pick_linear($this, nodes);
    $l$loop: while (true) {
      // Inline function 'kotlin.collections.isNotEmpty' call
      if (!!nodes.isEmpty_y1axqb_k$()) {
        break $l$loop;
      }
      _pick_next($this, nodes, n.get_c1px32_k$(0), n.get_c1px32_k$(1));
    }
    return n;
  }
  function _pick_next($this, nodes, a, b) {
    var area_a = _jons_ratio($this, a.d_1, ensureNotNull(a.nodes_1).get_size_woubt6_k$() + 1 | 0);
    var area_b = _jons_ratio($this, b.d_1, ensureNotNull(b.nodes_1).get_size_woubt6_k$() + 1 | 0);
    var high_area_delta = null;
    var high_area_node = null;
    var lowest_growth_group = null;
    var inductionVariable = nodes.get_size_woubt6_k$() - 1 | 0;
    if (0 <= inductionVariable)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        var l = nodes.get_c1px32_k$(i);
        var copy_of_intervals = _make_Intervals$default($this, a.d_1);
        _expand_intervals($this, copy_of_intervals, l.d_1);
        // Inline function 'kotlin.math.abs' call
        var x = _jons_ratio($this, copy_of_intervals, ensureNotNull(a.nodes_1).get_size_woubt6_k$() + 2 | 0) - area_a;
        var change_new_area_a = Math.abs(x);
        copy_of_intervals = _make_Intervals$default($this, b.d_1);
        _expand_intervals($this, copy_of_intervals, l.d_1);
        // Inline function 'kotlin.math.abs' call
        var x_0 = _jons_ratio($this, copy_of_intervals, ensureNotNull(b.nodes_1).get_size_woubt6_k$() + 2 | 0) - area_b;
        var change_new_area_b = Math.abs(x_0);
        var tmp;
        if (high_area_node == null ? true : high_area_delta == null) {
          tmp = true;
        } else {
          // Inline function 'kotlin.math.abs' call
          var x_1 = change_new_area_b - change_new_area_a;
          tmp = Math.abs(x_1) < high_area_delta;
        }
        if (tmp) {
          high_area_node = i;
          // Inline function 'kotlin.math.abs' call
          var x_2 = change_new_area_b - change_new_area_a;
          high_area_delta = Math.abs(x_2);
          lowest_growth_group = change_new_area_b < change_new_area_a ? b : a;
        }
      }
       while (0 <= inductionVariable);
    var temp_node = nodes.removeAt_6niowx_k$(ensureNotNull(high_area_node));
    if (((ensureNotNull(a.nodes_1).get_size_woubt6_k$() + nodes.get_size_woubt6_k$() | 0) + 1 | 0) <= $this.minWidth_1) {
      ensureNotNull(a.nodes_1).add_utx5q5_k$(temp_node);
      _expand_intervals($this, a.d_1, temp_node.d_1);
    } else if (((ensureNotNull(b.nodes_1).get_size_woubt6_k$() + nodes.get_size_woubt6_k$() | 0) + 1 | 0) <= $this.minWidth_1) {
      ensureNotNull(b.nodes_1).add_utx5q5_k$(temp_node);
      _expand_intervals($this, b.d_1, temp_node.d_1);
    } else {
      var tmp_0;
      if (lowest_growth_group == null) {
        throwUninitializedPropertyAccessException('lowest_growth_group');
      } else {
        tmp_0 = lowest_growth_group;
      }
      ensureNotNull(tmp_0.nodes_1).add_utx5q5_k$(temp_node);
      var tmp_1;
      if (lowest_growth_group == null) {
        throwUninitializedPropertyAccessException('lowest_growth_group');
      } else {
        tmp_1 = lowest_growth_group;
      }
      _expand_intervals($this, tmp_1.d_1, temp_node.d_1);
    }
  }
  function _pick_linear($this, nodes) {
    var tmp = 0;
    var tmp_0 = $this.dimensions_1;
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_1 = fillArrayVal(Array(tmp_0), null);
    while (tmp < tmp_0) {
      tmp_1[tmp] = nodes.get_size_woubt6_k$() - 1 | 0;
      tmp = tmp + 1 | 0;
    }
    var lowest_high = tmp_1;
    var tmp_2 = 0;
    var tmp_3 = $this.dimensions_1;
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_4 = fillArrayVal(Array(tmp_3), null);
    while (tmp_2 < tmp_3) {
      tmp_4[tmp_2] = 0;
      tmp_2 = tmp_2 + 1 | 0;
    }
    var highest_low = tmp_4;
    var inductionVariable = nodes.get_size_woubt6_k$() - 2 | 0;
    if (0 <= inductionVariable)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        var l = nodes.get_c1px32_k$(i);
        var inductionVariable_0 = 0;
        var last = $this.dimensions_1;
        if (inductionVariable_0 < last)
          do {
            var d = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            if (BVHRect__min_impl_5ss93n(l.d_1, d) > BVHRect__min_impl_5ss93n(nodes.get_c1px32_k$(highest_low[d]).d_1, d)) {
              highest_low[d] = i;
            } else if (BVHRect__min_impl_5ss93n(l.d_1, d) + BVHRect__size_impl_axghd8(l.d_1, d) < BVHRect__min_impl_5ss93n(nodes.get_c1px32_k$(lowest_high[d]).d_1, d) + BVHRect__size_impl_axghd8(nodes.get_c1px32_k$(lowest_high[d]).d_1, d)) {
              lowest_high[d] = i;
            }
          }
           while (inductionVariable_0 < last);
      }
       while (0 <= inductionVariable);
    var d_0 = 0;
    var last_difference = 0.0;
    var inductionVariable_1 = 0;
    var last_0 = $this.dimensions_1;
    if (inductionVariable_1 < last_0)
      do {
        var i_0 = inductionVariable_1;
        inductionVariable_1 = inductionVariable_1 + 1 | 0;
        // Inline function 'kotlin.math.abs' call
        var x = BVHRect__min_impl_5ss93n(nodes.get_c1px32_k$(lowest_high[i_0]).d_1, i_0) + BVHRect__size_impl_axghd8(nodes.get_c1px32_k$(lowest_high[i_0]).d_1, i_0) - BVHRect__min_impl_5ss93n(nodes.get_c1px32_k$(highest_low[i_0]).d_1, i_0);
        var difference = Math.abs(x);
        if (difference > last_difference) {
          d_0 = i_0;
          last_difference = difference;
        }
      }
       while (inductionVariable_1 < last_0);
    var t1;
    var t2;
    if (lowest_high[d_0] > highest_low[d_0]) {
      t1 = nodes.removeAt_6niowx_k$(lowest_high[d_0]);
      t2 = nodes.removeAt_6niowx_k$(highest_low[d_0]);
    } else {
      t2 = nodes.removeAt_6niowx_k$(highest_low[d_0]);
      t1 = nodes.removeAt_6niowx_k$(lowest_high[d_0]);
    }
    return fastArrayListOf([new Node(_make_Intervals$default($this, t1.d_1), VOID, fastArrayListOf([t1])), new Node(_make_Intervals$default($this, t2.d_1), VOID, fastArrayListOf([t2]))]);
  }
  function _insert_subtree($this, root, node) {
    var bc = null;
    if (ensureNotNull(root.nodes_1).isEmpty_y1axqb_k$()) {
      _make_Intervals($this, node.d_1, root.d_1);
      ensureNotNull(root.nodes_1).add_utx5q5_k$(node);
      return Unit_getInstance();
    }
    var tree_stack = _choose_leaf_subtree($this, node.d_1, root);
    var ret_obj_array = null;
    var ret_obj = node;
    $l$1: do {
      $l$0: do {
        var tmp;
        var tmp49_safe_receiver = bc;
        if (!((tmp49_safe_receiver == null ? null : tmp49_safe_receiver.nodes_1) == null)) {
          tmp = ensureNotNull(bc.nodes_1).isEmpty_y1axqb_k$();
        } else {
          tmp = false;
        }
        if (tmp) {
          var pbc = bc;
          bc = removeLast(tree_stack);
          var inductionVariable = 0;
          var last = ensureNotNull(bc.nodes_1).get_size_woubt6_k$();
          if (inductionVariable < last)
            $l$loop: do {
              var t = inductionVariable;
              inductionVariable = inductionVariable + 1 | 0;
              if (ensureNotNull(bc.nodes_1).get_c1px32_k$(t) === pbc ? true : ensureNotNull(ensureNotNull(bc.nodes_1).get_c1px32_k$(t).nodes_1).isEmpty_y1axqb_k$()) {
                ensureNotNull(bc.nodes_1).removeAt_6niowx_k$(t);
                break $l$loop;
              }
            }
             while (inductionVariable < last);
        } else {
          bc = removeLast(tree_stack);
        }
        if ((!(ret_obj_array == null) ? true : !(ensureNotNull(ret_obj).value_1 == null)) ? true : !(ret_obj.nodes_1 == null)) {
          if (!(ret_obj_array == null)) {
            var inductionVariable_0 = 0;
            var last_0 = ret_obj_array.get_size_woubt6_k$();
            if (inductionVariable_0 < last_0)
              do {
                var ai = inductionVariable_0;
                inductionVariable_0 = inductionVariable_0 + 1 | 0;
                _expand_intervals($this, bc.d_1, ret_obj_array.get_c1px32_k$(ai).d_1);
              }
               while (inductionVariable_0 < last_0);
            ensureNotNull(bc.nodes_1).addAll_6hs4wt_k$(ret_obj_array);
          } else {
            _expand_intervals($this, bc.d_1, ensureNotNull(ret_obj).d_1);
            ensureNotNull(bc.nodes_1).add_utx5q5_k$(ret_obj);
          }
          if (ensureNotNull(bc.nodes_1).get_size_woubt6_k$() <= $this.maxWidth_1) {
            ret_obj_array = null;
            ret_obj = new Node(_make_Intervals$default($this, bc.d_1));
          } else {
            var a = _linear_split($this, ensureNotNull(bc.nodes_1));
            ret_obj_array = a;
            ret_obj = null;
            if (tree_stack.isEmpty_y1axqb_k$()) {
              ensureNotNull(bc.nodes_1).add_utx5q5_k$(a.get_c1px32_k$(0));
              tree_stack.add_utx5q5_k$(bc);
              ret_obj_array = null;
              ret_obj = a.get_c1px32_k$(1);
            }
          }
        } else {
          _expand_intervals($this, bc.d_1, ret_obj.d_1);
          ret_obj_array = null;
          ret_obj = new Node(_make_Intervals$default($this, bc.d_1));
        }
      }
       while (false);
      // Inline function 'kotlin.collections.isNotEmpty' call
      var tmp$ret$0 = !tree_stack.isEmpty_y1axqb_k$();
    }
     while (tmp$ret$0);
  }
  function _intersect_Intervals($this, ray, intervals) {
    var ints = intervals;
    var tmp = ints;
    if ((tmp == null ? null : new BVHRect(tmp)) == null) {
      ints = $this.root_1.d_1;
    }
    var tmp_0 = 0;
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_1 = fillArrayVal(Array(2), null);
    while (tmp_0 < 2) {
      tmp_1[tmp_0] = new Float64Array($this.dimensions_1);
      tmp_0 = tmp_0 + 1 | 0;
    }
    var parameters = tmp_1;
    var inv_direction = new Float64Array($this.dimensions_1);
    var sign = new Int32Array($this.dimensions_1);
    var inductionVariable = 0;
    var last = $this.dimensions_1;
    if (inductionVariable < last)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        parameters[0][i] = BVHRect__min_impl_5ss93n(ints, i);
        parameters[1][i] = BVHRect__min_impl_5ss93n(ints, i) + BVHRect__size_impl_axghd8(ints, i);
        var j = 1.0 / BVHRay__dir_impl_jibowm(ray, i);
        inv_direction[i] = j;
        sign[i] = j <= 0.0 ? 1 : 0;
      }
       while (inductionVariable < last);
    var omin = (parameters[sign[0]][0] - BVHRay__pos_impl_rqkdin(ray, 0)) * inv_direction[0];
    var omax = (parameters[1 - sign[0] | 0][0] - BVHRay__pos_impl_rqkdin(ray, 0)) * inv_direction[0];
    var inductionVariable_0 = 1;
    var last_0 = $this.dimensions_1;
    if (inductionVariable_0 < last_0)
      do {
        var i_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        var tmin = (parameters[sign[i_0]][i_0] - BVHRay__pos_impl_rqkdin(ray, i_0)) * inv_direction[i_0];
        var tmax = (parameters[1 - sign[i_0] | 0][i_0] - BVHRay__pos_impl_rqkdin(ray, i_0)) * inv_direction[i_0];
        if (omin > tmax ? true : tmin > omax) {
          return null;
        }
        if (tmin > omin) {
          omin = tmin;
        }
        if (tmax < omax) {
          omax = tmax;
        }
      }
       while (inductionVariable_0 < last_0);
    if (omin >= Infinity ? true : omax <= -Infinity) {
      return null;
    }
    if (omin < 0.0 ? omax < 0.0 : false)
      return null;
    if (omin < 0.0) {
      omin = 0.0;
    }
    var rs = _make_Empty($this);
    var inductionVariable_1 = 0;
    var last_1 = $this.dimensions_1;
    if (inductionVariable_1 < last_1)
      do {
        var i_1 = inductionVariable_1;
        inductionVariable_1 = inductionVariable_1 + 1 | 0;
        BVHRect__min_impl_5ss93n_0(rs, i_1, BVHRay__pos_impl_rqkdin(ray, i_1) + BVHRay__dir_impl_jibowm(ray, i_1) * omin);
        BVHRect__size_impl_axghd8_0(rs, i_1, BVHRay__pos_impl_rqkdin(ray, i_1) + BVHRay__dir_impl_jibowm(ray, i_1) * omax);
      }
       while (inductionVariable_1 < last_1);
    return rs;
  }
  function _intersect_subtree($this, ray, return_array, root) {
    var hit_stack = fastArrayListOf([]);
    var tmp = _intersect_Intervals($this, ray, root.d_1);
    if ((tmp == null ? null : new BVHRect(tmp)) == null)
      return return_array;
    hit_stack.add_utx5q5_k$(ensureNotNull(root.nodes_1));
    $l$1: do {
      $l$0: do {
        var nodes = removeLast(hit_stack);
        var inductionVariable = nodes.get_size_woubt6_k$() - 1 | 0;
        if (0 <= inductionVariable)
          do {
            var i = inductionVariable;
            inductionVariable = inductionVariable + -1 | 0;
            var ltree = nodes.get_c1px32_k$(i);
            var intersect_points = _intersect_Intervals($this, ray, ltree.d_1);
            var tmp_0 = intersect_points;
            if (!((tmp_0 == null ? null : new BVHRect(tmp_0)) == null)) {
              if (!(ltree.nodes_1 == null)) {
                hit_stack.add_utx5q5_k$(ensureNotNull(ltree.nodes_1));
              } else if (!(ltree.value_1 == null)) {
                var tmp$ret$0;
                $l$block: {
                  // Inline function 'kotlin.collections.maxBy' call
                  var iterator = until(0, _BVHRay___get_dimensions__impl__ba0c7z(ray)).iterator_jk1svi_k$();
                  if (!iterator.hasNext_bitz1p_k$())
                    throw NoSuchElementException_init_$Create$();
                  var maxElem = iterator.next_20eer_k$();
                  if (!iterator.hasNext_bitz1p_k$()) {
                    tmp$ret$0 = maxElem;
                    break $l$block;
                  }
                  // Inline function 'korlibs.datastructure.ds.BVH._intersect_subtree.<anonymous>' call
                  var it = maxElem;
                  // Inline function 'kotlin.math.absoluteValue' call
                  var this_0 = BVHRay__dir_impl_jibowm(ray, it);
                  var maxValue = Math.abs(this_0);
                  do {
                    var e = iterator.next_20eer_k$();
                    // Inline function 'korlibs.datastructure.ds.BVH._intersect_subtree.<anonymous>' call
                    // Inline function 'kotlin.math.absoluteValue' call
                    var this_1 = BVHRay__dir_impl_jibowm(ray, e);
                    var v = Math.abs(this_1);
                    if (compareTo(maxValue, v) < 0) {
                      maxElem = e;
                      maxValue = v;
                    }
                  }
                   while (iterator.hasNext_bitz1p_k$());
                  tmp$ret$0 = maxElem;
                }
                var dim = tmp$ret$0;
                var raySize = BVHRay__dir_impl_jibowm(ray, dim);
                var imin = BVHRect__min_impl_5ss93n(intersect_points, dim);
                var rmin = BVHRay__pos_impl_rqkdin(ray, dim);
                var tminNum = imin - rmin;
                var tmin = tminNum / raySize;
                return_array.add_utx5q5_k$(new IntersectResult(ray, tmin, ltree));
              }
            }
          }
           while (0 <= inductionVariable);
      }
       while (false);
      // Inline function 'kotlin.collections.isNotEmpty' call
      var tmp$ret$5 = !hit_stack.isEmpty_y1axqb_k$();
    }
     while (tmp$ret$5);
    return return_array;
  }
  function _intersect_subtree$default($this, ray, return_array, root, $super) {
    return_array = return_array === VOID ? fastArrayListOf([]) : return_array;
    root = root === VOID ? $this.root_1 : root;
    return _intersect_subtree($this, ray, return_array, root);
  }
  function _search_subtree($this, intervals, comparators, return_array, root) {
    checkDimensions(intervals, $this);
    var hit_stack = fastArrayListOf([]);
    if (!comparators.overlap_intervals_9lu6pw_k$(intervals, root.d_1))
      return return_array;
    hit_stack.add_utx5q5_k$(ensureNotNull(root.nodes_1));
    $l$1: do {
      $l$0: do {
        var nodes = removeLast(hit_stack);
        var inductionVariable = nodes.get_size_woubt6_k$() - 1 | 0;
        if (0 <= inductionVariable)
          do {
            var i = inductionVariable;
            inductionVariable = inductionVariable + -1 | 0;
            var ltree = nodes.get_c1px32_k$(i);
            if (comparators.overlap_intervals_9lu6pw_k$(intervals, ltree.d_1)) {
              if (!(ltree.nodes_1 == null)) {
                hit_stack.add_utx5q5_k$(ensureNotNull(ltree.nodes_1));
              } else if (!(ltree.value_1 == null)) {
                return_array.add_utx5q5_k$(ltree);
              }
            }
          }
           while (0 <= inductionVariable);
      }
       while (false);
      // Inline function 'kotlin.collections.isNotEmpty' call
      var tmp$ret$0 = !hit_stack.isEmpty_y1axqb_k$();
    }
     while (tmp$ret$0);
    return return_array;
  }
  function _search_subtree$default($this, intervals, comparators, return_array, root, $super) {
    return_array = return_array === VOID ? fastArrayListOf([]) : return_array;
    root = root === VOID ? $this.root_1 : root;
    return _search_subtree($this, intervals, comparators, return_array, root);
  }
  function _get_objectToIntervalMap__otg09g($this) {
    return $this.objectToIntervalMap_1;
  }
  function BVH$iterator$slambda(this$0, resultContinuation) {
    this.this$0__1 = this$0;
    CoroutineImpl.call(this, resultContinuation);
  }
  protoOf(BVH$iterator$slambda).invoke_8m86yq_k$ = function ($this$iterator, $completion) {
    var tmp = this.create_nw6i46_k$($this$iterator, $completion);
    tmp.set_result_xj64lm_k$(Unit_getInstance());
    tmp.set_exception_px07aa_k$(null);
    return tmp.doResume_5yljmg_k$();
  };
  protoOf(BVH$iterator$slambda).invoke_qns8j1_k$ = function (p1, $completion) {
    return this.invoke_8m86yq_k$(p1 instanceof SequenceScope ? p1 : THROW_CCE(), $completion);
  };
  protoOf(BVH$iterator$slambda).doResume_5yljmg_k$ = function () {
    var suspendResult = this.get_result_iyg5d2_k$();
    $sm: do
      try {
        var tmp = this.get_state_iypx7s_k$();
        switch (tmp) {
          case 0:
            this.set_exceptionState_fex74n_k$(4);
            this.deque0__1 = TGenDeque_init_$Create$();
            this.deque0__1.addLast_s9bxg0_k$(this.this$0__1.root_1);
            this.set_state_rjd8d0_k$(1);
            continue $sm;
          case 1:
            if (!!this.deque0__1.isEmpty_y1axqb_k$()) {
              this.set_state_rjd8d0_k$(3);
              continue $sm;
            }

            this.node1__1 = this.deque0__1.removeFirst_58pi0k_k$();
            this.set_state_rjd8d0_k$(2);
            suspendResult = this.$this$iterator_1.yield_3xhcex_k$(this.node1__1, this);
            if (suspendResult === get_COROUTINE_SUSPENDED()) {
              return suspendResult;
            }

            continue $sm;
          case 2:
            var tmp51_safe_receiver = this.node1__1.nodes_1;
            if (tmp51_safe_receiver == null)
              null;
            else {
              this.deque0__1.addAll_66t59i_k$(tmp51_safe_receiver);
            }

            this.set_state_rjd8d0_k$(1);
            continue $sm;
          case 3:
            return Unit_getInstance();
          case 4:
            throw this.get_exception_x0n6w6_k$();
        }
      } catch ($p) {
        var e = $p;
        if (this.get_exceptionState_wflpxn_k$() === 4) {
          throw e;
        } else {
          this.set_state_rjd8d0_k$(this.get_exceptionState_wflpxn_k$());
          this.set_exception_px07aa_k$(e);
        }
      }
     while (true);
  };
  protoOf(BVH$iterator$slambda).create_nw6i46_k$ = function ($this$iterator, completion) {
    var i = new BVH$iterator$slambda(this.this$0__1, completion);
    i.$this$iterator_1 = $this$iterator;
    return i;
  };
  protoOf(BVH$iterator$slambda).create_wyq9v6_k$ = function (value, completion) {
    return this.create_nw6i46_k$(value instanceof SequenceScope ? value : THROW_CCE(), completion);
  };
  function BVH$iterator$slambda_0(this$0, resultContinuation) {
    var i = new BVH$iterator$slambda(this$0, resultContinuation);
    var l = function ($this$iterator, $completion) {
      return i.invoke_8m86yq_k$($this$iterator, $completion);
    };
    l.$arity = 1;
    return l;
  }
  function BVH(dimensions, width, allowUpdateObjects) {
    width = width === VOID ? imul(dimensions, 3) : width;
    allowUpdateObjects = allowUpdateObjects === VOID ? true : allowUpdateObjects;
    this.dimensions_1 = dimensions;
    this.allowUpdateObjects_1 = allowUpdateObjects;
    this.maxWidth_1 = width;
    var tmp = this;
    // Inline function 'kotlin.math.floor' call
    var x = this.maxWidth_1 / this.dimensions_1;
    var tmp$ret$0 = Math.floor(x);
    tmp.minWidth_1 = numberToInt(tmp$ret$0);
    this.root_1 = new Node(_make_Empty(this), 'root', fastArrayListOf([]));
    this.objectToIntervalMap_1 = HashMap_init_$Create$();
  }
  protoOf(BVH).get_dimensions_dbkfqi_k$ = function () {
    return this.dimensions_1;
  };
  protoOf(BVH).get_allowUpdateObjects_w68yiz_k$ = function () {
    return this.allowUpdateObjects_1;
  };
  protoOf(BVH).set_root_83gcgg_k$ = function (_set____db54di) {
    this.root_1 = _set____db54di;
  };
  protoOf(BVH).get_root_wott0r_k$ = function () {
    return this.root_1;
  };
  protoOf(BVH).isEmpty_y1axqb_k$ = function () {
    var tmp0_elvis_lhs = this.root_1.nodes_1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return true;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var nodes = tmp;
    return nodes.isEmpty_y1axqb_k$();
  };
  protoOf(BVH).envelope_pwo937_k$ = function () {
    return _make_Intervals$default(this, this.root_1.d_1);
  };
  protoOf(BVH).yieldTo_2rtdpg_k$ = function (intervals, yield_leaf, yield_node, root, comparators) {
    var hit_stack = fastArrayListOf([]);
    if (!comparators.overlap_intervals_9lu6pw_k$(intervals, root.d_1))
      return Unit_getInstance();
    hit_stack.add_utx5q5_k$(ensureNotNull(root.nodes_1));
    $l$1: do {
      $l$0: do {
        var nodes = removeLast(hit_stack);
        var inductionVariable = nodes.get_size_woubt6_k$() - 1 | 0;
        if (0 <= inductionVariable)
          do {
            var i = inductionVariable;
            inductionVariable = inductionVariable + -1 | 0;
            var ltree = nodes.get_c1px32_k$(i);
            if (comparators.overlap_intervals_9lu6pw_k$(intervals, ltree.d_1)) {
              if (!(ltree.nodes_1 == null)) {
                yield_node(ltree);
                hit_stack.add_utx5q5_k$(ensureNotNull(ltree.nodes_1));
              } else if (!(ltree.value_1 == null)) {
                yield_leaf(ltree);
              }
            }
          }
           while (0 <= inductionVariable);
      }
       while (false);
      // Inline function 'kotlin.collections.isNotEmpty' call
      var tmp$ret$0 = !hit_stack.isEmpty_y1axqb_k$();
    }
     while (tmp$ret$0);
  };
  protoOf(BVH).yieldTo$default_temdwh_k$ = function (intervals, yield_leaf, yield_node, root, comparators, $super) {
    root = root === VOID ? this.root_1 : root;
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    var tmp;
    if ($super === VOID) {
      this.yieldTo_2rtdpg_k$(intervals, yield_leaf, yield_node, root, comparators);
      tmp = Unit_getInstance();
    } else {
      tmp = $super.yieldTo_2rtdpg_k$.call(this, new BVHRect(intervals), yield_leaf, yield_node, root, comparators);
    }
    return tmp;
  };
  protoOf(BVH).intersectRay_uo32bu_k$ = function (ray, intervals) {
    return _intersect_Intervals(this, _BVHRay___init__impl__rrqut(ray), intervals);
  };
  protoOf(BVH).intersectRay_1s33ja_k$ = function (ray, intervals) {
    return _intersect_Intervals(this, ray, intervals);
  };
  protoOf(BVH).intersect_aulukl_k$ = function (ray, return_array) {
    return _intersect_subtree(this, _BVHRay___init__impl__rrqut(ray), return_array, this.root_1);
  };
  protoOf(BVH).intersect$default_25dmtt_k$ = function (ray, return_array, $super) {
    return_array = return_array === VOID ? fastArrayListOf([]) : return_array;
    return $super === VOID ? this.intersect_aulukl_k$(ray, return_array) : $super.intersect_aulukl_k$.call(this, ray, return_array);
  };
  protoOf(BVH).intersect_tndai1_k$ = function (ray, return_array) {
    return _intersect_subtree(this, ray, return_array, this.root_1);
  };
  protoOf(BVH).intersect$default_nfcqd1_k$ = function (ray, return_array, $super) {
    return_array = return_array === VOID ? fastArrayListOf([]) : return_array;
    return $super === VOID ? this.intersect_tndai1_k$(ray, return_array) : $super.intersect_tndai1_k$.call(this, new BVHRay(ray), return_array);
  };
  protoOf(BVH).search_mgha0s_k$ = function (intervals, return_array, comparators) {
    return this.search_rp15ie_k$(_BVHRect___init__impl__pawpsv(intervals), return_array, comparators);
  };
  protoOf(BVH).search$default_3cps2p_k$ = function (intervals, return_array, comparators, $super) {
    return_array = return_array === VOID ? fastArrayListOf([]) : return_array;
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    return $super === VOID ? this.search_mgha0s_k$(intervals, return_array, comparators) : $super.search_mgha0s_k$.call(this, intervals, return_array, comparators);
  };
  protoOf(BVH).search_rp15ie_k$ = function (intervals, return_array, comparators) {
    var tmp50_root = this.root_1;
    return _search_subtree(this, intervals, comparators, return_array, tmp50_root);
  };
  protoOf(BVH).search$default_ty1spn_k$ = function (intervals, return_array, comparators, $super) {
    return_array = return_array === VOID ? fastArrayListOf([]) : return_array;
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    return $super === VOID ? this.search_rp15ie_k$(intervals, return_array, comparators) : $super.search_rp15ie_k$.call(this, new BVHRect(intervals), return_array, comparators);
  };
  protoOf(BVH).iterator_jk1svi_k$ = function () {
    return iterator(BVH$iterator$slambda_0(this, null));
  };
  protoOf(BVH).findAll_e3zahk_k$ = function () {
    return toList(this);
  };
  protoOf(BVH).findAllValues_1zr34m_k$ = function () {
    // Inline function 'kotlin.collections.mapNotNull' call
    // Inline function 'kotlin.collections.mapNotNullTo' call
    var this_0 = this.findAll_e3zahk_k$();
    var destination = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.collections.forEach' call
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'kotlin.collections.mapNotNullTo.<anonymous>' call
      // Inline function 'korlibs.datastructure.ds.BVH.findAllValues.<anonymous>' call
      var tmp0_safe_receiver = element.value_1;
      if (tmp0_safe_receiver == null)
        null;
      else {
        // Inline function 'kotlin.let' call
        // Inline function 'kotlin.contracts.contract' call
        destination.add_utx5q5_k$(tmp0_safe_receiver);
      }
    }
    return destination;
  };
  protoOf(BVH).searchValues_mu5t8h_k$ = function (intervals, comparators) {
    return this.searchValues_qw3rbv_k$(_BVHRect___init__impl__pawpsv(intervals), comparators);
  };
  protoOf(BVH).searchValues$default_6yvp2t_k$ = function (intervals, comparators, $super) {
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    return $super === VOID ? this.searchValues_mu5t8h_k$(intervals, comparators) : $super.searchValues_mu5t8h_k$.call(this, intervals, comparators);
  };
  protoOf(BVH).searchValues_qw3rbv_k$ = function (rect, comparators) {
    // Inline function 'kotlin.collections.mapNotNull' call
    var tmp52_root = this.root_1;
    // Inline function 'kotlin.collections.mapNotNullTo' call
    var this_0 = _search_subtree$default(this, rect, comparators, VOID, tmp52_root);
    var destination = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.collections.forEach' call
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'kotlin.collections.mapNotNullTo.<anonymous>' call
      // Inline function 'korlibs.datastructure.ds.BVH.searchValues.<anonymous>' call
      var tmp0_safe_receiver = element.value_1;
      if (tmp0_safe_receiver == null)
        null;
      else {
        // Inline function 'kotlin.let' call
        // Inline function 'kotlin.contracts.contract' call
        destination.add_utx5q5_k$(tmp0_safe_receiver);
      }
    }
    return destination;
  };
  protoOf(BVH).searchValues$default_361u9h_k$ = function (rect, comparators, $super) {
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    return $super === VOID ? this.searchValues_qw3rbv_k$(rect, comparators) : $super.searchValues_qw3rbv_k$.call(this, new BVHRect(rect), comparators);
  };
  protoOf(BVH).insertOrUpdate_tsybz1_k$ = function (intervals, obj) {
    return this.insertOrUpdate_jfsk7t_k$(_BVHRect___init__impl__pawpsv(intervals), obj);
  };
  protoOf(BVH).insertOrUpdate_jfsk7t_k$ = function (rect, obj) {
    checkDimensions(rect, this);
    if (this.allowUpdateObjects_1) {
      var tmp = this.objectToIntervalMap_1.get_wei43m_k$(obj);
      var oldIntervals = tmp == null ? null : tmp.intervals_1;
      var tmp_0 = oldIntervals;
      if (!((tmp_0 == null ? null : new BVHRect(tmp_0)) == null)) {
        var tmp_1 = oldIntervals;
        if (equals(new BVHRect(rect), tmp_1 == null ? null : new BVHRect(tmp_1))) {
          return Unit_getInstance();
        }
        // Inline function 'kotlin.collections.contains' call
        // Inline function 'kotlin.collections.containsKey' call
        var this_0 = this.objectToIntervalMap_1;
        if ((isInterface(this_0, Map_0) ? this_0 : THROW_CCE()).containsKey_aw81wo_k$(obj)) {
          this.remove_pxrizf_k$(obj);
        }
      }
    }
    _insert_subtree(this, this.root_1, new Node(rect, VOID, VOID, obj));
    if (this.allowUpdateObjects_1) {
      // Inline function 'kotlin.collections.set' call
      var this_1 = this.objectToIntervalMap_1;
      var value = new BVHRect(rect);
      this_1.put_4fpzoq_k$(obj, value);
    }
  };
  protoOf(BVH).remove_pxrizf_k$ = function (obj) {
    if (!this.allowUpdateObjects_1) {
      // Inline function 'kotlin.error' call
      var message = 'allowUpdateObjects not enabled';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    var tmp = this.objectToIntervalMap_1.get_wei43m_k$(obj);
    var intervals = tmp == null ? null : tmp.intervals_1;
    var tmp_0 = intervals;
    if (!((tmp_0 == null ? null : new BVHRect(tmp_0)) == null)) {
      this.remove$default_gnkbni_k$(intervals, obj);
    }
  };
  protoOf(BVH).getObjectBounds_h2rav9_k$ = function (obj) {
    var tmp = this.objectToIntervalMap_1.get_wei43m_k$(obj);
    var tmp53_safe_receiver = tmp == null ? null : tmp.intervals_1;
    var tmp_0;
    var tmp_1 = tmp53_safe_receiver;
    if ((tmp_1 == null ? null : new BVHRect(tmp_1)) == null) {
      tmp_0 = null;
    } else {
      tmp_0 = _BVHRect___get_intervals__impl__r54djc(tmp53_safe_receiver);
    }
    return tmp_0;
  };
  protoOf(BVH).getObjectBoundsRect_8mb29h_k$ = function (obj) {
    var tmp = this.objectToIntervalMap_1.get_wei43m_k$(obj);
    return tmp == null ? null : tmp.intervals_1;
  };
  protoOf(BVH).remove_k10e4v_k$ = function (intervals, obj, comparators) {
    return this.remove_3x7bhl_k$(_BVHRect___init__impl__pawpsv(intervals), obj, comparators);
  };
  protoOf(BVH).remove$default_p1wm2y_k$ = function (intervals, obj, comparators, $super) {
    obj = obj === VOID ? null : obj;
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    return $super === VOID ? this.remove_k10e4v_k$(intervals, obj, comparators) : $super.remove_k10e4v_k$.call(this, intervals, obj, comparators);
  };
  protoOf(BVH).remove_3x7bhl_k$ = function (rect, obj, comparators) {
    checkDimensions(rect, this);
    // Inline function 'kotlin.also' call
    var tmp;
    if (obj == null) {
      var ret_array = fastArrayListOf([]);
      do {
        var numberdeleted = ret_array.get_size_woubt6_k$();
        var tmp54_root = this.root_1;
        ret_array.addAll_6hs4wt_k$(_remove_subtree(this, rect, null, tmp54_root, comparators));
      }
       while (!(numberdeleted === ret_array.get_size_woubt6_k$()));
      tmp = ret_array;
    } else {
      var tmp55_root = this.root_1;
      tmp = _remove_subtree(this, rect, obj, tmp55_root, comparators);
    }
    var this_0 = tmp;
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ds.BVH.remove.<anonymous>' call
    if (this.allowUpdateObjects_1) {
      // Inline function 'korlibs.datastructure.FastArrayList.fastForEach' call
      // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var array = this_0.get___array_ggax8r_k$();
      var n = 0;
      while (n < array.length) {
        // Inline function 'korlibs.datastructure.ds.BVH.remove.<anonymous>.<anonymous>' call
        // Inline function 'kotlin.js.unsafeCast' call
        // Inline function 'get' call
        var _unary__edvuaz = n;
        n = _unary__edvuaz + 1 | 0;
        // Inline function 'kotlin.js.asDynamic' call
        // Inline function 'kotlin.js.asDynamic' call
        var node = array[_unary__edvuaz];
        // Inline function 'kotlin.collections.remove' call
        var this_1 = this.objectToIntervalMap_1;
        var key = node.value_1;
        var tmp_0 = (isInterface(this_1, MutableMap) ? this_1 : THROW_CCE()).remove_gppy8k_k$(key);
        tmp_0 == null || tmp_0.intervals_1;
      }
    }
    return this_0;
  };
  protoOf(BVH).remove$default_gnkbni_k$ = function (rect, obj, comparators, $super) {
    obj = obj === VOID ? null : obj;
    comparators = comparators === VOID ? Companion_getInstance_20() : comparators;
    return $super === VOID ? this.remove_3x7bhl_k$(rect, obj, comparators) : $super.remove_3x7bhl_k$.call(this, new BVHRect(rect), obj, comparators);
  };
  protoOf(BVH).debug_9po9ee_k$ = function (node, indentation) {
    println(indentation + BVHRect__toString_impl_z8yavd(node.d_1) + ':' + toString_0(node.value_1));
    if (!(node.nodes_1 == null)) {
      var newIndentation = indentation + '  ';
      var _iterator__ex2g4s = ensureNotNull(node.nodes_1).iterator_jk1svi_k$();
      while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
        var n = _iterator__ex2g4s.next_20eer_k$();
        this.debug_9po9ee_k$(n, newIndentation);
      }
    }
  };
  protoOf(BVH).debug$default_ce3tmt_k$ = function (node, indentation, $super) {
    node = node === VOID ? this.root_1 : node;
    indentation = indentation === VOID ? '' : indentation;
    var tmp;
    if ($super === VOID) {
      this.debug_9po9ee_k$(node, indentation);
      tmp = Unit_getInstance();
    } else {
      tmp = $super.debug_9po9ee_k$.call(this, node, indentation);
    }
    return tmp;
  };
  function BVHIntervals_init_$Init$(dimensions, $this) {
    BVHIntervals.call($this, new Float64Array(imul(dimensions, 2)));
    return $this;
  }
  function BVHIntervals_init_$Create$(dimensions) {
    return BVHIntervals_init_$Init$(dimensions, objectCreate(protoOf(BVHIntervals)));
  }
  function _get_cachedHashCode__d7x0zy($this) {
    return $this.cachedHashCode_1;
  }
  function Companion_21() {
    Companion_instance_21 = this;
  }
  protoOf(Companion_21).invoke_bmfh5t_k$ = function (values) {
    return new BVHIntervals(values);
  };
  protoOf(Companion_21).invoke_42mpsw_k$ = function (values) {
    // Inline function 'korlibs.datastructure.mapDouble' call
    // Inline function 'kotlin.also' call
    var this_0 = new Float64Array(values.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapDouble.<anonymous>' call
    var inductionVariable = 0;
    var last = values.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.ds.Companion.invoke.<anonymous>' call
        this_0[n] = values[n];
      }
       while (inductionVariable < last);
    return new BVHIntervals(this_0);
  };
  protoOf(Companion_21).invoke_r4hpo3_k$ = function (values) {
    var tmp = 0;
    var tmp_0 = values.length;
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = values[tmp_2];
      tmp = tmp + 1 | 0;
    }
    return new BVHIntervals(tmp_1);
  };
  var Companion_instance_21;
  function Companion_getInstance_21() {
    if (Companion_instance_21 == null)
      new Companion_21();
    return Companion_instance_21;
  }
  function BVHIntervals(data) {
    Companion_getInstance_21();
    this.data_1 = data;
    this.cachedHashCode_1 = contentHashCode_2(this.data_1);
  }
  protoOf(BVHIntervals).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(BVHIntervals).hashCode = function () {
    return this.cachedHashCode_1;
  };
  protoOf(BVHIntervals).equals = function (other) {
    var tmp;
    if (other instanceof BVHIntervals) {
      tmp = contentEquals_2(this.data_1, other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(BVHIntervals).checkDimensions_c7bvg3_k$ = function (dimensions) {
    checkDimensions_2(this.get_dimensions_dbkfqi_k$(), dimensions);
  };
  protoOf(BVHIntervals).setTo_dkxp7u_k$ = function (values) {
    // Inline function 'kotlin.collections.copyInto' call
    var destination = this.data_1;
    var endIndex = values.length;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = values;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, destination, 0, 0, endIndex);
  };
  protoOf(BVHIntervals).setTo_z6fpeb_k$ = function (a0, b0, a1, b1) {
    this.data_1[0] = a0;
    this.data_1[1] = b0;
    this.data_1[2] = a1;
    this.data_1[3] = b1;
  };
  protoOf(BVHIntervals).setTo_1uslrh_k$ = function (a0, b0, a1, b1, a2, b2) {
    this.data_1[0] = a0;
    this.data_1[1] = b0;
    this.data_1[2] = a1;
    this.data_1[3] = b1;
    this.data_1[4] = a2;
    this.data_1[5] = b2;
  };
  protoOf(BVHIntervals).get_length_g42xv3_k$ = function () {
    return this.data_1.length / 2 | 0;
  };
  protoOf(BVHIntervals).get_dimensions_dbkfqi_k$ = function () {
    return this.get_length_g42xv3_k$();
  };
  protoOf(BVHIntervals).a_f6w34n_k$ = function (index) {
    return this.min_lvcr3u_k$(index);
  };
  protoOf(BVHIntervals).b_fizzjq_k$ = function (index) {
    return this.size_vxaref_k$(index);
  };
  protoOf(BVHIntervals).a_bku2in_k$ = function (index, value) {
    this.data_1[imul(index, 2) + 0 | 0] = value;
  };
  protoOf(BVHIntervals).b_el18sg_k$ = function (index, value) {
    this.data_1[imul(index, 2) + 1 | 0] = value;
  };
  protoOf(BVHIntervals).min_lvcr3u_k$ = function (dim) {
    return this.data_1[imul(dim, 2) + 0 | 0];
  };
  protoOf(BVHIntervals).size_vxaref_k$ = function (dim) {
    return this.data_1[imul(dim, 2) + 1 | 0];
  };
  protoOf(BVHIntervals).max_uw10uk_k$ = function (dim) {
    return this.min_lvcr3u_k$(dim) + this.size_vxaref_k$(dim);
  };
  protoOf(BVHIntervals).aPlusB_4kzjpr_k$ = function (index) {
    return this.min_lvcr3u_k$(index) + this.size_vxaref_k$(index);
  };
  protoOf(BVHIntervals).bSum_1sd55_k$ = function () {
    var result = 0.0;
    var data = this.data_1;
    var inductionVariable = 0;
    var last = this.get_length_g42xv3_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        result = result + data[imul(n, 2) + 1 | 0];
      }
       while (inductionVariable < last);
    return result;
  };
  protoOf(BVHIntervals).bMult_1jbhki_k$ = function () {
    var result = 1.0;
    var data = this.data_1;
    var inductionVariable = 0;
    var last = this.get_length_g42xv3_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        result = result * data[imul(n, 2) + 1 | 0];
      }
       while (inductionVariable < last);
    return result;
  };
  protoOf(BVHIntervals).copyFrom_yg1mxu_k$ = function (other) {
    // Inline function 'kotlin.collections.copyInto' call
    var this_0 = other.data_1;
    var destination = this.data_1;
    var endIndex = this_0.length;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = this_0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, destination, 0, 0, endIndex);
    return this;
  };
  protoOf(BVHIntervals).clone_1keycd_k$ = function () {
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = this.data_1.slice();
    return new BVHIntervals(tmp$ret$1);
  };
  protoOf(BVHIntervals).toString = function () {
    // Inline function 'kotlin.text.buildString' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.apply' call
    var this_0 = StringBuilder_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ds.BVHIntervals.toString.<anonymous>' call
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(91));
    var inductionVariable = 0;
    var last = this.get_length_g42xv3_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(n === 0)) {
          this_0.append_22ad7x_k$(', ');
        }
        this_0.append_am5a4z_k$(Char__plus_impl_qi7pgj(_Char___init__impl__6a9atx(120), n));
        this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(61));
        this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(40));
        this_0.append_jynnak_k$(this.min_lvcr3u_k$(n));
        this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(44));
        this_0.append_jynnak_k$(this.size_vxaref_k$(n));
        this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(41));
      }
       while (inductionVariable < last);
    this_0.append_am5a4z_k$(_Char___init__impl__6a9atx(93));
    return this_0.toString();
  };
  protoOf(BVHIntervals).component1_7eebsc_k$ = function () {
    return this.data_1;
  };
  protoOf(BVHIntervals).copy_3htnfi_k$ = function (data) {
    return new BVHIntervals(data);
  };
  protoOf(BVHIntervals).copy$default_cwkvd6_k$ = function (data, $super) {
    data = data === VOID ? this.data_1 : data;
    return $super === VOID ? this.copy_3htnfi_k$(data) : $super.copy_3htnfi_k$.call(this, data);
  };
  function _BVHRect___init__impl__pawpsv(intervals) {
    return intervals;
  }
  function _BVHRect___get_intervals__impl__r54djc($this) {
    return $this;
  }
  function _BVHRect___init__impl__pawpsv_0(dimensions) {
    return _BVHRect___init__impl__pawpsv(BVHIntervals_init_$Create$(dimensions));
  }
  function BVHRect__checkDimensions_impl_tlq5ao($this, dimensions) {
    checkDimensions_2(_BVHRect___get_dimensions__impl__9rcjgr($this), dimensions);
  }
  function _BVHRect___get_data__impl__kn7sj6($this) {
    return _BVHRect___get_intervals__impl__r54djc($this).data_1;
  }
  function BVHRect__copyFrom_impl_kjg73e($this, other) {
    _BVHRect___get_intervals__impl__r54djc($this).copyFrom_yg1mxu_k$(_BVHRect___get_intervals__impl__r54djc(other));
    return $this;
  }
  function BVHRect__clone_impl_weisiw($this) {
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = _BVHRect___get_data__impl__kn7sj6($this).slice();
    return _BVHRect___init__impl__pawpsv(new BVHIntervals(tmp$ret$1));
  }
  function _BVHRect___get_length__impl__sgc79u($this) {
    return _BVHRect___get_dimensions__impl__9rcjgr($this);
  }
  function _BVHRect___get_dimensions__impl__9rcjgr($this) {
    return _BVHRect___get_data__impl__kn7sj6($this).length / 2 | 0;
  }
  function BVHRect__min_impl_5ss93n($this, dim) {
    return _BVHRect___get_data__impl__kn7sj6($this)[imul(dim, 2) + 0 | 0];
  }
  function BVHRect__size_impl_axghd8($this, dim) {
    return _BVHRect___get_data__impl__kn7sj6($this)[imul(dim, 2) + 1 | 0];
  }
  function BVHRect__max_impl_z6ab7l($this, dim) {
    return BVHRect__min_impl_5ss93n($this, dim) + BVHRect__size_impl_axghd8($this, dim);
  }
  function BVHRect__min_impl_5ss93n_0($this, dim, value) {
    _BVHRect___get_data__impl__kn7sj6($this)[imul(dim, 2) + 0 | 0] = value;
  }
  function BVHRect__size_impl_axghd8_0($this, dim, value) {
    _BVHRect___get_data__impl__kn7sj6($this)[imul(dim, 2) + 1 | 0] = value;
  }
  function _BVHRect___get_min__impl__nebhl8($this) {
    var tmp = 0;
    var tmp_0 = _BVHRect___get_dimensions__impl__9rcjgr($this);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = BVHRect__min_impl_5ss93n($this, tmp_2);
      tmp = tmp + 1 | 0;
    }
    return new BVHVector(tmp_1);
  }
  function _BVHRect___get_size__impl__brn4bb($this) {
    var tmp = 0;
    var tmp_0 = _BVHRect___get_dimensions__impl__9rcjgr($this);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = BVHRect__size_impl_axghd8($this, tmp_2);
      tmp = tmp + 1 | 0;
    }
    return new BVHVector(tmp_1);
  }
  function _BVHRect___get_max__impl__6r11ia($this) {
    var tmp = 0;
    var tmp_0 = _BVHRect___get_dimensions__impl__9rcjgr($this);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = BVHRect__max_impl_z6ab7l($this, tmp_2);
      tmp = tmp + 1 | 0;
    }
    return new BVHVector(tmp_1);
  }
  function BVHRect__toString_impl_z8yavd($this) {
    return 'BVHRect(min=' + _BVHRect___get_min__impl__nebhl8($this).toString() + ', max=' + _BVHRect___get_max__impl__6r11ia($this).toString() + ')';
  }
  function BVHRect__hashCode_impl_dhse2i($this) {
    return $this.hashCode();
  }
  function BVHRect__equals_impl_cirifq($this, other) {
    if (!(other instanceof BVHRect))
      return false;
    var tmp0_other_with_cast = other instanceof BVHRect ? other.intervals_1 : THROW_CCE();
    if (!$this.equals(tmp0_other_with_cast))
      return false;
    return true;
  }
  function BVHRect(intervals) {
    this.intervals_1 = intervals;
  }
  protoOf(BVHRect).toString = function () {
    return BVHRect__toString_impl_z8yavd(this.intervals_1);
  };
  protoOf(BVHRect).hashCode = function () {
    return BVHRect__hashCode_impl_dhse2i(this.intervals_1);
  };
  protoOf(BVHRect).equals = function (other) {
    return BVHRect__equals_impl_cirifq(this.intervals_1, other);
  };
  function _BVHRay___init__impl__rrqut(intervals) {
    return intervals;
  }
  function _BVHRay___get_intervals__impl__b4qgpu($this) {
    return $this;
  }
  function BVHRay__checkDimensions_impl_mg9z0y($this, dimensions) {
    checkDimensions_2(_BVHRay___get_dimensions__impl__ba0c7z($this), dimensions);
  }
  function _BVHRay___get_data__impl__uwp1cs($this) {
    return _BVHRay___get_intervals__impl__b4qgpu($this).data_1;
  }
  function BVHRay__copyFrom_impl_9l9oi0($this, other) {
    _BVHRay___get_intervals__impl__b4qgpu($this).copyFrom_yg1mxu_k$(_BVHRay___get_intervals__impl__b4qgpu(other));
    return $this;
  }
  function BVHRay__clone_impl_71rzwa($this) {
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$1 = _BVHRay___get_data__impl__uwp1cs($this).slice();
    return _BVHRay___init__impl__rrqut(new BVHIntervals(tmp$ret$1));
  }
  function _BVHRay___get_length__impl__wbgql4($this) {
    return _BVHRay___get_dimensions__impl__ba0c7z($this);
  }
  function _BVHRay___get_dimensions__impl__ba0c7z($this) {
    return _BVHRay___get_data__impl__uwp1cs($this).length / 2 | 0;
  }
  function BVHRay__pos_impl_rqkdin($this, dim) {
    return _BVHRay___get_data__impl__uwp1cs($this)[imul(dim, 2) + 0 | 0];
  }
  function BVHRay__dir_impl_jibowm($this, dim) {
    return _BVHRay___get_data__impl__uwp1cs($this)[imul(dim, 2) + 1 | 0];
  }
  function _BVHRay___get_pos__impl__icqom4($this) {
    var tmp = 0;
    var tmp_0 = _BVHRay___get_dimensions__impl__ba0c7z($this);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = BVHRay__pos_impl_rqkdin($this, tmp_2);
      tmp = tmp + 1 | 0;
    }
    return new BVHVector(tmp_1);
  }
  function _BVHRay___get_dir__impl__57k859($this) {
    var tmp = 0;
    var tmp_0 = _BVHRay___get_dimensions__impl__ba0c7z($this);
    var tmp_1 = new Float64Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      tmp_1[tmp_2] = BVHRay__dir_impl_jibowm($this, tmp_2);
      tmp = tmp + 1 | 0;
    }
    return new BVHVector(tmp_1);
  }
  function BVHRay__toString_impl_otz8id($this) {
    return 'BVHRay(pos=' + _BVHRay___get_pos__impl__icqom4($this).toString() + ', dir=' + _BVHRay___get_dir__impl__57k859($this).toString() + ')';
  }
  function BVHRay__hashCode_impl_ofywnw($this) {
    return $this.hashCode();
  }
  function BVHRay__equals_impl_2zm11k($this, other) {
    if (!(other instanceof BVHRay))
      return false;
    var tmp0_other_with_cast = other instanceof BVHRay ? other.intervals_1 : THROW_CCE();
    if (!$this.equals(tmp0_other_with_cast))
      return false;
    return true;
  }
  function BVHRay(intervals) {
    this.intervals_1 = intervals;
  }
  protoOf(BVHRay).toString = function () {
    return BVHRay__toString_impl_otz8id(this.intervals_1);
  };
  protoOf(BVHRay).hashCode = function () {
    return BVHRay__hashCode_impl_ofywnw(this.intervals_1);
  };
  protoOf(BVHRay).equals = function (other) {
    return BVHRay__equals_impl_2zm11k(this.intervals_1, other);
  };
  function Companion_22() {
    Companion_instance_22 = this;
  }
  protoOf(Companion_22).invoke_42mpsw_k$ = function (data) {
    // Inline function 'korlibs.datastructure.mapDouble' call
    // Inline function 'kotlin.also' call
    var this_0 = new Float64Array(data.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapDouble.<anonymous>' call
    var inductionVariable = 0;
    var last = data.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.ds.Companion.invoke.<anonymous>' call
        this_0[n] = data[n];
      }
       while (inductionVariable < last);
    return new BVHVector(this_0);
  };
  protoOf(Companion_22).invoke_bmfh5t_k$ = function (data) {
    return new BVHVector(data);
  };
  protoOf(Companion_22).invoke_r4hpo3_k$ = function (data) {
    // Inline function 'korlibs.datastructure.mapDouble' call
    // Inline function 'kotlin.also' call
    var this_0 = new Float64Array(data.length);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapDouble.<anonymous>' call
    var inductionVariable = 0;
    var last = data.length;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.ds.Companion.invoke.<anonymous>' call
        this_0[n] = data[n];
      }
       while (inductionVariable < last);
    return new BVHVector(this_0);
  };
  var Companion_instance_22;
  function Companion_getInstance_22() {
    if (Companion_instance_22 == null)
      new Companion_22();
    return Companion_instance_22;
  }
  function BVHVector$toString$lambda(it) {
    return get_niceStr(it);
  }
  function BVHVector(data) {
    Companion_getInstance_22();
    this.data_1 = data;
  }
  protoOf(BVHVector).get_data_wokkxf_k$ = function () {
    return this.data_1;
  };
  protoOf(BVHVector).get_dimensions_dbkfqi_k$ = function () {
    return this.data_1.length;
  };
  protoOf(BVHVector).checkDimensions_c7bvg3_k$ = function (dims) {
    if (!(dims === this.get_dimensions_dbkfqi_k$())) {
      // Inline function 'kotlin.error' call
      var message = 'Expected ' + dims + ' dimensions, but found ' + this.get_dimensions_dbkfqi_k$();
      throw IllegalStateException_init_$Create$(toString(message));
    }
  };
  protoOf(BVHVector).get_c1px32_k$ = function (dim) {
    return this.data_1[dim];
  };
  protoOf(BVHVector).set_mvap1c_k$ = function (dim, value) {
    this.data_1[dim] = value;
  };
  protoOf(BVHVector).equals = function (other) {
    var tmp;
    if (other instanceof BVHVector) {
      tmp = contentEquals_2(this.data_1, other.data_1);
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(BVHVector).toString = function () {
    return 'BVHVector(' + joinToString_0(this.data_1, ', ', VOID, VOID, VOID, VOID, BVHVector$toString$lambda) + ')';
  };
  protoOf(BVHVector).component1_7eebsc_k$ = function () {
    return this.data_1;
  };
  protoOf(BVHVector).copy_3htnfi_k$ = function (data) {
    return new BVHVector(data);
  };
  protoOf(BVHVector).copy$default_d1c641_k$ = function (data, $super) {
    data = data === VOID ? this.data_1 : data;
    return $super === VOID ? this.copy_3htnfi_k$(data) : $super.copy_3htnfi_k$.call(this, data);
  };
  protoOf(BVHVector).hashCode = function () {
    return hashCode(this.data_1);
  };
  function checkDimensions_2(actual, expected) {
    if (!(actual === expected)) {
      // Inline function 'kotlin.error' call
      var message = 'element ' + actual + " doesn't match dimensions " + expected;
      throw IllegalStateException_init_$Create$(toString(message));
    }
  }
  function point$factory() {
    return getPropertyCallableRef('point', 1, KProperty1, function (receiver) {
      return receiver.get_point_iwziav_k$();
    }, null);
  }
  function normal$factory() {
    return getPropertyCallableRef('normal', 1, KProperty1, function (receiver) {
      return receiver.get_normal_h7qsj4_k$();
    }, null);
  }
  function FakeMutableIterator(iterator) {
    this.iterator_1 = iterator;
  }
  protoOf(FakeMutableIterator).get_iterator_c8vxs9_k$ = function () {
    return this.iterator_1;
  };
  protoOf(FakeMutableIterator).hasNext_bitz1p_k$ = function () {
    return this.iterator_1.hasNext_bitz1p_k$();
  };
  protoOf(FakeMutableIterator).next_20eer_k$ = function () {
    return this.iterator_1.next_20eer_k$();
  };
  protoOf(FakeMutableIterator).remove_fgfybg_k$ = function () {
    throw new NotImplementedError();
  };
  protoOf(FakeMutableIterator).remove_ldkf9o_k$ = function () {
    return this.remove_fgfybg_k$();
  };
  function asFakeMutable(_this__u8e3s4) {
    return new FakeMutableIterator(_this__u8e3s4);
  }
  function KdsInternalApi() {
  }
  protoOf(KdsInternalApi).equals = function (other) {
    if (!(other instanceof KdsInternalApi))
      return false;
    other instanceof KdsInternalApi || THROW_CCE();
    return true;
  };
  protoOf(KdsInternalApi).hashCode = function () {
    return 0;
  };
  protoOf(KdsInternalApi).toString = function () {
    return '@korlibs.datastructure.internal.KdsInternalApi()';
  };
  function contentHashCode_3(_this__u8e3s4, src, dst) {
    // Inline function 'korlibs.datastructure.internal.hashCoder' call
    var count = dst - src | 0;
    var out = 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out = imul(out, 7);
        var tmp = out;
        // Inline function 'korlibs.datastructure.internal.contentHashCode.<anonymous>' call
        out = tmp + _this__u8e3s4[src + n | 0] | 0;
      }
       while (inductionVariable < count);
    return out;
  }
  function contentEquals_3(_this__u8e3s4, that, src, dst) {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.datastructure.internal.equaler' call
      var count = dst - src | 0;
      var inductionVariable = 0;
      if (inductionVariable < count)
        do {
          var n = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          // Inline function 'korlibs.datastructure.internal.contentEquals.<anonymous>' call
          if (!(_this__u8e3s4[src + n | 0] === that[src + n | 0])) {
            tmp$ret$1 = false;
            break $l$block;
          }
        }
         while (inductionVariable < count);
      tmp$ret$1 = true;
    }
    return tmp$ret$1;
  }
  function contentHashCode_4(_this__u8e3s4, src, dst) {
    // Inline function 'korlibs.datastructure.internal.hashCoder' call
    var count = dst - src | 0;
    var out = 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out = imul(out, 7);
        var tmp = out;
        // Inline function 'korlibs.datastructure.internal.contentHashCode.<anonymous>' call
        out = tmp + toRawBits(_this__u8e3s4[src + n | 0]) | 0;
      }
       while (inductionVariable < count);
    return out;
  }
  function contentEquals_4(_this__u8e3s4, that, src, dst) {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.datastructure.internal.equaler' call
      var count = dst - src | 0;
      var inductionVariable = 0;
      if (inductionVariable < count)
        do {
          var n = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          // Inline function 'korlibs.datastructure.internal.contentEquals.<anonymous>' call
          if (!(_this__u8e3s4[src + n | 0] === that[src + n | 0])) {
            tmp$ret$1 = false;
            break $l$block;
          }
        }
         while (inductionVariable < count);
      tmp$ret$1 = true;
    }
    return tmp$ret$1;
  }
  function contentHashCode_5(_this__u8e3s4, src, dst) {
    // Inline function 'korlibs.datastructure.internal.hashCoder' call
    var count = dst - src | 0;
    var out = 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out = imul(out, 7);
        var tmp = out;
        // Inline function 'korlibs.datastructure.internal.contentHashCode.<anonymous>' call
        out = tmp + numberToInt(_this__u8e3s4[src + n | 0]) | 0;
      }
       while (inductionVariable < count);
    return out;
  }
  function contentEquals_5(_this__u8e3s4, that, src, dst) {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.datastructure.internal.equaler' call
      var count = dst - src | 0;
      var inductionVariable = 0;
      if (inductionVariable < count)
        do {
          var n = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          // Inline function 'korlibs.datastructure.internal.contentEquals.<anonymous>' call
          if (!(_this__u8e3s4[src + n | 0] === that[src + n | 0])) {
            tmp$ret$1 = false;
            break $l$block;
          }
        }
         while (inductionVariable < count);
      tmp$ret$1 = true;
    }
    return tmp$ret$1;
  }
  function fill_0(_this__u8e3s4, value) {
    var inductionVariable = 0;
    var last = _this__u8e3s4.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        _this__u8e3s4[n] = value;
      }
       while (inductionVariable <= last);
  }
  function contentHashCode_6(size, gen) {
    var result = 1;
    var inductionVariable = 0;
    if (inductionVariable < size)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp = imul(31, result);
        // Inline function 'kotlin.hashCode' call
        var tmp0_safe_receiver = gen(n);
        var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
        result = tmp + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0;
      }
       while (inductionVariable < size);
    return result;
  }
  function equaler(count, gen) {
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!gen(n))
          return false;
      }
       while (inductionVariable < count);
    return true;
  }
  function hashCoder(count, gen) {
    var out = 0;
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        out = imul(out, 7);
        out = out + gen(n) | 0;
      }
       while (inductionVariable < count);
    return out;
  }
  function fill_1(_this__u8e3s4, value) {
    var inductionVariable = 0;
    var last = _this__u8e3s4.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        _this__u8e3s4[n] = value;
      }
       while (inductionVariable <= last);
  }
  function fastIterateRemove(_this__u8e3s4, callback) {
    var n = 0;
    var m = 0;
    while (n < _this__u8e3s4.get_size_woubt6_k$()) {
      if (m >= 0 ? !(m === n) : false) {
        _this__u8e3s4.set_82063s_k$(m, _this__u8e3s4.get_c1px32_k$(n));
      }
      if (callback(_this__u8e3s4.get_c1px32_k$(n))) {
        m = m - 1 | 0;
      }
      n = n + 1 | 0;
      m = m + 1 | 0;
    }
    while (_this__u8e3s4.get_size_woubt6_k$() > m) {
      _this__u8e3s4.removeAt_6niowx_k$(_this__u8e3s4.get_size_woubt6_k$() - 1 | 0);
    }
    return _this__u8e3s4;
  }
  function fastForEachWithIndex(_this__u8e3s4, callback) {
    var n = 0;
    while (n < _this__u8e3s4.get_size_woubt6_k$()) {
      callback(n, _this__u8e3s4.get_c1px32_k$(n));
      n = n + 1 | 0;
    }
  }
  function parallelMapInt(_this__u8e3s4, transform) {
    // Inline function 'kotlin.also' call
    var this_0 = new Int32Array(_this__u8e3s4.get_size_woubt6_k$());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.iterators.parallelMapInt.<anonymous>' call
    // Inline function 'korlibs.datastructure.iterators.parallelForeach' call
    var count = _this__u8e3s4.get_size_woubt6_k$();
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.iterators.parallelMapInt.<anonymous>.<anonymous>' call
        this_0[n] = transform(_this__u8e3s4.get_c1px32_k$(n));
      }
       while (inductionVariable < count);
    return this_0;
  }
  function fastForEach_1(_this__u8e3s4, callback) {
    var n = 0;
    while (n < _this__u8e3s4.get_size_woubt6_k$()) {
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      callback(_this__u8e3s4.getAt_k8n1td_k$(_unary__edvuaz));
    }
  }
  function fastForEach_2(_this__u8e3s4, callback) {
    var n = 0;
    while (n < _this__u8e3s4.length) {
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      callback(_this__u8e3s4[_unary__edvuaz]);
    }
  }
  function fastForEach_3(_this__u8e3s4, callback) {
    var n = 0;
    while (n < _this__u8e3s4.get_size_woubt6_k$()) {
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      callback(_this__u8e3s4.get_c1px32_k$(_unary__edvuaz));
    }
  }
  function MutableMapExt() {
  }
  function Companion_23() {
    Companion_instance_23 = this;
  }
  protoOf(Companion_23).fromMap_u240j2_k$ = function (map, keys) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(keys, 10));
    var tmp0_iterator = keys.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.map.Companion.fromMap.<anonymous>' call
      var tmp$ret$0 = new MutableEntryExt(map, item);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    return toMutableSet(destination);
  };
  var Companion_instance_23;
  function Companion_getInstance_23() {
    if (Companion_instance_23 == null)
      new Companion_23();
    return Companion_instance_23;
  }
  function MutableEntryExt(map, key) {
    Companion_getInstance_23();
    this.map_1 = map;
    this.key_1 = key;
  }
  protoOf(MutableEntryExt).get_map_18j0ul_k$ = function () {
    return this.map_1;
  };
  protoOf(MutableEntryExt).get_key_18j28a_k$ = function () {
    return this.key_1;
  };
  protoOf(MutableEntryExt).get_value_j01efc_k$ = function () {
    return ensureNotNull(this.map_1.get_wei43m_k$(this.key_1));
  };
  protoOf(MutableEntryExt).setValue_9cjski_k$ = function (newValue) {
    var oldValue = this.get_value_j01efc_k$();
    // Inline function 'kotlin.collections.set' call
    var this_0 = this.map_1;
    var key = this.key_1;
    this_0.put_4fpzoq_k$(key, newValue);
    return oldValue;
  };
  function _get__instance__p9czbx($this) {
    return $this._instance_1;
  }
  function _get_lock__d9xa4g_1($this) {
    return $this.lock_1;
  }
  function _set_x__db55ql($this, _set____db54di) {
    $this.x_1 = _set____db54di;
  }
  function _get_x__7mlp09($this) {
    return $this.x_1;
  }
  function _set_y__db55rg($this, _set____db54di) {
    $this.y_1 = _set____db54di;
  }
  function _get_y__7mlp14($this) {
    return $this.y_1;
  }
  function _set_z__db55sb($this, _set____db54di) {
    $this.z_1 = _set____db54di;
  }
  function _get_z__7mlp1z($this) {
    return $this.z_1;
  }
  function _set_w__db55pq($this, _set____db54di) {
    $this.w_1 = _set____db54di;
  }
  function _get_w__7mloze($this) {
    return $this.w_1;
  }
  function _set_v__db55ov($this, _set____db54di) {
    $this.v_1 = _set____db54di;
  }
  function _get_v__7mloyj($this) {
    return $this.v_1;
  }
  function _set_addend__hcd0al($this, _set____db54di) {
    $this.addend_1 = _set____db54di;
  }
  function _get_addend__it3kxd($this) {
    return $this.addend_1;
  }
  function Companion_24() {
    Companion_instance_24 = this;
    FastRandom_init_$Init$_1(this);
    this._instance_1 = FastRandom_init_$Create$_0(Default_getInstance().nextLong_njwv0v_k$());
    this.lock_1 = new Lock();
  }
  protoOf(Companion_24).get_Default_goqax4_k$ = function () {
    return this;
  };
  protoOf(Companion_24).nextBits_kty4bl_k$ = function (bitCount) {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.random.Companion.nextBits.<anonymous>' call
        tmp$ret$1 = Companion_getInstance_24()._instance_1.nextBits_kty4bl_k$(bitCount);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$1;
  };
  var Companion_instance_24;
  function Companion_getInstance_24() {
    if (Companion_instance_24 == null)
      new Companion_24();
    return Companion_instance_24;
  }
  function FastRandom_init_$Init$(seed1, seed2, $this) {
    FastRandom.call($this, seed1, seed2, 0, 0, ~seed1, seed1 << 10 ^ (seed2 >>> 4 | 0));
    return $this;
  }
  function FastRandom_init_$Create$(seed1, seed2) {
    return FastRandom_init_$Init$(seed1, seed2, objectCreate(protoOf(FastRandom)));
  }
  function FastRandom_init_$Init$_0(seed, $this) {
    FastRandom_init_$Init$(seed.toInt_1tsl84_k$(), seed.ushr_z7nmq8_k$(32).toInt_1tsl84_k$(), $this);
    return $this;
  }
  function FastRandom_init_$Create$_0(seed) {
    return FastRandom_init_$Init$_0(seed, objectCreate(protoOf(FastRandom)));
  }
  function FastRandom_init_$Init$_1($this) {
    FastRandom_init_$Init$_0(Default_getInstance().nextLong_njwv0v_k$(), $this);
    return $this;
  }
  function FastRandom_init_$Create$_1() {
    return FastRandom_init_$Init$_1(objectCreate(protoOf(FastRandom)));
  }
  function takeUpperBits(_this__u8e3s4, $this, bitCount) {
    return _this__u8e3s4 >>> (32 - bitCount | 0) | 0;
  }
  function boundsErrorMessage($this, from, until) {
    return 'Random range is empty: [' + toString(from) + ', ' + toString(until) + ').';
  }
  function FastRandom(x, y, z, w, v, addend) {
    Companion_getInstance_24();
    Random.call(this);
    this.x_1 = x;
    this.y_1 = y;
    this.z_1 = z;
    this.w_1 = w;
    this.v_1 = v;
    this.addend_1 = addend;
    // Inline function 'kotlin.require' call
    // Inline function 'kotlin.contracts.contract' call
    if (!!((this.x_1 | this.y_1 | this.z_1 | this.w_1 | this.v_1) === 0)) {
      // Inline function 'korlibs.datastructure.random.FastRandom.<anonymous>' call
      var message = 'Initial state must have at least one non-zero element.';
      throw IllegalArgumentException_init_$Create$(toString(message));
    }
    // Inline function 'kotlin.repeat' call
    // Inline function 'kotlin.contracts.contract' call
    var inductionVariable = 0;
    if (inductionVariable < 64)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.random.FastRandom.<anonymous>' call
        this.nextInt_ujorgc_k$();
      }
       while (inductionVariable < 64);
  }
  protoOf(FastRandom).nextInt_ujorgc_k$ = function () {
    var t = this.x_1;
    t = t ^ (t >>> 2 | 0);
    this.x_1 = this.y_1;
    this.y_1 = this.z_1;
    this.z_1 = this.w_1;
    var v0 = this.v_1;
    this.w_1 = v0;
    t = t ^ t << 1 ^ v0 ^ v0 << 4;
    this.v_1 = t;
    this.addend_1 = this.addend_1 + 362437 | 0;
    return t + this.addend_1 | 0;
  };
  protoOf(FastRandom).nextBits_kty4bl_k$ = function (bitCount) {
    // Inline function 'korlibs.datastructure.random.FastRandom.takeUpperBits' call
    return this.nextInt_ujorgc_k$() >>> (32 - bitCount | 0) | 0;
  };
  protoOf(FastRandom).nextInt_ak696k_k$ = function (from, until) {
    // Inline function 'kotlin.require' call
    // Inline function 'kotlin.contracts.contract' call
    if (!(until > from)) {
      // Inline function 'korlibs.datastructure.random.FastRandom.nextInt.<anonymous>' call
      var message = boundsErrorMessage(this, from, until);
      throw IllegalArgumentException_init_$Create$(toString(message));
    }
    return (this.nextBits_kty4bl_k$(31) % (until - from | 0) | 0) + from | 0;
  };
  protoOf(FastRandom).nextLong_m0lbld_k$ = function (from, until) {
    // Inline function 'kotlin.require' call
    // Inline function 'kotlin.contracts.contract' call
    if (!(until.compareTo_9jj042_k$(from) > 0)) {
      // Inline function 'korlibs.datastructure.random.FastRandom.nextLong.<anonymous>' call
      var message = boundsErrorMessage(this, from, until);
      throw IllegalArgumentException_init_$Create$(toString(message));
    }
    return this.nextLong_njwv0v_k$().and_4spn93_k$((new Long(-1, 2147483647)).rem_bsnl9o_k$(until.minus_mfbszm_k$(from))).plus_r93sks_k$(from);
  };
  function fastRandom(_this__u8e3s4) {
    return random(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_0(_this__u8e3s4) {
    return random_0(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_1(_this__u8e3s4) {
    return random_1(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_2(_this__u8e3s4) {
    return random_2(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_3(_this__u8e3s4) {
    return random_3(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_4(_this__u8e3s4) {
    return random_4(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_5(_this__u8e3s4) {
    return random_5(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_6(_this__u8e3s4) {
    return random_6(_this__u8e3s4, Companion_getInstance_24());
  }
  function fastRandom_7(_this__u8e3s4) {
    return random_7(_this__u8e3s4, Companion_getInstance_24());
  }
  function nativeThread_0(start, isDaemon, name, priority, block) {
    start = start === VOID ? true : start;
    isDaemon = isDaemon === VOID ? false : isDaemon;
    name = name === VOID ? null : name;
    priority = priority === VOID ? -1 : priority;
    return nativeThread(start, isDaemon, name, priority, block);
  }
  function get_extra(_this__u8e3s4) {
    if (_this__u8e3s4.get_userData_yt8c7y_k$() == null) {
      _this__u8e3s4.set_userData_i5zsl9_k$(new Mixin());
    }
    var tmp = _this__u8e3s4.get_userData_yt8c7y_k$();
    return (!(tmp == null) ? isInterface(tmp, Extra) : false) ? tmp : THROW_CCE();
  }
  function WithParent() {
  }
  function Computed(prop, default_0) {
    this.prop_1 = prop;
    this.default_1 = default_0;
  }
  protoOf(Computed).get_prop_wosl9o_k$ = function () {
    return this.prop_1;
  };
  protoOf(Computed).get_default_qtagd4_k$ = function () {
    return this.default_1;
  };
  protoOf(Computed).getValue_v5m1ht_k$ = function (thisRef, p) {
    var current = thisRef;
    while (!(current == null)) {
      var result = this.prop_1.get(current);
      if (!(result == null))
        return result;
      current = current.get_parent_hy4reb_k$();
    }
    return this.default_1();
  };
  function Extra$PropertyThis$transform$lambda(_this__u8e3s4, it) {
    return it;
  }
  function Mixin(extra) {
    extra = extra === VOID ? null : extra;
    this.extra_1 = extra;
  }
  protoOf(Mixin).set_extra_ulxxnb_k$ = function (_set____db54di) {
    this.extra_1 = _set____db54di;
  };
  protoOf(Mixin).get_extra_ir3qw7_k$ = function () {
    return this.extra_1;
  };
  function Companion_25() {
    Companion_instance_25 = this;
  }
  protoOf(Companion_25).invoke_jkqnwo_k$ = function () {
    return new Mixin();
  };
  var Companion_instance_25;
  function Companion_getInstance_25() {
    if (Companion_instance_25 == null)
      new Companion_25();
    return Companion_instance_25;
  }
  function Property(name, defaultGen) {
    name = name === VOID ? null : name;
    this.name_1 = name;
    this.defaultGen_1 = defaultGen;
  }
  protoOf(Property).get_name_woqyms_k$ = function () {
    return this.name_1;
  };
  protoOf(Property).get_defaultGen_8y7pd4_k$ = function () {
    return this.defaultGen_1;
  };
  protoOf(Property).getValue_l6ftby_k$ = function (thisRef, property) {
    // Inline function 'korlibs.datastructure.fastCastTo' call
    var tmp56_safe_receiver = thisRef.get_extra_ir3qw7_k$();
    var tmp;
    if (tmp56_safe_receiver == null) {
      tmp = null;
    } else {
      var tmp0_elvis_lhs = this.name_1;
      tmp = tmp56_safe_receiver.get_6bo4tg_k$(tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs);
    }
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var res = tmp;
    if (res == null) {
      var r = this.defaultGen_1();
      var tmp_0;
      if (!(r == null)) {
        tmp_0 = true;
      } else {
        var tmp1_elvis_lhs = this.name_1;
        tmp_0 = hasExtra(thisRef, tmp1_elvis_lhs == null ? property.callableName : tmp1_elvis_lhs);
      }
      if (tmp_0) {
        // Inline function 'korlibs.datastructure.Property.setValue' call
        var tmp0_elvis_lhs_0 = this.name_1;
        var tmp_1 = tmp0_elvis_lhs_0 == null ? property.callableName : tmp0_elvis_lhs_0;
        // Inline function 'korlibs.datastructure.fastCastTo' call
        // Inline function 'kotlin.js.unsafeCast' call
        // Inline function 'kotlin.js.asDynamic' call
        setExtra(thisRef, tmp_1, r);
      }
      return r;
    }
    return res;
  };
  protoOf(Property).setValue_nwp63_k$ = function (thisRef, property, value) {
    var tmp0_elvis_lhs = this.name_1;
    var tmp = tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs;
    // Inline function 'korlibs.datastructure.fastCastTo' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    setExtra(thisRef, tmp, value);
  };
  function PropertyThis(name, defaultGen) {
    name = name === VOID ? null : name;
    this.name_1 = name;
    this.defaultGen_1 = defaultGen;
    var tmp = this;
    tmp.transform_1 = Extra$PropertyThis$transform$lambda;
  }
  protoOf(PropertyThis).get_name_woqyms_k$ = function () {
    return this.name_1;
  };
  protoOf(PropertyThis).get_defaultGen_8y7pd4_k$ = function () {
    return this.defaultGen_1;
  };
  protoOf(PropertyThis).set_transform_w84910_k$ = function (_set____db54di) {
    this.transform_1 = _set____db54di;
  };
  protoOf(PropertyThis).get_transform_7ltst4_k$ = function () {
    return this.transform_1;
  };
  protoOf(PropertyThis).withTransform_3ad6ky_k$ = function (block) {
    this.transform_1 = block;
    return this;
  };
  protoOf(PropertyThis).getValue_4fu5h9_k$ = function (thisRef, property) {
    var tmp0_elvis_lhs = this.name_1;
    var res = getExtraTyped(thisRef, tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs);
    if (res == null) {
      var r = this.defaultGen_1(thisRef);
      // Inline function 'korlibs.datastructure.PropertyThis.setValueUntransformed' call
      var tmp0_elvis_lhs_0 = this.name_1;
      setExtra(thisRef, tmp0_elvis_lhs_0 == null ? property.callableName : tmp0_elvis_lhs_0, r);
      return r;
    }
    return res;
  };
  protoOf(PropertyThis).setValueUntransformed_7dled8_k$ = function (thisRef, property, value) {
    var tmp0_elvis_lhs = this.name_1;
    setExtra(thisRef, tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs, value);
  };
  protoOf(PropertyThis).setValue_orbz1s_k$ = function (thisRef, property, value) {
    // Inline function 'korlibs.datastructure.PropertyThis.setValueUntransformed' call
    var value_0 = this.transform_1(thisRef, value);
    var tmp0_elvis_lhs = this.name_1;
    setExtra(thisRef, tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs, value_0);
  };
  function Extra() {
  }
  function extraProperty(name, default_0) {
    name = name === VOID ? null : name;
    return new Property(name, default_0);
  }
  function extraPropertyThis(name, transform, default_0) {
    name = name === VOID ? null : name;
    var tmp;
    if (transform === VOID) {
      tmp = extraPropertyThis$lambda;
    } else {
      tmp = transform;
    }
    transform = tmp;
    // Inline function 'korlibs.datastructure.PropertyThis.withTransform' call
    var this_0 = new PropertyThis(name, default_0);
    this_0.transform_1 = transform;
    return this_0;
  }
  function _get_lock__d9xa4g_2($this) {
    return $this.lock_1;
  }
  function _get_data__d5abxd_5($this) {
    return $this.data_1;
  }
  function ExtraObject() {
    this.lock_1 = new Lock();
    this.data_1 = FastStringMap_0();
    var tmp = this;
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.values.<anonymous>' call
        tmp$ret$1 = toMutableList(get_values_0(this.data_1));
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    tmp.values_1 = tmp$ret$1;
  }
  protoOf(ExtraObject).get_size_woubt6_k$ = function () {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.<get-size>.<anonymous>' call
        tmp$ret$1 = get_size_2(this.data_1);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$1;
  };
  protoOf(ExtraObject).get_entries_p20ztl_k$ = function () {
    var tmp$ret$6;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.<get-entries>.<anonymous>' call
        // Inline function 'kotlin.collections.associateWith' call
        var this_1 = get_keys_0(this.data_1);
        var result = LinkedHashMap_init_$Create$_0(coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_1, 10)), 16));
        // Inline function 'kotlin.collections.associateWithTo' call
        var tmp0_iterator = this_1.iterator_jk1svi_k$();
        while (tmp0_iterator.hasNext_bitz1p_k$()) {
          var element = tmp0_iterator.next_20eer_k$();
          // Inline function 'korlibs.datastructure.ExtraObject.<get-entries>.<anonymous>.<anonymous>' call
          // Inline function 'korlibs.datastructure.get' call
          // Inline function 'kotlin.js.asDynamic' call
          var tmp$ret$2 = this.data_1.get(element);
          result.put_4fpzoq_k$(element, tmp$ret$2);
        }
        tmp$ret$6 = toMutableSet(toMutableMap(result).get_entries_p20ztl_k$());
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$6;
  };
  protoOf(ExtraObject).get_keys_wop4xp_k$ = function () {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.<get-keys>.<anonymous>' call
        tmp$ret$1 = toMutableSet(get_keys_0(this.data_1));
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$1;
  };
  protoOf(ExtraObject).get_values_ksazhn_k$ = function () {
    return this.values_1;
  };
  protoOf(ExtraObject).get_6bo4tg_k$ = function (key) {
    var tmp$ret$3;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.get.<anonymous>' call
        // Inline function 'korlibs.datastructure.get' call
        // Inline function 'kotlin.js.asDynamic' call
        tmp$ret$3 = this.data_1.get(key);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$3;
  };
  protoOf(ExtraObject).get_wei43m_k$ = function (key) {
    if (!(!(key == null) ? typeof key === 'string' : false))
      return null;
    return this.get_6bo4tg_k$((!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE());
  };
  protoOf(ExtraObject).set_vvveh5_k$ = function (key, value) {
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.set' call
        // Inline function 'kotlin.run' call
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'kotlin.js.asDynamic' call
        this.data_1.set(key, value);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return Unit_getInstance();
  };
  protoOf(ExtraObject).contains_zh0gsb_k$ = function (key) {
    var tmp$ret$3;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.contains.<anonymous>' call
        // Inline function 'korlibs.datastructure.contains' call
        // Inline function 'kotlin.js.asDynamic' call
        tmp$ret$3 = this.data_1.has(key);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$3;
  };
  protoOf(ExtraObject).isEmpty_y1axqb_k$ = function () {
    return this.get_size_woubt6_k$() === 0;
  };
  protoOf(ExtraObject).clear_j9egeb_k$ = function () {
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'kotlin.js.asDynamic' call
        this.data_1.clear();
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return Unit_getInstance();
  };
  protoOf(ExtraObject).remove_z05dva_k$ = function (key) {
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'kotlin.js.asDynamic' call
        this.data_1.delete(key);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return Unit_getInstance();
  };
  protoOf(ExtraObject).remove_gppy8k_k$ = function (key) {
    if (!(!(key == null) ? typeof key === 'string' : false))
      return null;
    return this.remove_z05dva_k$((!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE());
  };
  protoOf(ExtraObject).putAll_lv0eka_k$ = function (from) {
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'kotlin.collections.iterator' call
        var _iterator__ex2g4s = from.get_entries_p20ztl_k$().iterator_jk1svi_k$();
        while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
          var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
          // Inline function 'kotlin.collections.component1' call
          var key = _destruct__k2r9zo.get_key_18j28a_k$();
          // Inline function 'kotlin.collections.component2' call
          var value = _destruct__k2r9zo.get_value_j01efc_k$();
          // Inline function 'korlibs.datastructure.set' call
          // Inline function 'kotlin.run' call
          // Inline function 'kotlin.contracts.contract' call
          // Inline function 'kotlin.js.asDynamic' call
          this.data_1.set(key, value);
        }
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return Unit_getInstance();
  };
  protoOf(ExtraObject).putAll_wgg6cj_k$ = function (from) {
    return this.putAll_lv0eka_k$(from);
  };
  protoOf(ExtraObject).put_9oxj04_k$ = function (key, value) {
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.set' call
        // Inline function 'kotlin.run' call
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'kotlin.js.asDynamic' call
        this.data_1.set(key, value);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return Unit_getInstance();
  };
  protoOf(ExtraObject).put_4fpzoq_k$ = function (key, value) {
    var tmp = (!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE();
    return this.put_9oxj04_k$(tmp, (value == null ? true : !(value == null)) ? value : THROW_CCE());
  };
  protoOf(ExtraObject).containsValue_4bai5p_k$ = function (value) {
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.containsValue.<anonymous>' call
        tmp$ret$1 = this.values_1.contains_aljjnj_k$(value);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$1;
  };
  protoOf(ExtraObject).containsValue_yf2ykl_k$ = function (value) {
    if (!(value == null ? true : !(value == null)))
      return false;
    return this.containsValue_4bai5p_k$((value == null ? true : !(value == null)) ? value : THROW_CCE());
  };
  protoOf(ExtraObject).containsKey_w445h6_k$ = function (key) {
    var tmp$ret$3;
    $l$block: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      var this_0 = this.lock_1;
      this_0.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.ExtraObject.containsKey.<anonymous>' call
        // Inline function 'korlibs.datastructure.contains' call
        // Inline function 'kotlin.js.asDynamic' call
        tmp$ret$3 = this.data_1.has(key);
        break $l$block;
      }finally {
        this_0.set_locked_3md9le_k$(false);
      }
    }
    return tmp$ret$3;
  };
  protoOf(ExtraObject).containsKey_aw81wo_k$ = function (key) {
    if (!(!(key == null) ? typeof key === 'string' : false))
      return false;
    return this.containsKey_w445h6_k$((!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE());
  };
  function WeakProperty(gen) {
    this.gen_1 = gen;
    this.map_1 = new WeakMap_0();
  }
  protoOf(WeakProperty).get_gen_18j57d_k$ = function () {
    return this.gen_1;
  };
  protoOf(WeakProperty).get_map_18j0ul_k$ = function () {
    return this.map_1;
  };
  protoOf(WeakProperty).getValue_m93qlt_k$ = function (obj, property) {
    // Inline function 'korlibs.datastructure.getOrPut' call
    var this_0 = this.map_1;
    if (!this_0.contains_7vtym0_k$(obj)) {
      // Inline function 'korlibs.datastructure.WeakProperty.getValue.<anonymous>' call
      var tmp$ret$0 = this.gen_1();
      this_0.set_c2d6a0_k$(obj, tmp$ret$0);
    }
    return ensureNotNull(this_0.get_h31hzz_k$(obj));
  };
  protoOf(WeakProperty).setValue_qx0o0_k$ = function (obj, property, value) {
    this.map_1.set_c2d6a0_k$(obj, value);
  };
  function WeakPropertyThis(gen) {
    this.gen_1 = gen;
    this.map_1 = new WeakMap_0();
  }
  protoOf(WeakPropertyThis).get_gen_18j57d_k$ = function () {
    return this.gen_1;
  };
  protoOf(WeakPropertyThis).get_map_18j0ul_k$ = function () {
    return this.map_1;
  };
  protoOf(WeakPropertyThis).getValue_q7b8sf_k$ = function (obj, property) {
    // Inline function 'korlibs.datastructure.getOrPut' call
    var this_0 = this.map_1;
    if (!this_0.contains_7vtym0_k$(obj)) {
      // Inline function 'korlibs.datastructure.WeakPropertyThis.getValue.<anonymous>' call
      var tmp$ret$0 = this.gen_1(obj);
      this_0.set_c2d6a0_k$(obj, tmp$ret$0);
    }
    return ensureNotNull(this_0.get_h31hzz_k$(obj));
  };
  protoOf(WeakPropertyThis).setValue_bmiw5s_k$ = function (obj, property, value) {
    this.map_1.set_c2d6a0_k$(obj, value);
  };
  function setExtra(_this__u8e3s4, name, value) {
    if (_this__u8e3s4.get_extra_ir3qw7_k$() == null) {
      if (value == null)
        return Unit_getInstance();
      _this__u8e3s4.set_extra_ulxxnb_k$(ExtraTypeCreate());
    }
    var tmp60_safe_receiver = _this__u8e3s4.get_extra_ir3qw7_k$();
    if (tmp60_safe_receiver == null)
      null;
    else {
      tmp60_safe_receiver.set_vvveh5_k$(name, value);
    }
  }
  function getExtra(_this__u8e3s4, name) {
    var tmp59_safe_receiver = _this__u8e3s4.get_extra_ir3qw7_k$();
    return tmp59_safe_receiver == null ? null : tmp59_safe_receiver.get_6bo4tg_k$(name);
  }
  function hasExtra(_this__u8e3s4, name) {
    var tmp58_safe_receiver = _this__u8e3s4.get_extra_ir3qw7_k$();
    return (tmp58_safe_receiver == null ? null : tmp58_safe_receiver.contains_zh0gsb_k$(name)) === true;
  }
  function getExtraTyped(_this__u8e3s4, name) {
    // Inline function 'korlibs.datastructure.fastCastTo' call
    var tmp57_safe_receiver = _this__u8e3s4.get_extra_ir3qw7_k$();
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return tmp57_safe_receiver == null ? null : tmp57_safe_receiver.get_6bo4tg_k$(name);
  }
  function ExtraTypeCreate() {
    return new ExtraObject();
  }
  function extraPropertyThis$lambda(_this__u8e3s4, it) {
    return it;
  }
  function _BSearchResult___init__impl__em6zam(raw) {
    return raw;
  }
  function _BSearchResult___get_raw__impl__uyuhf3($this) {
    return $this;
  }
  function _BSearchResult___get_found__impl__om04iz($this) {
    return _BSearchResult___get_raw__impl__uyuhf3($this) >= 0;
  }
  function _BSearchResult___get_index__impl__b5kraz($this) {
    return _BSearchResult___get_found__impl__om04iz($this) ? _BSearchResult___get_raw__impl__uyuhf3($this) : -1;
  }
  function _BSearchResult___get_nearIndex__impl__s8nq43($this) {
    return _BSearchResult___get_found__impl__om04iz($this) ? _BSearchResult___get_raw__impl__uyuhf3($this) : (-_BSearchResult___get_raw__impl__uyuhf3($this) | 0) - 1 | 0;
  }
  function BSearchResult__toString_impl_wqwv1i($this) {
    return 'BSearchResult(raw=' + $this + ')';
  }
  function BSearchResult__hashCode_impl_gj1a4r($this) {
    return $this;
  }
  function BSearchResult__equals_impl_5ycdp5($this, other) {
    if (!(other instanceof BSearchResult))
      return false;
    if (!($this === (other instanceof BSearchResult ? other.raw_1 : THROW_CCE())))
      return false;
    return true;
  }
  function BSearchResult(raw) {
    this.raw_1 = raw;
  }
  protoOf(BSearchResult).toString = function () {
    return BSearchResult__toString_impl_wqwv1i(this.raw_1);
  };
  protoOf(BSearchResult).hashCode = function () {
    return BSearchResult__hashCode_impl_gj1a4r(this.raw_1);
  };
  protoOf(BSearchResult).equals = function (other) {
    return BSearchResult__equals_impl_5ycdp5(this.raw_1, other);
  };
  function binarySearch(_this__u8e3s4, v, fromIndex, toIndex) {
    fromIndex = fromIndex === VOID ? 0 : fromIndex;
    toIndex = toIndex === VOID ? _this__u8e3s4.length : toIndex;
    // Inline function 'korlibs.datastructure.genericBinarySearchResult' call
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.datastructure.genericBinarySearch' call
      var low = fromIndex;
      var high = toIndex - 1 | 0;
      while (low <= high) {
        var mid = (low + high | 0) / 2 | 0;
        // Inline function 'korlibs.datastructure.binarySearch.<anonymous>' call
        var mval = compareTo(_this__u8e3s4[mid], v);
        if (mval < 0) {
          low = mid + 1 | 0;
        } else if (mval > 0) {
          high = mid - 1 | 0;
        } else {
          tmp$ret$1 = mid;
          break $l$block;
        }
      }
      // Inline function 'korlibs.datastructure.genericBinarySearchResult.<anonymous>' call
      tmp$ret$1 = (-low | 0) - 1 | 0;
    }
    return _BSearchResult___init__impl__em6zam(tmp$ret$1);
  }
  function binarySearchLeft(_this__u8e3s4, v, fromIndex, toIndex) {
    fromIndex = fromIndex === VOID ? 0 : fromIndex;
    toIndex = toIndex === VOID ? _this__u8e3s4.length : toIndex;
    // Inline function 'korlibs.datastructure.genericBinarySearchLeft' call
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.datastructure.genericBinarySearch' call
      var low = fromIndex;
      var high = toIndex - 1 | 0;
      while (low <= high) {
        var mid = (low + high | 0) / 2 | 0;
        // Inline function 'korlibs.datastructure.binarySearchLeft.<anonymous>' call
        var mval = compareTo(_this__u8e3s4[mid], v);
        if (mval < 0) {
          low = mid + 1 | 0;
        } else if (mval > 0) {
          high = mid - 1 | 0;
        } else {
          tmp$ret$1 = mid;
          break $l$block;
        }
      }
      // Inline function 'korlibs.datastructure.genericBinarySearchLeft.<anonymous>' call
      var low_0 = low;
      // Inline function 'kotlin.math.min' call
      var b = high;
      var tmp$ret$2 = Math.min(low_0, b);
      tmp$ret$1 = coerceIn(tmp$ret$2, fromIndex, toIndex - 1 | 0);
    }
    return tmp$ret$1;
  }
  function binarySearchRight(_this__u8e3s4, v, fromIndex, toIndex) {
    fromIndex = fromIndex === VOID ? 0 : fromIndex;
    toIndex = toIndex === VOID ? _this__u8e3s4.length : toIndex;
    // Inline function 'korlibs.datastructure.genericBinarySearchRight' call
    var tmp$ret$1;
    $l$block: {
      // Inline function 'korlibs.datastructure.genericBinarySearch' call
      var low = fromIndex;
      var high = toIndex - 1 | 0;
      while (low <= high) {
        var mid = (low + high | 0) / 2 | 0;
        // Inline function 'korlibs.datastructure.binarySearchRight.<anonymous>' call
        var mval = compareTo(_this__u8e3s4[mid], v);
        if (mval < 0) {
          low = mid + 1 | 0;
        } else if (mval > 0) {
          high = mid - 1 | 0;
        } else {
          tmp$ret$1 = mid;
          break $l$block;
        }
      }
      // Inline function 'korlibs.datastructure.genericBinarySearchRight.<anonymous>' call
      var low_0 = low;
      // Inline function 'kotlin.math.max' call
      var b = high;
      var tmp$ret$2 = Math.max(low_0, b);
      tmp$ret$1 = coerceIn(tmp$ret$2, fromIndex, toIndex - 1 | 0);
    }
    return tmp$ret$1;
  }
  function getCyclic(_this__u8e3s4, index) {
    return _this__u8e3s4.get_c1px32_k$(Math_getInstance().umod_uagn7p_k$(index, _this__u8e3s4.get_size_woubt6_k$()));
  }
  function getCyclic_0(_this__u8e3s4, index) {
    return _this__u8e3s4[Math_getInstance().umod_uagn7p_k$(index, _this__u8e3s4.length)];
  }
  function getCyclic_1(_this__u8e3s4, index) {
    return _this__u8e3s4.getAt_k8n1td_k$(Math_getInstance().umod_uagn7p_k$(index, _this__u8e3s4.get_size_woubt6_k$()));
  }
  function getCyclic_2(_this__u8e3s4, index) {
    return _this__u8e3s4.getAt_k8n1td_k$(Math_getInstance().umod_uagn7p_k$(index, _this__u8e3s4.get_size_woubt6_k$()));
  }
  function getCyclic_3(_this__u8e3s4, index) {
    return _this__u8e3s4.getAt_k8n1td_k$(Math_getInstance().umod_uagn7p_k$(index, _this__u8e3s4.get_size_woubt6_k$()));
  }
  function getCyclic_4(_this__u8e3s4, x, y) {
    return _this__u8e3s4.get_bzg6vq_k$(Math_getInstance().umod_uagn7p_k$(x, _this__u8e3s4.get_width_j0q4yl_k$()), Math_getInstance().umod_uagn7p_k$(y, _this__u8e3s4.get_height_e7t92o_k$()));
  }
  function linkedHashMapOf(pairs) {
    // Inline function 'kotlin.also' call
    var this_0 = LinkedHashMap_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.linkedHashMapOf.<anonymous>' call
    var inductionVariable = 0;
    var last = pairs.length;
    while (inductionVariable < last) {
      var _destruct__k2r9zo = pairs[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      var key = _destruct__k2r9zo.component1_7eebsc_k$();
      var value = _destruct__k2r9zo.component2_7eebsb_k$();
      // Inline function 'kotlin.collections.set' call
      this_0.put_4fpzoq_k$(key, value);
    }
    return this_0;
  }
  function toLinkedMap(_this__u8e3s4) {
    // Inline function 'kotlin.also' call
    var this_0 = LinkedHashMap_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.toLinkedMap.<anonymous>' call
    var _iterator__ex2g4s = _this__u8e3s4.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      var key = _destruct__k2r9zo.component1_7eebsc_k$();
      var value = _destruct__k2r9zo.component2_7eebsb_k$();
      // Inline function 'kotlin.collections.set' call
      this_0.put_4fpzoq_k$(key, value);
    }
    return this_0;
  }
  function flip(_this__u8e3s4) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(_this__u8e3s4.get_size_woubt6_k$());
    // Inline function 'kotlin.collections.iterator' call
    var tmp0_iterator = _this__u8e3s4.get_entries_p20ztl_k$().iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.flip.<anonymous>' call
      var tmp$ret$1 = new Pair(item.get_value_j01efc_k$(), item.get_key_18j28a_k$());
      destination.add_utx5q5_k$(tmp$ret$1);
    }
    return toMap(destination);
  }
  function countMap(_this__u8e3s4) {
    // Inline function 'kotlin.also' call
    var this_0 = LinkedHashMap_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.countMap.<anonymous>' call
    var _iterator__ex2g4s = _this__u8e3s4.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var key = _iterator__ex2g4s.next_20eer_k$();
      incr(this_0, key, 1);
    }
    return this_0;
  }
  function incr(_this__u8e3s4, key, delta) {
    delta = delta === VOID ? 1 : delta;
    // Inline function 'kotlin.collections.getOrPut' call
    var value = _this__u8e3s4.get_wei43m_k$(key);
    var tmp;
    if (value == null) {
      // Inline function 'korlibs.datastructure.incr.<anonymous>' call
      var answer = 0;
      _this__u8e3s4.put_4fpzoq_k$(key, answer);
      tmp = answer;
    } else {
      tmp = value;
    }
    var next = tmp + delta | 0;
    // Inline function 'kotlin.collections.set' call
    _this__u8e3s4.put_4fpzoq_k$(key, next);
    return next;
  }
  function mapWhileInt(cond, gen) {
    // Inline function 'kotlin.apply' call
    var this_0 = new IntArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileInt.<anonymous>' call
    while (cond(this_0.get_size_woubt6_k$()))
      this_0.plusAssign_8mmvnl_k$(gen(this_0.get_size_woubt6_k$()));
    return this_0.toIntArray_8jv8ed_k$();
  }
  function mapWhileFloat(cond, gen) {
    // Inline function 'kotlin.apply' call
    var this_0 = new FloatArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileFloat.<anonymous>' call
    while (cond(this_0.get_size_woubt6_k$()))
      this_0.plusAssign_xjcc2p_k$(gen(this_0.get_size_woubt6_k$()));
    return this_0.toFloatArray_ixdbug_k$();
  }
  function mapWhileDouble(cond, gen) {
    // Inline function 'kotlin.apply' call
    var this_0 = new DoubleArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileDouble.<anonymous>' call
    while (cond(this_0.get_size_woubt6_k$()))
      this_0.plusAssign_hd7up9_k$(gen(this_0.get_size_woubt6_k$()));
    return this_0.toDoubleArray_tm3pu5_k$();
  }
  function genericBinarySearchResult(fromIndex, toIndex, check) {
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure.genericBinarySearch' call
      var low = fromIndex;
      var high = toIndex - 1 | 0;
      while (low <= high) {
        var mid = (low + high | 0) / 2 | 0;
        var mval = check(mid);
        if (mval < 0) {
          low = mid + 1 | 0;
        } else if (mval > 0) {
          high = mid - 1 | 0;
        } else {
          tmp$ret$0 = mid;
          break $l$block;
        }
      }
      // Inline function 'korlibs.datastructure.genericBinarySearchResult.<anonymous>' call
      tmp$ret$0 = (-low | 0) - 1 | 0;
    }
    return _BSearchResult___init__impl__em6zam(tmp$ret$0);
  }
  function genericBinarySearchLeft(fromIndex, toIndex, check) {
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure.genericBinarySearch' call
      var low = fromIndex;
      var high = toIndex - 1 | 0;
      while (low <= high) {
        var mid = (low + high | 0) / 2 | 0;
        var mval = check(mid);
        if (mval < 0) {
          low = mid + 1 | 0;
        } else if (mval > 0) {
          high = mid - 1 | 0;
        } else {
          tmp$ret$0 = mid;
          break $l$block;
        }
      }
      // Inline function 'korlibs.datastructure.genericBinarySearchLeft.<anonymous>' call
      var low_0 = low;
      // Inline function 'kotlin.math.min' call
      var b = high;
      var tmp$ret$1 = Math.min(low_0, b);
      tmp$ret$0 = coerceIn(tmp$ret$1, fromIndex, toIndex - 1 | 0);
    }
    return tmp$ret$0;
  }
  function genericBinarySearchRight(fromIndex, toIndex, check) {
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure.genericBinarySearch' call
      var low = fromIndex;
      var high = toIndex - 1 | 0;
      while (low <= high) {
        var mid = (low + high | 0) / 2 | 0;
        var mval = check(mid);
        if (mval < 0) {
          low = mid + 1 | 0;
        } else if (mval > 0) {
          high = mid - 1 | 0;
        } else {
          tmp$ret$0 = mid;
          break $l$block;
        }
      }
      // Inline function 'korlibs.datastructure.genericBinarySearchRight.<anonymous>' call
      var low_0 = low;
      // Inline function 'kotlin.math.max' call
      var b = high;
      var tmp$ret$1 = Math.max(low_0, b);
      tmp$ret$0 = coerceIn(tmp$ret$1, fromIndex, toIndex - 1 | 0);
    }
    return tmp$ret$0;
  }
  function genericBinarySearch(fromIndex, toIndex, invalid, check) {
    var tmp;
    if (invalid === VOID) {
      tmp = genericBinarySearch$lambda;
    } else {
      tmp = invalid;
    }
    invalid = tmp;
    var low = fromIndex;
    var high = toIndex - 1 | 0;
    while (low <= high) {
      var mid = (low + high | 0) / 2 | 0;
      var mval = check(mid);
      if (mval < 0) {
        low = mid + 1 | 0;
      } else if (mval > 0) {
        high = mid - 1 | 0;
      } else
        return mid;
    }
    return invalid(fromIndex, toIndex, low, high);
  }
  function comparator() {
    var tmp = comparator$lambda;
    return new sam$kotlin_Comparator$0_1(tmp);
  }
  function sam$kotlin_Comparator$0_1(function_0) {
    this.function_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_1).compare_bczr_k$ = function (a, b) {
    return this.function_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_1).compare = function (a, b) {
    return this.compare_bczr_k$(a, b);
  };
  function genericBinarySearch$lambda(from, to, low, high) {
    return (-low | 0) - 1 | 0;
  }
  function comparator$lambda(a, b) {
    return compareTo(a, b);
  }
  function removeSortedDuplicates(_this__u8e3s4) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.removeSortedDuplicates.<anonymous>' call
    $l$block: {
      // Inline function 'korlibs.datastructure.genericRemoveSortedDuplicates' call
      var size = _this__u8e3s4.get_size_woubt6_k$();
      if (size < 2) {
        break $l$block;
      }
      var pivot = 0;
      var ref = 0;
      $l$loop_0: while (true) {
        $l$loop: while (true) {
          var tmp;
          if (ref < size) {
            // Inline function 'korlibs.datastructure.removeSortedDuplicates.<anonymous>.<anonymous>' call
            var x = pivot;
            var y = ref;
            tmp = equals(_this__u8e3s4.get_c1px32_k$(x), _this__u8e3s4.get_c1px32_k$(y));
          } else {
            tmp = false;
          }
          if (!tmp) {
            break $l$loop;
          }
          ref = ref + 1 | 0;
        }
        if (ref >= size)
          break $l$loop_0;
        // Inline function 'korlibs.datastructure.removeSortedDuplicates.<anonymous>.<anonymous>' call
        var src = ref;
        pivot = pivot + 1 | 0;
        var dst = pivot;
        _this__u8e3s4.set_82063s_k$(dst, _this__u8e3s4.get_c1px32_k$(src));
      }
      // Inline function 'korlibs.datastructure.removeSortedDuplicates.<anonymous>.<anonymous>' call
      var size_0 = pivot + 1 | 0;
      while (_this__u8e3s4.get_size_woubt6_k$() > size_0 ? size_0 >= 0 : false) {
        _this__u8e3s4.removeAt_6niowx_k$(_this__u8e3s4.get_size_woubt6_k$() - 1 | 0);
      }
    }
    return _this__u8e3s4;
  }
  function withoutSortedDuplicates(_this__u8e3s4, out) {
    var tmp;
    if (out === VOID) {
      // Inline function 'kotlin.collections.arrayListOf' call
      tmp = ArrayList_init_$Create$_0();
    } else {
      tmp = out;
    }
    out = tmp;
    out.clear_j9egeb_k$();
    out.addAll_4lagoh_k$(_this__u8e3s4);
    removeSortedDuplicates(out);
    return out;
  }
  function genericRemoveSortedDuplicates(size, equals, copy, resize) {
    if (size < 2)
      return Unit_getInstance();
    var pivot = 0;
    var ref = 0;
    $l$loop: while (true) {
      while (ref < size ? equals(pivot, ref) : false) {
        ref = ref + 1 | 0;
      }
      if (ref >= size)
        break $l$loop;
      var tmp = ref;
      pivot = pivot + 1 | 0;
      copy(tmp, pivot);
    }
    resize(pivot + 1 | 0);
  }
  function genericSorted(_this__u8e3s4, left, right) {
    left = left === VOID ? 0 : left;
    right = right === VOID ? _this__u8e3s4.get_size_woubt6_k$() - 1 | 0 : right;
    return genericSort_1(toMutableList(_this__u8e3s4.subList_xle3r2_k$(left, right + 1 | 0)));
  }
  function genericSort(subject, left, right, ops) {
    return genericSort_0(subject, left, right, ops, false);
  }
  function SortOps() {
  }
  protoOf(SortOps).shiftLeft_cw07hy_k$ = function (subject, indexL, indexR) {
    var inductionVariable = indexR;
    var last = indexL + 1 | 0;
    if (last <= inductionVariable)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        this.swap_nx7218_k$(subject, n - 1 | 0, n);
      }
       while (!(n === last));
  };
  protoOf(SortOps).reverse_v7yb7h_k$ = function (subject, indexL, indexR) {
    var count = (indexR - indexL | 0) + 1 | 0;
    var inductionVariable = 0;
    var last = count / 2 | 0;
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        this.swap_nx7218_k$(subject, indexL + n | 0, indexR - n | 0);
      }
       while (inductionVariable < last);
  };
  function timSorted(_this__u8e3s4) {
    // Inline function 'kotlin.also' call
    var this_0 = toMutableList(_this__u8e3s4);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.timSorted.<anonymous>' call
    timSort(this_0);
    return this_0;
  }
  function genericSort_0(subject, left, right, ops, reversed) {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.genericSort.<anonymous>' call
    timSort_0(subject, left, right, ops, reversed);
    return subject;
  }
  function genericSort_1(_this__u8e3s4, left, right) {
    left = left === VOID ? 0 : left;
    right = right === VOID ? _this__u8e3s4.get_size_woubt6_k$() - 1 | 0 : right;
    var tmp = SortOpsComparable_getInstance();
    return genericSort_0(_this__u8e3s4, left, right, tmp instanceof SortOps ? tmp : THROW_CCE(), false);
  }
  function timSort(_this__u8e3s4, left, right) {
    left = left === VOID ? 0 : left;
    right = right === VOID ? _this__u8e3s4.get_size_woubt6_k$() - 1 | 0 : right;
    var tmp = SortOpsComparable_getInstance();
    timSort_0(_this__u8e3s4, left, right, tmp instanceof SortOps ? tmp : THROW_CCE(), false);
    return _this__u8e3s4;
  }
  function timSort_0(arr, l, r, ops, reversed, RUN) {
    RUN = RUN === VOID ? 32 : RUN;
    var n = (r - l | 0) + 1 | 0;
    var progression = step(until(0, n), RUN);
    var inductionVariable = progression.get_first_irdx8n_k$();
    var last = progression.get_last_wopotb_k$();
    var step_0 = progression.get_step_woujh1_k$();
    if ((step_0 > 0 ? inductionVariable <= last : false) ? true : step_0 < 0 ? last <= inductionVariable : false)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + step_0 | 0;
        var tmp = l + i | 0;
        // Inline function 'kotlin.math.min' call
        var a = (i + RUN | 0) - 1 | 0;
        var b = n - 1 | 0;
        var tmp$ret$0 = Math.min(a, b);
        insertionSort(arr, tmp, l + tmp$ret$0 | 0, ops, reversed);
      }
       while (!(i === last));
    var size = RUN;
    while (size < n) {
      var progression_0 = step(until(0, n), imul(2, size));
      var inductionVariable_0 = progression_0.get_first_irdx8n_k$();
      var last_0 = progression_0.get_last_wopotb_k$();
      var step_1 = progression_0.get_step_woujh1_k$();
      if ((step_1 > 0 ? inductionVariable_0 <= last_0 : false) ? true : step_1 < 0 ? last_0 <= inductionVariable_0 : false)
        do {
          var left = inductionVariable_0;
          inductionVariable_0 = inductionVariable_0 + step_1 | 0;
          // Inline function 'kotlin.math.min' call
          var a_0 = size;
          var b_0 = (n - left | 0) - 1 | 0;
          var rize = Math.min(a_0, b_0);
          var mid = (left + rize | 0) - 1 | 0;
          // Inline function 'kotlin.math.min' call
          var a_1 = (left + imul(2, rize) | 0) - 1 | 0;
          var b_1 = n - 1 | 0;
          var right = Math.min(a_1, b_1);
          merge(arr, l + left | 0, l + mid | 0, l + right | 0, ops, reversed);
        }
         while (!(left === last_0));
      size = imul(size, 2);
    }
  }
  function SortOpsComparable() {
    SortOpsComparable_instance = this;
    SortOps.call(this);
  }
  protoOf(SortOpsComparable).compare_33sb67_k$ = function (subject, l, r) {
    return compareTo(subject.get_c1px32_k$(l), subject.get_c1px32_k$(r));
  };
  protoOf(SortOpsComparable).compare_nik356_k$ = function (subject, l, r) {
    return this.compare_33sb67_k$((!(subject == null) ? isInterface(subject, MutableList) : false) ? subject : THROW_CCE(), l, r);
  };
  protoOf(SortOpsComparable).swap_f32567_k$ = function (subject, indexL, indexR) {
    var tmp = subject.get_c1px32_k$(indexL);
    subject.set_82063s_k$(indexL, subject.get_c1px32_k$(indexR));
    subject.set_82063s_k$(indexR, tmp);
  };
  protoOf(SortOpsComparable).swap_nx7218_k$ = function (subject, indexL, indexR) {
    return this.swap_f32567_k$((!(subject == null) ? isInterface(subject, MutableList) : false) ? subject : THROW_CCE(), indexL, indexR);
  };
  var SortOpsComparable_instance;
  function SortOpsComparable_getInstance() {
    if (SortOpsComparable_instance == null)
      new SortOpsComparable();
    return SortOpsComparable_instance;
  }
  function insertionSort(arr, left, right, ops, reversed) {
    var inductionVariable = left + 1 | 0;
    if (inductionVariable <= right)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var m = n - 1 | 0;
        $l$loop: while (m >= left && negateIf(ops.compare_nik356_k$(arr, m, n), reversed) > 0) {
          m = m - 1 | 0;
        }
        m = m + 1 | 0;
        if (!(m === n)) {
          ops.shiftLeft_cw07hy_k$(arr, m, n);
        }
      }
       while (!(n === right));
  }
  function merge(arr, start, mid, end, ops, reversed) {
    var s = start;
    var m = mid;
    var s2 = m + 1 | 0;
    if (negateIf(ops.compare_nik356_k$(arr, m, s2), reversed) <= 0)
      return Unit_getInstance();
    while (s <= m ? s2 <= end : false) {
      if (negateIf(ops.compare_nik356_k$(arr, s, s2), reversed) <= 0) {
        s = s + 1 | 0;
      } else {
        ops.shiftLeft_cw07hy_k$(arr, s, s2);
        s = s + 1 | 0;
        m = m + 1 | 0;
        s2 = s2 + 1 | 0;
      }
    }
  }
  function negateIf(_this__u8e3s4, doNegate) {
    return doNegate ? -_this__u8e3s4 | 0 : _this__u8e3s4;
  }
  function sortedRadix(_this__u8e3s4, start, end, default_0, transform) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? _this__u8e3s4.length : end;
    default_0 = default_0 === VOID ? _Char___init__impl__6a9atx(0) : default_0;
    var tmp;
    if (transform === VOID) {
      tmp = sortedRadix$lambda;
    } else {
      tmp = transform;
    }
    transform = tmp;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.sortedRadix.<anonymous>' call
    sortRadix(this_0, start, end, default_0, transform);
    return this_0;
  }
  function sortedArrayRadix(_this__u8e3s4, start, end, bits) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? _this__u8e3s4.length : end;
    bits = bits === VOID ? 16 : bits;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = _this__u8e3s4.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.sortedArrayRadix.<anonymous>' call
    sortRadix_0(this_0, start, end, bits);
    return this_0;
  }
  function sortRadix(_this__u8e3s4, start, end, default_0, transform) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? _this__u8e3s4.length : end;
    default_0 = default_0 === VOID ? _Char___init__impl__6a9atx(0) : default_0;
    var tmp;
    if (transform === VOID) {
      tmp = sortRadix$lambda;
    } else {
      tmp = transform;
    }
    transform = tmp;
    var tmp$ret$0;
    $l$block: {
      // Inline function 'kotlin.collections.maxOfOrNull' call
      var iterator = until(start, end).iterator_jk1svi_k$();
      if (!iterator.hasNext_bitz1p_k$()) {
        tmp$ret$0 = null;
        break $l$block;
      }
      // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
      var it = iterator.next_20eer_k$();
      var maxValue = charSequenceLength(_this__u8e3s4[it]);
      while (iterator.hasNext_bitz1p_k$()) {
        // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
        var it_0 = iterator.next_20eer_k$();
        var v = charSequenceLength(_this__u8e3s4[it_0]);
        if (compareTo(maxValue, v) < 0) {
          maxValue = v;
        }
      }
      tmp$ret$0 = maxValue;
    }
    var tmp0_elvis_lhs = tmp$ret$0;
    var tmp_0;
    if (tmp0_elvis_lhs == null) {
      return Unit_getInstance();
    } else {
      tmp_0 = tmp0_elvis_lhs;
    }
    var maxLength = tmp_0;
    // Inline function 'kotlin.comparisons.maxOf' call
    var tmp$ret$3;
    $l$block_0: {
      // Inline function 'kotlin.collections.maxOfOrNull' call
      var iterator_0 = until(start, end).iterator_jk1svi_k$();
      if (!iterator_0.hasNext_bitz1p_k$()) {
        tmp$ret$3 = null;
        break $l$block_0;
      }
      // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
      var it_1 = iterator_0.next_20eer_k$();
      // Inline function 'kotlin.code' call
      var this_0 = max(_this__u8e3s4[it_1]);
      var maxValue_0 = Char__toInt_impl_vasixd(this_0);
      while (iterator_0.hasNext_bitz1p_k$()) {
        // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
        var it_2 = iterator_0.next_20eer_k$();
        // Inline function 'kotlin.code' call
        var this_1 = max(_this__u8e3s4[it_2]);
        var v_0 = Char__toInt_impl_vasixd(this_1);
        if (compareTo(maxValue_0, v_0) < 0) {
          maxValue_0 = v_0;
        }
      }
      tmp$ret$3 = maxValue_0;
    }
    var tmp1_elvis_lhs = tmp$ret$3;
    var tmp_1;
    if (tmp1_elvis_lhs == null) {
      return Unit_getInstance();
    } else {
      tmp_1 = tmp1_elvis_lhs;
    }
    var a = tmp_1;
    // Inline function 'kotlin.code' call
    var b = Char__toInt_impl_vasixd(default_0);
    var maxChar = Math.max(a, b);
    // Inline function 'kotlin.arrayOfNulls' call
    var size = end - start | 0;
    var temp = fillArrayVal(Array(size), null);
    // Inline function 'korlibs.datastructure.radixSortGeneric' call
    var noffsets = 1 << usedBits(maxChar);
    var offsets = new Int32Array(noffsets);
    var inductionVariable = 0;
    if (inductionVariable < maxLength)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure._radixSortStep' call
        var inductionVariable_0 = start;
        if (inductionVariable_0 < end)
          do {
            var n_0 = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            // Inline function 'korlibs.datastructure.radixSortGeneric.<anonymous>' call
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            // Inline function 'kotlin.code' call
            // Inline function 'kotlin.text.getOrElse' call
            var this_2 = _this__u8e3s4[n_0];
            var index = (maxLength - 1 | 0) - n | 0;
            var tmp_2;
            if (index >= 0 ? index <= get_lastIndex(this_2) : false) {
              tmp_2 = charSequenceGet(this_2, index);
            } else {
              // Inline function 'korlibs.datastructure.sortRadix.<anonymous>.<anonymous>' call
              tmp_2 = default_0;
            }
            var tmp$ret$13 = tmp_2;
            var this_3 = transform(new Char(tmp$ret$13)).value_1;
            var _index_0__fvwizt = Char__toInt_impl_vasixd(this_3) & 65535;
            offsets[_index_0__fvwizt] = offsets[_index_0__fvwizt] + 1 | 0;
          }
           while (inductionVariable_0 < end);
        var inductionVariable_1 = 1;
        var last = offsets.length;
        if (inductionVariable_1 < last)
          do {
            var i = inductionVariable_1;
            inductionVariable_1 = inductionVariable_1 + 1 | 0;
            offsets[i] = offsets[i] + offsets[i - 1 | 0] | 0;
          }
           while (inductionVariable_1 < last);
        var inductionVariable_2 = start;
        if (inductionVariable_2 < end)
          do {
            var i_0 = inductionVariable_2;
            inductionVariable_2 = inductionVariable_2 + 1 | 0;
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            var v_1 = _this__u8e3s4[(end - 1 | 0) - i_0 | 0];
            // Inline function 'korlibs.datastructure.radixSortGeneric.<anonymous>' call
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            // Inline function 'kotlin.code' call
            // Inline function 'kotlin.text.getOrElse' call
            var index_0 = (maxLength - 1 | 0) - n | 0;
            var tmp_3;
            if (index_0 >= 0 ? index_0 <= get_lastIndex(v_1) : false) {
              tmp_3 = charSequenceGet(v_1, index_0);
            } else {
              // Inline function 'korlibs.datastructure.sortRadix.<anonymous>.<anonymous>' call
              tmp_3 = default_0;
            }
            var tmp$ret$19 = tmp_3;
            var this_4 = transform(new Char(tmp$ret$19)).value_1;
            var index_1 = Char__toInt_impl_vasixd(this_4) & 65535;
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            temp[offsets[index_1] - 1 | 0] = v_1;
            offsets[index_1] = offsets[index_1] - 1 | 0;
          }
           while (inductionVariable_2 < end);
        fill_0(offsets, 0);
        // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
        Memory_getInstance().arraycopy_vhn1fy_k$(temp, 0, _this__u8e3s4, start, temp.length);
      }
       while (inductionVariable < maxLength);
  }
  function sortRadix_0(_this__u8e3s4, start, end, bits) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? _this__u8e3s4.length : end;
    bits = bits === VOID ? 16 : bits;
    var maxBits_0 = maxBits(_this__u8e3s4, start, end);
    // Inline function 'kotlin.math.min' call
    var tmp$ret$0 = Math.min(16, maxBits_0);
    var bits_0 = coerceIn(bits, 1, tmp$ret$0);
    var temp = new Int32Array(end - start | 0);
    var mask = (1 << bits_0) - 1 | 0;
    // Inline function 'korlibs.datastructure.radixSortGeneric' call
    // Inline function 'kotlin.math.ceil' call
    var x = maxBits_0 / bits_0;
    var tmp$ret$1 = Math.ceil(x);
    var stepEnd = numberToInt(tmp$ret$1);
    var noffsets = 1 << bits_0;
    var offsets = new Int32Array(noffsets);
    var inductionVariable = 0;
    if (inductionVariable < stepEnd)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure._radixSortStep' call
        var inductionVariable_0 = start;
        if (inductionVariable_0 < end)
          do {
            var n_0 = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            // Inline function 'korlibs.datastructure.radixSortGeneric.<anonymous>' call
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            var _index_0__fvwizt = (_this__u8e3s4[n_0] >>> imul(n, bits_0) | 0) & mask;
            offsets[_index_0__fvwizt] = offsets[_index_0__fvwizt] + 1 | 0;
          }
           while (inductionVariable_0 < end);
        var inductionVariable_1 = 1;
        var last = offsets.length;
        if (inductionVariable_1 < last)
          do {
            var i = inductionVariable_1;
            inductionVariable_1 = inductionVariable_1 + 1 | 0;
            offsets[i] = offsets[i] + offsets[i - 1 | 0] | 0;
          }
           while (inductionVariable_1 < last);
        var inductionVariable_2 = start;
        if (inductionVariable_2 < end)
          do {
            var i_0 = inductionVariable_2;
            inductionVariable_2 = inductionVariable_2 + 1 | 0;
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            var v = _this__u8e3s4[(end - 1 | 0) - i_0 | 0];
            // Inline function 'korlibs.datastructure.radixSortGeneric.<anonymous>' call
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            var index = (v >>> imul(n, bits_0) | 0) & mask;
            // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
            temp[(offsets[index] - 1 | 0) - start | 0] = v;
            offsets[index] = offsets[index] - 1 | 0;
          }
           while (inductionVariable_2 < end);
        fill_0(offsets, 0);
        // Inline function 'korlibs.datastructure.sortRadix.<anonymous>' call
        Memory_getInstance().arraycopy_7oxh5s_k$(temp, 0, _this__u8e3s4, start, temp.length);
      }
       while (inductionVariable < stepEnd);
  }
  function radixSortGeneric(start, end, stepStart, stepEnd, get, setTemp, flip, getRadix, noffsets) {
    var offsets = new Int32Array(noffsets);
    var inductionVariable = stepStart;
    if (inductionVariable < stepEnd)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure._radixSortStep' call
        var inductionVariable_0 = start;
        if (inductionVariable_0 < end)
          do {
            var n_0 = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            // Inline function 'korlibs.datastructure.radixSortGeneric.<anonymous>' call
            var _index_0__fvwizt = getRadix(n, get(n_0));
            offsets[_index_0__fvwizt] = offsets[_index_0__fvwizt] + 1 | 0;
          }
           while (inductionVariable_0 < end);
        var inductionVariable_1 = 1;
        var last = offsets.length;
        if (inductionVariable_1 < last)
          do {
            var i = inductionVariable_1;
            inductionVariable_1 = inductionVariable_1 + 1 | 0;
            offsets[i] = offsets[i] + offsets[i - 1 | 0] | 0;
          }
           while (inductionVariable_1 < last);
        var inductionVariable_2 = start;
        if (inductionVariable_2 < end)
          do {
            var i_0 = inductionVariable_2;
            inductionVariable_2 = inductionVariable_2 + 1 | 0;
            var v = get((end - 1 | 0) - i_0 | 0);
            // Inline function 'korlibs.datastructure.radixSortGeneric.<anonymous>' call
            var index = getRadix(n, v);
            setTemp(offsets[index] - 1 | 0, v);
            offsets[index] = offsets[index] - 1 | 0;
          }
           while (inductionVariable_2 < end);
        fill_0(offsets, 0);
        flip();
      }
       while (inductionVariable < stepEnd);
  }
  function usedBits(_this__u8e3s4) {
    // Inline function 'korlibs.datastructure.internal.memory.Memory.countLeadingZeros' call
    Memory_getInstance();
    // Inline function 'kotlin.countLeadingZeroBits' call
    return 32 - clz32(_this__u8e3s4) | 0;
  }
  function maxBits(_this__u8e3s4, start, end) {
    start = start === VOID ? 0 : start;
    end = end === VOID ? _this__u8e3s4.length : end;
    var maxNum = _UInt___init__impl__l7qpdl(0);
    var inductionVariable = start;
    if (inductionVariable < end)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'kotlin.math.max' call
        var a = maxNum;
        // Inline function 'kotlin.toUInt' call
        var this_0 = _this__u8e3s4[n];
        var b = _UInt___init__impl__l7qpdl(this_0);
        maxNum = maxOf(a, b);
      }
       while (inductionVariable < end);
    // Inline function 'kotlin.UInt.toInt' call
    var this_1 = maxNum;
    var tmp$ret$2 = _UInt___get_data__impl__f0vqqw(this_1);
    var maxBits = usedBits(tmp$ret$2);
    return maxBits;
  }
  function _radixSortStep(start, end, get, setTemp, getRadix, offsets) {
    var inductionVariable = start;
    if (inductionVariable < end)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var _index_0__fvwizt = getRadix(get(n));
        offsets[_index_0__fvwizt] = offsets[_index_0__fvwizt] + 1 | 0;
      }
       while (inductionVariable < end);
    var inductionVariable_0 = 1;
    var last = offsets.length;
    if (inductionVariable_0 < last)
      do {
        var i = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        offsets[i] = offsets[i] + offsets[i - 1 | 0] | 0;
      }
       while (inductionVariable_0 < last);
    var inductionVariable_1 = start;
    if (inductionVariable_1 < end)
      do {
        var i_0 = inductionVariable_1;
        inductionVariable_1 = inductionVariable_1 + 1 | 0;
        var v = get((end - 1 | 0) - i_0 | 0);
        var index = getRadix(v);
        setTemp(offsets[index] - 1 | 0, v);
        offsets[index] = offsets[index] - 1 | 0;
      }
       while (inductionVariable_1 < end);
  }
  function sortedRadix$lambda(it) {
    return it;
  }
  function sortRadix$lambda(it) {
    return it;
  }
  function _get_MINUS_ZERO_F__392f2l($this) {
    return $this.MINUS_ZERO_F_1;
  }
  function Math_0() {
    Math_instance = this;
    this.MINUS_ZERO_F_1 = -0.0;
  }
  protoOf(Math_0).ilog2_j1avof_k$ = function (v) {
    var tmp;
    if (v === 0) {
      tmp = -1;
    } else {
      // Inline function 'korlibs.datastructure.internal.memory.Memory.countLeadingZeros' call
      Memory_getInstance();
      // Inline function 'kotlin.countLeadingZeroBits' call
      tmp = 31 - clz32(v) | 0;
    }
    return tmp;
  };
  protoOf(Math_0).umod_uagn7p_k$ = function (_this__u8e3s4, other) {
    var rm = _this__u8e3s4 % other | 0;
    var remainder = rm === 0 ? 0 : rm;
    return remainder < 0 ? remainder + other | 0 : remainder;
  };
  protoOf(Math_0).umod_imonk5_k$ = function (_this__u8e3s4, other) {
    var rm = _this__u8e3s4 % other;
    var remainder = rm === -0.0 ? 0.0 : rm;
    return remainder < 0.0 ? remainder + other : remainder;
  };
  protoOf(Math_0).umod_fw7ngl_k$ = function (_this__u8e3s4, other) {
    var rm = _this__u8e3s4 % other;
    var remainder = rm === this.MINUS_ZERO_F_1 ? 0.0 : rm;
    return remainder < 0.0 ? remainder + other : remainder;
  };
  protoOf(Math_0).divCeil_mqwqpw_k$ = function (_this__u8e3s4, that) {
    return !((_this__u8e3s4 % that | 0) === 0) ? (_this__u8e3s4 / that | 0) + 1 | 0 : _this__u8e3s4 / that | 0;
  };
  protoOf(Math_0).ilog2Ceil_kf6ask_k$ = function (v) {
    // Inline function 'kotlin.math.ceil' call
    // Inline function 'kotlin.math.log2' call
    var x = log2(v);
    var tmp$ret$1 = Math.ceil(x);
    return numberToInt(tmp$ret$1);
  };
  var Math_instance;
  function Math_getInstance() {
    if (Math_instance == null)
      new Math_0();
    return Math_instance;
  }
  function isAlmostEquals_1(_this__u8e3s4, other, epsilon) {
    epsilon = epsilon === VOID ? 1.0E-5 : epsilon;
    // Inline function 'kotlin.math.absoluteValue' call
    var this_0 = _this__u8e3s4 - other;
    return Math.abs(this_0) < epsilon;
  }
  function isAlmostEquals_2(_this__u8e3s4, other, epsilon) {
    epsilon = epsilon === VOID ? 1.0E-6 : epsilon;
    // Inline function 'kotlin.math.absoluteValue' call
    var this_0 = _this__u8e3s4 - other;
    return Math.abs(this_0) < epsilon;
  }
  function get_niceStr(_this__u8e3s4) {
    return niceStr(_this__u8e3s4, -1, false);
  }
  function niceStr(_this__u8e3s4, decimalPlaces, zeroSuffix) {
    zeroSuffix = zeroSuffix === VOID ? false : zeroSuffix;
    // Inline function 'kotlin.text.buildString' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.apply' call
    var this_0 = StringBuilder_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.internal.math.niceStr.<anonymous>' call
    appendNice(this_0, roundDecimalPlaces(_this__u8e3s4, decimalPlaces), zeroSuffix ? decimalPlaces > 0 : false);
    return this_0.toString();
  }
  function appendNice(_this__u8e3s4, value, zeroSuffix) {
    zeroSuffix = zeroSuffix === VOID ? false : zeroSuffix;
    if (isAlmostEquals_2(round(value), value))
      if (value >= -2.147483648E9 ? value <= 2.147483647E9 : false)
        _this__u8e3s4.append_uppzia_k$(numberToInt(round(value)));
      else
        _this__u8e3s4.append_8gl4h8_k$(numberToLong(round(value)));
    else {
      _this__u8e3s4.append_jynnak_k$(value);
      return Unit_getInstance();
    }
    if (zeroSuffix) {
      _this__u8e3s4.append_22ad7x_k$('.0');
    }
  }
  function roundDecimalPlaces(_this__u8e3s4, places) {
    if (places < 0)
      return _this__u8e3s4;
    // Inline function 'kotlin.math.pow' call
    var placesFactor = Math.pow(10.0, places);
    return round(_this__u8e3s4 * placesFactor) / placesFactor;
  }
  function Memory() {
    Memory_instance = this;
  }
  protoOf(Memory).arraycopy_vhn1fy_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var destination = isArray(dst) ? dst : THROW_CCE();
    var endIndex = srcPos + size | 0;
    arrayCopy(src, destination, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_ab88sy_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_6o3jwa_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_o9i58a_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_ro9n0m_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_jf5sjq_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_7oxh5s_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_1hjksm_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_wqviw6_k$ = function (src, srcPos, dst, dstPos, size) {
    // Inline function 'kotlin.collections.copyInto' call
    var endIndex = srcPos + size | 0;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = src;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    arrayCopy(tmp, dst, dstPos, srcPos, endIndex);
  };
  protoOf(Memory).arraycopy_i84ysl_k$ = function (src, srcPos, dst, dstPos, size) {
    if (src === dst) {
      // Inline function 'kotlin.error' call
      var message = 'Not supporting the same array';
      throw IllegalStateException_init_$Create$(toString(message));
    }
    var inductionVariable = 0;
    if (inductionVariable < size)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        dst.set_82063s_k$(dstPos + n | 0, src.get_c1px32_k$(srcPos));
      }
       while (inductionVariable < size);
  };
  protoOf(Memory).arraycopy_n91pff_k$ = function (size, src, srcPos, dst, dstPos, setDst, getSrc) {
    var overlapping = src === dst ? dstPos > srcPos : false;
    if (overlapping) {
      var n = size;
      $l$loop: while (true) {
        n = n - 1 | 0;
        if (!(n >= 0)) {
          break $l$loop;
        }
        setDst(dstPos + n | 0, getSrc(srcPos + n | 0));
      }
    } else {
      var inductionVariable = 0;
      if (inductionVariable < size)
        do {
          var n_0 = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          setDst(dstPos + n_0 | 0, getSrc(srcPos + n_0 | 0));
        }
         while (inductionVariable < size);
    }
  };
  protoOf(Memory).countLeadingZeros_qy5l6e_k$ = function (_this__u8e3s4) {
    // Inline function 'kotlin.countLeadingZeroBits' call
    return clz32(_this__u8e3s4);
  };
  var Memory_instance;
  function Memory_getInstance() {
    if (Memory_instance == null)
      new Memory();
    return Memory_instance;
  }
  function FastArrayList_init_$Init$($this) {
    // Inline function 'kotlin.emptyArray' call
    var tmp$ret$0 = [];
    FastArrayList.call($this, tmp$ret$0);
    return $this;
  }
  function FastArrayList_init_$Create$() {
    return FastArrayList_init_$Init$(objectCreate(protoOf(FastArrayList)));
  }
  function FastArrayList_init_$Init$_0(initialCapacity, $this) {
    // Inline function 'kotlin.emptyArray' call
    var tmp$ret$0 = [];
    FastArrayList.call($this, tmp$ret$0);
    return $this;
  }
  function FastArrayList_init_$Create$_0(initialCapacity) {
    return FastArrayList_init_$Init$_0(initialCapacity, objectCreate(protoOf(FastArrayList)));
  }
  function FastArrayList_init_$Init$_1(elements, $this) {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.collections.toTypedArray' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = copyToArray(elements);
    FastArrayList.call($this, tmp$ret$2);
    return $this;
  }
  function FastArrayList_init_$Create$_1(elements) {
    return FastArrayList_init_$Init$_1(elements, objectCreate(protoOf(FastArrayList)));
  }
  function _addAll_1($this, elements) {
    // Inline function 'kotlin.collections.isEmpty' call
    if (elements.length === 0)
      return false;
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$3 = $this.__array_1;
    (function () {
      var $externalVarargReceiverTmp = tmp$ret$3;
      return $externalVarargReceiverTmp.push.apply($externalVarargReceiverTmp, [].slice.call(elements.slice()));
    }.call(this));
    var _unary__edvuaz = $this.get_modCount_sgzjli_k$();
    $this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
    return true;
  }
  function rangeCheck($this, index) {
    if (index < 0 ? true : index >= $this.get_size_woubt6_k$())
      throw IndexOutOfBoundsException_init_$Create$('index: ' + index + ', size: ' + $this.get_size_woubt6_k$());
    return index;
  }
  function insertionRangeCheck($this, index) {
    if (index < 0 ? true : index > $this.get_size_woubt6_k$())
      throw IndexOutOfBoundsException_init_$Create$('index: ' + index + ', size: ' + $this.get_size_woubt6_k$());
    return index;
  }
  function FastArrayList(__array) {
    AbstractMutableList.call(this);
    this.__array_1 = __array;
  }
  protoOf(FastArrayList).get___array_ggax8r_k$ = function () {
    return this.__array_1;
  };
  protoOf(FastArrayList).get_jsArray_rtj12s_k$ = function () {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.__array_1;
  };
  protoOf(FastArrayList).trimToSize_dmxq0i_k$ = function () {
  };
  protoOf(FastArrayList).ensureCapacity_wr7980_k$ = function (minCapacity) {
  };
  protoOf(FastArrayList).get_size_woubt6_k$ = function () {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.__array_1.length;
  };
  protoOf(FastArrayList).get_c1px32_k$ = function (index) {
    // Inline function 'get' call
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.__array_1[rangeCheck(this, index)];
  };
  protoOf(FastArrayList).set_82063s_k$ = function (index, element) {
    rangeCheck(this, index);
    // Inline function 'kotlin.apply' call
    // Inline function 'get' call
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = this.__array_1[index];
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastArrayList.set.<anonymous>' call
    // Inline function 'set' call
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    this.__array_1[index] = element;
    return this_0;
  };
  protoOf(FastArrayList).add_utx5q5_k$ = function (element) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    this.__array_1.push(element);
    var _unary__edvuaz = this.get_modCount_sgzjli_k$();
    this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
    return true;
  };
  protoOf(FastArrayList).add_dl6gt3_k$ = function (index, element) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    this.__array_1.splice(insertionRangeCheck(this, index), 0, element);
    var _unary__edvuaz = this.get_modCount_sgzjli_k$();
    this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
  };
  protoOf(FastArrayList).addAll_6hs4wt_k$ = function (elements) {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$4 = elements.__array_1;
    return _addAll_1(this, tmp$ret$4);
  };
  protoOf(FastArrayList).addAll_4lagoh_k$ = function (elements) {
    // Inline function 'kotlin.collections.toTypedArray' call
    var tmp$ret$0 = copyToArray(elements);
    return _addAll_1(this, tmp$ret$0);
  };
  protoOf(FastArrayList).addAll_lxodh3_k$ = function (index, elements) {
    insertionRangeCheck(this, index);
    if (elements.isEmpty_y1axqb_k$())
      return false;
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = this.__array_1;
    // Inline function 'kotlin.collections.toTypedArray' call
    var tmp$ret$3 = copyToArray(elements);
    (function () {
      var $externalVarargReceiverTmp = tmp;
      return $externalVarargReceiverTmp.splice.apply($externalVarargReceiverTmp, [index, 0].concat([].slice.call(tmp$ret$3.slice())));
    }.call(this));
    var _unary__edvuaz = this.get_modCount_sgzjli_k$();
    this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
    return true;
  };
  protoOf(FastArrayList).removeAt_6niowx_k$ = function (index) {
    rangeCheck(this, index);
    var _unary__edvuaz = this.get_modCount_sgzjli_k$();
    this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
    var tmp;
    if (index === 0) {
      // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      tmp = this.__array_1.shift();
    } else if (index === get_lastIndex_0(this)) {
      // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      tmp = this.__array_1.pop();
    } else {
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.asDynamic' call
      tmp = this.__array_1.splice(index, 1)[0];
    }
    return tmp;
  };
  protoOf(FastArrayList).remove_cedx0m_k$ = function (element) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var array = this.__array_1;
    var inductionVariable = 0;
    var last = array.length;
    if (inductionVariable < last)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'get' call
        // Inline function 'kotlin.js.asDynamic' call
        var tmp$ret$4 = array[index];
        if (equals(tmp$ret$4, element)) {
          array.splice(index, 1);
          var _unary__edvuaz = this.get_modCount_sgzjli_k$();
          this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
          return true;
        }
      }
       while (inductionVariable < last);
    return false;
  };
  protoOf(FastArrayList).removeRange_sm1kzt_k$ = function (fromIndex, toIndex) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    this.__array_1.splice(fromIndex, toIndex - fromIndex | 0);
    var _unary__edvuaz = this.get_modCount_sgzjli_k$();
    this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
  };
  protoOf(FastArrayList).clear_j9egeb_k$ = function () {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    this.__array_1.length = 0;
    var _unary__edvuaz = this.get_modCount_sgzjli_k$();
    this.set_modCount_dsd9nm_k$(_unary__edvuaz + 1 | 0);
  };
  protoOf(FastArrayList).indexOf_si1fv9_k$ = function (element) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.__array_1.indexOf(element);
  };
  protoOf(FastArrayList).lastIndexOf_v2p1fv_k$ = function (element) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.__array_1.lastIndexOf(element);
  };
  protoOf(FastArrayList).toString = function () {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$4 = this.__array_1;
    return '[' + joinToString_1(tmp$ret$4, ', ') + ']';
  };
  protoOf(FastArrayList).toArray_jjyjqa_k$ = function () {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.__array_1.concat();
  };
  protoOf(FastArrayList).toArray = function () {
    return this.toArray_jjyjqa_k$();
  };
  protoOf(FastArrayList).fastForEach_rwxoc_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var array = this.__array_1;
    var n = 0;
    while (n < array.length) {
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'get' call
      var _unary__edvuaz = n;
      n = _unary__edvuaz + 1 | 0;
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.asDynamic' call
      callback(array[_unary__edvuaz]);
    }
  };
  protoOf(FastArrayList).fastForEachWithIndex_uc0x3d_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var array = this.__array_1;
    var n = 0;
    while (n < array.length) {
      var tmp = n;
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'get' call
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.asDynamic' call
      callback(tmp, array[n]);
      n = n + 1 | 0;
    }
  };
  protoOf(FastArrayList).fastForEachReverse_7covhe_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var array = this.__array_1;
    var n = 0;
    while (n < array.length) {
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'get' call
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.asDynamic' call
      callback(array[(this.get_size_woubt6_k$() - n | 0) - 1 | 0]);
      n = n + 1 | 0;
    }
  };
  protoOf(FastArrayList).fastForEachReverseWithIndex_liqy59_k$ = function (callback) {
    // Inline function 'korlibs.datastructure.FastArrayList.jsArray' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var array = this.__array_1;
    var n = 0;
    while (n < array.length) {
      var index = (array.length - n | 0) - 1 | 0;
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'get' call
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.asDynamic' call
      callback(index, array[index]);
      n = n + 1 | 0;
    }
  };
  function FastIdentityMap(dummy) {
  }
  function FastIdentityMap_0() {
    // Inline function 'kotlin.js.asDynamic' call
    return new Map();
  }
  function set_0(_this__u8e3s4, key, value) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.set(key, value);
  }
  function get(_this__u8e3s4, key) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.get(key);
  }
  function get_size_0(_this__u8e3s4) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.size;
  }
  function remove(_this__u8e3s4, key) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.delete(key);
  }
  function FastIntMap(dummy) {
  }
  function FastIntMap_0() {
    // Inline function 'kotlin.js.asDynamic' call
    return new Map();
  }
  function get_size_1(_this__u8e3s4) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.size;
  }
  function set_1(_this__u8e3s4, key, value) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.set(key, value);
  }
  function get_0(_this__u8e3s4, key) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.get(key);
  }
  function remove_0(_this__u8e3s4, key) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.delete(key);
  }
  function removeRange(_this__u8e3s4, src, dst) {
    var _iterator__ex2g4s = get_keys(_this__u8e3s4).iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var key = _iterator__ex2g4s.next_20eer_k$();
      if (src <= key ? key <= dst : false) {
        // Inline function 'korlibs.datastructure.remove' call
        // Inline function 'kotlin.js.asDynamic' call
        _this__u8e3s4.delete(key);
      }
    }
  }
  function clear(_this__u8e3s4) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.clear();
  }
  function FastStringMap(dummy) {
  }
  function FastStringMap_0() {
    // Inline function 'kotlin.js.asDynamic' call
    return new Map();
  }
  function get_size_2(_this__u8e3s4) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.size;
  }
  function set_2(_this__u8e3s4, key, value) {
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.set(key, value);
    return Unit_getInstance();
  }
  function get_1(_this__u8e3s4, key) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.get(key);
  }
  function contains_0(_this__u8e3s4, key) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.has(key);
  }
  function clear_0(_this__u8e3s4) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.clear();
  }
  function remove_1(_this__u8e3s4, key) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.delete(key);
  }
  function fastKeyForEach(_this__u8e3s4, callback) {
    // Inline function 'kotlin.js.asDynamic' call
    var mapIterator = _this__u8e3s4.keys();
    $l$loop: while (true) {
      var v = mapIterator.next();
      if (v.done)
        break $l$loop;
      callback(v.value);
    }
  }
  function WeakMap_0() {
    this.wm_1 = new WeakMap();
  }
  protoOf(WeakMap_0).get_wm_kntnkx_k$ = function () {
    return this.wm_1;
  };
  protoOf(WeakMap_0).contains_7vtym0_k$ = function (key) {
    return this.wm_1.has(key);
  };
  protoOf(WeakMap_0).set_c2d6a0_k$ = function (key, value) {
    if (typeof key === 'string') {
      // Inline function 'kotlin.error' call
      var message = "Can't use String as WeakMap keys";
      throw IllegalStateException_init_$Create$(toString(message));
    }
    this.wm_1.set(key, value);
  };
  protoOf(WeakMap_0).get_h31hzz_k$ = function (key) {
    // Inline function 'kotlin.js.unsafeCast' call
    return this.wm_1.get(key);
  };
  protoOf(WeakMap_0).remove_tgbfrx_k$ = function (key) {
    this.wm_1.delete(key);
  };
  function fastCastTo(_this__u8e3s4) {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4;
  }
  function clear_1(_this__u8e3s4) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4.clear();
  }
  function keys(_this__u8e3s4) {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = Array_from(_this__u8e3s4.keys());
    return toList_0(tmp$ret$2);
  }
  function keys_0(_this__u8e3s4) {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = Array_from(_this__u8e3s4.keys());
    return toList_0(tmp$ret$2);
  }
  function fastKeyForEach_0(_this__u8e3s4, callback) {
    // Inline function 'kotlin.js.asDynamic' call
    var mapIterator = _this__u8e3s4.keys();
    $l$loop: while (true) {
      var v = mapIterator.next();
      if (v.done)
        break $l$loop;
      callback(v.value);
    }
  }
  function keys_1(_this__u8e3s4) {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = Array_from(_this__u8e3s4.keys());
    return toList_0(tmp$ret$2);
  }
  function set_lastIdentityHashCodeId(_set____db54di) {
    _init_properties_InternalJs_kt__82xo06();
    lastIdentityHashCodeId = _set____db54di;
  }
  function get_lastIdentityHashCodeId() {
    _init_properties_InternalJs_kt__82xo06();
    return lastIdentityHashCodeId;
  }
  var lastIdentityHashCodeId;
  function get_IDENTITY_HASH_CODE_SYMBOL() {
    _init_properties_InternalJs_kt__82xo06();
    return IDENTITY_HASH_CODE_SYMBOL;
  }
  var IDENTITY_HASH_CODE_SYMBOL;
  function anyIdentityHashCode(obj) {
    _init_properties_InternalJs_kt__82xo06();
    if (obj == null)
      return 0;
    // Inline function 'kotlin.js.asDynamic' call
    var dyn = obj;
    if (dyn[get_IDENTITY_HASH_CODE_SYMBOL()] === undefined) {
      var _unary__edvuaz = get_lastIdentityHashCodeId();
      set_lastIdentityHashCodeId(_unary__edvuaz + 1 | 0);
      dyn[get_IDENTITY_HASH_CODE_SYMBOL()] = _unary__edvuaz;
    }
    // Inline function 'kotlin.js.unsafeCast' call
    return dyn[get_IDENTITY_HASH_CODE_SYMBOL()];
  }
  var properties_initialized_InternalJs_kt_5zcpco;
  function _init_properties_InternalJs_kt__82xo06() {
    if (!properties_initialized_InternalJs_kt_5zcpco) {
      properties_initialized_InternalJs_kt_5zcpco = true;
      lastIdentityHashCodeId = 0;
      IDENTITY_HASH_CODE_SYMBOL = Symbol('KotlinIdentityHashCode');
    }
  }
  function get_2(_this__u8e3s4, index) {
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4[index];
  }
  function set_3(_this__u8e3s4, index, value) {
    // Inline function 'kotlin.js.asDynamic' call
    _this__u8e3s4[index] = value;
  }
  function Array_from(value) {
    return Array.from(value);
  }
  function get_CONCURRENCY_COUNT() {
    return CONCURRENCY_COUNT;
  }
  var CONCURRENCY_COUNT;
  function parallelForeach(count, block) {
    var inductionVariable = 0;
    if (inductionVariable < count)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        block(n);
      }
       while (inductionVariable < count);
  }
  //region block: post-declaration
  protoOf(Array2).get_size_woubt6_k$ = get_size;
  protoOf(Array2).inside_c6756s_k$ = inside;
  protoOf(Array2).printAt_8yatbk_k$ = printAt;
  protoOf(Array2).setAt_i59r5z_k$ = setAt;
  protoOf(Array2).getAt_1svvdj_k$ = getAt;
  protoOf(Array2).set_pxv0t5_k$ = set;
  protoOf(Array2).contains_aljjnj_k$ = contains;
  protoOf(Array2).getPositionsWithValue_2wymhz_k$ = getPositionsWithValue;
  protoOf(Array2).dump_415nac_k$ = dump;
  protoOf(Array2).toStringList_lj7ccw_k$ = toStringList;
  protoOf(Array2).toStringList$default_og7j7x_k$ = toStringList$default;
  protoOf(Array2).asString_df7bct_k$ = asString;
  protoOf(Array2).asString$default_euc81c_k$ = asString$default;
  protoOf(Array2).asString_iq9yx0_k$ = asString_0;
  protoOf(Array2).asString$default_8p5a7z_k$ = asString$default_0;
  protoOf(Array2).asString_l3f38d_k$ = asString_1;
  protoOf(FloatArray2).get_size_woubt6_k$ = get_size;
  protoOf(FloatArray2).inside_c6756s_k$ = inside;
  protoOf(FloatArray2).printAt_8yatbk_k$ = printAt;
  protoOf(FloatArray2).setAt_i59r5z_k$ = setAt;
  protoOf(FloatArray2).getAt_1svvdj_k$ = getAt;
  protoOf(FloatArray2).set_pxv0t5_k$ = set;
  protoOf(FloatArray2).contains_aljjnj_k$ = contains;
  protoOf(FloatArray2).getPositionsWithValue_2wymhz_k$ = getPositionsWithValue;
  protoOf(FloatArray2).dump_415nac_k$ = dump;
  protoOf(FloatArray2).toStringList_lj7ccw_k$ = toStringList;
  protoOf(FloatArray2).toStringList$default_og7j7x_k$ = toStringList$default;
  protoOf(FloatArray2).asString_df7bct_k$ = asString;
  protoOf(FloatArray2).asString$default_euc81c_k$ = asString$default;
  protoOf(FloatArray2).asString_iq9yx0_k$ = asString_0;
  protoOf(FloatArray2).asString$default_8p5a7z_k$ = asString$default_0;
  protoOf(FloatArray2).asString_l3f38d_k$ = asString_1;
  protoOf(IntArray2).get_size_woubt6_k$ = get_size;
  protoOf(IntArray2).inside_c6756s_k$ = inside;
  protoOf(IntArray2).printAt_8yatbk_k$ = printAt;
  protoOf(IntArray2).setAt_i59r5z_k$ = setAt;
  protoOf(IntArray2).getAt_1svvdj_k$ = getAt;
  protoOf(IntArray2).set_pxv0t5_k$ = set;
  protoOf(IntArray2).contains_aljjnj_k$ = contains;
  protoOf(IntArray2).getPositionsWithValue_2wymhz_k$ = getPositionsWithValue;
  protoOf(IntArray2).dump_415nac_k$ = dump;
  protoOf(IntArray2).toStringList_lj7ccw_k$ = toStringList;
  protoOf(IntArray2).toStringList$default_og7j7x_k$ = toStringList$default;
  protoOf(IntArray2).asString_df7bct_k$ = asString;
  protoOf(IntArray2).asString$default_euc81c_k$ = asString$default;
  protoOf(IntArray2).asString_iq9yx0_k$ = asString_0;
  protoOf(IntArray2).asString$default_8p5a7z_k$ = asString$default_0;
  protoOf(IntArray2).asString_l3f38d_k$ = asString_1;
  protoOf(DoubleArray2).get_size_woubt6_k$ = get_size;
  protoOf(DoubleArray2).inside_c6756s_k$ = inside;
  protoOf(DoubleArray2).printAt_8yatbk_k$ = printAt;
  protoOf(DoubleArray2).setAt_i59r5z_k$ = setAt;
  protoOf(DoubleArray2).getAt_1svvdj_k$ = getAt;
  protoOf(DoubleArray2).set_pxv0t5_k$ = set;
  protoOf(DoubleArray2).contains_aljjnj_k$ = contains;
  protoOf(DoubleArray2).getPositionsWithValue_2wymhz_k$ = getPositionsWithValue;
  protoOf(DoubleArray2).dump_415nac_k$ = dump;
  protoOf(DoubleArray2).toStringList_lj7ccw_k$ = toStringList;
  protoOf(DoubleArray2).toStringList$default_og7j7x_k$ = toStringList$default;
  protoOf(DoubleArray2).asString_df7bct_k$ = asString;
  protoOf(DoubleArray2).asString$default_euc81c_k$ = asString$default;
  protoOf(DoubleArray2).asString_iq9yx0_k$ = asString_0;
  protoOf(DoubleArray2).asString$default_8p5a7z_k$ = asString$default_0;
  protoOf(DoubleArray2).asString_l3f38d_k$ = asString_1;
  protoOf(FloatArrayList).indexOf$default_3pezde_k$ = indexOf$default;
  protoOf(FloatArrayList).lastIndexOf$default_4kni78_k$ = lastIndexOf$default;
  protoOf(FloatArrayList).isAlmostEquals_4iy4pi_k$ = isAlmostEquals;
  protoOf(DoubleArrayList).indexOf$default_n984hg_k$ = indexOf$default_0;
  protoOf(DoubleArrayList).lastIndexOf$default_ppmd6m_k$ = lastIndexOf$default_0;
  protoOf(DoubleArrayList).indexOf_n8fhlp_k$ = indexOf_0;
  protoOf(DoubleArrayList).lastIndexOf_kb0inh_k$ = lastIndexOf_0;
  protoOf(DoubleArrayList).listIterator_xjshxw_k$ = listIterator_0;
  protoOf(DoubleArrayList).isAlmostEquals_t1a1b8_k$ = isAlmostEquals_0;
  protoOf(BaseCacheMap).isEmpty_y1axqb_k$ = isEmpty;
  protoOf(BaseCacheMap).containsKey_aw81wo_k$ = containsKey;
  protoOf(BaseCacheMap).containsValue_yf2ykl_k$ = containsValue;
  protoOf(SortedMap).isEmpty_y1axqb_k$ = isEmpty_0;
  protoOf(SortedMap).putAll_wgg6cj_k$ = putAll_2;
  protoOf(SparseChunkedStackedIntArray2).setFirst_azb6ca_k$ = setFirst;
  protoOf(SparseChunkedStackedIntArray2).getFirst_z3c13a_k$ = getFirst;
  protoOf(SparseChunkedStackedIntArray2).getLast_4ur4dw_k$ = getLast;
  protoOf(StackedIntArray2).setFirst_azb6ca_k$ = setFirst;
  protoOf(StackedIntArray2).getFirst_z3c13a_k$ = getFirst;
  protoOf(StackedIntArray2).getLast_4ur4dw_k$ = getLast;
  protoOf(StackedIntArray2).inside_c6756s_k$ = inside_0;
  protoOf(FastArrayList).addAll_qvfgz7_k$ = addAll_2;
  protoOf(FastArrayList).addAll$default_f0wuda_k$ = addAll$default;
  protoOf(FastArrayList).setAddAll_nj37h9_k$ = setAddAll;
  protoOf(FastArrayList).setAddAll$default_tbb3em_k$ = setAddAll$default;
  protoOf(FastArrayList).setAll_lwy6my_k$ = setAll;
  protoOf(FastArrayList).setAll$default_8bi873_k$ = setAll$default;
  protoOf(FastArrayList).removeToSize_jfdn0o_k$ = removeToSize;
  //endregion
  //region block: init
  CONCURRENCY_COUNT = 1;
  //endregion
  //region block: exports
  _.$_$ = _.$_$ || {};
  _.$_$.a = FastRandom_init_$Create$_1;
  _.$_$.b = FastRandom_init_$Create$_0;
  _.$_$.c = BitArray_init_$Create$;
  _.$_$.d = CaseInsensitiveStringMap_init_$Create$_0;
  _.$_$.e = CaseInsensitiveStringMap_init_$Create$_1;
  _.$_$.f = FastArrayList_init_$Create$_0;
  _.$_$.g = FastArrayList_init_$Create$;
  _.$_$.h = FastArrayList_init_$Create$_1;
  _.$_$.i = IntArrayList_init_$Create$;
  _.$_$.j = IntDeque_init_$Create$;
  _.$_$.k = IntFloatMap_init_$Create$;
  _.$_$.l = IntIntMap_init_$Create$;
  _.$_$.m = IntMap_init_$Create$;
  _.$_$.n = TGenDeque_init_$Create$;
  _.$_$.o = _BSearchResult___get_found__impl__om04iz;
  _.$_$.p = _BSearchResult___get_index__impl__b5kraz;
  _.$_$.q = _BSearchResult___get_nearIndex__impl__s8nq43;
  _.$_$.r = _IntStack___init__impl__t6qo2g;
  _.$_$.s = IntStack__pop_impl_e52gy3;
  _.$_$.t = IntStack__push_impl_ipqpa2;
  _.$_$.u = _IntStack___get_size__impl__uebm0g;
  _.$_$.v = Companion_getInstance_18;
  _.$_$.w = Companion_getInstance_19;
  _.$_$.x = Companion_getInstance_24;
  _.$_$.y = Companion_getInstance;
  _.$_$.z = Companion_getInstance_6;
  _.$_$.a1 = Companion_getInstance_2;
  _.$_$.b1 = Companion_getInstance_0;
  _.$_$.c1 = Companion_getInstance_1;
  _.$_$.d1 = Companion_getInstance_13;
  _.$_$.e1 = Companion_getInstance_15;
  _.$_$.f1 = Companion_getInstance_14;
  _.$_$.g1 = Companion_getInstance_17;
  _.$_$.h1 = Companion_getInstance_12;
  _.$_$.i1 = Historiogram;
  _.$_$.j1 = RLE;
  _.$_$.k1 = ComparatorComparable;
  _.$_$.l1 = get_CONCURRENCY_COUNT;
  _.$_$.m1 = get_extra;
  _.$_$.n1 = nativeThread_0;
  _.$_$.o1 = Array2;
  _.$_$.p1 = BitSet;
  _.$_$.q1 = ByteArrayDeque;
  _.$_$.r1 = CacheMap;
  _.$_$.s1 = ChunkedByteDeque;
  _.$_$.t1 = WithParent;
  _.$_$.u1 = Computed;
  _.$_$.v1 = DoubleArray2;
  _.$_$.w1 = DoubleArrayList;
  _.$_$.x1 = Mixin;
  _.$_$.y1 = PropertyThis;
  _.$_$.z1 = Property;
  _.$_$.a2 = Extra;
  _.$_$.b2 = FastIdentityCacheMap;
  _.$_$.c2 = FastIdentityMap_0;
  _.$_$.d2 = FastIntMap_0;
  _.$_$.e2 = FastSmallSet;
  _.$_$.f2 = FastStringMap_0;
  _.$_$.g2 = FloatArray2;
  _.$_$.h2 = FloatArrayList;
  _.$_$.i2 = GenericSubList;
  _.$_$.j2 = HistoryStack;
  _.$_$.k2 = IndexedTable;
  _.$_$.l2 = IntArray2;
  _.$_$.m2 = IntArrayList;
  _.$_$.n2 = IntDeque;
  _.$_$.o2 = IntQueue;
  _.$_$.p2 = IntStack;
  _.$_$.q2 = LRUCache;
  _.$_$.r2 = Pool;
  _.$_$.s2 = RingBuffer;
  _.$_$.t2 = SortOps;
  _.$_$.u2 = SortedMap;
  _.$_$.v2 = SparseChunkedStackedIntArray2;
  _.$_$.w2 = StackedIntArray2;
  _.$_$.x2 = TGenQueue;
  _.$_$.y2 = WeakMap_0;
  _.$_$.z2 = WeakPropertyThis;
  _.$_$.a3 = WeakProperty;
  _.$_$.b3 = binarySearchLeft;
  _.$_$.c3 = binarySearchRight;
  _.$_$.d3 = binarySearch;
  _.$_$.e3 = countMap;
  _.$_$.f3 = doubleArrayListOf;
  _.$_$.g3 = get_endX;
  _.$_$.h3 = get_endY;
  _.$_$.i3 = expect;
  _.$_$.j3 = fastArrayListOf;
  _.$_$.k3 = flatten;
  _.$_$.l3 = flip;
  _.$_$.m3 = floatArrayListOf;
  _.$_$.n3 = genericSorted;
  _.$_$.o3 = genericSort;
  _.$_$.p3 = getCyclic_4;
  _.$_$.q3 = getCyclic_1;
  _.$_$.r3 = getCyclic_3;
  _.$_$.s3 = getCyclic_2;
  _.$_$.t3 = getCyclic_0;
  _.$_$.u3 = getCyclic;
  _.$_$.v3 = getExtraTyped;
  _.$_$.w3 = getExtra;
  _.$_$.x3 = getFirst_0;
  _.$_$.y3 = getLast_0;
  _.$_$.z3 = get;
  _.$_$.a4 = hasExtra;
  _.$_$.b4 = incr;
  _.$_$.c4 = intArrayListOf;
  _.$_$.d4 = intIntMapOf;
  _.$_$.e4 = intMapOf;
  _.$_$.f4 = intSetOf;
  _.$_$.g4 = get_keys_0;
  _.$_$.h4 = get_keys;
  _.$_$.i4 = linkedHashMapListOf;
  _.$_$.j4 = linkedHashMapOf;
  _.$_$.k4 = memoize;
  _.$_$.l4 = reader;
  _.$_$.m4 = removeSortedDuplicates;
  _.$_$.n4 = remove;
  _.$_$.o4 = reverse_8;
  _.$_$.p4 = rotatedLeft_7;
  _.$_$.q4 = rotatedLeft_6;
  _.$_$.r4 = rotatedLeft_4;
  _.$_$.s4 = rotatedLeft_5;
  _.$_$.t4 = rotatedLeft_0;
  _.$_$.u4 = rotatedLeft_2;
  _.$_$.v4 = rotatedLeft_3;
  _.$_$.w4 = rotatedLeft_1;
  _.$_$.x4 = rotatedLeft;
  _.$_$.y4 = rotatedRight_2;
  _.$_$.z4 = rotatedRight_1;
  _.$_$.a5 = rotatedRight_7;
  _.$_$.b5 = rotatedRight_5;
  _.$_$.c5 = rotatedRight_6;
  _.$_$.d5 = rotatedRight_4;
  _.$_$.e5 = rotatedRight_0;
  _.$_$.f5 = rotatedRight_3;
  _.$_$.g5 = rotatedRight;
  _.$_$.h5 = rotated;
  _.$_$.i5 = setExtra;
  _.$_$.j5 = set_0;
  _.$_$.k5 = get_size_0;
  _.$_$.l5 = get_size_2;
  _.$_$.m5 = get_size_1;
  _.$_$.n5 = slowIdentityHashMapOf;
  _.$_$.o5 = sortedArrayRadix;
  _.$_$.p5 = sortedMapOf;
  _.$_$.q5 = sortedRadix;
  _.$_$.r5 = sort;
  _.$_$.s5 = swap_0;
  _.$_$.t5 = swap;
  _.$_$.u5 = timSorted;
  _.$_$.v5 = toCaseInsensitiveMap;
  _.$_$.w5 = toFastList;
  _.$_$.x5 = toIntArrayList_0;
  _.$_$.y5 = toIntArrayList;
  _.$_$.z5 = toLinkedMap;
  _.$_$.a6 = toMap_0;
  _.$_$.b6 = get_values;
  _.$_$.c6 = get_values_0;
  _.$_$.d6 = values;
  _.$_$.e6 = withoutSortedDuplicates;
  //endregion
  return _;
}));
