(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js', './korge-root-korlibs-datastructure.js', './kotlin-kotlin-test.js', './korge-root-korlibs-concurrent.js', './korge-root-korlibs-platform.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'), require('./korge-root-korlibs-datastructure.js'), require('./kotlin-kotlin-test.js'), require('./korge-root-korlibs-concurrent.js'), require('./korge-root-korlibs-platform.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'com.soywiz.korge:korlibs-datastructure_test'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'com.soywiz.korge:korlibs-datastructure_test'.");
    }
    if (typeof this['korge-root-korlibs-datastructure'] === 'undefined') {
      throw new Error("Error loading module 'com.soywiz.korge:korlibs-datastructure_test'. Its dependency 'korge-root-korlibs-datastructure' was not found. Please, check whether 'korge-root-korlibs-datastructure' is loaded prior to 'com.soywiz.korge:korlibs-datastructure_test'.");
    }
    if (typeof this['kotlin-kotlin-test'] === 'undefined') {
      throw new Error("Error loading module 'com.soywiz.korge:korlibs-datastructure_test'. Its dependency 'kotlin-kotlin-test' was not found. Please, check whether 'kotlin-kotlin-test' is loaded prior to 'com.soywiz.korge:korlibs-datastructure_test'.");
    }
    if (typeof this['korge-root-korlibs-concurrent'] === 'undefined') {
      throw new Error("Error loading module 'com.soywiz.korge:korlibs-datastructure_test'. Its dependency 'korge-root-korlibs-concurrent' was not found. Please, check whether 'korge-root-korlibs-concurrent' is loaded prior to 'com.soywiz.korge:korlibs-datastructure_test'.");
    }
    if (typeof this['korge-root-korlibs-platform'] === 'undefined') {
      throw new Error("Error loading module 'com.soywiz.korge:korlibs-datastructure_test'. Its dependency 'korge-root-korlibs-platform' was not found. Please, check whether 'korge-root-korlibs-platform' is loaded prior to 'com.soywiz.korge:korlibs-datastructure_test'.");
    }
    root['com.soywiz.korge:korlibs-datastructure_test'] = factory(typeof this['com.soywiz.korge:korlibs-datastructure_test'] === 'undefined' ? {} : this['com.soywiz.korge:korlibs-datastructure_test'], this['kotlin-kotlin-stdlib'], this['korge-root-korlibs-datastructure'], this['kotlin-kotlin-test'], this['korge-root-korlibs-concurrent'], this['korge-root-korlibs-platform']);
  }
}(this, function (_, kotlin_kotlin, kotlin_com_soywiz_korge_korlibs_datastructure, kotlin_kotlin_test, kotlin_com_soywiz_korge_korlibs_concurrent, kotlin_com_soywiz_korge_korlibs_platform) {
  'use strict';
  //region block: imports
  var imul = Math.imul;
  var setOf = kotlin_kotlin.$_$.k6;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.z1;
  var Char = kotlin_kotlin.$_$.bc;
  var to = kotlin_kotlin.$_$.ad;
  var mapOf = kotlin_kotlin.$_$.j5;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.l;
  var toMap = kotlin_kotlin.$_$.c7;
  var protoOf = kotlin_kotlin.$_$.x9;
  var initMetadataForObject = kotlin_kotlin.$_$.i9;
  var Companion_getInstance = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.y;
  var lines = kotlin_kotlin.$_$.fb;
  var collectionSizeOrDefault = kotlin_kotlin.$_$.q3;
  var THROW_CCE = kotlin_kotlin.$_$.oc;
  var isCharSequence = kotlin_kotlin.$_$.l9;
  var trim = kotlin_kotlin.$_$.vb;
  var toString = kotlin_kotlin.$_$.ba;
  var startsWith = kotlin_kotlin.$_$.nb;
  var ArrayList_init_$Create$_0 = kotlin_kotlin.$_$.m;
  var charSequenceLength = kotlin_kotlin.$_$.q8;
  var maxOrNull = kotlin_kotlin.$_$.k5;
  var fillArrayVal = kotlin_kotlin.$_$.v8;
  var getOrNull = kotlin_kotlin.$_$.t4;
  var getOrNull_0 = kotlin_kotlin.$_$.cb;
  var isArray = kotlin_kotlin.$_$.k9;
  var Array2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.o1;
  var listOf = kotlin_kotlin.$_$.g5;
  var joinToString = kotlin_kotlin.$_$.z4;
  var assertEquals = kotlin_kotlin_test.$_$.c;
  var Companion_getInstance_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.b1;
  var isFloatArray = kotlin_kotlin.$_$.o9;
  var FloatArray2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.g2;
  var replace = kotlin_kotlin.$_$.kb;
  var until = kotlin_kotlin.$_$.ma;
  var Companion_getInstance_1 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.c1;
  var isIntArray = kotlin_kotlin.$_$.p9;
  var IntArray2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.l2;
  var Companion_getInstance_2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.a1;
  var isDoubleArray = kotlin_kotlin.$_$.n9;
  var DoubleArray2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.v1;
  var numberToInt = kotlin_kotlin.$_$.u9;
  var initMetadataForClass = kotlin_kotlin.$_$.d9;
  var suite = kotlin_kotlin_test.$_$.i;
  var test = kotlin_kotlin_test.$_$.j;
  var Unit_getInstance = kotlin_kotlin.$_$.r2;
  var swap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.t5;
  var toList = kotlin_kotlin.$_$.u6;
  var mutableListOf = kotlin_kotlin.$_$.l5;
  var swap_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.s5;
  var toList_0 = kotlin_kotlin.$_$.y6;
  var rotatedRight = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.g5;
  var rotatedLeft = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.x4;
  var rotatedRight_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.e5;
  var rotatedRight_1 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.z4;
  var toList_1 = kotlin_kotlin.$_$.a7;
  var rotatedRight_2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.y4;
  var charArrayOf = kotlin_kotlin.$_$.n8;
  var rotatedRight_3 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.f5;
  var Char__toInt_impl_vasixd = kotlin_kotlin.$_$.c2;
  var rotatedRight_4 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.d5;
  var Long = kotlin_kotlin.$_$.jc;
  var longArrayOf = kotlin_kotlin.$_$.s9;
  var rotatedRight_5 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.b5;
  var rotatedRight_6 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.c5;
  var rotatedRight_7 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.a5;
  var rotatedLeft_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.t4;
  var rotatedLeft_1 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.w4;
  var rotatedLeft_2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.u4;
  var rotatedLeft_3 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.v4;
  var rotatedLeft_4 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.r4;
  var rotatedLeft_5 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.s4;
  var rotatedLeft_6 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.q4;
  var rotatedLeft_7 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.p4;
  var emptyList = kotlin_kotlin.$_$.m4;
  var intArrayListOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.c4;
  var reverse = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.o4;
  var listOf_0 = kotlin_kotlin.$_$.f5;
  var sort = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.r5;
  var VOID = kotlin_kotlin.$_$.d;
  var rotated = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.h5;
  var IntArrayList = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.m2;
  var FloatArrayList = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.h2;
  var DoubleArrayList = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.w1;
  var coerceAtLeast = kotlin_kotlin.$_$.ha;
  var THROW_IAE = kotlin_kotlin.$_$.pc;
  var getProgressionLastElement = kotlin_kotlin.$_$.e8;
  var IntArrayList_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.i;
  var asSequence = kotlin_kotlin.$_$.sa;
  var toList_2 = kotlin_kotlin.$_$.wa;
  var assertNotEquals = kotlin_kotlin_test.$_$.e;
  var binarySearch = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.d3;
  var _BSearchResult___get_index__impl__b5kraz = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.p;
  var _BSearchResult___get_found__impl__om04iz = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.o;
  var _BSearchResult___get_nearIndex__impl__s8nq43 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.q;
  var binarySearchLeft = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.b3;
  var binarySearchRight = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.c3;
  var Companion_getInstance_3 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.z;
  var BitArray_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.c;
  var booleanArray = kotlin_kotlin.$_$.l8;
  var toList_3 = kotlin_kotlin.$_$.z6;
  var IndexOutOfBoundsException = kotlin_kotlin.$_$.ic;
  var getKClass = kotlin_kotlin.$_$.b;
  var Companion_getInstance_4 = kotlin_kotlin.$_$.q2;
  var _Result___init__impl__xyqfz8 = kotlin_kotlin.$_$.e2;
  var createFailure = kotlin_kotlin.$_$.sc;
  var checkResultIsFailure = kotlin_kotlin_test.$_$.h;
  var taggedArrayCopy = kotlin_kotlin.$_$.c;
  var BitSet = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.p1;
  var booleanArrayOf = kotlin_kotlin.$_$.k8;
  var step = kotlin_kotlin.$_$.la;
  var copyOf = kotlin_kotlin.$_$.e4;
  var ByteArrayDeque = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.q1;
  var toList_4 = kotlin_kotlin.$_$.w6;
  var ensureNotNull = kotlin_kotlin.$_$.tc;
  var KMutableProperty0 = kotlin_kotlin.$_$.na;
  var getPropertyCallableRef = kotlin_kotlin.$_$.a9;
  var CacheMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.r1;
  var Map = kotlin_kotlin.$_$.b3;
  var isInterface = kotlin_kotlin.$_$.q9;
  var toCaseInsensitiveMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.v5;
  var sorted = kotlin_kotlin.$_$.p6;
  var CaseInsensitiveStringMap_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.e;
  var mapOf_0 = kotlin_kotlin.$_$.i5;
  var CaseInsensitiveStringMap_init_$Create$_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.d;
  var ChunkedByteDeque = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.s1;
  var joinToString_0 = kotlin_kotlin.$_$.y4;
  var Computed = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.u1;
  var WithParent = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.t1;
  var KMutableProperty1 = kotlin_kotlin.$_$.oa;
  var KProperty1 = kotlin_kotlin.$_$.qa;
  var TGenDeque_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.n;
  var addAll = kotlin_kotlin.$_$.k3;
  var toMutableList = kotlin_kotlin.$_$.e7;
  var IntDeque_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.j;
  var IntDeque = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.n2;
  var setExtra = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.i5;
  var hasExtra = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.a4;
  var getExtraTyped = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.v3;
  var Mixin = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.x1;
  var Extra = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.a2;
  var assertTrue = kotlin_kotlin_test.$_$.f;
  var LinkedHashSet_init_$Create$ = kotlin_kotlin.$_$.s;
  var Property = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.z1;
  var PropertyThis = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.y1;
  var toMap_0 = kotlin_kotlin.$_$.b7;
  var FastArrayList_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.g;
  var FastArrayList_init_$Create$_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.f;
  var toFastList = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.w5;
  var FastArrayList_init_$Create$_1 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.h;
  var fastArrayListOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.j3;
  var copyToArray = kotlin_kotlin.$_$.j4;
  var get = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.z3;
  var set = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.j5;
  var FastIdentityCacheMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.b2;
  var FastIdentityMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.c2;
  var get_size = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.k5;
  var get_values = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.b6;
  var remove = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.n4;
  var Comparator = kotlin_kotlin.$_$.dc;
  var compareValues = kotlin_kotlin.$_$.j7;
  var FastIntMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.d2;
  var get_size_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.m5;
  var get_keys = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.h4;
  var values = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.d6;
  var FastStringMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.f2;
  var get_size_1 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.l5;
  var get_keys_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.g4;
  var get_values_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.c6;
  var HashMap_init_$Create$ = kotlin_kotlin.$_$.o;
  var sortedWith = kotlin_kotlin.$_$.o6;
  var mapCapacity = kotlin_kotlin.$_$.h5;
  var LinkedHashMap_init_$Create$ = kotlin_kotlin.$_$.q;
  var FastRandom_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.b;
  var checkResultIsFailure_0 = kotlin_kotlin_test.$_$.g;
  var Companion_getInstance_5 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.x;
  var FastRandom_init_$Create$_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.a;
  var distinct = kotlin_kotlin.$_$.k4;
  var random = kotlin_kotlin.$_$.y5;
  var random_0 = kotlin_kotlin.$_$.q5;
  var random_1 = kotlin_kotlin.$_$.t5;
  var charArray = kotlin_kotlin.$_$.o8;
  var random_2 = kotlin_kotlin.$_$.w5;
  var random_3 = kotlin_kotlin.$_$.x5;
  var random_4 = kotlin_kotlin.$_$.s5;
  var longArray = kotlin_kotlin.$_$.t9;
  var random_5 = kotlin_kotlin.$_$.r5;
  var random_6 = kotlin_kotlin.$_$.u5;
  var random_7 = kotlin_kotlin.$_$.v5;
  var println = kotlin_kotlin.$_$.f8;
  var FastSmallSet = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.e2;
  var memoize = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.k4;
  var SortOps = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.t2;
  var compareTo = kotlin_kotlin.$_$.r8;
  var ArrayList = kotlin_kotlin.$_$.v2;
  var genericSorted = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.n3;
  var arrayListOf = kotlin_kotlin.$_$.m3;
  var genericSort = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.o3;
  var GenericSubList = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.i2;
  var HistoryStack = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.j2;
  var IndexedTable = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.k2;
  var IntFloatMap_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.k;
  var IntIntMap_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.l;
  var Random = kotlin_kotlin.$_$.ea;
  var IntMap_init_$Create$ = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.m;
  var sequence = kotlin_kotlin.$_$.ua;
  var Random_0 = kotlin_kotlin.$_$.fa;
  var take = kotlin_kotlin.$_$.va;
  var toSet = kotlin_kotlin.$_$.h7;
  var asSequence_0 = kotlin_kotlin.$_$.o3;
  var IllegalStateException_init_$Create$ = kotlin_kotlin.$_$.f1;
  var take_0 = kotlin_kotlin.$_$.t6;
  var drop = kotlin_kotlin.$_$.l4;
  var intMapOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.e4;
  var toMap_1 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.a6;
  var CoroutineImpl = kotlin_kotlin.$_$.b8;
  var SequenceScope = kotlin_kotlin.$_$.ra;
  var get_COROUTINE_SUSPENDED = kotlin_kotlin.$_$.n7;
  var initMetadataForLambda = kotlin_kotlin.$_$.h9;
  var SuspendFunction1 = kotlin_kotlin.$_$.c8;
  var intSetOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.f4;
  var LRUCache = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.q2;
  var repeat = kotlin_kotlin.$_$.jb;
  var reader = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.l4;
  var expect = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.i3;
  var linkedHashMapListOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.i4;
  var getFirst = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.x3;
  var getLast = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.y3;
  var flatten = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.k3;
  var Pool = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.r2;
  var Companion_getInstance_6 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.h1;
  var addAll_0 = kotlin_kotlin.$_$.j3;
  var Companion_getInstance_7 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.d1;
  var assertFalse = kotlin_kotlin_test.$_$.d;
  var TGenQueue = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.x2;
  var IntQueue = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.o2;
  var toString_0 = kotlin_kotlin.$_$.d2;
  var charSequenceGet = kotlin_kotlin.$_$.p8;
  var compareTo_0 = kotlin_kotlin.$_$.xa;
  var sortedRadix = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.q5;
  var sortedArrayWith = kotlin_kotlin.$_$.m6;
  var assertContentEquals = kotlin_kotlin_test.$_$.b;
  var sortedArrayRadix = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.o5;
  var sortedArray = kotlin_kotlin.$_$.n6;
  var assertContentEquals_0 = kotlin_kotlin_test.$_$.a;
  var removeSortedDuplicates = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.m4;
  var withoutSortedDuplicates = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.e6;
  var RingBuffer = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.s2;
  var hashMapOf = kotlin_kotlin.$_$.u4;
  var slowIdentityHashMapOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.n5;
  var Companion_getInstance_8 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.f1;
  var ComparatorComparable = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.k1;
  var SortedMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.u2;
  var sortedMapOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.p5;
  var SparseChunkedStackedIntArray2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.v2;
  var StackedIntArray2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.w2;
  var Collection = kotlin_kotlin.$_$.w2;
  var get_endX = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.g3;
  var get_endY = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.h3;
  var _IntStack___init__impl__t6qo2g = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.r;
  var _IntStack___get_size__impl__uebm0g = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.u;
  var IntStack__push_impl_ipqpa2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.t;
  var IntStack__pop_impl_e52gy3 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.s;
  var Companion_getInstance_9 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.e1;
  var IntStack = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.p2;
  var Companion_getInstance_10 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.g1;
  var shuffled = kotlin_kotlin.$_$.l6;
  var timSorted = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.u5;
  var WeakMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.y2;
  var WeakProperty = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.a3;
  var WeakPropertyThis = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.z2;
  var intIntMapOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.d4;
  var Companion_getInstance_11 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.v;
  var Historiogram = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.i1;
  var Companion_getInstance_12 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.w;
  var RLE = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.j1;
  var trimIndent = kotlin_kotlin.$_$.ub;
  var flatten_0 = kotlin_kotlin.$_$.s4;
  var getCyclic = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.u3;
  var reversed = kotlin_kotlin.$_$.b6;
  var getCyclic_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.t3;
  var getCyclic_1 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.q3;
  var floatArrayListOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.m3;
  var getCyclic_2 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.s3;
  var doubleArrayListOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.f3;
  var getCyclic_3 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.r3;
  var getCyclic_4 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.p3;
  var linkedHashMapOf = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.j4;
  var toLinkedMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.z5;
  var flip = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.l3;
  var countMap = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.e3;
  var incr = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.b4;
  var toList_5 = kotlin_kotlin.$_$.x6;
  var toList_6 = kotlin_kotlin.$_$.v6;
  var get_CONCURRENCY_COUNT = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.l1;
  var toIntArrayList = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.x5;
  var toIntArrayList_0 = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.y5;
  var Companion_getInstance_13 = kotlin_com_soywiz_korge_korlibs_concurrent.$_$.a;
  var Companion_getInstance_14 = kotlin_kotlin.$_$.o2;
  var DurationUnit_MILLISECONDS_getInstance = kotlin_kotlin.$_$.g;
  var toDuration = kotlin_kotlin.$_$.xb;
  var Lock = kotlin_com_soywiz_korge_korlibs_concurrent.$_$.b;
  var NonRecursiveLock = kotlin_com_soywiz_korge_korlibs_concurrent.$_$.c;
  var Companion_getInstance_15 = kotlin_com_soywiz_korge_korlibs_platform.$_$.a;
  var nativeThread = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.n1;
  var DurationUnit_SECONDS_getInstance = kotlin_kotlin.$_$.j;
  var NativeThread = kotlin_com_soywiz_korge_korlibs_concurrent.$_$.d;
  var get_extra = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.m1;
  var getExtra = kotlin_com_soywiz_korge_korlibs_datastructure.$_$.w3;
  //endregion
  //region block: pre-declaration
  initMetadataForObject(Tiles, 'Tiles');
  initMetadataForClass(Array2Test, 'Array2Test', Array2Test);
  initMetadataForClass(ArrayExtTest, 'ArrayExtTest', ArrayExtTest);
  initMetadataForClass(ArrayListReverseTest, 'ArrayListReverseTest', ArrayListReverseTest);
  initMetadataForClass(ArrayListSortTest, 'ArrayListSortTest', ArrayListSortTest);
  initMetadataForClass(ArrayListTest, 'ArrayListTest', ArrayListTest);
  initMetadataForClass(BinarySearchTest, 'BinarySearchTest', BinarySearchTest);
  initMetadataForClass(BitArrayTest, 'BitArrayTest', BitArrayTest);
  initMetadataForClass(BitSetTest, 'BitSetTest', BitSetTest);
  initMetadataForClass(ByteArrayDequeTest, 'ByteArrayDequeTest', ByteArrayDequeTest);
  initMetadataForClass(CacheLazyNullableTest, 'CacheLazyNullableTest', CacheLazyNullableTest);
  initMetadataForClass(CacheMapTest$test$cache$1, VOID, VOID, CacheMap);
  initMetadataForClass(CacheMapTest, 'CacheMapTest', CacheMapTest);
  initMetadataForClass(CaseInsensitiveStringMapTest, 'CaseInsensitiveStringMapTest', CaseInsensitiveStringMapTest);
  initMetadataForClass(ChunkedByteDequeTest, 'ChunkedByteDequeTest', ChunkedByteDequeTest);
  initMetadataForClass(Format, 'Format', Format, VOID, [WithParent]);
  initMetadataForClass(ComputedTest, 'ComputedTest', ComputedTest);
  initMetadataForClass(DequeTest, 'DequeTest', DequeTest);
  initMetadataForClass(Demo, 'Demo', Demo, VOID, [Extra]);
  initMetadataForClass(ExtraPropertyTest, 'ExtraPropertyTest', ExtraPropertyTest);
  initMetadataForClass(ExtraTestDemo, 'ExtraTestDemo', ExtraTestDemo, VOID, [Extra]);
  initMetadataForClass(ExtraTest, 'ExtraTest', ExtraTest);
  initMetadataForClass(FastArrayListTest, 'FastArrayListTest', FastArrayListTest);
  initMetadataForClass(FastIdentityCacheMapTest, 'FastIdentityCacheMapTest', FastIdentityCacheMapTest);
  initMetadataForClass(FastIdentityMapTest$test$Demo, 'Demo');
  initMetadataForClass(FastIdentityMapTest, 'FastIdentityMapTest', FastIdentityMapTest);
  initMetadataForClass(sam$kotlin_Comparator$0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForClass(sam$kotlin_Comparator$0_0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForClass(FastMapTest, 'FastMapTest', FastMapTest);
  initMetadataForClass(FastRandomTest, 'FastRandomTest', FastRandomTest);
  initMetadataForClass(FastSmallSetTest, 'FastSmallSetTest', FastSmallSetTest);
  initMetadataForClass(FastStringMapTest, 'FastStringMapTest', FastStringMapTest);
  initMetadataForClass(FunctionMemoizeTest, 'FunctionMemoizeTest', FunctionMemoizeTest);
  initMetadataForClass(GenericSortTest$test$result$1, VOID, VOID, SortOps);
  initMetadataForClass(GenericSortTest, 'GenericSortTest', GenericSortTest);
  initMetadataForClass(GenericSubListTest, 'GenericSubListTest', GenericSubListTest);
  initMetadataForClass(HistoryStackTest, 'HistoryStackTest', HistoryStackTest);
  initMetadataForClass(IndexedTableTest, 'IndexedTableTest', IndexedTableTest);
  initMetadataForClass(IntFloatMapTest, 'IntFloatMapTest', IntFloatMapTest);
  initMetadataForClass(IntIntMapTest, 'IntIntMapTest', IntIntMapTest);
  initMetadataForClass(IntMap2JvmTest, 'IntMap2JvmTest', IntMap2JvmTest);
  initMetadataForClass(IntMap2Test, 'IntMap2Test', IntMap2Test);
  initMetadataForClass(sam$kotlin_Comparator$0_1, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForClass(IntMapTest, 'IntMapTest', IntMapTest);
  initMetadataForLambda(intStream$slambda, CoroutineImpl, [CoroutineImpl], [1]);
  initMetadataForLambda(intStream$slambda_1, CoroutineImpl, [CoroutineImpl], [1]);
  initMetadataForClass(IntSetTest, 'IntSetTest', IntSetTest);
  initMetadataForClass(LRUCacheTest, 'LRUCacheTest', LRUCacheTest);
  initMetadataForClass(ListReaderTest, 'ListReaderTest', ListReaderTest);
  initMetadataForClass(MapListTest, 'MapListTest', MapListTest);
  initMetadataForClass(Demo_0, 'Demo', Demo_0);
  initMetadataForClass(PoolTest, 'PoolTest', PoolTest);
  initMetadataForClass(QueueItem, 'QueueItem');
  initMetadataForClass(sam$kotlin_Comparator$0_2, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForClass(PriorityQueueTest$test2$Demo, 'Demo');
  initMetadataForClass(PriorityQueueTest$test4$WI, 'WI');
  initMetadataForClass(PriorityQueueTest$test5$WI, 'WI');
  initMetadataForClass(PriorityQueueTest$test6$WI, 'WI');
  initMetadataForClass(PriorityQueueTest, 'PriorityQueueTest', PriorityQueueTest);
  initMetadataForClass(QueueTest, 'QueueTest', QueueTest);
  initMetadataForClass(sam$kotlin_Comparator$0_3, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForClass(RadixSortTest, 'RadixSortTest', RadixSortTest);
  initMetadataForClass(RemoveSortedDuplicatesTest, 'RemoveSortedDuplicatesTest', RemoveSortedDuplicatesTest);
  initMetadataForClass(RingBufferTest, 'RingBufferTest', RingBufferTest);
  initMetadataForClass(SlowIdentityHashMapTest$test$Test, 'Test');
  initMetadataForClass(SlowIdentityHashMapTest, 'SlowIdentityHashMapTest', SlowIdentityHashMapTest);
  initMetadataForClass(SortedMapTest, 'SortedMapTest', SortedMapTest);
  initMetadataForClass(SparseChunkedStackedIntArray2Test, 'SparseChunkedStackedIntArray2Test', SparseChunkedStackedIntArray2Test);
  initMetadataForClass(StackTest, 'StackTest', StackTest);
  initMetadataForClass(StackedIntArray2Test, 'StackedIntArray2Test', StackedIntArray2Test);
  initMetadataForClass(TimSortTest, 'TimSortTest', TimSortTest);
  initMetadataForClass(WeakMapTest$test$Demo, 'Demo');
  initMetadataForClass(WeakMapTest, 'WeakMapTest', WeakMapTest);
  initMetadataForClass(C, 'C', C);
  initMetadataForClass(WeakPropertyTest, 'WeakPropertyTest', WeakPropertyTest);
  initMetadataForClass(HistoriogramTest, 'HistoriogramTest', HistoriogramTest);
  initMetadataForClass(RLETest, 'RLETest', RLETest);
  initMetadataForClass(GetCyclicTest, 'GetCyclicTest', GetCyclicTest);
  initMetadataForClass(MapExtTest, 'MapExtTest', MapExtTest);
  initMetadataForClass(MapWhileTest, 'MapWhileTest', MapWhileTest);
  initMetadataForClass(FastIteratorsTest, 'FastIteratorsTest', FastIteratorsTest);
  initMetadataForClass(ParallelTest, 'ParallelTest', ParallelTest);
  initMetadataForClass(LockTest, 'LockTest', LockTest);
  initMetadataForClass(ThreadTest, 'ThreadTest', ThreadTest);
  initMetadataForClass(KdsJsTest, 'KdsJsTest', KdsJsTest);
  //endregion
  function Tiles() {
    Tiles_instance = this;
    this.GROUND_1 = 0;
    this.WALL_1 = 1;
    this.CONTAINER_1 = 2;
    this.BOX_1 = 3;
    this.BOX_OVER_1 = 4;
    this.CHARACTER_1 = 10;
    this.AVAILABLE_1 = setOf([0, 2]);
    this.BOXLIKE_1 = setOf([3, 4]);
    this.MAPPING_1 = mapOf([to(new Char(_Char___init__impl__6a9atx(32)), 0), to(new Char(_Char___init__impl__6a9atx(35)), 1), to(new Char(_Char___init__impl__6a9atx(46)), 2), to(new Char(_Char___init__impl__6a9atx(36)), 3), to(new Char(_Char___init__impl__6a9atx(42)), 4), to(new Char(_Char___init__impl__6a9atx(64)), 10)]);
    var tmp = this;
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.MAPPING_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(this_0.get_size_woubt6_k$());
    // Inline function 'kotlin.collections.iterator' call
    var tmp0_iterator = this_0.get_entries_p20ztl_k$().iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Tiles.REV_MAPPING.<anonymous>' call
      var tmp$ret$1 = to(item.get_value_j01efc_k$(), item.get_key_18j28a_k$());
      destination.add_utx5q5_k$(tmp$ret$1);
    }
    tmp.REV_MAPPING_1 = toMap(destination);
  }
  protoOf(Tiles).get_GROUND_1plcvk_k$ = function () {
    return this.GROUND_1;
  };
  protoOf(Tiles).get_WALL_wobleb_k$ = function () {
    return this.WALL_1;
  };
  protoOf(Tiles).get_CONTAINER_n872o8_k$ = function () {
    return this.CONTAINER_1;
  };
  protoOf(Tiles).get_BOX_18jx6m_k$ = function () {
    return this.BOX_1;
  };
  protoOf(Tiles).get_BOX_OVER_ivf2k1_k$ = function () {
    return this.BOX_OVER_1;
  };
  protoOf(Tiles).get_CHARACTER_9fralc_k$ = function () {
    return this.CHARACTER_1;
  };
  protoOf(Tiles).get_AVAILABLE_skgwzk_k$ = function () {
    return this.AVAILABLE_1;
  };
  protoOf(Tiles).get_BOXLIKE_eci1g9_k$ = function () {
    return this.BOXLIKE_1;
  };
  protoOf(Tiles).get_MAPPING_qzojph_k$ = function () {
    return this.MAPPING_1;
  };
  protoOf(Tiles).get_REV_MAPPING_w0n3ih_k$ = function () {
    return this.REV_MAPPING_1;
  };
  var Tiles_instance;
  function Tiles_getInstance() {
    if (Tiles_instance == null)
      new Tiles();
    return Tiles_instance;
  }
  function Array2Test() {
  }
  protoOf(Array2Test).name_ugbo79_k$ = function () {
    // Inline function 'korlibs.datastructure.Companion.fromString' call
    Companion_getInstance();
    var maps = Tiles_getInstance().MAPPING_1;
    var code = '\n\t\t\t:    #####\n\t\t\t:    #   #\n\t\t\t:    #$  #\n\t\t\t:  ###  $##\n\t\t\t:  #  $ $ #\n\t\t\t:### # ## #   ######\n\t\t\t:#   # ## #####  ..#\n\t\t\t:# $  $         *..#\n\t\t\t:##### ### #@##  ..#\n\t\t\t:    #     #########\n\t\t\t:    #######\n\t\t';
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    var marginChar = _Char___init__impl__6a9atx(0);
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
      tmp_2[tmp_3] = tmp0_elvis_lhs_1 == null ? -1 : tmp0_elvis_lhs_1;
      tmp_0 = tmp_0 + 1 | 0;
    }
    var tmp_6 = tmp_2;
    var map = new Array2(width, height, isArray(tmp_6) ? tmp_6 : THROW_CCE());
    var output = map.asString_iq9yx0_k$(Tiles_getInstance().REV_MAPPING_1, ':');
    var expected = joinToString(listOf([':     #####          ', ':     #   #          ', ':     #$  #          ', ':   ###  $##         ', ':   #  $ $ #         ', ': ### # ## #   ######', ': #   # ## #####  ..#', ': # $  $         *..#', ': ##### ### #@##  ..#', ':     #     #########', ':     #######        ']), '\n');
    assertEquals(expected, output);
  };
  protoOf(Array2Test).test2_6xsqgg_k$ = function () {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp = 0;
    var tmp_0 = imul(5, 5);
    var tmp_1 = new Float32Array(tmp_0);
    while (tmp < tmp_0) {
      tmp_1[tmp] = 100.0;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    var map = new FloatArray2(5, 5, isFloatArray(tmp_2) ? tmp_2 : THROW_CCE());
    // Inline function 'korlibs.datastructure.each' call
    var inductionVariable = 0;
    var last = map.get_height_e7t92o_k$();
    if (inductionVariable < last)
      do {
        var y = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var inductionVariable_0 = 0;
        var last_0 = map.get_width_j0q4yl_k$();
        if (inductionVariable_0 < last_0)
          do {
            var x = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            // Inline function 'korlibs.datastructure.Array2Test.test2.<anonymous>' call
            var v = map.getAt_1svvdj_k$(x, y);
            map.set_hk1aii_k$(x, y, v + (imul(x, 10) + y | 0));
          }
           while (inductionVariable_0 < last_0);
      }
       while (inductionVariable < last);
    assertEquals('100, 110, 120, 130, 140\n101, 111, 121, 131, 141\n102, 112, 122, 132, 142\n103, 113, 123, 133, 143\n104, 114, 124, 134, 144', replace(map.toString(), '.0', ''));
  };
  protoOf(Array2Test).test3_ffxdzl_k$ = function () {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp = 0;
    var tmp_0 = imul(5, 5);
    var tmp_1 = new Float32Array(tmp_0);
    while (tmp < tmp_0) {
      tmp_1[tmp] = 100.0;
      tmp = tmp + 1 | 0;
    }
    var tmp_2 = tmp_1;
    var tmp_3 = new FloatArray2(5, 5, isFloatArray(tmp_2) ? tmp_2 : THROW_CCE());
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_4 = 0;
    var tmp_5 = imul(5, 5);
    var tmp_6 = new Float32Array(tmp_5);
    while (tmp_4 < tmp_5) {
      // Inline function 'korlibs.datastructure.Array2Test.test3.<anonymous>' call
      tmp_6[tmp_4] = 100.0;
      tmp_4 = tmp_4 + 1 | 0;
    }
    var tmp_7 = tmp_6;
    var tmp$ret$4 = new FloatArray2(5, 5, isFloatArray(tmp_7) ? tmp_7 : THROW_CCE());
    assertEquals(tmp_3, tmp$ret$4);
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_8 = 0;
    var tmp_9 = imul(5, 5);
    var tmp_10 = new Float32Array(tmp_9);
    while (tmp_8 < tmp_9) {
      tmp_10[tmp_8] = 100.0;
      tmp_8 = tmp_8 + 1 | 0;
    }
    var tmp_11 = tmp_10;
    var tmp_12 = new FloatArray2(5, 5, isFloatArray(tmp_11) ? tmp_11 : THROW_CCE());
    // Inline function 'korlibs.datastructure.Companion.withGen' call
    Companion_getInstance_0();
    var tmp_13 = 0;
    var tmp_14 = imul(5, 5);
    var tmp_15 = new Float32Array(tmp_14);
    while (tmp_13 < tmp_14) {
      // Inline function 'korlibs.datastructure.Array2Test.test3.<anonymous>' call
      tmp_15[tmp_13] = 100.0;
      tmp_13 = tmp_13 + 1 | 0;
    }
    var tmp_16 = tmp_15;
    var tmp$ret$9 = new FloatArray2(5, 5, isFloatArray(tmp_16) ? tmp_16 : THROW_CCE());
    assertEquals(tmp_12, tmp$ret$9);
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_17 = 0;
    var tmp_18 = imul(5, 5);
    var tmp_19 = new Float32Array(tmp_18);
    while (tmp_17 < tmp_18) {
      tmp_19[tmp_17] = 100.0;
      tmp_17 = tmp_17 + 1 | 0;
    }
    var tmp_20 = tmp_19;
    var tmp_21 = new FloatArray2(5, 5, isFloatArray(tmp_20) ? tmp_20 : THROW_CCE());
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, 5);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.Array2Test.test3.<anonymous>' call
      // Inline function 'kotlin.collections.map' call
      var this_1 = until(0, 5);
      // Inline function 'kotlin.collections.mapTo' call
      var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(this_1, 10));
      var tmp0_iterator_0 = this_1.iterator_jk1svi_k$();
      while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
        var item_0 = tmp0_iterator_0.next_20eer_k$();
        // Inline function 'korlibs.datastructure.Array2Test.test3.<anonymous>.<anonymous>' call
        destination_0.add_utx5q5_k$(100.0);
      }
      destination.add_utx5q5_k$(destination_0);
    }
    var width = destination.get_c1px32_k$(0).get_size_woubt6_k$();
    var height = destination.get_size_woubt6_k$();
    var anyCell = destination.get_c1px32_k$(0).get_c1px32_k$(0);
    // Inline function 'kotlin.apply' call
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_22 = 0;
    var tmp_23 = imul(width, height);
    var tmp_24 = new Float32Array(tmp_23);
    while (tmp_22 < tmp_23) {
      // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
      tmp_24[tmp_22] = anyCell;
      tmp_22 = tmp_22 + 1 | 0;
    }
    var tmp_25 = tmp_24;
    var this_2 = new FloatArray2(width, height, isFloatArray(tmp_25) ? tmp_25 : THROW_CCE());
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    this_2.set_pxv0t5_k$(destination);
    assertEquals(tmp_21, this_2);
  };
  protoOf(Array2Test).eachWorks_j9zju9_k$ = function () {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_1();
    var tmp = 0;
    var tmp_0 = imul(2, 2);
    var tmp_1 = new Int32Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
      tmp_1[tmp_2] = tmp_2;
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    var intArray2 = new IntArray2(2, 2, isIntArray(tmp_3) ? tmp_3 : THROW_CCE());
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_0();
    var tmp_4 = 0;
    var tmp_5 = imul(2, 2);
    var tmp_6 = new Float32Array(tmp_5);
    while (tmp_4 < tmp_5) {
      var tmp_7 = tmp_4;
      // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
      tmp_6[tmp_7] = tmp_7;
      tmp_4 = tmp_4 + 1 | 0;
    }
    var tmp_8 = tmp_6;
    var floatArray2 = new FloatArray2(2, 2, isFloatArray(tmp_8) ? tmp_8 : THROW_CCE());
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_2();
    var tmp_9 = 0;
    var tmp_10 = imul(2, 2);
    var tmp_11 = new Float64Array(tmp_10);
    while (tmp_9 < tmp_10) {
      var tmp_12 = tmp_9;
      // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
      tmp_11[tmp_12] = tmp_12;
      tmp_9 = tmp_9 + 1 | 0;
    }
    var tmp_13 = tmp_11;
    var doubleArray2 = new DoubleArray2(2, 2, isDoubleArray(tmp_13) ? tmp_13 : THROW_CCE());
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance();
    var tmp_14 = 0;
    var tmp_15 = imul(2, 2);
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_16 = fillArrayVal(Array(tmp_15), null);
    while (tmp_14 < tmp_15) {
      var tmp_17 = tmp_14;
      // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
      tmp_16[tmp_17] = (tmp_17 % 2 | 0) === 1;
      tmp_14 = tmp_14 + 1 | 0;
    }
    var tmp_18 = tmp_16;
    var typedArray2 = new Array2(2, 2, isArray(tmp_18) ? tmp_18 : THROW_CCE());
    // Inline function 'kotlin.collections.arrayListOf' call
    var out = ArrayList_init_$Create$_0();
    // Inline function 'korlibs.datastructure.each' call
    var inductionVariable = 0;
    var last = intArray2.get_height_e7t92o_k$();
    if (inductionVariable < last)
      do {
        var y = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var inductionVariable_0 = 0;
        var last_0 = intArray2.get_width_j0q4yl_k$();
        if (inductionVariable_0 < last_0)
          do {
            var x = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
            var v = intArray2.getAt_1svvdj_k$(x, y);
            out.add_utx5q5_k$('x: ' + x + ', y: ' + y + ', v: ' + v);
          }
           while (inductionVariable_0 < last_0);
      }
       while (inductionVariable < last);
    // Inline function 'korlibs.datastructure.each' call
    var inductionVariable_1 = 0;
    var last_1 = floatArray2.get_height_e7t92o_k$();
    if (inductionVariable_1 < last_1)
      do {
        var y_0 = inductionVariable_1;
        inductionVariable_1 = inductionVariable_1 + 1 | 0;
        var inductionVariable_2 = 0;
        var last_2 = floatArray2.get_width_j0q4yl_k$();
        if (inductionVariable_2 < last_2)
          do {
            var x_0 = inductionVariable_2;
            inductionVariable_2 = inductionVariable_2 + 1 | 0;
            // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
            var v_0 = floatArray2.getAt_1svvdj_k$(x_0, y_0);
            out.add_utx5q5_k$('x: ' + x_0 + ', y: ' + y_0 + ', v: ' + numberToInt(v_0));
          }
           while (inductionVariable_2 < last_2);
      }
       while (inductionVariable_1 < last_1);
    // Inline function 'korlibs.datastructure.each' call
    var inductionVariable_3 = 0;
    var last_3 = doubleArray2.get_height_e7t92o_k$();
    if (inductionVariable_3 < last_3)
      do {
        var y_1 = inductionVariable_3;
        inductionVariable_3 = inductionVariable_3 + 1 | 0;
        var inductionVariable_4 = 0;
        var last_4 = doubleArray2.get_width_j0q4yl_k$();
        if (inductionVariable_4 < last_4)
          do {
            var x_1 = inductionVariable_4;
            inductionVariable_4 = inductionVariable_4 + 1 | 0;
            // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
            var v_1 = doubleArray2.getAt_1svvdj_k$(x_1, y_1);
            out.add_utx5q5_k$('x: ' + x_1 + ', y: ' + y_1 + ', v: ' + numberToInt(v_1));
          }
           while (inductionVariable_4 < last_4);
      }
       while (inductionVariable_3 < last_3);
    // Inline function 'korlibs.datastructure.each' call
    var inductionVariable_5 = 0;
    var last_5 = typedArray2.get_height_e7t92o_k$();
    if (inductionVariable_5 < last_5)
      do {
        var y_2 = inductionVariable_5;
        inductionVariable_5 = inductionVariable_5 + 1 | 0;
        var inductionVariable_6 = 0;
        var last_6 = typedArray2.get_width_j0q4yl_k$();
        if (inductionVariable_6 < last_6)
          do {
            var x_2 = inductionVariable_6;
            inductionVariable_6 = inductionVariable_6 + 1 | 0;
            // Inline function 'korlibs.datastructure.Array2Test.eachWorks.<anonymous>' call
            var v_2 = typedArray2.getAt_1svvdj_k$(x_2, y_2);
            out.add_utx5q5_k$('x: ' + x_2 + ', y: ' + y_2 + ', v: ' + v_2);
          }
           while (inductionVariable_6 < last_6);
      }
       while (inductionVariable_5 < last_5);
    assertEquals('x: 0, y: 0, v: 0\nx: 1, y: 0, v: 1\nx: 0, y: 1, v: 2\nx: 1, y: 1, v: 3\nx: 0, y: 0, v: 0\nx: 1, y: 0, v: 1\nx: 0, y: 1, v: 2\nx: 1, y: 1, v: 3\nx: 0, y: 0, v: 0\nx: 1, y: 0, v: 1\nx: 0, y: 1, v: 2\nx: 1, y: 1, v: 3\nx: 0, y: 0, v: false\nx: 1, y: 0, v: true\nx: 0, y: 1, v: false\nx: 1, y: 1, v: true', joinToString(out, '\n'));
  };
  function test_fun_izoufj() {
    suite('Array2Test', false, test_fun$Array2Test_test_fun_9o93wt);
  }
  function test_fun$Array2Test_test_fun_9o93wt() {
    test('name', false, test_fun$Array2Test_test_fun$name_test_fun_a4syyz);
    test('test2', false, test_fun$Array2Test_test_fun$test2_test_fun_gkqvwi);
    test('test3', false, test_fun$Array2Test_test_fun$test3_test_fun_dbqx41);
    test('eachWorks', false, test_fun$Array2Test_test_fun$eachWorks_test_fun_wlszgf);
    return Unit_getInstance();
  }
  function test_fun$Array2Test_test_fun$name_test_fun_a4syyz() {
    var tmp = new Array2Test();
    tmp.name_ugbo79_k$();
    return Unit_getInstance();
  }
  function test_fun$Array2Test_test_fun$test2_test_fun_gkqvwi() {
    var tmp = new Array2Test();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function test_fun$Array2Test_test_fun$test3_test_fun_dbqx41() {
    var tmp = new Array2Test();
    tmp.test3_ffxdzl_k$();
    return Unit_getInstance();
  }
  function test_fun$Array2Test_test_fun$eachWorks_test_fun_wlszgf() {
    var tmp = new Array2Test();
    tmp.eachWorks_j9zju9_k$();
    return Unit_getInstance();
  }
  function ArrayExtTest() {
    var tmp = this;
    // Inline function 'kotlin.intArrayOf' call
    tmp.array_1 = new Int32Array([1, 2, 3, 4]);
  }
  protoOf(ArrayExtTest).get_array_iosoq8_k$ = function () {
    return this.array_1;
  };
  protoOf(ArrayExtTest).testSwap_nloryj_k$ = function () {
    var tmp = listOf([4, 2, 3, 1]);
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = this.array_1.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayExtTest.testSwap.<anonymous>' call
    swap(this_0, 0, 3);
    assertEquals(tmp, toList(this_0));
    var tmp_0 = listOf([1, 3, 2, 4]);
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_1 = this.array_1.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayExtTest.testSwap.<anonymous>' call
    swap(this_1, 1, 2);
    assertEquals(tmp_0, toList(this_1));
    var tmp_1 = listOf([3, 2, 1, 4]);
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_2 = this.array_1.slice();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayExtTest.testSwap.<anonymous>' call
    swap(this_2, 0, 2);
    assertEquals(tmp_1, toList(this_2));
    var tmp_2 = listOf([3, 2, 1, 4]);
    // Inline function 'kotlin.also' call
    var this_3 = mutableListOf([1, 2, 3, 4]);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayExtTest.testSwap.<anonymous>' call
    swap_0(this_3, 0, 2);
    assertEquals(tmp_2, toList_0(this_3));
  };
  protoOf(ArrayExtTest).testRotatedRight_lmqfhn_k$ = function () {
    assertEquals(listOf([4, 1, 2, 3]), toList(rotatedRight(this.array_1, 1)));
    assertEquals(listOf([3, 4, 1, 2]), toList(rotatedRight(this.array_1, 2)));
    assertEquals(listOf([2, 3, 4, 1]), toList(rotatedRight(this.array_1, 3)));
    assertEquals(listOf([1, 2, 3, 4]), toList(rotatedRight(this.array_1, 4)));
    assertEquals(listOf([1, 2, 3, 4]), toList(rotatedRight(this.array_1, 0)));
    assertEquals(listOf([2, 3, 4, 1]), toList(rotatedRight(this.array_1, -1)));
    assertEquals(listOf([3, 4, 1, 2]), toList(rotatedRight(this.array_1, -2)));
    assertEquals(listOf([4, 1, 2, 3]), toList(rotatedRight(this.array_1, -3)));
    assertEquals(listOf([1, 2, 3, 4]), toList(rotatedRight(this.array_1, -4)));
  };
  protoOf(ArrayExtTest).testRotatedLeft_3jffzm_k$ = function () {
    assertEquals(listOf([4, 1, 2, 3]), toList(rotatedLeft(this.array_1, -1)));
    assertEquals(listOf([3, 4, 1, 2]), toList(rotatedLeft(this.array_1, -2)));
    assertEquals(listOf([2, 3, 4, 1]), toList(rotatedLeft(this.array_1, -3)));
    assertEquals(listOf([1, 2, 3, 4]), toList(rotatedLeft(this.array_1, -4)));
    assertEquals(listOf([1, 2, 3, 4]), toList(rotatedLeft(this.array_1, 0)));
    assertEquals(listOf([2, 3, 4, 1]), toList(rotatedLeft(this.array_1, 1)));
    assertEquals(listOf([3, 4, 1, 2]), toList(rotatedLeft(this.array_1, 2)));
    assertEquals(listOf([4, 1, 2, 3]), toList(rotatedLeft(this.array_1, 3)));
    assertEquals(listOf([1, 2, 3, 4]), toList(rotatedLeft(this.array_1, 4)));
  };
  protoOf(ArrayExtTest).testVariants_ocqlio_k$ = function () {
    assertEquals(listOf([3, 4, 1, 2]), toList_0(rotatedRight_0(listOf([1, 2, 3, 4]), -2)));
    var tmp = listOf([3, 4, 1, 2]);
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = [1, 2, 3, 4];
    assertEquals(tmp, toList_1(rotatedRight_1(tmp$ret$2, -2)));
    var tmp_0 = listOf([3, 4, 1, 2]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$3 = new Int8Array([1, 2, 3, 4]);
    var this_0 = rotatedRight_2(tmp$ret$3, -2);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(this_0.length);
    var inductionVariable = 0;
    var last = this_0.length;
    while (inductionVariable < last) {
      var item = this_0[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      destination.add_utx5q5_k$(item);
    }
    assertEquals(tmp_0, toList_0(destination));
    var tmp_1 = listOf([3, 4, 1, 2]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.charArrayOf' call
    var tmp$ret$7 = charArrayOf([_Char___init__impl__6a9atx(1), _Char___init__impl__6a9atx(2), _Char___init__impl__6a9atx(3), _Char___init__impl__6a9atx(4)]);
    var this_1 = rotatedRight_3(tmp$ret$7, -2);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(this_1.length);
    var inductionVariable_0 = 0;
    var last_0 = this_1.length;
    while (inductionVariable_0 < last_0) {
      var item_0 = this_1[inductionVariable_0];
      inductionVariable_0 = inductionVariable_0 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$8 = Char__toInt_impl_vasixd(item_0);
      destination_0.add_utx5q5_k$(tmp$ret$8);
    }
    assertEquals(tmp_1, toList_0(destination_0));
    var tmp_2 = listOf([3, 4, 1, 2]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.shortArrayOf' call
    var tmp$ret$11 = new Int16Array([1, 2, 3, 4]);
    var this_2 = rotatedRight_4(tmp$ret$11, -2);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(this_2.length);
    var inductionVariable_1 = 0;
    var last_1 = this_2.length;
    while (inductionVariable_1 < last_1) {
      var item_1 = this_2[inductionVariable_1];
      inductionVariable_1 = inductionVariable_1 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      destination_1.add_utx5q5_k$(item_1);
    }
    assertEquals(tmp_2, toList_0(destination_1));
    var tmp_3 = listOf([3, 4, 1, 2]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.longArrayOf' call
    var tmp$ret$15 = longArrayOf([new Long(1, 0), new Long(2, 0), new Long(3, 0), new Long(4, 0)]);
    var this_3 = rotatedRight_5(tmp$ret$15, -2);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_2 = ArrayList_init_$Create$(this_3.length);
    var inductionVariable_2 = 0;
    var last_2 = this_3.length;
    while (inductionVariable_2 < last_2) {
      var item_2 = this_3[inductionVariable_2];
      inductionVariable_2 = inductionVariable_2 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$16 = item_2.toInt_1tsl84_k$();
      destination_2.add_utx5q5_k$(tmp$ret$16);
    }
    assertEquals(tmp_3, toList_0(destination_2));
    var tmp_4 = listOf([3, 4, 1, 2]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.floatArrayOf' call
    var tmp$ret$19 = new Float32Array([1.0, 2.0, 3.0, 4.0]);
    var this_4 = rotatedRight_6(tmp$ret$19, -2);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_3 = ArrayList_init_$Create$(this_4.length);
    var inductionVariable_3 = 0;
    var last_3 = this_4.length;
    while (inductionVariable_3 < last_3) {
      var item_3 = this_4[inductionVariable_3];
      inductionVariable_3 = inductionVariable_3 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$20 = numberToInt(item_3);
      destination_3.add_utx5q5_k$(tmp$ret$20);
    }
    assertEquals(tmp_4, toList_0(destination_3));
    var tmp_5 = listOf([3, 4, 1, 2]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.doubleArrayOf' call
    var tmp$ret$23 = new Float64Array([1.0, 2.0, 3.0, 4.0]);
    var this_5 = rotatedRight_7(tmp$ret$23, -2);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_4 = ArrayList_init_$Create$(this_5.length);
    var inductionVariable_4 = 0;
    var last_4 = this_5.length;
    while (inductionVariable_4 < last_4) {
      var item_4 = this_5[inductionVariable_4];
      inductionVariable_4 = inductionVariable_4 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$24 = numberToInt(item_4);
      destination_4.add_utx5q5_k$(tmp$ret$24);
    }
    assertEquals(tmp_5, toList_0(destination_4));
    assertEquals(listOf([4, 1, 2, 3]), toList(rotatedLeft(this.array_1, -1)));
    assertEquals(listOf([4, 1, 2, 3]), toList_0(rotatedLeft_0(listOf([1, 2, 3, 4]), -1)));
    var tmp_6 = listOf([4, 1, 2, 3]);
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$29 = [1, 2, 3, 4];
    assertEquals(tmp_6, toList_1(rotatedLeft_1(tmp$ret$29, -1)));
    var tmp_7 = listOf([4, 1, 2, 3]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$30 = new Int8Array([1, 2, 3, 4]);
    var this_6 = rotatedLeft_2(tmp$ret$30, -1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_5 = ArrayList_init_$Create$(this_6.length);
    var inductionVariable_5 = 0;
    var last_5 = this_6.length;
    while (inductionVariable_5 < last_5) {
      var item_5 = this_6[inductionVariable_5];
      inductionVariable_5 = inductionVariable_5 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      destination_5.add_utx5q5_k$(item_5);
    }
    assertEquals(tmp_7, toList_0(destination_5));
    var tmp_8 = listOf([4, 1, 2, 3]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.charArrayOf' call
    var tmp$ret$34 = charArrayOf([_Char___init__impl__6a9atx(1), _Char___init__impl__6a9atx(2), _Char___init__impl__6a9atx(3), _Char___init__impl__6a9atx(4)]);
    var this_7 = rotatedLeft_3(tmp$ret$34, -1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_6 = ArrayList_init_$Create$(this_7.length);
    var inductionVariable_6 = 0;
    var last_6 = this_7.length;
    while (inductionVariable_6 < last_6) {
      var item_6 = this_7[inductionVariable_6];
      inductionVariable_6 = inductionVariable_6 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$35 = Char__toInt_impl_vasixd(item_6);
      destination_6.add_utx5q5_k$(tmp$ret$35);
    }
    assertEquals(tmp_8, toList_0(destination_6));
    var tmp_9 = listOf([4, 1, 2, 3]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.shortArrayOf' call
    var tmp$ret$38 = new Int16Array([1, 2, 3, 4]);
    var this_8 = rotatedLeft_4(tmp$ret$38, -1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_7 = ArrayList_init_$Create$(this_8.length);
    var inductionVariable_7 = 0;
    var last_7 = this_8.length;
    while (inductionVariable_7 < last_7) {
      var item_7 = this_8[inductionVariable_7];
      inductionVariable_7 = inductionVariable_7 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      destination_7.add_utx5q5_k$(item_7);
    }
    assertEquals(tmp_9, toList_0(destination_7));
    var tmp_10 = listOf([4, 1, 2, 3]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.longArrayOf' call
    var tmp$ret$42 = longArrayOf([new Long(1, 0), new Long(2, 0), new Long(3, 0), new Long(4, 0)]);
    var this_9 = rotatedLeft_5(tmp$ret$42, -1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_8 = ArrayList_init_$Create$(this_9.length);
    var inductionVariable_8 = 0;
    var last_8 = this_9.length;
    while (inductionVariable_8 < last_8) {
      var item_8 = this_9[inductionVariable_8];
      inductionVariable_8 = inductionVariable_8 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$43 = item_8.toInt_1tsl84_k$();
      destination_8.add_utx5q5_k$(tmp$ret$43);
    }
    assertEquals(tmp_10, toList_0(destination_8));
    var tmp_11 = listOf([4, 1, 2, 3]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.floatArrayOf' call
    var tmp$ret$46 = new Float32Array([1.0, 2.0, 3.0, 4.0]);
    var this_10 = rotatedLeft_6(tmp$ret$46, -1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_9 = ArrayList_init_$Create$(this_10.length);
    var inductionVariable_9 = 0;
    var last_9 = this_10.length;
    while (inductionVariable_9 < last_9) {
      var item_9 = this_10[inductionVariable_9];
      inductionVariable_9 = inductionVariable_9 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$47 = numberToInt(item_9);
      destination_9.add_utx5q5_k$(tmp$ret$47);
    }
    assertEquals(tmp_11, toList_0(destination_9));
    var tmp_12 = listOf([4, 1, 2, 3]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.doubleArrayOf' call
    var tmp$ret$50 = new Float64Array([1.0, 2.0, 3.0, 4.0]);
    var this_11 = rotatedLeft_7(tmp$ret$50, -1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_10 = ArrayList_init_$Create$(this_11.length);
    var inductionVariable_10 = 0;
    var last_10 = this_11.length;
    while (inductionVariable_10 < last_10) {
      var item_10 = this_11[inductionVariable_10];
      inductionVariable_10 = inductionVariable_10 + 1 | 0;
      // Inline function 'korlibs.datastructure.ArrayExtTest.testVariants.<anonymous>' call
      var tmp$ret$51 = numberToInt(item_10);
      destination_10.add_utx5q5_k$(tmp$ret$51);
    }
    assertEquals(tmp_12, toList_0(destination_10));
  };
  function test_fun_izoufj_0() {
    suite('ArrayExtTest', false, test_fun$ArrayExtTest_test_fun_uac930);
  }
  function test_fun$ArrayExtTest_test_fun_uac930() {
    test('testSwap', false, test_fun$ArrayExtTest_test_fun$testSwap_test_fun_9zbo6s);
    test('testRotatedRight', false, test_fun$ArrayExtTest_test_fun$testRotatedRight_test_fun_et8n2s);
    test('testRotatedLeft', false, test_fun$ArrayExtTest_test_fun$testRotatedLeft_test_fun_29q759);
    test('testVariants', false, test_fun$ArrayExtTest_test_fun$testVariants_test_fun_3fmor3);
    return Unit_getInstance();
  }
  function test_fun$ArrayExtTest_test_fun$testSwap_test_fun_9zbo6s() {
    var tmp = new ArrayExtTest();
    tmp.testSwap_nloryj_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayExtTest_test_fun$testRotatedRight_test_fun_et8n2s() {
    var tmp = new ArrayExtTest();
    tmp.testRotatedRight_lmqfhn_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayExtTest_test_fun$testRotatedLeft_test_fun_29q759() {
    var tmp = new ArrayExtTest();
    tmp.testRotatedLeft_3jffzm_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayExtTest_test_fun$testVariants_test_fun_3fmor3() {
    var tmp = new ArrayExtTest();
    tmp.testVariants_ocqlio_k$();
    return Unit_getInstance();
  }
  function ArrayListReverseTest() {
  }
  protoOf(ArrayListReverseTest).test_w5z8la_k$ = function () {
    // Inline function 'kotlin.collections.listOf' call
    var tmp = emptyList();
    // Inline function 'kotlin.apply' call
    var this_0 = intArrayListOf(new Int32Array([]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListReverseTest.test.<anonymous>' call
    reverse(this_0);
    assertEquals(tmp, toList_0(this_0));
    var tmp_0 = listOf_0(1);
    // Inline function 'kotlin.apply' call
    var this_1 = intArrayListOf(new Int32Array([1]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListReverseTest.test.<anonymous>' call
    reverse(this_1);
    assertEquals(tmp_0, toList_0(this_1));
    var tmp_1 = listOf([2, 1]);
    // Inline function 'kotlin.apply' call
    var this_2 = intArrayListOf(new Int32Array([1, 2]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListReverseTest.test.<anonymous>' call
    reverse(this_2);
    assertEquals(tmp_1, toList_0(this_2));
    var tmp_2 = listOf([3, 2, 1]);
    // Inline function 'kotlin.apply' call
    var this_3 = intArrayListOf(new Int32Array([1, 2, 3]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListReverseTest.test.<anonymous>' call
    reverse(this_3);
    assertEquals(tmp_2, toList_0(this_3));
    var tmp_3 = listOf([4, 3, 2, 1]);
    // Inline function 'kotlin.apply' call
    var this_4 = intArrayListOf(new Int32Array([1, 2, 3, 4]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListReverseTest.test.<anonymous>' call
    reverse(this_4);
    assertEquals(tmp_3, toList_0(this_4));
  };
  protoOf(ArrayListReverseTest).test2_6xsqgg_k$ = function () {
    var tmp = listOf([1, 3, 2, 4]);
    // Inline function 'kotlin.apply' call
    var this_0 = intArrayListOf(new Int32Array([1, 2, 3, 4]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListReverseTest.test2.<anonymous>' call
    reverse(this_0, 1, 3);
    assertEquals(tmp, toList_0(this_0));
  };
  function test_fun_izoufj_1() {
    suite('ArrayListReverseTest', false, test_fun$ArrayListReverseTest_test_fun_uhzfdt);
  }
  function test_fun$ArrayListReverseTest_test_fun_uhzfdt() {
    test('test', false, test_fun$ArrayListReverseTest_test_fun$test_test_fun_a3dkak);
    test('test2', false, test_fun$ArrayListReverseTest_test_fun$test2_test_fun_9o1uhg);
    return Unit_getInstance();
  }
  function test_fun$ArrayListReverseTest_test_fun$test_test_fun_a3dkak() {
    var tmp = new ArrayListReverseTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListReverseTest_test_fun$test2_test_fun_9o1uhg() {
    var tmp = new ArrayListReverseTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function ArrayListSortTest() {
  }
  protoOf(ArrayListSortTest).test_w5z8la_k$ = function () {
    var tmp = listOf([1, 2, 3, 4, 5, 6, 7]);
    // Inline function 'kotlin.apply' call
    var this_0 = intArrayListOf(new Int32Array([5, 3, 6, 4, 1, 7, 2]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListSortTest.test.<anonymous>' call
    sort(this_0);
    assertEquals(tmp, toList_0(this_0));
    var tmp_0 = listOf([7, 6, 5, 4, 3, 2, 1]);
    // Inline function 'kotlin.apply' call
    var this_1 = intArrayListOf(new Int32Array([5, 3, 6, 4, 1, 7, 2]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListSortTest.test.<anonymous>' call
    sort(this_1, VOID, VOID, true);
    assertEquals(tmp_0, toList_0(this_1));
  };
  function test_fun_izoufj_2() {
    suite('ArrayListSortTest', false, test_fun$ArrayListSortTest_test_fun_ceeoil);
  }
  function test_fun$ArrayListSortTest_test_fun_ceeoil() {
    test('test', false, test_fun$ArrayListSortTest_test_fun$test_test_fun_s0dzpk);
    return Unit_getInstance();
  }
  function test_fun$ArrayListSortTest_test_fun$test_test_fun_s0dzpk() {
    var tmp = new ArrayListSortTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function ArrayListTest() {
  }
  protoOf(ArrayListTest).testRotated_m216c9_k$ = function () {
    assertEquals(listOf([1, 2, 3, 4]), rotated(listOf([1, 2, 3, 4]), 0));
    assertEquals(listOf([4, 1, 2, 3]), rotated(listOf([1, 2, 3, 4]), 1));
    assertEquals(listOf([3, 4, 1, 2]), rotated(listOf([1, 2, 3, 4]), 2));
    assertEquals(listOf([2, 3, 4, 1]), rotated(listOf([1, 2, 3, 4]), 3));
    assertEquals(listOf([2, 3, 4, 1]), rotated(listOf([1, 2, 3, 4]), -1));
    assertEquals(listOf([3, 4, 1, 2]), rotated(listOf([1, 2, 3, 4]), -2));
    assertEquals(listOf([4, 1, 2, 3]), rotated(listOf([1, 2, 3, 4]), -3));
  };
  protoOf(ArrayListTest).testInt_uxerir_k$ = function () {
    var values = new IntArrayList(2);
    assertEquals(0, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_c9dakn_k$(1);
    assertEquals(listOf_0(1), toList_0(values));
    assertEquals(1, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_c9dakn_k$(2);
    assertEquals(listOf([1, 2]), toList_0(values));
    assertEquals(2, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_c9dakn_k$(3);
    assertEquals(listOf([1, 2, 3]), toList_0(values));
    assertEquals(3, values.get_size_woubt6_k$());
    assertEquals(6, values.get_capacity_tq6ug8_k$());
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    var v = new IntArrayList();
    v.add_c9dakn_k$(1);
    v.add_c9dakn_k$(2);
    v.add_c9dakn_k$(3);
    assertEquals(listOf([1, 2, 3]), toList_0(v));
    v.removeAt_6niowx_k$(1);
    assertEquals(listOf([1, 3]), toList_0(v));
    assertEquals(2, v.get_size_woubt6_k$());
    v.removeAt_6niowx_k$(1);
    assertEquals(listOf_0(1), toList_0(v));
    v.removeAt_6niowx_k$(0);
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$0 = emptyList();
    assertEquals(tmp$ret$0, toList_0(v));
  };
  protoOf(ArrayListTest).testFloat_ucmehy_k$ = function () {
    var values = new FloatArrayList(2);
    assertEquals(0, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_8nv8gp_k$(1.0);
    assertEquals(listOf_0(1.0), toList_0(values));
    assertEquals(1, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_8nv8gp_k$(2.0);
    assertEquals(listOf([1.0, 2.0]), toList_0(values));
    assertEquals(2, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_8nv8gp_k$(3.0);
    assertEquals(listOf([1.0, 2.0, 3.0]), toList_0(values));
    assertEquals(3, values.get_size_woubt6_k$());
    assertEquals(6, values.get_capacity_tq6ug8_k$());
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    var v = new FloatArrayList();
    v.add_8nv8gp_k$(1.0);
    v.add_8nv8gp_k$(2.0);
    v.add_8nv8gp_k$(3.0);
    assertEquals(listOf([1.0, 2.0, 3.0]), toList_0(v));
    v.removeAt_6niowx_k$(1);
    assertEquals(listOf([1.0, 3.0]), toList_0(v));
    assertEquals(2, v.get_size_woubt6_k$());
    v.removeAt_6niowx_k$(1);
    assertEquals(listOf_0(1.0), toList_0(v));
    v.removeAt_6niowx_k$(0);
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$0 = emptyList();
    assertEquals(tmp$ret$0, toList_0(v));
  };
  protoOf(ArrayListTest).testDouble_3ippe5_k$ = function () {
    var values = new DoubleArrayList(2);
    assertEquals(0, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_fu98p7_k$(1.0);
    assertEquals(listOf_0(1.0), toList_0(values));
    assertEquals(1, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_fu98p7_k$(2.0);
    assertEquals(listOf([1.0, 2.0]), toList_0(values));
    assertEquals(2, values.get_size_woubt6_k$());
    assertEquals(2, values.get_capacity_tq6ug8_k$());
    values.add_fu98p7_k$(3.0);
    assertEquals(listOf([1.0, 2.0, 3.0]), toList_0(values));
    assertEquals(3, values.get_size_woubt6_k$());
    assertEquals(6, values.get_capacity_tq6ug8_k$());
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    var v = new DoubleArrayList();
    v.add_fu98p7_k$(1.0);
    v.add_fu98p7_k$(2.0);
    v.add_fu98p7_k$(3.0);
    assertEquals(listOf([1.0, 2.0, 3.0]), toList_0(v));
    v.removeAt_6niowx_k$(1);
    assertEquals(listOf([1.0, 3.0]), toList_0(v));
    assertEquals(2, v.get_size_woubt6_k$());
    v.removeAt_6niowx_k$(1);
    assertEquals(listOf_0(1.0), toList_0(v));
    v.removeAt_6niowx_k$(0);
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$0 = emptyList();
    assertEquals(tmp$ret$0, toList_0(v));
  };
  protoOf(ArrayListTest).map_4oakj8_k$ = function () {
    var tmp = intArrayListOf(new Int32Array([0, 6, 12, 18, 24]));
    // Inline function 'korlibs.datastructure.filter' call
    // Inline function 'korlibs.datastructure.mapInt' call
    var this_0 = until(0, 10);
    // Inline function 'kotlin.also' call
    var this_1 = new IntArrayList((coerceAtLeast(this_0.get_endInclusive_r07xpi_k$() - this_0.get_start_iypx6h_k$() | 0, 0) / this_0.get_step_woujh1_k$() | 0) + 1 | 0);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapInt.<anonymous>' call
    var nestedFirst = this_0.get_start_iypx6h_k$();
    var nestedLast = this_0.get_endInclusive_r07xpi_k$();
    var stepArg = this_0.get_step_woujh1_k$();
    if (stepArg <= 0)
      THROW_IAE('Step must be positive, was: ' + stepArg + '.');
    var inductionVariable = nestedFirst;
    var last = getProgressionLastElement(nestedFirst, nestedLast, stepArg);
    if (inductionVariable <= last)
      do {
        var v = inductionVariable;
        inductionVariable = inductionVariable + stepArg | 0;
        // Inline function 'korlibs.datastructure.ArrayListTest.map.<anonymous>' call
        var it = v;
        var tmp$ret$0 = imul(it, 3);
        this_1.add_c9dakn_k$(tmp$ret$0);
      }
       while (!(v === last));
    // Inline function 'kotlin.also' call
    var this_2 = new IntArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.filter.<anonymous>' call
    var _iterator__ex2g4s = this_1.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var v_0 = _iterator__ex2g4s.next_20eer_k$();
      // Inline function 'korlibs.datastructure.ArrayListTest.map.<anonymous>' call
      if ((v_0 % 2 | 0) === 0) {
        this_2.add_c9dakn_k$(v_0);
      }
    }
    assertEquals(tmp, this_2);
  };
  protoOf(ArrayListTest).list_xxuwxe_k$ = function () {
    assertEquals(toList_0(intArrayListOf(new Int32Array([1, 2, 3]))), listOf([1, 2, 3]));
    assertEquals(listOf([1, 2, 3]), toList_0(intArrayListOf(new Int32Array([1, 2, 3]))));
  };
  protoOf(ArrayListTest).demo_7zh8fh_k$ = function () {
    var a1 = intArrayListOf(new Int32Array([1, 2, 3, 4]));
    var a2 = IntArrayList_init_$Create$(a1);
    assertEquals(listOf([1, 2, 3, 4]), toList_0(a1));
    assertEquals(listOf([1, 2, 3, 4]), toList_0(a2));
  };
  protoOf(ArrayListTest).listIterator_lbdack_k$ = function () {
    assertEquals(listOf([2, 3, 4]), toList_2(asSequence(intArrayListOf(new Int32Array([1, 2, 3, 4])).listIterator_70e65o_k$(1))));
  };
  protoOf(ArrayListTest).testInsertAt_34wgi_k$ = function () {
    assertEquals(listOf([4, 3, 2, 1]), toList_0(intArrayListOf(new Int32Array([])).insertAt_21d2vg_k$(0, 1).insertAt_21d2vg_k$(0, 2).insertAt_21d2vg_k$(0, 3).insertAt_21d2vg_k$(0, 4)));
    var tmp = listOf([21, 22, 23, 11, 12, 13, 1, 2, 3]);
    var tmp_0 = intArrayListOf(new Int32Array([]));
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$0 = new Int32Array([1, 2, 3]);
    var tmp_1 = tmp_0.insertAt$default_sru7m3_k$(0, tmp$ret$0);
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$1 = new Int32Array([11, 12, 13]);
    var tmp_2 = tmp_1.insertAt$default_sru7m3_k$(0, tmp$ret$1);
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$2 = new Int32Array([21, 22, 23]);
    assertEquals(tmp, toList_0(tmp_2.insertAt$default_sru7m3_k$(0, tmp$ret$2)));
  };
  protoOf(ArrayListTest).testRemoveAt_b7djuh_k$ = function () {
    var tmp = listOf([1, 5]);
    // Inline function 'kotlin.apply' call
    var this_0 = intArrayListOf(new Int32Array([1, 2, 3, 4, 5]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testRemoveAt.<anonymous>' call
    this_0.removeAt_eg4l0p_k$(1, 3);
    assertEquals(tmp, toList_0(this_0));
    var tmp_0 = listOf_0(1);
    // Inline function 'kotlin.apply' call
    var this_1 = intArrayListOf(new Int32Array([1, 2, 3, 4, 5]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testRemoveAt.<anonymous>' call
    this_1.removeAt_eg4l0p_k$(1, 4);
    assertEquals(tmp_0, toList_0(this_1));
    var tmp_1 = listOf_0(5);
    // Inline function 'kotlin.apply' call
    var this_2 = intArrayListOf(new Int32Array([1, 2, 3, 4, 5]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testRemoveAt.<anonymous>' call
    this_2.removeAt_eg4l0p_k$(0, 4);
    assertEquals(tmp_1, toList_0(this_2));
    // Inline function 'kotlin.collections.listOf' call
    var tmp_2 = emptyList();
    // Inline function 'kotlin.apply' call
    var this_3 = intArrayListOf(new Int32Array([1, 2, 3, 4, 5]));
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testRemoveAt.<anonymous>' call
    this_3.removeAt_eg4l0p_k$(0, 5);
    assertEquals(tmp_2, toList_0(this_3));
  };
  protoOf(ArrayListTest).testHashCodeIntArrayList_ql7abp_k$ = function () {
    var a = new IntArrayList(10);
    var hc0 = a.hashCode();
    a.add_c9dakn_k$(10);
    var hc1 = a.hashCode();
    a.add_c9dakn_k$(20);
    var hc2 = a.hashCode();
    a.removeAt_6niowx_k$(a.get_size_woubt6_k$() - 1 | 0);
    var hc3 = a.hashCode();
    // Inline function 'kotlin.also' call
    var this_0 = new IntArrayList(10);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testHashCodeIntArrayList.<anonymous>' call
    this_0.add_50s29d_k$(a);
    var b = this_0;
    assertNotEquals(hc1, hc0);
    assertNotEquals(hc1, hc2);
    assertEquals(hc1, hc3);
    assertEquals(a, b);
  };
  protoOf(ArrayListTest).testHashCodeFloatArrayList_fjqlu0_k$ = function () {
    var a = new FloatArrayList(10);
    var hc0 = a.hashCode();
    a.add_8nv8gp_k$(10.0);
    var hc1 = a.hashCode();
    a.add_8nv8gp_k$(20.0);
    var hc2 = a.hashCode();
    a.removeAt_6niowx_k$(a.get_size_woubt6_k$() - 1 | 0);
    var hc3 = a.hashCode();
    // Inline function 'kotlin.also' call
    var this_0 = new FloatArrayList(10);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testHashCodeFloatArrayList.<anonymous>' call
    this_0.add_ppi54v_k$(a);
    var b = this_0;
    assertNotEquals(hc1, hc0);
    assertNotEquals(hc1, hc2);
    assertEquals(hc1, hc3);
    assertEquals(a, b);
  };
  protoOf(ArrayListTest).testHashCodeDoubleArrayList_pktb3d_k$ = function () {
    var a = new DoubleArrayList(10);
    var hc0 = a.hashCode();
    a.add_fu98p7_k$(10.0);
    var hc1 = a.hashCode();
    a.add_fu98p7_k$(20.0);
    var hc2 = a.hashCode();
    a.removeAt_6niowx_k$(a.get_size_woubt6_k$() - 1 | 0);
    var hc3 = a.hashCode();
    // Inline function 'kotlin.also' call
    var this_0 = new DoubleArrayList(10);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testHashCodeDoubleArrayList.<anonymous>' call
    this_0.add_btq1hv_k$(a);
    var b = this_0;
    assertNotEquals(hc1, hc0);
    assertNotEquals(hc1, hc2);
    assertEquals(hc1, hc3);
    assertEquals(a, b);
  };
  protoOf(ArrayListTest).testHashCodeLongArrayList_89mi3m_k$ = function () {
    var a = ArrayList_init_$Create$(10);
    var hc0 = a.hashCode();
    a.add_utx5q5_k$(Unit_getInstance());
    var hc1 = a.hashCode();
    a.add_utx5q5_k$(Unit_getInstance());
    var hc2 = a.hashCode();
    a.removeAt_6niowx_k$(a.get_size_woubt6_k$() - 1 | 0);
    var hc3 = a.hashCode();
    // Inline function 'kotlin.also' call
    var this_0 = ArrayList_init_$Create$(10);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ArrayListTest.testHashCodeLongArrayList.<anonymous>' call
    this_0.addAll_4lagoh_k$(a);
    var b = this_0;
    assertNotEquals(hc1, hc0);
    assertNotEquals(hc1, hc2);
    assertEquals(hc1, hc3);
    assertEquals(a, b);
  };
  function test_fun_izoufj_3() {
    suite('ArrayListTest', false, test_fun$ArrayListTest_test_fun_xwefs5);
  }
  function test_fun$ArrayListTest_test_fun_xwefs5() {
    test('testRotated', false, test_fun$ArrayListTest_test_fun$testRotated_test_fun_mhmcir);
    test('testInt', false, test_fun$ArrayListTest_test_fun$testInt_test_fun_rrox3x);
    test('testFloat', false, test_fun$ArrayListTest_test_fun$testFloat_test_fun_iaizg0);
    test('testDouble', false, test_fun$ArrayListTest_test_fun$testDouble_test_fun_n61kh1);
    test('map', false, test_fun$ArrayListTest_test_fun$map_test_fun_qmlc9u);
    test('list', false, test_fun$ArrayListTest_test_fun$list_test_fun_a78kq2);
    test('demo', false, test_fun$ArrayListTest_test_fun$demo_test_fun_ly4285);
    test('listIterator', false, test_fun$ArrayListTest_test_fun$listIterator_test_fun_kuh2cc);
    test('testInsertAt', false, test_fun$ArrayListTest_test_fun$testInsertAt_test_fun_fi6q6i);
    test('testRemoveAt', false, test_fun$ArrayListTest_test_fun$testRemoveAt_test_fun_8aiesf);
    test('testHashCodeIntArrayList', false, test_fun$ArrayListTest_test_fun$testHashCodeIntArrayList_test_fun_t2w92l);
    test('testHashCodeFloatArrayList', false, test_fun$ArrayListTest_test_fun$testHashCodeFloatArrayList_test_fun_yi9ge8);
    test('testHashCodeDoubleArrayList', false, test_fun$ArrayListTest_test_fun$testHashCodeDoubleArrayList_test_fun_c1cgo1);
    test('testHashCodeLongArrayList', false, test_fun$ArrayListTest_test_fun$testHashCodeLongArrayList_test_fun_xw9az0);
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testRotated_test_fun_mhmcir() {
    var tmp = new ArrayListTest();
    tmp.testRotated_m216c9_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testInt_test_fun_rrox3x() {
    var tmp = new ArrayListTest();
    tmp.testInt_uxerir_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testFloat_test_fun_iaizg0() {
    var tmp = new ArrayListTest();
    tmp.testFloat_ucmehy_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testDouble_test_fun_n61kh1() {
    var tmp = new ArrayListTest();
    tmp.testDouble_3ippe5_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$map_test_fun_qmlc9u() {
    var tmp = new ArrayListTest();
    tmp.map_4oakj8_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$list_test_fun_a78kq2() {
    var tmp = new ArrayListTest();
    tmp.list_xxuwxe_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$demo_test_fun_ly4285() {
    var tmp = new ArrayListTest();
    tmp.demo_7zh8fh_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$listIterator_test_fun_kuh2cc() {
    var tmp = new ArrayListTest();
    tmp.listIterator_lbdack_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testInsertAt_test_fun_fi6q6i() {
    var tmp = new ArrayListTest();
    tmp.testInsertAt_34wgi_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testRemoveAt_test_fun_8aiesf() {
    var tmp = new ArrayListTest();
    tmp.testRemoveAt_b7djuh_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testHashCodeIntArrayList_test_fun_t2w92l() {
    var tmp = new ArrayListTest();
    tmp.testHashCodeIntArrayList_ql7abp_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testHashCodeFloatArrayList_test_fun_yi9ge8() {
    var tmp = new ArrayListTest();
    tmp.testHashCodeFloatArrayList_fjqlu0_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testHashCodeDoubleArrayList_test_fun_c1cgo1() {
    var tmp = new ArrayListTest();
    tmp.testHashCodeDoubleArrayList_pktb3d_k$();
    return Unit_getInstance();
  }
  function test_fun$ArrayListTest_test_fun$testHashCodeLongArrayList_test_fun_xw9az0() {
    var tmp = new ArrayListTest();
    tmp.testHashCodeLongArrayList_89mi3m_k$();
    return Unit_getInstance();
  }
  function BinarySearchTest() {
  }
  protoOf(BinarySearchTest).test_w5z8la_k$ = function () {
    // Inline function 'kotlin.intArrayOf' call
    var v = new Int32Array([10, 20, 30, 40, 50]);
    assertEquals(0, _BSearchResult___get_index__impl__b5kraz(binarySearch(v, 10)));
    assertEquals(1, _BSearchResult___get_index__impl__b5kraz(binarySearch(v, 20)));
    assertEquals(2, _BSearchResult___get_index__impl__b5kraz(binarySearch(v, 30)));
    assertEquals(3, _BSearchResult___get_index__impl__b5kraz(binarySearch(v, 40)));
    assertEquals(4, _BSearchResult___get_index__impl__b5kraz(binarySearch(v, 50)));
    assertEquals(true, _BSearchResult___get_found__impl__om04iz(binarySearch(v, 10)));
    assertEquals(false, _BSearchResult___get_found__impl__om04iz(binarySearch(v, 11)));
    assertEquals(0, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 0)));
    assertEquals(0, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 9)));
    assertEquals(1, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 11)));
    assertEquals(2, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 21)));
    assertEquals(3, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 31)));
    assertEquals(4, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 41)));
    assertEquals(5, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 51)));
    assertEquals(5, _BSearchResult___get_nearIndex__impl__s8nq43(binarySearch(v, 100)));
    var _iterator__ex2g4s = listOf([-1, 0, 9, 11, 19, 21, 31, 41, 51]).iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var nonItem = _iterator__ex2g4s.next_20eer_k$();
      assertEquals(-1, _BSearchResult___get_index__impl__b5kraz(binarySearch(v, nonItem)));
    }
  };
  protoOf(BinarySearchTest).testLeft_1xl4bb_k$ = function () {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.intArrayOf' call
    var this_0 = new Int32Array([10, 20, 30, 40, 50]);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.BinarySearchTest.testLeft.<anonymous>' call
    assertEquals(0, binarySearchLeft(this_0, 5));
    assertEquals(0, binarySearchLeft(this_0, 10));
    assertEquals(0, binarySearchLeft(this_0, 15));
    assertEquals(1, binarySearchLeft(this_0, 20));
    assertEquals(1, binarySearchLeft(this_0, 25));
    assertEquals(2, binarySearchLeft(this_0, 30));
    assertEquals(2, binarySearchLeft(this_0, 35));
    assertEquals(3, binarySearchLeft(this_0, 40));
    assertEquals(3, binarySearchLeft(this_0, 45));
    assertEquals(4, binarySearchLeft(this_0, 50));
    assertEquals(4, binarySearchLeft(this_0, 55));
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.intArrayOf' call
    var this_1 = new Int32Array([10, 20, 30, 40]);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.BinarySearchTest.testLeft.<anonymous>' call
    assertEquals(0, binarySearchLeft(this_1, 5));
    assertEquals(0, binarySearchLeft(this_1, 10));
    assertEquals(0, binarySearchLeft(this_1, 15));
    assertEquals(1, binarySearchLeft(this_1, 20));
    assertEquals(1, binarySearchLeft(this_1, 25));
    assertEquals(2, binarySearchLeft(this_1, 30));
    assertEquals(2, binarySearchLeft(this_1, 35));
    assertEquals(3, binarySearchLeft(this_1, 40));
    assertEquals(3, binarySearchLeft(this_1, 45));
  };
  protoOf(BinarySearchTest).testRight_s6dmfu_k$ = function () {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.intArrayOf' call
    var this_0 = new Int32Array([10, 20, 30, 40, 50]);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.BinarySearchTest.testRight.<anonymous>' call
    assertEquals(0, binarySearchRight(this_0, 5));
    assertEquals(0, binarySearchRight(this_0, 10));
    assertEquals(1, binarySearchRight(this_0, 15));
    assertEquals(1, binarySearchRight(this_0, 20));
    assertEquals(2, binarySearchRight(this_0, 25));
    assertEquals(2, binarySearchRight(this_0, 30));
    assertEquals(3, binarySearchRight(this_0, 35));
    assertEquals(3, binarySearchRight(this_0, 40));
    assertEquals(4, binarySearchRight(this_0, 45));
    assertEquals(4, binarySearchRight(this_0, 50));
    assertEquals(4, binarySearchRight(this_0, 55));
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.intArrayOf' call
    var this_1 = new Int32Array([10, 20, 30, 40]);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.BinarySearchTest.testRight.<anonymous>' call
    assertEquals(0, binarySearchRight(this_1, 5));
    assertEquals(0, binarySearchRight(this_1, 10));
    assertEquals(1, binarySearchRight(this_1, 15));
    assertEquals(1, binarySearchRight(this_1, 20));
    assertEquals(2, binarySearchRight(this_1, 25));
    assertEquals(2, binarySearchRight(this_1, 30));
    assertEquals(3, binarySearchRight(this_1, 35));
    assertEquals(3, binarySearchRight(this_1, 40));
    assertEquals(3, binarySearchRight(this_1, 45));
  };
  function test_fun_izoufj_4() {
    suite('BinarySearchTest', false, test_fun$BinarySearchTest_test_fun_g3rhel);
  }
  function test_fun$BinarySearchTest_test_fun_g3rhel() {
    test('test', false, test_fun$BinarySearchTest_test_fun$test_test_fun_aody4i);
    test('testLeft', false, test_fun$BinarySearchTest_test_fun$testLeft_test_fun_e9adop);
    test('testRight', false, test_fun$BinarySearchTest_test_fun$testRight_test_fun_va1zy0);
    return Unit_getInstance();
  }
  function test_fun$BinarySearchTest_test_fun$test_test_fun_aody4i() {
    var tmp = new BinarySearchTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$BinarySearchTest_test_fun$testLeft_test_fun_e9adop() {
    var tmp = new BinarySearchTest();
    tmp.testLeft_1xl4bb_k$();
    return Unit_getInstance();
  }
  function test_fun$BinarySearchTest_test_fun$testRight_test_fun_va1zy0() {
    var tmp = new BinarySearchTest();
    tmp.testRight_s6dmfu_k$();
    return Unit_getInstance();
  }
  function BitArrayTest() {
  }
  protoOf(BitArrayTest).testBasic_u0w4j0_k$ = function () {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_3();
    // Inline function 'kotlin.also' call
    var this_0 = BitArray_init_$Create$(5);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    var inductionVariable = 0;
    if (inductionVariable < 5)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.BitArrayTest.testBasic.<anonymous>' call
        var tmp$ret$0 = !((n % 2 | 0) === 0);
        this_0.set_jlo0rj_k$(n, tmp$ret$0);
      }
       while (inductionVariable < 5);
    var bitArray = this_0;
    var tmp = 0;
    var tmp_0 = booleanArray(5);
    while (tmp < 5) {
      var tmp_1 = tmp;
      tmp_0[tmp_1] = !((tmp_1 % 2 | 0) === 0);
      tmp = tmp + 1 | 0;
    }
    var booleanArray_0 = tmp_0;
    assertEquals(5, bitArray.get_size_woubt6_k$());
    assertEquals(false, bitArray.get_c1px32_k$(0));
    assertEquals(true, bitArray.get_c1px32_k$(1));
    assertEquals(toList_3(booleanArray_0), toList_0(bitArray));
    assertEquals('[false, true, false, true, false]', toString(toList_0(bitArray)));
    var tmp_2 = 0;
    var tmp_3 = booleanArray(100);
    while (tmp_2 < 100) {
      var tmp_4 = tmp_2;
      tmp_3[tmp_4] = !((tmp_4 % 2 | 0) === 0);
      tmp_2 = tmp_2 + 1 | 0;
    }
    var tmp_5 = toList_3(tmp_3);
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_3();
    // Inline function 'kotlin.also' call
    var this_1 = BitArray_init_$Create$(100);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 100)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        // Inline function 'korlibs.datastructure.BitArrayTest.testBasic.<anonymous>' call
        var tmp$ret$5 = !((n_0 % 2 | 0) === 0);
        this_1.set_jlo0rj_k$(n_0, tmp$ret$5);
      }
       while (inductionVariable_0 < 100);
    assertEquals(tmp_5, toList_0(this_1));
    assertEquals(toList_3(booleanArray(100)), toList_0(BitArray_init_$Create$(100)));
  };
  protoOf(BitArrayTest).testOutOfBounds_c73kew_k$ = function () {
    var bitArray = BitArray_init_$Create$(5);
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      bitArray.get_c1px32_k$(-1);
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    checkResultIsFailure(exceptionClass, null, tmp$ret$2);
    var inductionVariable = 0;
    if (inductionVariable < 5)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        bitArray.get_c1px32_k$(n);
      }
       while (inductionVariable < 5);
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass_0 = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp_1;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      bitArray.get_c1px32_k$(5);
      tmp_1 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_2;
      if ($p instanceof Error) {
        var e_0 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_2 = _Result___init__impl__xyqfz8(createFailure(e_0));
      } else {
        throw $p;
      }
      tmp_1 = tmp_2;
    }
    var tmp$ret$7 = tmp_1;
    checkResultIsFailure(exceptionClass_0, null, tmp$ret$7);
  };
  protoOf(BitArrayTest).testWrite_8h759f_k$ = function () {
    var bitArray = BitArray_init_$Create$(111);
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_3();
    // Inline function 'kotlin.also' call
    var this_0 = BitArray_init_$Create$(111);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.Companion.invoke.<anonymous>' call
    var inductionVariable = 0;
    if (inductionVariable < 111)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.BitArrayTest.testWrite.<anonymous>' call
        this_0.set_jlo0rj_k$(n, true);
      }
       while (inductionVariable < 111);
    var bitArray2 = this_0;
    var booleanArray_0 = booleanArray(111);
    var tmp = 0;
    var tmp_0 = booleanArray(111);
    while (tmp < 111) {
      tmp_0[tmp] = true;
      tmp = tmp + 1 | 0;
    }
    var booleanArray2 = tmp_0;
    var n_0 = 0;
    while (n_0 < bitArray.get_size_woubt6_k$()) {
      bitArray.set_jlo0rj_k$(n_0, true);
      booleanArray_0[n_0] = true;
      bitArray2.set_jlo0rj_k$(n_0, false);
      booleanArray2[n_0] = false;
      n_0 = n_0 + (n_0 + 1 | 0) | 0;
    }
    assertEquals(toList_0(bitArray), toList_3(booleanArray_0));
    assertEquals(toList_0(bitArray2), toList_3(booleanArray2));
  };
  function test_fun_izoufj_5() {
    suite('BitArrayTest', false, test_fun$BitArrayTest_test_fun_l7qwqo);
  }
  function test_fun$BitArrayTest_test_fun_l7qwqo() {
    test('testBasic', false, test_fun$BitArrayTest_test_fun$testBasic_test_fun_vzekhz);
    test('testOutOfBounds', false, test_fun$BitArrayTest_test_fun$testOutOfBounds_test_fun_rs248j);
    test('testWrite', false, test_fun$BitArrayTest_test_fun$testWrite_test_fun_5ix7mg);
    return Unit_getInstance();
  }
  function test_fun$BitArrayTest_test_fun$testBasic_test_fun_vzekhz() {
    var tmp = new BitArrayTest();
    tmp.testBasic_u0w4j0_k$();
    return Unit_getInstance();
  }
  function test_fun$BitArrayTest_test_fun$testOutOfBounds_test_fun_rs248j() {
    var tmp = new BitArrayTest();
    tmp.testOutOfBounds_c73kew_k$();
    return Unit_getInstance();
  }
  function test_fun$BitArrayTest_test_fun$testWrite_test_fun_5ix7mg() {
    var tmp = new BitArrayTest();
    tmp.testWrite_8h759f_k$();
    return Unit_getInstance();
  }
  function BitSetTest() {
  }
  protoOf(BitSetTest).setCheckSeq_f4jk79_k$ = function (_this__u8e3s4, bits) {
    var inductionVariable = 0;
    var last = bits.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        _this__u8e3s4.set_jlo0rj_k$(n, bits[n]);
      }
       while (inductionVariable <= last);
    this.checkSeq_nh518d_k$(_this__u8e3s4, taggedArrayCopy(bits));
  };
  protoOf(BitSetTest).checkSeq_nh518d_k$ = function (_this__u8e3s4, bits) {
    var inductionVariable = 0;
    var last = bits.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        assertEquals(bits[n], _this__u8e3s4.get_c1px32_k$(n));
      }
       while (inductionVariable <= last);
  };
  protoOf(BitSetTest).justOneBit_7px69v_k$ = function () {
    var v = new BitSet(1);
    v.set_jlo0rj_k$(0, false);
    assertEquals(false, v.get_c1px32_k$(0));
    v.set_jlo0rj_k$(0, true);
    assertEquals(true, v.get_c1px32_k$(0));
  };
  protoOf(BitSetTest).fewBits_wx7q3y_k$ = function () {
    var v = new BitSet(3);
    this.setCheckSeq_f4jk79_k$(v, booleanArrayOf([false, true, false]));
    this.setCheckSeq_f4jk79_k$(v, booleanArrayOf([true, false, true]));
    this.setCheckSeq_f4jk79_k$(v, booleanArrayOf([true, true, true]));
    v.clear_j9egeb_k$();
    this.checkSeq_nh518d_k$(v, booleanArrayOf([false, false, false]));
  };
  protoOf(BitSetTest).empty_puyu7x_k$ = function () {
    new BitSet(0);
  };
  protoOf(BitSetTest).bits33_bqcaga_k$ = function () {
    var bs = new BitSet(33);
    var inductionVariable = 0;
    var last = bs.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        bs.set_jlo0rj_k$(n, false);
        assertEquals(false, bs.get_c1px32_k$(n));
        bs.set_jlo0rj_k$(n, true);
        assertEquals(true, bs.get_c1px32_k$(n));
      }
       while (inductionVariable < last);
  };
  protoOf(BitSetTest).unset_qi6921_k$ = function () {
    var bs = new BitSet(8);
    var inductionVariable = 0;
    var last = bs.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        bs.set_jlo0rj_k$(n, true);
      }
       while (inductionVariable < last);
    var progression = step(until(0, bs.get_size_woubt6_k$()), 2);
    var inductionVariable_0 = progression.get_first_irdx8n_k$();
    var last_0 = progression.get_last_wopotb_k$();
    var step_0 = progression.get_step_woujh1_k$();
    if ((step_0 > 0 ? inductionVariable_0 <= last_0 : false) ? true : step_0 < 0 ? last_0 <= inductionVariable_0 : false)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + step_0 | 0;
        bs.unset_pvpgsf_k$(n_0);
      }
       while (!(n_0 === last_0));
    assertEquals(listOf([false, true, false, true, false, true, false, true]), toList_0(bs));
  };
  function test_fun_izoufj_6() {
    suite('BitSetTest', false, test_fun$BitSetTest_test_fun_iludsn);
  }
  function test_fun$BitSetTest_test_fun_iludsn() {
    test('justOneBit', false, test_fun$BitSetTest_test_fun$justOneBit_test_fun_zayalr);
    test('fewBits', false, test_fun$BitSetTest_test_fun$fewBits_test_fun_yjyurk);
    test('empty', false, test_fun$BitSetTest_test_fun$empty_test_fun_vqxf0x);
    test('bits33', false, test_fun$BitSetTest_test_fun$bits33_test_fun_43bfj6);
    test('unset', false, test_fun$BitSetTest_test_fun$unset_test_fun_6e5y45);
    return Unit_getInstance();
  }
  function test_fun$BitSetTest_test_fun$justOneBit_test_fun_zayalr() {
    var tmp = new BitSetTest();
    tmp.justOneBit_7px69v_k$();
    return Unit_getInstance();
  }
  function test_fun$BitSetTest_test_fun$fewBits_test_fun_yjyurk() {
    var tmp = new BitSetTest();
    tmp.fewBits_wx7q3y_k$();
    return Unit_getInstance();
  }
  function test_fun$BitSetTest_test_fun$empty_test_fun_vqxf0x() {
    var tmp = new BitSetTest();
    tmp.empty_puyu7x_k$();
    return Unit_getInstance();
  }
  function test_fun$BitSetTest_test_fun$bits33_test_fun_43bfj6() {
    var tmp = new BitSetTest();
    tmp.bits33_bqcaga_k$();
    return Unit_getInstance();
  }
  function test_fun$BitSetTest_test_fun$unset_test_fun_6e5y45() {
    var tmp = new BitSetTest();
    tmp.unset_qi6921_k$();
    return Unit_getInstance();
  }
  function readBytesUpTo(_this__u8e3s4, $this, count) {
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ByteArrayDequeTest.readBytesUpTo.<anonymous>' call
    var it = new Int8Array(count);
    var size = _this__u8e3s4.read_7zpyie_k$(it, 0, it.length);
    return copyOf(it, size);
  }
  function ByteArrayDequeTest() {
  }
  protoOf(ByteArrayDequeTest).test_w5z8la_k$ = function () {
    var data = new ByteArrayDeque(1);
    assertEquals(0, data.get_availableRead_tre4t2_k$());
    assertEquals(2, data.get_availableWriteWithoutAllocating_m040u5_k$());
    data.writeByte_3m2t4h_k$(255);
    assertEquals(1, data.get_availableRead_tre4t2_k$());
    assertEquals(1, data.get_availableWriteWithoutAllocating_m040u5_k$());
    data.writeByte_3m2t4h_k$(119);
    assertEquals(2, data.get_availableRead_tre4t2_k$());
    assertEquals(0, data.get_availableWriteWithoutAllocating_m040u5_k$());
    data.writeByte_3m2t4h_k$(63);
    assertEquals(3, data.get_availableRead_tre4t2_k$());
    assertEquals(5, data.get_availableWriteWithoutAllocating_m040u5_k$());
    var tmp = listOf([255, 119]);
    // Inline function 'kotlin.collections.map' call
    var this_0 = readBytesUpTo(data, this, 2);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(this_0.length);
    var inductionVariable = 0;
    var last = this_0.length;
    while (inductionVariable < last) {
      var item = this_0[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'korlibs.datastructure.ByteArrayDequeTest.test.<anonymous>' call
      var tmp$ret$0 = item & 255;
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    assertEquals(tmp, destination);
    data.writeByte_3m2t4h_k$(127);
    assertEquals(63, data.readByte_ectjk2_k$());
    assertEquals(1, data.get_availableRead_tre4t2_k$());
    assertEquals(7, data.get_availableWriteWithoutAllocating_m040u5_k$());
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$3 = new Int8Array([-103, -120, 51, 68, 85, 102, -86]);
    data.write$default_81zfb5_k$(tmp$ret$3);
    assertEquals(8, data.get_availableRead_tre4t2_k$());
    assertEquals(0, data.get_availableWriteWithoutAllocating_m040u5_k$());
    var tmp_0 = listOf([127, 153, 136, 51, 68, 85, 102, 170]);
    // Inline function 'kotlin.collections.map' call
    var this_1 = readBytesUpTo(data, this, 8);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(this_1.length);
    var inductionVariable_0 = 0;
    var last_0 = this_1.length;
    while (inductionVariable_0 < last_0) {
      var item_0 = this_1[inductionVariable_0];
      inductionVariable_0 = inductionVariable_0 + 1 | 0;
      // Inline function 'korlibs.datastructure.ByteArrayDequeTest.test.<anonymous>' call
      var tmp$ret$4 = item_0 & 255;
      destination_0.add_utx5q5_k$(tmp$ret$4);
    }
    assertEquals(tmp_0, destination_0);
    assertEquals(-1, data.readByte_ectjk2_k$());
    assertEquals(0, readBytesUpTo(data, this, 10).length);
  };
  protoOf(ByteArrayDequeTest).demo_7zh8fh_k$ = function () {
    var data = new ByteArrayDeque(4);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$0 = new Int8Array([4, 5, 6]);
    data.write$default_81zfb5_k$(tmp$ret$0);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$1 = new Int8Array([1, 2, 3]);
    data.writeHead$default_mr1qdd_k$(tmp$ret$1);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$2 = new Int8Array([1, 2, 3, 4, 5, 6]);
    assertEquals(toList_4(tmp$ret$2), toList_4(readBytesUpTo(data, this, 6)));
  };
  function test_fun_izoufj_7() {
    suite('ByteArrayDequeTest', false, test_fun$ByteArrayDequeTest_test_fun_gx2ke5);
  }
  function test_fun$ByteArrayDequeTest_test_fun_gx2ke5() {
    test('test', false, test_fun$ByteArrayDequeTest_test_fun$test_test_fun_oeqgpk);
    test('demo', false, test_fun$ByteArrayDequeTest_test_fun$demo_test_fun_chdtd3);
    return Unit_getInstance();
  }
  function test_fun$ByteArrayDequeTest_test_fun$test_test_fun_oeqgpk() {
    var tmp = new ByteArrayDequeTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$ByteArrayDequeTest_test_fun$demo_test_fun_chdtd3() {
    var tmp = new ByteArrayDequeTest();
    tmp.demo_7zh8fh_k$();
    return Unit_getInstance();
  }
  function _set_called__pmkpfq($this, _set____db54di) {
    $this.called_1 = _set____db54di;
  }
  function _get_called__o5u4sy($this) {
    return $this.called_1;
  }
  function _set__myfield__yae3ag($this, _set____db54di) {
    $this._myfield_1 = _set____db54di;
  }
  function _get__myfield__lhhrto($this) {
    return $this._myfield_1;
  }
  function CacheLazyNullableTest() {
    this.called_1 = 0;
    this._myfield_1 = null;
  }
  protoOf(CacheLazyNullableTest).get_myfield_qbo4bp_k$ = function () {
    // Inline function 'korlibs.datastructure.cacheLazyNullable' call
    var field = _myfield$factory(this);
    var result = field.get();
    if (result == null) {
      // Inline function 'korlibs.datastructure.CacheLazyNullableTest.<get-myfield>.<anonymous>' call
      // Inline function 'kotlin.also' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'korlibs.datastructure.CacheLazyNullableTest.<get-myfield>.<anonymous>.<anonymous>' call
      this.called_1 = this.called_1 + 1 | 0;
      result = 10;
      field.set(result);
    }
    return ensureNotNull(result);
  };
  protoOf(CacheLazyNullableTest).test_w5z8la_k$ = function () {
    assertEquals(0, this.called_1);
    // Inline function 'kotlin.repeat' call
    // Inline function 'kotlin.contracts.contract' call
    var inductionVariable = 0;
    if (inductionVariable < 3)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.CacheLazyNullableTest.test.<anonymous>' call
        assertEquals(10, this.get_myfield_qbo4bp_k$());
      }
       while (inductionVariable < 3);
    assertEquals(10, this.get_myfield_qbo4bp_k$());
    assertEquals(1, this.called_1);
  };
  function test_fun_izoufj_8() {
    suite('CacheLazyNullableTest', false, test_fun$CacheLazyNullableTest_test_fun_jq5riz);
  }
  function test_fun$CacheLazyNullableTest_test_fun_jq5riz() {
    test('test', false, test_fun$CacheLazyNullableTest_test_fun$test_test_fun_hihjsm);
    return Unit_getInstance();
  }
  function test_fun$CacheLazyNullableTest_test_fun$test_test_fun_hihjsm() {
    var tmp = new CacheLazyNullableTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function _myfield$factory($b0) {
    return getPropertyCallableRef('_myfield', 0, KMutableProperty0, function () {
      return $b0._myfield_1;
    }, function (value) {
      $b0._myfield_1 = value;
      return Unit_getInstance();
    });
  }
  function CacheMapTest$test$cache$1($freeLog) {
    this.$freeLog_1 = $freeLog;
    CacheMap.call(this, 2);
  }
  protoOf(CacheMapTest$test$cache$1).freed_924c6y_k$ = function (key, value) {
    protoOf(CacheMap).freed_lk3m6b_k$.call(this, key, value);
    // Inline function 'kotlin.collections.plusAssign' call
    var this_0 = this.$freeLog_1;
    var element = key + ':' + value;
    this_0.add_utx5q5_k$(element);
  };
  protoOf(CacheMapTest$test$cache$1).freed_lk3m6b_k$ = function (key, value) {
    var tmp = (!(key == null) ? typeof key === 'string' : false) ? key : THROW_CCE();
    return this.freed_924c6y_k$(tmp, (!(value == null) ? typeof value === 'number' : false) ? value : THROW_CCE());
  };
  function CacheMapTest() {
  }
  protoOf(CacheMapTest).test_w5z8la_k$ = function () {
    // Inline function 'kotlin.collections.arrayListOf' call
    var freeLog = ArrayList_init_$Create$_0();
    var cache = new CacheMapTest$test$cache$1(freeLog);
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$('a', 1);
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$('b', 2);
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$('c', 3);
    assertEquals('{b=2, c=3}', cache.toString());
    assertEquals('a:1', joinToString(freeLog, ', '));
    // Inline function 'kotlin.collections.contains' call
    // Inline function 'kotlin.collections.containsKey' call
    var tmp$ret$2 = (isInterface(cache, Map) ? cache : THROW_CCE()).containsKey_aw81wo_k$('a');
    assertEquals(false, tmp$ret$2);
    // Inline function 'kotlin.collections.contains' call
    // Inline function 'kotlin.collections.containsKey' call
    var tmp$ret$4 = (isInterface(cache, Map) ? cache : THROW_CCE()).containsKey_aw81wo_k$('b');
    assertEquals(true, tmp$ret$4);
    // Inline function 'kotlin.collections.contains' call
    // Inline function 'kotlin.collections.containsKey' call
    var tmp$ret$6 = (isInterface(cache, Map) ? cache : THROW_CCE()).containsKey_aw81wo_k$('c');
    assertEquals(true, tmp$ret$6);
    // Inline function 'kotlin.collections.getOrPut' call
    var value = cache.get_wei43m_k$('b');
    var tmp;
    if (value == null) {
      // Inline function 'korlibs.datastructure.CacheMapTest.test.<anonymous>' call
      var answer = 20;
      cache.put_4fpzoq_k$('b', answer);
      tmp = answer;
    } else {
      tmp = value;
    }
    var tmp$ret$8 = tmp;
    assertEquals(2, tmp$ret$8);
    // Inline function 'kotlin.collections.getOrPut' call
    var value_0 = cache.get_wei43m_k$('a');
    var tmp_0;
    if (value_0 == null) {
      // Inline function 'korlibs.datastructure.CacheMapTest.test.<anonymous>' call
      var answer_0 = 10;
      cache.put_4fpzoq_k$('a', answer_0);
      tmp_0 = answer_0;
    } else {
      tmp_0 = value_0;
    }
    var tmp$ret$10 = tmp_0;
    assertEquals(10, tmp$ret$10);
    // Inline function 'kotlin.collections.getOrPut' call
    var value_1 = cache.get_wei43m_k$('d');
    var tmp_1;
    if (value_1 == null) {
      // Inline function 'korlibs.datastructure.CacheMapTest.test.<anonymous>' call
      var answer_1 = 3;
      cache.put_4fpzoq_k$('d', answer_1);
      tmp_1 = answer_1;
    } else {
      tmp_1 = value_1;
    }
    var tmp$ret$12 = tmp_1;
    assertEquals(3, tmp$ret$12);
    cache.putAll_wgg6cj_k$(mapOf([to('aa', 1), to('bb', 2), to('cc', 3)]));
    assertEquals('{bb=2, cc=3}', cache.toString());
  };
  function test_fun_izoufj_9() {
    suite('CacheMapTest', false, test_fun$CacheMapTest_test_fun_h390ry);
  }
  function test_fun$CacheMapTest_test_fun_h390ry() {
    test('test', false, test_fun$CacheMapTest_test_fun$test_test_fun_k4zn8t);
    return Unit_getInstance();
  }
  function test_fun$CacheMapTest_test_fun$test_test_fun_k4zn8t() {
    var tmp = new CacheMapTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function CaseInsensitiveStringMapTest() {
  }
  protoOf(CaseInsensitiveStringMapTest).test_w5z8la_k$ = function () {
    var map = toCaseInsensitiveMap(mapOf([to('hELLo', 1), to('World', 2)]));
    assertEquals(2, map.get_size_woubt6_k$());
    assertEquals(1, map.get_wei43m_k$('hello'));
    assertEquals(2, map.get_wei43m_k$('world'));
    assertEquals(1, map.get_wei43m_k$('HELLo'));
    assertEquals(2, map.get_wei43m_k$('World'));
    assertEquals(listOf(['World', 'hELLo']), sorted(map.get_keys_wop4xp_k$()));
  };
  protoOf(CaseInsensitiveStringMapTest).test2_6xsqgg_k$ = function () {
    var map = CaseInsensitiveStringMap_init_$Create$([to('hELLo', 1), to('World', 2)]);
    assertEquals(2, map.get_size_woubt6_k$());
    assertEquals(1, map.get_6bo4tg_k$('hello'));
    assertEquals(2, map.get_6bo4tg_k$('world'));
    assertEquals(1, map.get_6bo4tg_k$('HELLo'));
    assertEquals(2, map.get_6bo4tg_k$('World'));
    assertEquals(listOf(['World', 'hELLo']), sorted(map.get_keys_wop4xp_k$()));
  };
  protoOf(CaseInsensitiveStringMapTest).test3_ffxdzl_k$ = function () {
    var map = CaseInsensitiveStringMap_init_$Create$([to('hELLo', 1)]);
    // Inline function 'kotlin.collections.set' call
    map.put_4fpzoq_k$('HELLO', 2);
    assertEquals(2, map.get_6bo4tg_k$('hello'));
    assertEquals(listOf_0('HELLO'), toList_0(map.get_keys_wop4xp_k$()));
    var map2 = CaseInsensitiveStringMap_init_$Create$_0(mapOf_0(to('hELLo', 1)));
    assertEquals(listOf_0('hELLo'), toList_0(map2.get_keys_wop4xp_k$()));
  };
  function test_fun_izoufj_10() {
    suite('CaseInsensitiveStringMapTest', false, test_fun$CaseInsensitiveStringMapTest_test_fun_sxsjuq);
  }
  function test_fun$CaseInsensitiveStringMapTest_test_fun_sxsjuq() {
    test('test', false, test_fun$CaseInsensitiveStringMapTest_test_fun$test_test_fun_jdqler);
    test('test2', false, test_fun$CaseInsensitiveStringMapTest_test_fun$test2_test_fun_jbxndf);
    test('test3', false, test_fun$CaseInsensitiveStringMapTest_test_fun$test3_test_fun_g2xoky);
    return Unit_getInstance();
  }
  function test_fun$CaseInsensitiveStringMapTest_test_fun$test_test_fun_jdqler() {
    var tmp = new CaseInsensitiveStringMapTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$CaseInsensitiveStringMapTest_test_fun$test2_test_fun_jbxndf() {
    var tmp = new CaseInsensitiveStringMapTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function test_fun$CaseInsensitiveStringMapTest_test_fun$test3_test_fun_g2xoky() {
    var tmp = new CaseInsensitiveStringMapTest();
    tmp.test3_ffxdzl_k$();
    return Unit_getInstance();
  }
  function ChunkedByteDequeTest() {
  }
  protoOf(ChunkedByteDequeTest).test_w5z8la_k$ = function () {
    // Inline function 'kotlin.collections.arrayListOf' call
    var log = ArrayList_init_$Create$_0();
    var deque = new ChunkedByteDeque();
    // Inline function 'kotlin.collections.plusAssign' call
    var element = '' + deque.get_availableRead_tre4t2_k$();
    log.add_utx5q5_k$(element);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$1 = new Int8Array([1, 2, 3, 4]);
    deque.write$default_m2hpbm_k$(tmp$ret$1);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_0 = '' + deque.get_availableRead_tre4t2_k$();
    log.add_utx5q5_k$(element_0);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$2 = new Int8Array([5, 6, 7]);
    deque.write$default_m2hpbm_k$(tmp$ret$2);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_1 = '' + deque.get_availableRead_tre4t2_k$();
    log.add_utx5q5_k$(element_1);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_2 = ':' + joinToString_0(deque.read_yyte66_k$(100), ',');
    log.add_utx5q5_k$(element_2);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_3 = '' + deque.get_availableRead_tre4t2_k$();
    log.add_utx5q5_k$(element_3);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_4 = ':' + joinToString_0(deque.read_yyte66_k$(100), ',');
    log.add_utx5q5_k$(element_4);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_5 = '' + deque.get_availableRead_tre4t2_k$();
    log.add_utx5q5_k$(element_5);
    assertEquals('0\n4\n7\n:1,2,3,4,5,6,7\n0\n:\n0', joinToString(log, '\n'));
  };
  function test_fun_izoufj_11() {
    suite('ChunkedByteDequeTest', false, test_fun$ChunkedByteDequeTest_test_fun_j0rnnk);
  }
  function test_fun$ChunkedByteDequeTest_test_fun_j0rnnk() {
    test('test', false, test_fun$ChunkedByteDequeTest_test_fun$test_test_fun_vc7iqj);
    return Unit_getInstance();
  }
  function test_fun$ChunkedByteDequeTest_test_fun$test_test_fun_vc7iqj() {
    var tmp = new ChunkedByteDequeTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function ComputedTest$Format$computedSize$delegate$lambda() {
    return 10;
  }
  function Format(parent) {
    parent = parent === VOID ? null : parent;
    this.parent_1 = parent;
    this.size_1 = null;
    var tmp = this;
    var tmp_0 = size$factory();
    tmp.computedSize$delegate_1 = new Computed(tmp_0, ComputedTest$Format$computedSize$delegate$lambda);
  }
  protoOf(Format).set_parent_lj3skf_k$ = function (_set____db54di) {
    this.parent_1 = _set____db54di;
  };
  protoOf(Format).get_parent_hy4reb_k$ = function () {
    return this.parent_1;
  };
  protoOf(Format).set_size_a07e8t_k$ = function (_set____db54di) {
    this.size_1 = _set____db54di;
  };
  protoOf(Format).get_size_woubt6_k$ = function () {
    return this.size_1;
  };
  protoOf(Format).get_computedSize_r00b9l_k$ = function () {
    return this.computedSize$delegate_1.getValue_v5m1ht_k$(this, computedSize$factory());
  };
  function ComputedTest() {
  }
  protoOf(ComputedTest).name_ugbo79_k$ = function () {
    var f2 = new Format();
    var f1 = new Format(f2);
    assertEquals(10, f1.get_computedSize_r00b9l_k$());
    f2.size_1 = 12;
    assertEquals(12, f1.get_computedSize_r00b9l_k$());
    f1.size_1 = 15;
    assertEquals(15, f1.get_computedSize_r00b9l_k$());
  };
  protoOf(ComputedTest).name2_ovm8cp_k$ = function () {
    var f3 = new Format();
    var f2 = new Format(f3);
    var f1 = new Format(f2);
    assertEquals(10, f1.get_computedSize_r00b9l_k$());
    f3.size_1 = 12;
    assertEquals(12, f1.get_computedSize_r00b9l_k$());
    f3.size_1 = 15;
    assertEquals(15, f1.get_computedSize_r00b9l_k$());
    f2.size_1 = 14;
    assertEquals(14, f1.get_computedSize_r00b9l_k$());
    f1.size_1 = 13;
    assertEquals(13, f1.get_computedSize_r00b9l_k$());
  };
  function test_fun_izoufj_12() {
    suite('ComputedTest', false, test_fun$ComputedTest_test_fun_ce33z5);
  }
  function test_fun$ComputedTest_test_fun_ce33z5() {
    test('name', false, test_fun$ComputedTest_test_fun$name_test_fun_3mbpy9);
    test('name2', false, test_fun$ComputedTest_test_fun$name2_test_fun_m2svtb);
    return Unit_getInstance();
  }
  function test_fun$ComputedTest_test_fun$name_test_fun_3mbpy9() {
    var tmp = new ComputedTest();
    tmp.name_ugbo79_k$();
    return Unit_getInstance();
  }
  function test_fun$ComputedTest_test_fun$name2_test_fun_m2svtb() {
    var tmp = new ComputedTest();
    tmp.name2_ovm8cp_k$();
    return Unit_getInstance();
  }
  function size$factory() {
    return getPropertyCallableRef('size', 1, KMutableProperty1, function (receiver) {
      return receiver.size_1;
    }, function (receiver, value) {
      receiver.size_1 = value;
      return Unit_getInstance();
    });
  }
  function computedSize$factory() {
    return getPropertyCallableRef('computedSize', 1, KProperty1, function (receiver) {
      return receiver.get_computedSize_r00b9l_k$();
    }, null);
  }
  function _get_str__e6gw3k(_this__u8e3s4, $this) {
    return joinToString(_this__u8e3s4, ',');
  }
  function create($this) {
    return TGenDeque_init_$Create$();
  }
  function DequeTest() {
  }
  protoOf(DequeTest).simple_w8bz1a_k$ = function () {
    var l = create(this);
    // Inline function 'kotlin.collections.plusAssign' call
    var elements = listOf(['a', 'b', 'c', 'd', 'e', 'f']);
    addAll(l, elements);
    assertEquals('a,b,c,d,e,f', _get_str__e6gw3k(l, this));
    assertEquals('a', l.get_first_irdx8n_k$());
    assertEquals('f', l.get_last_wopotb_k$());
    assertEquals(6, l.get_size_woubt6_k$());
    l.removeAt_6niowx_k$(1);
    assertEquals(5, l.get_size_woubt6_k$());
    l.removeAt_6niowx_k$(l.get_size_woubt6_k$() - 2 | 0);
    assertEquals(4, l.get_size_woubt6_k$());
    assertEquals('a,c,d,f', _get_str__e6gw3k(l, this));
    l.remove_tetxhv_k$('d');
    assertEquals(3, l.get_size_woubt6_k$());
    assertEquals('a,c,f', _get_str__e6gw3k(l, this));
    l.retainAll_ta6v7a_k$(listOf(['a', 'f']));
    assertEquals(2, l.get_size_woubt6_k$());
    assertEquals('a,f', _get_str__e6gw3k(l, this));
    l.removeAll_eizctx_k$(listOf_0('a'));
    assertEquals(1, l.get_size_woubt6_k$());
    assertEquals('f', _get_str__e6gw3k(l, this));
  };
  protoOf(DequeTest).grow_79va6b_k$ = function () {
    var l = create(this);
    var inductionVariable = 0;
    if (inductionVariable < 1000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        l.add_2y5tg6_k$('' + n);
      }
       while (inductionVariable < 1000);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 495)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        l.removeFirst_58pi0k_k$();
        l.removeLast_i5wx8a_k$();
      }
       while (inductionVariable_0 < 495);
    assertEquals(10, l.get_size_woubt6_k$());
    assertEquals('495,496,497,498,499,500,501,502,503,504', _get_str__e6gw3k(l, this));
  };
  protoOf(DequeTest).grow2_86khpr_k$ = function () {
    var l = create(this);
    var inductionVariable = 0;
    if (inductionVariable < 1000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        l.addFirst_hbgi1k_k$(true);
      }
       while (inductionVariable < 1000);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 1000)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        l.removeFirst_58pi0k_k$();
      }
       while (inductionVariable_0 < 1000);
    var inductionVariable_1 = 0;
    if (inductionVariable_1 < 1000)
      do {
        var n_1 = inductionVariable_1;
        inductionVariable_1 = inductionVariable_1 + 1 | 0;
        l.addFirst_hbgi1k_k$(true);
      }
       while (inductionVariable_1 < 1000);
  };
  protoOf(DequeTest).test2_6xsqgg_k$ = function () {
    var l = create(this);
    l.addLast_s9bxg0_k$('a');
    l.addLast_s9bxg0_k$('b');
    l.addLast_s9bxg0_k$('c');
    l.removeAt_6niowx_k$(1);
    assertEquals('a,c', _get_str__e6gw3k(l, this));
  };
  protoOf(DequeTest).exceptions_wcz67o_k$ = function () {
    var l = create(this);
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      l.removeFirst_58pi0k_k$();
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    checkResultIsFailure(exceptionClass, null, tmp$ret$2);
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass_0 = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp_1;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      l.removeLast_i5wx8a_k$();
      tmp_1 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_2;
      if ($p instanceof Error) {
        var e_0 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_2 = _Result___init__impl__xyqfz8(createFailure(e_0));
      } else {
        throw $p;
      }
      tmp_1 = tmp_2;
    }
    var tmp$ret$7 = tmp_1;
    checkResultIsFailure(exceptionClass_0, null, tmp$ret$7);
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass_1 = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp_3;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      l.removeAt_6niowx_k$(1);
      tmp_3 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_4;
      if ($p instanceof Error) {
        var e_1 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_4 = _Result___init__impl__xyqfz8(createFailure(e_1));
      } else {
        throw $p;
      }
      tmp_3 = tmp_4;
    }
    var tmp$ret$12 = tmp_3;
    checkResultIsFailure(exceptionClass_1, null, tmp$ret$12);
    l.addFirst_hbgi1k_k$(true);
    l.removeAt_6niowx_k$(0);
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass_2 = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp_5;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      l.removeAt_6niowx_k$(0);
      tmp_5 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_6;
      if ($p instanceof Error) {
        var e_2 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_6 = _Result___init__impl__xyqfz8(createFailure(e_2));
      } else {
        throw $p;
      }
      tmp_5 = tmp_6;
    }
    var tmp$ret$17 = tmp_5;
    checkResultIsFailure(exceptionClass_2, null, tmp$ret$17);
  };
  protoOf(DequeTest).remove_ldkf9o_k$ = function () {
    var inductionVariable = 0;
    if (inductionVariable < 16)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'kotlin.apply' call
        var this_0 = toMutableList(until(0, 16));
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_0.remove_cedx0m_k$(n);
        var tmp = this_0;
        // Inline function 'kotlin.apply' call
        // Inline function 'kotlin.apply' call
        var this_1 = IntDeque_init_$Create$();
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_1.addAll_jzcfmw_k$(until(0, 16));
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_1.removeAt_6niowx_k$(n);
        assertEquals(tmp, toList_0(this_1));
      }
       while (inductionVariable < 16);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 16)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        // Inline function 'kotlin.apply' call
        // Inline function 'kotlin.apply' call
        var this_2 = toMutableList(until(0, 17));
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_2.removeAt_6niowx_k$(0);
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_2.removeAt_6niowx_k$(n_0);
        var tmp_0 = this_2;
        // Inline function 'kotlin.apply' call
        // Inline function 'kotlin.apply' call
        // Inline function 'kotlin.apply' call
        var this_3 = IntDeque_init_$Create$();
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_3.addAll_jzcfmw_k$(until(0, 17));
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_3.removeFirst_58pi0k_k$();
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_3.removeAt_6niowx_k$(n_0);
        assertEquals(tmp_0, toList_0(this_3));
      }
       while (inductionVariable_0 < 16);
    var inductionVariable_1 = 0;
    if (inductionVariable_1 < 16)
      do {
        var n_1 = inductionVariable_1;
        inductionVariable_1 = inductionVariable_1 + 1 | 0;
        // Inline function 'kotlin.apply' call
        // Inline function 'kotlin.apply' call
        var this_4 = toMutableList(until(0, 17));
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_4.removeAt_6niowx_k$(this_4.get_size_woubt6_k$() - 1 | 0);
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_4.removeAt_6niowx_k$(n_1);
        var tmp_1 = this_4;
        // Inline function 'kotlin.apply' call
        // Inline function 'kotlin.apply' call
        // Inline function 'kotlin.apply' call
        var this_5 = IntDeque_init_$Create$();
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_5.addAll_jzcfmw_k$(until(0, 17));
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_5.removeLast_i5wx8a_k$();
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.DequeTest.remove.<anonymous>' call
        this_5.removeAt_6niowx_k$(n_1);
        assertEquals(tmp_1, toList_0(this_5));
      }
       while (inductionVariable_1 < 16);
  };
  protoOf(DequeTest).removeIterator_9zdh32_k$ = function () {
    var deque = IntDeque_init_$Create$();
    deque.addAll_jzcfmw_k$(until(0, 10));
    var iterator = deque.iterator_jk1svi_k$();
    while (iterator.hasNext_bitz1p_k$()) {
      var item = iterator.next_20eer_k$();
      if ((item % 2 | 0) === 0 ? true : item < 5) {
        iterator.remove_ldkf9o_k$();
      }
    }
    assertEquals(listOf([5, 7, 9]), toList_0(deque));
  };
  protoOf(DequeTest).hashCodeEqualsTest_6v0hi4_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = IntDeque_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.DequeTest.hashCodeEqualsTest.<anonymous>' call
    this_0.addAll_u5nlws_k$(listOf([1, 2, 3, 4]));
    var a = this_0;
    // Inline function 'kotlin.apply' call
    var this_1 = IntDeque_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.DequeTest.hashCodeEqualsTest.<anonymous>' call
    this_1.addAll_u5nlws_k$(listOf([1, 2, 3, 4]));
    var b = this_1;
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(a, b);
  };
  protoOf(DequeTest).testAddFirstAll_2nwq40_k$ = function () {
    var deque = new IntDeque(4);
    deque.addAll_u5nlws_k$(listOf([5, 6, 7, 8]));
    deque.addAllFirst_7xkpzw_k$(listOf([1, 2, 3, 4]));
    assertEquals(listOf([1, 2, 3, 4, 5, 6, 7, 8]), toList_0(deque));
  };
  protoOf(DequeTest).testAddAllArray_zc3xl5_k$ = function () {
    var deque = new IntDeque(1);
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$0 = new Int32Array([1, 2]);
    deque.addAll_k6ueej_k$(tmp$ret$0);
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$1 = new Int32Array([-2, -1]);
    deque.addAllFirst_f2j6w5_k$(tmp$ret$1);
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$2 = new Int32Array([3]);
    deque.addAll_k6ueej_k$(tmp$ret$2);
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$3 = new Int32Array([-3]);
    deque.addAllFirst_f2j6w5_k$(tmp$ret$3);
    assertEquals('-3,-2,-1,1,2,3', joinToString(deque, ','));
  };
  protoOf(DequeTest).testAddOverflow_pckkqp_k$ = function () {
    var deque = new IntDeque(4);
    deque.addAll_k6ueej_k$(new Int32Array(1000));
    assertEquals(1000, deque.get_size_woubt6_k$());
    deque.addAll_k6ueej_k$(new Int32Array(1000));
    assertEquals(2000, deque.get_size_woubt6_k$());
  };
  function test_fun_izoufj_13() {
    suite('DequeTest', false, test_fun$DequeTest_test_fun_jy8fxu);
  }
  function test_fun$DequeTest_test_fun_jy8fxu() {
    test('simple', false, test_fun$DequeTest_test_fun$simple_test_fun_of3kx9);
    test('grow', false, test_fun$DequeTest_test_fun$grow_test_fun_nz2vak);
    test('grow2', false, test_fun$DequeTest_test_fun$grow2_test_fun_ds5zms);
    test('test2', false, test_fun$DequeTest_test_fun$test2_test_fun_560lgd);
    test('exceptions', false, test_fun$DequeTest_test_fun$exceptions_test_fun_3b1ug5);
    test('remove', false, test_fun$DequeTest_test_fun$remove_test_fun_hqji8r);
    test('removeIterator', false, test_fun$DequeTest_test_fun$removeIterator_test_fun_idnakt);
    test('hashCodeEqualsTest', false, test_fun$DequeTest_test_fun$hashCodeEqualsTest_test_fun_obr3n7);
    test('testAddFirstAll', false, test_fun$DequeTest_test_fun$testAddFirstAll_test_fun_y0glcz);
    test('testAddAllArray', false, test_fun$DequeTest_test_fun$testAddAllArray_test_fun_41v49w);
    test('testAddOverflow', false, test_fun$DequeTest_test_fun$testAddOverflow_test_fun_d6tjvy);
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$simple_test_fun_of3kx9() {
    var tmp = new DequeTest();
    tmp.simple_w8bz1a_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$grow_test_fun_nz2vak() {
    var tmp = new DequeTest();
    tmp.grow_79va6b_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$grow2_test_fun_ds5zms() {
    var tmp = new DequeTest();
    tmp.grow2_86khpr_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$test2_test_fun_560lgd() {
    var tmp = new DequeTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$exceptions_test_fun_3b1ug5() {
    var tmp = new DequeTest();
    tmp.exceptions_wcz67o_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$remove_test_fun_hqji8r() {
    var tmp = new DequeTest();
    tmp.remove_ldkf9o_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$removeIterator_test_fun_idnakt() {
    var tmp = new DequeTest();
    tmp.removeIterator_9zdh32_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$hashCodeEqualsTest_test_fun_obr3n7() {
    var tmp = new DequeTest();
    tmp.hashCodeEqualsTest_6v0hi4_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$testAddFirstAll_test_fun_y0glcz() {
    var tmp = new DequeTest();
    tmp.testAddFirstAll_2nwq40_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$testAddAllArray_test_fun_41v49w() {
    var tmp = new DequeTest();
    tmp.testAddAllArray_zc3xl5_k$();
    return Unit_getInstance();
  }
  function test_fun$DequeTest_test_fun$testAddOverflow_test_fun_d6tjvy() {
    var tmp = new DequeTest();
    tmp.testAddOverflow_pckkqp_k$();
    return Unit_getInstance();
  }
  function set_demo1(_this__u8e3s4, _set____db54di) {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var this_0 = demo1$delegate;
    var property = demo1$factory();
    var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
    var tmp = tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs;
    // Inline function 'korlibs.datastructure.fastCastTo' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    setExtra(_this__u8e3s4, tmp, _set____db54di);
    return Unit_getInstance();
  }
  function get_demo1(_this__u8e3s4) {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var tmp$ret$6;
    $l$block: {
      // Inline function 'korlibs.datastructure.Property.getValue' call
      var this_0 = demo1$delegate;
      var property = demo1$factory_0();
      // Inline function 'korlibs.datastructure.fastCastTo' call
      var tmp56_safe_receiver = _this__u8e3s4.get_extra_ir3qw7_k$();
      var tmp;
      if (tmp56_safe_receiver == null) {
        tmp = null;
      } else {
        var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
        tmp = tmp56_safe_receiver.get_6bo4tg_k$(tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs);
      }
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var res = tmp;
      if (res == null) {
        var r = this_0.get_defaultGen_8y7pd4_k$()();
        var tmp_0;
        if (!(r == null)) {
          tmp_0 = true;
        } else {
          var tmp1_elvis_lhs = this_0.get_name_woqyms_k$();
          tmp_0 = hasExtra(_this__u8e3s4, tmp1_elvis_lhs == null ? property.callableName : tmp1_elvis_lhs);
        }
        if (tmp_0) {
          // Inline function 'korlibs.datastructure.Property.setValue' call
          var tmp0_elvis_lhs_0 = this_0.get_name_woqyms_k$();
          var tmp_1 = tmp0_elvis_lhs_0 == null ? property.callableName : tmp0_elvis_lhs_0;
          // Inline function 'korlibs.datastructure.fastCastTo' call
          // Inline function 'kotlin.js.unsafeCast' call
          // Inline function 'kotlin.js.asDynamic' call
          setExtra(_this__u8e3s4, tmp_1, r);
        }
        tmp$ret$6 = r;
        break $l$block;
      }
      tmp$ret$6 = res;
    }
    return tmp$ret$6;
  }
  var demo1$delegate;
  function get_demo(_this__u8e3s4) {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var tmp$ret$6;
    $l$block: {
      // Inline function 'korlibs.datastructure.Property.getValue' call
      var this_0 = demo$delegate;
      var property = demo$factory();
      // Inline function 'korlibs.datastructure.fastCastTo' call
      var tmp56_safe_receiver = _this__u8e3s4.get_extra_ir3qw7_k$();
      var tmp;
      if (tmp56_safe_receiver == null) {
        tmp = null;
      } else {
        var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
        tmp = tmp56_safe_receiver.get_6bo4tg_k$(tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs);
      }
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var res = tmp;
      if (res == null) {
        var r = this_0.get_defaultGen_8y7pd4_k$()();
        var tmp_0;
        if (!(r == null)) {
          tmp_0 = true;
        } else {
          var tmp1_elvis_lhs = this_0.get_name_woqyms_k$();
          tmp_0 = hasExtra(_this__u8e3s4, tmp1_elvis_lhs == null ? property.callableName : tmp1_elvis_lhs);
        }
        if (tmp_0) {
          // Inline function 'korlibs.datastructure.Property.setValue' call
          var tmp0_elvis_lhs_0 = this_0.get_name_woqyms_k$();
          var tmp_1 = tmp0_elvis_lhs_0 == null ? property.callableName : tmp0_elvis_lhs_0;
          // Inline function 'korlibs.datastructure.fastCastTo' call
          // Inline function 'kotlin.js.unsafeCast' call
          // Inline function 'kotlin.js.asDynamic' call
          setExtra(_this__u8e3s4, tmp_1, r);
        }
        tmp$ret$6 = r;
        break $l$block;
      }
      tmp$ret$6 = res;
    }
    return tmp$ret$6;
  }
  var demo$delegate;
  function set_demo2(_this__u8e3s4, _set____db54di) {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var this_0 = demo2$delegate;
    var property = demo2$factory();
    var value = this_0.get_transform_7ltst4_k$()(_this__u8e3s4, _set____db54di);
    var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
    setExtra(_this__u8e3s4, tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs, value);
    return Unit_getInstance();
  }
  function get_demo2(_this__u8e3s4) {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure.PropertyThis.getValue' call
      var this_0 = demo2$delegate;
      var property = demo2$factory_0();
      var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
      var res = getExtraTyped(_this__u8e3s4, tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs);
      if (res == null) {
        var r = this_0.get_defaultGen_8y7pd4_k$()(_this__u8e3s4);
        // Inline function 'korlibs.datastructure.PropertyThis.setValueUntransformed' call
        var tmp0_elvis_lhs_0 = this_0.get_name_woqyms_k$();
        setExtra(_this__u8e3s4, tmp0_elvis_lhs_0 == null ? property.callableName : tmp0_elvis_lhs_0, r);
        tmp$ret$0 = r;
        break $l$block;
      }
      tmp$ret$0 = res;
    }
    return tmp$ret$0;
  }
  var demo2$delegate;
  function Demo() {
    this.$$delegate_0__1 = new Mixin();
  }
  protoOf(Demo).set_extra_ulxxnb_k$ = function (_set____db54di) {
    this.$$delegate_0__1.set_extra_ulxxnb_k$(_set____db54di);
  };
  protoOf(Demo).get_extra_ir3qw7_k$ = function () {
    return this.$$delegate_0__1.get_extra_ir3qw7_k$();
  };
  function ExtraPropertyTest() {
  }
  protoOf(ExtraPropertyTest).test1_tbiuwh_k$ = function () {
    var demo = new Demo();
    assertEquals('hello', get_demo1(demo));
    set_demo1(demo, 'test');
    assertEquals('test', get_demo1(demo));
  };
  protoOf(ExtraPropertyTest).test2_6xsqgg_k$ = function () {
    var demo = new Demo();
    get_demo(demo).add_utx5q5_k$('hello');
    // Inline function 'kotlin.test.assertTrue' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.ExtraPropertyTest.test2.<anonymous>' call
    var tmp$ret$0 = get_demo(demo).contains_aljjnj_k$('hello');
    assertTrue(tmp$ret$0, null);
  };
  protoOf(ExtraPropertyTest).testTransformer_ss58yx_k$ = function () {
    var demo = new Demo();
    assertEquals(10, get_demo2(demo));
    set_demo2(demo, 12);
    assertEquals(10, get_demo2(demo));
    set_demo2(demo, 22);
    assertEquals(20, get_demo2(demo));
    set_demo2(demo, 39);
    assertEquals(30, get_demo2(demo));
  };
  function test_fun_izoufj_14() {
    suite('ExtraPropertyTest', false, test_fun$ExtraPropertyTest_test_fun_4y1o4t);
  }
  function demo1$delegate$lambda() {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    return 'hello';
  }
  function demo$delegate$lambda() {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    // Inline function 'kotlin.collections.linkedSetOf' call
    return LinkedHashSet_init_$Create$();
  }
  function demo2$delegate$lambda($this$extraPropertyThis, it) {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    return (it % 10 | 0) === 0 ? it : it - (it % 10 | 0) | 0;
  }
  function demo2$delegate$lambda_0($this$extraPropertyThis) {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    return 10;
  }
  function test_fun$ExtraPropertyTest_test_fun_4y1o4t() {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    test('test1', false, test_fun$ExtraPropertyTest_test_fun$test1_test_fun_8vtdm1);
    test('test2', false, test_fun$ExtraPropertyTest_test_fun$test2_test_fun_5mtetk);
    test('testTransformer', false, test_fun$ExtraPropertyTest_test_fun$testTransformer_test_fun_yo5kbl);
    return Unit_getInstance();
  }
  function test_fun$ExtraPropertyTest_test_fun$test1_test_fun_8vtdm1() {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var tmp = new ExtraPropertyTest();
    tmp.test1_tbiuwh_k$();
    return Unit_getInstance();
  }
  function test_fun$ExtraPropertyTest_test_fun$test2_test_fun_5mtetk() {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var tmp = new ExtraPropertyTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function test_fun$ExtraPropertyTest_test_fun$testTransformer_test_fun_yo5kbl() {
    _init_properties_ExtraPropertyTest_kt__gwn29n();
    var tmp = new ExtraPropertyTest();
    tmp.testTransformer_ss58yx_k$();
    return Unit_getInstance();
  }
  function demo1$factory() {
    return getPropertyCallableRef('demo1', 1, KMutableProperty1, function (receiver) {
      return get_demo1(receiver);
    }, function (receiver, value) {
      return set_demo1(receiver, value);
    });
  }
  function demo1$factory_0() {
    return getPropertyCallableRef('demo1', 1, KMutableProperty1, function (receiver) {
      return get_demo1(receiver);
    }, function (receiver, value) {
      return set_demo1(receiver, value);
    });
  }
  function demo$factory() {
    return getPropertyCallableRef('demo', 1, KProperty1, function (receiver) {
      return get_demo(receiver);
    }, null);
  }
  function demo2$factory() {
    return getPropertyCallableRef('demo2', 1, KMutableProperty1, function (receiver) {
      return get_demo2(receiver);
    }, function (receiver, value) {
      return set_demo2(receiver, value);
    });
  }
  function demo2$factory_0() {
    return getPropertyCallableRef('demo2', 1, KMutableProperty1, function (receiver) {
      return get_demo2(receiver);
    }, function (receiver, value) {
      return set_demo2(receiver, value);
    });
  }
  var properties_initialized_ExtraPropertyTest_kt_75cek9;
  function _init_properties_ExtraPropertyTest_kt__gwn29n() {
    if (!properties_initialized_ExtraPropertyTest_kt_75cek9) {
      properties_initialized_ExtraPropertyTest_kt_75cek9 = true;
      // Inline function 'korlibs.datastructure.extraProperty' call
      var default_0 = demo1$delegate$lambda;
      demo1$delegate = new Property(null, default_0);
      // Inline function 'korlibs.datastructure.extraProperty' call
      var default_1 = demo$delegate$lambda;
      demo$delegate = new Property(null, default_1);
      // Inline function 'korlibs.datastructure.extraPropertyThis' call
      var transform = demo2$delegate$lambda;
      var default_2 = demo2$delegate$lambda_0;
      // Inline function 'korlibs.datastructure.PropertyThis.withTransform' call
      var this_0 = new PropertyThis(null, default_2);
      this_0.set_transform_w84910_k$(transform);
      demo2$delegate = this_0;
    }
  }
  function set_demo(_this__u8e3s4, _set____db54di) {
    _init_properties_ExtraTest_kt__qe0eds();
    var this_0 = demo$delegate_0;
    var property = demo$factory_0();
    var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
    var tmp = tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs;
    // Inline function 'korlibs.datastructure.fastCastTo' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    setExtra(_this__u8e3s4, tmp, _set____db54di);
    return Unit_getInstance();
  }
  function get_demo_0(_this__u8e3s4) {
    _init_properties_ExtraTest_kt__qe0eds();
    var tmp$ret$6;
    $l$block: {
      // Inline function 'korlibs.datastructure.Property.getValue' call
      var this_0 = demo$delegate_0;
      var property = demo$factory_1();
      // Inline function 'korlibs.datastructure.fastCastTo' call
      var tmp56_safe_receiver = _this__u8e3s4.get_extra_ir3qw7_k$();
      var tmp;
      if (tmp56_safe_receiver == null) {
        tmp = null;
      } else {
        var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
        tmp = tmp56_safe_receiver.get_6bo4tg_k$(tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs);
      }
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var res = tmp;
      if (res == null) {
        var r = this_0.get_defaultGen_8y7pd4_k$()();
        var tmp_0;
        if (!(r == null)) {
          tmp_0 = true;
        } else {
          var tmp1_elvis_lhs = this_0.get_name_woqyms_k$();
          tmp_0 = hasExtra(_this__u8e3s4, tmp1_elvis_lhs == null ? property.callableName : tmp1_elvis_lhs);
        }
        if (tmp_0) {
          // Inline function 'korlibs.datastructure.Property.setValue' call
          var tmp0_elvis_lhs_0 = this_0.get_name_woqyms_k$();
          var tmp_1 = tmp0_elvis_lhs_0 == null ? property.callableName : tmp0_elvis_lhs_0;
          // Inline function 'korlibs.datastructure.fastCastTo' call
          // Inline function 'kotlin.js.unsafeCast' call
          // Inline function 'kotlin.js.asDynamic' call
          setExtra(_this__u8e3s4, tmp_1, r);
        }
        tmp$ret$6 = r;
        break $l$block;
      }
      tmp$ret$6 = res;
    }
    return tmp$ret$6;
  }
  var demo$delegate_0;
  function set_demo2_0(_this__u8e3s4, _set____db54di) {
    _init_properties_ExtraTest_kt__qe0eds();
    var this_0 = demo2$delegate_0;
    var property = demo2$factory_1();
    var value = this_0.get_transform_7ltst4_k$()(_this__u8e3s4, _set____db54di);
    var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
    setExtra(_this__u8e3s4, tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs, value);
    return Unit_getInstance();
  }
  function get_demo2_0(_this__u8e3s4) {
    _init_properties_ExtraTest_kt__qe0eds();
    var tmp$ret$0;
    $l$block: {
      // Inline function 'korlibs.datastructure.PropertyThis.getValue' call
      var this_0 = demo2$delegate_0;
      var property = demo2$factory_2();
      var tmp0_elvis_lhs = this_0.get_name_woqyms_k$();
      var res = getExtraTyped(_this__u8e3s4, tmp0_elvis_lhs == null ? property.callableName : tmp0_elvis_lhs);
      if (res == null) {
        var r = this_0.get_defaultGen_8y7pd4_k$()(_this__u8e3s4);
        // Inline function 'korlibs.datastructure.PropertyThis.setValueUntransformed' call
        var tmp0_elvis_lhs_0 = this_0.get_name_woqyms_k$();
        setExtra(_this__u8e3s4, tmp0_elvis_lhs_0 == null ? property.callableName : tmp0_elvis_lhs_0, r);
        tmp$ret$0 = r;
        break $l$block;
      }
      tmp$ret$0 = res;
    }
    return tmp$ret$0;
  }
  var demo2$delegate_0;
  function ExtraTestDemo() {
    this.$$delegate_0__1 = new Mixin();
    this.default_1 = 9;
  }
  protoOf(ExtraTestDemo).set_extra_ulxxnb_k$ = function (_set____db54di) {
    this.$$delegate_0__1.set_extra_ulxxnb_k$(_set____db54di);
  };
  protoOf(ExtraTestDemo).get_extra_ir3qw7_k$ = function () {
    return this.$$delegate_0__1.get_extra_ir3qw7_k$();
  };
  protoOf(ExtraTestDemo).get_default_qtagd4_k$ = function () {
    return this.default_1;
  };
  function ExtraTest() {
  }
  protoOf(ExtraTest).name_ugbo79_k$ = function () {
    var demo = new ExtraTestDemo();
    assertEquals(0, get_demo_0(demo));
    assertEquals(9, get_demo2_0(demo));
    set_demo(demo, 7);
    assertEquals(7, get_demo_0(demo));
    var tmp = mapOf([to('demo', 7), to('demo2', 9)]);
    var tmp0_safe_receiver = demo.get_extra_ir3qw7_k$();
    assertEquals(tmp, tmp0_safe_receiver == null ? null : toMap_0(tmp0_safe_receiver));
  };
  function test_fun_izoufj_15() {
    suite('ExtraTest', false, test_fun$ExtraTest_test_fun_6v9jcy);
  }
  function demo$delegate$lambda_0() {
    _init_properties_ExtraTest_kt__qe0eds();
    return 0;
  }
  function demo2$delegate$lambda_1($this$PropertyThis) {
    _init_properties_ExtraTest_kt__qe0eds();
    return $this$PropertyThis.default_1;
  }
  function test_fun$ExtraTest_test_fun_6v9jcy() {
    _init_properties_ExtraTest_kt__qe0eds();
    test('name', true, test_fun$ExtraTest_test_fun$name_test_fun_aeazng);
    return Unit_getInstance();
  }
  function test_fun$ExtraTest_test_fun$name_test_fun_aeazng() {
    _init_properties_ExtraTest_kt__qe0eds();
    var tmp = new ExtraTest();
    tmp.name_ugbo79_k$();
    return Unit_getInstance();
  }
  function demo$factory_0() {
    return getPropertyCallableRef('demo', 1, KMutableProperty1, function (receiver) {
      return get_demo_0(receiver);
    }, function (receiver, value) {
      return set_demo(receiver, value);
    });
  }
  function demo$factory_1() {
    return getPropertyCallableRef('demo', 1, KMutableProperty1, function (receiver) {
      return get_demo_0(receiver);
    }, function (receiver, value) {
      return set_demo(receiver, value);
    });
  }
  function demo2$factory_1() {
    return getPropertyCallableRef('demo2', 1, KMutableProperty1, function (receiver) {
      return get_demo2_0(receiver);
    }, function (receiver, value) {
      return set_demo2_0(receiver, value);
    });
  }
  function demo2$factory_2() {
    return getPropertyCallableRef('demo2', 1, KMutableProperty1, function (receiver) {
      return get_demo2_0(receiver);
    }, function (receiver, value) {
      return set_demo2_0(receiver, value);
    });
  }
  var properties_initialized_ExtraTest_kt_v1lydq;
  function _init_properties_ExtraTest_kt__qe0eds() {
    if (!properties_initialized_ExtraTest_kt_v1lydq) {
      properties_initialized_ExtraTest_kt_v1lydq = true;
      demo$delegate_0 = new Property(VOID, demo$delegate$lambda_0);
      demo2$delegate_0 = new PropertyThis(VOID, demo2$delegate$lambda_1);
    }
  }
  function FastArrayListTest() {
  }
  protoOf(FastArrayListTest).test_w5z8la_k$ = function () {
    var fal = FastArrayList_init_$Create$();
    fal.add_utx5q5_k$(1);
    fal.add_utx5q5_k$(2);
    fal.add_utx5q5_k$(3);
    fal.add_utx5q5_k$(4);
    assertEquals(0, fal.indexOf_si1fv9_k$(1));
    assertEquals(1, fal.indexOf_si1fv9_k$(2));
    assertEquals(2, fal.indexOf_si1fv9_k$(3));
    assertEquals(3, fal.indexOf_si1fv9_k$(4));
    assertEquals('[1, 2, 3, 4]', fal.toString());
    fal.removeAt_6niowx_k$(1);
    assertEquals('[1, 3, 4]', fal.toString());
    fal.removeAt_6niowx_k$(1);
    assertEquals('[1, 4]', fal.toString());
    fal.removeAt_6niowx_k$(0);
    assertEquals('[4]', fal.toString());
    fal.removeAt_6niowx_k$(0);
    assertEquals('[]', fal.toString());
    assertEquals(-1, fal.indexOf_si1fv9_k$(1));
    assertEquals(-1, fal.indexOf_si1fv9_k$(2));
    assertEquals(-1, fal.indexOf_si1fv9_k$(3));
    assertEquals(-1, fal.indexOf_si1fv9_k$(4));
    fal.add_dl6gt3_k$(0, 4);
    fal.add_dl6gt3_k$(0, 3);
    fal.add_dl6gt3_k$(0, 1);
    fal.add_dl6gt3_k$(1, 2);
    assertEquals('[1, 2, 3, 4]', fal.toString());
  };
  protoOf(FastArrayListTest).testAddLast_jtdxij_k$ = function () {
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    var a = ArrayList_init_$Create$(1);
    a.add_dl6gt3_k$(0, 0);
    a.add_dl6gt3_k$(1, 1);
    assertEquals(listOf([0, 1]), toList_0(a));
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    var a_0 = FastArrayList_init_$Create$_0(1);
    a_0.add_dl6gt3_k$(0, 0);
    a_0.add_dl6gt3_k$(1, 1);
    assertEquals(listOf([0, 1]), toList_0(a_0));
  };
  protoOf(FastArrayListTest).testToFastList_srs7br_k$ = function () {
    assertEquals(listOf([1, 2, 3]), toFastList(listOf([1, 2, 3]), FastArrayList_init_$Create$()));
    assertEquals(listOf([1, 2, 3]), toFastList(listOf([1, 2, 3]), FastArrayList_init_$Create$_1(listOf([-1, -2, -3]))));
    assertEquals(listOf([1, 2, 3]), toFastList(listOf([1, 2, 3]), FastArrayList_init_$Create$_1(listOf_0(-1))));
    assertEquals(listOf([1, 2, 3]), toFastList(listOf([1, 2, 3]), FastArrayList_init_$Create$_1(listOf([-1, -2, -3, -4]))));
  };
  protoOf(FastArrayListTest).testRemove_ldo5qy_k$ = function () {
    var list = fastArrayListOf([1, 2, 3]);
    assertEquals(true, list.remove_cedx0m_k$(2));
    assertEquals(listOf([1, 3]), list);
    assertEquals(false, list.remove_cedx0m_k$(2));
    assertEquals(listOf([1, 3]), list);
  };
  protoOf(FastArrayListTest).testRemoveAt_b7djuh_k$ = function () {
    var list = fastArrayListOf([1, 2, 3, 4, 5]);
    list.removeAt_6niowx_k$(4);
    assertEquals(listOf([1, 2, 3, 4]), list);
    list.removeAt_6niowx_k$(0);
    assertEquals(listOf([2, 3, 4]), list);
    list.removeAt_6niowx_k$(1);
    assertEquals(listOf([2, 4]), list);
  };
  protoOf(FastArrayListTest).testClear_gyx0xh_k$ = function () {
    var list = fastArrayListOf([1, 2, 3]);
    assertEquals(listOf([1, 2, 3]), list);
    list.clear_j9egeb_k$();
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$0 = emptyList();
    assertEquals(tmp$ret$0, list);
    list.add_utx5q5_k$(1);
    assertEquals(listOf_0(1), list);
  };
  protoOf(FastArrayListTest).testToArrayIsACopy_8z8z80_k$ = function () {
    var list = fastArrayListOf([1, 2, 3]);
    // Inline function 'kotlin.collections.toTypedArray' call
    var list2 = copyToArray(list);
    list.set_82063s_k$(0, -1);
    // Inline function 'kotlin.collections.toTypedArray' call
    var list3 = copyToArray(list);
    assertEquals(listOf([1, 2, 3]), toList_1(list2));
    assertEquals(listOf([-1, 2, 3]), toList_1(list3));
  };
  protoOf(FastArrayListTest).testAddAll_aao8te_k$ = function () {
    var tmp = listOf([1, 2, 3, 4, 5, 6]);
    // Inline function 'kotlin.also' call
    var this_0 = fastArrayListOf([1, 2, 3]);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastArrayListTest.testAddAll.<anonymous>' call
    this_0.addAll_6hs4wt_k$(fastArrayListOf([4, 5, 6]));
    assertEquals(tmp, this_0);
  };
  function test_fun_izoufj_16() {
    suite('FastArrayListTest', false, test_fun$FastArrayListTest_test_fun_hz2mi1);
  }
  function test_fun$FastArrayListTest_test_fun_hz2mi1() {
    test('test', false, test_fun$FastArrayListTest_test_fun$test_test_fun_c2bipe);
    test('testAddLast', false, test_fun$FastArrayListTest_test_fun$testAddLast_test_fun_hr9s7);
    test('testToFastList', false, test_fun$FastArrayListTest_test_fun$testToFastList_test_fun_yqebh);
    test('testRemove', false, test_fun$FastArrayListTest_test_fun$testRemove_test_fun_9yscc2);
    test('testRemoveAt', false, test_fun$FastArrayListTest_test_fun$testRemoveAt_test_fun_i44pjv);
    test('testClear', false, test_fun$FastArrayListTest_test_fun$testClear_test_fun_buo4ar);
    test('testToArrayIsACopy', false, test_fun$FastArrayListTest_test_fun$testToArrayIsACopy_test_fun_k2mmqk);
    test('testAddAll', false, test_fun$FastArrayListTest_test_fun$testAddAll_test_fun_7jlv0y);
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$test_test_fun_c2bipe() {
    var tmp = new FastArrayListTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$testAddLast_test_fun_hr9s7() {
    var tmp = new FastArrayListTest();
    tmp.testAddLast_jtdxij_k$();
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$testToFastList_test_fun_yqebh() {
    var tmp = new FastArrayListTest();
    tmp.testToFastList_srs7br_k$();
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$testRemove_test_fun_9yscc2() {
    var tmp = new FastArrayListTest();
    tmp.testRemove_ldo5qy_k$();
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$testRemoveAt_test_fun_i44pjv() {
    var tmp = new FastArrayListTest();
    tmp.testRemoveAt_b7djuh_k$();
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$testClear_test_fun_buo4ar() {
    var tmp = new FastArrayListTest();
    tmp.testClear_gyx0xh_k$();
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$testToArrayIsACopy_test_fun_k2mmqk() {
    var tmp = new FastArrayListTest();
    tmp.testToArrayIsACopy_8z8z80_k$();
    return Unit_getInstance();
  }
  function test_fun$FastArrayListTest_test_fun$testAddAll_test_fun_7jlv0y() {
    var tmp = new FastArrayListTest();
    tmp.testAddAll_aao8te_k$();
    return Unit_getInstance();
  }
  function test$getOne(map) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut' call
      if ('one' === map.get_fast1K_1hx0as_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast1V_nrz92p_k$());
        break $l$block_0;
      }
      if ('one' === map.get_fast2K_i2m8bh_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast2V_rolka6_k$());
        break $l$block_0;
      }
      var tmp$ret$1;
      $l$block_1: {
        // Inline function 'korlibs.datastructure.getOrPut' call
        var this_0 = map.get_cache_w0nr7m_k$();
        var res = get(this_0, 'one');
        if (!(res == null)) {
          tmp$ret$1 = res;
          break $l$block_1;
        }
        // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut.<anonymous>' call
        // Inline function 'korlibs.datastructure.FastIdentityCacheMapTest.test.getOne.<anonymous>' call
        var out = 1;
        set(this_0, 'one', out);
        tmp$ret$1 = out;
      }
      var value = tmp$ret$1;
      map.set_fast2K_sibyfc_k$(map.get_fast1K_1hx0as_k$());
      map.set_fast2V_j6v3zm_k$(map.get_fast1V_nrz92p_k$());
      map.set_fast1K_sa9wg7_k$('one');
      map.set_fast1V_iyt20h_k$(value);
      tmp$ret$0 = value;
    }
    return tmp$ret$0;
  }
  function test$getTwo(map) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut' call
      if ('two' === map.get_fast1K_1hx0as_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast1V_nrz92p_k$());
        break $l$block_0;
      }
      if ('two' === map.get_fast2K_i2m8bh_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast2V_rolka6_k$());
        break $l$block_0;
      }
      var tmp$ret$1;
      $l$block_1: {
        // Inline function 'korlibs.datastructure.getOrPut' call
        var this_0 = map.get_cache_w0nr7m_k$();
        var res = get(this_0, 'two');
        if (!(res == null)) {
          tmp$ret$1 = res;
          break $l$block_1;
        }
        // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut.<anonymous>' call
        // Inline function 'korlibs.datastructure.FastIdentityCacheMapTest.test.getTwo.<anonymous>' call
        var out = 2;
        set(this_0, 'two', out);
        tmp$ret$1 = out;
      }
      var value = tmp$ret$1;
      map.set_fast2K_sibyfc_k$(map.get_fast1K_1hx0as_k$());
      map.set_fast2V_j6v3zm_k$(map.get_fast1V_nrz92p_k$());
      map.set_fast1K_sa9wg7_k$('two');
      map.set_fast1V_iyt20h_k$(value);
      tmp$ret$0 = value;
    }
    return tmp$ret$0;
  }
  function test$getThree(map) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut' call
      if ('three' === map.get_fast1K_1hx0as_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast1V_nrz92p_k$());
        break $l$block_0;
      }
      if ('three' === map.get_fast2K_i2m8bh_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast2V_rolka6_k$());
        break $l$block_0;
      }
      var tmp$ret$1;
      $l$block_1: {
        // Inline function 'korlibs.datastructure.getOrPut' call
        var this_0 = map.get_cache_w0nr7m_k$();
        var res = get(this_0, 'three');
        if (!(res == null)) {
          tmp$ret$1 = res;
          break $l$block_1;
        }
        // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut.<anonymous>' call
        // Inline function 'korlibs.datastructure.FastIdentityCacheMapTest.test.getThree.<anonymous>' call
        var out = 3;
        set(this_0, 'three', out);
        tmp$ret$1 = out;
      }
      var value = tmp$ret$1;
      map.set_fast2K_sibyfc_k$(map.get_fast1K_1hx0as_k$());
      map.set_fast2V_j6v3zm_k$(map.get_fast1V_nrz92p_k$());
      map.set_fast1K_sa9wg7_k$('three');
      map.set_fast1V_iyt20h_k$(value);
      tmp$ret$0 = value;
    }
    return tmp$ret$0;
  }
  function test$getMOne(map) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut' call
      if ('one' === map.get_fast1K_1hx0as_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast1V_nrz92p_k$());
        break $l$block_0;
      }
      if ('one' === map.get_fast2K_i2m8bh_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast2V_rolka6_k$());
        break $l$block_0;
      }
      var tmp$ret$1;
      $l$block_1: {
        // Inline function 'korlibs.datastructure.getOrPut' call
        var this_0 = map.get_cache_w0nr7m_k$();
        var res = get(this_0, 'one');
        if (!(res == null)) {
          tmp$ret$1 = res;
          break $l$block_1;
        }
        // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut.<anonymous>' call
        // Inline function 'korlibs.datastructure.FastIdentityCacheMapTest.test.getMOne.<anonymous>' call
        var out = -1;
        set(this_0, 'one', out);
        tmp$ret$1 = out;
      }
      var value = tmp$ret$1;
      map.set_fast2K_sibyfc_k$(map.get_fast1K_1hx0as_k$());
      map.set_fast2V_j6v3zm_k$(map.get_fast1V_nrz92p_k$());
      map.set_fast1K_sa9wg7_k$('one');
      map.set_fast1V_iyt20h_k$(value);
      tmp$ret$0 = value;
    }
    return tmp$ret$0;
  }
  function test$getMTwo(map) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut' call
      if ('two' === map.get_fast1K_1hx0as_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast1V_nrz92p_k$());
        break $l$block_0;
      }
      if ('two' === map.get_fast2K_i2m8bh_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast2V_rolka6_k$());
        break $l$block_0;
      }
      var tmp$ret$1;
      $l$block_1: {
        // Inline function 'korlibs.datastructure.getOrPut' call
        var this_0 = map.get_cache_w0nr7m_k$();
        var res = get(this_0, 'two');
        if (!(res == null)) {
          tmp$ret$1 = res;
          break $l$block_1;
        }
        // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut.<anonymous>' call
        // Inline function 'korlibs.datastructure.FastIdentityCacheMapTest.test.getMTwo.<anonymous>' call
        var out = -2;
        set(this_0, 'two', out);
        tmp$ret$1 = out;
      }
      var value = tmp$ret$1;
      map.set_fast2K_sibyfc_k$(map.get_fast1K_1hx0as_k$());
      map.set_fast2V_j6v3zm_k$(map.get_fast1V_nrz92p_k$());
      map.set_fast1K_sa9wg7_k$('two');
      map.set_fast1V_iyt20h_k$(value);
      tmp$ret$0 = value;
    }
    return tmp$ret$0;
  }
  function test$getMThree(map) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut' call
      if ('three' === map.get_fast1K_1hx0as_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast1V_nrz92p_k$());
        break $l$block_0;
      }
      if ('three' === map.get_fast2K_i2m8bh_k$()) {
        tmp$ret$0 = ensureNotNull(map.get_fast2V_rolka6_k$());
        break $l$block_0;
      }
      var tmp$ret$1;
      $l$block_1: {
        // Inline function 'korlibs.datastructure.getOrPut' call
        var this_0 = map.get_cache_w0nr7m_k$();
        var res = get(this_0, 'three');
        if (!(res == null)) {
          tmp$ret$1 = res;
          break $l$block_1;
        }
        // Inline function 'korlibs.datastructure.FastIdentityCacheMap.getOrPut.<anonymous>' call
        // Inline function 'korlibs.datastructure.FastIdentityCacheMapTest.test.getMThree.<anonymous>' call
        var out = -3;
        set(this_0, 'three', out);
        tmp$ret$1 = out;
      }
      var value = tmp$ret$1;
      map.set_fast2K_sibyfc_k$(map.get_fast1K_1hx0as_k$());
      map.set_fast2V_j6v3zm_k$(map.get_fast1V_nrz92p_k$());
      map.set_fast1K_sa9wg7_k$('three');
      map.set_fast1V_iyt20h_k$(value);
      tmp$ret$0 = value;
    }
    return tmp$ret$0;
  }
  function FastIdentityCacheMapTest() {
  }
  protoOf(FastIdentityCacheMapTest).test_w5z8la_k$ = function () {
    var map = new FastIdentityCacheMap();
    var inductionVariable = 1;
    if (inductionVariable <= 3)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        map.clear_j9egeb_k$();
        assertEquals(1, test$getOne(map));
        assertEquals(1, test$getMOne(map));
        if (n >= 2) {
          assertEquals(2, test$getTwo(map));
          assertEquals(2, test$getMTwo(map));
        }
        if (n >= 3) {
          assertEquals(3, test$getThree(map));
          assertEquals(3, test$getMThree(map));
        }
        map.clear_j9egeb_k$();
        assertEquals(-1, test$getMOne(map));
        assertEquals(-1, test$getOne(map));
        if (n >= 2) {
          assertEquals(-2, test$getMTwo(map));
          assertEquals(-2, test$getTwo(map));
        }
        if (n >= 3) {
          assertEquals(-3, test$getMThree(map));
          assertEquals(-3, test$getThree(map));
        }
      }
       while (inductionVariable <= 3);
  };
  function test_fun_izoufj_17() {
    suite('FastIdentityCacheMapTest', false, test_fun$FastIdentityCacheMapTest_test_fun_xzq5zc);
  }
  function test_fun$FastIdentityCacheMapTest_test_fun_xzq5zc() {
    test('test', false, test_fun$FastIdentityCacheMapTest_test_fun$test_test_fun_1lejwz);
    return Unit_getInstance();
  }
  function test_fun$FastIdentityCacheMapTest_test_fun$test_test_fun_1lejwz() {
    var tmp = new FastIdentityCacheMapTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function FastIdentityMapTest$test$Demo() {
  }
  protoOf(FastIdentityMapTest$test$Demo).hashCode = function () {
    return 0;
  };
  function FastIdentityMapTest() {
  }
  protoOf(FastIdentityMapTest).test_w5z8la_k$ = function () {
    var map = FastIdentityMap();
    var i1 = new FastIdentityMapTest$test$Demo();
    var i2 = new FastIdentityMapTest$test$Demo();
    var i3 = new FastIdentityMapTest$test$Demo();
    set(map, i1, 'i1');
    set(map, i2, 'i2');
    assertEquals('i1', get(map, i1));
    assertEquals('i2', get(map, i2));
    assertEquals(null, get(map, i3));
    assertEquals(2, get_size(map));
    assertEquals(listOf(['i1', 'i2']), sorted(get_values(map)));
    set(map, i1, 'i1b');
    set(map, i2, 'i2b');
    assertEquals('i1b', get(map, i1));
    assertEquals('i2b', get(map, i2));
    assertEquals(null, get(map, i3));
    assertEquals(2, get_size(map));
    assertEquals(listOf(['i1b', 'i2b']), sorted(get_values(map)));
    remove(map, i1);
    assertEquals(null, get(map, i1));
    assertEquals('i2b', get(map, i2));
    assertEquals(null, get(map, i3));
    assertEquals(1, get_size(map));
    assertEquals(listOf_0('i2b'), sorted(get_values(map)));
  };
  function test_fun_izoufj_18() {
    suite('FastIdentityMapTest', false, test_fun$FastIdentityMapTest_test_fun_lg54b4);
  }
  function test_fun$FastIdentityMapTest_test_fun_lg54b4() {
    test('test', false, test_fun$FastIdentityMapTest_test_fun$test_test_fun_13gedx);
    return Unit_getInstance();
  }
  function test_fun$FastIdentityMapTest_test_fun$test_test_fun_13gedx() {
    var tmp = new FastIdentityMapTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function sam$kotlin_Comparator$0(function_0) {
    this.function_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0).compare_bczr_k$ = function (a, b) {
    return this.function_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0).compare = function (a, b) {
    return this.compare_bczr_k$(a, b);
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
  function FastMapTest$testStringForeach$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    // Inline function 'korlibs.datastructure.FastMapTest.testStringForeach.<anonymous>' call
    var tmp = a.get_key_18j28a_k$();
    // Inline function 'korlibs.datastructure.FastMapTest.testStringForeach.<anonymous>' call
    var tmp$ret$1 = b.get_key_18j28a_k$();
    return compareValues(tmp, tmp$ret$1);
  }
  function FastMapTest$testIntForeach$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    // Inline function 'korlibs.datastructure.FastMapTest.testIntForeach.<anonymous>' call
    var tmp = a.get_key_18j28a_k$();
    // Inline function 'korlibs.datastructure.FastMapTest.testIntForeach.<anonymous>' call
    var tmp$ret$1 = b.get_key_18j28a_k$();
    return compareValues(tmp, tmp$ret$1);
  }
  function FastMapTest() {
  }
  protoOf(FastMapTest).testInt_uxerir_k$ = function () {
    var map = FastIntMap();
    assertEquals(0, get_size_0(map));
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set(1, 'a');
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set(2, 'b');
    assertEquals(listOf([1, 2]), sorted(get_keys(map)));
    assertEquals(2, get_size_0(map));
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$3 = map.get(1);
    assertEquals('a', tmp$ret$3);
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$5 = map.get(2);
    assertEquals('b', tmp$ret$5);
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$7 = map.get(3);
    assertEquals(null, tmp$ret$7);
    // Inline function 'korlibs.datastructure.remove' call
    // Inline function 'kotlin.js.asDynamic' call
    map.delete(1);
    assertEquals(1, get_size_0(map));
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$10 = map.get(1);
    assertEquals(null, tmp$ret$10);
    var inductionVariable = 10;
    if (inductionVariable < 20)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.set' call
        var value = '' + n;
        // Inline function 'kotlin.js.asDynamic' call
        map.set(n, value);
      }
       while (inductionVariable < 20);
    assertEquals(11, get_size_0(map));
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$13 = map.get(15);
    assertEquals('15', tmp$ret$13);
    // Inline function 'korlibs.datastructure.removeRange' call
    var _iterator__ex2g4s = get_keys(map).iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var key = _iterator__ex2g4s.next_20eer_k$();
      if (5 <= key ? key <= 17 : false) {
        // Inline function 'korlibs.datastructure.remove' call
        // Inline function 'kotlin.js.asDynamic' call
        map.delete(key);
      }
    }
    assertEquals(3, get_size_0(map));
    assertEquals(listOf([2, 18, 19]), sorted(get_keys(map)));
    // Inline function 'korlibs.datastructure.clear' call
    // Inline function 'kotlin.js.asDynamic' call
    map.clear();
    assertEquals(0, get_size_0(map));
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$16 = emptyList();
    assertEquals(tmp$ret$16, sorted(get_keys(map)));
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set(0, 'z');
    assertEquals(1, get_size_0(map));
    assertEquals(listOf_0(0), sorted(get_keys(map)));
    assertEquals(listOf_0('z'), values(map));
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set(1, 'y');
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set(2, 'r');
    // Inline function 'korlibs.datastructure.removeRange' call
    var _iterator__ex2g4s_0 = get_keys(map).iterator_jk1svi_k$();
    while (_iterator__ex2g4s_0.hasNext_bitz1p_k$()) {
      var key_0 = _iterator__ex2g4s_0.next_20eer_k$();
      if (-1 <= key_0 ? key_0 <= 1 : false) {
        // Inline function 'korlibs.datastructure.remove' call
        // Inline function 'kotlin.js.asDynamic' call
        map.delete(key_0);
      }
    }
    assertEquals(1, get_size_0(map));
    assertEquals(listOf_0(2), sorted(get_keys(map)));
    assertEquals(listOf_0('r'), values(map));
  };
  protoOf(FastMapTest).testString_ax7zoz_k$ = function () {
    var map = FastStringMap();
    assertEquals(0, get_size_1(map));
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set('a', 1);
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set('b', 2);
    assertEquals(2, get_size_1(map));
    assertEquals(listOf(['a', 'b']), sorted(get_keys_0(map)));
    assertEquals(listOf([1, 2]), sorted(get_values_0(map)));
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$7 = map.get('a');
    assertEquals(1, tmp$ret$7);
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$9 = map.get('b');
    assertEquals(2, tmp$ret$9);
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$11 = map.get('c');
    assertEquals(null, tmp$ret$11);
    // Inline function 'korlibs.datastructure.contains' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$13 = map.has('a');
    assertEquals(true, tmp$ret$13);
    // Inline function 'korlibs.datastructure.contains' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$15 = map.has('c');
    assertEquals(false, tmp$ret$15);
    // Inline function 'korlibs.datastructure.clear' call
    // Inline function 'kotlin.js.asDynamic' call
    map.clear();
    assertEquals(0, get_size_1(map));
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$17 = emptyList();
    assertEquals(tmp$ret$17, sorted(get_keys_0(map)));
    // Inline function 'kotlin.collections.listOf' call
    var tmp$ret$18 = emptyList();
    assertEquals(tmp$ret$18, sorted(get_values_0(map)));
    // Inline function 'korlibs.datastructure.get' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$20 = map.get('a');
    assertEquals(null, tmp$ret$20);
    var tmp$ret$23;
    $l$block: {
      // Inline function 'korlibs.datastructure.getOrPut' call
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var res = map.get('a');
      if (!(res == null)) {
        tmp$ret$23 = res;
        break $l$block;
      }
      // Inline function 'korlibs.datastructure.FastMapTest.testString.<anonymous>' call
      var out = 10;
      // Inline function 'korlibs.datastructure.set' call
      // Inline function 'kotlin.run' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.js.asDynamic' call
      map.set('a', out);
      tmp$ret$23 = out;
    }
    assertEquals(10, tmp$ret$23);
    var tmp$ret$30;
    $l$block_0: {
      // Inline function 'korlibs.datastructure.getOrPut' call
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var res_0 = map.get('a');
      if (!(res_0 == null)) {
        tmp$ret$30 = res_0;
        break $l$block_0;
      }
      // Inline function 'korlibs.datastructure.FastMapTest.testString.<anonymous>' call
      var out_0 = 11;
      // Inline function 'korlibs.datastructure.set' call
      // Inline function 'kotlin.run' call
      // Inline function 'kotlin.contracts.contract' call
      // Inline function 'kotlin.js.asDynamic' call
      map.set('a', out_0);
      tmp$ret$30 = out_0;
    }
    assertEquals(10, tmp$ret$30);
    assertEquals(1, get_size_1(map));
    // Inline function 'korlibs.datastructure.remove' call
    // Inline function 'kotlin.js.asDynamic' call
    map.delete('a');
    // Inline function 'korlibs.datastructure.remove' call
    // Inline function 'kotlin.js.asDynamic' call
    map.delete('b');
    assertEquals(0, get_size_1(map));
  };
  protoOf(FastMapTest).testStringForeach_pmew1j_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = FastStringMap();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastMapTest.testStringForeach.<anonymous>' call
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    this_0.set('a', 1);
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    this_0.set('b', 2);
    var map = this_0;
    var other = HashMap_init_$Create$();
    // Inline function 'korlibs.datastructure.fastForEach' call
    // Inline function 'korlibs.datastructure.fastKeyForEach' call
    // Inline function 'kotlin.js.asDynamic' call
    var mapIterator = map.keys();
    $l$loop: while (true) {
      var v = mapIterator.next();
      if (v.done)
        break $l$loop;
      // Inline function 'korlibs.datastructure.fastForEach.<anonymous>' call
      var it = v.value;
      // Inline function 'korlibs.datastructure.FastMapTest.testStringForeach.<anonymous>' call
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$9 = map.get(it);
      var value = ensureNotNull(tmp$ret$9);
      // Inline function 'kotlin.collections.set' call
      var value_0 = ensureNotNull(value);
      other.put_4fpzoq_k$(it, value_0);
    }
    // Inline function 'kotlin.collections.associate' call
    // Inline function 'kotlin.collections.sortedBy' call
    var this_1 = other.get_entries_p20ztl_k$();
    // Inline function 'kotlin.comparisons.compareBy' call
    var tmp = FastMapTest$testStringForeach$lambda;
    var tmp$ret$10 = new sam$kotlin_Comparator$0(tmp);
    var tmp$ret$11 = sortedWith(this_1, tmp$ret$10);
    var this_2 = toList_0(tmp$ret$11);
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_2, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var tmp0_iterator = this_2.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'kotlin.collections.plusAssign' call
      // Inline function 'korlibs.datastructure.FastMapTest.testStringForeach.<anonymous>' call
      var pair = to(element.get_key_18j28a_k$(), element.get_value_j01efc_k$());
      destination.put_4fpzoq_k$(pair.get_first_irdx8n_k$(), pair.get_second_jf7fjx_k$());
    }
    var other2 = destination;
    assertEquals(mapOf([to('a', 1), to('b', 2)]), other2);
  };
  protoOf(FastMapTest).testIntForeach_dorp31_k$ = function () {
    // Inline function 'kotlin.apply' call
    var this_0 = FastIntMap();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastMapTest.testIntForeach.<anonymous>' call
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.js.asDynamic' call
    this_0.set(1, 'a');
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.js.asDynamic' call
    this_0.set(2, 'b');
    var map = this_0;
    var other = HashMap_init_$Create$();
    // Inline function 'korlibs.datastructure.fastForEach' call
    // Inline function 'korlibs.datastructure.fastKeyForEach' call
    // Inline function 'kotlin.js.asDynamic' call
    var mapIterator = map.keys();
    $l$loop: while (true) {
      var v = mapIterator.next();
      if (v.done)
        break $l$loop;
      // Inline function 'korlibs.datastructure.fastForEach.<anonymous>' call
      var it = v.value;
      // Inline function 'korlibs.datastructure.FastMapTest.testIntForeach.<anonymous>' call
      // Inline function 'korlibs.datastructure.get' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$5 = map.get(it);
      var value = ensureNotNull(tmp$ret$5);
      // Inline function 'kotlin.collections.set' call
      var value_0 = ensureNotNull(value);
      other.put_4fpzoq_k$(it, value_0);
    }
    // Inline function 'kotlin.collections.associate' call
    // Inline function 'kotlin.collections.sortedBy' call
    var this_1 = other.get_entries_p20ztl_k$();
    // Inline function 'kotlin.comparisons.compareBy' call
    var tmp = FastMapTest$testIntForeach$lambda;
    var tmp$ret$6 = new sam$kotlin_Comparator$0_0(tmp);
    var tmp$ret$7 = sortedWith(this_1, tmp$ret$6);
    var this_2 = toList_0(tmp$ret$7);
    var capacity = coerceAtLeast(mapCapacity(collectionSizeOrDefault(this_2, 10)), 16);
    // Inline function 'kotlin.collections.associateTo' call
    var destination = LinkedHashMap_init_$Create$(capacity);
    var tmp0_iterator = this_2.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var element = tmp0_iterator.next_20eer_k$();
      // Inline function 'kotlin.collections.plusAssign' call
      // Inline function 'korlibs.datastructure.FastMapTest.testIntForeach.<anonymous>' call
      var pair = to(element.get_key_18j28a_k$(), element.get_value_j01efc_k$());
      destination.put_4fpzoq_k$(pair.get_first_irdx8n_k$(), pair.get_second_jf7fjx_k$());
    }
    var other2 = destination;
    assertEquals(mapOf([to(1, 'a'), to(2, 'b')]), other2);
  };
  function test_fun_izoufj_19() {
    suite('FastMapTest', false, test_fun$FastMapTest_test_fun_l8xnf2);
  }
  function test_fun$FastMapTest_test_fun_l8xnf2() {
    test('testInt', false, test_fun$FastMapTest_test_fun$testInt_test_fun_i513iy);
    test('testString', false, test_fun$FastMapTest_test_fun$testString_test_fun_a3d1dw);
    test('testStringForeach', false, test_fun$FastMapTest_test_fun$testStringForeach_test_fun_hn3fh8);
    test('testIntForeach', false, test_fun$FastMapTest_test_fun$testIntForeach_test_fun_tmmawi);
    return Unit_getInstance();
  }
  function test_fun$FastMapTest_test_fun$testInt_test_fun_i513iy() {
    var tmp = new FastMapTest();
    tmp.testInt_uxerir_k$();
    return Unit_getInstance();
  }
  function test_fun$FastMapTest_test_fun$testString_test_fun_a3d1dw() {
    var tmp = new FastMapTest();
    tmp.testString_ax7zoz_k$();
    return Unit_getInstance();
  }
  function test_fun$FastMapTest_test_fun$testStringForeach_test_fun_hn3fh8() {
    var tmp = new FastMapTest();
    tmp.testStringForeach_pmew1j_k$();
    return Unit_getInstance();
  }
  function test_fun$FastMapTest_test_fun$testIntForeach_test_fun_tmmawi() {
    var tmp = new FastMapTest();
    tmp.testIntForeach_dorp31_k$();
    return Unit_getInstance();
  }
  function FastRandomTest() {
    this.random_1 = FastRandom_init_$Create$(new Long(0, 0));
  }
  protoOf(FastRandomTest).get_random_iw5fik_k$ = function () {
    return this.random_1;
  };
  protoOf(FastRandomTest).testLong_3s2bfi_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, 10);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastRandomTest.testLong.<anonymous>' call
      var tmp$ret$0 = this.random_1.nextLong_m0lbld_k$(new Long(0, 0), new Long(1000, 0));
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    assertEquals('[512, 774, 807, 39, 6, 289, 517, 769, 807, 32]', toString(destination));
  };
  protoOf(FastRandomTest).testInt_uxerir_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, 10);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastRandomTest.testInt.<anonymous>' call
      var tmp$ret$0 = this.random_1.nextInt_ak696k_k$(0, 1000);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    assertEquals('[214, 848, 257, 367, 882, 107, 961, 727, 814, 459]', toString(destination));
  };
  protoOf(FastRandomTest).testFail_81gtmo_k$ = function () {
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      this.random_1.nextInt_ak696k_k$(10, 0);
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    checkResultIsFailure_0(null, tmp$ret$2);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_1;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      this.random_1.nextLong_m0lbld_k$(new Long(10, 0), new Long(0, 0));
      tmp_1 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_2;
      if ($p instanceof Error) {
        var e_0 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_2 = _Result___init__impl__xyqfz8(createFailure(e_0));
      } else {
        throw $p;
      }
      tmp_1 = tmp_2;
    }
    var tmp$ret$6 = tmp_1;
    checkResultIsFailure_0(null, tmp$ret$6);
  };
  protoOf(FastRandomTest).testGlobal_kmft2z_k$ = function () {
    Companion_getInstance_5().get_Default_goqax4_k$().nextBits_kty4bl_k$(0);
    Companion_getInstance_5().nextBits_kty4bl_k$(0);
    FastRandom_init_$Create$_0();
    // Inline function 'kotlin.test.assertTrue' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastRandomTest.testGlobal.<anonymous>' call
    // Inline function 'kotlin.collections.count' call
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, 20);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastRandomTest.testGlobal.<anonymous>.<anonymous>' call
      var tmp$ret$0 = Companion_getInstance_5().nextBits_kty4bl_k$(0);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    var tmp$ret$4 = distinct(destination).get_size_woubt6_k$() > 1;
    assertTrue(tmp$ret$4, null);
    // Inline function 'kotlin.test.assertTrue' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastRandomTest.testGlobal.<anonymous>' call
    // Inline function 'kotlin.collections.count' call
    // Inline function 'kotlin.collections.map' call
    var this_1 = until(0, 20);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(this_1, 10));
    var tmp0_iterator_0 = this_1.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastRandomTest.testGlobal.<anonymous>.<anonymous>' call
      var tmp$ret$5 = Companion_getInstance_5().get_Default_goqax4_k$().nextBits_kty4bl_k$(0);
      destination_0.add_utx5q5_k$(tmp$ret$5);
    }
    var tmp$ret$9 = distinct(destination_0).get_size_woubt6_k$() > 1;
    assertTrue(tmp$ret$9, null);
    // Inline function 'kotlin.test.assertTrue' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastRandomTest.testGlobal.<anonymous>' call
    // Inline function 'kotlin.collections.count' call
    // Inline function 'kotlin.collections.map' call
    var this_2 = until(0, 20);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(collectionSizeOrDefault(this_2, 10));
    var tmp0_iterator_1 = this_2.iterator_jk1svi_k$();
    while (tmp0_iterator_1.hasNext_bitz1p_k$()) {
      var item_1 = tmp0_iterator_1.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastRandomTest.testGlobal.<anonymous>.<anonymous>' call
      var tmp$ret$10 = FastRandom_init_$Create$_0().nextBits_kty4bl_k$(0);
      destination_1.add_utx5q5_k$(tmp$ret$10);
    }
    var tmp$ret$14 = distinct(destination_1).get_size_woubt6_k$() > 1;
    assertTrue(tmp$ret$14, null);
  };
  protoOf(FastRandomTest).testArrayExtensionsFail_e538on_k$ = function () {
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      // Inline function 'kotlin.collections.listOf' call
      var this_0 = emptyList();
      random(this_0, Companion_getInstance_5());
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$4 = tmp;
    checkResultIsFailure_0(null, tmp$ret$4);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_1;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      // Inline function 'kotlin.arrayOfNulls' call
      var this_1 = fillArrayVal(Array(0), null);
      random_0(this_1, Companion_getInstance_5());
      tmp_1 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_2;
      if ($p instanceof Error) {
        var e_0 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_2 = _Result___init__impl__xyqfz8(createFailure(e_0));
      } else {
        throw $p;
      }
      tmp_1 = tmp_2;
    }
    var tmp$ret$10 = tmp_1;
    checkResultIsFailure_0(null, tmp$ret$10);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_3;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      // Inline function 'kotlin.arrayOf' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var this_2 = [];
      random_0(this_2, Companion_getInstance_5());
      tmp_3 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_4;
      if ($p instanceof Error) {
        var e_1 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_4 = _Result___init__impl__xyqfz8(createFailure(e_1));
      } else {
        throw $p;
      }
      tmp_3 = tmp_4;
    }
    var tmp$ret$18 = tmp_3;
    checkResultIsFailure_0(null, tmp$ret$18);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_5;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      var this_3 = booleanArray(0);
      random_1(this_3, Companion_getInstance_5());
      tmp_5 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_6;
      if ($p instanceof Error) {
        var e_2 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_6 = _Result___init__impl__xyqfz8(createFailure(e_2));
      } else {
        throw $p;
      }
      tmp_5 = tmp_6;
    }
    var tmp$ret$23 = tmp_5;
    checkResultIsFailure_0(null, tmp$ret$23);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_7;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      var this_4 = charArray(0);
      random_2(this_4, Companion_getInstance_5());
      tmp_7 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_8;
      if ($p instanceof Error) {
        var e_3 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_8 = _Result___init__impl__xyqfz8(createFailure(e_3));
      } else {
        throw $p;
      }
      tmp_7 = tmp_8;
    }
    var tmp$ret$28 = tmp_7;
    checkResultIsFailure_0(null, tmp$ret$28);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_9;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      var this_5 = new Int16Array(0);
      random_3(this_5, Companion_getInstance_5());
      tmp_9 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_10;
      if ($p instanceof Error) {
        var e_4 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_10 = _Result___init__impl__xyqfz8(createFailure(e_4));
      } else {
        throw $p;
      }
      tmp_9 = tmp_10;
    }
    var tmp$ret$33 = tmp_9;
    checkResultIsFailure_0(null, tmp$ret$33);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_11;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      var this_6 = new Int32Array(0);
      random_4(this_6, Companion_getInstance_5());
      tmp_11 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_12;
      if ($p instanceof Error) {
        var e_5 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_12 = _Result___init__impl__xyqfz8(createFailure(e_5));
      } else {
        throw $p;
      }
      tmp_11 = tmp_12;
    }
    var tmp$ret$38 = tmp_11;
    checkResultIsFailure_0(null, tmp$ret$38);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_13;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      var this_7 = longArray(0);
      random_5(this_7, Companion_getInstance_5());
      tmp_13 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_14;
      if ($p instanceof Error) {
        var e_6 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_14 = _Result___init__impl__xyqfz8(createFailure(e_6));
      } else {
        throw $p;
      }
      tmp_13 = tmp_14;
    }
    var tmp$ret$43 = tmp_13;
    checkResultIsFailure_0(null, tmp$ret$43);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_15;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      var this_8 = new Float32Array(0);
      random_6(this_8, Companion_getInstance_5());
      tmp_15 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_16;
      if ($p instanceof Error) {
        var e_7 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_16 = _Result___init__impl__xyqfz8(createFailure(e_7));
      } else {
        throw $p;
      }
      tmp_15 = tmp_16;
    }
    var tmp$ret$48 = tmp_15;
    checkResultIsFailure_0(null, tmp$ret$48);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp_17;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.datastructure.random.fastRandom' call
      var this_9 = new Float64Array(0);
      random_7(this_9, Companion_getInstance_5());
      tmp_17 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_18;
      if ($p instanceof Error) {
        var e_8 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_18 = _Result___init__impl__xyqfz8(createFailure(e_8));
      } else {
        throw $p;
      }
      tmp_17 = tmp_18;
    }
    var tmp$ret$53 = tmp_17;
    checkResultIsFailure_0(null, tmp$ret$53);
  };
  protoOf(FastRandomTest).testArrayExtensions_bixfgr_k$ = function () {
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    var this_0 = listOf_0('a');
    var tmp$ret$0 = random(this_0, Companion_getInstance_5());
    assertEquals('a', tmp$ret$0);
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_1 = ['a'];
    var tmp$ret$4 = random_0(this_1, Companion_getInstance_5());
    assertEquals('a', tmp$ret$4);
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.booleanArrayOf' call
    var this_2 = booleanArrayOf([true]);
    var tmp$ret$6 = random_1(this_2, Companion_getInstance_5());
    assertEquals(true, tmp$ret$6);
    var tmp = _Char___init__impl__6a9atx(97);
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.charArrayOf' call
    var this_3 = charArrayOf([_Char___init__impl__6a9atx(97)]);
    var tmp$ret$8 = random_2(this_3, Companion_getInstance_5());
    assertEquals(new Char(tmp), new Char(tmp$ret$8));
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.shortArrayOf' call
    var this_4 = new Int16Array([-10]);
    var tmp$ret$10 = random_3(this_4, Companion_getInstance_5());
    assertEquals(-10, tmp$ret$10);
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.intArrayOf' call
    var this_5 = new Int32Array([-10]);
    var tmp$ret$12 = random_4(this_5, Companion_getInstance_5());
    assertEquals(-10, tmp$ret$12);
    var tmp_0 = new Long(-10, -1);
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.longArrayOf' call
    var this_6 = longArrayOf([new Long(-10, -1)]);
    var tmp$ret$14 = random_5(this_6, Companion_getInstance_5());
    assertEquals(tmp_0, tmp$ret$14);
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.floatArrayOf' call
    var this_7 = new Float32Array([3.0]);
    var tmp$ret$16 = random_6(this_7, Companion_getInstance_5());
    assertEquals(3.0, tmp$ret$16);
    // Inline function 'korlibs.datastructure.random.fastRandom' call
    // Inline function 'kotlin.doubleArrayOf' call
    var this_8 = new Float64Array([7.0]);
    var tmp$ret$18 = random_7(this_8, Companion_getInstance_5());
    assertEquals(7.0, tmp$ret$18);
  };
  protoOf(FastRandomTest).testArrayExtensions2_22t28p_k$ = function () {
    var random_8 = FastRandom_init_$Create$(new Long(0, 0));
    assertEquals('a', random(listOf_0('a'), random_8));
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = ['a'];
    assertEquals('a', random_0(tmp$ret$2, random_8));
    // Inline function 'kotlin.booleanArrayOf' call
    var tmp$ret$3 = booleanArrayOf([true]);
    assertEquals(true, random_1(tmp$ret$3, random_8));
    var tmp = _Char___init__impl__6a9atx(97);
    // Inline function 'kotlin.charArrayOf' call
    var tmp$ret$4 = charArrayOf([_Char___init__impl__6a9atx(97)]);
    assertEquals(new Char(tmp), new Char(random_2(tmp$ret$4, random_8)));
    // Inline function 'kotlin.shortArrayOf' call
    var tmp$ret$5 = new Int16Array([-10]);
    assertEquals(-10, random_3(tmp$ret$5, random_8));
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$6 = new Int32Array([-10]);
    assertEquals(-10, random_4(tmp$ret$6, random_8));
    var tmp_0 = new Long(-10, -1);
    // Inline function 'kotlin.longArrayOf' call
    var tmp$ret$7 = longArrayOf([new Long(-10, -1)]);
    assertEquals(tmp_0, random_5(tmp$ret$7, random_8));
    // Inline function 'kotlin.floatArrayOf' call
    var tmp$ret$8 = new Float32Array([3.0]);
    assertEquals(3.0, random_6(tmp$ret$8, random_8));
    // Inline function 'kotlin.doubleArrayOf' call
    var tmp$ret$9 = new Float64Array([7.0]);
    assertEquals(7.0, random_7(tmp$ret$9, random_8));
  };
  protoOf(FastRandomTest).testGlobalInstance_7k26h2_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, 10);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastRandomTest.testGlobalInstance.<anonymous>' call
      var tmp$ret$0 = Companion_getInstance_5().nextInt_ujorgc_k$();
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    println(destination);
    // Inline function 'kotlin.test.assertTrue' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.FastRandomTest.testGlobalInstance.<anonymous>' call
    // Inline function 'kotlin.collections.map' call
    var this_1 = until(0, 10);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(this_1, 10));
    var tmp0_iterator_0 = this_1.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.FastRandomTest.testGlobalInstance.<anonymous>.<anonymous>' call
      var tmp$ret$3 = Companion_getInstance_5().nextInt_ujorgc_k$();
      destination_0.add_utx5q5_k$(tmp$ret$3);
    }
    var tmp$ret$6 = distinct(destination_0).get_size_woubt6_k$() >= 2;
    assertTrue(tmp$ret$6, null);
  };
  function test_fun_izoufj_20() {
    suite('FastRandomTest', false, test_fun$FastRandomTest_test_fun_k9u5f1);
  }
  function test_fun$FastRandomTest_test_fun_k9u5f1() {
    test('testLong', false, test_fun$FastRandomTest_test_fun$testLong_test_fun_dbjquk);
    test('testInt', false, test_fun$FastRandomTest_test_fun$testInt_test_fun_chvalx);
    test('testFail', false, test_fun$FastRandomTest_test_fun$testFail_test_fun_2tsitm);
    test('testGlobal', false, test_fun$FastRandomTest_test_fun$testGlobal_test_fun_ndatln);
    test('testArrayExtensionsFail', false, test_fun$FastRandomTest_test_fun$testArrayExtensionsFail_test_fun_1yircf);
    test('testArrayExtensions', false, test_fun$FastRandomTest_test_fun$testArrayExtensions_test_fun_lywln7);
    test('testArrayExtensions2', false, test_fun$FastRandomTest_test_fun$testArrayExtensions2_test_fun_a4vi2r);
    test('testGlobalInstance', false, test_fun$FastRandomTest_test_fun$testGlobalInstance_test_fun_7937n4);
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testLong_test_fun_dbjquk() {
    var tmp = new FastRandomTest();
    tmp.testLong_3s2bfi_k$();
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testInt_test_fun_chvalx() {
    var tmp = new FastRandomTest();
    tmp.testInt_uxerir_k$();
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testFail_test_fun_2tsitm() {
    var tmp = new FastRandomTest();
    tmp.testFail_81gtmo_k$();
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testGlobal_test_fun_ndatln() {
    var tmp = new FastRandomTest();
    tmp.testGlobal_kmft2z_k$();
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testArrayExtensionsFail_test_fun_1yircf() {
    var tmp = new FastRandomTest();
    tmp.testArrayExtensionsFail_e538on_k$();
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testArrayExtensions_test_fun_lywln7() {
    var tmp = new FastRandomTest();
    tmp.testArrayExtensions_bixfgr_k$();
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testArrayExtensions2_test_fun_a4vi2r() {
    var tmp = new FastRandomTest();
    tmp.testArrayExtensions2_22t28p_k$();
    return Unit_getInstance();
  }
  function test_fun$FastRandomTest_test_fun$testGlobalInstance_test_fun_7937n4() {
    var tmp = new FastRandomTest();
    tmp.testGlobalInstance_7k26h2_k$();
    return Unit_getInstance();
  }
  function FastSmallSetTest() {
  }
  protoOf(FastSmallSetTest).test_w5z8la_k$ = function () {
    var set = new FastSmallSet();
    set.add_wl2rvy_k$('a');
    assertEquals(true, set.contains_ccp5tc_k$('a'));
    set.add_wl2rvy_k$('b');
    assertEquals(true, set.contains_ccp5tc_k$('b'));
    set.add_wl2rvy_k$('c');
    assertEquals(false, set.contains_ccp5tc_k$('d'));
    assertEquals(true, set.contains_ccp5tc_k$('c'));
    assertEquals(true, set.contains_ccp5tc_k$('b'));
    assertEquals(true, set.contains_ccp5tc_k$('a'));
    set.add_wl2rvy_k$('d');
    assertEquals(true, set.contains_ccp5tc_k$('d'));
    assertEquals(true, set.contains_ccp5tc_k$('c'));
    assertEquals(true, set.contains_ccp5tc_k$('b'));
    assertEquals(true, set.contains_ccp5tc_k$('a'));
    set.remove_an8aut_k$('c');
    assertEquals(true, set.contains_ccp5tc_k$('d'));
    assertEquals(false, set.contains_ccp5tc_k$('c'));
    assertEquals(true, set.contains_ccp5tc_k$('b'));
    assertEquals(true, set.contains_ccp5tc_k$('a'));
  };
  function test_fun_izoufj_21() {
    suite('FastSmallSetTest', false, test_fun$FastSmallSetTest_test_fun_5eswid);
  }
  function test_fun$FastSmallSetTest_test_fun_5eswid() {
    test('test', false, test_fun$FastSmallSetTest_test_fun$test_test_fun_n2vq4w);
    return Unit_getInstance();
  }
  function test_fun$FastSmallSetTest_test_fun$test_test_fun_n2vq4w() {
    var tmp = new FastSmallSetTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function FastStringMapTest() {
  }
  protoOf(FastStringMapTest).testFastKeyForEach_h23t3r_k$ = function () {
    var map = FastStringMap();
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set('hello', 10);
    // Inline function 'korlibs.datastructure.set' call
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'kotlin.js.asDynamic' call
    map.set('world', 20);
    // Inline function 'kotlin.collections.arrayListOf' call
    var out = ArrayList_init_$Create$_0();
    // Inline function 'korlibs.datastructure.fastKeyForEach' call
    // Inline function 'kotlin.js.asDynamic' call
    var mapIterator = map.keys();
    $l$loop: while (true) {
      var v = mapIterator.next();
      if (v.done)
        break $l$loop;
      // Inline function 'korlibs.datastructure.FastStringMapTest.testFastKeyForEach.<anonymous>' call
      var it = v.value;
      out.add_utx5q5_k$(it);
    }
    assertEquals(listOf(['hello', 'world']), sorted(out));
  };
  function test_fun_izoufj_22() {
    suite('FastStringMapTest', false, test_fun$FastStringMapTest_test_fun_jemp0j);
  }
  function test_fun$FastStringMapTest_test_fun_jemp0j() {
    test('testFastKeyForEach', false, test_fun$FastStringMapTest_test_fun$testFastKeyForEach_test_fun_scmc95);
    return Unit_getInstance();
  }
  function test_fun$FastStringMapTest_test_fun$testFastKeyForEach_test_fun_scmc95() {
    var tmp = new FastStringMapTest();
    tmp.testFastKeyForEach_h23t3r_k$();
    return Unit_getInstance();
  }
  function FunctionMemoizeTest$test$lambda($called) {
    return function () {
      var _unary__edvuaz = $called._v;
      $called._v = _unary__edvuaz + 1 | 0;
      return 10;
    };
  }
  function FunctionMemoizeTest() {
  }
  protoOf(FunctionMemoizeTest).test_w5z8la_k$ = function () {
    var called = {_v: 0};
    var func = FunctionMemoizeTest$test$lambda(called);
    assertEquals(10, func());
    assertEquals(10, func());
    assertEquals(2, called._v);
    var funcMemo = memoize(func);
    var inductionVariable = 0;
    if (inductionVariable < 10)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        assertEquals(10, funcMemo());
      }
       while (inductionVariable < 10);
    assertEquals(3, called._v);
  };
  function test_fun_izoufj_23() {
    suite('FunctionMemoizeTest', false, test_fun$FunctionMemoizeTest_test_fun_kdpd74);
  }
  function test_fun$FunctionMemoizeTest_test_fun_kdpd74() {
    test('test', false, test_fun$FunctionMemoizeTest_test_fun$test_test_fun_9ltma3);
    return Unit_getInstance();
  }
  function test_fun$FunctionMemoizeTest_test_fun$test_test_fun_9ltma3() {
    var tmp = new FunctionMemoizeTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function swap_1(_this__u8e3s4, $this, indexA, indexB) {
    var tmp = _this__u8e3s4.get_c1px32_k$(indexA);
    _this__u8e3s4.set_82063s_k$(indexA, _this__u8e3s4.get_c1px32_k$(indexB));
    _this__u8e3s4.set_82063s_k$(indexB, tmp);
  }
  function GenericSortTest$test$result$1(this$0) {
    this.this$0__1 = this$0;
    SortOps.call(this);
  }
  protoOf(GenericSortTest$test$result$1).compare_boofa8_k$ = function (subject, l, r) {
    return compareTo(subject.get_c1px32_k$(l), subject.get_c1px32_k$(r));
  };
  protoOf(GenericSortTest$test$result$1).compare_nik356_k$ = function (subject, l, r) {
    return this.compare_boofa8_k$(subject instanceof ArrayList ? subject : THROW_CCE(), l, r);
  };
  protoOf(GenericSortTest$test$result$1).swap_i26wgu_k$ = function (subject, indexL, indexR) {
    swap_1(subject, this.this$0__1, indexL, indexR);
  };
  protoOf(GenericSortTest$test$result$1).swap_nx7218_k$ = function (subject, indexL, indexR) {
    return this.swap_i26wgu_k$(subject instanceof ArrayList ? subject : THROW_CCE(), indexL, indexR);
  };
  function GenericSortTest() {
  }
  protoOf(GenericSortTest).test1_tbiuwh_k$ = function () {
    assertEquals(listOf([0, 1, 2, 3, 4]), genericSorted(listOf([1, 2, 3, 4, 0])));
    assertEquals(listOf([0, 1, 2, 3, 4]), genericSorted(listOf([1, 2, 3, 0, 4])));
    assertEquals(listOf([0, 1, 2, 3, 4]), genericSorted(listOf([1, 2, 0, 3, 4])));
    assertEquals(listOf([0, 1, 2, 3, 4]), genericSorted(listOf([1, 0, 2, 3, 4])));
    assertEquals(listOf([0, 1, 2, 3, 4]), genericSorted(listOf([0, 1, 2, 3, 4])));
    assertEquals(listOf_0(0), genericSorted(listOf_0(0)));
    assertEquals(listOf([0, 1]), genericSorted(listOf([1, 0])));
  };
  protoOf(GenericSortTest).test_w5z8la_k$ = function () {
    var tmp = arrayListOf([10, 30, 20, 10, 5, 3, 40, 7]);
    var result = genericSort(tmp, 0, 7, new GenericSortTest$test$result$1(this));
    assertEquals(listOf([3, 5, 7, 10, 10, 20, 30, 40]), result);
  };
  function test_fun_izoufj_24() {
    suite('GenericSortTest', false, test_fun$GenericSortTest_test_fun_4fu1p9);
  }
  function test_fun$GenericSortTest_test_fun_4fu1p9() {
    test('test1', false, test_fun$GenericSortTest_test_fun$test1_test_fun_5046fb);
    test('test', false, test_fun$GenericSortTest_test_fun$test_test_fun_deb5vs);
    return Unit_getInstance();
  }
  function test_fun$GenericSortTest_test_fun$test1_test_fun_5046fb() {
    var tmp = new GenericSortTest();
    tmp.test1_tbiuwh_k$();
    return Unit_getInstance();
  }
  function test_fun$GenericSortTest_test_fun$test_test_fun_deb5vs() {
    var tmp = new GenericSortTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function _get_list__d9tsa5($this) {
    return $this.list_1;
  }
  function testSubList($this, sub) {
    assertEquals(3, sub.get_size_woubt6_k$());
    assertEquals('[3, 4, 3]', toString(sub));
    assertEquals(-1, sub.indexOf_si1fv9_k$(2));
    assertEquals(0, sub.indexOf_si1fv9_k$(3));
    assertEquals(1, sub.indexOf_si1fv9_k$(4));
    assertEquals(2, sub.lastIndexOf_v2p1fv_k$(3));
    assertEquals(false, sub.contains_aljjnj_k$(2));
    assertEquals(true, sub.contains_aljjnj_k$(3));
    assertEquals(false, sub.containsAll_xk45sd_k$(listOf([2, 3])));
    assertEquals(true, sub.containsAll_xk45sd_k$(listOf([3, 4])));
    assertEquals(false, sub.isEmpty_y1axqb_k$());
    // Inline function 'kotlin.collections.isNotEmpty' call
    var tmp$ret$0 = !sub.isEmpty_y1axqb_k$();
    assertEquals(true, tmp$ret$0);
    assertEquals('[4, 3]', toString(sub.subList_xle3r2_k$(1, 3)));
    assertEquals('[3, 4, 3]', toString(toList_2(asSequence(sub.listIterator_xjshxw_k$()))));
    assertEquals('[4, 3]', toString(toList_2(asSequence(sub.listIterator_70e65o_k$(1)))));
  }
  function GenericSubListTest() {
    this.list_1 = listOf([1, 2, 3, 4, 3]);
  }
  protoOf(GenericSubListTest).test_w5z8la_k$ = function () {
    testSubList(this, this.list_1.subList_xle3r2_k$(2, 5));
  };
  protoOf(GenericSubListTest).test2_6xsqgg_k$ = function () {
    testSubList(this, new GenericSubList(this.list_1, 2, 5));
  };
  function test_fun_izoufj_25() {
    suite('GenericSubListTest', false, test_fun$GenericSubListTest_test_fun_rqwznf);
  }
  function test_fun$GenericSubListTest_test_fun_rqwznf() {
    test('test', false, test_fun$GenericSubListTest_test_fun$test_test_fun_sl144g);
    test('test2', false, test_fun$GenericSubListTest_test_fun$test2_test_fun_i6br5c);
    return Unit_getInstance();
  }
  function test_fun$GenericSubListTest_test_fun$test_test_fun_sl144g() {
    var tmp = new GenericSubListTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$GenericSubListTest_test_fun$test2_test_fun_i6br5c() {
    var tmp = new GenericSubListTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function HistoryStackTest() {
  }
  protoOf(HistoryStackTest).test_w5z8la_k$ = function () {
    var stack = new HistoryStack();
    stack.push_rwg0cl_k$('a');
    assertEquals(null, stack.undo_251ic_k$());
    assertEquals('a', stack.redo_22xvi_k$());
    stack.push_rwg0cl_k$('b');
    stack.push_rwg0cl_k$('c');
    stack.push_rwg0cl_k$('d');
    assertEquals('c', stack.undo_251ic_k$());
    assertEquals('b', stack.undo_251ic_k$());
    assertEquals('a', stack.undo_251ic_k$());
    assertEquals(null, stack.undo_251ic_k$());
    assertEquals('a', stack.redo_22xvi_k$());
    assertEquals('b', stack.redo_22xvi_k$());
    stack.push_rwg0cl_k$('C');
    assertEquals(null, stack.redo_22xvi_k$());
    assertEquals('C', stack.undo_251ic_k$());
    assertEquals('b', stack.undo_251ic_k$());
    assertEquals('a', stack.undo_251ic_k$());
    assertEquals(null, stack.undo_251ic_k$());
  };
  function test_fun_izoufj_26() {
    suite('HistoryStackTest', false, test_fun$HistoryStackTest_test_fun_d4nzqw);
  }
  function test_fun$HistoryStackTest_test_fun_d4nzqw() {
    test('test', false, test_fun$HistoryStackTest_test_fun$test_test_fun_95jufn);
    return Unit_getInstance();
  }
  function test_fun$HistoryStackTest_test_fun$test_test_fun_95jufn() {
    var tmp = new HistoryStackTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function IndexedTableTest() {
  }
  protoOf(IndexedTableTest).test_w5z8la_k$ = function () {
    var table = new IndexedTable();
    table.add_jcyd1a_k$('hello');
    table.add_jcyd1a_k$('world');
    assertEquals(0, table.get_51jv09_k$('hello'));
    assertEquals(1, table.get_51jv09_k$('world'));
    assertEquals(2, table.get_51jv09_k$('test'));
    assertEquals(0, table.get_51jv09_k$('hello'));
    assertEquals(1, table.get_51jv09_k$('world'));
    assertEquals(2, table.get_51jv09_k$('test'));
    assertEquals(3, table.get_size_woubt6_k$());
  };
  function test_fun_izoufj_27() {
    suite('IndexedTableTest', false, test_fun$IndexedTableTest_test_fun_j4xzu9);
  }
  function test_fun$IndexedTableTest_test_fun_j4xzu9() {
    test('test', false, test_fun$IndexedTableTest_test_fun$test_test_fun_byql06);
    return Unit_getInstance();
  }
  function test_fun$IndexedTableTest_test_fun$test_test_fun_byql06() {
    var tmp = new IndexedTableTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function IntFloatMapTest() {
  }
  protoOf(IntFloatMapTest).simple_w8bz1a_k$ = function () {
    var m = IntFloatMap_init_$Create$();
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(0.0, m.get_c1px32_k$(0));
    m.set_g2yi9q_k$(0, 98.0);
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals(98.0, m.get_c1px32_k$(0));
    assertEquals(0.0, m.get_c1px32_k$(1));
    m.set_g2yi9q_k$(0, 99.0);
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals(99.0, m.get_c1px32_k$(0));
    assertEquals(0.0, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(0.0, m.get_c1px32_k$(0));
    assertEquals(0.0, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
  };
  protoOf(IntFloatMapTest).name2_ovm8cp_k$ = function () {
    var m = IntFloatMap_init_$Create$();
    var inductionVariable = 0;
    if (inductionVariable < 1000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        m.set_g2yi9q_k$(n, imul(n, 1000));
      }
       while (inductionVariable < 1000);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 1000)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        assertEquals(imul(n_0, 1000), m.get_c1px32_k$(n_0));
        assertEquals(true, m.contains_7q95ev_k$(n_0));
      }
       while (inductionVariable_0 < 1000);
    assertEquals(0.0, m.get_c1px32_k$(-1));
    assertEquals(0.0, m.get_c1px32_k$(1001));
    assertEquals(false, m.contains_7q95ev_k$(-1));
    assertEquals(false, m.contains_7q95ev_k$(1001));
  };
  function test_fun_izoufj_28() {
    suite('IntFloatMapTest', false, test_fun$IntFloatMapTest_test_fun_4ozklf);
  }
  function test_fun$IntFloatMapTest_test_fun_4ozklf() {
    test('simple', false, test_fun$IntFloatMapTest_test_fun$simple_test_fun_t7wb9e);
    test('name2', false, test_fun$IntFloatMapTest_test_fun$name2_test_fun_wsp2nf);
    return Unit_getInstance();
  }
  function test_fun$IntFloatMapTest_test_fun$simple_test_fun_t7wb9e() {
    var tmp = new IntFloatMapTest();
    tmp.simple_w8bz1a_k$();
    return Unit_getInstance();
  }
  function test_fun$IntFloatMapTest_test_fun$name2_test_fun_wsp2nf() {
    var tmp = new IntFloatMapTest();
    tmp.name2_ovm8cp_k$();
    return Unit_getInstance();
  }
  function IntIntMapTest() {
  }
  protoOf(IntIntMapTest).simple_w8bz1a_k$ = function () {
    var m = IntIntMap_init_$Create$();
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(0, m.get_c1px32_k$(0));
    m.set_tq3pjy_k$(0, 98);
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals(98, m.get_c1px32_k$(0));
    assertEquals(0, m.get_c1px32_k$(1));
    m.set_tq3pjy_k$(0, 99);
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals(99, m.get_c1px32_k$(0));
    assertEquals(0, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(0, m.get_c1px32_k$(0));
    assertEquals(0, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
  };
  protoOf(IntIntMapTest).name2_ovm8cp_k$ = function () {
    var m = IntIntMap_init_$Create$();
    var inductionVariable = 0;
    if (inductionVariable < 1000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        m.set_tq3pjy_k$(n, imul(n, 1000));
      }
       while (inductionVariable < 1000);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 1000)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        assertEquals(imul(n_0, 1000), m.get_c1px32_k$(n_0));
        assertEquals(true, m.contains_7q95ev_k$(n_0));
      }
       while (inductionVariable_0 < 1000);
    assertEquals(0, m.get_c1px32_k$(-1));
    assertEquals(0, m.get_c1px32_k$(1001));
    assertEquals(false, m.contains_7q95ev_k$(-1));
    assertEquals(false, m.contains_7q95ev_k$(1001));
  };
  function test_fun_izoufj_29() {
    suite('IntIntMapTest', false, test_fun$IntIntMapTest_test_fun_lju9uy);
  }
  function test_fun$IntIntMapTest_test_fun_lju9uy() {
    test('simple', false, test_fun$IntIntMapTest_test_fun$simple_test_fun_eukoep);
    test('name2', false, test_fun$IntIntMapTest_test_fun$name2_test_fun_p1x86w);
    return Unit_getInstance();
  }
  function test_fun$IntIntMapTest_test_fun$simple_test_fun_eukoep() {
    var tmp = new IntIntMapTest();
    tmp.simple_w8bz1a_k$();
    return Unit_getInstance();
  }
  function test_fun$IntIntMapTest_test_fun$name2_test_fun_p1x86w() {
    var tmp = new IntIntMapTest();
    tmp.name2_ovm8cp_k$();
    return Unit_getInstance();
  }
  function name4$checkValues(ref, imp) {
    var rand = Random(new Long(0, 0));
    assertEquals(ref.get_size_woubt6_k$(), imp.get_size_woubt6_k$());
    var inductionVariable = 0;
    if (inductionVariable < 10000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var key = rand.nextInt_ujorgc_k$();
        // Inline function 'kotlin.collections.contains' call
        // Inline function 'kotlin.collections.containsKey' call
        var tmp$ret$1 = (isInterface(ref, Map) ? ref : THROW_CCE()).containsKey_aw81wo_k$(key);
        assertEquals(tmp$ret$1, imp.contains_7q95ev_k$(key));
        assertEquals(ref.get_wei43m_k$(key), imp.get_c1px32_k$(key));
      }
       while (inductionVariable < 10000);
  }
  function IntMap2JvmTest() {
  }
  protoOf(IntMap2JvmTest).simple_w8bz1a_k$ = function () {
    var m = IntMap_init_$Create$();
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(null, m.get_c1px32_k$(0));
    m.set_8cx7ps_k$(0, 'test');
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals('test', m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.set_8cx7ps_k$(0, 'test2');
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals('test2', m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(null, m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
  };
  protoOf(IntMap2JvmTest).name2_ovm8cp_k$ = function () {
    var m = IntMap_init_$Create$();
    var inductionVariable = 0;
    if (inductionVariable < 1000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        m.set_8cx7ps_k$(n, imul(n, 1000));
      }
       while (inductionVariable < 1000);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 1000)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        assertEquals(imul(n_0, 1000), m.get_c1px32_k$(n_0));
      }
       while (inductionVariable_0 < 1000);
    assertEquals(null, m.get_c1px32_k$(-1));
    assertEquals(null, m.get_c1px32_k$(1001));
  };
  protoOf(IntMap2JvmTest).name3_2hw3wo_k$ = function () {
    var m = IntMap_init_$Create$();
    var inductionVariable = 0;
    if (inductionVariable < 100000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        m.set_8cx7ps_k$(n, 'a' + n);
      }
       while (inductionVariable < 100000);
  };
  protoOf(IntMap2JvmTest).name4_jvu0jd_k$ = function () {
    // Inline function 'kotlin.collections.hashMapOf' call
    var ref = HashMap_init_$Create$();
    var imp = IntMap_init_$Create$();
    var rand1 = Random(new Long(0, 0));
    var inductionVariable = 0;
    if (inductionVariable < 10000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var key = rand1.nextInt_ujorgc_k$();
        // Inline function 'kotlin.collections.set' call
        var value = 'n' + n;
        ref.put_4fpzoq_k$(key, value);
        imp.set_8cx7ps_k$(key, 'n' + n);
      }
       while (inductionVariable < 10000);
    name4$checkValues(ref, imp);
    var rand2 = Random(new Long(0, 0));
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 5000)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        var key_0 = rand2.nextInt_ujorgc_k$();
        ref.remove_gppy8k_k$(key_0);
        imp.remove_cqondg_k$(key_0);
      }
       while (inductionVariable_0 < 5000);
    name4$checkValues(ref, imp);
    ref.clear_j9egeb_k$();
    imp.clear_j9egeb_k$();
    name4$checkValues(ref, imp);
  };
  function test_fun_izoufj_30() {
    suite('IntMap2JvmTest', false, test_fun$IntMap2JvmTest_test_fun_2z18hc);
  }
  function test_fun$IntMap2JvmTest_test_fun_2z18hc() {
    test('simple', false, test_fun$IntMap2JvmTest_test_fun$simple_test_fun_3ftw3v);
    test('name2', false, test_fun$IntMap2JvmTest_test_fun$name2_test_fun_qz5s8u);
    test('name3', false, test_fun$IntMap2JvmTest_test_fun$name3_test_fun_nq5tgd);
    test('name4', false, test_fun$IntMap2JvmTest_test_fun$name4_test_fun_kh5unw);
    return Unit_getInstance();
  }
  function test_fun$IntMap2JvmTest_test_fun$simple_test_fun_3ftw3v() {
    var tmp = new IntMap2JvmTest();
    tmp.simple_w8bz1a_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMap2JvmTest_test_fun$name2_test_fun_qz5s8u() {
    var tmp = new IntMap2JvmTest();
    tmp.name2_ovm8cp_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMap2JvmTest_test_fun$name3_test_fun_nq5tgd() {
    var tmp = new IntMap2JvmTest();
    tmp.name3_2hw3wo_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMap2JvmTest_test_fun$name4_test_fun_kh5unw() {
    var tmp = new IntMap2JvmTest();
    tmp.name4_jvu0jd_k$();
    return Unit_getInstance();
  }
  function IntMap2Test() {
  }
  protoOf(IntMap2Test).simple_w8bz1a_k$ = function () {
    var m = IntMap_init_$Create$();
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(null, m.get_c1px32_k$(0));
    m.set_8cx7ps_k$(0, 'test');
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals('test', m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.set_8cx7ps_k$(0, 'test2');
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals('test2', m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(null, m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
  };
  protoOf(IntMap2Test).name2_ovm8cp_k$ = function () {
    var m = IntMap_init_$Create$();
    var inductionVariable = 0;
    if (inductionVariable < 1000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        m.set_8cx7ps_k$(n, imul(n, 1000));
      }
       while (inductionVariable < 1000);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 1000)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        assertEquals(imul(n_0, 1000), m.get_c1px32_k$(n_0));
      }
       while (inductionVariable_0 < 1000);
    assertEquals(null, m.get_c1px32_k$(-1));
    assertEquals(null, m.get_c1px32_k$(1001));
  };
  function test_fun_izoufj_31() {
    suite('IntMap2Test', false, test_fun$IntMap2Test_test_fun_xsn2gj);
  }
  function test_fun$IntMap2Test_test_fun_xsn2gj() {
    test('simple', false, test_fun$IntMap2Test_test_fun$simple_test_fun_t1m4vs);
    test('name2', false, test_fun$IntMap2Test_test_fun$name2_test_fun_1kevrz);
    return Unit_getInstance();
  }
  function test_fun$IntMap2Test_test_fun$simple_test_fun_t1m4vs() {
    var tmp = new IntMap2Test();
    tmp.simple_w8bz1a_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMap2Test_test_fun$name2_test_fun_1kevrz() {
    var tmp = new IntMap2Test();
    tmp.name2_ovm8cp_k$();
    return Unit_getInstance();
  }
  function intStream(_this__u8e3s4) {
    return sequence(intStream$slambda_0(_this__u8e3s4, null));
  }
  function intStream_0(_this__u8e3s4, from, until) {
    return sequence(intStream$slambda_2(_this__u8e3s4, from, until, null));
  }
  function intStream_1(_this__u8e3s4, range) {
    return intStream_0(_this__u8e3s4, range.get_start_iypx6h_k$(), range.get_endInclusive_r07xpi_k$() + 1 | 0);
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
  function IntMapTest$testIntMapGetOrPut$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    // Inline function 'korlibs.datastructure.IntMapTest.testIntMapGetOrPut.<anonymous>' call
    var tmp = a.get_key_18j28a_k$();
    // Inline function 'korlibs.datastructure.IntMapTest.testIntMapGetOrPut.<anonymous>' call
    var tmp$ret$1 = b.get_key_18j28a_k$();
    return compareValues(tmp, tmp$ret$1);
  }
  function IntMapTest() {
  }
  protoOf(IntMapTest).smoke_2mwr6n_k$ = function () {
    var values = distinct(toList_2(take(intStream(Random_0(0)), 10000)));
    var valuesSet = toSet(values);
    var m = IntMap_init_$Create$();
    var _iterator__ex2g4s = values.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var value = _iterator__ex2g4s.next_20eer_k$();
      m.set_8cx7ps_k$(value, 'value');
    }
    assertEquals(m.get_size_woubt6_k$(), values.get_size_woubt6_k$());
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.sequences.firstOrNull' call
      var tmp0_iterator = asSequence_0(until(0, 10001)).iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.IntMapTest.smoke.<anonymous>' call
        if (!valuesSet.contains_aljjnj_k$(element)) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    var tmp0_elvis_lhs = tmp$ret$1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      var message = 'Unexpected';
      throw IllegalStateException_init_$Create$(toString(message));
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var notIn = tmp;
    assertEquals(false, m.contains_7q95ev_k$(notIn));
    var _iterator__ex2g4s_0 = toList_0(m.get_keys_wop4xp_k$()).iterator_jk1svi_k$();
    while (_iterator__ex2g4s_0.hasNext_bitz1p_k$()) {
      var key = _iterator__ex2g4s_0.next_20eer_k$();
      assertEquals(true, m.contains_7q95ev_k$(key));
      assertEquals(true, m.contains_7q95ev_k$(key));
    }
    var removeValues = take_0(values, values.get_size_woubt6_k$() / 2 | 0);
    var _iterator__ex2g4s_1 = removeValues.iterator_jk1svi_k$();
    while (_iterator__ex2g4s_1.hasNext_bitz1p_k$()) {
      var key_0 = _iterator__ex2g4s_1.next_20eer_k$();
      m.remove_cqondg_k$(key_0);
    }
    assertEquals(values.get_size_woubt6_k$() - removeValues.get_size_woubt6_k$() | 0, m.get_size_woubt6_k$());
    var containingValues = drop(values, removeValues.get_size_woubt6_k$());
    var _iterator__ex2g4s_2 = removeValues.iterator_jk1svi_k$();
    while (_iterator__ex2g4s_2.hasNext_bitz1p_k$()) {
      var key_1 = _iterator__ex2g4s_2.next_20eer_k$();
      assertEquals(false, m.contains_7q95ev_k$(key_1));
    }
    var _iterator__ex2g4s_3 = containingValues.iterator_jk1svi_k$();
    while (_iterator__ex2g4s_3.hasNext_bitz1p_k$()) {
      var key_2 = _iterator__ex2g4s_3.next_20eer_k$();
      assertEquals(true, m.contains_7q95ev_k$(key_2));
    }
  };
  protoOf(IntMapTest).simple_w8bz1a_k$ = function () {
    var m = IntMap_init_$Create$();
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(null, m.get_c1px32_k$(0));
    m.set_8cx7ps_k$(0, 'test');
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals('test', m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.set_8cx7ps_k$(0, 'test2');
    assertEquals(1, m.get_size_woubt6_k$());
    assertEquals('test2', m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
    assertEquals(0, m.get_size_woubt6_k$());
    assertEquals(null, m.get_c1px32_k$(0));
    assertEquals(null, m.get_c1px32_k$(1));
    m.remove_cqondg_k$(0);
  };
  protoOf(IntMapTest).name2_ovm8cp_k$ = function () {
    var m = IntMap_init_$Create$();
    var inductionVariable = 0;
    if (inductionVariable < 1000)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        m.set_8cx7ps_k$(n, imul(n, 1000));
      }
       while (inductionVariable < 1000);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 1000)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        assertEquals(imul(n_0, 1000), m.get_c1px32_k$(n_0));
      }
       while (inductionVariable_0 < 1000);
    assertEquals(null, m.get_c1px32_k$(-1));
    assertEquals(null, m.get_c1px32_k$(1001));
  };
  protoOf(IntMapTest).testIntMapOf_6585ty_k$ = function () {
    var map = intMapOf([to(1, 'one'), to(2, 'two')]);
    assertEquals(null, map.get_c1px32_k$(0));
    assertEquals('one', map.get_c1px32_k$(1));
    assertEquals('two', map.get_c1px32_k$(2));
    assertEquals(null, map.get_c1px32_k$(3));
  };
  protoOf(IntMapTest).testIntMapGetOrPut_hd16ud_k$ = function () {
    var map = intMapOf([]);
    var inductionVariable = 0;
    if (inductionVariable < 3)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.IntMap.getOrPut' call
        var res = map.get_c1px32_k$(n);
        if (res == null) {
          // Inline function 'korlibs.datastructure.IntMapTest.testIntMapGetOrPut.<anonymous>' call
          var tmp$ret$0 = '' + (-n | 0);
          map.set_8cx7ps_k$(n, tmp$ret$0);
        }
        ensureNotNull(map.get_c1px32_k$(n));
      }
       while (inductionVariable < 3);
    // Inline function 'kotlin.collections.sortedBy' call
    var this_0 = toMap_1(map).get_entries_p20ztl_k$();
    // Inline function 'kotlin.comparisons.compareBy' call
    var tmp = IntMapTest$testIntMapGetOrPut$lambda;
    var tmp$ret$2 = new sam$kotlin_Comparator$0_1(tmp);
    var tmp$ret$3 = sortedWith(this_0, tmp$ret$2);
    assertEquals('0=0, 1=-1, 2=-2', joinToString(tmp$ret$3, ', '));
  };
  function test_fun_izoufj_32() {
    suite('IntMapTest', false, test_fun$IntMapTest_test_fun_z60i2p);
  }
  function intStream$slambda($this_intStream, resultContinuation) {
    this.$this_intStream_1 = $this_intStream;
    CoroutineImpl.call(this, resultContinuation);
  }
  protoOf(intStream$slambda).invoke_pgsqqr_k$ = function ($this$sequence, $completion) {
    var tmp = this.create_rkcuc7_k$($this$sequence, $completion);
    tmp.set_result_xj64lm_k$(Unit_getInstance());
    tmp.set_exception_px07aa_k$(null);
    return tmp.doResume_5yljmg_k$();
  };
  protoOf(intStream$slambda).invoke_qns8j1_k$ = function (p1, $completion) {
    return this.invoke_pgsqqr_k$(p1 instanceof SequenceScope ? p1 : THROW_CCE(), $completion);
  };
  protoOf(intStream$slambda).doResume_5yljmg_k$ = function () {
    var suspendResult = this.get_result_iyg5d2_k$();
    $sm: do
      try {
        var tmp = this.get_state_iypx7s_k$();
        switch (tmp) {
          case 0:
            this.set_exceptionState_fex74n_k$(3);
            this.set_state_rjd8d0_k$(1);
            continue $sm;
          case 1:
            if (!true) {
              this.set_state_rjd8d0_k$(4);
              continue $sm;
            }

            this.set_state_rjd8d0_k$(2);
            suspendResult = this.$this$sequence_1.yield_3xhcex_k$(this.$this_intStream_1.nextInt_ujorgc_k$(), this);
            if (suspendResult === get_COROUTINE_SUSPENDED()) {
              return suspendResult;
            }

            continue $sm;
          case 2:
            this.set_state_rjd8d0_k$(1);
            continue $sm;
          case 3:
            throw this.get_exception_x0n6w6_k$();
          case 4:
            return Unit_getInstance();
        }
      } catch ($p) {
        var e = $p;
        if (this.get_exceptionState_wflpxn_k$() === 3) {
          throw e;
        } else {
          this.set_state_rjd8d0_k$(this.get_exceptionState_wflpxn_k$());
          this.set_exception_px07aa_k$(e);
        }
      }
     while (true);
  };
  protoOf(intStream$slambda).create_rkcuc7_k$ = function ($this$sequence, completion) {
    var i = new intStream$slambda(this.$this_intStream_1, completion);
    i.$this$sequence_1 = $this$sequence;
    return i;
  };
  protoOf(intStream$slambda).create_wyq9v6_k$ = function (value, completion) {
    return this.create_rkcuc7_k$(value instanceof SequenceScope ? value : THROW_CCE(), completion);
  };
  function intStream$slambda_0($this_intStream, resultContinuation) {
    var i = new intStream$slambda($this_intStream, resultContinuation);
    var l = function ($this$sequence, $completion) {
      return i.invoke_pgsqqr_k$($this$sequence, $completion);
    };
    l.$arity = 1;
    return l;
  }
  function intStream$slambda_1($this_intStream, $from, $until, resultContinuation) {
    this.$this_intStream_1 = $this_intStream;
    this.$from_1 = $from;
    this.$until_1 = $until;
    CoroutineImpl.call(this, resultContinuation);
  }
  protoOf(intStream$slambda_1).invoke_pgsqqr_k$ = function ($this$sequence, $completion) {
    var tmp = this.create_rkcuc7_k$($this$sequence, $completion);
    tmp.set_result_xj64lm_k$(Unit_getInstance());
    tmp.set_exception_px07aa_k$(null);
    return tmp.doResume_5yljmg_k$();
  };
  protoOf(intStream$slambda_1).invoke_qns8j1_k$ = function (p1, $completion) {
    return this.invoke_pgsqqr_k$(p1 instanceof SequenceScope ? p1 : THROW_CCE(), $completion);
  };
  protoOf(intStream$slambda_1).doResume_5yljmg_k$ = function () {
    var suspendResult = this.get_result_iyg5d2_k$();
    $sm: do
      try {
        var tmp = this.get_state_iypx7s_k$();
        switch (tmp) {
          case 0:
            this.set_exceptionState_fex74n_k$(3);
            this.set_state_rjd8d0_k$(1);
            continue $sm;
          case 1:
            if (!true) {
              this.set_state_rjd8d0_k$(4);
              continue $sm;
            }

            this.set_state_rjd8d0_k$(2);
            suspendResult = this.$this$sequence_1.yield_3xhcex_k$(this.$this_intStream_1.nextInt_ak696k_k$(this.$from_1, this.$until_1), this);
            if (suspendResult === get_COROUTINE_SUSPENDED()) {
              return suspendResult;
            }

            continue $sm;
          case 2:
            this.set_state_rjd8d0_k$(1);
            continue $sm;
          case 3:
            throw this.get_exception_x0n6w6_k$();
          case 4:
            return Unit_getInstance();
        }
      } catch ($p) {
        var e = $p;
        if (this.get_exceptionState_wflpxn_k$() === 3) {
          throw e;
        } else {
          this.set_state_rjd8d0_k$(this.get_exceptionState_wflpxn_k$());
          this.set_exception_px07aa_k$(e);
        }
      }
     while (true);
  };
  protoOf(intStream$slambda_1).create_rkcuc7_k$ = function ($this$sequence, completion) {
    var i = new intStream$slambda_1(this.$this_intStream_1, this.$from_1, this.$until_1, completion);
    i.$this$sequence_1 = $this$sequence;
    return i;
  };
  protoOf(intStream$slambda_1).create_wyq9v6_k$ = function (value, completion) {
    return this.create_rkcuc7_k$(value instanceof SequenceScope ? value : THROW_CCE(), completion);
  };
  function intStream$slambda_2($this_intStream, $from, $until, resultContinuation) {
    var i = new intStream$slambda_1($this_intStream, $from, $until, resultContinuation);
    var l = function ($this$sequence, $completion) {
      return i.invoke_pgsqqr_k$($this$sequence, $completion);
    };
    l.$arity = 1;
    return l;
  }
  function test_fun$IntMapTest_test_fun_z60i2p() {
    test('smoke', false, test_fun$IntMapTest_test_fun$smoke_test_fun_uqcbwn);
    test('simple', false, test_fun$IntMapTest_test_fun$simple_test_fun_pnx0na);
    test('name2', false, test_fun$IntMapTest_test_fun$name2_test_fun_qnulw1);
    test('testIntMapOf', false, test_fun$IntMapTest_test_fun$testIntMapOf_test_fun_7chgq2);
    test('testIntMapGetOrPut', false, test_fun$IntMapTest_test_fun$testIntMapGetOrPut_test_fun_6quxm1);
    return Unit_getInstance();
  }
  function test_fun$IntMapTest_test_fun$smoke_test_fun_uqcbwn() {
    var tmp = new IntMapTest();
    tmp.smoke_2mwr6n_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMapTest_test_fun$simple_test_fun_pnx0na() {
    var tmp = new IntMapTest();
    tmp.simple_w8bz1a_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMapTest_test_fun$name2_test_fun_qnulw1() {
    var tmp = new IntMapTest();
    tmp.name2_ovm8cp_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMapTest_test_fun$testIntMapOf_test_fun_7chgq2() {
    var tmp = new IntMapTest();
    tmp.testIntMapOf_6585ty_k$();
    return Unit_getInstance();
  }
  function test_fun$IntMapTest_test_fun$testIntMapGetOrPut_test_fun_6quxm1() {
    var tmp = new IntMapTest();
    tmp.testIntMapGetOrPut_hd16ud_k$();
    return Unit_getInstance();
  }
  function IntSetTest() {
  }
  protoOf(IntSetTest).testSmoke_4xe6nh_k$ = function () {
    var set = intSetOf(new Int32Array([1, 2, 4]));
    assertEquals(3, set.get_size_woubt6_k$());
    assertEquals(false, set.contains_7q95ev_k$(0));
    assertEquals(true, set.contains_7q95ev_k$(1));
    assertEquals(true, set.contains_7q95ev_k$(2));
    assertEquals(false, set.contains_7q95ev_k$(3));
    assertEquals(true, set.contains_7q95ev_k$(4));
    assertEquals(false, set.contains_7q95ev_k$(5));
    set.remove_cqondg_k$(2);
    assertEquals(2, set.get_size_woubt6_k$());
    assertEquals(true, set.contains_7q95ev_k$(1));
    assertEquals(false, set.contains_7q95ev_k$(2));
    assertEquals(true, set.contains_7q95ev_k$(4));
  };
  protoOf(IntSetTest).testToString_o64fqq_k$ = function () {
    assertEquals('[]', intSetOf(new Int32Array([])).toString());
    assertEquals('[1, 2, 4]', intSetOf(new Int32Array([1, 2, 4])).toString());
    assertEquals('[1, 2, 4]', intSetOf(new Int32Array([4, 2, 1])).toString());
  };
  protoOf(IntSetTest).test2_6xsqgg_k$ = function () {
    assertEquals(listOf([1, 2, 3, 4]), sorted(toList_0(intSetOf(new Int32Array([1, 2, 3, 4])))));
  };
  function test_fun_izoufj_33() {
    suite('IntSetTest', false, test_fun$IntSetTest_test_fun_jjiuo7);
  }
  function test_fun$IntSetTest_test_fun_jjiuo7() {
    test('testSmoke', false, test_fun$IntSetTest_test_fun$testSmoke_test_fun_xn1drz);
    test('testToString', false, test_fun$IntSetTest_test_fun$testToString_test_fun_66ebns);
    test('test2', false, test_fun$IntSetTest_test_fun$test2_test_fun_llxw5g);
    return Unit_getInstance();
  }
  function test_fun$IntSetTest_test_fun$testSmoke_test_fun_xn1drz() {
    var tmp = new IntSetTest();
    tmp.testSmoke_4xe6nh_k$();
    return Unit_getInstance();
  }
  function test_fun$IntSetTest_test_fun$testToString_test_fun_66ebns() {
    var tmp = new IntSetTest();
    tmp.testToString_o64fqq_k$();
    return Unit_getInstance();
  }
  function test_fun$IntSetTest_test_fun$test2_test_fun_llxw5g() {
    var tmp = new IntSetTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function testSimple$info(cache) {
    return '' + cache.get_size_woubt6_k$() + ':' + cache.get_computedMemory_nuxn61_k$().toString();
  }
  function testAtLeastOneDisabled$info(cache) {
    return '' + cache.get_size_woubt6_k$() + ':' + cache.get_computedMemory_nuxn61_k$().toString();
  }
  function LRUCacheTest$testSimple$lambda(it) {
    return it.length;
  }
  function LRUCacheTest$testAtLeastOneDisabled$lambda(it) {
    return it.length;
  }
  function LRUCacheTest() {
  }
  protoOf(LRUCacheTest).testSimple_w8fpik_k$ = function () {
    var tmp = new Long(10, 0);
    var cache = new LRUCache(6, tmp, VOID, LRUCacheTest$testSimple$lambda);
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$(0, 'hello');
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$(1, 'test');
    assertEquals('2:9', testSimple$info(cache));
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$(2, 'demo');
    assertEquals('2:8', testSimple$info(cache));
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$(3, 'ok');
    assertEquals('3:10', testSimple$info(cache));
    // Inline function 'kotlin.collections.set' call
    cache.put_4fpzoq_k$(4, '1');
    assertEquals('3:7', testSimple$info(cache));
    // Inline function 'kotlin.collections.set' call
    var value = repeat('1', 20);
    cache.put_4fpzoq_k$(10, value);
    assertEquals('1:20', testSimple$info(cache));
    var inductionVariable = 0;
    if (inductionVariable < 20)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'kotlin.collections.set' call
        cache.put_4fpzoq_k$(n, 'n');
      }
       while (inductionVariable < 20);
    assertEquals('6:6', testSimple$info(cache));
  };
  protoOf(LRUCacheTest).testAtLeastOneDisabled_9icqku_k$ = function () {
    var tmp = new Long(10, 0);
    var cache = new LRUCache(10, tmp, false, LRUCacheTest$testAtLeastOneDisabled$lambda);
    // Inline function 'kotlin.collections.set' call
    var value = repeat('1', 20);
    cache.put_4fpzoq_k$(10, value);
    assertEquals('0:0', testAtLeastOneDisabled$info(cache));
  };
  function test_fun_izoufj_34() {
    suite('LRUCacheTest', false, test_fun$LRUCacheTest_test_fun_ar2plz);
  }
  function test_fun$LRUCacheTest_test_fun_ar2plz() {
    test('testSimple', false, test_fun$LRUCacheTest_test_fun$testSimple_test_fun_n4r90i);
    test('testAtLeastOneDisabled', false, test_fun$LRUCacheTest_test_fun$testAtLeastOneDisabled_test_fun_tu3tjs);
    return Unit_getInstance();
  }
  function test_fun$LRUCacheTest_test_fun$testSimple_test_fun_n4r90i() {
    var tmp = new LRUCacheTest();
    tmp.testSimple_w8fpik_k$();
    return Unit_getInstance();
  }
  function test_fun$LRUCacheTest_test_fun$testAtLeastOneDisabled_test_fun_tu3tjs() {
    var tmp = new LRUCacheTest();
    tmp.testAtLeastOneDisabled_9icqku_k$();
    return Unit_getInstance();
  }
  function ListReaderTest() {
  }
  protoOf(ListReaderTest).test_w5z8la_k$ = function () {
    var reader_0 = reader(listOf([1, 2, 3]));
    assertEquals(true, reader_0.get_hasMore_csdhd2_k$());
    assertEquals(false, reader_0.get_eof_18j6gd_k$());
    assertEquals(1, reader_0.peek_21nx7_k$());
    assertEquals(1, reader_0.peek_21nx7_k$());
    assertEquals(1, reader_0.read_22xsm_k$());
    assertEquals(2, reader_0.read_22xsm_k$());
    assertEquals(3, expect(reader_0, 3));
    assertEquals(false, reader_0.get_hasMore_csdhd2_k$());
    assertEquals(true, reader_0.get_eof_18j6gd_k$());
  };
  protoOf(ListReaderTest).expect_qy225z_k$ = function () {
    var reader_0 = reader(listOf([1, 2, 3]));
    expect(reader_0, 1);
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      expect(reader_0, -2);
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    var tmp$ret$3 = checkResultIsFailure_0(null, tmp$ret$2);
    assertEquals("Expecting '-2' but found '2'", tmp$ret$3.message);
  };
  function test_fun_izoufj_35() {
    suite('ListReaderTest', false, test_fun$ListReaderTest_test_fun_7fwzu3);
  }
  function test_fun$ListReaderTest_test_fun_7fwzu3() {
    test('test', false, test_fun$ListReaderTest_test_fun$test_test_fun_ch4h1i);
    test('expect', false, test_fun$ListReaderTest_test_fun$expect_test_fun_6kudsx);
    return Unit_getInstance();
  }
  function test_fun$ListReaderTest_test_fun$test_test_fun_ch4h1i() {
    var tmp = new ListReaderTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$ListReaderTest_test_fun$expect_test_fun_6kudsx() {
    var tmp = new ListReaderTest();
    tmp.expect_qy225z_k$();
    return Unit_getInstance();
  }
  function MapListTest() {
  }
  protoOf(MapListTest).test_w5z8la_k$ = function () {
    var map = linkedHashMapListOf([to('a', 10), to('a', 20), to('b', 30)]);
    assertEquals(10, getFirst(map, 'a'));
    assertEquals(20, getLast(map, 'a'));
    assertEquals(30, getFirst(map, 'b'));
    assertEquals(30, getLast(map, 'b'));
    assertEquals(null, getLast(map, 'c'));
    assertEquals(listOf([to('a', 10), to('a', 20), to('b', 30)]), flatten(map));
  };
  function test_fun_izoufj_36() {
    suite('MapListTest', false, test_fun$MapListTest_test_fun_p4kd3c);
  }
  function test_fun$MapListTest_test_fun_p4kd3c() {
    test('test', false, test_fun$MapListTest_test_fun$test_test_fun_h5qrbn);
    return Unit_getInstance();
  }
  function test_fun$MapListTest_test_fun$test_test_fun_h5qrbn() {
    var tmp = new MapListTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function Demo_0() {
    this.x_1 = 0;
    this.y_1 = 0;
  }
  protoOf(Demo_0).set_x_z5k1bn_k$ = function (_set____db54di) {
    this.x_1 = _set____db54di;
  };
  protoOf(Demo_0).get_x_1mhr67_k$ = function () {
    return this.x_1;
  };
  protoOf(Demo_0).set_y_zw46k_k$ = function (_set____db54di) {
    this.y_1 = _set____db54di;
  };
  protoOf(Demo_0).get_y_1mhr68_k$ = function () {
    return this.y_1;
  };
  function PoolTest$name$lambda($totalResetCount) {
    return function (it) {
      var _unary__edvuaz = $totalResetCount._v;
      $totalResetCount._v = _unary__edvuaz + 1 | 0;
      it.x_1 = 0;
      it.y_1 = 0;
      return Unit_getInstance();
    };
  }
  function PoolTest$name$lambda_0($totalAllocCount) {
    return function (it) {
      var _unary__edvuaz = $totalAllocCount._v;
      $totalAllocCount._v = _unary__edvuaz + 1 | 0;
      return new Demo_0();
    };
  }
  function PoolTest() {
  }
  protoOf(PoolTest).name_ugbo79_k$ = function () {
    var totalResetCount = {_v: 0};
    var totalAllocCount = {_v: 0};
    var tmp = PoolTest$name$lambda(totalResetCount);
    var pool = new Pool(tmp, VOID, PoolTest$name$lambda_0(totalAllocCount));
    var a = pool.alloc_1jbayd_k$();
    var b = pool.alloc_1jbayd_k$();
    var c = pool.alloc_1jbayd_k$();
    assertEquals(0, pool.get_itemsInPool_2zl6so_k$());
    pool.free_6u7ei6_k$(c);
    assertEquals(1, pool.get_itemsInPool_2zl6so_k$());
    pool.free_6u7ei6_k$(b);
    assertEquals(2, pool.get_itemsInPool_2zl6so_k$());
    pool.free_6u7ei6_k$(a);
    assertEquals(3, pool.get_itemsInPool_2zl6so_k$());
    var d = pool.alloc_1jbayd_k$();
    assertEquals(2, pool.get_itemsInPool_2zl6so_k$());
    pool.free_6u7ei6_k$(d);
    $l$block: {
      // Inline function 'korlibs.datastructure.Pool.alloc' call
      var temp = pool.alloc_1jbayd_k$();
      try {
        assertEquals(2, pool.get_itemsInPool_2zl6so_k$());
        break $l$block;
      }finally {
        pool.free_6u7ei6_k$(temp);
      }
    }
    assertEquals(3, pool.get_itemsInPool_2zl6so_k$());
    assertEquals(5, totalResetCount._v);
    assertEquals(3, totalAllocCount._v);
  };
  function test_fun_izoufj_37() {
    suite('PoolTest', false, test_fun$PoolTest_test_fun_4wvifk);
  }
  function test_fun$PoolTest_test_fun_4wvifk() {
    test('name', false, test_fun$PoolTest_test_fun$name_test_fun_u1kbta);
    return Unit_getInstance();
  }
  function test_fun$PoolTest_test_fun$name_test_fun_u1kbta() {
    var tmp = new PoolTest();
    tmp.name_ugbo79_k$();
    return Unit_getInstance();
  }
  function _get_o1__ndc6cp($this) {
    return $this.o1__1;
  }
  function _get_o2__ndc6dk($this) {
    return $this.o2__1;
  }
  function _get_o3__ndc6ef($this) {
    return $this.o3__1;
  }
  function _get_o4__ndc6fa($this) {
    return $this.o4__1;
  }
  function _get_o5__ndc6g5($this) {
    return $this.o5__1;
  }
  function _get_o6__ndc6h0($this) {
    return $this.o6__1;
  }
  function _get_pq__ndc8mi($this) {
    return $this.pq_1;
  }
  function _get_n1__ndc5m0($this) {
    return $this.n1__1;
  }
  function QueueItem(name, priority) {
    this.name_1 = name;
    this.priority_1 = priority;
  }
  protoOf(QueueItem).set_name_aqnlwe_k$ = function (_set____db54di) {
    this.name_1 = _set____db54di;
  };
  protoOf(QueueItem).get_name_woqyms_k$ = function () {
    return this.name_1;
  };
  protoOf(QueueItem).set_priority_9imq2v_k$ = function (_set____db54di) {
    this.priority_1 = _set____db54di;
  };
  protoOf(QueueItem).get_priority_jyafsd_k$ = function () {
    return this.priority_1;
  };
  protoOf(QueueItem).toString = function () {
    return this.name_1;
  };
  function sam$kotlin_Comparator$0_2(function_0) {
    this.function_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_2).compare_bczr_k$ = function (a, b) {
    return this.function_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_2).compare = function (a, b) {
    return this.compare_bczr_k$(a, b);
  };
  function test6$checkInOrder(_this__u8e3s4) {
    var inductionVariable = 1;
    var last = _this__u8e3s4.get_size_woubt6_k$();
    if (inductionVariable < last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (_this__u8e3s4.get_c1px32_k$(n).v_1 < _this__u8e3s4.get_c1px32_k$(n - 1 | 0).v_1)
          return n;
      }
       while (inductionVariable < last);
    return -1;
  }
  function PriorityQueueTest$test2$Demo(value) {
    this.value_1 = value;
  }
  protoOf(PriorityQueueTest$test2$Demo).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  protoOf(PriorityQueueTest$test2$Demo).component1_7eebsc_k$ = function () {
    return this.value_1;
  };
  protoOf(PriorityQueueTest$test2$Demo).copy_dtzw2h_k$ = function (value) {
    return new PriorityQueueTest$test2$Demo(value);
  };
  protoOf(PriorityQueueTest$test2$Demo).copy$default_48pezw_k$ = function (value, $super) {
    value = value === VOID ? this.value_1 : value;
    return $super === VOID ? this.copy_dtzw2h_k$(value) : $super.copy_dtzw2h_k$.call(this, value);
  };
  protoOf(PriorityQueueTest$test2$Demo).toString = function () {
    return 'Demo(value=' + this.value_1.toString() + ')';
  };
  protoOf(PriorityQueueTest$test2$Demo).hashCode = function () {
    return this.value_1.hashCode();
  };
  protoOf(PriorityQueueTest$test2$Demo).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof PriorityQueueTest$test2$Demo))
      return false;
    var tmp0_other_with_cast = other instanceof PriorityQueueTest$test2$Demo ? other : THROW_CCE();
    if (!this.value_1.equals(tmp0_other_with_cast.value_1))
      return false;
    return true;
  };
  function PriorityQueueTest$test2$lambda(a, b) {
    return a.value_1.compareTo_9jj042_k$(b.value_1);
  }
  function PriorityQueueTest$test3$lambda(a, b) {
    return compareTo(-a | 0, -b | 0);
  }
  function PriorityQueueTest$test4$WI(v) {
    this.v_1 = v;
  }
  protoOf(PriorityQueueTest$test4$WI).get_v_1mhr65_k$ = function () {
    return this.v_1;
  };
  function PriorityQueueTest$test4$lambda(a, b) {
    return compareTo(-a.v_1 | 0, -b.v_1 | 0);
  }
  function PriorityQueueTest$test5$WI(v) {
    this.v_1 = v;
  }
  protoOf(PriorityQueueTest$test5$WI).set_v_ylrrm7_k$ = function (_set____db54di) {
    this.v_1 = _set____db54di;
  };
  protoOf(PriorityQueueTest$test5$WI).get_v_1mhr65_k$ = function () {
    return this.v_1;
  };
  protoOf(PriorityQueueTest$test5$WI).toString = function () {
    return '' + this.v_1;
  };
  protoOf(PriorityQueueTest$test5$WI).component1_7eebsc_k$ = function () {
    return this.v_1;
  };
  protoOf(PriorityQueueTest$test5$WI).copy_ns6qmb_k$ = function (v) {
    return new PriorityQueueTest$test5$WI(v);
  };
  protoOf(PriorityQueueTest$test5$WI).copy$default_o38cse_k$ = function (v, $super) {
    v = v === VOID ? this.v_1 : v;
    return $super === VOID ? this.copy_ns6qmb_k$(v) : $super.copy_ns6qmb_k$.call(this, v);
  };
  protoOf(PriorityQueueTest$test5$WI).hashCode = function () {
    return this.v_1;
  };
  protoOf(PriorityQueueTest$test5$WI).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof PriorityQueueTest$test5$WI))
      return false;
    var tmp0_other_with_cast = other instanceof PriorityQueueTest$test5$WI ? other : THROW_CCE();
    if (!(this.v_1 === tmp0_other_with_cast.v_1))
      return false;
    return true;
  };
  function PriorityQueueTest$test5$lambda(a, b) {
    return compareTo(a.v_1, b.v_1);
  }
  function PriorityQueueTest$test6$WI(v) {
    this.v_1 = v;
  }
  protoOf(PriorityQueueTest$test6$WI).set_v_ylrrm7_k$ = function (_set____db54di) {
    this.v_1 = _set____db54di;
  };
  protoOf(PriorityQueueTest$test6$WI).get_v_1mhr65_k$ = function () {
    return this.v_1;
  };
  protoOf(PriorityQueueTest$test6$WI).toString = function () {
    return '' + this.v_1;
  };
  protoOf(PriorityQueueTest$test6$WI).component1_7eebsc_k$ = function () {
    return this.v_1;
  };
  protoOf(PriorityQueueTest$test6$WI).copy_ns6qmb_k$ = function (v) {
    return new PriorityQueueTest$test6$WI(v);
  };
  protoOf(PriorityQueueTest$test6$WI).copy$default_wj6vhb_k$ = function (v, $super) {
    v = v === VOID ? this.v_1 : v;
    return $super === VOID ? this.copy_ns6qmb_k$(v) : $super.copy_ns6qmb_k$.call(this, v);
  };
  protoOf(PriorityQueueTest$test6$WI).hashCode = function () {
    return this.v_1;
  };
  protoOf(PriorityQueueTest$test6$WI).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof PriorityQueueTest$test6$WI))
      return false;
    var tmp0_other_with_cast = other instanceof PriorityQueueTest$test6$WI ? other : THROW_CCE();
    if (!(this.v_1 === tmp0_other_with_cast.v_1))
      return false;
    return true;
  };
  function PriorityQueueTest$test6$lambda(a, b) {
    return compareTo(a.v_1, b.v_1);
  }
  function PriorityQueueTest$pq$lambda(l, r) {
    return compareTo(l.priority_1, r.priority_1);
  }
  function PriorityQueueTest() {
    this.o1__1 = new QueueItem('o1', 1);
    this.o2__1 = new QueueItem('o2', -5);
    this.o3__1 = new QueueItem('o3', 7);
    this.o4__1 = new QueueItem('o4', 4);
    this.o5__1 = new QueueItem('o5', 9);
    this.o6__1 = new QueueItem('o6', 0);
    var tmp = this;
    // Inline function 'kotlin.apply' call
    var tmp_0 = Companion_getInstance_6();
    var this_0 = tmp_0.invoke$default_f3xc9e_k$(VOID, PriorityQueueTest$pq$lambda);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.PriorityQueueTest.pq.<anonymous>' call
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = [this.o1__1, this.o2__1, this.o3__1, this.o4__1, this.o5__1, this.o6__1];
    addAll_0(this_0, tmp$ret$2);
    tmp.pq_1 = this_0;
    this.n1__1 = new QueueItem('n1', 3);
  }
  protoOf(PriorityQueueTest).test_w5z8la_k$ = function () {
    var pq = Companion_getInstance_6().invoke$default_o6axds_k$();
    pq.add_2y5tg6_k$(10);
    pq.add_2y5tg6_k$(15);
    pq.add_2y5tg6_k$(5);
    assertEquals(3, pq.get_size_woubt6_k$());
    assertEquals(5, pq.removeHead_i5uft0_k$());
    assertEquals(2, pq.get_size_woubt6_k$());
    assertEquals(10, pq.removeHead_i5uft0_k$());
    assertEquals(1, pq.get_size_woubt6_k$());
    assertEquals(15, pq.removeHead_i5uft0_k$());
    assertEquals(0, pq.get_size_woubt6_k$());
  };
  protoOf(PriorityQueueTest).testInt_uxerir_k$ = function () {
    var pq = Companion_getInstance_7().invoke$default_wa1xyh_k$();
    pq.add_lnluon_k$(10);
    pq.add_lnluon_k$(15);
    pq.add_lnluon_k$(5);
    assertEquals(3, pq.get_size_woubt6_k$());
    assertEquals(5, pq.removeHead_i5uft0_k$());
    assertEquals(2, pq.get_size_woubt6_k$());
    assertEquals(10, pq.removeHead_i5uft0_k$());
    assertEquals(1, pq.get_size_woubt6_k$());
    assertEquals(15, pq.removeHead_i5uft0_k$());
    assertEquals(0, pq.get_size_woubt6_k$());
  };
  protoOf(PriorityQueueTest).test2_6xsqgg_k$ = function () {
    var tmp = Companion_getInstance_6();
    var tmp_0 = PriorityQueueTest$test2$lambda;
    var pq = tmp.invoke$default_rvw0kw_k$(new sam$kotlin_Comparator$0_2(tmp_0));
    var a = new PriorityQueueTest$test2$Demo(new Long(10, 0));
    var b = new PriorityQueueTest$test2$Demo(new Long(15, 0));
    var c = new PriorityQueueTest$test2$Demo(new Long(5, 0));
    pq.add_2y5tg6_k$(a);
    pq.add_2y5tg6_k$(b);
    pq.add_2y5tg6_k$(c);
    assertEquals(3, pq.get_size_woubt6_k$());
    assertEquals(c, pq.removeHead_i5uft0_k$());
    assertEquals(2, pq.get_size_woubt6_k$());
    assertEquals(a, pq.removeHead_i5uft0_k$());
    assertEquals(1, pq.get_size_woubt6_k$());
    assertEquals(b, pq.removeHead_i5uft0_k$());
    assertEquals(0, pq.get_size_woubt6_k$());
  };
  protoOf(PriorityQueueTest).test3_ffxdzl_k$ = function () {
    var tmp = Companion_getInstance_7();
    var pq = tmp.invoke$default_jys7t9_k$(VOID, PriorityQueueTest$test3$lambda);
    pq.addAll_c9pvhk_k$(listOf([1, 2, 3, 4]));
    assertEquals(listOf([4, 3, 2, 1]), toList_0(pq));
  };
  protoOf(PriorityQueueTest).test4_x7gjji_k$ = function () {
    var tmp = Companion_getInstance_6();
    var pq = tmp.invoke$default_f3xc9e_k$(VOID, PriorityQueueTest$test4$lambda);
    // Inline function 'kotlin.collections.map' call
    var this_0 = listOf([1, 2, 3, 4]);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.PriorityQueueTest.test4.<anonymous>' call
      var tmp$ret$0 = new PriorityQueueTest$test4$WI(item);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    pq.addAll_dmwg7m_k$(destination);
    var tmp_0 = listOf([4, 3, 2, 1]);
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(pq, 10));
    var tmp0_iterator_0 = pq.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.PriorityQueueTest.test4.<anonymous>' call
      var tmp$ret$3 = item_0.v_1;
      destination_0.add_utx5q5_k$(tmp$ret$3);
    }
    assertEquals(tmp_0, destination_0);
  };
  protoOf(PriorityQueueTest).test5_atqf3h_k$ = function () {
    var tmp = Companion_getInstance_6();
    var pq = tmp.invoke$default_f3xc9e_k$(VOID, PriorityQueueTest$test5$lambda);
    var a = new PriorityQueueTest$test5$WI(10);
    var b = new PriorityQueueTest$test5$WI(20);
    var c = new PriorityQueueTest$test5$WI(30);
    var d = new PriorityQueueTest$test5$WI(40);
    pq.addAll_dmwg7m_k$(listOf([a, b, c, d]));
    b.v_1 = 35;
    pq.updateObject_g477of_k$(b);
    assertEquals('[10, 30, 35, 40]', toString(toList_1(pq.toArraySorted_2aj2th_k$())));
    b.v_1 = 15;
    pq.updateObject_g477of_k$(b);
    assertEquals('[10, 15, 30, 40]', toString(toList_1(pq.toArraySorted_2aj2th_k$())));
    b.v_1 = 0;
    pq.updateObject_g477of_k$(b);
    assertEquals('[0, 10, 30, 40]', toString(toList_1(pq.toArraySorted_2aj2th_k$())));
    b.v_1 = 41;
    pq.updateObject_g477of_k$(b);
    assertEquals('[10, 30, 40, 41]', toString(toList_1(pq.toArraySorted_2aj2th_k$())));
  };
  protoOf(PriorityQueueTest).test6_bjzpck_k$ = function () {
    var tmp = Companion_getInstance_6();
    var pq = tmp.invoke$default_f3xc9e_k$(VOID, PriorityQueueTest$test6$lambda);
    var rand = Random(new Long(0, 0));
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, 8);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.PriorityQueueTest.test6.<anonymous>' call
      var tmp$ret$0 = new PriorityQueueTest$test6$WI(rand.nextInt_ujorgc_k$());
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    var instances = destination;
    pq.addAll_dmwg7m_k$(instances);
    var inductionVariable = 0;
    if (inductionVariable < 1024)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var instance = instances.get_c1px32_k$(rand.nextInt_ak696k_k$(0, instances.get_size_woubt6_k$()));
        instance.v_1 = rand.nextInt_ujorgc_k$();
        pq.updateObject_g477of_k$(instance);
        var sorted = toList_1(pq.toArraySorted_2aj2th_k$());
        assertEquals(-1, test6$checkInOrder(sorted), toString(sorted));
      }
       while (inductionVariable < 1024);
  };
  protoOf(PriorityQueueTest).testInitialOrder_s2nelw_k$ = function () {
    assertEquals('o2', this.pq_1.get_head_won7e1_k$().toString());
    assertEquals('[o2, o6, o1, o4, o3, o5]', toString(toList_1(this.pq_1.toArraySorted_2aj2th_k$())));
  };
  protoOf(PriorityQueueTest).testUpdateOrder_yf8sl9_k$ = function () {
    this.o3__1.priority_1 = -6;
    this.pq_1.updateObject_g477of_k$(this.o3__1);
    assertEquals('o3', this.pq_1.get_head_won7e1_k$().toString());
    assertEquals('[o3, o2, o6, o1, o4, o5]', toString(toList_1(this.pq_1.toArraySorted_2aj2th_k$())));
  };
  protoOf(PriorityQueueTest).testPostUpdateInsert_nlcfic_k$ = function () {
    this.o3__1.priority_1 = -6;
    this.pq_1.updateObject_g477of_k$(this.o3__1);
    this.pq_1.add_2y5tg6_k$(this.n1__1);
    assertEquals('[o3, o2, o6, o1, n1, o4, o5]', toString(toList_1(this.pq_1.toArraySorted_2aj2th_k$())));
  };
  protoOf(PriorityQueueTest).testContains_ynj8jj_k$ = function () {
    assertTrue(this.pq_1.contains_c6f694_k$(this.o4__1));
    assertFalse(this.pq_1.contains_c6f694_k$(this.n1__1));
  };
  function test_fun_izoufj_38() {
    suite('PriorityQueueTest', false, test_fun$PriorityQueueTest_test_fun_armbtn);
  }
  function test_fun$PriorityQueueTest_test_fun_armbtn() {
    test('test', false, test_fun$PriorityQueueTest_test_fun$test_test_fun_5xwsn4);
    test('testInt', false, test_fun$PriorityQueueTest_test_fun$testInt_test_fun_f416fn);
    test('test2', false, test_fun$PriorityQueueTest_test_fun$test2_test_fun_9rx3bk);
    test('test3', false, test_fun$PriorityQueueTest_test_fun$test3_test_fun_6ix4j3);
    test('test4', false, test_fun$PriorityQueueTest_test_fun$test4_test_fun_39x5qm);
    test('test5', false, test_fun$PriorityQueueTest_test_fun$test5_test_fun_x6y5);
    test('test6', false, test_fun$PriorityQueueTest_test_fun$test6_test_fun_382ruc);
    test('testInitialOrder', false, test_fun$PriorityQueueTest_test_fun$testInitialOrder_test_fun_k8zc52);
    test('testUpdateOrder', false, test_fun$PriorityQueueTest_test_fun$testUpdateOrder_test_fun_x6sadf);
    test('testPostUpdateInsert', false, test_fun$PriorityQueueTest_test_fun$testPostUpdateInsert_test_fun_tnsk1u);
    test('testContains', false, test_fun$PriorityQueueTest_test_fun$testContains_test_fun_v3gfa7);
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$test_test_fun_5xwsn4() {
    var tmp = new PriorityQueueTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$testInt_test_fun_f416fn() {
    var tmp = new PriorityQueueTest();
    tmp.testInt_uxerir_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$test2_test_fun_9rx3bk() {
    var tmp = new PriorityQueueTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$test3_test_fun_6ix4j3() {
    var tmp = new PriorityQueueTest();
    tmp.test3_ffxdzl_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$test4_test_fun_39x5qm() {
    var tmp = new PriorityQueueTest();
    tmp.test4_x7gjji_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$test5_test_fun_x6y5() {
    var tmp = new PriorityQueueTest();
    tmp.test5_atqf3h_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$test6_test_fun_382ruc() {
    var tmp = new PriorityQueueTest();
    tmp.test6_bjzpck_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$testInitialOrder_test_fun_k8zc52() {
    var tmp = new PriorityQueueTest();
    tmp.testInitialOrder_s2nelw_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$testUpdateOrder_test_fun_x6sadf() {
    var tmp = new PriorityQueueTest();
    tmp.testUpdateOrder_yf8sl9_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$testPostUpdateInsert_test_fun_tnsk1u() {
    var tmp = new PriorityQueueTest();
    tmp.testPostUpdateInsert_nlcfic_k$();
    return Unit_getInstance();
  }
  function test_fun$PriorityQueueTest_test_fun$testContains_test_fun_v3gfa7() {
    var tmp = new PriorityQueueTest();
    tmp.testContains_ynj8jj_k$();
    return Unit_getInstance();
  }
  function QueueTest() {
  }
  protoOf(QueueTest).name_ugbo79_k$ = function () {
    var queue = new TGenQueue();
    var inductionVariable = 0;
    if (inductionVariable < 1025)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        queue.enqueue_iirzep_k$(true);
      }
       while (inductionVariable < 1025);
    var inductionVariable_0 = 0;
    if (inductionVariable_0 < 1025)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        assertEquals(true, queue.dequeue_pq4oxc_k$());
      }
       while (inductionVariable_0 < 1025);
  };
  protoOf(QueueTest).test1_tbiuwh_k$ = function () {
    var queue = new TGenQueue();
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      queue.dequeue_pq4oxc_k$();
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    checkResultIsFailure(exceptionClass, null, tmp$ret$2);
    queue.enqueue_iirzep_k$(true);
    queue.enqueue_iirzep_k$(true);
    queue.dequeue_pq4oxc_k$();
    queue.dequeue_pq4oxc_k$();
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass_0 = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp_1;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      queue.dequeue_pq4oxc_k$();
      tmp_1 = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_2;
      if ($p instanceof Error) {
        var e_0 = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_2 = _Result___init__impl__xyqfz8(createFailure(e_0));
      } else {
        throw $p;
      }
      tmp_1 = tmp_2;
    }
    var tmp$ret$7 = tmp_1;
    checkResultIsFailure(exceptionClass_0, null, tmp$ret$7);
  };
  protoOf(QueueTest).int_2mdjhd_k$ = function () {
    var queue = new IntQueue();
    queue.enqueue_mi7o8w_k$(10);
    queue.enqueue_mi7o8w_k$(20);
    queue.enqueue_mi7o8w_k$(15);
    // Inline function 'kotlin.collections.isNotEmpty' call
    var tmp$ret$0 = !queue.isEmpty_y1axqb_k$();
    assertEquals(true, tmp$ret$0);
    assertEquals(3, queue.get_size_woubt6_k$());
    assertEquals(10, queue.dequeue_pq4oxc_k$());
    assertEquals(2, queue.get_size_woubt6_k$());
    assertEquals(20, queue.dequeue_pq4oxc_k$());
    assertEquals(1, queue.get_size_woubt6_k$());
    assertEquals(15, queue.dequeue_pq4oxc_k$());
    assertEquals(0, queue.get_size_woubt6_k$());
    // Inline function 'kotlin.collections.isNotEmpty' call
    var tmp$ret$1 = !queue.isEmpty_y1axqb_k$();
    assertEquals(false, tmp$ret$1);
  };
  function test_fun_izoufj_39() {
    suite('QueueTest', false, test_fun$QueueTest_test_fun_j4axwf);
  }
  function test_fun$QueueTest_test_fun_j4axwf() {
    test('name', false, test_fun$QueueTest_test_fun$name_test_fun_e6vr6r);
    test('test1', false, test_fun$QueueTest_test_fun$test1_test_fun_7g783v);
    test('int', false, test_fun$QueueTest_test_fun$int_test_fun_q3v66z);
    return Unit_getInstance();
  }
  function test_fun$QueueTest_test_fun$name_test_fun_e6vr6r() {
    var tmp = new QueueTest();
    tmp.name_ugbo79_k$();
    return Unit_getInstance();
  }
  function test_fun$QueueTest_test_fun$test1_test_fun_7g783v() {
    var tmp = new QueueTest();
    tmp.test1_tbiuwh_k$();
    return Unit_getInstance();
  }
  function test_fun$QueueTest_test_fun$int_test_fun_q3v66z() {
    var tmp = new QueueTest();
    tmp.int_2mdjhd_k$();
    return Unit_getInstance();
  }
  function sam$kotlin_Comparator$0_3(function_0) {
    this.function_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_3).compare_bczr_k$ = function (a, b) {
    return this.function_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_3).compare = function (a, b) {
    return this.compare_bczr_k$(a, b);
  };
  function RadixSortTest$testStringArray$lambda(it) {
    // Inline function 'kotlin.text.lowercaseChar' call
    // Inline function 'kotlin.text.lowercase' call
    var this_0 = it.value_1;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = toString_0(this_0).toLowerCase();
    var tmp$ret$3 = charSequenceGet(tmp$ret$2, 0);
    return new Char(tmp$ret$3);
  }
  function RadixSortTest$testStringArray$lambda_0(a, b) {
    return compareTo_0(a, b, true);
  }
  function RadixSortTest() {
  }
  protoOf(RadixSortTest).testStringArray_elak1y_k$ = function () {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var array1 = ['abc', 'aaa', 'bca', 'acc', 'bbb', 'cad', 'caa', 'dddd', 'aaaa', 'AAA', 'BBB'];
    // Inline function 'kotlin.collections.copyOf' call
    // Inline function 'kotlin.js.asDynamic' call
    var original = array1.slice();
    var items = sortedRadix(array1, VOID, VOID, VOID, RadixSortTest$testStringArray$lambda);
    var tmp = RadixSortTest$testStringArray$lambda_0;
    var tmp_0 = sortedArrayWith(array1, new sam$kotlin_Comparator$0_3(tmp));
    var items2 = isArray(tmp_0) ? tmp_0 : THROW_CCE();
    assertContentEquals(original, array1);
    assertContentEquals(items, items2);
  };
  protoOf(RadixSortTest).testInts_5fd5tm_k$ = function () {
    var random = Random(new Long(-1, -1));
    var tmp = 0;
    var tmp_0 = new Int32Array(100000);
    while (tmp < 100000) {
      tmp_0[tmp] = random.nextInt_ujorgc_k$() & 2147483647;
      tmp = tmp + 1 | 0;
    }
    var ints = tmp_0;
    assertContentEquals_0(sortedArrayRadix(ints), sortedArray(ints));
  };
  function test_fun_izoufj_40() {
    suite('RadixSortTest', false, test_fun$RadixSortTest_test_fun_hkq8q8);
  }
  function test_fun$RadixSortTest_test_fun_hkq8q8() {
    test('testStringArray', false, test_fun$RadixSortTest_test_fun$testStringArray_test_fun_tt7vw1);
    test('testInts', false, test_fun$RadixSortTest_test_fun$testInts_test_fun_6sfgrd);
    return Unit_getInstance();
  }
  function test_fun$RadixSortTest_test_fun$testStringArray_test_fun_tt7vw1() {
    var tmp = new RadixSortTest();
    tmp.testStringArray_elak1y_k$();
    return Unit_getInstance();
  }
  function test_fun$RadixSortTest_test_fun$testInts_test_fun_6sfgrd() {
    var tmp = new RadixSortTest();
    tmp.testInts_5fd5tm_k$();
    return Unit_getInstance();
  }
  function RemoveSortedDuplicatesTest() {
  }
  protoOf(RemoveSortedDuplicatesTest).testRemoveSortedDuplicates_qlrckr_k$ = function () {
    // Inline function 'kotlin.collections.arrayListOf' call
    var tmp = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.collections.arrayListOf' call
    var tmp$ret$1 = ArrayList_init_$Create$_0();
    assertEquals(tmp, removeSortedDuplicates(tmp$ret$1));
    assertEquals(arrayListOf([1]), removeSortedDuplicates(arrayListOf([1])));
    assertEquals(arrayListOf([1, 2]), removeSortedDuplicates(arrayListOf([1, 2])));
    assertEquals(arrayListOf([1, 2, 3]), removeSortedDuplicates(arrayListOf([1, 2, 3])));
    assertEquals(arrayListOf([1, 2, 3, 4]), removeSortedDuplicates(arrayListOf([1, 2, 3, 4])));
    assertEquals(arrayListOf([1, 2]), removeSortedDuplicates(arrayListOf([1, 1, 2, 2])));
    assertEquals(arrayListOf([1, 2]), removeSortedDuplicates(arrayListOf([1, 2, 2])));
    assertEquals(arrayListOf([1, 2]), removeSortedDuplicates(arrayListOf([1, 1, 2])));
    assertEquals(arrayListOf([1, 2, 3, 4, 5, 6, 7]), removeSortedDuplicates(arrayListOf([1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 7])));
  };
  protoOf(RemoveSortedDuplicatesTest).testWithoutSortedDuplicates_b4h1md_k$ = function () {
    assertEquals(listOf([1, 2]), withoutSortedDuplicates(listOf([1, 1, 2, 2])));
  };
  function test_fun_izoufj_41() {
    suite('RemoveSortedDuplicatesTest', false, test_fun$RemoveSortedDuplicatesTest_test_fun_3gbco3);
  }
  function test_fun$RemoveSortedDuplicatesTest_test_fun_3gbco3() {
    test('testRemoveSortedDuplicates', false, test_fun$RemoveSortedDuplicatesTest_test_fun$testRemoveSortedDuplicates_test_fun_8m9edn);
    test('testWithoutSortedDuplicates', false, test_fun$RemoveSortedDuplicatesTest_test_fun$testWithoutSortedDuplicates_test_fun_a9x7ex);
    return Unit_getInstance();
  }
  function test_fun$RemoveSortedDuplicatesTest_test_fun$testRemoveSortedDuplicates_test_fun_8m9edn() {
    var tmp = new RemoveSortedDuplicatesTest();
    tmp.testRemoveSortedDuplicates_qlrckr_k$();
    return Unit_getInstance();
  }
  function test_fun$RemoveSortedDuplicatesTest_test_fun$testWithoutSortedDuplicates_test_fun_a9x7ex() {
    var tmp = new RemoveSortedDuplicatesTest();
    tmp.testWithoutSortedDuplicates_b4h1md_k$();
    return Unit_getInstance();
  }
  function RingBufferTest() {
  }
  protoOf(RingBufferTest).get_info_8assxe_k$ = function (_this__u8e3s4) {
    return 'totalSize=' + _this__u8e3s4.get_totalSize_116z44_k$() + ', WRITE[pos=' + _this__u8e3s4.get_internalWritePos_1ado63_k$() + ', available=' + _this__u8e3s4.get_availableWrite_qb4gx_k$() + ', availableBeforeWrap=' + _this__u8e3s4.get_availableWriteBeforeWrap_702fmg_k$() + '], READ[pos=' + _this__u8e3s4.get_internalReadPos_yxlvnc_k$() + ', available=' + _this__u8e3s4.get_availableRead_tre4t2_k$() + ', availableBeforeWrap=' + _this__u8e3s4.get_availableReadBeforeWrap_3bxtsx_k$() + ']';
  };
  protoOf(RingBufferTest).test_w5z8la_k$ = function () {
    // Inline function 'kotlin.collections.arrayListOf' call
    var log = ArrayList_init_$Create$_0();
    var ring = new RingBuffer(4);
    // Inline function 'kotlin.collections.plusAssign' call
    var element = this.get_info_8assxe_k$(ring);
    log.add_utx5q5_k$(element);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$1 = new Int8Array([1, 2, 3]);
    ring.write$default_1yyjre_k$(tmp$ret$1);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$2 = new Int8Array([4, 5, 6]);
    ring.write$default_1yyjre_k$(tmp$ret$2);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_0 = this.get_info_8assxe_k$(ring);
    log.add_utx5q5_k$(element_0);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_1 = '' + ring.readByte_ectjk2_k$();
    log.add_utx5q5_k$(element_1);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_2 = '' + ring.readByte_ectjk2_k$();
    log.add_utx5q5_k$(element_2);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_3 = this.get_info_8assxe_k$(ring);
    log.add_utx5q5_k$(element_3);
    // Inline function 'kotlin.byteArrayOf' call
    var tmp$ret$3 = new Int8Array([7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]);
    ring.write$default_1yyjre_k$(tmp$ret$3);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_4 = this.get_info_8assxe_k$(ring);
    log.add_utx5q5_k$(element_4);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_5 = toString(toList_4(ring.readBytes_do0jwd_k$(100)));
    log.add_utx5q5_k$(element_5);
    // Inline function 'kotlin.collections.plusAssign' call
    var element_6 = this.get_info_8assxe_k$(ring);
    log.add_utx5q5_k$(element_6);
    assertEquals('totalSize=16, WRITE[pos=0, available=16, availableBeforeWrap=16], READ[pos=0, available=0, availableBeforeWrap=0]\ntotalSize=16, WRITE[pos=6, available=10, availableBeforeWrap=10], READ[pos=0, available=6, availableBeforeWrap=6]\n1\n2\ntotalSize=16, WRITE[pos=6, available=12, availableBeforeWrap=10], READ[pos=2, available=4, availableBeforeWrap=4]\ntotalSize=16, WRITE[pos=2, available=0, availableBeforeWrap=0], READ[pos=2, available=16, availableBeforeWrap=14]\n[3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]\ntotalSize=16, WRITE[pos=2, available=16, availableBeforeWrap=14], READ[pos=2, available=0, availableBeforeWrap=0]', joinToString(log, '\n'));
  };
  function test_fun_izoufj_42() {
    suite('RingBufferTest', false, test_fun$RingBufferTest_test_fun_s3t610);
  }
  function test_fun$RingBufferTest_test_fun_s3t610() {
    test('test', false, test_fun$RingBufferTest_test_fun$test_test_fun_tk0qzt);
    return Unit_getInstance();
  }
  function test_fun$RingBufferTest_test_fun$test_test_fun_tk0qzt() {
    var tmp = new RingBufferTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function SlowIdentityHashMapTest$test$Test(a) {
    this.a_1 = a;
  }
  protoOf(SlowIdentityHashMapTest$test$Test).get_a_1mhr5k_k$ = function () {
    return this.a_1;
  };
  protoOf(SlowIdentityHashMapTest$test$Test).component1_7eebsc_k$ = function () {
    return this.a_1;
  };
  protoOf(SlowIdentityHashMapTest$test$Test).copy_ns6qmb_k$ = function (a) {
    return new SlowIdentityHashMapTest$test$Test(a);
  };
  protoOf(SlowIdentityHashMapTest$test$Test).copy$default_iiwild_k$ = function (a, $super) {
    a = a === VOID ? this.a_1 : a;
    return $super === VOID ? this.copy_ns6qmb_k$(a) : $super.copy_ns6qmb_k$.call(this, a);
  };
  protoOf(SlowIdentityHashMapTest$test$Test).toString = function () {
    return 'Test(a=' + this.a_1 + ')';
  };
  protoOf(SlowIdentityHashMapTest$test$Test).hashCode = function () {
    return this.a_1;
  };
  protoOf(SlowIdentityHashMapTest$test$Test).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof SlowIdentityHashMapTest$test$Test))
      return false;
    var tmp0_other_with_cast = other instanceof SlowIdentityHashMapTest$test$Test ? other : THROW_CCE();
    if (!(this.a_1 === tmp0_other_with_cast.a_1))
      return false;
    return true;
  };
  function SlowIdentityHashMapTest() {
  }
  protoOf(SlowIdentityHashMapTest).test_w5z8la_k$ = function () {
    var i1 = new SlowIdentityHashMapTest$test$Test(1);
    var i2 = new SlowIdentityHashMapTest$test$Test(1);
    var map = hashMapOf([to(i1, 1), to(i2, 2)]);
    var imap = slowIdentityHashMapOf([to(i1, 1), to(i2, 2)]);
    assertEquals(2, map.get_wei43m_k$(i1));
    assertEquals(2, map.get_wei43m_k$(i2));
    assertEquals(1, imap.get_wei43m_k$(i1));
    assertEquals(2, imap.get_wei43m_k$(i2));
  };
  function test_fun_izoufj_43() {
    suite('SlowIdentityHashMapTest', false, test_fun$SlowIdentityHashMapTest_test_fun_d2h3x9);
  }
  function test_fun$SlowIdentityHashMapTest_test_fun_d2h3x9() {
    test('test', false, test_fun$SlowIdentityHashMapTest_test_fun$test_test_fun_gzgeri);
    return Unit_getInstance();
  }
  function test_fun$SlowIdentityHashMapTest_test_fun$test_test_fun_gzgeri() {
    var tmp = new SlowIdentityHashMapTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function SortedMapTest() {
  }
  protoOf(SortedMapTest).test_w5z8la_k$ = function () {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_8();
    var map = new SortedMap(ComparatorComparable());
    map.set_b9w55f_k$(10, 10);
    map.set_b9w55f_k$(5, 5);
    map.set_b9w55f_k$(20, 20);
    map.set_b9w55f_k$(15, 15);
    assertEquals(5, map.get_wei43m_k$(5));
    assertEquals(10, map.get_wei43m_k$(10));
    assertEquals(15, map.get_wei43m_k$(15));
    assertEquals(20, map.get_wei43m_k$(20));
    assertEquals(null, map.get_wei43m_k$(1));
    assertEquals(fastArrayListOf([5, 10, 15, 20]), map.keysToList_3da4zx_k$());
    assertEquals(null, map.nearestLowExcludingExact_a51ah2_k$(4));
    assertEquals(null, map.nearestLowExcludingExact_a51ah2_k$(5));
    assertEquals(5, map.nearestLowExcludingExact_a51ah2_k$(6));
    assertEquals(5, map.nearestLowExcludingExact_a51ah2_k$(9));
    assertEquals(10, map.nearestLowExcludingExact_a51ah2_k$(11));
    assertEquals(10, map.nearestLowExcludingExact_a51ah2_k$(14));
    assertEquals(10, map.nearestLowExcludingExact_a51ah2_k$(15));
    assertEquals(15, map.nearestLowExcludingExact_a51ah2_k$(16));
    assertEquals(15, map.nearestLowExcludingExact_a51ah2_k$(19));
    assertEquals(15, map.nearestLowExcludingExact_a51ah2_k$(20));
    assertEquals(20, map.nearestLowExcludingExact_a51ah2_k$(21));
    assertEquals(20, map.nearestLowExcludingExact_a51ah2_k$(30));
    assertEquals(5, map.nearestHighExcludingExact_q0wc6o_k$(4));
    assertEquals(10, map.nearestHighExcludingExact_q0wc6o_k$(5));
    assertEquals(10, map.nearestHighExcludingExact_q0wc6o_k$(6));
    assertEquals(10, map.nearestHighExcludingExact_q0wc6o_k$(9));
    assertEquals(15, map.nearestHighExcludingExact_q0wc6o_k$(10));
    assertEquals(15, map.nearestHighExcludingExact_q0wc6o_k$(11));
    assertEquals(15, map.nearestHighExcludingExact_q0wc6o_k$(14));
    assertEquals(20, map.nearestHighExcludingExact_q0wc6o_k$(15));
    assertEquals(20, map.nearestHighExcludingExact_q0wc6o_k$(16));
    assertEquals(20, map.nearestHighExcludingExact_q0wc6o_k$(19));
    assertEquals(null, map.nearestHighExcludingExact_q0wc6o_k$(20));
    assertEquals(null, map.nearestHighExcludingExact_q0wc6o_k$(21));
    assertEquals(null, map.nearestHighExcludingExact_q0wc6o_k$(30));
    assertEquals(null, map.nearestLow_8aspz6_k$(4));
    assertEquals(5, map.nearestLow_8aspz6_k$(5));
    assertEquals(5, map.nearestLow_8aspz6_k$(6));
    assertEquals(5, map.nearestLow_8aspz6_k$(9));
    assertEquals(10, map.nearestLow_8aspz6_k$(11));
    assertEquals(10, map.nearestLow_8aspz6_k$(14));
    assertEquals(15, map.nearestLow_8aspz6_k$(15));
    assertEquals(15, map.nearestLow_8aspz6_k$(16));
    assertEquals(15, map.nearestLow_8aspz6_k$(19));
    assertEquals(20, map.nearestLow_8aspz6_k$(20));
    assertEquals(20, map.nearestLow_8aspz6_k$(21));
    assertEquals(20, map.nearestLow_8aspz6_k$(30));
    assertEquals(5, map.nearestHigh_x4li4o_k$(4));
    assertEquals(5, map.nearestHigh_x4li4o_k$(5));
    assertEquals(10, map.nearestHigh_x4li4o_k$(6));
    assertEquals(10, map.nearestHigh_x4li4o_k$(9));
    assertEquals(10, map.nearestHigh_x4li4o_k$(10));
    assertEquals(15, map.nearestHigh_x4li4o_k$(11));
    assertEquals(15, map.nearestHigh_x4li4o_k$(14));
    assertEquals(15, map.nearestHigh_x4li4o_k$(15));
    assertEquals(20, map.nearestHigh_x4li4o_k$(16));
    assertEquals(20, map.nearestHigh_x4li4o_k$(19));
    assertEquals(20, map.nearestHigh_x4li4o_k$(20));
    assertEquals(null, map.nearestHigh_x4li4o_k$(21));
    assertEquals(null, map.nearestHigh_x4li4o_k$(30));
    map.remove_gppy8k_k$(15);
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    assertEquals(null, map.nearestLow_8aspz6_k$(4));
    assertEquals(5, map.nearestLow_8aspz6_k$(5));
    assertEquals(5, map.nearestLow_8aspz6_k$(6));
    assertEquals(5, map.nearestLow_8aspz6_k$(9));
    assertEquals(10, map.nearestLow_8aspz6_k$(11));
    assertEquals(10, map.nearestLow_8aspz6_k$(14));
    assertEquals(20, map.nearestLow_8aspz6_k$(20));
    assertEquals(20, map.nearestLow_8aspz6_k$(21));
    assertEquals(20, map.nearestLow_8aspz6_k$(30));
    assertEquals(5, map.nearestHigh_x4li4o_k$(4));
    assertEquals(5, map.nearestHigh_x4li4o_k$(5));
    assertEquals(10, map.nearestHigh_x4li4o_k$(6));
    assertEquals(10, map.nearestHigh_x4li4o_k$(9));
    assertEquals(10, map.nearestHigh_x4li4o_k$(10));
    assertEquals(20, map.nearestHigh_x4li4o_k$(16));
    assertEquals(20, map.nearestHigh_x4li4o_k$(19));
    assertEquals(20, map.nearestHigh_x4li4o_k$(20));
    assertEquals(null, map.nearestHigh_x4li4o_k$(21));
    assertEquals(null, map.nearestHigh_x4li4o_k$(30));
  };
  protoOf(SortedMapTest).testSortedMapOf_rxlbu4_k$ = function () {
    var sortedValues = toList_0(sortedMapOf([to(2, 'two'), to(3, 'three'), to(1, 'one')]).get_values_ksazhn_k$());
    assertEquals(listOf(['one', 'two', 'three']), sortedValues);
  };
  function test_fun_izoufj_44() {
    suite('SortedMapTest', false, test_fun$SortedMapTest_test_fun_fbsgfh);
  }
  function test_fun$SortedMapTest_test_fun_fbsgfh() {
    test('test', false, test_fun$SortedMapTest_test_fun$test_test_fun_pvstri);
    test('testSortedMapOf', false, test_fun$SortedMapTest_test_fun$testSortedMapOf_test_fun_vebb1a);
    return Unit_getInstance();
  }
  function test_fun$SortedMapTest_test_fun$test_test_fun_pvstri() {
    var tmp = new SortedMapTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$SortedMapTest_test_fun$testSortedMapOf_test_fun_vebb1a() {
    var tmp = new SortedMapTest();
    tmp.testSortedMapOf_rxlbu4_k$();
    return Unit_getInstance();
  }
  function SparseChunkedStackedIntArray2Test() {
  }
  protoOf(SparseChunkedStackedIntArray2Test).test_w5z8la_k$ = function () {
    var sparse = new SparseChunkedStackedIntArray2();
    var chunk1 = new StackedIntArray2(16, 16, VOID, -100, -100);
    var chunk2 = new StackedIntArray2(16, 16, VOID, -50, 50);
    sparse.putChunk_ovl62r_k$(chunk1);
    sparse.putChunk_ovl62r_k$(chunk2);
    // Inline function 'kotlin.test.assertTrue' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.SparseChunkedStackedIntArray2Test.test.<anonymous>' call
    var tmp$ret$0;
    $l$block_2: {
      // Inline function 'kotlin.collections.all' call
      var this_0 = until(0, 16);
      var tmp;
      if (isInterface(this_0, Collection)) {
        tmp = this_0.isEmpty_y1axqb_k$();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = true;
        break $l$block_2;
      }
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.datastructure.SparseChunkedStackedIntArray2Test.test.<anonymous>.<anonymous>' call
        var tmp$ret$1;
        $l$block_1: {
          // Inline function 'kotlin.collections.all' call
          var this_1 = until(0, 16);
          var tmp_0;
          if (isInterface(this_1, Collection)) {
            tmp_0 = this_1.isEmpty_y1axqb_k$();
          } else {
            tmp_0 = false;
          }
          if (tmp_0) {
            tmp$ret$1 = true;
            break $l$block_1;
          }
          var tmp0_iterator_0 = this_1.iterator_jk1svi_k$();
          while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
            var element_0 = tmp0_iterator_0.next_20eer_k$();
            // Inline function 'korlibs.datastructure.SparseChunkedStackedIntArray2Test.test.<anonymous>.<anonymous>.<anonymous>' call
            if (!(sparse.getChunkAt_7vb48m_k$(-100 + element_0 | 0, -100 + element | 0) === chunk1)) {
              tmp$ret$1 = false;
              break $l$block_1;
            }
          }
          tmp$ret$1 = true;
        }
        if (!tmp$ret$1) {
          tmp$ret$0 = false;
          break $l$block_2;
        }
      }
      tmp$ret$0 = true;
    }
    var tmp$ret$4 = tmp$ret$0;
    assertTrue(tmp$ret$4, null);
    assertEquals(null, sparse.getChunkAt_7vb48m_k$(-101, -100));
    assertEquals(null, sparse.getChunkAt_7vb48m_k$(-84, -100));
    assertEquals(null, sparse.getChunkAt_7vb48m_k$(10, -200));
    assertEquals(chunk1, sparse.getChunkAt_7vb48m_k$(-90, -90));
    assertEquals(chunk2, sparse.getChunkAt_7vb48m_k$(-40, 55));
    assertEquals(-1, sparse.getLast_4ur4dw_k$(-200, -200));
    assertEquals(-1, sparse.getLast_4ur4dw_k$(-90, -90));
    sparse.push_96pvvy_k$(-200, -200, 10);
    sparse.push_96pvvy_k$(-90, -90, 10);
    sparse.push_96pvvy_k$(-200, -200, 20);
    sparse.push_96pvvy_k$(-90, -90, 20);
    assertEquals(-1, sparse.getLast_4ur4dw_k$(-200, -200));
    assertEquals(20, sparse.getLast_4ur4dw_k$(-90, -90));
    assertEquals(-1, sparse.getFirst_z3c13a_k$(-200, -200));
    assertEquals(10, sparse.getFirst_z3c13a_k$(-90, -90));
    sparse.push_96pvvy_k$(-40, 55, 10);
    assertEquals(10, sparse.getLast_4ur4dw_k$(-40, 55));
    assertEquals(10, sparse.getFirst_z3c13a_k$(-40, 55));
    sparse.push_96pvvy_k$(-40, 55, 20);
    assertEquals(20, sparse.getLast_4ur4dw_k$(-40, 55));
    assertEquals(10, sparse.getFirst_z3c13a_k$(-40, 55));
    var sparse2 = sparse.clone_1keycd_k$();
    sparse2.push_96pvvy_k$(-40, 55, 30);
    assertEquals(20, sparse.getLast_4ur4dw_k$(-40, 55));
    assertEquals(10, sparse.getFirst_z3c13a_k$(-40, 55));
    assertEquals(2, sparse.findAllChunks_avlqmq_k$().get_size_woubt6_k$());
    assertEquals(2, sparse2.findAllChunks_avlqmq_k$().get_size_woubt6_k$());
    assertEquals(30, sparse2.getLast_4ur4dw_k$(-40, 55));
    assertEquals(10, sparse2.getFirst_z3c13a_k$(-40, 55));
    assertEquals('-100, -100, -34, 66', '' + sparse2.get_startX_jnf4u7_k$() + ', ' + sparse2.get_startY_jnf4u8_k$() + ', ' + get_endX(sparse2) + ', ' + get_endY(sparse2));
  };
  function test_fun_izoufj_45() {
    suite('SparseChunkedStackedIntArray2Test', false, test_fun$SparseChunkedStackedIntArray2Test_test_fun_3ujhqz);
  }
  function test_fun$SparseChunkedStackedIntArray2Test_test_fun_3ujhqz() {
    test('test', false, test_fun$SparseChunkedStackedIntArray2Test_test_fun$test_test_fun_bnghj4);
    return Unit_getInstance();
  }
  function test_fun$SparseChunkedStackedIntArray2Test_test_fun$test_test_fun_bnghj4() {
    var tmp = new SparseChunkedStackedIntArray2Test();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function StackTest() {
  }
  protoOf(StackTest).name_ugbo79_k$ = function () {
    var s = _IntStack___init__impl__t6qo2g();
    assertEquals(0, _IntStack___get_size__impl__uebm0g(s));
    IntStack__push_impl_ipqpa2(s, 3);
    assertEquals(1, _IntStack___get_size__impl__uebm0g(s));
    IntStack__push_impl_ipqpa2(s, 5);
    assertEquals(2, _IntStack___get_size__impl__uebm0g(s));
    IntStack__push_impl_ipqpa2(s, 7);
    assertEquals(3, _IntStack___get_size__impl__uebm0g(s));
    assertEquals(7, IntStack__pop_impl_e52gy3(s));
    assertEquals(2, _IntStack___get_size__impl__uebm0g(s));
    assertEquals(5, IntStack__pop_impl_e52gy3(s));
    assertEquals(1, _IntStack___get_size__impl__uebm0g(s));
    assertEquals(3, IntStack__pop_impl_e52gy3(s));
    assertEquals(0, _IntStack___get_size__impl__uebm0g(s));
    // Inline function 'kotlin.test.assertFailsWith' call
    // Inline function 'kotlin.test.assertFailsWith' call
    var exceptionClass = getKClass(IndexOutOfBoundsException);
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      IntStack__pop_impl_e52gy3(s);
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    checkResultIsFailure(exceptionClass, null, tmp$ret$2);
  };
  protoOf(StackTest).grow_79va6b_k$ = function () {
    var s = _IntStack___init__impl__t6qo2g();
    var inductionVariable = 0;
    if (inductionVariable <= 999)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        IntStack__push_impl_ipqpa2(s, n);
      }
       while (inductionVariable <= 999);
    var inductionVariable_0 = 999;
    if (0 <= inductionVariable_0)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + -1 | 0;
        assertEquals(n_0, IntStack__pop_impl_e52gy3(s));
      }
       while (0 <= inductionVariable_0);
  };
  protoOf(StackTest).collection_rqenym_k$ = function () {
    var s = Companion_getInstance_9().invoke_soxitb_k$(new Int32Array([1, 2, 3]));
    assertEquals(listOf([1, 2, 3]), toList_0(new IntStack(s)));
  };
  function test_fun_izoufj_46() {
    suite('StackTest', false, test_fun$StackTest_test_fun_slfjka);
  }
  function test_fun$StackTest_test_fun_slfjka() {
    test('name', false, test_fun$StackTest_test_fun$name_test_fun_6brqlw);
    test('grow', false, test_fun$StackTest_test_fun$grow_test_fun_h0xd6s);
    test('collection', false, test_fun$StackTest_test_fun$collection_test_fun_hn2gd5);
    return Unit_getInstance();
  }
  function test_fun$StackTest_test_fun$name_test_fun_6brqlw() {
    var tmp = new StackTest();
    tmp.name_ugbo79_k$();
    return Unit_getInstance();
  }
  function test_fun$StackTest_test_fun$grow_test_fun_h0xd6s() {
    var tmp = new StackTest();
    tmp.grow_79va6b_k$();
    return Unit_getInstance();
  }
  function test_fun$StackTest_test_fun$collection_test_fun_hn2gd5() {
    var tmp = new StackTest();
    tmp.collection_rqenym_k$();
    return Unit_getInstance();
  }
  function StackedIntArray2Test() {
  }
  protoOf(StackedIntArray2Test).test_w5z8la_k$ = function () {
    var value = new StackedIntArray2(2, 2);
    assertEquals(-1, value.getFirst_z3c13a_k$(0, 0));
    value.push_96pvvy_k$(0, 0, 1);
    value.push_96pvvy_k$(0, 0, 2);
    value.push_96pvvy_k$(0, 0, 3);
    assertEquals(3, value.getStackLevel_20g9m6_k$(0, 0));
    assertEquals(1, value.getFirst_z3c13a_k$(0, 0));
    assertEquals(2, value.get_ky6wce_k$(0, 0, 1));
    assertEquals(3, value.getLast_4ur4dw_k$(0, 0));
    value.removeLast_2ln5zq_k$(0, 0);
    assertEquals(1, value.getFirst_z3c13a_k$(0, 0));
    assertEquals(2, value.getLast_4ur4dw_k$(0, 0));
    assertEquals(2, value.getStackLevel_20g9m6_k$(0, 0));
    value.removeLast_2ln5zq_k$(0, 0);
    assertEquals(1, value.getFirst_z3c13a_k$(0, 0));
    assertEquals(1, value.getLast_4ur4dw_k$(0, 0));
    assertEquals(1, value.getStackLevel_20g9m6_k$(0, 0));
    value.removeLast_2ln5zq_k$(0, 0);
    assertEquals(-1, value.getFirst_z3c13a_k$(0, 0));
    assertEquals(-1, value.getLast_4ur4dw_k$(0, 0));
    assertEquals(0, value.getStackLevel_20g9m6_k$(0, 0));
    assertEquals(-1, value.getFirst_z3c13a_k$(1, 0));
    assertEquals(0, value.getStackLevel_20g9m6_k$(1, 0));
  };
  protoOf(StackedIntArray2Test).testInitialSetLevel_kbnmjo_k$ = function () {
    var tmp = Companion_getInstance_10();
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$0 = new Int32Array([10, 20, 30, 40]);
    var s = tmp.invoke$default_o6rois_k$([new IntArray2(2, 2, tmp$ret$0)]);
    assertEquals(1, s.getStackLevel_20g9m6_k$(0, 0));
    assertEquals(1, s.getStackLevel_20g9m6_k$(1, 1));
  };
  function test_fun_izoufj_47() {
    suite('StackedIntArray2Test', false, test_fun$StackedIntArray2Test_test_fun_xe2t1);
  }
  function test_fun$StackedIntArray2Test_test_fun_xe2t1() {
    test('test', false, test_fun$StackedIntArray2Test_test_fun$test_test_fun_rn89ui);
    test('testInitialSetLevel', false, test_fun$StackedIntArray2Test_test_fun$testInitialSetLevel_test_fun_cf861i);
    return Unit_getInstance();
  }
  function test_fun$StackedIntArray2Test_test_fun$test_test_fun_rn89ui() {
    var tmp = new StackedIntArray2Test();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$StackedIntArray2Test_test_fun$testInitialSetLevel_test_fun_cf861i() {
    var tmp = new StackedIntArray2Test();
    tmp.testInitialSetLevel_kbnmjo_k$();
    return Unit_getInstance();
  }
  function TimSortTest() {
  }
  protoOf(TimSortTest).test_w5z8la_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = listOf([1, 2, 3, 4, 5, 11, 15, 76, 192, 1024, 2048, 3000]);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.TimSortTest.test.<anonymous>' call
      var items = toList_0(until(0, item));
      var shuffledItems = shuffled(items, Random_0(0));
      var tmp$ret$0 = to(items, shuffledItems);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    var pairs = destination;
    var _iterator__ex2g4s = pairs.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      var items_0 = _destruct__k2r9zo.component1_7eebsc_k$();
      var shuffledItems_0 = _destruct__k2r9zo.component2_7eebsb_k$();
      assertEquals(items_0, timSorted(shuffledItems_0));
    }
  };
  function test_fun_izoufj_48() {
    suite('TimSortTest', false, test_fun$TimSortTest_test_fun_he66f0);
  }
  function test_fun$TimSortTest_test_fun_he66f0() {
    test('test', false, test_fun$TimSortTest_test_fun$test_test_fun_n169d5);
    return Unit_getInstance();
  }
  function test_fun$TimSortTest_test_fun$test_test_fun_n169d5() {
    var tmp = new TimSortTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function WeakMapTest$test$Demo() {
  }
  function WeakMapTest() {
  }
  protoOf(WeakMapTest).test_w5z8la_k$ = function () {
    var map = new WeakMap();
    var demo1 = new WeakMapTest$test$Demo();
    var demo2 = new WeakMapTest$test$Demo();
    map.set_c2d6a0_k$(demo1, 'hello');
    assertEquals('hello', map.get_h31hzz_k$(demo1));
    assertEquals(true, map.contains_7vtym0_k$(demo1));
    assertEquals(null, map.get_h31hzz_k$(demo2));
    assertEquals(false, map.contains_7vtym0_k$(demo2));
  };
  function test_fun_izoufj_49() {
    suite('WeakMapTest', false, test_fun$WeakMapTest_test_fun_pmygfm);
  }
  function test_fun$WeakMapTest_test_fun_pmygfm() {
    test('test', false, test_fun$WeakMapTest_test_fun$test_test_fun_svaynd);
    return Unit_getInstance();
  }
  function test_fun$WeakMapTest_test_fun$test_test_fun_svaynd() {
    var tmp = new WeakMapTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function set_prop(_this__u8e3s4, _set____db54di) {
    _init_properties_WeakPropertyTest_kt__qhltt();
    return prop$delegate.setValue_qx0o0_k$(_this__u8e3s4, prop$factory(), _set____db54di);
  }
  function get_prop(_this__u8e3s4) {
    _init_properties_WeakPropertyTest_kt__qhltt();
    return prop$delegate.getValue_m93qlt_k$(_this__u8e3s4, prop$factory_0());
  }
  var prop$delegate;
  function set_prop2(_this__u8e3s4, _set____db54di) {
    _init_properties_WeakPropertyTest_kt__qhltt();
    return prop2$delegate.setValue_bmiw5s_k$(_this__u8e3s4, prop2$factory(), _set____db54di);
  }
  function get_prop2(_this__u8e3s4) {
    _init_properties_WeakPropertyTest_kt__qhltt();
    return prop2$delegate.getValue_q7b8sf_k$(_this__u8e3s4, prop2$factory_0());
  }
  var prop2$delegate;
  function C() {
    this.value_1 = 1;
  }
  protoOf(C).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  function WeakPropertyTest() {
  }
  protoOf(WeakPropertyTest).name_ugbo79_k$ = function () {
    var c1 = new C();
    var c2 = new C();
    assertEquals(0, get_prop(c1));
    assertEquals(0, get_prop(c2));
    set_prop(c1, 1);
    set_prop(c2, 2);
    assertEquals(1, get_prop(c1));
    assertEquals(2, get_prop(c2));
    assertEquals('2', get_prop2(c2));
    set_prop2(c2, '3');
    assertEquals('3', get_prop2(c2));
  };
  function test_fun_izoufj_50() {
    suite('WeakPropertyTest', false, test_fun$WeakPropertyTest_test_fun_n88e9r);
  }
  function prop$delegate$lambda() {
    _init_properties_WeakPropertyTest_kt__qhltt();
    return 0;
  }
  function prop2$delegate$lambda($this$WeakPropertyThis) {
    _init_properties_WeakPropertyTest_kt__qhltt();
    return '' + imul($this$WeakPropertyThis.value_1, 2);
  }
  function test_fun$WeakPropertyTest_test_fun_n88e9r() {
    _init_properties_WeakPropertyTest_kt__qhltt();
    test('name', false, test_fun$WeakPropertyTest_test_fun$name_test_fun_dr8ta9);
    return Unit_getInstance();
  }
  function test_fun$WeakPropertyTest_test_fun$name_test_fun_dr8ta9() {
    _init_properties_WeakPropertyTest_kt__qhltt();
    var tmp = new WeakPropertyTest();
    tmp.name_ugbo79_k$();
    return Unit_getInstance();
  }
  function prop$factory() {
    return getPropertyCallableRef('prop', 1, KMutableProperty1, function (receiver) {
      return get_prop(receiver);
    }, function (receiver, value) {
      return set_prop(receiver, value);
    });
  }
  function prop$factory_0() {
    return getPropertyCallableRef('prop', 1, KMutableProperty1, function (receiver) {
      return get_prop(receiver);
    }, function (receiver, value) {
      return set_prop(receiver, value);
    });
  }
  function prop2$factory() {
    return getPropertyCallableRef('prop2', 1, KMutableProperty1, function (receiver) {
      return get_prop2(receiver);
    }, function (receiver, value) {
      return set_prop2(receiver, value);
    });
  }
  function prop2$factory_0() {
    return getPropertyCallableRef('prop2', 1, KMutableProperty1, function (receiver) {
      return get_prop2(receiver);
    }, function (receiver, value) {
      return set_prop2(receiver, value);
    });
  }
  var properties_initialized_WeakPropertyTest_kt_u8ef27;
  function _init_properties_WeakPropertyTest_kt__qhltt() {
    if (!properties_initialized_WeakPropertyTest_kt_u8ef27) {
      properties_initialized_WeakPropertyTest_kt_u8ef27 = true;
      prop$delegate = new WeakProperty(prop$delegate$lambda);
      prop2$delegate = new WeakPropertyThis(prop2$delegate$lambda);
    }
  }
  function HistoriogramTest() {
  }
  protoOf(HistoriogramTest).test_w5z8la_k$ = function () {
    var tmp = intIntMapOf([to(1, 3), to(5, 2), to(9, 1)]);
    var tmp_0 = Companion_getInstance_11();
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$0 = new Int32Array([1, 1, 5, 1, 9, 5]);
    assertEquals(tmp, tmp_0.values$default_wdzule_k$(tmp$ret$0));
  };
  protoOf(HistoriogramTest).test2_6xsqgg_k$ = function () {
    var a = new Historiogram();
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.algo.HistoriogramTest.test2.<anonymous>' call
    a.add_c9dakn_k$(1);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.algo.HistoriogramTest.test2.<anonymous>' call
    a.add_c9dakn_k$(2);
    var b = a.clone_1keycd_k$();
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.algo.HistoriogramTest.test2.<anonymous>' call
    a.add_c9dakn_k$(3);
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.algo.HistoriogramTest.test2.<anonymous>' call
    b.add_c9dakn_k$(1);
    assertEquals(intIntMapOf([to(1, 1), to(2, 1), to(3, 1)]), a.getMapCopy_uz6ncr_k$());
    assertEquals(intIntMapOf([to(1, 2), to(2, 1)]), b.getMapCopy_uz6ncr_k$());
  };
  function test_fun_izoufj_51() {
    suite('HistoriogramTest', false, test_fun$HistoriogramTest_test_fun_fwajxy);
  }
  function test_fun$HistoriogramTest_test_fun_fwajxy() {
    test('test', false, test_fun$HistoriogramTest_test_fun$test_test_fun_l8qbq3);
    test('test2', false, test_fun$HistoriogramTest_test_fun$test2_test_fun_woa1l7);
    return Unit_getInstance();
  }
  function test_fun$HistoriogramTest_test_fun$test_test_fun_l8qbq3() {
    var tmp = new HistoriogramTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$HistoriogramTest_test_fun$test2_test_fun_woa1l7() {
    var tmp = new HistoriogramTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function RLETest() {
  }
  protoOf(RLETest).test_w5z8la_k$ = function () {
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data = new Int32Array([]);
    var end = data.length;
    var out = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count = end - 0 | 0;
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
          tmp = data[0 + n | 0];
        }
        var value = tmp;
        if (n === 0 ? true : !(value === lastValue)) {
          if (!(currentStart === n)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk = nchunk + 1 | 0;
            var start = currentStart;
            var count_0 = n - currentStart | 0;
            var value_0 = lastValue;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out.emit_qlraj_k$(start, count_0, value_0);
            }
          }
          currentStart = n;
        }
        lastValue = value;
      }
       while (inductionVariable < last);
    var tmp_0 = out.toString();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data_0 = new Int32Array([1]);
    var end_0 = data_0.length;
    var out_0 = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count_1 = end_0 - 0 | 0;
    out_0.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue_0 = 0;
    var currentStart_0 = 0;
    var nchunk_0 = 0;
    var inductionVariable_0 = 0;
    var last_0 = count_1 + 1 | 0;
    if (inductionVariable_0 < last_0)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        var tmp_1;
        if (n_0 === count_1) {
          tmp_1 = lastValue_0 + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp_1 = data_0[0 + n_0 | 0];
        }
        var value_1 = tmp_1;
        if (n_0 === 0 ? true : !(value_1 === lastValue_0)) {
          if (!(currentStart_0 === n_0)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk_0 = nchunk_0 + 1 | 0;
            var start_0 = currentStart_0;
            var count_2 = n_0 - currentStart_0 | 0;
            var value_2 = lastValue_0;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out_0.emit_qlraj_k$(start_0, count_2, value_2);
            }
          }
          currentStart_0 = n_0;
        }
        lastValue_0 = value_1;
      }
       while (inductionVariable_0 < last_0);
    var tmp_2 = out_0.toString();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data_1 = new Int32Array([1, 1]);
    var end_1 = data_1.length;
    var out_1 = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count_3 = end_1 - 0 | 0;
    out_1.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue_1 = 0;
    var currentStart_1 = 0;
    var nchunk_1 = 0;
    var inductionVariable_1 = 0;
    var last_1 = count_3 + 1 | 0;
    if (inductionVariable_1 < last_1)
      do {
        var n_1 = inductionVariable_1;
        inductionVariable_1 = inductionVariable_1 + 1 | 0;
        var tmp_3;
        if (n_1 === count_3) {
          tmp_3 = lastValue_1 + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp_3 = data_1[0 + n_1 | 0];
        }
        var value_3 = tmp_3;
        if (n_1 === 0 ? true : !(value_3 === lastValue_1)) {
          if (!(currentStart_1 === n_1)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk_1 = nchunk_1 + 1 | 0;
            var start_1 = currentStart_1;
            var count_4 = n_1 - currentStart_1 | 0;
            var value_4 = lastValue_1;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out_1.emit_qlraj_k$(start_1, count_4, value_4);
            }
          }
          currentStart_1 = n_1;
        }
        lastValue_1 = value_3;
      }
       while (inductionVariable_1 < last_1);
    var tmp_4 = out_1.toString();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data_2 = new Int32Array([1, 1, 1]);
    var end_2 = data_2.length;
    var out_2 = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count_5 = end_2 - 0 | 0;
    out_2.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue_2 = 0;
    var currentStart_2 = 0;
    var nchunk_2 = 0;
    var inductionVariable_2 = 0;
    var last_2 = count_5 + 1 | 0;
    if (inductionVariable_2 < last_2)
      do {
        var n_2 = inductionVariable_2;
        inductionVariable_2 = inductionVariable_2 + 1 | 0;
        var tmp_5;
        if (n_2 === count_5) {
          tmp_5 = lastValue_2 + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp_5 = data_2[0 + n_2 | 0];
        }
        var value_5 = tmp_5;
        if (n_2 === 0 ? true : !(value_5 === lastValue_2)) {
          if (!(currentStart_2 === n_2)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk_2 = nchunk_2 + 1 | 0;
            var start_2 = currentStart_2;
            var count_6 = n_2 - currentStart_2 | 0;
            var value_6 = lastValue_2;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out_2.emit_qlraj_k$(start_2, count_6, value_6);
            }
          }
          currentStart_2 = n_2;
        }
        lastValue_2 = value_5;
      }
       while (inductionVariable_2 < last_2);
    var tmp_6 = out_2.toString();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data_3 = new Int32Array([1, 2]);
    var end_3 = data_3.length;
    var out_3 = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count_7 = end_3 - 0 | 0;
    out_3.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue_3 = 0;
    var currentStart_3 = 0;
    var nchunk_3 = 0;
    var inductionVariable_3 = 0;
    var last_3 = count_7 + 1 | 0;
    if (inductionVariable_3 < last_3)
      do {
        var n_3 = inductionVariable_3;
        inductionVariable_3 = inductionVariable_3 + 1 | 0;
        var tmp_7;
        if (n_3 === count_7) {
          tmp_7 = lastValue_3 + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp_7 = data_3[0 + n_3 | 0];
        }
        var value_7 = tmp_7;
        if (n_3 === 0 ? true : !(value_7 === lastValue_3)) {
          if (!(currentStart_3 === n_3)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk_3 = nchunk_3 + 1 | 0;
            var start_3 = currentStart_3;
            var count_8 = n_3 - currentStart_3 | 0;
            var value_8 = lastValue_3;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out_3.emit_qlraj_k$(start_3, count_8, value_8);
            }
          }
          currentStart_3 = n_3;
        }
        lastValue_3 = value_7;
      }
       while (inductionVariable_3 < last_3);
    var tmp_8 = out_3.toString();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data_4 = new Int32Array([1, 2, 1]);
    var end_4 = data_4.length;
    var out_4 = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count_9 = end_4 - 0 | 0;
    out_4.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue_4 = 0;
    var currentStart_4 = 0;
    var nchunk_4 = 0;
    var inductionVariable_4 = 0;
    var last_4 = count_9 + 1 | 0;
    if (inductionVariable_4 < last_4)
      do {
        var n_4 = inductionVariable_4;
        inductionVariable_4 = inductionVariable_4 + 1 | 0;
        var tmp_9;
        if (n_4 === count_9) {
          tmp_9 = lastValue_4 + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp_9 = data_4[0 + n_4 | 0];
        }
        var value_9 = tmp_9;
        if (n_4 === 0 ? true : !(value_9 === lastValue_4)) {
          if (!(currentStart_4 === n_4)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk_4 = nchunk_4 + 1 | 0;
            var start_4 = currentStart_4;
            var count_10 = n_4 - currentStart_4 | 0;
            var value_10 = lastValue_4;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out_4.emit_qlraj_k$(start_4, count_10, value_10);
            }
          }
          currentStart_4 = n_4;
        }
        lastValue_4 = value_9;
      }
       while (inductionVariable_4 < last_4);
    var tmp_10 = out_4.toString();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data_5 = new Int32Array([1, 2, 2, 1]);
    var end_5 = data_5.length;
    var out_5 = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count_11 = end_5 - 0 | 0;
    out_5.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue_5 = 0;
    var currentStart_5 = 0;
    var nchunk_5 = 0;
    var inductionVariable_5 = 0;
    var last_5 = count_11 + 1 | 0;
    if (inductionVariable_5 < last_5)
      do {
        var n_5 = inductionVariable_5;
        inductionVariable_5 = inductionVariable_5 + 1 | 0;
        var tmp_11;
        if (n_5 === count_11) {
          tmp_11 = lastValue_5 + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp_11 = data_5[0 + n_5 | 0];
        }
        var value_11 = tmp_11;
        if (n_5 === 0 ? true : !(value_11 === lastValue_5)) {
          if (!(currentStart_5 === n_5)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk_5 = nchunk_5 + 1 | 0;
            var start_5 = currentStart_5;
            var count_12 = n_5 - currentStart_5 | 0;
            var value_12 = lastValue_5;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out_5.emit_qlraj_k$(start_5, count_12, value_12);
            }
          }
          currentStart_5 = n_5;
        }
        lastValue_5 = value_11;
      }
       while (inductionVariable_5 < last_5);
    var tmp_12 = out_5.toString();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    Companion_getInstance_12();
    // Inline function 'kotlin.intArrayOf' call
    var data_6 = new Int32Array([1, 1, 3, 2, 2]);
    var end_6 = data_6.length;
    var out_6 = new RLE();
    // Inline function 'korlibs.datastructure.algo.Companion.compute' call
    var count_13 = end_6 - 0 | 0;
    out_6.clear_j9egeb_k$();
    // Inline function 'korlibs.datastructure.algo.Companion.emit' call
    var lastValue_6 = 0;
    var currentStart_6 = 0;
    var nchunk_6 = 0;
    var inductionVariable_6 = 0;
    var last_6 = count_13 + 1 | 0;
    if (inductionVariable_6 < last_6)
      do {
        var n_6 = inductionVariable_6;
        inductionVariable_6 = inductionVariable_6 + 1 | 0;
        var tmp_13;
        if (n_6 === count_13) {
          tmp_13 = lastValue_6 + 1 | 0;
        } else {
          // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
          tmp_13 = data_6[0 + n_6 | 0];
        }
        var value_13 = tmp_13;
        if (n_6 === 0 ? true : !(value_13 === lastValue_6)) {
          if (!(currentStart_6 === n_6)) {
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            nchunk_6 = nchunk_6 + 1 | 0;
            var start_6 = currentStart_6;
            var count_14 = n_6 - currentStart_6 | 0;
            var value_14 = lastValue_6;
            // Inline function 'korlibs.datastructure.algo.Companion.compute.<anonymous>' call
            if (true) {
              out_6.emit_qlraj_k$(start_6, count_14, value_14);
            }
          }
          currentStart_6 = n_6;
        }
        lastValue_6 = value_13;
      }
       while (inductionVariable_6 < last_6);
    assertEquals('RLE()\nRLE([(1),0,1])\nRLE([(1),0,2])\nRLE([(1),0,3])\nRLE([(1),0,1], [(2),1,1])\nRLE([(1),0,1], [(2),1,1], [(1),2,1])\nRLE([(1),0,1], [(2),1,2], [(1),3,1])\nRLE([(1),0,2], [(3),2,1], [(2),3,2])', trimIndent('\n                ' + tmp_0 + '\n                ' + tmp_2 + '\n                ' + tmp_4 + '\n                ' + tmp_6 + '\n                ' + tmp_8 + '\n                ' + tmp_10 + '\n                ' + tmp_12 + '\n                ' + out_6.toString() + '\n            '));
  };
  function test_fun_izoufj_52() {
    suite('RLETest', false, test_fun$RLETest_test_fun_fvzh53);
  }
  function test_fun$RLETest_test_fun_fvzh53() {
    test('test', false, test_fun$RLETest_test_fun$test_test_fun_gu1fmq);
    return Unit_getInstance();
  }
  function test_fun$RLETest_test_fun$test_test_fun_gu1fmq() {
    var tmp = new RLETest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function GetCyclicTest() {
  }
  protoOf(GetCyclicTest).list_xxuwxe_k$ = function () {
    var list = listOf([5, 10, 20, 30]);
    // Inline function 'kotlin.collections.map' call
    var this_0 = until(0, 4);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.datastructure.extensions.GetCyclicTest.list.<anonymous>' call
      destination.add_utx5q5_k$(list);
    }
    var cyclicList = flatten_0(destination);
    var inductionVariable = 0;
    var last = cyclicList.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        assertEquals(cyclicList.get_c1px32_k$(n), getCyclic(list, n));
      }
       while (inductionVariable <= last);
    // Inline function 'kotlin.collections.map' call
    var this_1 = until(0, 4);
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(this_1, 10));
    var tmp0_iterator_0 = this_1.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.datastructure.extensions.GetCyclicTest.list.<anonymous>' call
      var tmp$ret$3 = reversed(list);
      destination_0.add_utx5q5_k$(tmp$ret$3);
    }
    var cyclicListNeg = flatten_0(destination_0);
    var inductionVariable_0 = 0;
    var last_0 = cyclicListNeg.get_size_woubt6_k$() - 1 | 0;
    if (inductionVariable_0 <= last_0)
      do {
        var n_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        assertEquals(cyclicListNeg.get_c1px32_k$(n_0), getCyclic(list, (-n_0 | 0) - 1 | 0));
      }
       while (inductionVariable_0 <= last_0);
    assertEquals(5, getCyclic(list, 0));
    assertEquals(10, getCyclic(list, 1));
    assertEquals(20, getCyclic(list, 2));
    assertEquals(30, getCyclic(list, 3));
    assertEquals(30, getCyclic(list, -1));
    assertEquals(20, getCyclic(list, -2));
    assertEquals(10, getCyclic(list, -3));
    assertEquals(5, getCyclic(list, -4));
    assertEquals(30, getCyclic(list, -5));
  };
  protoOf(GetCyclicTest).array_koe5iv_k$ = function () {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$2 = ['a', 'b'];
    assertEquals('a', getCyclic_0(tmp$ret$2, 2));
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$5 = ['a', 'b'];
    assertEquals('b', getCyclic_0(tmp$ret$5, -1));
  };
  protoOf(GetCyclicTest).typedList_2aowgo_k$ = function () {
    assertEquals(10, getCyclic_1(intArrayListOf(new Int32Array([10, 20])), 2));
    assertEquals(20, getCyclic_1(intArrayListOf(new Int32Array([10, 20])), -1));
    assertEquals(10.0, getCyclic_2(floatArrayListOf(new Float32Array([10.0, 20.0])), 2));
    assertEquals(20.0, getCyclic_2(floatArrayListOf(new Float32Array([10.0, 20.0])), -1));
    assertEquals(10.0, getCyclic_3(doubleArrayListOf(new Float64Array([10.0, 20.0])), 2));
    assertEquals(20.0, getCyclic_3(doubleArrayListOf(new Float64Array([10.0, 20.0])), -1));
  };
  protoOf(GetCyclicTest).cyclicArray2_a79y9q_k$ = function () {
    // Inline function 'korlibs.datastructure.Companion.invoke' call
    Companion_getInstance_1();
    var tmp = 0;
    var tmp_0 = imul(2, 2);
    var tmp_1 = new Int32Array(tmp_0);
    while (tmp < tmp_0) {
      var tmp_2 = tmp;
      // Inline function 'korlibs.datastructure.extensions.GetCyclicTest.cyclicArray2.<anonymous>' call
      tmp_1[tmp_2] = tmp_2;
      tmp = tmp + 1 | 0;
    }
    var tmp_3 = tmp_1;
    var array = new IntArray2(2, 2, isIntArray(tmp_3) ? tmp_3 : THROW_CCE());
    assertEquals(0, getCyclic_4(array, 0, 0));
    assertEquals(1, getCyclic_4(array, 1, 0));
    assertEquals(0, getCyclic_4(array, 2, 0));
    assertEquals(1, getCyclic_4(array, 3, 0));
    assertEquals(2, getCyclic_4(array, 0, 1));
    assertEquals(3, getCyclic_4(array, 1, 1));
    assertEquals(2, getCyclic_4(array, 2, 1));
    assertEquals(3, getCyclic_4(array, 3, 1));
    assertEquals(0, getCyclic_4(array, 0, 2));
    assertEquals(1, getCyclic_4(array, 1, 2));
    assertEquals(0, getCyclic_4(array, 2, 2));
    assertEquals(1, getCyclic_4(array, 3, 2));
    assertEquals(3, getCyclic_4(array, -1, -1));
    assertEquals(0, getCyclic_4(array, -2, -2));
    assertEquals(3, getCyclic_4(array, -3, -3));
  };
  function test_fun_izoufj_53() {
    suite('GetCyclicTest', false, test_fun$GetCyclicTest_test_fun_8x80oz);
  }
  function test_fun$GetCyclicTest_test_fun_8x80oz() {
    test('list', false, test_fun$GetCyclicTest_test_fun$list_test_fun_odkvn2);
    test('array', false, test_fun$GetCyclicTest_test_fun$array_test_fun_vfudjt);
    test('typedList', false, test_fun$GetCyclicTest_test_fun$typedList_test_fun_1j531m);
    test('cyclicArray2', false, test_fun$GetCyclicTest_test_fun$cyclicArray2_test_fun_1xr07y);
    return Unit_getInstance();
  }
  function test_fun$GetCyclicTest_test_fun$list_test_fun_odkvn2() {
    var tmp = new GetCyclicTest();
    tmp.list_xxuwxe_k$();
    return Unit_getInstance();
  }
  function test_fun$GetCyclicTest_test_fun$array_test_fun_vfudjt() {
    var tmp = new GetCyclicTest();
    tmp.array_koe5iv_k$();
    return Unit_getInstance();
  }
  function test_fun$GetCyclicTest_test_fun$typedList_test_fun_1j531m() {
    var tmp = new GetCyclicTest();
    tmp.typedList_2aowgo_k$();
    return Unit_getInstance();
  }
  function test_fun$GetCyclicTest_test_fun$cyclicArray2_test_fun_1xr07y() {
    var tmp = new GetCyclicTest();
    tmp.cyclicArray2_a79y9q_k$();
    return Unit_getInstance();
  }
  function MapExtTest() {
  }
  protoOf(MapExtTest).linked_4o4ax5_k$ = function () {
    assertEquals('{a=1, b=2}', linkedHashMapOf([to('a', 1), to('b', 2)]).toString());
    assertEquals('{a=1, b=2}', toLinkedMap(listOf([to('a', 1), to('b', 2)])).toString());
  };
  protoOf(MapExtTest).flip_uxh0f7_k$ = function () {
    var map = mapOf([to('a', 'A'), to('b', 'B')]);
    assertEquals('{a=A, b=B}', toString(map));
    assertEquals('{A=a, B=b}', toString(flip(map)));
  };
  protoOf(MapExtTest).count_6f481t_k$ = function () {
    var list = listOf(['a', 'a', 'b', 'a', 'c', 'b']);
    assertEquals(mapOf([to('a', 3), to('b', 2), to('c', 1)]), countMap(list));
  };
  protoOf(MapExtTest).incr_6h3zi4_k$ = function () {
    // Inline function 'kotlin.collections.hashMapOf' call
    var map = HashMap_init_$Create$();
    incr(map, false, 100);
    incr(map, true, 1000);
    incr(map, true, 200);
    assertEquals('{false=100, true=1200}', map.toString());
  };
  function test_fun_izoufj_54() {
    suite('MapExtTest', false, test_fun$MapExtTest_test_fun_22o85j);
  }
  function test_fun$MapExtTest_test_fun_22o85j() {
    test('linked', false, test_fun$MapExtTest_test_fun$linked_test_fun_7mtjgb);
    test('flip', false, test_fun$MapExtTest_test_fun$flip_test_fun_vqiwfb);
    test('count', false, test_fun$MapExtTest_test_fun$count_test_fun_6kb50h);
    test('incr', false, test_fun$MapExtTest_test_fun$incr_test_fun_ju0bq8);
    return Unit_getInstance();
  }
  function test_fun$MapExtTest_test_fun$linked_test_fun_7mtjgb() {
    var tmp = new MapExtTest();
    tmp.linked_4o4ax5_k$();
    return Unit_getInstance();
  }
  function test_fun$MapExtTest_test_fun$flip_test_fun_vqiwfb() {
    var tmp = new MapExtTest();
    tmp.flip_uxh0f7_k$();
    return Unit_getInstance();
  }
  function test_fun$MapExtTest_test_fun$count_test_fun_6kb50h() {
    var tmp = new MapExtTest();
    tmp.count_6f481t_k$();
    return Unit_getInstance();
  }
  function test_fun$MapExtTest_test_fun$incr_test_fun_ju0bq8() {
    var tmp = new MapExtTest();
    tmp.incr_6h3zi4_k$();
    return Unit_getInstance();
  }
  function MapWhileTest() {
  }
  protoOf(MapWhileTest).test_w5z8la_k$ = function () {
    var tmp = listOf([0, 1, 2, 3]);
    // Inline function 'korlibs.datastructure.mapWhile' call
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_0 = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhile.<anonymous>' call
    $l$loop: while (true) {
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      if (!(this_0.get_size_woubt6_k$() < 4)) {
        break $l$loop;
      }
      // Inline function 'kotlin.collections.plusAssign' call
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      var element = this_0.get_size_woubt6_k$();
      this_0.add_utx5q5_k$(element);
    }
    assertEquals(tmp, this_0);
    var tmp_0 = listOf([0, 1, 2, 3]);
    // Inline function 'korlibs.datastructure.mapWhileArray' call
    // Inline function 'kotlin.collections.toTypedArray' call
    // Inline function 'korlibs.datastructure.mapWhile' call
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_1 = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhile.<anonymous>' call
    $l$loop_0: while (true) {
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      if (!(this_1.get_size_woubt6_k$() < 4)) {
        break $l$loop_0;
      }
      // Inline function 'kotlin.collections.plusAssign' call
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      var element_0 = this_1.get_size_woubt6_k$();
      this_1.add_utx5q5_k$(element_0);
    }
    var tmp$ret$11 = copyToArray(this_1);
    assertEquals(tmp_0, toList_1(tmp$ret$11));
    // Inline function 'kotlin.intArrayOf' call
    var tmp$ret$12 = new Int32Array([0, 1, 2, 3]);
    var tmp_1 = toList(tmp$ret$12);
    // Inline function 'korlibs.datastructure.mapWhileInt' call
    // Inline function 'kotlin.apply' call
    var this_2 = new IntArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileInt.<anonymous>' call
    $l$loop_1: while (true) {
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      if (!(this_2.get_size_woubt6_k$() < 4)) {
        break $l$loop_1;
      }
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      var tmp$ret$14 = this_2.get_size_woubt6_k$();
      this_2.plusAssign_8mmvnl_k$(tmp$ret$14);
    }
    var tmp$ret$16 = this_2.toIntArray_8jv8ed_k$();
    assertEquals(tmp_1, toList(tmp$ret$16));
    // Inline function 'kotlin.floatArrayOf' call
    var tmp$ret$17 = new Float32Array([0.0, 1.0, 2.0, 3.0]);
    var tmp_2 = toList_5(tmp$ret$17);
    // Inline function 'korlibs.datastructure.mapWhileFloat' call
    // Inline function 'kotlin.apply' call
    var this_3 = new FloatArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileFloat.<anonymous>' call
    $l$loop_2: while (true) {
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      if (!(this_3.get_size_woubt6_k$() < 4)) {
        break $l$loop_2;
      }
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      var tmp$ret$19 = this_3.get_size_woubt6_k$();
      this_3.plusAssign_xjcc2p_k$(tmp$ret$19);
    }
    var tmp$ret$21 = this_3.toFloatArray_ixdbug_k$();
    assertEquals(tmp_2, toList_5(tmp$ret$21));
    // Inline function 'kotlin.doubleArrayOf' call
    var tmp$ret$22 = new Float64Array([0.0, 1.0, 2.0, 3.0]);
    var tmp_3 = toList_6(tmp$ret$22);
    // Inline function 'korlibs.datastructure.mapWhileDouble' call
    // Inline function 'kotlin.apply' call
    var this_4 = new DoubleArrayList();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileDouble.<anonymous>' call
    $l$loop_3: while (true) {
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      if (!(this_4.get_size_woubt6_k$() < 4)) {
        break $l$loop_3;
      }
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test.<anonymous>' call
      var tmp$ret$24 = this_4.get_size_woubt6_k$();
      this_4.plusAssign_hd7up9_k$(tmp$ret$24);
    }
    var tmp$ret$26 = this_4.toDoubleArray_tm3pu5_k$();
    assertEquals(tmp_3, toList_6(tmp$ret$26));
  };
  protoOf(MapWhileTest).test2_6xsqgg_k$ = function () {
    var iterator = listOf([1, 2, 3]).iterator_jk1svi_k$();
    var tmp = listOf([1, 2, 3]);
    // Inline function 'korlibs.datastructure.mapWhile' call
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_0 = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhile.<anonymous>' call
    $l$loop: while (true) {
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test2.<anonymous>' call
      this_0.get_size_woubt6_k$();
      if (!iterator.hasNext_bitz1p_k$()) {
        break $l$loop;
      }
      // Inline function 'kotlin.collections.plusAssign' call
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.test2.<anonymous>' call
      this_0.get_size_woubt6_k$();
      var element = iterator.next_20eer_k$();
      this_0.add_utx5q5_k$(element);
    }
    assertEquals(tmp, this_0);
  };
  protoOf(MapWhileTest).testNotNull_2oq69k_k$ = function () {
    var tmp = listOf([0, 1, 2]);
    // Inline function 'korlibs.datastructure.mapWhileNotNull' call
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_0 = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileNotNull.<anonymous>' call
    $l$loop: while (true) {
      // Inline function 'kotlin.collections.plusAssign' call
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.testNotNull.<anonymous>' call
      var it = this_0.get_size_woubt6_k$();
      var tmp0_elvis_lhs = it >= 3 ? null : it;
      var tmp_0;
      if (tmp0_elvis_lhs == null) {
        break $l$loop;
      } else {
        tmp_0 = tmp0_elvis_lhs;
      }
      var element = tmp_0;
      this_0.add_utx5q5_k$(element);
    }
    assertEquals(tmp, this_0);
  };
  protoOf(MapWhileTest).testNotCheck_gph9ef_k$ = function () {
    var tmp = listOf([0, 1000, 2000]);
    // Inline function 'korlibs.datastructure.mapWhileCheck' call
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_0 = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.mapWhileCheck.<anonymous>' call
    $l$loop: while (true) {
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.testNotCheck.<anonymous>' call
      var it = this_0.get_size_woubt6_k$();
      var res = imul(it, 1000);
      // Inline function 'korlibs.datastructure.extensions.MapWhileTest.testNotCheck.<anonymous>' call
      if (!(res < 3000))
        break $l$loop;
      // Inline function 'kotlin.collections.plusAssign' call
      this_0.add_utx5q5_k$(res);
    }
    assertEquals(tmp, this_0);
  };
  function test_fun_izoufj_55() {
    suite('MapWhileTest', false, test_fun$MapWhileTest_test_fun_tgojcn);
  }
  function test_fun$MapWhileTest_test_fun_tgojcn() {
    test('test', false, test_fun$MapWhileTest_test_fun$test_test_fun_rzhzv6);
    test('test2', false, test_fun$MapWhileTest_test_fun$test2_test_fun_3x16ki);
    test('testNotNull', false, test_fun$MapWhileTest_test_fun$testNotNull_test_fun_dcx1ey);
    test('testNotCheck', false, test_fun$MapWhileTest_test_fun$testNotCheck_test_fun_26drod);
    return Unit_getInstance();
  }
  function test_fun$MapWhileTest_test_fun$test_test_fun_rzhzv6() {
    var tmp = new MapWhileTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$MapWhileTest_test_fun$test2_test_fun_3x16ki() {
    var tmp = new MapWhileTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function test_fun$MapWhileTest_test_fun$testNotNull_test_fun_dcx1ey() {
    var tmp = new MapWhileTest();
    tmp.testNotNull_2oq69k_k$();
    return Unit_getInstance();
  }
  function test_fun$MapWhileTest_test_fun$testNotCheck_test_fun_26drod() {
    var tmp = new MapWhileTest();
    tmp.testNotCheck_gph9ef_k$();
    return Unit_getInstance();
  }
  function FastIteratorsTest() {
  }
  protoOf(FastIteratorsTest).testFastIterateRemove_aingbk_k$ = function () {
    var tmp = listOf([1, 3, 5, 5, 3]);
    // Inline function 'korlibs.datastructure.iterators.fastIterateRemove' call
    var this_0 = arrayListOf([1, 2, 3, 4, 5, 5, 8, 8, 3]);
    var n = 0;
    var m = 0;
    while (n < this_0.get_size_woubt6_k$()) {
      if (m >= 0 ? !(m === n) : false) {
        this_0.set_82063s_k$(m, this_0.get_c1px32_k$(n));
      }
      // Inline function 'korlibs.datastructure.iterators.FastIteratorsTest.testFastIterateRemove.<anonymous>' call
      if ((this_0.get_c1px32_k$(n) % 2 | 0) === 0) {
        m = m - 1 | 0;
      }
      n = n + 1 | 0;
      m = m + 1 | 0;
    }
    while (this_0.get_size_woubt6_k$() > m) {
      this_0.removeAt_6niowx_k$(this_0.get_size_woubt6_k$() - 1 | 0);
    }
    assertEquals(tmp, this_0);
  };
  protoOf(FastIteratorsTest).testSupportsAddingItemsWhileIterating_ubo9k1_k$ = function () {
    var items = arrayListOf(['a', 'b']);
    // Inline function 'kotlin.collections.arrayListOf' call
    var out = ArrayList_init_$Create$_0();
    // Inline function 'korlibs.datastructure.iterators.fastForEachWithIndex' call
    var n = 0;
    while (n < items.get_size_woubt6_k$()) {
      // Inline function 'korlibs.datastructure.iterators.FastIteratorsTest.testSupportsAddingItemsWhileIterating.<anonymous>' call
      var index = n;
      var value = items.get_c1px32_k$(n);
      if (value === 'a') {
        items.add_utx5q5_k$('c');
      }
      if (value === 'b') {
        items.add_utx5q5_k$('d');
      }
      // Inline function 'kotlin.collections.plusAssign' call
      var element = '' + index + ':' + value;
      out.add_utx5q5_k$(element);
      n = n + 1 | 0;
    }
    assertEquals('0:a,1:b,2:c,3:d', joinToString(out, ','));
  };
  function test_fun_izoufj_56() {
    suite('FastIteratorsTest', false, test_fun$FastIteratorsTest_test_fun_ca04rt);
  }
  function test_fun$FastIteratorsTest_test_fun_ca04rt() {
    test('testFastIterateRemove', false, test_fun$FastIteratorsTest_test_fun$testFastIterateRemove_test_fun_ikoeh0);
    test('testSupportsAddingItemsWhileIterating', false, test_fun$FastIteratorsTest_test_fun$testSupportsAddingItemsWhileIterating_test_fun_4ysjab);
    return Unit_getInstance();
  }
  function test_fun$FastIteratorsTest_test_fun$testFastIterateRemove_test_fun_ikoeh0() {
    var tmp = new FastIteratorsTest();
    tmp.testFastIterateRemove_aingbk_k$();
    return Unit_getInstance();
  }
  function test_fun$FastIteratorsTest_test_fun$testSupportsAddingItemsWhileIterating_test_fun_4ysjab() {
    var tmp = new FastIteratorsTest();
    tmp.testSupportsAddingItemsWhileIterating_ubo9k1_k$();
    return Unit_getInstance();
  }
  function ParallelTest() {
  }
  protoOf(ParallelTest).test_w5z8la_k$ = function () {
    // Inline function 'kotlin.test.assertTrue' call
    var message = 'CONCURRENCY_COUNT:' + get_CONCURRENCY_COUNT() + ' >= 1';
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.datastructure.iterators.ParallelTest.test.<anonymous>' call
    var tmp$ret$0 = get_CONCURRENCY_COUNT() >= 1;
    assertTrue(tmp$ret$0, message);
    var inductionVariable = 0;
    if (inductionVariable < 128)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.datastructure.mapInt' call
        var this_0 = until(0, n);
        // Inline function 'kotlin.also' call
        var this_1 = new IntArrayList((coerceAtLeast(this_0.get_endInclusive_r07xpi_k$() - this_0.get_start_iypx6h_k$() | 0, 0) / this_0.get_step_woujh1_k$() | 0) + 1 | 0);
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.mapInt.<anonymous>' call
        var nestedFirst = this_0.get_start_iypx6h_k$();
        var nestedLast = this_0.get_endInclusive_r07xpi_k$();
        var stepArg = this_0.get_step_woujh1_k$();
        if (stepArg <= 0)
          THROW_IAE('Step must be positive, was: ' + stepArg + '.');
        var inductionVariable_0 = nestedFirst;
        var last = getProgressionLastElement(nestedFirst, nestedLast, stepArg);
        if (inductionVariable_0 <= last)
          do {
            var v = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + stepArg | 0;
            // Inline function 'korlibs.datastructure.iterators.ParallelTest.test.<anonymous>' call
            var tmp$ret$1 = v;
            this_1.add_c9dakn_k$(tmp$ret$1);
          }
           while (!(v === last));
        var list = this_1;
        // Inline function 'korlibs.datastructure.mapInt' call
        // Inline function 'kotlin.also' call
        var this_2 = new IntArrayList(list.get_size_woubt6_k$());
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.mapInt.<anonymous>' call
        // Inline function 'korlibs.datastructure.iterators.fastForEach' call
        var n_0 = 0;
        while (n_0 < list.get_size_woubt6_k$()) {
          // Inline function 'korlibs.datastructure.mapInt.<anonymous>.<anonymous>' call
          var _unary__edvuaz = n_0;
          n_0 = _unary__edvuaz + 1 | 0;
          // Inline function 'korlibs.datastructure.iterators.ParallelTest.test.<anonymous>' call
          var it = list.getAt_k8n1td_k$(_unary__edvuaz);
          var tmp$ret$4 = imul(it, 2);
          this_2.add_c9dakn_k$(tmp$ret$4);
        }
        var tmp = toIntArrayList(this_2);
        // Inline function 'korlibs.datastructure.iterators.parallelMapInt' call
        // Inline function 'kotlin.also' call
        var this_3 = new Int32Array(list.get_size_woubt6_k$());
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.datastructure.iterators.parallelMapInt.<anonymous>' call
        // Inline function 'korlibs.datastructure.iterators.parallelForeach' call
        var count = list.get_size_woubt6_k$();
        var inductionVariable_1 = 0;
        if (inductionVariable_1 < count)
          do {
            var n_1 = inductionVariable_1;
            inductionVariable_1 = inductionVariable_1 + 1 | 0;
            // Inline function 'korlibs.datastructure.iterators.parallelMapInt.<anonymous>.<anonymous>' call
            // Inline function 'korlibs.datastructure.iterators.ParallelTest.test.<anonymous>' call
            var it_0 = list.get_c1px32_k$(n_1);
            this_3[n_1] = imul(it_0, 2);
          }
           while (inductionVariable_1 < count);
        assertEquals(tmp, toIntArrayList_0(this_3));
      }
       while (inductionVariable < 128);
  };
  function test_fun_izoufj_57() {
    suite('ParallelTest', false, test_fun$ParallelTest_test_fun_xf0yn9);
  }
  function test_fun$ParallelTest_test_fun_xf0yn9() {
    test('test', false, test_fun$ParallelTest_test_fun$test_test_fun_lvgbwg);
    return Unit_getInstance();
  }
  function test_fun$ParallelTest_test_fun$test_test_fun_lvgbwg() {
    var tmp = new ParallelTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function LockTest$testWaitNotify$lambda($log, $lock) {
    return function (it) {
      var tmp = Companion_getInstance_13();
      // Inline function 'korlibs.time.milliseconds' call
      // Inline function 'kotlin.time.Companion.milliseconds' call
      Companion_getInstance_14();
      var tmp$ret$1 = toDuration(10, DurationUnit_MILLISECONDS_getInstance());
      tmp.sleep_sn34jm_k$(tmp$ret$1);
      $log._v.add_utx5q5_k$('b');
      $l$block: {
        // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
        var this_0 = $lock;
        this_0.set_locked_3md9le_k$(true);
        try {
          $lock.notify$default_c7e85j_k$();
          break $l$block;
        }finally {
          this_0.set_locked_3md9le_k$(false);
        }
      }
      return Unit_getInstance();
    };
  }
  function LockTest() {
  }
  protoOf(LockTest).test_w5z8la_k$ = function () {
    var lock = new Lock();
    var a = 0;
    $l$block_0: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      lock.set_locked_3md9le_k$(true);
      try {
        // Inline function 'korlibs.datastructure.lock.LockTest.test.<anonymous>' call
        $l$block: {
          // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
          lock.set_locked_3md9le_k$(true);
          try {
            // Inline function 'korlibs.datastructure.lock.LockTest.test.<anonymous>.<anonymous>' call
            a = a + 1 | 0;
            break $l$block;
          }finally {
            lock.set_locked_3md9le_k$(false);
          }
        }
        break $l$block_0;
      }finally {
        lock.set_locked_3md9le_k$(false);
      }
    }
    assertEquals(1, a);
  };
  protoOf(LockTest).test2_6xsqgg_k$ = function () {
    var lock = new NonRecursiveLock();
    var a = 0;
    // Inline function 'korlibs.concurrent.lock.NonRecursiveLock.invoke' call
    // Inline function 'korlibs.datastructure.lock.LockTest.test2.<anonymous>' call
    a = a + 1 | 0;
    assertEquals(1, a);
  };
  protoOf(LockTest).testWaitNotify_i50sxs_k$ = function () {
    if (Companion_getInstance_15().get_isJsOrWasm_a0g2u5_k$())
      return Unit_getInstance();
    var lock = new Lock();
    // Inline function 'kotlin.collections.arrayListOf' call
    var log = {_v: ArrayList_init_$Create$_0()};
    nativeThread(true, VOID, VOID, VOID, LockTest$testWaitNotify$lambda(log, lock));
    $l$block_0: {
      // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
      lock.set_locked_3md9le_k$(true);
      try {
        $l$block: {
          // Inline function 'korlibs.concurrent.lock.Lock.invoke' call
          lock.set_locked_3md9le_k$(true);
          try {
            // Inline function 'kotlin.collections.plusAssign' call
            log._v.add_utx5q5_k$('a');
            // Inline function 'korlibs.time.seconds' call
            // Inline function 'korlibs.time.seconds' call
            var tmp$ret$2 = toDuration(1, DurationUnit_SECONDS_getInstance());
            lock.wait_yik34g_k$(tmp$ret$2);
            log._v.add_utx5q5_k$('c');
            break $l$block;
          }finally {
            lock.set_locked_3md9le_k$(false);
          }
        }
        break $l$block_0;
      }finally {
        lock.set_locked_3md9le_k$(false);
      }
    }
    assertEquals('abc', joinToString(log._v, ''));
  };
  protoOf(LockTest).testNotifyError_2kr5oj_k$ = function () {
    var lock = new Lock();
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      lock.notify$default_c7e85j_k$();
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$2 = tmp;
    checkResultIsFailure_0(null, tmp$ret$2);
  };
  protoOf(LockTest).testWaitError_y3x67_k$ = function () {
    var lock = new Lock();
    // Inline function 'kotlin.test.assertFails' call
    // Inline function 'kotlin.runCatching' call
    var tmp;
    try {
      // Inline function 'kotlin.Companion.success' call
      Companion_getInstance_4();
      // Inline function 'korlibs.time.seconds' call
      // Inline function 'korlibs.time.seconds' call
      var tmp$ret$1 = toDuration(1, DurationUnit_SECONDS_getInstance());
      lock.wait_yik34g_k$(tmp$ret$1);
      tmp = _Result___init__impl__xyqfz8(Unit_getInstance());
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        // Inline function 'kotlin.Companion.failure' call
        Companion_getInstance_4();
        tmp_0 = _Result___init__impl__xyqfz8(createFailure(e));
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var tmp$ret$4 = tmp;
    checkResultIsFailure_0(null, tmp$ret$4);
  };
  function test_fun_izoufj_58() {
    suite('LockTest', false, test_fun$LockTest_test_fun_u3wnun);
  }
  function test_fun$LockTest_test_fun_u3wnun() {
    test('test', false, test_fun$LockTest_test_fun$test_test_fun_81ch2c);
    test('test2', false, test_fun$LockTest_test_fun$test2_test_fun_fuj46s);
    test('testWaitNotify', false, test_fun$LockTest_test_fun$testWaitNotify_test_fun_s81owu);
    test('testNotifyError', false, test_fun$LockTest_test_fun$testNotifyError_test_fun_mzzf53);
    test('testWaitError', false, test_fun$LockTest_test_fun$testWaitError_test_fun_uh5fl9);
    return Unit_getInstance();
  }
  function test_fun$LockTest_test_fun$test_test_fun_81ch2c() {
    var tmp = new LockTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function test_fun$LockTest_test_fun$test2_test_fun_fuj46s() {
    var tmp = new LockTest();
    tmp.test2_6xsqgg_k$();
    return Unit_getInstance();
  }
  function test_fun$LockTest_test_fun$testWaitNotify_test_fun_s81owu() {
    var tmp = new LockTest();
    tmp.testWaitNotify_i50sxs_k$();
    return Unit_getInstance();
  }
  function test_fun$LockTest_test_fun$testNotifyError_test_fun_mzzf53() {
    var tmp = new LockTest();
    tmp.testNotifyError_2kr5oj_k$();
    return Unit_getInstance();
  }
  function test_fun$LockTest_test_fun$testWaitError_test_fun_uh5fl9() {
    var tmp = new LockTest();
    tmp.testWaitError_y3x67_k$();
    return Unit_getInstance();
  }
  function ThreadTest$test$lambda(it) {
    return Unit_getInstance();
  }
  function ThreadTest() {
  }
  protoOf(ThreadTest).test_w5z8la_k$ = function () {
    var KEY = 'hello';
    var VALUE = 'world';
    var extra = get_extra(new NativeThread(ThreadTest$test$lambda));
    setExtra(extra, KEY, VALUE);
    assertEquals(VALUE, getExtra(extra, KEY));
  };
  function test_fun_izoufj_59() {
    suite('ThreadTest', false, test_fun$ThreadTest_test_fun_ntxsvy);
  }
  function test_fun$ThreadTest_test_fun_ntxsvy() {
    test('test', false, test_fun$ThreadTest_test_fun$test_test_fun_v18wyr);
    return Unit_getInstance();
  }
  function test_fun$ThreadTest_test_fun$test_test_fun_v18wyr() {
    var tmp = new ThreadTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  function KdsJsTest() {
  }
  protoOf(KdsJsTest).test_w5z8la_k$ = function () {
    assertTrue(true);
  };
  function test_fun_izoufj_60() {
    suite('KdsJsTest', false, test_fun$KdsJsTest_test_fun_ppe4gf);
  }
  function test_fun$KdsJsTest_test_fun_ppe4gf() {
    test('test', false, test_fun$KdsJsTest_test_fun$test_test_fun_sg24mi);
    return Unit_getInstance();
  }
  function test_fun$KdsJsTest_test_fun$test_test_fun_sg24mi() {
    var tmp = new KdsJsTest();
    tmp.test_w5z8la_k$();
    return Unit_getInstance();
  }
  //region block: tests
  (function () {
    suite('korlibs.datastructure', false, function () {
      test_fun_izoufj();
      test_fun_izoufj_0();
      test_fun_izoufj_1();
      test_fun_izoufj_2();
      test_fun_izoufj_3();
      test_fun_izoufj_4();
      test_fun_izoufj_5();
      test_fun_izoufj_6();
      test_fun_izoufj_7();
      test_fun_izoufj_8();
      test_fun_izoufj_9();
      test_fun_izoufj_10();
      test_fun_izoufj_11();
      test_fun_izoufj_12();
      test_fun_izoufj_13();
      test_fun_izoufj_14();
      test_fun_izoufj_15();
      test_fun_izoufj_16();
      test_fun_izoufj_17();
      test_fun_izoufj_18();
      test_fun_izoufj_19();
      test_fun_izoufj_20();
      test_fun_izoufj_21();
      test_fun_izoufj_22();
      test_fun_izoufj_23();
      test_fun_izoufj_24();
      test_fun_izoufj_25();
      test_fun_izoufj_26();
      test_fun_izoufj_27();
      test_fun_izoufj_28();
      test_fun_izoufj_29();
      test_fun_izoufj_30();
      test_fun_izoufj_31();
      test_fun_izoufj_32();
      test_fun_izoufj_33();
      test_fun_izoufj_34();
      test_fun_izoufj_35();
      test_fun_izoufj_36();
      test_fun_izoufj_37();
      test_fun_izoufj_38();
      test_fun_izoufj_39();
      test_fun_izoufj_40();
      test_fun_izoufj_41();
      test_fun_izoufj_42();
      test_fun_izoufj_43();
      test_fun_izoufj_44();
      test_fun_izoufj_45();
      test_fun_izoufj_46();
      test_fun_izoufj_47();
      test_fun_izoufj_48();
      test_fun_izoufj_49();
      test_fun_izoufj_50();
      test_fun_izoufj_60();
    });
    suite('korlibs.datastructure.algo', false, function () {
      test_fun_izoufj_51();
      test_fun_izoufj_52();
    });
    suite('korlibs.datastructure.extensions', false, function () {
      test_fun_izoufj_53();
      test_fun_izoufj_54();
      test_fun_izoufj_55();
    });
    suite('korlibs.datastructure.iterators', false, function () {
      test_fun_izoufj_56();
      test_fun_izoufj_57();
    });
    suite('korlibs.datastructure.lock', false, function () {
      test_fun_izoufj_58();
    });
    suite('korlibs.datastructure.thread', false, function () {
      test_fun_izoufj_59();
    });
  }());
  //endregion
  return _;
}));
