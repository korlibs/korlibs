(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'korge-root-korlibs-time'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'korge-root-korlibs-time'.");
    }
    root['korge-root-korlibs-time'] = factory(typeof this['korge-root-korlibs-time'] === 'undefined' ? {} : this['korge-root-korlibs-time'], this['kotlin-kotlin-stdlib']);
  }
}(this, function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var imul = Math.imul;
  var log10 = Math.log10;
  var sign = Math.sign;
  var Long = kotlin_kotlin.$_$.jc;
  var protoOf = kotlin_kotlin.$_$.x9;
  var initMetadataForCompanion = kotlin_kotlin.$_$.e9;
  var abs = kotlin_kotlin.$_$.ca;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.z1;
  var padStart = kotlin_kotlin.$_$.hb;
  var compareTo = kotlin_kotlin.$_$.r8;
  var THROW_CCE = kotlin_kotlin.$_$.oc;
  var Comparable = kotlin_kotlin.$_$.cc;
  var initMetadataForClass = kotlin_kotlin.$_$.d9;
  var VOID = kotlin_kotlin.$_$.d;
  var RuntimeException = kotlin_kotlin.$_$.nc;
  var RuntimeException_init_$Init$ = kotlin_kotlin.$_$.l1;
  var captureStack = kotlin_kotlin.$_$.m8;
  var listOf = kotlin_kotlin.$_$.g5;
  var ensureNotNull = kotlin_kotlin.$_$.tc;
  var initMetadataForInterface = kotlin_kotlin.$_$.g9;
  var toString = kotlin_kotlin.$_$.ba;
  var DurationUnit_MINUTES_getInstance = kotlin_kotlin.$_$.h;
  var toDuration = kotlin_kotlin.$_$.xb;
  var THROW_IAE = kotlin_kotlin.$_$.pc;
  var enumEntries = kotlin_kotlin.$_$.d8;
  var Unit_getInstance = kotlin_kotlin.$_$.r2;
  var Enum = kotlin_kotlin.$_$.ec;
  var Companion_getInstance = kotlin_kotlin.$_$.o2;
  var DurationUnit_MILLISECONDS_getInstance = kotlin_kotlin.$_$.g;
  var coerceIn = kotlin_kotlin.$_$.ja;
  var numberToLong = kotlin_kotlin.$_$.v9;
  var IllegalStateException_init_$Create$ = kotlin_kotlin.$_$.f1;
  var DurationUnit_DAYS_getInstance = kotlin_kotlin.$_$.e;
  var Duration__unaryMinus_impl_x2k1y0 = kotlin_kotlin.$_$.x1;
  var getNumberHashCode = kotlin_kotlin.$_$.z8;
  var equals = kotlin_kotlin.$_$.u8;
  var Duration__plus_impl_yu9v8f = kotlin_kotlin.$_$.v1;
  var DurationUnit_HOURS_getInstance = kotlin_kotlin.$_$.f;
  var DurationUnit_SECONDS_getInstance = kotlin_kotlin.$_$.j;
  var objectCreate = kotlin_kotlin.$_$.w9;
  var lazy = kotlin_kotlin.$_$.uc;
  var Duration__times_impl_sfuzvp = kotlin_kotlin.$_$.w1;
  var Duration__compareTo_impl_pchp0f = kotlin_kotlin.$_$.r1;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.m;
  var joinToString = kotlin_kotlin.$_$.z4;
  var Duration = kotlin_kotlin.$_$.wb;
  var Duration__hashCode_impl_u4exz6 = kotlin_kotlin.$_$.s1;
  var KProperty1 = kotlin_kotlin.$_$.qa;
  var getPropertyCallableRef = kotlin_kotlin.$_$.a9;
  var hashCode = kotlin_kotlin.$_$.c9;
  var Comparator = kotlin_kotlin.$_$.dc;
  var collectionSizeOrDefault = kotlin_kotlin.$_$.q3;
  var ArrayList_init_$Create$_0 = kotlin_kotlin.$_$.l;
  var fillArrayVal = kotlin_kotlin.$_$.v8;
  var indexOf = kotlin_kotlin.$_$.v4;
  var numberToInt = kotlin_kotlin.$_$.u9;
  var getBooleanHashCode = kotlin_kotlin.$_$.y8;
  var LinkedHashMap_init_$Create$ = kotlin_kotlin.$_$.r;
  var toList = kotlin_kotlin.$_$.y6;
  var Companion_getInstance_0 = kotlin_kotlin.$_$.n2;
  var startsWith = kotlin_kotlin.$_$.nb;
  var Regex_init_$Create$ = kotlin_kotlin.$_$.w;
  var checkIndexOverflow = kotlin_kotlin.$_$.p3;
  var StringBuilder_init_$Create$ = kotlin_kotlin.$_$.y;
  var startsWith_0 = kotlin_kotlin.$_$.mb;
  var drop = kotlin_kotlin.$_$.l4;
  var zip = kotlin_kotlin.$_$.i7;
  var charSequenceLength = kotlin_kotlin.$_$.q8;
  var toInt = kotlin_kotlin.$_$.sb;
  var RuntimeException_init_$Create$ = kotlin_kotlin.$_$.m1;
  var toDouble = kotlin_kotlin.$_$.qb;
  var replace = kotlin_kotlin.$_$.kb;
  var removePrefix = kotlin_kotlin.$_$.ib;
  var take = kotlin_kotlin.$_$.ob;
  var drop_0 = kotlin_kotlin.$_$.za;
  var first = kotlin_kotlin.$_$.bb;
  var equals_0 = kotlin_kotlin.$_$.ab;
  var getStringHashCode = kotlin_kotlin.$_$.b9;
  var _Duration___get_inWholeNanoseconds__impl__r5x4mr = kotlin_kotlin.$_$.t1;
  var DurationUnit_NANOSECONDS_getInstance = kotlin_kotlin.$_$.i;
  var toDuration_0 = kotlin_kotlin.$_$.yb;
  var toList_0 = kotlin_kotlin.$_$.a7;
  var first_0 = kotlin_kotlin.$_$.p4;
  var to = kotlin_kotlin.$_$.ad;
  var toMap = kotlin_kotlin.$_$.c7;
  var plus = kotlin_kotlin.$_$.m5;
  var isCharSequence = kotlin_kotlin.$_$.l9;
  var trim = kotlin_kotlin.$_$.vb;
  var emptyList = kotlin_kotlin.$_$.m4;
  var charSequenceGet = kotlin_kotlin.$_$.p8;
  var coerceAtMost = kotlin_kotlin.$_$.ia;
  var toIntOrNull = kotlin_kotlin.$_$.rb;
  var replace_0 = kotlin_kotlin.$_$.lb;
  var toDoubleOrNull = kotlin_kotlin.$_$.pb;
  var Char__minus_impl_a2frrh = kotlin_kotlin.$_$.a2;
  var Char = kotlin_kotlin.$_$.bc;
  var Duration__minus_impl_q5cfm7 = kotlin_kotlin.$_$.u1;
  var initMetadataForObject = kotlin_kotlin.$_$.i9;
  //endregion
  //region block: pre-declaration
  initMetadataForCompanion(Companion);
  initMetadataForInterface(Serializable, 'Serializable');
  initMetadataForClass(Date_0, 'Date', VOID, VOID, [Comparable, Serializable]);
  initMetadataForClass(DateException, 'DateException', VOID, RuntimeException);
  initMetadataForCompanion(Companion_0);
  function tryParse$default(str, doThrow, doAdjust, $super) {
    doThrow = doThrow === VOID ? false : doThrow;
    doAdjust = doAdjust === VOID ? true : doAdjust;
    return $super === VOID ? this.tryParse_hjzgny_k$(str, doThrow, doAdjust) : $super.tryParse_hjzgny_k$.call(this, str, doThrow, doAdjust);
  }
  initMetadataForInterface(DateFormat, 'DateFormat');
  initMetadataForClass(DatePart, 'DatePart', VOID, Enum);
  initMetadataForCompanion(Companion_1);
  initMetadataForClass(DateTime, 'DateTime', VOID, VOID, [Comparable, Serializable]);
  initMetadataForCompanion(Companion_2);
  initMetadataForCompanion(Companion_3);
  initMetadataForClass(ComputedTime, 'ComputedTime');
  initMetadataForClass(DateTimeSpan, 'DateTimeSpan', DateTimeSpan_init_$Create$, VOID, [Comparable, Serializable]);
  initMetadataForCompanion(Companion_4);
  initMetadataForClass(DateTimeTz, 'DateTimeTz', VOID, VOID, [Comparable, Serializable]);
  initMetadataForCompanion(Companion_5);
  initMetadataForClass(DayOfWeek, 'DayOfWeek', VOID, Enum, [Serializable, Enum]);
  initMetadataForClass(DayOfWeekWithLocale, 'DayOfWeekWithLocale', VOID, VOID, [Comparable]);
  initMetadataForClass(KlockLocale, 'KlockLocale');
  initMetadataForClass(English, 'English', English, KlockLocale);
  initMetadataForCompanion(Companion_6, English);
  initMetadataForCompanion(Companion_7);
  initMetadataForClass(sam$kotlin_Comparator$0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator]);
  initMetadataForCompanion(Companion_8);
  initMetadataForClass(KlockLocaleContext, 'KlockLocaleContext', KlockLocaleContext);
  initMetadataForClass(KlockLocaleGender, 'KlockLocaleGender', VOID, Enum);
  initMetadataForCompanion(Companion_9);
  initMetadataForClass(Month, 'Month', VOID, Enum, [Serializable, Enum]);
  initMetadataForCompanion(Companion_10);
  initMetadataForClass(MonthSpan, 'MonthSpan', VOID, VOID, [Comparable, Serializable]);
  initMetadataForCompanion(Companion_11);
  initMetadataForCompanion(Companion_12);
  initMetadataForClass(Options, 'Options', Options, VOID, [Serializable]);
  initMetadataForClass(PatternDateFormat, 'PatternDateFormat', VOID, VOID, [DateFormat, Serializable]);
  initMetadataForCompanion(Companion_13);
  initMetadataForCompanion(Companion_14);
  initMetadataForClass(Options_0, 'Options', Options_0, VOID, [Serializable]);
  function tryParse$default_0(str, doThrow, doAdjust, $super) {
    doAdjust = doAdjust === VOID ? true : doAdjust;
    var tmp;
    if ($super === VOID) {
      tmp = this.tryParse_je1hyv_k$(str, doThrow, doAdjust);
    } else {
      var tmp_0 = $super.tryParse_je1hyv_k$.call(this, str, doThrow, doAdjust);
      tmp = tmp_0 == null ? null : tmp_0.rawValue_1;
    }
    return tmp;
  }
  initMetadataForInterface(TimeFormat, 'TimeFormat');
  initMetadataForClass(PatternTimeFormat, 'PatternTimeFormat', VOID, VOID, [TimeFormat, Serializable]);
  initMetadataForCompanion(Companion_15);
  initMetadataForClass(Time, 'Time', VOID, VOID, [Comparable, Serializable]);
  initMetadataForCompanion(Companion_16);
  initMetadataForClass(Timezone, 'Timezone', VOID, Enum);
  initMetadataForCompanion(Companion_17);
  initMetadataForClass(TimezoneNames, 'TimezoneNames', VOID, VOID, [Serializable]);
  initMetadataForCompanion(Companion_18);
  initMetadataForClass(TimezoneOffset, 'TimezoneOffset', VOID, VOID, [Comparable, Serializable]);
  initMetadataForCompanion(Companion_19);
  initMetadataForClass(Year, 'Year', VOID, VOID, [Comparable, Serializable]);
  initMetadataForCompanion(Companion_20);
  initMetadataForClass(YearMonth, 'YearMonth', VOID, VOID, [Serializable]);
  initMetadataForClass(MicroStrReader, 'MicroStrReader');
  initMetadataForClass(Moduler, 'Moduler');
  initMetadataForObject(KlockInternal, 'KlockInternal');
  //endregion
  function _get_serialVersionUID__fhggm9($this) {
    return $this.serialVersionUID_1;
  }
  function _Date___init__impl__c4wj2r(encoded) {
    return encoded;
  }
  function _Date___get_encoded__impl__hcvuga($this) {
    return $this;
  }
  function Companion() {
    Companion_instance = this;
    this.serialVersionUID_1 = new Long(1, 0);
  }
  protoOf(Companion).invoke_b5nqkb_k$ = function (year, month, day) {
    return _Date___init__impl__c4wj2r(year << 16 | month << 8 | day << 0);
  };
  protoOf(Companion).invoke_yl075t_k$ = function (year, month, day) {
    return Companion_getInstance_1().invoke_b5nqkb_k$(year, month.get_index1_etpsq0_k$(), day);
  };
  protoOf(Companion).invoke_ifz3a8_k$ = function (year, month, day) {
    return Companion_getInstance_1().invoke_b5nqkb_k$(_Year___get_year__impl__hjar94(year), month.get_index1_etpsq0_k$(), day);
  };
  protoOf(Companion).invoke_u8j8mg_k$ = function (yearMonth, day) {
    return Companion_getInstance_1().invoke_b5nqkb_k$(_YearMonth___get_yearInt__impl__2gysi1(yearMonth), _YearMonth___get_month1__impl__5u1az6(yearMonth), day);
  };
  var Companion_instance;
  function Companion_getInstance_1() {
    if (Companion_instance == null)
      new Companion();
    return Companion_instance;
  }
  function _Date___get_year__impl__gvb68n($this) {
    return _Date___get_encoded__impl__hcvuga($this) >> 16;
  }
  function _Date___get_month1__impl__tkbnv($this) {
    return (_Date___get_encoded__impl__hcvuga($this) >>> 8 | 0) & 255;
  }
  function _Date___get_month__impl__def9fs($this) {
    return Companion_getInstance_11().get_c1px32_k$(_Date___get_month1__impl__tkbnv($this));
  }
  function _Date___get_day__impl__v4952c($this) {
    return (_Date___get_encoded__impl__hcvuga($this) >>> 0 | 0) & 255;
  }
  function _Date___get_yearYear__impl__2woj84($this) {
    return _Year___init__impl__2jvef0(_Date___get_year__impl__gvb68n($this));
  }
  function _Date___get_dateTimeDayStart__impl__u9j4lh($this) {
    return Companion_getInstance_3().invoke$default_d0vuz8_k$(_Date___get_year__impl__gvb68n($this), _Date___get_month__impl__def9fs($this), _Date___get_day__impl__v4952c($this));
  }
  function _Date___get_dayOfYear__impl__14oum0($this) {
    return _DateTime___get_dayOfYear__impl__1v0nln(_Date___get_dateTimeDayStart__impl__u9j4lh($this));
  }
  function _Date___get_dayOfWeek__impl__l3nrsh($this) {
    return _DateTime___get_dayOfWeek__impl__o3da04(_Date___get_dateTimeDayStart__impl__u9j4lh($this));
  }
  function _Date___get_dayOfWeekInt__impl__mqxwoy($this) {
    return _DateTime___get_dayOfWeekInt__impl__yrgob9(_Date___get_dateTimeDayStart__impl__u9j4lh($this));
  }
  function Date__format_impl_tp30aw($this, format) {
    return DateTime__format_impl_6el5l1_0(_Date___get_dateTimeDayStart__impl__u9j4lh($this), format);
  }
  function Date__format_impl_tp30aw_0($this, format) {
    return DateTime__format_impl_6el5l1(_Date___get_dateTimeDayStart__impl__u9j4lh($this), format);
  }
  function Date__toString_impl_i97yib($this) {
    return (_Date___get_year__impl__gvb68n($this) < 0 ? '-' : '') + abs(_Date___get_year__impl__gvb68n($this)).toString() + '-' + padStart(abs(_Date___get_month1__impl__tkbnv($this)).toString(), 2, _Char___init__impl__6a9atx(48)) + '-' + padStart(abs(_Date___get_day__impl__v4952c($this)).toString(), 2, _Char___init__impl__6a9atx(48));
  }
  function Date__compareTo_impl_inwfgb($this, other) {
    return compareTo(_Date___get_encoded__impl__hcvuga($this), _Date___get_encoded__impl__hcvuga(other));
  }
  function Date__compareTo_impl_inwfgb_0($this, other) {
    return Date__compareTo_impl_inwfgb($this.encoded_1, other instanceof Date_0 ? other.encoded_1 : THROW_CCE());
  }
  function Date__hashCode_impl_3hxyak($this) {
    return $this;
  }
  function Date__equals_impl_8k83uo($this, other) {
    if (!(other instanceof Date_0))
      return false;
    if (!($this === (other instanceof Date_0 ? other.encoded_1 : THROW_CCE())))
      return false;
    return true;
  }
  function Date_0(encoded) {
    Companion_getInstance_1();
    this.encoded_1 = encoded;
  }
  protoOf(Date_0).toString = function () {
    return Date__toString_impl_i97yib(this.encoded_1);
  };
  protoOf(Date_0).compareTo_ewttpx_k$ = function (other) {
    return Date__compareTo_impl_inwfgb(this.encoded_1, other);
  };
  protoOf(Date_0).compareTo_hpufkf_k$ = function (other) {
    return Date__compareTo_impl_inwfgb_0(this, other);
  };
  protoOf(Date_0).hashCode = function () {
    return Date__hashCode_impl_3hxyak(this.encoded_1);
  };
  protoOf(Date_0).equals = function (other) {
    return Date__equals_impl_8k83uo(this.encoded_1, other);
  };
  function DateException(msg) {
    RuntimeException_init_$Init$(msg, this);
    captureStack(this, DateException);
  }
  function Companion_0() {
    Companion_instance_0 = this;
    this.DEFAULT_FORMAT_1 = Companion_getInstance_2().invoke_lt562m_k$('EEE, dd MMM yyyy HH:mm:ss z');
    this.FORMAT1__1 = Companion_getInstance_2().invoke_lt562m_k$("yyyy-MM-dd'T'HH:mm:ssXXX");
    this.FORMAT2__1 = Companion_getInstance_2().invoke_lt562m_k$("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    this.FORMAT_DATE_1 = Companion_getInstance_2().invoke_lt562m_k$('yyyy-MM-dd');
    this.FORMATS_1 = listOf([this.DEFAULT_FORMAT_1, this.FORMAT1__1, this.FORMAT2__1, this.FORMAT_DATE_1]);
  }
  protoOf(Companion_0).get_DEFAULT_FORMAT_t79cy_k$ = function () {
    return this.DEFAULT_FORMAT_1;
  };
  protoOf(Companion_0).get_FORMAT1_1xou29_k$ = function () {
    return this.FORMAT1__1;
  };
  protoOf(Companion_0).get_FORMAT2_1xou2a_k$ = function () {
    return this.FORMAT2__1;
  };
  protoOf(Companion_0).get_FORMAT_DATE_32mi6l_k$ = function () {
    return this.FORMAT_DATE_1;
  };
  protoOf(Companion_0).get_FORMATS_1xou37_k$ = function () {
    return this.FORMATS_1;
  };
  protoOf(Companion_0).parse_pc1q8p_k$ = function (date) {
    var lastError = null;
    var _iterator__ex2g4s = this.FORMATS_1.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var format = _iterator__ex2g4s.next_20eer_k$();
      try {
        return parse(format, date);
      } catch ($p) {
        if ($p instanceof Error) {
          var e = $p;
          lastError = e;
        } else {
          throw $p;
        }
      }
    }
    throw ensureNotNull(lastError);
  };
  protoOf(Companion_0).invoke_lt562m_k$ = function (pattern) {
    return new PatternDateFormat(pattern);
  };
  var Companion_instance_0;
  function Companion_getInstance_2() {
    if (Companion_instance_0 == null)
      new Companion_0();
    return Companion_instance_0;
  }
  function DateFormat() {
  }
  function parse(_this__u8e3s4, str, doAdjust) {
    doAdjust = doAdjust === VOID ? true : doAdjust;
    var tmp0_elvis_lhs = _this__u8e3s4.tryParse_hjzgny_k$(str, true, doAdjust);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      throw new DateException("Not a valid format: '" + str + "' for '" + toString(_this__u8e3s4) + "'");
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
  function format(_this__u8e3s4, dd) {
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var tmp$ret$1 = toDuration(0, DurationUnit_MINUTES_getInstance());
    return _this__u8e3s4.format_tfqdjs_k$(DateTime__toOffsetUnadjusted_impl_iorn(dd, tmp$ret$1));
  }
  var DatePart_Year_instance;
  var DatePart_DayOfYear_instance;
  var DatePart_Month_instance;
  var DatePart_Day_instance;
  function values() {
    return [DatePart_Year_getInstance(), DatePart_DayOfYear_getInstance(), DatePart_Month_getInstance(), DatePart_Day_getInstance()];
  }
  function valueOf(value) {
    switch (value) {
      case 'Year':
        return DatePart_Year_getInstance();
      case 'DayOfYear':
        return DatePart_DayOfYear_getInstance();
      case 'Month':
        return DatePart_Month_getInstance();
      case 'Day':
        return DatePart_Day_getInstance();
      default:
        DatePart_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries() {
    if ($ENTRIES == null)
      $ENTRIES = enumEntries(values());
    return $ENTRIES;
  }
  var DatePart_entriesInitialized;
  function DatePart_initEntries() {
    if (DatePart_entriesInitialized)
      return Unit_getInstance();
    DatePart_entriesInitialized = true;
    DatePart_Year_instance = new DatePart('Year', 0);
    DatePart_DayOfYear_instance = new DatePart('DayOfYear', 1);
    DatePart_Month_instance = new DatePart('Month', 2);
    DatePart_Day_instance = new DatePart('Day', 3);
  }
  var $ENTRIES;
  function _get_serialVersionUID__fhggm9_0($this) {
    return $this.serialVersionUID_1;
  }
  function DatePart(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function timeToMillisUnchecked($this, hour, minute, second) {
    return hour * 3600000 + minute * 60000 + second * 1000;
  }
  function dateToMillis($this, year, month, day) {
    Companion_getInstance_11().checked_99ekhb_k$(month);
    if (!(1 <= day ? day <= Companion_getInstance_11().invoke_dlyhwg_k$(month).days_101u5b_k$(year) : false))
      throw new DateException('Day ' + day + ' not valid for year=' + year + ' and month=' + month);
    return $this.dateToMillisUnchecked_q3lt3e_k$(year, month, day);
  }
  function timeToMillis($this, hour, minute, second) {
    if (!(0 <= hour ? hour <= 23 : false))
      throw new DateException('Hour ' + hour + ' not in 0..23');
    if (!(0 <= minute ? minute <= 59 : false))
      throw new DateException('Minute ' + minute + ' not in 0..59');
    if (!(0 <= second ? second <= 59 : false))
      throw new DateException('Second ' + second + ' not in 0..59');
    return timeToMillisUnchecked($this, hour, minute, second);
  }
  function DatePart_Year_getInstance() {
    DatePart_initEntries();
    return DatePart_Year_instance;
  }
  function DatePart_DayOfYear_getInstance() {
    DatePart_initEntries();
    return DatePart_DayOfYear_instance;
  }
  function DatePart_Month_getInstance() {
    DatePart_initEntries();
    return DatePart_Month_instance;
  }
  function DatePart_Day_getInstance() {
    DatePart_initEntries();
    return DatePart_Day_instance;
  }
  function _DateTime___init__impl__ifgty2(unixMillis) {
    return unixMillis;
  }
  function _DateTime___get_unixMillis__impl__ofxkzp($this) {
    return $this;
  }
  function Companion_1() {
    Companion_instance_1 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.EPOCH_1 = _DateTime___init__impl__ifgty2(0.0);
    this.EPOCH_INTERNAL_MILLIS_1 = 6.21355968E13;
  }
  protoOf(Companion_1).get_EPOCH_w1kzk8_k$ = function () {
    return this.EPOCH_1;
  };
  protoOf(Companion_1).invoke_422pxp_k$ = function (year, month, day, hour, minute, second, milliseconds) {
    return _DateTime___init__impl__ifgty2(dateToMillis(Companion_getInstance_3(), _Year___get_year__impl__hjar94(year), month.get_index1_etpsq0_k$(), day) + timeToMillis(Companion_getInstance_3(), hour, minute, second) + milliseconds);
  };
  protoOf(Companion_1).invoke$default_hxrmzr_k$ = function (year, month, day, hour, minute, second, milliseconds, $super) {
    hour = hour === VOID ? 0 : hour;
    minute = minute === VOID ? 0 : minute;
    second = second === VOID ? 0 : second;
    milliseconds = milliseconds === VOID ? 0 : milliseconds;
    return $super === VOID ? this.invoke_422pxp_k$(year, month, day, hour, minute, second, milliseconds) : $super.invoke_422pxp_k$.call(this, new Year(year), month, day, hour, minute, second, milliseconds).unixMillis_1;
  };
  protoOf(Companion_1).invoke_1t74us_k$ = function (date, time) {
    return Companion_getInstance_3().invoke_k1rwbc_k$(_Date___get_year__impl__gvb68n(date), _Date___get_month1__impl__tkbnv(date), _Date___get_day__impl__v4952c(date), _Time___get_hour__impl__xa2741(time), _Time___get_minute__impl__egf45t(time), _Time___get_second__impl__nhehzj(time), _Time___get_millisecond__impl__qz3nfc(time));
  };
  protoOf(Companion_1).invoke$default_c7tj3f_k$ = function (date, time, $super) {
    var tmp;
    if (time === VOID) {
      // Inline function 'korlibs.time.milliseconds' call
      // Inline function 'kotlin.time.Companion.milliseconds' call
      Companion_getInstance();
      var tmp$ret$1 = toDuration(0, DurationUnit_MILLISECONDS_getInstance());
      tmp = _Time___init__impl__x7mm30(tmp$ret$1);
    } else {
      tmp = time;
    }
    time = tmp;
    return $super === VOID ? this.invoke_1t74us_k$(date, time) : $super.invoke_1t74us_k$.call(this, new Date_0(date), new Time(time)).unixMillis_1;
  };
  protoOf(Companion_1).invoke_bvz63y_k$ = function (year, month, day, hour, minute, second, milliseconds) {
    return _DateTime___init__impl__ifgty2(dateToMillis(Companion_getInstance_3(), year, month.get_index1_etpsq0_k$(), day) + timeToMillis(Companion_getInstance_3(), hour, minute, second) + milliseconds);
  };
  protoOf(Companion_1).invoke$default_d0vuz8_k$ = function (year, month, day, hour, minute, second, milliseconds, $super) {
    hour = hour === VOID ? 0 : hour;
    minute = minute === VOID ? 0 : minute;
    second = second === VOID ? 0 : second;
    milliseconds = milliseconds === VOID ? 0 : milliseconds;
    return $super === VOID ? this.invoke_bvz63y_k$(year, month, day, hour, minute, second, milliseconds) : $super.invoke_bvz63y_k$.call(this, year, month, day, hour, minute, second, milliseconds).unixMillis_1;
  };
  protoOf(Companion_1).invoke_k1rwbc_k$ = function (year, month, day, hour, minute, second, milliseconds) {
    return _DateTime___init__impl__ifgty2(dateToMillis(Companion_getInstance_3(), year, month, day) + timeToMillis(Companion_getInstance_3(), hour, minute, second) + milliseconds);
  };
  protoOf(Companion_1).invoke$default_dbh6d0_k$ = function (year, month, day, hour, minute, second, milliseconds, $super) {
    hour = hour === VOID ? 0 : hour;
    minute = minute === VOID ? 0 : minute;
    second = second === VOID ? 0 : second;
    milliseconds = milliseconds === VOID ? 0 : milliseconds;
    return $super === VOID ? this.invoke_k1rwbc_k$(year, month, day, hour, minute, second, milliseconds) : $super.invoke_k1rwbc_k$.call(this, year, month, day, hour, minute, second, milliseconds).unixMillis_1;
  };
  protoOf(Companion_1).createClamped_ejfxjy_k$ = function (year, month, day, hour, minute, second, milliseconds) {
    var clampedMonth = coerceIn(month, 1, 12);
    return this.createUnchecked_bq02a6_k$(year, clampedMonth, coerceIn(day, 1, Companion_getInstance_11().invoke_dlyhwg_k$(month).days_101u5b_k$(year)), coerceIn(hour, 0, 23), coerceIn(minute, 0, 59), coerceIn(second, 0, 59), milliseconds);
  };
  protoOf(Companion_1).createClamped$default_3jiipa_k$ = function (year, month, day, hour, minute, second, milliseconds, $super) {
    hour = hour === VOID ? 0 : hour;
    minute = minute === VOID ? 0 : minute;
    second = second === VOID ? 0 : second;
    milliseconds = milliseconds === VOID ? 0 : milliseconds;
    return $super === VOID ? this.createClamped_ejfxjy_k$(year, month, day, hour, minute, second, milliseconds) : $super.createClamped_ejfxjy_k$.call(this, year, month, day, hour, minute, second, milliseconds).unixMillis_1;
  };
  protoOf(Companion_1).createAdjusted_m79lxy_k$ = function (year, month, day, hour, minute, second, milliseconds) {
    var dy = year;
    var dm = month;
    var dd = day;
    var th = hour;
    var tm = minute;
    var ts = second;
    tm = tm + cycleSteps(ts, 0, 59) | 0;
    ts = cycle(ts, 0, 59);
    th = th + cycleSteps(tm, 0, 59) | 0;
    tm = cycle(tm, 0, 59);
    dd = dd + cycleSteps(th, 0, 23) | 0;
    th = cycle(th, 0, 23);
    $l$loop: while (true) {
      var dup = Companion_getInstance_11().invoke_dlyhwg_k$(dm).days_101u5b_k$(dy);
      dm = dm + cycleSteps(dd, 1, dup) | 0;
      dd = cycle(dd, 1, dup);
      dy = dy + cycleSteps(dm, 1, 12) | 0;
      dm = cycle(dm, 1, 12);
      if (cycle(dd, 1, Companion_getInstance_11().invoke_dlyhwg_k$(dm).days_101u5b_k$(dy)) === dd) {
        break $l$loop;
      }
    }
    return this.createUnchecked_bq02a6_k$(dy, dm, dd, th, tm, ts, milliseconds);
  };
  protoOf(Companion_1).createAdjusted$default_2y50de_k$ = function (year, month, day, hour, minute, second, milliseconds, $super) {
    hour = hour === VOID ? 0 : hour;
    minute = minute === VOID ? 0 : minute;
    second = second === VOID ? 0 : second;
    milliseconds = milliseconds === VOID ? 0 : milliseconds;
    return $super === VOID ? this.createAdjusted_m79lxy_k$(year, month, day, hour, minute, second, milliseconds) : $super.createAdjusted_m79lxy_k$.call(this, year, month, day, hour, minute, second, milliseconds).unixMillis_1;
  };
  protoOf(Companion_1).createUnchecked_bq02a6_k$ = function (year, month, day, hour, minute, second, milliseconds) {
    return _DateTime___init__impl__ifgty2(Companion_getInstance_3().dateToMillisUnchecked_q3lt3e_k$(year, month, day) + timeToMillisUnchecked(Companion_getInstance_3(), hour, minute, second) + milliseconds);
  };
  protoOf(Companion_1).createUnchecked$default_e3jfs6_k$ = function (year, month, day, hour, minute, second, milliseconds, $super) {
    hour = hour === VOID ? 0 : hour;
    minute = minute === VOID ? 0 : minute;
    second = second === VOID ? 0 : second;
    milliseconds = milliseconds === VOID ? 0 : milliseconds;
    return $super === VOID ? this.createUnchecked_bq02a6_k$(year, month, day, hour, minute, second, milliseconds) : $super.createUnchecked_bq02a6_k$.call(this, year, month, day, hour, minute, second, milliseconds).unixMillis_1;
  };
  protoOf(Companion_1).invoke_ajmm9u_k$ = function (unix) {
    return this.fromUnixMillis_7tu0am_k$(unix);
  };
  protoOf(Companion_1).invoke_wda5d6_k$ = function (unix) {
    return this.fromUnixMillis_rsfk2y_k$(unix);
  };
  protoOf(Companion_1).fromUnixMillis_rsfk2y_k$ = function (unix) {
    return _DateTime___init__impl__ifgty2(unix);
  };
  protoOf(Companion_1).fromUnixMillis_7tu0am_k$ = function (unix) {
    return this.fromUnixMillis_rsfk2y_k$(unix.toDouble_ygsx0s_k$());
  };
  protoOf(Companion_1).fromString_g5hge7_k$ = function (str) {
    return Companion_getInstance_2().parse_pc1q8p_k$(str);
  };
  protoOf(Companion_1).parse_pc1q8p_k$ = function (str) {
    return Companion_getInstance_2().parse_pc1q8p_k$(str);
  };
  protoOf(Companion_1).now_wzdese_k$ = function () {
    return _DateTime___init__impl__ifgty2(KlockInternal_getInstance().get_currentTime_nu5t31_k$());
  };
  protoOf(Companion_1).nowLocal_r78aid_k$ = function () {
    return Companion_getInstance_6().nowLocal_r78aid_k$();
  };
  protoOf(Companion_1).nowUnixMillis_eon0kc_k$ = function () {
    return KlockInternal_getInstance().get_currentTime_nu5t31_k$();
  };
  protoOf(Companion_1).nowUnixMillisLong_cptog0_k$ = function () {
    return numberToLong(KlockInternal_getInstance().get_currentTime_nu5t31_k$());
  };
  protoOf(Companion_1).get_EPOCH_INTERNAL_MILLIS_9v8vfc_k$ = function () {
    return this.EPOCH_INTERNAL_MILLIS_1;
  };
  protoOf(Companion_1).dateToMillisUnchecked_q3lt3e_k$ = function (year, month, day) {
    return (((_Year___get_daysSinceOne__impl__cf4kem(_Year___init__impl__2jvef0(year)) + Companion_getInstance_11().invoke_dlyhwg_k$(month).daysToStart_x9v8c8_k$(year) | 0) + day | 0) - 1 | 0) * 8.64E7 - 6.21355968E13;
  };
  protoOf(Companion_1).getDatePart_scmrio_k$ = function (millis, part) {
    var totalDays = toInt2(millis / 86400000);
    var year = Companion_getInstance_21().fromDays_egwdof_k$(totalDays);
    if (part.equals(DatePart_Year_getInstance()))
      return _Year___get_year__impl__hjar94(year);
    var isLeap = _Year___get_isLeap__impl__kmyeoz(year);
    var startYearDays = _Year___get_daysSinceOne__impl__cf4kem(year);
    var dayOfYear = 1 + umod(totalDays - startYearDays | 0, _Year___get_days__impl__xyqg2q(year)) | 0;
    if (part.equals(DatePart_DayOfYear_getInstance()))
      return dayOfYear;
    var tmp0_elvis_lhs = Companion_getInstance_11().fromDayOfYear_p5oq19_k$(dayOfYear, isLeap);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      var message = 'Invalid dayOfYear=' + dayOfYear + ', isLeap=' + isLeap;
      throw IllegalStateException_init_$Create$(toString(message));
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var month = tmp;
    if (part.equals(DatePart_Month_getInstance()))
      return month.get_index1_etpsq0_k$();
    var dayOfMonth = dayOfYear - month.daysToStart_kxj591_k$(isLeap) | 0;
    if (part.equals(DatePart_Day_getInstance()))
      return dayOfMonth;
    // Inline function 'kotlin.error' call
    var message_0 = 'Invalid DATE_PART';
    throw IllegalStateException_init_$Create$(toString(message_0));
  };
  var Companion_instance_1;
  function Companion_getInstance_3() {
    if (Companion_instance_1 == null)
      new Companion_1();
    return Companion_instance_1;
  }
  function _DateTime___get_yearOneMillis__impl__jjnslg($this) {
    return 6.21355968E13 + _DateTime___get_unixMillis__impl__ofxkzp($this);
  }
  function _DateTime___get_localOffset__impl__qgw4hf($this) {
    return Companion_getInstance_20().local_qfhv1l_k$(_DateTime___init__impl__ifgty2(_DateTime___get_unixMillisDouble__impl__3dzlsc($this)));
  }
  function _DateTime___get_unixMillisDouble__impl__3dzlsc($this) {
    return _DateTime___get_unixMillis__impl__ofxkzp($this);
  }
  function _DateTime___get_unixMillisLong__impl__sdx9bd($this) {
    return numberToLong(_DateTime___get_unixMillisDouble__impl__3dzlsc($this));
  }
  function _DateTime___get_year__impl__mczyhi($this) {
    return _Year___init__impl__2jvef0(_DateTime___get_yearInt__impl__876eh3($this));
  }
  function _DateTime___get_yearInt__impl__876eh3($this) {
    return Companion_getInstance_3().getDatePart_scmrio_k$(_DateTime___get_yearOneMillis__impl__jjnslg($this), DatePart_Year_getInstance());
  }
  function _DateTime___get_month__impl__4rr4id($this) {
    return Companion_getInstance_11().get_c1px32_k$(_DateTime___get_month1__impl__hf8ase($this));
  }
  function _DateTime___get_month0__impl__os9a4j($this) {
    return _DateTime___get_month1__impl__hf8ase($this) - 1 | 0;
  }
  function _DateTime___get_month1__impl__hf8ase($this) {
    return Companion_getInstance_3().getDatePart_scmrio_k$(_DateTime___get_yearOneMillis__impl__jjnslg($this), DatePart_Month_getInstance());
  }
  function _DateTime___get_yearMonth__impl__3rw9mg($this) {
    return Companion_getInstance_22().invoke_1jh3dp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this));
  }
  function _DateTime___get_dayOfMonth__impl__z5062($this) {
    return Companion_getInstance_3().getDatePart_scmrio_k$(_DateTime___get_yearOneMillis__impl__jjnslg($this), DatePart_Day_getInstance());
  }
  function _DateTime___get_dayOfWeek__impl__o3da04($this) {
    return Companion_getInstance_7().get_c1px32_k$(_DateTime___get_dayOfWeekInt__impl__yrgob9($this));
  }
  function _DateTime___get_dayOfWeekInt__impl__yrgob9($this) {
    return toIntMod(_DateTime___get_yearOneMillis__impl__jjnslg($this) / 86400000 + 1, 7);
  }
  function _DateTime___get_dayOfYear__impl__1v0nln($this) {
    return Companion_getInstance_3().getDatePart_scmrio_k$(_DateTime___get_yearOneMillis__impl__jjnslg($this), DatePart_DayOfYear_getInstance());
  }
  function _DateTime___get_hours__impl__gihczo($this) {
    return toIntMod(_DateTime___get_yearOneMillis__impl__jjnslg($this) / 3600000, 24);
  }
  function _DateTime___get_minutes__impl__ua9590($this) {
    return toIntMod(_DateTime___get_yearOneMillis__impl__jjnslg($this) / 60000, 60);
  }
  function _DateTime___get_seconds__impl__145pqk($this) {
    return toIntMod(_DateTime___get_yearOneMillis__impl__jjnslg($this) / 1000, 60);
  }
  function _DateTime___get_milliseconds__impl__xmznxd($this) {
    return toIntMod(_DateTime___get_yearOneMillis__impl__jjnslg($this), 1000);
  }
  function _DateTime___get_localUnadjusted__impl__4nzly1($this) {
    return Companion_getInstance_6().local_h88k47_k$($this, _DateTime___get_localOffset__impl__qgw4hf($this));
  }
  function DateTime__toOffsetUnadjusted_impl_iorn($this, offset) {
    return DateTime__toOffsetUnadjusted_impl_iorn_0($this, get_offset(offset));
  }
  function DateTime__toOffsetUnadjusted_impl_iorn_0($this, offset) {
    return Companion_getInstance_6().local_h88k47_k$($this, offset);
  }
  function _DateTime___get_local__impl__1rvktc($this) {
    return Companion_getInstance_6().utc_dlfehc_k$($this, _DateTime___get_localOffset__impl__qgw4hf($this));
  }
  function DateTime__toOffset_impl_xf5s4k($this, offset) {
    return DateTime__toOffset_impl_xf5s4k_0($this, get_offset(offset));
  }
  function DateTime__toOffset_impl_xf5s4k_0($this, offset) {
    return Companion_getInstance_6().utc_dlfehc_k$($this, offset);
  }
  function DateTime__toTimezone_impl_aod08u($this, timeZone) {
    return DateTime__toOffset_impl_xf5s4k_0($this, timeZone.get_offset_noi1v9_k$());
  }
  function _DateTime___get_utc__impl__wn9kuf($this) {
    var tmp = Companion_getInstance_6();
    var tmp_0 = Companion_getInstance_20();
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var tmp$ret$1 = toDuration(0, DurationUnit_MINUTES_getInstance());
    return tmp.utc_dlfehc_k$($this, tmp_0.invoke_m9hl7e_k$(tmp$ret$1));
  }
  function _DateTime___get_dateDayStart__impl__hfd2rl($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), 0, 0, 0, 0);
  }
  function _DateTime___get_dateDayEnd__impl__bbd3ty($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), 23, 59, 59, 999);
  }
  function _DateTime___get_quarter__impl__ich2kh($this) {
    return (_DateTime___get_month0__impl__os9a4j($this) / 3 | 0) + 1 | 0;
  }
  function _DateTime___get_startOfYear__impl__er9m6j($this) {
    return Companion_getInstance_3().invoke$default_hxrmzr_k$(_DateTime___get_year__impl__mczyhi($this), Month_January_getInstance(), 1);
  }
  function _DateTime___get_startOfMonth__impl__iy71lg($this) {
    return Companion_getInstance_3().invoke$default_hxrmzr_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), 1);
  }
  function _DateTime___get_startOfQuarter__impl__6dsqm8($this) {
    return Companion_getInstance_3().invoke$default_hxrmzr_k$(_DateTime___get_year__impl__mczyhi($this), Companion_getInstance_11().get_c1px32_k$(imul(_DateTime___get_quarter__impl__ich2kh($this) - 1 | 0, 3) + 1 | 0), 1);
  }
  function DateTime__startOfDayOfWeek_impl_v8qbi4($this, day) {
    var inductionVariable = 0;
    if (inductionVariable < 7)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.time.days' call
        // Inline function 'korlibs.time.days' call
        var tmp$ret$1 = toDuration(n, DurationUnit_DAYS_getInstance());
        var date = DateTime__minus_impl_10njw8_1($this, tmp$ret$1);
        if (_DateTime___get_dayOfWeek__impl__o3da04(date).equals(day))
          return _DateTime___get_startOfDay__impl__srba5s(date);
      }
       while (inductionVariable < 7);
    // Inline function 'kotlin.error' call
    var message = "Shouldn't happen";
    throw IllegalStateException_init_$Create$(toString(message));
  }
  function _DateTime___get_startOfWeek__impl__7h307y($this) {
    return DateTime__startOfDayOfWeek_impl_v8qbi4($this, DayOfWeek_Sunday_getInstance());
  }
  function _DateTime___get_startOfIsoWeek__impl__vuysi5($this) {
    return DateTime__startOfDayOfWeek_impl_v8qbi4($this, DayOfWeek_Monday_getInstance());
  }
  function _DateTime___get_startOfDay__impl__srba5s($this) {
    return Companion_getInstance_3().invoke$default_hxrmzr_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this));
  }
  function _DateTime___get_startOfHour__impl__26bram($this) {
    return Companion_getInstance_3().invoke$default_hxrmzr_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), _DateTime___get_hours__impl__gihczo($this));
  }
  function _DateTime___get_startOfMinute__impl__rs1zi6($this) {
    return Companion_getInstance_3().invoke$default_hxrmzr_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), _DateTime___get_hours__impl__gihczo($this), _DateTime___get_minutes__impl__ua9590($this));
  }
  function _DateTime___get_startOfSecond__impl__a5rmn6($this) {
    return Companion_getInstance_3().invoke$default_hxrmzr_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), _DateTime___get_hours__impl__gihczo($this), _DateTime___get_minutes__impl__ua9590($this), _DateTime___get_seconds__impl__145pqk($this));
  }
  function _DateTime___get_endOfYear__impl__43jzac($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), Month_December_getInstance(), 31, 23, 59, 59, 999);
  }
  function _DateTime___get_endOfMonth__impl__rcfg85($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_month__impl__4rr4id($this).days_eg9z5s_k$(_DateTime___get_year__impl__mczyhi($this)), 23, 59, 59, 999);
  }
  function _DateTime___get_endOfQuarter__impl__hw6njt($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), Companion_getInstance_11().get_c1px32_k$(imul(_DateTime___get_quarter__impl__ich2kh($this) - 1 | 0, 3) + 3 | 0), _DateTime___get_month__impl__4rr4id($this).days_eg9z5s_k$(_DateTime___get_year__impl__mczyhi($this)), 23, 59, 59, 999);
  }
  function DateTime__endOfDayOfWeek_impl_j1y9dv($this, day) {
    var inductionVariable = 0;
    if (inductionVariable < 7)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'korlibs.time.days' call
        // Inline function 'korlibs.time.days' call
        var tmp$ret$1 = toDuration(n, DurationUnit_DAYS_getInstance());
        var date = DateTime__plus_impl_5iyako_1($this, tmp$ret$1);
        if (_DateTime___get_dayOfWeek__impl__o3da04(date).equals(day))
          return _DateTime___get_endOfDay__impl__5i26gn(date);
      }
       while (inductionVariable < 7);
    // Inline function 'kotlin.error' call
    var message = "Shouldn't happen";
    throw IllegalStateException_init_$Create$(toString(message));
  }
  function _DateTime___get_endOfWeek__impl__i4sn45($this) {
    return DateTime__endOfDayOfWeek_impl_j1y9dv($this, DayOfWeek_Monday_getInstance());
  }
  function _DateTime___get_endOfIsoWeek__impl__7kzec4($this) {
    return DateTime__endOfDayOfWeek_impl_j1y9dv($this, DayOfWeek_Sunday_getInstance());
  }
  function _DateTime___get_endOfDay__impl__5i26gn($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), 23, 59, 59, 999);
  }
  function _DateTime___get_endOfHour__impl__cu1e6t($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), _DateTime___get_hours__impl__gihczo($this), 59, 59, 999);
  }
  function _DateTime___get_endOfMinute__impl__spxti3($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), _DateTime___get_hours__impl__gihczo($this), _DateTime___get_minutes__impl__ua9590($this), 59, 999);
  }
  function _DateTime___get_endOfSecond__impl__4dcmbp($this) {
    return Companion_getInstance_3().invoke_422pxp_k$(_DateTime___get_year__impl__mczyhi($this), _DateTime___get_month__impl__4rr4id($this), _DateTime___get_dayOfMonth__impl__z5062($this), _DateTime___get_hours__impl__gihczo($this), _DateTime___get_minutes__impl__ua9590($this), _DateTime___get_seconds__impl__145pqk($this), 999);
  }
  function _DateTime___get_date__impl__x8g5h($this) {
    return Companion_getInstance_1().invoke_b5nqkb_k$(_DateTime___get_yearInt__impl__876eh3($this), _DateTime___get_month1__impl__hf8ase($this), _DateTime___get_dayOfMonth__impl__z5062($this));
  }
  function _DateTime___get_time__impl__779dfe($this) {
    return Companion_getInstance_17().invoke_pdi41e_k$(_DateTime___get_hours__impl__gihczo($this), _DateTime___get_minutes__impl__ua9590($this), _DateTime___get_seconds__impl__145pqk($this), _DateTime___get_milliseconds__impl__xmznxd($this));
  }
  function DateTime__plus_impl_5iyako($this, delta) {
    return DateTime__add_impl_fzlt95($this, _MonthSpan___get_totalMonths__impl__8ojvr1(delta), 0.0);
  }
  function DateTime__plus_impl_5iyako_0($this, delta) {
    return DateTime__add_impl_fzlt95($this, delta.get_totalMonths_16su1e_k$(), delta.get_totalMilliseconds_547ho3_k$());
  }
  function DateTime__plus_impl_5iyako_1($this, delta) {
    return DateTime__add_impl_fzlt95($this, 0, get_milliseconds_0(delta));
  }
  function DateTime__minus_impl_10njw8($this, delta) {
    return DateTime__plus_impl_5iyako($this, MonthSpan__unaryMinus_impl_3wgm0y(delta));
  }
  function DateTime__minus_impl_10njw8_0($this, delta) {
    return DateTime__plus_impl_5iyako_0($this, delta.unaryMinus_6uz0qp_k$());
  }
  function DateTime__minus_impl_10njw8_1($this, delta) {
    return DateTime__plus_impl_5iyako_1($this, Duration__unaryMinus_impl_x2k1y0(delta));
  }
  function DateTime__minus_impl_10njw8_2($this, other) {
    // Inline function 'korlibs.time.milliseconds' call
    var this_0 = _DateTime___get_unixMillisDouble__impl__3dzlsc($this) - _DateTime___get_unixMillisDouble__impl__3dzlsc(other);
    return toDuration(this_0, DurationUnit_MILLISECONDS_getInstance());
  }
  function DateTime__compareTo_impl_geo3bs($this, other) {
    return compareTo(_DateTime___get_unixMillis__impl__ofxkzp($this), _DateTime___get_unixMillis__impl__ofxkzp(other));
  }
  function DateTime__compareTo_impl_geo3bs_0($this, other) {
    return DateTime__compareTo_impl_geo3bs($this.unixMillis_1, other instanceof DateTime ? other.unixMillis_1 : THROW_CCE());
  }
  function DateTime__add_impl_fzlt95($this, deltaMonths, deltaMilliseconds) {
    var tmp;
    if (deltaMonths === 0 ? deltaMilliseconds === 0.0 : false) {
      tmp = $this;
    } else if (deltaMonths === 0) {
      tmp = _DateTime___init__impl__ifgty2(_DateTime___get_unixMillis__impl__ofxkzp($this) + deltaMilliseconds);
    } else {
      var year = _DateTime___get_year__impl__mczyhi($this);
      var month = _DateTime___get_month__impl__4rr4id($this).get_index1_etpsq0_k$();
      var day = _DateTime___get_dayOfMonth__impl__z5062($this);
      var i = (month - 1 | 0) + deltaMonths | 0;
      if (i >= 0) {
        month = (i % 12 | 0) + 1 | 0;
        year = Year__plus_impl_l5v1ey(year, i / 12 | 0);
      } else {
        month = 12 + ((i + 1 | 0) % 12 | 0) | 0;
        year = Year__plus_impl_l5v1ey(year, (i - 11 | 0) / 12 | 0);
      }
      var days = Companion_getInstance_11().invoke_dlyhwg_k$(month).days_eg9z5s_k$(year);
      if (day > days) {
        day = days;
      }
      tmp = _DateTime___init__impl__ifgty2(Companion_getInstance_3().dateToMillisUnchecked_q3lt3e_k$(_Year___get_year__impl__hjar94(year), month, day) + _DateTime___get_yearOneMillis__impl__jjnslg($this) % 86400000 + deltaMilliseconds);
    }
    return tmp;
  }
  function DateTime__add_impl_fzlt95_0($this, dateSpan, timeSpan) {
    return DateTime__add_impl_fzlt95($this, _MonthSpan___get_totalMonths__impl__8ojvr1(dateSpan), get_milliseconds_0(timeSpan));
  }
  function DateTime__copyDayOfMonth_impl_ti7cfk($this, year, month, dayOfMonth, hours, minutes, seconds, milliseconds) {
    return Companion_getInstance_3().invoke_422pxp_k$(year, month, dayOfMonth, hours, minutes, seconds, milliseconds);
  }
  function DateTime__copyDayOfMonth$default_impl_apy88d($this, year, month, dayOfMonth, hours, minutes, seconds, milliseconds, $super) {
    year = year === VOID ? _DateTime___get_year__impl__mczyhi($this) : year;
    month = month === VOID ? _DateTime___get_month__impl__4rr4id($this) : month;
    dayOfMonth = dayOfMonth === VOID ? _DateTime___get_dayOfMonth__impl__z5062($this) : dayOfMonth;
    hours = hours === VOID ? _DateTime___get_hours__impl__gihczo($this) : hours;
    minutes = minutes === VOID ? _DateTime___get_minutes__impl__ua9590($this) : minutes;
    seconds = seconds === VOID ? _DateTime___get_seconds__impl__145pqk($this) : seconds;
    milliseconds = milliseconds === VOID ? _DateTime___get_milliseconds__impl__xmznxd($this) : milliseconds;
    var tmp;
    if ($super === VOID) {
      tmp = DateTime__copyDayOfMonth_impl_ti7cfk($this, year, month, dayOfMonth, hours, minutes, seconds, milliseconds);
    } else {
      var tmp_0 = $super;
      tmp = (tmp_0 == null ? null : new DateTime(tmp_0)).copyDayOfMonth_xy930j_k$.call(new DateTime($this), new Year(year), month, dayOfMonth, hours, minutes, seconds, milliseconds).unixMillis_1;
    }
    return tmp;
  }
  function DateTime__format_impl_6el5l1($this, format_0) {
    return format(format_0, $this);
  }
  function DateTime__format_impl_6el5l1_0($this, format_0) {
    return format(Companion_getInstance_2().invoke_lt562m_k$(format_0), $this);
  }
  function DateTime__toString_impl_cnueje($this, format_0) {
    return format(Companion_getInstance_2().invoke_lt562m_k$(format_0), $this);
  }
  function DateTime__toString_impl_cnueje_0($this, format_0) {
    return format(format_0, $this);
  }
  function DateTime__toStringDefault_impl_c0dpub($this) {
    return format(Companion_getInstance_2().get_DEFAULT_FORMAT_t79cy_k$(), $this);
  }
  function DateTime__toString_impl_cnueje_1($this) {
    return 'DateTime(' + _DateTime___get_unixMillisLong__impl__sdx9bd($this).toString() + ')';
  }
  function DateTime__hashCode_impl_yf0bc9($this) {
    return getNumberHashCode($this);
  }
  function DateTime__equals_impl_qd7s8j($this, other) {
    if (!(other instanceof DateTime))
      return false;
    var tmp0_other_with_cast = other instanceof DateTime ? other.unixMillis_1 : THROW_CCE();
    if (!equals($this, tmp0_other_with_cast))
      return false;
    return true;
  }
  function DateTime(unixMillis) {
    Companion_getInstance_3();
    this.unixMillis_1 = unixMillis;
  }
  protoOf(DateTime).compareTo_re4xb9_k$ = function (other) {
    return DateTime__compareTo_impl_geo3bs(this.unixMillis_1, other);
  };
  protoOf(DateTime).compareTo_hpufkf_k$ = function (other) {
    return DateTime__compareTo_impl_geo3bs_0(this, other);
  };
  protoOf(DateTime).toString = function () {
    return DateTime__toString_impl_cnueje_1(this.unixMillis_1);
  };
  protoOf(DateTime).hashCode = function () {
    return DateTime__hashCode_impl_yf0bc9(this.unixMillis_1);
  };
  protoOf(DateTime).equals = function (other) {
    return DateTime__equals_impl_qd7s8j(this.unixMillis_1, other);
  };
  function _get_serialVersionUID__fhggm9_1($this) {
    return $this.serialVersionUID_1;
  }
  function Companion_2() {
    Companion_instance_2 = this;
  }
  protoOf(Companion_2).invoke_71s1sd_k$ = function (time) {
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.contracts.contract' call
    var $this$run = new Moduler(get_milliseconds_0(time));
    var weeks = $this$run.int_hxmlc9_k$(604800000);
    var days = $this$run.int_hxmlc9_k$(86400000);
    var hours = $this$run.int_hxmlc9_k$(3600000);
    var minutes = $this$run.int_hxmlc9_k$(60000);
    var seconds = $this$run.int_hxmlc9_k$(1000);
    var milliseconds = $this$run.double_86m3c7_k$(1);
    return new ComputedTime(weeks, days, hours, minutes, seconds, milliseconds);
  };
  var Companion_instance_2;
  function Companion_getInstance_4() {
    if (Companion_instance_2 == null)
      new Companion_2();
    return Companion_instance_2;
  }
  function Companion_3() {
    Companion_instance_3 = this;
    this.serialVersionUID_1 = new Long(1, 0);
  }
  var Companion_instance_3;
  function Companion_getInstance_5() {
    if (Companion_instance_3 == null)
      new Companion_3();
    return Companion_instance_3;
  }
  function DateTimeSpan_init_$Init$(years, months, weeks, days, hours, minutes, seconds, milliseconds, $this) {
    years = years === VOID ? 0 : years;
    months = months === VOID ? 0 : months;
    weeks = weeks === VOID ? 0 : weeks;
    days = days === VOID ? 0 : days;
    hours = hours === VOID ? 0 : hours;
    minutes = minutes === VOID ? 0 : minutes;
    seconds = seconds === VOID ? 0 : seconds;
    milliseconds = milliseconds === VOID ? 0.0 : milliseconds;
    // Inline function 'korlibs.time.years' call
    var tmp = _MonthSpan___init__impl__5r6mxb(imul(12, years));
    // Inline function 'korlibs.time.months' call
    var tmp$ret$1 = _MonthSpan___init__impl__5r6mxb(months);
    var tmp_0 = MonthSpan__plus_impl_mt00vr_0(tmp, tmp$ret$1);
    // Inline function 'korlibs.time.weeks' call
    // Inline function 'korlibs.time.weeks' call
    // Inline function 'korlibs.time.days' call
    var this_0 = weeks * 7;
    var tmp_1 = toDuration(this_0, DurationUnit_DAYS_getInstance());
    // Inline function 'korlibs.time.days' call
    // Inline function 'korlibs.time.days' call
    var tmp$ret$6 = toDuration(days, DurationUnit_DAYS_getInstance());
    var tmp_2 = Duration__plus_impl_yu9v8f(tmp_1, tmp$ret$6);
    // Inline function 'korlibs.time.hours' call
    // Inline function 'korlibs.time.hours' call
    var tmp$ret$8 = toDuration(hours, DurationUnit_HOURS_getInstance());
    var tmp_3 = Duration__plus_impl_yu9v8f(tmp_2, tmp$ret$8);
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var tmp$ret$10 = toDuration(minutes, DurationUnit_MINUTES_getInstance());
    var tmp_4 = Duration__plus_impl_yu9v8f(tmp_3, tmp$ret$10);
    // Inline function 'korlibs.time.seconds' call
    // Inline function 'korlibs.time.seconds' call
    var tmp$ret$12 = toDuration(seconds, DurationUnit_SECONDS_getInstance());
    var tmp_5 = Duration__plus_impl_yu9v8f(tmp_4, tmp$ret$12);
    // Inline function 'korlibs.time.milliseconds' call
    var tmp$ret$13 = toDuration(milliseconds, DurationUnit_MILLISECONDS_getInstance());
    DateTimeSpan.call($this, tmp_0, Duration__plus_impl_yu9v8f(tmp_5, tmp$ret$13));
    return $this;
  }
  function DateTimeSpan_init_$Create$(years, months, weeks, days, hours, minutes, seconds, milliseconds) {
    return DateTimeSpan_init_$Init$(years, months, weeks, days, hours, minutes, seconds, milliseconds, objectCreate(protoOf(DateTimeSpan)));
  }
  function ComputedTime(weeks, days, hours, minutes, seconds, milliseconds) {
    Companion_getInstance_4();
    this.weeks_1 = weeks;
    this.days_1 = days;
    this.hours_1 = hours;
    this.minutes_1 = minutes;
    this.seconds_1 = seconds;
    this.milliseconds_1 = milliseconds;
  }
  protoOf(ComputedTime).get_weeks_j0nljq_k$ = function () {
    return this.weeks_1;
  };
  protoOf(ComputedTime).get_days_wokl28_k$ = function () {
    return this.days_1;
  };
  protoOf(ComputedTime).get_hours_islejq_k$ = function () {
    return this.hours_1;
  };
  protoOf(ComputedTime).get_minutes_ivkhkm_k$ = function () {
    return this.minutes_1;
  };
  protoOf(ComputedTime).get_seconds_xuhyfq_k$ = function () {
    return this.seconds_1;
  };
  protoOf(ComputedTime).get_milliseconds_5yabtn_k$ = function () {
    return this.milliseconds_1;
  };
  function _get_computed__1g6kpq($this) {
    // Inline function 'kotlin.getValue' call
    var this_0 = $this.computed$delegate_1;
    computed$factory();
    return this_0.get_value_j01efc_k$();
  }
  function DateTimeSpan$computed$delegate$lambda(this$0) {
    return function () {
      return Companion_getInstance_4().invoke_71s1sd_k$(this$0.timeSpan_1);
    };
  }
  function DateTimeSpan(monthSpan, timeSpan) {
    Companion_getInstance_5();
    this.monthSpan_1 = monthSpan;
    this.timeSpan_1 = timeSpan;
    var tmp = this;
    tmp.computed$delegate_1 = lazy(DateTimeSpan$computed$delegate$lambda(this));
  }
  protoOf(DateTimeSpan).get_monthSpan_o3llz8_k$ = function () {
    return this.monthSpan_1;
  };
  protoOf(DateTimeSpan).get_timeSpan_7vf9yi_k$ = function () {
    return this.timeSpan_1;
  };
  protoOf(DateTimeSpan).unaryMinus_6uz0qp_k$ = function () {
    return new DateTimeSpan(MonthSpan__unaryMinus_impl_3wgm0y(this.monthSpan_1), Duration__unaryMinus_impl_x2k1y0(this.timeSpan_1));
  };
  protoOf(DateTimeSpan).unaryPlus_g9fn1l_k$ = function () {
    return new DateTimeSpan(MonthSpan__unaryPlus_impl_gw1hsi(this.monthSpan_1), unaryPlus(this.timeSpan_1));
  };
  protoOf(DateTimeSpan).plus_oeswd1_k$ = function (other) {
    return new DateTimeSpan(this.monthSpan_1, Duration__plus_impl_yu9v8f(this.timeSpan_1, other));
  };
  protoOf(DateTimeSpan).plus_ea76px_k$ = function (other) {
    return new DateTimeSpan(MonthSpan__plus_impl_mt00vr_0(this.monthSpan_1, other), this.timeSpan_1);
  };
  protoOf(DateTimeSpan).plus_yz29n7_k$ = function (other) {
    return new DateTimeSpan(MonthSpan__plus_impl_mt00vr_0(this.monthSpan_1, other.monthSpan_1), Duration__plus_impl_yu9v8f(this.timeSpan_1, other.timeSpan_1));
  };
  protoOf(DateTimeSpan).minus_j7epkb_k$ = function (other) {
    return this.plus_oeswd1_k$(Duration__unaryMinus_impl_x2k1y0(other));
  };
  protoOf(DateTimeSpan).minus_lkoelr_k$ = function (other) {
    return this.plus_ea76px_k$(MonthSpan__unaryMinus_impl_3wgm0y(other));
  };
  protoOf(DateTimeSpan).minus_tktrh3_k$ = function (other) {
    return this.plus_yz29n7_k$(other.unaryMinus_6uz0qp_k$());
  };
  protoOf(DateTimeSpan).times_qz1dds_k$ = function (times) {
    return new DateTimeSpan(MonthSpan__times_impl_vbjrw1(this.monthSpan_1, times), Duration__times_impl_sfuzvp(this.timeSpan_1, times));
  };
  protoOf(DateTimeSpan).times_kr2a3y_k$ = function (times) {
    return this.times_qz1dds_k$(times);
  };
  protoOf(DateTimeSpan).times_422v76_k$ = function (times) {
    return this.times_qz1dds_k$(times);
  };
  protoOf(DateTimeSpan).div_k6dnjf_k$ = function (times) {
    return this.times_qz1dds_k$(1.0 / times);
  };
  protoOf(DateTimeSpan).div_fxyyjd_k$ = function (times) {
    return this.div_k6dnjf_k$(times);
  };
  protoOf(DateTimeSpan).div_nq5qk9_k$ = function (times) {
    return this.div_k6dnjf_k$(times);
  };
  protoOf(DateTimeSpan).get_totalYears_vxsj2d_k$ = function () {
    return get_totalYears(this.monthSpan_1);
  };
  protoOf(DateTimeSpan).get_totalMonths_16su1e_k$ = function () {
    return _MonthSpan___get_totalMonths__impl__8ojvr1(this.monthSpan_1);
  };
  protoOf(DateTimeSpan).get_totalMilliseconds_547ho3_k$ = function () {
    return get_milliseconds_0(this.timeSpan_1);
  };
  protoOf(DateTimeSpan).get_years_j1r3xp_k$ = function () {
    return get_years(this.monthSpan_1);
  };
  protoOf(DateTimeSpan).get_months_gqmrjw_k$ = function () {
    return get_months_0(this.monthSpan_1);
  };
  protoOf(DateTimeSpan).get_weeks_j0nljq_k$ = function () {
    return _get_computed__1g6kpq(this).weeks_1;
  };
  protoOf(DateTimeSpan).get_daysNotIncludingWeeks_inp4s3_k$ = function () {
    return this.get_days_wokl28_k$();
  };
  protoOf(DateTimeSpan).get_daysIncludingWeeks_dtup2_k$ = function () {
    return _get_computed__1g6kpq(this).days_1 + imul(_get_computed__1g6kpq(this).weeks_1, 7) | 0;
  };
  protoOf(DateTimeSpan).get_days_wokl28_k$ = function () {
    return _get_computed__1g6kpq(this).days_1;
  };
  protoOf(DateTimeSpan).get_hours_islejq_k$ = function () {
    return _get_computed__1g6kpq(this).hours_1;
  };
  protoOf(DateTimeSpan).get_minutes_ivkhkm_k$ = function () {
    return _get_computed__1g6kpq(this).minutes_1;
  };
  protoOf(DateTimeSpan).get_seconds_xuhyfq_k$ = function () {
    return _get_computed__1g6kpq(this).seconds_1;
  };
  protoOf(DateTimeSpan).get_milliseconds_5yabtn_k$ = function () {
    return _get_computed__1g6kpq(this).milliseconds_1;
  };
  protoOf(DateTimeSpan).get_secondsIncludingMilliseconds_luy40v_k$ = function () {
    return _get_computed__1g6kpq(this).seconds_1 + _get_computed__1g6kpq(this).milliseconds_1 / 1000;
  };
  protoOf(DateTimeSpan).compareTo_y1ypa1_k$ = function (other) {
    if (!(this.get_totalMonths_16su1e_k$() === other.get_totalMonths_16su1e_k$()))
      return MonthSpan__compareTo_impl_82y3av(this.monthSpan_1, other.monthSpan_1);
    return Duration__compareTo_impl_pchp0f(this.timeSpan_1, other.timeSpan_1);
  };
  protoOf(DateTimeSpan).compareTo_hpufkf_k$ = function (other) {
    return this.compareTo_y1ypa1_k$(other instanceof DateTimeSpan ? other : THROW_CCE());
  };
  protoOf(DateTimeSpan).toString_8x7yyh_k$ = function (includeWeeks) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_0 = ArrayList_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.time.DateTimeSpan.toString.<anonymous>' call
    if (!(this.get_years_j1r3xp_k$() === 0)) {
      this_0.add_utx5q5_k$('' + this.get_years_j1r3xp_k$() + 'Y');
    }
    if (!(this.get_months_gqmrjw_k$() === 0)) {
      this_0.add_utx5q5_k$('' + this.get_months_gqmrjw_k$() + 'M');
    }
    if (includeWeeks ? !(this.get_weeks_j0nljq_k$() === 0) : false) {
      this_0.add_utx5q5_k$('' + this.get_weeks_j0nljq_k$() + 'W');
    }
    if (!(this.get_days_wokl28_k$() === 0) ? true : !includeWeeks ? !(this.get_weeks_j0nljq_k$() === 0) : false) {
      this_0.add_utx5q5_k$('' + (includeWeeks ? this.get_days_wokl28_k$() : this.get_daysIncludingWeeks_dtup2_k$()) + 'D');
    }
    if (!(this.get_hours_islejq_k$() === 0)) {
      this_0.add_utx5q5_k$('' + this.get_hours_islejq_k$() + 'H');
    }
    if (!(this.get_minutes_ivkhkm_k$() === 0)) {
      this_0.add_utx5q5_k$('' + this.get_minutes_ivkhkm_k$() + 'm');
    }
    if (!(this.get_seconds_xuhyfq_k$() === 0) ? true : !(this.get_milliseconds_5yabtn_k$() === 0.0)) {
      this_0.add_utx5q5_k$('' + this.get_secondsIncludingMilliseconds_luy40v_k$() + 's');
    }
    var tmp;
    // Inline function 'korlibs.time.years' call
    var tmp$ret$1 = _MonthSpan___init__impl__5r6mxb(imul(12, 0));
    if (this.monthSpan_1 === tmp$ret$1) {
      var tmp_0;
      // Inline function 'korlibs.time.seconds' call
      // Inline function 'korlibs.time.seconds' call
      var tmp$ret$3 = toDuration(0, DurationUnit_SECONDS_getInstance());
      if (equals(this.timeSpan_1, tmp$ret$3)) {
        tmp_0 = true;
      } else {
        // Inline function 'korlibs.time.seconds' call
        // Inline function 'korlibs.time.seconds' call
        var tmp$ret$5 = toDuration(0, DurationUnit_SECONDS_getInstance());
        tmp_0 = equals(this.timeSpan_1, tmp$ret$5);
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    if (tmp) {
      this_0.add_utx5q5_k$('0s');
    }
    return joinToString(this_0, ' ');
  };
  protoOf(DateTimeSpan).toString = function () {
    return this.toString_8x7yyh_k$(true);
  };
  protoOf(DateTimeSpan).component1_1dui2f_k$ = function () {
    return this.monthSpan_1;
  };
  protoOf(DateTimeSpan).component2_pa7z4b_k$ = function () {
    return this.timeSpan_1;
  };
  protoOf(DateTimeSpan).copy_k8l64x_k$ = function (monthSpan, timeSpan) {
    return new DateTimeSpan(monthSpan, timeSpan);
  };
  protoOf(DateTimeSpan).copy$default_mx7huw_k$ = function (monthSpan, timeSpan, $super) {
    monthSpan = monthSpan === VOID ? this.monthSpan_1 : monthSpan;
    timeSpan = timeSpan === VOID ? this.timeSpan_1 : timeSpan;
    return $super === VOID ? this.copy_k8l64x_k$(monthSpan, timeSpan) : $super.copy_k8l64x_k$.call(this, new MonthSpan(monthSpan), new Duration(timeSpan));
  };
  protoOf(DateTimeSpan).hashCode = function () {
    var result = MonthSpan__hashCode_impl_318aso(this.monthSpan_1);
    result = imul(result, 31) + Duration__hashCode_impl_u4exz6(this.timeSpan_1) | 0;
    return result;
  };
  protoOf(DateTimeSpan).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof DateTimeSpan))
      return false;
    var tmp0_other_with_cast = other instanceof DateTimeSpan ? other : THROW_CCE();
    if (!(this.monthSpan_1 === tmp0_other_with_cast.monthSpan_1))
      return false;
    if (!equals(this.timeSpan_1, tmp0_other_with_cast.timeSpan_1))
      return false;
    return true;
  };
  function computed$factory() {
    return getPropertyCallableRef('computed', 1, KProperty1, function (receiver) {
      return _get_computed__1g6kpq(receiver);
    }, null);
  }
  function _get_serialVersionUID__fhggm9_2($this) {
    return $this.serialVersionUID_1;
  }
  function _get_adjusted__de2pcz($this) {
    return $this.adjusted_1;
  }
  function Companion_4() {
    Companion_instance_4 = this;
    this.serialVersionUID_1 = new Long(1, 0);
  }
  protoOf(Companion_4).local_h88k47_k$ = function (local, offset) {
    return new DateTimeTz(local, offset);
  };
  protoOf(Companion_4).utc_dlfehc_k$ = function (utc, offset) {
    return new DateTimeTz(DateTime__plus_impl_5iyako_1(utc, _TimezoneOffset___get_time__impl__u2t42f(offset)), offset);
  };
  protoOf(Companion_4).fromUnixLocal_k9q42z_k$ = function (unix) {
    return _DateTime___get_localUnadjusted__impl__4nzly1(Companion_getInstance_3().invoke_ajmm9u_k$(unix));
  };
  protoOf(Companion_4).fromUnix_3nprtw_k$ = function (unix) {
    var unixDateTime = Companion_getInstance_3().invoke_ajmm9u_k$(unix);
    return this.utc_dlfehc_k$(unixDateTime, Companion_getInstance_20().local_qfhv1l_k$(unixDateTime));
  };
  protoOf(Companion_4).nowLocal_r78aid_k$ = function () {
    return _DateTime___get_local__impl__1rvktc(Companion_getInstance_3().now_wzdese_k$());
  };
  var Companion_instance_4;
  function Companion_getInstance_6() {
    if (Companion_instance_4 == null)
      new Companion_4();
    return Companion_instance_4;
  }
  function DateTimeTz(adjusted, offset) {
    Companion_getInstance_6();
    this.adjusted_1 = adjusted;
    this.offset_1 = offset;
  }
  protoOf(DateTimeTz).get_offset_noi1v9_k$ = function () {
    return this.offset_1;
  };
  protoOf(DateTimeTz).get_local_g3bngq_k$ = function () {
    return this.adjusted_1;
  };
  protoOf(DateTimeTz).get_utc_6lrfzx_k$ = function () {
    return DateTime__minus_impl_10njw8_1(this.adjusted_1, _TimezoneOffset___get_time__impl__u2t42f(this.offset_1));
  };
  protoOf(DateTimeTz).get_year_szgp3k_k$ = function () {
    return _DateTime___get_year__impl__mczyhi(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_yearInt_k6evlj_k$ = function () {
    return _DateTime___get_yearInt__impl__876eh3(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_month_ivc8d3_k$ = function () {
    return _DateTime___get_month__impl__4rr4id(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_month0_gqmri1_k$ = function () {
    return _DateTime___get_month0__impl__os9a4j(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_month1_gqmri2_k$ = function () {
    return _DateTime___get_month1__impl__hf8ase(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_yearMonth_x8084k_k$ = function () {
    return _DateTime___get_yearMonth__impl__3rw9mg(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_dayOfMonth_vblo3a_k$ = function () {
    return _DateTime___get_dayOfMonth__impl__z5062(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_dayOfWeek_3kfgci_k$ = function () {
    return _DateTime___get_dayOfWeek__impl__o3da04(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_dayOfWeekInt_gk546n_k$ = function () {
    return _DateTime___get_dayOfWeekInt__impl__yrgob9(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_dayOfYear_3ke6gp_k$ = function () {
    return _DateTime___get_dayOfYear__impl__1v0nln(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_hours_islejq_k$ = function () {
    return _DateTime___get_hours__impl__gihczo(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_minutes_ivkhkm_k$ = function () {
    return _DateTime___get_minutes__impl__ua9590(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_seconds_xuhyfq_k$ = function () {
    return _DateTime___get_seconds__impl__145pqk(this.adjusted_1);
  };
  protoOf(DateTimeTz).get_milliseconds_5yabtn_k$ = function () {
    return _DateTime___get_milliseconds__impl__xmznxd(this.adjusted_1);
  };
  protoOf(DateTimeTz).toOffsetUnadjusted_hhp53k_k$ = function (offset) {
    return this.toOffsetUnadjusted_27urtw_k$(get_offset(offset));
  };
  protoOf(DateTimeTz).toOffsetUnadjusted_27urtw_k$ = function (offset) {
    return Companion_getInstance_6().local_h88k47_k$(this.get_local_g3bngq_k$(), offset);
  };
  protoOf(DateTimeTz).addOffsetUnadjusted_cky4wm_k$ = function (offset) {
    return this.addOffsetUnadjusted_t16eki_k$(get_offset(offset));
  };
  protoOf(DateTimeTz).addOffsetUnadjusted_t16eki_k$ = function (offset) {
    return Companion_getInstance_6().local_h88k47_k$(this.get_local_g3bngq_k$(), get_offset(Duration__plus_impl_yu9v8f(_TimezoneOffset___get_time__impl__u2t42f(this.offset_1), _TimezoneOffset___get_time__impl__u2t42f(offset))));
  };
  protoOf(DateTimeTz).toOffset_j7m2ll_k$ = function (offset) {
    return this.toOffset_rd4fg5_k$(get_offset(offset));
  };
  protoOf(DateTimeTz).toOffset_rd4fg5_k$ = function (offset) {
    return Companion_getInstance_6().utc_dlfehc_k$(this.get_utc_6lrfzx_k$(), offset);
  };
  protoOf(DateTimeTz).addOffset_xu6bn5_k$ = function (offset) {
    return this.addOffset_33it7f_k$(get_offset(offset));
  };
  protoOf(DateTimeTz).addOffset_33it7f_k$ = function (offset) {
    return Companion_getInstance_6().utc_dlfehc_k$(this.get_utc_6lrfzx_k$(), get_offset(Duration__plus_impl_yu9v8f(_TimezoneOffset___get_time__impl__u2t42f(this.offset_1), _TimezoneOffset___get_time__impl__u2t42f(offset))));
  };
  protoOf(DateTimeTz).add_schqt9_k$ = function (dateSpan, timeSpan) {
    return new DateTimeTz(DateTime__add_impl_fzlt95_0(this.adjusted_1, dateSpan, timeSpan), this.offset_1);
  };
  protoOf(DateTimeTz).plus_ea76px_k$ = function (delta) {
    // Inline function 'korlibs.time.milliseconds' call
    // Inline function 'kotlin.time.Companion.milliseconds' call
    Companion_getInstance();
    var tmp$ret$1 = toDuration(0, DurationUnit_MILLISECONDS_getInstance());
    return this.add_schqt9_k$(delta, tmp$ret$1);
  };
  protoOf(DateTimeTz).plus_yz29n7_k$ = function (delta) {
    return this.add_schqt9_k$(delta.get_monthSpan_o3llz8_k$(), delta.get_timeSpan_7vf9yi_k$());
  };
  protoOf(DateTimeTz).plus_oeswd1_k$ = function (delta) {
    // Inline function 'korlibs.time.months' call
    var tmp$ret$0 = _MonthSpan___init__impl__5r6mxb(0);
    return this.add_schqt9_k$(tmp$ret$0, delta);
  };
  protoOf(DateTimeTz).minus_lkoelr_k$ = function (delta) {
    return this.plus_ea76px_k$(MonthSpan__unaryMinus_impl_3wgm0y(delta));
  };
  protoOf(DateTimeTz).minus_tktrh3_k$ = function (delta) {
    return this.plus_yz29n7_k$(delta.unaryMinus_6uz0qp_k$());
  };
  protoOf(DateTimeTz).minus_j7epkb_k$ = function (delta) {
    return this.plus_oeswd1_k$(Duration__unaryMinus_impl_x2k1y0(delta));
  };
  protoOf(DateTimeTz).minus_sz992h_k$ = function (other) {
    // Inline function 'korlibs.time.milliseconds' call
    var this_0 = _DateTime___get_unixMillisDouble__impl__3dzlsc(this.get_utc_6lrfzx_k$()) - _DateTime___get_unixMillisDouble__impl__3dzlsc(other.get_utc_6lrfzx_k$());
    return toDuration(this_0, DurationUnit_MILLISECONDS_getInstance());
  };
  protoOf(DateTimeTz).hashCode = function () {
    return DateTime__hashCode_impl_yf0bc9(this.get_local_g3bngq_k$()) + _TimezoneOffset___get_totalMinutesInt__impl__9h3gzq(this.offset_1) | 0;
  };
  protoOf(DateTimeTz).equals = function (other) {
    var tmp;
    if (other instanceof DateTimeTz) {
      tmp = _DateTime___get_unixMillisDouble__impl__3dzlsc(this.get_utc_6lrfzx_k$()) === _DateTime___get_unixMillisDouble__impl__3dzlsc(other.get_utc_6lrfzx_k$());
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(DateTimeTz).compareTo_33io35_k$ = function (other) {
    return compareTo(_DateTime___get_unixMillis__impl__ofxkzp(this.get_utc_6lrfzx_k$()), _DateTime___get_unixMillis__impl__ofxkzp(other.get_utc_6lrfzx_k$()));
  };
  protoOf(DateTimeTz).compareTo_hpufkf_k$ = function (other) {
    return this.compareTo_33io35_k$(other instanceof DateTimeTz ? other : THROW_CCE());
  };
  protoOf(DateTimeTz).format_40gall_k$ = function (format) {
    return format.format_tfqdjs_k$(this);
  };
  protoOf(DateTimeTz).format_rd602l_k$ = function (format) {
    return Companion_getInstance_2().invoke_lt562m_k$(format).format_tfqdjs_k$(this);
  };
  protoOf(DateTimeTz).toString_97qgce_k$ = function (format) {
    return format.format_tfqdjs_k$(this);
  };
  protoOf(DateTimeTz).toString_ly31pu_k$ = function (format) {
    return Companion_getInstance_2().invoke_lt562m_k$(format).format_tfqdjs_k$(this);
  };
  protoOf(DateTimeTz).toStringDefault_fhokdx_k$ = function () {
    return Companion_getInstance_2().get_DEFAULT_FORMAT_t79cy_k$().format_tfqdjs_k$(this);
  };
  protoOf(DateTimeTz).toString = function () {
    return 'DateTimeTz(' + DateTime__toString_impl_cnueje_1(this.adjusted_1) + ', ' + TimezoneOffset__toString_impl_ibx4zv(this.offset_1) + ')';
  };
  function _get_serialVersionUID__fhggm9_3($this) {
    return $this.serialVersionUID_1;
  }
  function _get_BY_INDEX0__rqhld7($this) {
    return $this.BY_INDEX0__1;
  }
  var DayOfWeek_Sunday_instance;
  var DayOfWeek_Monday_instance;
  var DayOfWeek_Tuesday_instance;
  var DayOfWeek_Wednesday_instance;
  var DayOfWeek_Thursday_instance;
  var DayOfWeek_Friday_instance;
  var DayOfWeek_Saturday_instance;
  function Companion_5() {
    Companion_instance_5 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.Count_1 = 7;
    this.BY_INDEX0__1 = values_0();
  }
  protoOf(Companion_5).get_Count_i890hy_k$ = function () {
    return this.Count_1;
  };
  protoOf(Companion_5).get_c1px32_k$ = function (index0) {
    return this.BY_INDEX0__1[umod(index0, 7)];
  };
  protoOf(Companion_5).get0_4ezctb_k$ = function (index0, locale) {
    return Companion_getInstance_7().get_c1px32_k$(index0 + locale.get_firstDayOfWeek_t16080_k$().index0__1 | 0);
  };
  protoOf(Companion_5).get0$default_2zcty6_k$ = function (index0, locale, $super) {
    locale = locale === VOID ? Companion_getInstance_9().get_default_qtagd4_k$() : locale;
    return $super === VOID ? this.get0_4ezctb_k$(index0, locale) : $super.get0_4ezctb_k$.call(this, index0, locale);
  };
  protoOf(Companion_5).get1_4ngsao_k$ = function (index1, locale) {
    return this.get0_4ezctb_k$(umod(index1 - 1 | 0, 7), locale);
  };
  protoOf(Companion_5).get1$default_7yjbr7_k$ = function (index1, locale, $super) {
    locale = locale === VOID ? Companion_getInstance_9().get_default_qtagd4_k$() : locale;
    return $super === VOID ? this.get1_4ngsao_k$(index1, locale) : $super.get1_4ngsao_k$.call(this, index1, locale);
  };
  protoOf(Companion_5).firstDayOfWeek_swvtqe_k$ = function (locale) {
    return locale.get_firstDayOfWeek_t16080_k$();
  };
  protoOf(Companion_5).firstDayOfWeek$default_qqkz1r_k$ = function (locale, $super) {
    locale = locale === VOID ? Companion_getInstance_9().get_default_qtagd4_k$() : locale;
    return $super === VOID ? this.firstDayOfWeek_swvtqe_k$(locale) : $super.firstDayOfWeek_swvtqe_k$.call(this, locale);
  };
  protoOf(Companion_5).comparator_p0n6ih_k$ = function (locale) {
    return locale.get_daysOfWeekComparator_ocl673_k$();
  };
  protoOf(Companion_5).comparator$default_w4339s_k$ = function (locale, $super) {
    locale = locale === VOID ? Companion_getInstance_9().get_default_qtagd4_k$() : locale;
    return $super === VOID ? this.comparator_p0n6ih_k$(locale) : $super.comparator_p0n6ih_k$.call(this, locale);
  };
  var Companion_instance_5;
  function Companion_getInstance_7() {
    DayOfWeek_initEntries();
    if (Companion_instance_5 == null)
      new Companion_5();
    return Companion_instance_5;
  }
  function values_0() {
    return [DayOfWeek_Sunday_getInstance(), DayOfWeek_Monday_getInstance(), DayOfWeek_Tuesday_getInstance(), DayOfWeek_Wednesday_getInstance(), DayOfWeek_Thursday_getInstance(), DayOfWeek_Friday_getInstance(), DayOfWeek_Saturday_getInstance()];
  }
  function valueOf_0(value) {
    switch (value) {
      case 'Sunday':
        return DayOfWeek_Sunday_getInstance();
      case 'Monday':
        return DayOfWeek_Monday_getInstance();
      case 'Tuesday':
        return DayOfWeek_Tuesday_getInstance();
      case 'Wednesday':
        return DayOfWeek_Wednesday_getInstance();
      case 'Thursday':
        return DayOfWeek_Thursday_getInstance();
      case 'Friday':
        return DayOfWeek_Friday_getInstance();
      case 'Saturday':
        return DayOfWeek_Saturday_getInstance();
      default:
        DayOfWeek_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_0() {
    if ($ENTRIES_0 == null)
      $ENTRIES_0 = enumEntries(values_0());
    return $ENTRIES_0;
  }
  var DayOfWeek_entriesInitialized;
  function DayOfWeek_initEntries() {
    if (DayOfWeek_entriesInitialized)
      return Unit_getInstance();
    DayOfWeek_entriesInitialized = true;
    DayOfWeek_Sunday_instance = new DayOfWeek('Sunday', 0, 0);
    DayOfWeek_Monday_instance = new DayOfWeek('Monday', 1, 1);
    DayOfWeek_Tuesday_instance = new DayOfWeek('Tuesday', 2, 2);
    DayOfWeek_Wednesday_instance = new DayOfWeek('Wednesday', 3, 3);
    DayOfWeek_Thursday_instance = new DayOfWeek('Thursday', 4, 4);
    DayOfWeek_Friday_instance = new DayOfWeek('Friday', 5, 5);
    DayOfWeek_Saturday_instance = new DayOfWeek('Saturday', 6, 6);
    Companion_getInstance_7();
  }
  var $ENTRIES_0;
  function DayOfWeek(name, ordinal, index0) {
    Enum.call(this, name, ordinal);
    this.index0__1 = index0;
  }
  protoOf(DayOfWeek).get_index0_etpspz_k$ = function () {
    return this.index0__1;
  };
  protoOf(DayOfWeek).get_index1_etpsq0_k$ = function () {
    return this.index0__1 + 1 | 0;
  };
  protoOf(DayOfWeek).get_index0Sunday_lyznfb_k$ = function () {
    return this.index0__1;
  };
  protoOf(DayOfWeek).get_index1Sunday_ydq4bs_k$ = function () {
    return this.get_index1_etpsq0_k$();
  };
  protoOf(DayOfWeek).get_index0Monday_j1f5hz_k$ = function () {
    return umod(this.index0__1 - 1 | 0, 7);
  };
  protoOf(DayOfWeek).get_index1Monday_xptfq0_k$ = function () {
    return this.get_index0Monday_j1f5hz_k$() + 1 | 0;
  };
  protoOf(DayOfWeek).index0Locale_owk1ad_k$ = function (locale) {
    return umod(this.index0__1 - locale.get_firstDayOfWeek_t16080_k$().index0__1 | 0, 7);
  };
  protoOf(DayOfWeek).index1Locale_wbp5tw_k$ = function (locale) {
    return this.index0Locale_owk1ad_k$(locale) + 1 | 0;
  };
  protoOf(DayOfWeek).isWeekend_net2kg_k$ = function (locale) {
    return locale.isWeekend_covexr_k$(this);
  };
  protoOf(DayOfWeek).isWeekend$default_m3em7l_k$ = function (locale, $super) {
    locale = locale === VOID ? Companion_getInstance_9().get_default_qtagd4_k$() : locale;
    return $super === VOID ? this.isWeekend_net2kg_k$(locale) : $super.isWeekend_net2kg_k$.call(this, locale);
  };
  protoOf(DayOfWeek).get_localName_bf4zrn_k$ = function () {
    return this.localName_ietoqh_k$(Companion_getInstance_9().get_default_qtagd4_k$());
  };
  protoOf(DayOfWeek).localName_ietoqh_k$ = function (locale) {
    return locale.get_daysOfWeek_j1qimt_k$().get_c1px32_k$(this.index0__1);
  };
  protoOf(DayOfWeek).get_localShortName_r1vbp7_k$ = function () {
    return this.localShortName_mk3rof_k$(Companion_getInstance_9().get_default_qtagd4_k$());
  };
  protoOf(DayOfWeek).localShortName_mk3rof_k$ = function (locale) {
    return locale.get_daysOfWeekShort_m93awf_k$().get_c1px32_k$(this.index0__1);
  };
  protoOf(DayOfWeek).get_prev_wosl18_k$ = function () {
    return Companion_getInstance_7().get_c1px32_k$(this.index0__1 - 1 | 0);
  };
  protoOf(DayOfWeek).get_next_wor1vg_k$ = function () {
    return Companion_getInstance_7().get_c1px32_k$(this.index0__1 + 1 | 0);
  };
  protoOf(DayOfWeek).prev_emosed_k$ = function (offset) {
    return Companion_getInstance_7().get_c1px32_k$(this.index0__1 - offset | 0);
  };
  protoOf(DayOfWeek).prev$default_3s68m4_k$ = function (offset, $super) {
    offset = offset === VOID ? 1 : offset;
    return $super === VOID ? this.prev_emosed_k$(offset) : $super.prev_emosed_k$.call(this, offset);
  };
  protoOf(DayOfWeek).next_letk3f_k$ = function (offset) {
    return Companion_getInstance_7().get_c1px32_k$(this.index0__1 + offset | 0);
  };
  protoOf(DayOfWeek).next$default_pqj3vg_k$ = function (offset, $super) {
    offset = offset === VOID ? 1 : offset;
    return $super === VOID ? this.next_letk3f_k$(offset) : $super.next_letk3f_k$.call(this, offset);
  };
  function DayOfWeekWithLocale(dayOfWeek, locale) {
    this.dayOfWeek_1 = dayOfWeek;
    this.locale_1 = locale;
  }
  protoOf(DayOfWeekWithLocale).get_dayOfWeek_3kfgci_k$ = function () {
    return this.dayOfWeek_1;
  };
  protoOf(DayOfWeekWithLocale).get_locale_g9dqar_k$ = function () {
    return this.locale_1;
  };
  protoOf(DayOfWeekWithLocale).get_index0_etpspz_k$ = function () {
    return this.dayOfWeek_1.index0Locale_owk1ad_k$(this.locale_1);
  };
  protoOf(DayOfWeekWithLocale).get_index1_etpsq0_k$ = function () {
    return this.dayOfWeek_1.index1Locale_wbp5tw_k$(this.locale_1);
  };
  protoOf(DayOfWeekWithLocale).compareTo_c5vfsr_k$ = function (other) {
    if (!equals(other.locale_1, this.locale_1)) {
      // Inline function 'kotlin.error' call
      var message = "Can't compare two day of weeks with different locales";
      throw IllegalStateException_init_$Create$(toString(message));
    }
    return this.locale_1.get_daysOfWeekComparator_ocl673_k$().compare(this.dayOfWeek_1, other.dayOfWeek_1);
  };
  protoOf(DayOfWeekWithLocale).compareTo_hpufkf_k$ = function (other) {
    return this.compareTo_c5vfsr_k$(other instanceof DayOfWeekWithLocale ? other : THROW_CCE());
  };
  protoOf(DayOfWeekWithLocale).component1_7eebsc_k$ = function () {
    return this.dayOfWeek_1;
  };
  protoOf(DayOfWeekWithLocale).component2_7eebsb_k$ = function () {
    return this.locale_1;
  };
  protoOf(DayOfWeekWithLocale).copy_e5ld0_k$ = function (dayOfWeek, locale) {
    return new DayOfWeekWithLocale(dayOfWeek, locale);
  };
  protoOf(DayOfWeekWithLocale).copy$default_bl3gwn_k$ = function (dayOfWeek, locale, $super) {
    dayOfWeek = dayOfWeek === VOID ? this.dayOfWeek_1 : dayOfWeek;
    locale = locale === VOID ? this.locale_1 : locale;
    return $super === VOID ? this.copy_e5ld0_k$(dayOfWeek, locale) : $super.copy_e5ld0_k$.call(this, dayOfWeek, locale);
  };
  protoOf(DayOfWeekWithLocale).toString = function () {
    return 'DayOfWeekWithLocale(dayOfWeek=' + toString(this.dayOfWeek_1) + ', locale=' + toString(this.locale_1) + ')';
  };
  protoOf(DayOfWeekWithLocale).hashCode = function () {
    var result = this.dayOfWeek_1.hashCode();
    result = imul(result, 31) + hashCode(this.locale_1) | 0;
    return result;
  };
  protoOf(DayOfWeekWithLocale).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof DayOfWeekWithLocale))
      return false;
    var tmp0_other_with_cast = other instanceof DayOfWeekWithLocale ? other : THROW_CCE();
    if (!this.dayOfWeek_1.equals(tmp0_other_with_cast.dayOfWeek_1))
      return false;
    if (!equals(this.locale_1, tmp0_other_with_cast.locale_1))
      return false;
    return true;
  };
  function DayOfWeek_Sunday_getInstance() {
    DayOfWeek_initEntries();
    return DayOfWeek_Sunday_instance;
  }
  function DayOfWeek_Monday_getInstance() {
    DayOfWeek_initEntries();
    return DayOfWeek_Monday_instance;
  }
  function DayOfWeek_Tuesday_getInstance() {
    DayOfWeek_initEntries();
    return DayOfWeek_Tuesday_instance;
  }
  function DayOfWeek_Wednesday_getInstance() {
    DayOfWeek_initEntries();
    return DayOfWeek_Wednesday_instance;
  }
  function DayOfWeek_Thursday_getInstance() {
    DayOfWeek_initEntries();
    return DayOfWeek_Thursday_instance;
  }
  function DayOfWeek_Friday_getInstance() {
    DayOfWeek_initEntries();
    return DayOfWeek_Friday_instance;
  }
  function DayOfWeek_Saturday_getInstance() {
    DayOfWeek_initEntries();
    return DayOfWeek_Saturday_instance;
  }
  function set_KlockLocale_default(_set____db54di) {
    KlockLocale_default = _set____db54di;
  }
  function get_KlockLocale_default() {
    return KlockLocale_default;
  }
  var KlockLocale_default;
  function Companion_6() {
    Companion_instance_6 = this;
    English.call(this);
  }
  var Companion_instance_6;
  function Companion_getInstance_8() {
    if (Companion_instance_6 == null)
      new Companion_6();
    return Companion_instance_6;
  }
  function Companion_7() {
    Companion_instance_7 = this;
  }
  protoOf(Companion_7).get_english_p9a54p_k$ = function () {
    return Companion_getInstance_8();
  };
  protoOf(Companion_7).set_default_hznpu1_k$ = function (value) {
    KlockLocale_default = value;
  };
  protoOf(Companion_7).get_default_qtagd4_k$ = function () {
    var tmp0_elvis_lhs = KlockLocale_default;
    return tmp0_elvis_lhs == null ? Companion_getInstance_8() : tmp0_elvis_lhs;
  };
  protoOf(Companion_7).setTemporarily_8f9cmr_k$ = function (locale, callback) {
    var old = this.get_default_qtagd4_k$();
    this.set_default_hznpu1_k$(locale);
    try {
      return callback();
    }finally {
      this.set_default_hznpu1_k$(old);
    }
  };
  var Companion_instance_7;
  function Companion_getInstance_9() {
    if (Companion_instance_7 == null)
      new Companion_7();
    return Companion_instance_7;
  }
  function English() {
    Companion_getInstance_8();
    KlockLocale.call(this);
  }
  protoOf(English).get_ISO639_1_9ojbfy_k$ = function () {
    return 'en';
  };
  protoOf(English).get_firstDayOfWeek_t16080_k$ = function () {
    return DayOfWeek_Sunday_getInstance();
  };
  protoOf(English).get_daysOfWeek_j1qimt_k$ = function () {
    return listOf(['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']);
  };
  protoOf(English).get_months_gqmrjw_k$ = function () {
    return listOf(['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']);
  };
  protoOf(English).get_formatTimeMedium_lfid36_k$ = function () {
    return this.format_rd602l_k$('h:mm:ss a');
  };
  protoOf(English).get_formatTimeShort_pzlwun_k$ = function () {
    return this.format_rd602l_k$('h:mm a');
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
  function KlockLocale$_get_daysOfWeekComparator_$lambda_pi0bbw(this$0) {
    return function (a, b) {
      return compareTo(a.index0Locale_owk1ad_k$(this$0), b.index0Locale_owk1ad_k$(this$0));
    };
  }
  function KlockLocale() {
    Companion_getInstance_9();
  }
  protoOf(KlockLocale).get_monthsShort_uq3qxs_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.get_months_gqmrjw_k$();
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.time.KlockLocale.<get-monthsShort>.<anonymous>' call
      var tmp$ret$0 = substr(item, 0, 3);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    return destination;
  };
  protoOf(KlockLocale).get_daysOfWeekShort_m93awf_k$ = function () {
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.get_daysOfWeek_j1qimt_k$();
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.time.KlockLocale.<get-daysOfWeekShort>.<anonymous>' call
      var tmp$ret$0 = substr(item, 0, 3);
      destination.add_utx5q5_k$(tmp$ret$0);
    }
    return destination;
  };
  protoOf(KlockLocale).localizedDayOfWeek_egzl3k_k$ = function (dayOfWeek) {
    return new DayOfWeekWithLocale(Companion_getInstance_7().get_c1px32_k$(dayOfWeek.get_index0_etpspz_k$()), this);
  };
  protoOf(KlockLocale).get_daysOfWeekComparator_ocl673_k$ = function () {
    var tmp = KlockLocale$_get_daysOfWeekComparator_$lambda_pi0bbw(this);
    return new sam$kotlin_Comparator$0(tmp);
  };
  protoOf(KlockLocale).get_ordinals_bbxmvp_k$ = function () {
    var tmp = 0;
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_0 = fillArrayVal(Array(32), null);
    while (tmp < 32) {
      var tmp_1 = tmp;
      var tmp_2;
      if (11 <= tmp_1 ? tmp_1 <= 13 : false) {
        tmp_2 = '' + tmp_1 + 'th';
      } else {
        switch (tmp_1 % 10 | 0) {
          case 1:
            tmp_2 = '' + tmp_1 + 'st';
            break;
          case 2:
            tmp_2 = '' + tmp_1 + 'nd';
            break;
          case 3:
            tmp_2 = '' + tmp_1 + 'rd';
            break;
          default:
            tmp_2 = '' + tmp_1 + 'th';
            break;
        }
      }
      tmp_0[tmp_1] = tmp_2;
      tmp = tmp + 1 | 0;
    }
    return tmp_0;
  };
  protoOf(KlockLocale).getOrdinalByDay_heu72y_k$ = function (day, context) {
    return this.get_ordinals_bbxmvp_k$()[day];
  };
  protoOf(KlockLocale).getOrdinalByDay$default_hl9obs_k$ = function (day, context, $super) {
    context = context === VOID ? Companion_getInstance_10().get_Default_goqax4_k$() : context;
    return $super === VOID ? this.getOrdinalByDay_heu72y_k$(day, context) : $super.getOrdinalByDay_heu72y_k$.call(this, day, context);
  };
  protoOf(KlockLocale).getDayByOrdinal_757iy_k$ = function (ordinal) {
    return indexOf(this.get_ordinals_bbxmvp_k$(), ordinal);
  };
  protoOf(KlockLocale).get_h12Marker_7coo6y_k$ = function () {
    return listOf(['am', 'pm']);
  };
  protoOf(KlockLocale).intToString_gj53st_k$ = function (value) {
    return '' + value;
  };
  protoOf(KlockLocale).isWeekend_covexr_k$ = function (dayOfWeek) {
    return dayOfWeek.equals(DayOfWeek_Saturday_getInstance()) ? true : dayOfWeek.equals(DayOfWeek_Sunday_getInstance());
  };
  protoOf(KlockLocale).format_rd602l_k$ = function (str) {
    return new PatternDateFormat(str, this);
  };
  protoOf(KlockLocale).get_formatDateTimeMedium_29thqo_k$ = function () {
    return this.format_rd602l_k$('MMM d, y h:mm:ss a');
  };
  protoOf(KlockLocale).get_formatDateTimeShort_kge0sf_k$ = function () {
    return this.format_rd602l_k$('M/d/yy h:mm a');
  };
  protoOf(KlockLocale).get_formatDateFull_8hh4sj_k$ = function () {
    return this.format_rd602l_k$('EEEE, MMMM d, y');
  };
  protoOf(KlockLocale).get_formatDateLong_8hdf9y_k$ = function () {
    return this.format_rd602l_k$('MMMM d, y');
  };
  protoOf(KlockLocale).get_formatDateMedium_h3yxnn_k$ = function () {
    return this.format_rd602l_k$('MMM d, y');
  };
  protoOf(KlockLocale).get_formatDateShort_l9m6oe_k$ = function () {
    return this.format_rd602l_k$('M/d/yy');
  };
  protoOf(KlockLocale).get_formatTimeMedium_lfid36_k$ = function () {
    return this.format_rd602l_k$('HH:mm:ss');
  };
  protoOf(KlockLocale).get_formatTimeShort_pzlwun_k$ = function () {
    return this.format_rd602l_k$('HH:mm');
  };
  function Companion_8() {
    Companion_instance_8 = this;
    this.Default_1 = new KlockLocaleContext();
  }
  protoOf(Companion_8).get_Default_goqax4_k$ = function () {
    return this.Default_1;
  };
  var Companion_instance_8;
  function Companion_getInstance_10() {
    if (Companion_instance_8 == null)
      new Companion_8();
    return Companion_instance_8;
  }
  function KlockLocaleContext(gender) {
    Companion_getInstance_10();
    gender = gender === VOID ? KlockLocaleGender_Neuter_getInstance() : gender;
    this.gender_1 = gender;
  }
  protoOf(KlockLocaleContext).get_gender_dqurbe_k$ = function () {
    return this.gender_1;
  };
  protoOf(KlockLocaleContext).component1_7eebsc_k$ = function () {
    return this.gender_1;
  };
  protoOf(KlockLocaleContext).copy_by9h0p_k$ = function (gender) {
    return new KlockLocaleContext(gender);
  };
  protoOf(KlockLocaleContext).copy$default_yu8rd4_k$ = function (gender, $super) {
    gender = gender === VOID ? this.gender_1 : gender;
    return $super === VOID ? this.copy_by9h0p_k$(gender) : $super.copy_by9h0p_k$.call(this, gender);
  };
  protoOf(KlockLocaleContext).toString = function () {
    return 'KlockLocaleContext(gender=' + this.gender_1.toString() + ')';
  };
  protoOf(KlockLocaleContext).hashCode = function () {
    return this.gender_1.hashCode();
  };
  protoOf(KlockLocaleContext).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof KlockLocaleContext))
      return false;
    var tmp0_other_with_cast = other instanceof KlockLocaleContext ? other : THROW_CCE();
    if (!this.gender_1.equals(tmp0_other_with_cast.gender_1))
      return false;
    return true;
  };
  var KlockLocaleGender_Neuter_instance;
  var KlockLocaleGender_Masculine_instance;
  function values_1() {
    return [KlockLocaleGender_Neuter_getInstance(), KlockLocaleGender_Masculine_getInstance()];
  }
  function valueOf_1(value) {
    switch (value) {
      case 'Neuter':
        return KlockLocaleGender_Neuter_getInstance();
      case 'Masculine':
        return KlockLocaleGender_Masculine_getInstance();
      default:
        KlockLocaleGender_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_1() {
    if ($ENTRIES_1 == null)
      $ENTRIES_1 = enumEntries(values_1());
    return $ENTRIES_1;
  }
  var KlockLocaleGender_entriesInitialized;
  function KlockLocaleGender_initEntries() {
    if (KlockLocaleGender_entriesInitialized)
      return Unit_getInstance();
    KlockLocaleGender_entriesInitialized = true;
    KlockLocaleGender_Neuter_instance = new KlockLocaleGender('Neuter', 0);
    KlockLocaleGender_Masculine_instance = new KlockLocaleGender('Masculine', 1);
  }
  var $ENTRIES_1;
  function KlockLocaleGender(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function KlockLocaleGender_Neuter_getInstance() {
    KlockLocaleGender_initEntries();
    return KlockLocaleGender_Neuter_instance;
  }
  function KlockLocaleGender_Masculine_getInstance() {
    KlockLocaleGender_initEntries();
    return KlockLocaleGender_Masculine_instance;
  }
  function _get_serialVersionUID__fhggm9_4($this) {
    return $this.serialVersionUID_1;
  }
  function _get_BY_INDEX0__rqhld7_0($this) {
    return $this.BY_INDEX0__1;
  }
  function YEAR_DAYS($this, isLeap) {
    return isLeap ? $this.YEAR_DAYS_LEAP_1 : $this.YEAR_DAYS_COMMON_1;
  }
  function _get_YEAR_DAYS_LEAP__pt4pvn($this) {
    return $this.YEAR_DAYS_LEAP_1;
  }
  function _get_YEAR_DAYS_COMMON__2104d6($this) {
    return $this.YEAR_DAYS_COMMON_1;
  }
  function generateDaysToStart($this, leap) {
    var total = 0;
    var tmp = 0;
    var tmp_0 = new Int32Array(13);
    while (tmp < 13) {
      var tmp_1 = tmp;
      total = total + (tmp_1 === 0 ? 0 : Companion_getInstance_11().BY_INDEX0__1[tmp_1 - 1 | 0].days_tlk1ta_k$(leap)) | 0;
      tmp_0[tmp_1] = total;
      tmp = tmp + 1 | 0;
    }
    return tmp_0;
  }
  var Month_January_instance;
  var Month_February_instance;
  var Month_March_instance;
  var Month_April_instance;
  var Month_May_instance;
  var Month_June_instance;
  var Month_July_instance;
  var Month_August_instance;
  var Month_September_instance;
  var Month_October_instance;
  var Month_November_instance;
  var Month_December_instance;
  function Companion_9() {
    Companion_instance_9 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.Count_1 = 12;
    this.BY_INDEX0__1 = values_2();
    this.YEAR_DAYS_LEAP_1 = generateDaysToStart(this, true);
    this.YEAR_DAYS_COMMON_1 = generateDaysToStart(this, false);
  }
  protoOf(Companion_9).get_Count_i890hy_k$ = function () {
    return this.Count_1;
  };
  protoOf(Companion_9).invoke_dlyhwg_k$ = function (index1) {
    return this.adjusted_urrcuu_k$(index1);
  };
  protoOf(Companion_9).get_c1px32_k$ = function (index1) {
    return this.adjusted_urrcuu_k$(index1);
  };
  protoOf(Companion_9).adjusted_urrcuu_k$ = function (index1) {
    return this.BY_INDEX0__1[umod(index1 - 1 | 0, 12)];
  };
  protoOf(Companion_9).checked_99ekhb_k$ = function (index1) {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.time.Companion.checked.<anonymous>' call
    if (!(1 <= index1 ? index1 <= 12 : false))
      throw new DateException('Month ' + index1 + ' not in 1..12');
    return this.BY_INDEX0__1[index1 - 1 | 0];
  };
  protoOf(Companion_9).fromDayOfYear_p5oq19_k$ = function (dayOfYear, leap) {
    var days = YEAR_DAYS(this, leap);
    var day0 = dayOfYear - 1 | 0;
    var guess = day0 / 32 | 0;
    var tmp;
    if (0 <= guess ? guess <= 11 : false) {
      var containsLower = days[guess];
      tmp = day0 < days[guess + 1 | 0] ? containsLower <= day0 : false;
    } else {
      tmp = false;
    }
    if (tmp)
      return Companion_getInstance_11().get_c1px32_k$(guess + 1 | 0);
    var tmp_0;
    if (0 <= guess ? guess <= 10 : false) {
      var containsLower_0 = days[guess + 1 | 0];
      tmp_0 = day0 < days[guess + 2 | 0] ? containsLower_0 <= day0 : false;
    } else {
      tmp_0 = false;
    }
    if (tmp_0)
      return Companion_getInstance_11().get_c1px32_k$(guess + 2 | 0);
    return null;
  };
  protoOf(Companion_9).fromDayOfYear_expb6j_k$ = function (dayOfYear, year) {
    return this.fromDayOfYear_p5oq19_k$(dayOfYear, _Year___get_isLeap__impl__kmyeoz(year));
  };
  var Companion_instance_9;
  function Companion_getInstance_11() {
    Month_initEntries();
    if (Companion_instance_9 == null)
      new Companion_9();
    return Companion_instance_9;
  }
  function values_2() {
    return [Month_January_getInstance(), Month_February_getInstance(), Month_March_getInstance(), Month_April_getInstance(), Month_May_getInstance(), Month_June_getInstance(), Month_July_getInstance(), Month_August_getInstance(), Month_September_getInstance(), Month_October_getInstance(), Month_November_getInstance(), Month_December_getInstance()];
  }
  function valueOf_2(value) {
    switch (value) {
      case 'January':
        return Month_January_getInstance();
      case 'February':
        return Month_February_getInstance();
      case 'March':
        return Month_March_getInstance();
      case 'April':
        return Month_April_getInstance();
      case 'May':
        return Month_May_getInstance();
      case 'June':
        return Month_June_getInstance();
      case 'July':
        return Month_July_getInstance();
      case 'August':
        return Month_August_getInstance();
      case 'September':
        return Month_September_getInstance();
      case 'October':
        return Month_October_getInstance();
      case 'November':
        return Month_November_getInstance();
      case 'December':
        return Month_December_getInstance();
      default:
        Month_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_2() {
    if ($ENTRIES_2 == null)
      $ENTRIES_2 = enumEntries(values_2());
    return $ENTRIES_2;
  }
  var Month_entriesInitialized;
  function Month_initEntries() {
    if (Month_entriesInitialized)
      return Unit_getInstance();
    Month_entriesInitialized = true;
    Month_January_instance = new Month('January', 0, 1, 31);
    Month_February_instance = new Month('February', 1, 2, 28, 29);
    Month_March_instance = new Month('March', 2, 3, 31);
    Month_April_instance = new Month('April', 3, 4, 30);
    Month_May_instance = new Month('May', 4, 5, 31);
    Month_June_instance = new Month('June', 5, 6, 30);
    Month_July_instance = new Month('July', 6, 7, 31);
    Month_August_instance = new Month('August', 7, 8, 31);
    Month_September_instance = new Month('September', 8, 9, 30);
    Month_October_instance = new Month('October', 9, 10, 31);
    Month_November_instance = new Month('November', 10, 11, 30);
    Month_December_instance = new Month('December', 11, 12, 31);
    Companion_getInstance_11();
  }
  var $ENTRIES_2;
  function Month(name, ordinal, index1, daysCommon, daysLeap) {
    daysLeap = daysLeap === VOID ? daysCommon : daysLeap;
    Enum.call(this, name, ordinal);
    this.index1__1 = index1;
    this.daysCommon_1 = daysCommon;
    this.daysLeap_1 = daysLeap;
  }
  protoOf(Month).get_index1_etpsq0_k$ = function () {
    return this.index1__1;
  };
  protoOf(Month).get_daysCommon_okxm51_k$ = function () {
    return this.daysCommon_1;
  };
  protoOf(Month).get_daysLeap_f74tk_k$ = function () {
    return this.daysLeap_1;
  };
  protoOf(Month).get_index0_etpspz_k$ = function () {
    return this.index1__1 - 1 | 0;
  };
  protoOf(Month).days_tlk1ta_k$ = function (leap) {
    return leap ? this.daysLeap_1 : this.daysCommon_1;
  };
  protoOf(Month).days_101u5b_k$ = function (year) {
    return this.days_tlk1ta_k$(_Year___get_isLeap__impl__kmyeoz(_Year___init__impl__2jvef0(year)));
  };
  protoOf(Month).days_eg9z5s_k$ = function (year) {
    return this.days_tlk1ta_k$(_Year___get_isLeap__impl__kmyeoz(year));
  };
  protoOf(Month).daysToStart_kxj591_k$ = function (leap) {
    return YEAR_DAYS(Companion_getInstance_11(), leap)[this.get_index0_etpspz_k$()];
  };
  protoOf(Month).daysToStart_x9v8c8_k$ = function (year) {
    return this.daysToStart_kxj591_k$(_Year___get_isLeap__impl__kmyeoz(_Year___init__impl__2jvef0(year)));
  };
  protoOf(Month).daysToStart_hdhiiv_k$ = function (year) {
    return this.daysToStart_kxj591_k$(_Year___get_isLeap__impl__kmyeoz(year));
  };
  protoOf(Month).daysToEnd_sjlksc_k$ = function (leap) {
    return YEAR_DAYS(Companion_getInstance_11(), leap)[this.index1__1];
  };
  protoOf(Month).daysToEnd_4bt8fj_k$ = function (year) {
    return this.daysToEnd_sjlksc_k$(_Year___get_isLeap__impl__kmyeoz(_Year___init__impl__2jvef0(year)));
  };
  protoOf(Month).daysToEnd_xa20r2_k$ = function (year) {
    return this.daysToEnd_sjlksc_k$(_Year___get_isLeap__impl__kmyeoz(year));
  };
  protoOf(Month).get_previous_i5svy8_k$ = function () {
    return this.minus_vfk7ag_k$(1);
  };
  protoOf(Month).get_next_wor1vg_k$ = function () {
    return this.plus_gv6ohq_k$(1);
  };
  protoOf(Month).plus_gv6ohq_k$ = function (delta) {
    return Companion_getInstance_11().get_c1px32_k$(this.index1__1 + delta | 0);
  };
  protoOf(Month).minus_vfk7ag_k$ = function (delta) {
    return Companion_getInstance_11().get_c1px32_k$(this.index1__1 - delta | 0);
  };
  protoOf(Month).minus_wb88fy_k$ = function (other) {
    return abs(this.get_index0_etpspz_k$() - other.get_index0_etpspz_k$() | 0);
  };
  protoOf(Month).get_localName_bf4zrn_k$ = function () {
    return this.localName_ietoqh_k$(Companion_getInstance_9().get_default_qtagd4_k$());
  };
  protoOf(Month).localName_ietoqh_k$ = function (locale) {
    return locale.get_months_gqmrjw_k$().get_c1px32_k$(this.get_index0_etpspz_k$());
  };
  protoOf(Month).get_localShortName_r1vbp7_k$ = function () {
    return this.localShortName_mk3rof_k$(Companion_getInstance_9().get_default_qtagd4_k$());
  };
  protoOf(Month).localShortName_mk3rof_k$ = function (locale) {
    return locale.get_monthsShort_uq3qxs_k$().get_c1px32_k$(this.get_index0_etpspz_k$());
  };
  function Month_January_getInstance() {
    Month_initEntries();
    return Month_January_instance;
  }
  function Month_February_getInstance() {
    Month_initEntries();
    return Month_February_instance;
  }
  function Month_March_getInstance() {
    Month_initEntries();
    return Month_March_instance;
  }
  function Month_April_getInstance() {
    Month_initEntries();
    return Month_April_instance;
  }
  function Month_May_getInstance() {
    Month_initEntries();
    return Month_May_instance;
  }
  function Month_June_getInstance() {
    Month_initEntries();
    return Month_June_instance;
  }
  function Month_July_getInstance() {
    Month_initEntries();
    return Month_July_instance;
  }
  function Month_August_getInstance() {
    Month_initEntries();
    return Month_August_instance;
  }
  function Month_September_getInstance() {
    Month_initEntries();
    return Month_September_instance;
  }
  function Month_October_getInstance() {
    Month_initEntries();
    return Month_October_instance;
  }
  function Month_November_getInstance() {
    Month_initEntries();
    return Month_November_instance;
  }
  function Month_December_getInstance() {
    Month_initEntries();
    return Month_December_instance;
  }
  function _get_serialVersionUID__fhggm9_5($this) {
    return $this.serialVersionUID_1;
  }
  function _MonthSpan___init__impl__5r6mxb(totalMonths) {
    return totalMonths;
  }
  function _MonthSpan___get_totalMonths__impl__8ojvr1($this) {
    return $this;
  }
  function Companion_10() {
    Companion_instance_10 = this;
    this.serialVersionUID_1 = new Long(1, 0);
  }
  var Companion_instance_10;
  function Companion_getInstance_12() {
    if (Companion_instance_10 == null)
      new Companion_10();
    return Companion_instance_10;
  }
  function MonthSpan__unaryMinus_impl_3wgm0y($this) {
    return _MonthSpan___init__impl__5r6mxb(-_MonthSpan___get_totalMonths__impl__8ojvr1($this) | 0);
  }
  function MonthSpan__unaryPlus_impl_gw1hsi($this) {
    return _MonthSpan___init__impl__5r6mxb(+_MonthSpan___get_totalMonths__impl__8ojvr1($this));
  }
  function MonthSpan__plus_impl_mt00vr($this, other) {
    return new DateTimeSpan($this, other);
  }
  function MonthSpan__plus_impl_mt00vr_0($this, other) {
    return _MonthSpan___init__impl__5r6mxb(_MonthSpan___get_totalMonths__impl__8ojvr1($this) + _MonthSpan___get_totalMonths__impl__8ojvr1(other) | 0);
  }
  function MonthSpan__plus_impl_mt00vr_1($this, other) {
    return new DateTimeSpan(MonthSpan__plus_impl_mt00vr_0(other.get_monthSpan_o3llz8_k$(), $this), other.get_timeSpan_7vf9yi_k$());
  }
  function MonthSpan__minus_impl_xm2c5j($this, other) {
    return MonthSpan__plus_impl_mt00vr($this, Duration__unaryMinus_impl_x2k1y0(other));
  }
  function MonthSpan__minus_impl_xm2c5j_0($this, other) {
    return MonthSpan__plus_impl_mt00vr_0($this, MonthSpan__unaryMinus_impl_3wgm0y(other));
  }
  function MonthSpan__minus_impl_xm2c5j_1($this, other) {
    return MonthSpan__plus_impl_mt00vr_1($this, other.unaryMinus_6uz0qp_k$());
  }
  function MonthSpan__times_impl_vbjrw1($this, times) {
    return _MonthSpan___init__impl__5r6mxb(numberToInt(_MonthSpan___get_totalMonths__impl__8ojvr1($this) * times));
  }
  function MonthSpan__times_impl_vbjrw1_0($this, times) {
    return MonthSpan__times_impl_vbjrw1($this, times);
  }
  function MonthSpan__times_impl_vbjrw1_1($this, times) {
    return MonthSpan__times_impl_vbjrw1($this, times);
  }
  function MonthSpan__div_impl_mb1ypm($this, times) {
    return _MonthSpan___init__impl__5r6mxb(numberToInt(_MonthSpan___get_totalMonths__impl__8ojvr1($this) / times));
  }
  function MonthSpan__div_impl_mb1ypm_0($this, times) {
    return MonthSpan__div_impl_mb1ypm($this, times);
  }
  function MonthSpan__div_impl_mb1ypm_1($this, times) {
    return MonthSpan__div_impl_mb1ypm($this, times);
  }
  function MonthSpan__compareTo_impl_82y3av($this, other) {
    return compareTo(_MonthSpan___get_totalMonths__impl__8ojvr1($this), _MonthSpan___get_totalMonths__impl__8ojvr1(other));
  }
  function MonthSpan__compareTo_impl_82y3av_0($this, other) {
    return MonthSpan__compareTo_impl_82y3av($this.totalMonths_1, other instanceof MonthSpan ? other.totalMonths_1 : THROW_CCE());
  }
  function MonthSpan__toString_impl_ose7lj($this) {
    // Inline function 'kotlin.collections.arrayListOf' call
    var list = ArrayList_init_$Create$();
    if (!(get_years($this) === 0)) {
      list.add_utx5q5_k$('' + get_years($this) + 'Y');
    }
    if (!(get_months_0($this) === 0) ? true : get_years($this) === 0) {
      list.add_utx5q5_k$('' + get_months_0($this) + 'M');
    }
    return joinToString(list, ' ');
  }
  function MonthSpan__hashCode_impl_318aso($this) {
    return $this;
  }
  function MonthSpan__equals_impl_snqpdo($this, other) {
    if (!(other instanceof MonthSpan))
      return false;
    if (!($this === (other instanceof MonthSpan ? other.totalMonths_1 : THROW_CCE())))
      return false;
    return true;
  }
  function MonthSpan(totalMonths) {
    Companion_getInstance_12();
    this.totalMonths_1 = totalMonths;
  }
  protoOf(MonthSpan).compareTo_8tzjdt_k$ = function (other) {
    return MonthSpan__compareTo_impl_82y3av(this.totalMonths_1, other);
  };
  protoOf(MonthSpan).compareTo_hpufkf_k$ = function (other) {
    return MonthSpan__compareTo_impl_82y3av_0(this, other);
  };
  protoOf(MonthSpan).toString = function () {
    return MonthSpan__toString_impl_ose7lj(this.totalMonths_1);
  };
  protoOf(MonthSpan).hashCode = function () {
    return MonthSpan__hashCode_impl_318aso(this.totalMonths_1);
  };
  protoOf(MonthSpan).equals = function (other) {
    return MonthSpan__equals_impl_snqpdo(this.totalMonths_1, other);
  };
  function get_months(_this__u8e3s4) {
    return _MonthSpan___init__impl__5r6mxb(_this__u8e3s4);
  }
  function get_months_0(_this__u8e3s4) {
    return _MonthSpan___get_totalMonths__impl__8ojvr1(_this__u8e3s4) % 12 | 0;
  }
  function get_years(_this__u8e3s4) {
    return _MonthSpan___get_totalMonths__impl__8ojvr1(_this__u8e3s4) / 12 | 0;
  }
  function get_years_0(_this__u8e3s4) {
    return _MonthSpan___init__impl__5r6mxb(imul(12, _this__u8e3s4));
  }
  function get_totalYears(_this__u8e3s4) {
    return _MonthSpan___get_totalMonths__impl__8ojvr1(_this__u8e3s4) / 12.0;
  }
  function _get_serialVersionUID__fhggm9_6($this) {
    return $this.serialVersionUID_1;
  }
  function _get_serialVersionUID__fhggm9_7($this) {
    return $this.serialVersionUID_1;
  }
  function Companion_11() {
    Companion_instance_11 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.DEFAULT_1 = new Options(false);
    this.WITH_OPTIONAL_1 = new Options(true);
  }
  protoOf(Companion_11).get_DEFAULT_wccqmg_k$ = function () {
    return this.DEFAULT_1;
  };
  protoOf(Companion_11).get_WITH_OPTIONAL_1fkui8_k$ = function () {
    return this.WITH_OPTIONAL_1;
  };
  var Companion_instance_11;
  function Companion_getInstance_13() {
    if (Companion_instance_11 == null)
      new Companion_11();
    return Companion_instance_11;
  }
  function Companion_12() {
    Companion_instance_12 = this;
    this.serialVersionUID_1 = new Long(1, 0);
  }
  var Companion_instance_12;
  function Companion_getInstance_14() {
    if (Companion_instance_12 == null)
      new Companion_12();
    return Companion_instance_12;
  }
  function Options(optionalSupport) {
    Companion_getInstance_13();
    optionalSupport = optionalSupport === VOID ? false : optionalSupport;
    this.optionalSupport_1 = optionalSupport;
  }
  protoOf(Options).get_optionalSupport_3v38me_k$ = function () {
    return this.optionalSupport_1;
  };
  protoOf(Options).component1_7eebsc_k$ = function () {
    return this.optionalSupport_1;
  };
  protoOf(Options).copy_o18wmo_k$ = function (optionalSupport) {
    return new Options(optionalSupport);
  };
  protoOf(Options).copy$default_7uki6o_k$ = function (optionalSupport, $super) {
    optionalSupport = optionalSupport === VOID ? this.optionalSupport_1 : optionalSupport;
    return $super === VOID ? this.copy_o18wmo_k$(optionalSupport) : $super.copy_o18wmo_k$.call(this, optionalSupport);
  };
  protoOf(Options).toString = function () {
    return 'Options(optionalSupport=' + this.optionalSupport_1 + ')';
  };
  protoOf(Options).hashCode = function () {
    return getBooleanHashCode(this.optionalSupport_1);
  };
  protoOf(Options).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Options))
      return false;
    var tmp0_other_with_cast = other instanceof Options ? other : THROW_CCE();
    if (!(this.optionalSupport_1 === tmp0_other_with_cast.optionalSupport_1))
      return false;
    return true;
  };
  function _get_openOffsets__f0vio5($this) {
    return $this.openOffsets_1;
  }
  function _get_closeOffsets__3wdqub($this) {
    return $this.closeOffsets_1;
  }
  function PatternDateFormat(format, locale, tzNames, options) {
    Companion_getInstance_14();
    locale = locale === VOID ? null : locale;
    tzNames = tzNames === VOID ? Companion_getInstance_19().get_DEFAULT_wccqmg_k$() : tzNames;
    options = options === VOID ? Companion_getInstance_13().DEFAULT_1 : options;
    this.format_1 = format;
    this.locale_1 = locale;
    this.tzNames_1 = tzNames;
    this.options_1 = options;
    this.openOffsets_1 = LinkedHashMap_init_$Create$();
    this.closeOffsets_1 = LinkedHashMap_init_$Create$();
    var tmp = this;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_0 = ArrayList_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.time.PatternDateFormat.chunks.<anonymous>' call
    var s = new MicroStrReader(this.format_1);
    $l$loop_1: while (s.get_hasMore_csdhd2_k$()) {
      if (s.peekChar_2c2lw3_k$() === _Char___init__impl__6a9atx(39)) {
        // Inline function 'korlibs.time.internal.MicroStrReader.readChunk' call
        var start = s.get_offset_hjmqak_k$();
        // Inline function 'korlibs.time.PatternDateFormat.chunks.<anonymous>.<anonymous>' call
        s.tryRead_2h17j0_k$(_Char___init__impl__6a9atx(39));
        while (s.get_hasMore_csdhd2_k$() ? !(s.readChar_vl5a7m_k$() === _Char___init__impl__6a9atx(39)) : false)
        ;
        var end = s.get_offset_hjmqak_k$();
        // Inline function 'kotlin.text.substring' call
        // Inline function 'kotlin.js.asDynamic' call
        var escapedChunk = s.get_str_18ivy0_k$().substring(start, end);
        this_0.add_utx5q5_k$(escapedChunk);
        continue $l$loop_1;
      }
      if (this.options_1.optionalSupport_1) {
        var offset = this_0.get_size_woubt6_k$();
        if (s.tryRead_2h17j0_k$(_Char___init__impl__6a9atx(91))) {
          increment(this.openOffsets_1, offset);
          continue $l$loop_1;
        }
        if (s.tryRead_2h17j0_k$(_Char___init__impl__6a9atx(93))) {
          increment(this.closeOffsets_1, offset - 1 | 0);
          continue $l$loop_1;
        }
      }
      var tmp0_elvis_lhs = s.tryReadOrNull_rx9ydb_k$('do');
      this_0.add_utx5q5_k$(tmp0_elvis_lhs == null ? readRepeatedChar(s) : tmp0_elvis_lhs);
    }
    tmp.chunks_1 = toList(this_0);
    var tmp_0 = this;
    // Inline function 'kotlin.collections.map' call
    var this_1 = this.chunks_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_1, 10));
    var tmp0_iterator = this_1.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.time.PatternDateFormat.regexChunks.<anonymous>' call
      switch (item) {
        case 'E':
        case 'EE':
        case 'EEE':
        case 'EEEE':
        case 'EEEEE':
        case 'EEEEEE':
          var tmp$ret$5 = '(\\w+)';
          break;
        case 'z':
        case 'zzz':
          tmp$ret$5 = '([\\w\\s\\-\\+:]+)';
          break;
        case 'do':
          tmp$ret$5 = '(\\d{1,2}\\w+)';
          break;
        case 'd':
          tmp$ret$5 = '(\\d{1,2})';
          break;
        case 'dd':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 'M':
          tmp$ret$5 = '(\\d{1,5})';
          break;
        case 'MM':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 'MMM':
        case 'MMMM':
        case 'MMMMM':
          tmp$ret$5 = '(\\w+)';
          break;
        case 'y':
          tmp$ret$5 = '(\\d{1,5})';
          break;
        case 'yy':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 'yyy':
          tmp$ret$5 = '(\\d{3})';
          break;
        case 'yyyy':
          tmp$ret$5 = '(\\d{4})';
          break;
        case 'YYYY':
          tmp$ret$5 = '(\\d{4})';
          break;
        case 'H':
        case 'k':
          tmp$ret$5 = '(\\d{1,2})';
          break;
        case 'HH':
        case 'kk':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 'h':
        case 'K':
          tmp$ret$5 = '(\\d{1,2})';
          break;
        case 'hh':
        case 'KK':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 'm':
          tmp$ret$5 = '(\\d{1,2})';
          break;
        case 'mm':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 's':
          tmp$ret$5 = '(\\d{1,2})';
          break;
        case 'ss':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 'S':
          tmp$ret$5 = '(\\d{1,9})';
          break;
        case 'SS':
          tmp$ret$5 = '(\\d{2})';
          break;
        case 'SSS':
          tmp$ret$5 = '(\\d{3})';
          break;
        case 'SSSS':
          tmp$ret$5 = '(\\d{4})';
          break;
        case 'SSSSS':
          tmp$ret$5 = '(\\d{5})';
          break;
        case 'SSSSSS':
          tmp$ret$5 = '(\\d{6})';
          break;
        case 'SSSSSSS':
          tmp$ret$5 = '(\\d{7})';
          break;
        case 'SSSSSSSS':
          tmp$ret$5 = '(\\d{8})';
          break;
        case 'SSSSSSSSS':
          tmp$ret$5 = '(\\d{9})';
          break;
        case 'X':
        case 'XX':
        case 'XXX':
        case 'x':
        case 'xx':
        case 'xxx':
        case 'Z':
          tmp$ret$5 = '([\\w:\\+\\-]+)';
          break;
        case 'a':
          tmp$ret$5 = '(\\w+)';
          break;
        case ' ':
          tmp$ret$5 = '(\\s+)';
          break;
        default:
          tmp$ret$5 = startsWith(item, _Char___init__impl__6a9atx(39)) ? '(' + Companion_getInstance_0().escape_984trb_k$(substr(item, 1, item.length - 2 | 0)) + ')' : '(' + Companion_getInstance_0().escape_984trb_k$(item) + ')';
          break;
      }
      destination.add_utx5q5_k$(tmp$ret$5);
    }
    tmp_0.regexChunks_1 = destination;
    this.rx2__1 = Regex_init_$Create$('^' + this.matchingRegexString_pan5dx_k$() + '$');
  }
  protoOf(PatternDateFormat).get_format_dfdtds_k$ = function () {
    return this.format_1;
  };
  protoOf(PatternDateFormat).get_locale_g9dqar_k$ = function () {
    return this.locale_1;
  };
  protoOf(PatternDateFormat).get_tzNames_cw2e13_k$ = function () {
    return this.tzNames_1;
  };
  protoOf(PatternDateFormat).get_options_jecmyz_k$ = function () {
    return this.options_1;
  };
  protoOf(PatternDateFormat).get_realLocale_ejo4yp_k$ = function () {
    var tmp0_elvis_lhs = this.locale_1;
    return tmp0_elvis_lhs == null ? Companion_getInstance_9().get_default_qtagd4_k$() : tmp0_elvis_lhs;
  };
  protoOf(PatternDateFormat).withLocale_1h53jw_k$ = function (locale) {
    return this.copy$default_5ppx6c_k$(VOID, locale);
  };
  protoOf(PatternDateFormat).withTimezoneNames_bdipw0_k$ = function (tzNames) {
    return this.copy$default_5ppx6c_k$(VOID, VOID, this.tzNames_1.plus_4pfgan_k$(tzNames));
  };
  protoOf(PatternDateFormat).withOptions_lfrt93_k$ = function (options) {
    return this.copy$default_5ppx6c_k$(VOID, VOID, VOID, options);
  };
  protoOf(PatternDateFormat).withOptional_umqaii_k$ = function () {
    return this.copy$default_5ppx6c_k$(VOID, VOID, VOID, this.options_1.copy_o18wmo_k$(true));
  };
  protoOf(PatternDateFormat).withNonOptional_s53xw9_k$ = function () {
    return this.copy$default_5ppx6c_k$(VOID, VOID, VOID, this.options_1.copy_o18wmo_k$(false));
  };
  protoOf(PatternDateFormat).get_chunks_xqch8u_k$ = function () {
    return this.chunks_1;
  };
  protoOf(PatternDateFormat).get_regexChunks_1y4y03_k$ = function () {
    return this.regexChunks_1;
  };
  protoOf(PatternDateFormat).matchingRegexString_pan5dx_k$ = function () {
    // Inline function 'kotlin.collections.mapIndexed' call
    var this_0 = this.regexChunks_1;
    // Inline function 'kotlin.collections.mapIndexedTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_0, 10));
    var index = 0;
    var tmp0_iterator = this_0.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.time.PatternDateFormat.matchingRegexString.<anonymous>' call
      var tmp1 = index;
      index = tmp1 + 1 | 0;
      var index_0 = checkIndexOverflow(tmp1);
      var tmp;
      if (this.options_1.optionalSupport_1) {
        // Inline function 'kotlin.collections.getOrElse' call
        var tmp0_elvis_lhs = this.openOffsets_1.get_wei43m_k$(index_0);
        var tmp_0;
        if (tmp0_elvis_lhs == null) {
          // Inline function 'korlibs.time.PatternDateFormat.matchingRegexString.<anonymous>.<anonymous>' call
          tmp_0 = 0;
        } else {
          tmp_0 = tmp0_elvis_lhs;
        }
        var opens = tmp_0;
        // Inline function 'kotlin.collections.getOrElse' call
        var tmp0_elvis_lhs_0 = this.closeOffsets_1.get_wei43m_k$(index_0);
        var tmp_1;
        if (tmp0_elvis_lhs_0 == null) {
          // Inline function 'korlibs.time.PatternDateFormat.matchingRegexString.<anonymous>.<anonymous>' call
          tmp_1 = 0;
        } else {
          tmp_1 = tmp0_elvis_lhs_0;
        }
        var closes = tmp_1;
        // Inline function 'kotlin.text.buildString' call
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'kotlin.apply' call
        var this_1 = StringBuilder_init_$Create$();
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.time.PatternDateFormat.matchingRegexString.<anonymous>.<anonymous>' call
        // Inline function 'kotlin.repeat' call
        // Inline function 'kotlin.contracts.contract' call
        var inductionVariable = 0;
        if (inductionVariable < opens)
          do {
            var index_1 = inductionVariable;
            inductionVariable = inductionVariable + 1 | 0;
            // Inline function 'korlibs.time.PatternDateFormat.matchingRegexString.<anonymous>.<anonymous>.<anonymous>' call
            this_1.append_22ad7x_k$('(?:');
          }
           while (inductionVariable < opens);
        this_1.append_22ad7x_k$(item);
        // Inline function 'kotlin.repeat' call
        // Inline function 'kotlin.contracts.contract' call
        var inductionVariable_0 = 0;
        if (inductionVariable_0 < closes)
          do {
            var index_2 = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            // Inline function 'korlibs.time.PatternDateFormat.matchingRegexString.<anonymous>.<anonymous>.<anonymous>' call
            this_1.append_22ad7x_k$(')?');
          }
           while (inductionVariable_0 < closes);
        tmp = this_1.toString();
      } else {
        tmp = item;
      }
      var tmp$ret$6 = tmp;
      destination.add_utx5q5_k$(tmp$ret$6);
    }
    return joinToString(destination, '');
  };
  protoOf(PatternDateFormat).get_rx2_nhtkki_k$ = function () {
    return this.rx2__1;
  };
  protoOf(PatternDateFormat).format_tfqdjs_k$ = function (dd) {
    var utc = dd.get_local_g3bngq_k$();
    var out = '';
    var _iterator__ex2g4s = this.chunks_1.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var name = _iterator__ex2g4s.next_20eer_k$();
      var nlen = name.length;
      var tmp = out;
      var tmp_0;
      switch (name) {
        case 'E':
        case 'EE':
        case 'EEE':
          tmp_0 = Companion_getInstance_7().get_c1px32_k$(_DateTime___get_dayOfWeek__impl__o3da04(utc).get_index0_etpspz_k$()).localShortName_mk3rof_k$(this.get_realLocale_ejo4yp_k$());
          break;
        case 'EEEE':
        case 'EEEEE':
        case 'EEEEEE':
          tmp_0 = Companion_getInstance_7().get_c1px32_k$(_DateTime___get_dayOfWeek__impl__o3da04(utc).get_index0_etpspz_k$()).localName_ietoqh_k$(this.get_realLocale_ejo4yp_k$());
          break;
        case 'z':
        case 'zzz':
          tmp_0 = _TimezoneOffset___get_timeZone__impl__6q3m9h(dd.get_offset_noi1v9_k$());
          break;
        case 'd':
        case 'dd':
          tmp_0 = padded(_DateTime___get_dayOfMonth__impl__z5062(utc), nlen);
          break;
        case 'do':
          tmp_0 = this.get_realLocale_ejo4yp_k$().getOrdinalByDay$default_hl9obs_k$(_DateTime___get_dayOfMonth__impl__z5062(utc));
          break;
        case 'M':
        case 'MM':
          tmp_0 = padded(_DateTime___get_month1__impl__hf8ase(utc), nlen);
          break;
        case 'MMM':
          tmp_0 = substr(Companion_getInstance_11().get_c1px32_k$(_DateTime___get_month1__impl__hf8ase(utc)).localName_ietoqh_k$(this.get_realLocale_ejo4yp_k$()), 0, 3);
          break;
        case 'MMMM':
          tmp_0 = Companion_getInstance_11().get_c1px32_k$(_DateTime___get_month1__impl__hf8ase(utc)).localName_ietoqh_k$(this.get_realLocale_ejo4yp_k$());
          break;
        case 'MMMMM':
          tmp_0 = substr(Companion_getInstance_11().get_c1px32_k$(_DateTime___get_month1__impl__hf8ase(utc)).localName_ietoqh_k$(this.get_realLocale_ejo4yp_k$()), 0, 1);
          break;
        case 'y':
          tmp_0 = _DateTime___get_yearInt__impl__876eh3(utc);
          break;
        case 'yy':
          tmp_0 = padded(_DateTime___get_yearInt__impl__876eh3(utc) % 100 | 0, 2);
          break;
        case 'yyy':
          tmp_0 = padded(_DateTime___get_yearInt__impl__876eh3(utc) % 1000 | 0, 3);
          break;
        case 'yyyy':
          tmp_0 = padded(_DateTime___get_yearInt__impl__876eh3(utc), 4);
          break;
        case 'YYYY':
          tmp_0 = padded(_DateTime___get_yearInt__impl__876eh3(utc), 4);
          break;
        case 'H':
        case 'HH':
          tmp_0 = padded(mconvertRangeZero(_DateTime___get_hours__impl__gihczo(utc), 24), nlen);
          break;
        case 'k':
        case 'kk':
          tmp_0 = padded(mconvertRangeNonZero(_DateTime___get_hours__impl__gihczo(utc), 24), nlen);
          break;
        case 'h':
        case 'hh':
          tmp_0 = padded(mconvertRangeNonZero(_DateTime___get_hours__impl__gihczo(utc), 12), nlen);
          break;
        case 'K':
        case 'KK':
          tmp_0 = padded(mconvertRangeZero(_DateTime___get_hours__impl__gihczo(utc), 12), nlen);
          break;
        case 'm':
        case 'mm':
          tmp_0 = padded(_DateTime___get_minutes__impl__ua9590(utc), nlen);
          break;
        case 's':
        case 'ss':
          tmp_0 = padded(_DateTime___get_seconds__impl__145pqk(utc), nlen);
          break;
        case 'S':
        case 'SS':
        case 'SSS':
        case 'SSSS':
        case 'SSSSS':
        case 'SSSSSS':
        case 'SSSSSSS':
        case 'SSSSSSSS':
        case 'SSSSSSSSS':
          var milli = _DateTime___get_milliseconds__impl__xmznxd(utc);
          // Inline function 'kotlin.math.log10' call

          var x = _DateTime___get_milliseconds__impl__xmznxd(utc);
          var tmp$ret$0 = log10(x);
          var base10length = numberToInt(tmp$ret$0) + 1 | 0;
          var tmp_1;
          if (base10length > name.length) {
            var tmp_2 = milli;
            // Inline function 'kotlin.math.pow' call
            var n = imul(-1, base10length - name.length | 0);
            var tmp$ret$1 = Math.pow(10.0, n);
            tmp_1 = numberToInt(tmp_2 * tmp$ret$1);
          } else {
            tmp_1 = substr(padded(milli, 3) + '000000', 0, name.length);
          }

          tmp_0 = tmp_1;
          break;
        case 'X':
        case 'XX':
        case 'XXX':
        case 'x':
        case 'xx':
        case 'xxx':
          var tmp_3;
          if (startsWith_0(name, 'X') ? _TimezoneOffset___get_totalMinutesInt__impl__9h3gzq(dd.get_offset_noi1v9_k$()) === 0 : false) {
            tmp_3 = 'Z';
          } else {
            var p = _TimezoneOffset___get_totalMinutesInt__impl__9h3gzq(dd.get_offset_noi1v9_k$()) >= 0 ? '+' : '-';
            // Inline function 'kotlin.math.absoluteValue' call
            var this_0 = _TimezoneOffset___get_totalMinutesInt__impl__9h3gzq(dd.get_offset_noi1v9_k$()) / 60 | 0;
            var hours = abs(this_0);
            // Inline function 'kotlin.math.absoluteValue' call
            var this_1 = _TimezoneOffset___get_totalMinutesInt__impl__9h3gzq(dd.get_offset_noi1v9_k$()) % 60 | 0;
            var minutes = abs(this_1);
            switch (name) {
              case 'X':
              case 'x':
                tmp_3 = p + padded(hours, 2);
                break;
              case 'XX':
              case 'xx':
                tmp_3 = p + padded(hours, 2) + padded(minutes, 2);
                break;
              case 'XXX':
              case 'xxx':
                tmp_3 = p + padded(hours, 2) + ':' + padded(minutes, 2);
                break;
              default:
                tmp_3 = name;
                break;
            }
          }

          tmp_0 = tmp_3;
          break;
        case 'a':
          tmp_0 = this.get_realLocale_ejo4yp_k$().get_h12Marker_7coo6y_k$().get_c1px32_k$(_DateTime___get_hours__impl__gihczo(utc) < 12 ? 0 : 1);
          break;
        default:
          var tmp_4;
          if (startsWith(name, _Char___init__impl__6a9atx(39))) {
            // Inline function 'kotlin.text.substring' call
            var endIndex = name.length - 1 | 0;
            // Inline function 'kotlin.js.asDynamic' call
            tmp_4 = name.substring(1, endIndex);
          } else {
            tmp_4 = name;
          }

          tmp_0 = tmp_4;
          break;
      }
      out = tmp + toString(tmp_0);
    }
    return out;
  };
  protoOf(PatternDateFormat).tryParse_hjzgny_k$ = function (str, doThrow, doAdjust) {
    var millisecond = 0;
    var second = 0;
    var minute = 0;
    var hour = 0;
    var day = 1;
    var month = 1;
    var fullYear = 1970;
    var offset = null;
    var isPm = false;
    var is12HourFormat = false;
    var tmp0_elvis_lhs = this.rx2__1.find$default_xakyli_k$(str);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var result = tmp;
    var _iterator__ex2g4s = zip(this.chunks_1, drop(result.get_groupValues_rkv314_k$(), 1)).iterator_jk1svi_k$();
    $l$loop: while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      var name = _destruct__k2r9zo.component1_7eebsc_k$();
      var value = _destruct__k2r9zo.component2_7eebsb_k$();
      // Inline function 'kotlin.text.isEmpty' call
      if (charSequenceLength(value) === 0)
        continue $l$loop;
      switch (name) {
        case 'E':
        case 'EE':
        case 'EEE':
        case 'EEEE':
        case 'EEEEE':
        case 'EEEEEE':
          break;
        case 'z':
        case 'zzz':
          offset = readTimeZoneOffset(new MicroStrReader(value), this.tzNames_1);
          break;
        case 'd':
        case 'dd':
          day = toInt(value);
          break;
        case 'do':
          day = this.get_realLocale_ejo4yp_k$().getDayByOrdinal_757iy_k$(value);
          break;
        case 'M':
        case 'MM':
          month = toInt(value);
          break;
        case 'MMM':
          month = this.get_realLocale_ejo4yp_k$().get_monthsShort_uq3qxs_k$().indexOf_si1fv9_k$(value) + 1 | 0;
          break;
        case 'y':
        case 'yyyy':
        case 'YYYY':
          fullYear = toInt(value);
          break;
        case 'yy':
          if (doThrow)
            throw RuntimeException_init_$Create$('Not guessing years from two digits.');
          else
            return null;
        case 'yyy':
          fullYear = toInt(value) + (toInt(value) < 800 ? 2000 : 1000) | 0;
          break;
        case 'H':
        case 'HH':
        case 'k':
        case 'kk':
          hour = toInt(value);
          break;
        case 'h':
        case 'hh':
        case 'K':
        case 'KK':
          hour = toInt(value);
          is12HourFormat = true;
          break;
        case 'm':
        case 'mm':
          minute = toInt(value);
          break;
        case 's':
        case 'ss':
          second = toInt(value);
          break;
        case 'S':
        case 'SS':
        case 'SSS':
        case 'SSSS':
        case 'SSSSS':
        case 'SSSSSS':
        case 'SSSSSSS':
        case 'SSSSSSSS':
        case 'SSSSSSSSS':
          // Inline function 'kotlin.math.log10' call

          var x = toDouble(value);
          var tmp$ret$1 = log10(x);
          var base10length = numberToInt(tmp$ret$1) + 1 | 0;
          var tmp_0;
          if (base10length > 3) {
            var tmp_1 = toDouble(value);
            // Inline function 'kotlin.math.pow' call
            var n = imul(-1, base10length - 3 | 0);
            var tmp$ret$2 = Math.pow(10.0, n);
            tmp_0 = numberToInt(tmp_1 * tmp$ret$2);
          } else {
            tmp_0 = toInt(value);
          }

          millisecond = tmp_0;
          break;
        case 'X':
        case 'XX':
        case 'XXX':
        case 'x':
        case 'xx':
        case 'xxx':
          if (startsWith_0(name, 'X') ? first(value) === _Char___init__impl__6a9atx(90) : false) {
            // Inline function 'korlibs.time.hours' call
            // Inline function 'korlibs.time.hours' call
            offset = toDuration(0, DurationUnit_HOURS_getInstance());
          } else if (startsWith_0(name, 'x') ? first(value) === _Char___init__impl__6a9atx(90) : false) {
            if (doThrow)
              throw RuntimeException_init_$Create$('Zulu Time Zone is only accepted with X-XXX formats.');
            else
              return null;
          } else if (!(first(value) === _Char___init__impl__6a9atx(90))) {
            var valueUnsigned = removePrefix(removePrefix(replace(value, ':', ''), '-'), '+');
            var hours = name.length === 1 ? toInt(valueUnsigned) : toInt(take(valueUnsigned, 2));
            var minutes = name.length === 1 ? 0 : toInt(drop_0(valueUnsigned, 2));
            // Inline function 'korlibs.time.hours' call
            // Inline function 'korlibs.time.hours' call
            var tmp_2 = toDuration(hours, DurationUnit_HOURS_getInstance());
            // Inline function 'korlibs.time.minutes' call
            // Inline function 'korlibs.time.minutes' call
            var tmp$ret$8 = toDuration(minutes, DurationUnit_MINUTES_getInstance());
            offset = Duration__plus_impl_yu9v8f(tmp_2, tmp$ret$8);
            if (first(value) === _Char___init__impl__6a9atx(45)) {
              offset = Duration__unaryMinus_impl_x2k1y0(offset);
            }
          }

          break;
        case 'MMMM':
          month = this.get_realLocale_ejo4yp_k$().get_months_gqmrjw_k$().indexOf_si1fv9_k$(value) + 1 | 0;
          break;
        case 'MMMMM':
          if (doThrow)
            throw RuntimeException_init_$Create$('Not possible to get the month from one letter.');
          else
            return null;
        case 'a':
          isPm = equals_0(value, 'pm', true);
          break;
        default:
          break;
      }
    }
    if (is12HourFormat) {
      if (isPm) {
        if (!(hour === 12)) {
          hour = hour + 12 | 0;
        }
      } else {
        if (hour === 12) {
          hour = 0;
        }
      }
    }
    if (!doAdjust) {
      if (!(1 <= month ? month <= 12 : false))
        if (doThrow) {
          // Inline function 'kotlin.error' call
          var message = 'Invalid month ' + month;
          throw IllegalStateException_init_$Create$(toString(message));
        } else
          return null;
      if (!(1 <= day ? day <= 32 : false))
        if (doThrow) {
          // Inline function 'kotlin.error' call
          var message_0 = 'Invalid day ' + day;
          throw IllegalStateException_init_$Create$(toString(message_0));
        } else
          return null;
      if (!(0 <= hour ? hour <= 24 : false))
        if (doThrow) {
          // Inline function 'kotlin.error' call
          var message_1 = 'Invalid hour ' + hour;
          throw IllegalStateException_init_$Create$(toString(message_1));
        } else
          return null;
      if (!(0 <= minute ? minute <= 59 : false))
        if (doThrow) {
          // Inline function 'kotlin.error' call
          var message_2 = 'Invalid minute ' + minute;
          throw IllegalStateException_init_$Create$(toString(message_2));
        } else
          return null;
      if (!(0 <= second ? second <= 59 : false))
        if (doThrow) {
          // Inline function 'kotlin.error' call
          var message_3 = 'Invalid second ' + second;
          throw IllegalStateException_init_$Create$(toString(message_3));
        } else
          return null;
      if (!(0 <= millisecond ? millisecond <= 999 : false))
        if (doThrow) {
          // Inline function 'kotlin.error' call
          var message_4 = 'Invalid millisecond ' + millisecond;
          throw IllegalStateException_init_$Create$(toString(message_4));
        } else
          return null;
    }
    var dateTime = Companion_getInstance_3().createAdjusted_m79lxy_k$(fullYear, month, day, umod(hour, 24), minute, second, millisecond);
    var tmp1_elvis_lhs = offset;
    var tmp_3;
    var tmp_4 = tmp1_elvis_lhs;
    if ((tmp_4 == null ? null : new Duration(tmp_4)) == null) {
      // Inline function 'korlibs.time.hours' call
      // Inline function 'korlibs.time.hours' call
      tmp_3 = toDuration(0, DurationUnit_HOURS_getInstance());
    } else {
      tmp_3 = tmp1_elvis_lhs;
    }
    return DateTime__toOffsetUnadjusted_impl_iorn(dateTime, tmp_3);
  };
  protoOf(PatternDateFormat).toString = function () {
    return this.format_1;
  };
  protoOf(PatternDateFormat).component1_7eebsc_k$ = function () {
    return this.format_1;
  };
  protoOf(PatternDateFormat).component2_7eebsb_k$ = function () {
    return this.locale_1;
  };
  protoOf(PatternDateFormat).component3_7eebsa_k$ = function () {
    return this.tzNames_1;
  };
  protoOf(PatternDateFormat).component4_7eebs9_k$ = function () {
    return this.options_1;
  };
  protoOf(PatternDateFormat).copy_pr8je1_k$ = function (format, locale, tzNames, options) {
    return new PatternDateFormat(format, locale, tzNames, options);
  };
  protoOf(PatternDateFormat).copy$default_5ppx6c_k$ = function (format, locale, tzNames, options, $super) {
    format = format === VOID ? this.format_1 : format;
    locale = locale === VOID ? this.locale_1 : locale;
    tzNames = tzNames === VOID ? this.tzNames_1 : tzNames;
    options = options === VOID ? this.options_1 : options;
    return $super === VOID ? this.copy_pr8je1_k$(format, locale, tzNames, options) : $super.copy_pr8je1_k$.call(this, format, locale, tzNames, options);
  };
  protoOf(PatternDateFormat).hashCode = function () {
    var result = getStringHashCode(this.format_1);
    result = imul(result, 31) + (this.locale_1 == null ? 0 : hashCode(this.locale_1)) | 0;
    result = imul(result, 31) + this.tzNames_1.hashCode() | 0;
    result = imul(result, 31) + this.options_1.hashCode() | 0;
    return result;
  };
  protoOf(PatternDateFormat).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof PatternDateFormat))
      return false;
    var tmp0_other_with_cast = other instanceof PatternDateFormat ? other : THROW_CCE();
    if (!(this.format_1 === tmp0_other_with_cast.format_1))
      return false;
    if (!equals(this.locale_1, tmp0_other_with_cast.locale_1))
      return false;
    if (!this.tzNames_1.equals(tmp0_other_with_cast.tzNames_1))
      return false;
    if (!this.options_1.equals(tmp0_other_with_cast.options_1))
      return false;
    return true;
  };
  function readRepeatedChar(_this__u8e3s4) {
    // Inline function 'korlibs.time.internal.MicroStrReader.readChunk' call
    var start = _this__u8e3s4.get_offset_hjmqak_k$();
    // Inline function 'korlibs.time.readRepeatedChar.<anonymous>' call
    var c = _this__u8e3s4.readChar_vl5a7m_k$();
    while (_this__u8e3s4.get_hasMore_csdhd2_k$() ? _this__u8e3s4.tryRead_2h17j0_k$(c) : false)
    ;
    var end = _this__u8e3s4.get_offset_hjmqak_k$();
    // Inline function 'kotlin.text.substring' call
    // Inline function 'kotlin.js.asDynamic' call
    return _this__u8e3s4.get_str_18ivy0_k$().substring(start, end);
  }
  function mconvertRangeZero(value, size) {
    return umod(value, size);
  }
  function mconvertRangeNonZero(value, size) {
    var res = umod(value, size);
    return res === 0 ? size : res;
  }
  function _get_serialVersionUID__fhggm9_8($this) {
    return $this.serialVersionUID_1;
  }
  function _get_serialVersionUID__fhggm9_9($this) {
    return $this.serialVersionUID_1;
  }
  function Companion_13() {
    Companion_instance_13 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.DEFAULT_1 = new Options_0(false);
    this.WITH_OPTIONAL_1 = new Options_0(true);
  }
  protoOf(Companion_13).get_DEFAULT_wccqmg_k$ = function () {
    return this.DEFAULT_1;
  };
  protoOf(Companion_13).get_WITH_OPTIONAL_1fkui8_k$ = function () {
    return this.WITH_OPTIONAL_1;
  };
  var Companion_instance_13;
  function Companion_getInstance_15() {
    if (Companion_instance_13 == null)
      new Companion_13();
    return Companion_instance_13;
  }
  function Companion_14() {
    Companion_instance_14 = this;
    this.serialVersionUID_1 = new Long(1, 0);
  }
  var Companion_instance_14;
  function Companion_getInstance_16() {
    if (Companion_instance_14 == null)
      new Companion_14();
    return Companion_instance_14;
  }
  function Options_0(optionalSupport) {
    Companion_getInstance_15();
    optionalSupport = optionalSupport === VOID ? false : optionalSupport;
    this.optionalSupport_1 = optionalSupport;
  }
  protoOf(Options_0).get_optionalSupport_3v38me_k$ = function () {
    return this.optionalSupport_1;
  };
  protoOf(Options_0).component1_7eebsc_k$ = function () {
    return this.optionalSupport_1;
  };
  protoOf(Options_0).copy_o18wmo_k$ = function (optionalSupport) {
    return new Options_0(optionalSupport);
  };
  protoOf(Options_0).copy$default_6yj2xd_k$ = function (optionalSupport, $super) {
    optionalSupport = optionalSupport === VOID ? this.optionalSupport_1 : optionalSupport;
    return $super === VOID ? this.copy_o18wmo_k$(optionalSupport) : $super.copy_o18wmo_k$.call(this, optionalSupport);
  };
  protoOf(Options_0).toString = function () {
    return 'Options(optionalSupport=' + this.optionalSupport_1 + ')';
  };
  protoOf(Options_0).hashCode = function () {
    return getBooleanHashCode(this.optionalSupport_1);
  };
  protoOf(Options_0).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Options_0))
      return false;
    var tmp0_other_with_cast = other instanceof Options_0 ? other : THROW_CCE();
    if (!(this.optionalSupport_1 === tmp0_other_with_cast.optionalSupport_1))
      return false;
    return true;
  };
  function _get_openOffsets__f0vio5_0($this) {
    return $this.openOffsets_1;
  }
  function _get_closeOffsets__3wdqub_0($this) {
    return $this.closeOffsets_1;
  }
  function _get_regexChunks__squc9o($this) {
    return $this.regexChunks_1;
  }
  function _get_rx2__e6gajp($this) {
    return $this.rx2__1;
  }
  function clampZero($this, value, size) {
    return umod(value, size);
  }
  function clampNonZero($this, value, size) {
    // Inline function 'kotlin.let' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.time.PatternTimeFormat.clampNonZero.<anonymous>' call
    var it = umod(value, size);
    return it === 0 ? size : it;
  }
  function PatternTimeFormat(format, options) {
    Companion_getInstance_16();
    options = options === VOID ? Companion_getInstance_15().DEFAULT_1 : options;
    this.format_1 = format;
    this.options_1 = options;
    this.openOffsets_1 = LinkedHashMap_init_$Create$();
    this.closeOffsets_1 = LinkedHashMap_init_$Create$();
    var tmp = this;
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.collections.arrayListOf' call
    var this_0 = ArrayList_init_$Create$();
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.time.PatternTimeFormat.chunks.<anonymous>' call
    var s = new MicroStrReader(this.format_1);
    $l$loop_1: while (s.get_hasMore_csdhd2_k$()) {
      if (s.peekChar_2c2lw3_k$() === _Char___init__impl__6a9atx(39)) {
        // Inline function 'korlibs.time.internal.MicroStrReader.readChunk' call
        var start = s.get_offset_hjmqak_k$();
        // Inline function 'korlibs.time.PatternTimeFormat.chunks.<anonymous>.<anonymous>' call
        s.tryRead_2h17j0_k$(_Char___init__impl__6a9atx(39));
        while (s.get_hasMore_csdhd2_k$() ? !(s.readChar_vl5a7m_k$() === _Char___init__impl__6a9atx(39)) : false)
        ;
        var end = s.get_offset_hjmqak_k$();
        // Inline function 'kotlin.text.substring' call
        // Inline function 'kotlin.js.asDynamic' call
        var escapedChunk = s.get_str_18ivy0_k$().substring(start, end);
        this_0.add_utx5q5_k$(escapedChunk);
        continue $l$loop_1;
      }
      if (this.options_1.optionalSupport_1) {
        var offset = this_0.get_size_woubt6_k$();
        if (s.tryRead_2h17j0_k$(_Char___init__impl__6a9atx(91))) {
          increment(this.openOffsets_1, offset);
          continue $l$loop_1;
        }
        if (s.tryRead_2h17j0_k$(_Char___init__impl__6a9atx(93))) {
          increment(this.closeOffsets_1, offset - 1 | 0);
          continue $l$loop_1;
        }
      }
      // Inline function 'korlibs.time.internal.MicroStrReader.readChunk' call
      var start_0 = s.get_offset_hjmqak_k$();
      // Inline function 'korlibs.time.PatternTimeFormat.chunks.<anonymous>.<anonymous>' call
      var c = s.readChar_vl5a7m_k$();
      while (s.get_hasMore_csdhd2_k$() ? s.tryRead_2h17j0_k$(c) : false)
      ;
      var end_0 = s.get_offset_hjmqak_k$();
      // Inline function 'kotlin.text.substring' call
      // Inline function 'kotlin.js.asDynamic' call
      var chunk = s.get_str_18ivy0_k$().substring(start_0, end_0);
      this_0.add_utx5q5_k$(chunk);
    }
    tmp.chunks_1 = toList(this_0);
    var tmp_0 = this;
    // Inline function 'kotlin.collections.map' call
    var this_1 = this.chunks_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_1, 10));
    var tmp0_iterator = this_1.iterator_jk1svi_k$();
    while (tmp0_iterator.hasNext_bitz1p_k$()) {
      var item = tmp0_iterator.next_20eer_k$();
      // Inline function 'korlibs.time.PatternTimeFormat.regexChunks.<anonymous>' call
      switch (item) {
        case 'H':
        case 'k':
          var tmp$ret$8 = '(\\d{1,})';
          break;
        case 'HH':
        case 'kk':
          tmp$ret$8 = '(\\d{2,})';
          break;
        case 'h':
        case 'K':
          tmp$ret$8 = '(\\d{1,2})';
          break;
        case 'hh':
        case 'KK':
          tmp$ret$8 = '(\\d{2})';
          break;
        case 'm':
          tmp$ret$8 = '(\\d{1,2})';
          break;
        case 'mm':
          tmp$ret$8 = '(\\d{2})';
          break;
        case 's':
          tmp$ret$8 = '(\\d{1,2})';
          break;
        case 'ss':
          tmp$ret$8 = '(\\d{2})';
          break;
        case 'S':
          tmp$ret$8 = '(\\d{1,6})';
          break;
        case 'SS':
          tmp$ret$8 = '(\\d{2})';
          break;
        case 'SSS':
          tmp$ret$8 = '(\\d{3})';
          break;
        case 'SSSS':
          tmp$ret$8 = '(\\d{4})';
          break;
        case 'SSSSS':
          tmp$ret$8 = '(\\d{5})';
          break;
        case 'SSSSSS':
          tmp$ret$8 = '(\\d{6})';
          break;
        case 'SSSSSSS':
          tmp$ret$8 = '(\\d{7})';
          break;
        case 'SSSSSSSS':
          tmp$ret$8 = '(\\d{8})';
          break;
        case 'a':
          tmp$ret$8 = '(\\w+)';
          break;
        case ' ':
          tmp$ret$8 = '(\\s+)';
          break;
        default:
          tmp$ret$8 = startsWith(item, _Char___init__impl__6a9atx(39)) ? '(' + Companion_getInstance_0().escapeReplacement_1j0fzr_k$(substr(item, 1, item.length - 2 | 0)) + ')' : '(' + Companion_getInstance_0().escapeReplacement_1j0fzr_k$(item) + ')';
          break;
      }
      destination.add_utx5q5_k$(tmp$ret$8);
    }
    tmp_0.regexChunks_1 = destination;
    var tmp_1 = this;
    // Inline function 'kotlin.collections.mapIndexed' call
    var this_2 = this.regexChunks_1;
    // Inline function 'kotlin.collections.mapIndexedTo' call
    var destination_0 = ArrayList_init_$Create$_0(collectionSizeOrDefault(this_2, 10));
    var index = 0;
    var tmp0_iterator_0 = this_2.iterator_jk1svi_k$();
    while (tmp0_iterator_0.hasNext_bitz1p_k$()) {
      var item_0 = tmp0_iterator_0.next_20eer_k$();
      // Inline function 'korlibs.time.PatternTimeFormat.rx2.<anonymous>' call
      var tmp1 = index;
      index = tmp1 + 1 | 0;
      var index_0 = checkIndexOverflow(tmp1);
      var tmp_2;
      if (this.options_1.optionalSupport_1) {
        // Inline function 'kotlin.collections.getOrElse' call
        var tmp0_elvis_lhs = this.openOffsets_1.get_wei43m_k$(index_0);
        var tmp_3;
        if (tmp0_elvis_lhs == null) {
          // Inline function 'korlibs.time.PatternTimeFormat.rx2.<anonymous>.<anonymous>' call
          tmp_3 = 0;
        } else {
          tmp_3 = tmp0_elvis_lhs;
        }
        var opens = tmp_3;
        // Inline function 'kotlin.collections.getOrElse' call
        var tmp0_elvis_lhs_0 = this.closeOffsets_1.get_wei43m_k$(index_0);
        var tmp_4;
        if (tmp0_elvis_lhs_0 == null) {
          // Inline function 'korlibs.time.PatternTimeFormat.rx2.<anonymous>.<anonymous>' call
          tmp_4 = 0;
        } else {
          tmp_4 = tmp0_elvis_lhs_0;
        }
        var closes = tmp_4;
        // Inline function 'kotlin.text.buildString' call
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'kotlin.apply' call
        var this_3 = StringBuilder_init_$Create$();
        // Inline function 'kotlin.contracts.contract' call
        // Inline function 'korlibs.time.PatternTimeFormat.rx2.<anonymous>.<anonymous>' call
        // Inline function 'kotlin.repeat' call
        // Inline function 'kotlin.contracts.contract' call
        var inductionVariable = 0;
        if (inductionVariable < opens)
          do {
            var index_1 = inductionVariable;
            inductionVariable = inductionVariable + 1 | 0;
            // Inline function 'korlibs.time.PatternTimeFormat.rx2.<anonymous>.<anonymous>.<anonymous>' call
            this_3.append_22ad7x_k$('(?:');
          }
           while (inductionVariable < opens);
        this_3.append_22ad7x_k$(item_0);
        // Inline function 'kotlin.repeat' call
        // Inline function 'kotlin.contracts.contract' call
        var inductionVariable_0 = 0;
        if (inductionVariable_0 < closes)
          do {
            var index_2 = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            // Inline function 'korlibs.time.PatternTimeFormat.rx2.<anonymous>.<anonymous>.<anonymous>' call
            this_3.append_22ad7x_k$(')?');
          }
           while (inductionVariable_0 < closes);
        tmp_2 = this_3.toString();
      } else {
        tmp_2 = item_0;
      }
      var tmp$ret$17 = tmp_2;
      destination_0.add_utx5q5_k$(tmp$ret$17);
    }
    tmp_1.rx2__1 = Regex_init_$Create$('^' + joinToString(destination_0, '') + '$');
  }
  protoOf(PatternTimeFormat).get_format_dfdtds_k$ = function () {
    return this.format_1;
  };
  protoOf(PatternTimeFormat).get_options_jecmyz_k$ = function () {
    return this.options_1;
  };
  protoOf(PatternTimeFormat).withOptions_7vd97u_k$ = function (options) {
    return this.copy$default_ssjnie_k$(VOID, options);
  };
  protoOf(PatternTimeFormat).withOptional_umqaii_k$ = function () {
    return this.copy$default_ssjnie_k$(VOID, this.options_1.copy_o18wmo_k$(true));
  };
  protoOf(PatternTimeFormat).withNonOptional_s53xw9_k$ = function () {
    return this.copy$default_ssjnie_k$(VOID, this.options_1.copy_o18wmo_k$(false));
  };
  protoOf(PatternTimeFormat).get_chunks_xqch8u_k$ = function () {
    return this.chunks_1;
  };
  protoOf(PatternTimeFormat).format_6hgmc2_k$ = function (dd) {
    var time = _Time___init__impl__x7mm30(dd);
    var out = '';
    var _iterator__ex2g4s = this.chunks_1.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var name = _iterator__ex2g4s.next_20eer_k$();
      var nlen = name.length;
      var tmp = out;
      var tmp_0;
      switch (name) {
        case 'H':
        case 'HH':
          tmp_0 = padded(_Time___get_hour__impl__xa2741(time), nlen);
          break;
        case 'k':
        case 'kk':
          tmp_0 = padded(_Time___get_hour__impl__xa2741(time), nlen);
          break;
        case 'h':
        case 'hh':
          tmp_0 = padded(clampNonZero(this, _Time___get_hour__impl__xa2741(time), 12), nlen);
          break;
        case 'K':
        case 'KK':
          tmp_0 = padded(clampZero(this, _Time___get_hour__impl__xa2741(time), 12), nlen);
          break;
        case 'm':
        case 'mm':
          tmp_0 = padded(_Time___get_minute__impl__egf45t(time), nlen);
          break;
        case 's':
        case 'ss':
          tmp_0 = padded(_Time___get_second__impl__nhehzj(time), nlen);
          break;
        case 'S':
        case 'SS':
        case 'SSS':
        case 'SSSS':
        case 'SSSSS':
        case 'SSSSSS':
        case 'SSSSSSS':
        case 'SSSSSSSS':
          var milli = _Time___get_millisecond__impl__qz3nfc(time);
          // Inline function 'kotlin.math.log10' call

          var x = _Time___get_millisecond__impl__qz3nfc(time);
          var tmp$ret$0 = log10(x);
          var numberLength = numberToInt(tmp$ret$0) + 1 | 0;
          var tmp_1;
          if (numberLength > name.length) {
            var tmp_2 = milli;
            // Inline function 'kotlin.math.pow' call
            var n = numberLength - name.length | 0;
            var tmp$ret$1 = Math.pow(10.0, n);
            tmp_1 = numberToInt(tmp_2 / tmp$ret$1);
          } else {
            tmp_1 = substr(padded(milli, 3) + '00000', 0, name.length);
          }

          tmp_0 = tmp_1;
          break;
        case 'a':
          tmp_0 = _Time___get_hour__impl__xa2741(time) < 12 ? 'am' : _Time___get_hour__impl__xa2741(time) < 24 ? 'pm' : '';
          break;
        default:
          var tmp_3;
          if (startsWith(name, _Char___init__impl__6a9atx(39))) {
            // Inline function 'kotlin.text.substring' call
            var endIndex = name.length - 1 | 0;
            // Inline function 'kotlin.js.asDynamic' call
            tmp_3 = name.substring(1, endIndex);
          } else {
            tmp_3 = name;
          }

          tmp_0 = tmp_3;
          break;
      }
      out = tmp + toString(tmp_0);
    }
    return out;
  };
  protoOf(PatternTimeFormat).tryParse_je1hyv_k$ = function (str, doThrow, doAdjust) {
    var millisecond = 0;
    var second = 0;
    var minute = 0;
    var hour = 0;
    var isPm = false;
    var is12HourFormat = false;
    var tmp0_elvis_lhs = this.rx2__1.find$default_xakyli_k$(str);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var result = tmp;
    var _iterator__ex2g4s = zip(this.chunks_1, drop(result.get_groupValues_rkv314_k$(), 1)).iterator_jk1svi_k$();
    $l$loop: while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      var name = _destruct__k2r9zo.component1_7eebsc_k$();
      var value = _destruct__k2r9zo.component2_7eebsb_k$();
      // Inline function 'kotlin.text.isEmpty' call
      if (charSequenceLength(value) === 0)
        continue $l$loop;
      switch (name) {
        case 'H':
        case 'HH':
        case 'k':
        case 'kk':
          hour = toInt(value);
          break;
        case 'h':
        case 'hh':
        case 'K':
        case 'KK':
          hour = umod(toInt(value), 24);
          is12HourFormat = true;
          break;
        case 'm':
        case 'mm':
          minute = toInt(value);
          break;
        case 's':
        case 'ss':
          second = toInt(value);
          break;
        case 'S':
        case 'SS':
        case 'SSS':
        case 'SSSS':
        case 'SSSSS':
        case 'SSSSSS':
          // Inline function 'kotlin.math.log10' call

          var x = toDouble(value);
          var tmp$ret$1 = log10(x);
          var numberLength = numberToInt(tmp$ret$1) + 1 | 0;
          var tmp_0;
          if (numberLength > 3) {
            var tmp_1 = toDouble(value);
            // Inline function 'kotlin.math.pow' call
            var n = imul(-1, numberLength - 3 | 0);
            var tmp$ret$2 = Math.pow(10.0, n);
            tmp_0 = numberToInt(tmp_1 * tmp$ret$2);
          } else {
            tmp_0 = toInt(value);
          }

          millisecond = tmp_0;
          break;
        case 'a':
          isPm = value === 'pm';
          break;
        default:
          break;
      }
    }
    if (is12HourFormat ? isPm : false) {
      hour = hour + 12 | 0;
    }
    // Inline function 'korlibs.time.hours' call
    // Inline function 'korlibs.time.hours' call
    var this_0 = hour;
    var tmp_2 = toDuration(this_0, DurationUnit_HOURS_getInstance());
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var this_1 = minute;
    var tmp$ret$6 = toDuration(this_1, DurationUnit_MINUTES_getInstance());
    var tmp_3 = Duration__plus_impl_yu9v8f(tmp_2, tmp$ret$6);
    // Inline function 'korlibs.time.seconds' call
    // Inline function 'korlibs.time.seconds' call
    var this_2 = second;
    var tmp$ret$8 = toDuration(this_2, DurationUnit_SECONDS_getInstance());
    var tmp_4 = Duration__plus_impl_yu9v8f(tmp_3, tmp$ret$8);
    // Inline function 'korlibs.time.milliseconds' call
    var this_3 = millisecond;
    // Inline function 'kotlin.time.Companion.milliseconds' call
    Companion_getInstance();
    var tmp$ret$10 = toDuration(this_3, DurationUnit_MILLISECONDS_getInstance());
    return Duration__plus_impl_yu9v8f(tmp_4, tmp$ret$10);
  };
  protoOf(PatternTimeFormat).toString = function () {
    return this.format_1;
  };
  protoOf(PatternTimeFormat).component1_7eebsc_k$ = function () {
    return this.format_1;
  };
  protoOf(PatternTimeFormat).component2_7eebsb_k$ = function () {
    return this.options_1;
  };
  protoOf(PatternTimeFormat).copy_ixm1zr_k$ = function (format, options) {
    return new PatternTimeFormat(format, options);
  };
  protoOf(PatternTimeFormat).copy$default_ssjnie_k$ = function (format, options, $super) {
    format = format === VOID ? this.format_1 : format;
    options = options === VOID ? this.options_1 : options;
    return $super === VOID ? this.copy_ixm1zr_k$(format, options) : $super.copy_ixm1zr_k$.call(this, format, options);
  };
  protoOf(PatternTimeFormat).hashCode = function () {
    var result = getStringHashCode(this.format_1);
    result = imul(result, 31) + this.options_1.hashCode() | 0;
    return result;
  };
  protoOf(PatternTimeFormat).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof PatternTimeFormat))
      return false;
    var tmp0_other_with_cast = other instanceof PatternTimeFormat ? other : THROW_CCE();
    if (!(this.format_1 === tmp0_other_with_cast.format_1))
      return false;
    if (!this.options_1.equals(tmp0_other_with_cast.options_1))
      return false;
    return true;
  };
  function _get_serialVersionUID__fhggm9_10($this) {
    return $this.serialVersionUID_1;
  }
  function _get_DIV_MILLISECONDS__cmqzit($this) {
    return $this.DIV_MILLISECONDS_1;
  }
  function _get_DIV_SECONDS__qmeg4g($this) {
    return $this.DIV_SECONDS_1;
  }
  function _get_DIV_MINUTES__b9qqtc($this) {
    return $this.DIV_MINUTES_1;
  }
  function _get_DIV_HOURS__7wgbow($this) {
    return $this.DIV_HOURS_1;
  }
  function _Time___init__impl__x7mm30(encoded) {
    return encoded;
  }
  function _Time___get_encoded__impl__p1onjf($this) {
    return $this;
  }
  function Companion_15() {
    Companion_instance_15 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.DIV_MILLISECONDS_1 = 1;
    this.DIV_SECONDS_1 = 1000;
    this.DIV_MINUTES_1 = 60000;
    this.DIV_HOURS_1 = 3600000;
  }
  protoOf(Companion_15).invoke_pdi41e_k$ = function (hour, minute, second, millisecond) {
    // Inline function 'korlibs.time.hours' call
    // Inline function 'korlibs.time.hours' call
    var tmp = toDuration(hour, DurationUnit_HOURS_getInstance());
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var tmp$ret$3 = toDuration(minute, DurationUnit_MINUTES_getInstance());
    var tmp_0 = Duration__plus_impl_yu9v8f(tmp, tmp$ret$3);
    // Inline function 'korlibs.time.seconds' call
    // Inline function 'korlibs.time.seconds' call
    var tmp$ret$5 = toDuration(second, DurationUnit_SECONDS_getInstance());
    var tmp_1 = Duration__plus_impl_yu9v8f(tmp_0, tmp$ret$5);
    // Inline function 'korlibs.time.milliseconds' call
    // Inline function 'kotlin.time.Companion.milliseconds' call
    Companion_getInstance();
    var tmp$ret$7 = toDuration(millisecond, DurationUnit_MILLISECONDS_getInstance());
    return _Time___init__impl__x7mm30(Duration__plus_impl_yu9v8f(tmp_1, tmp$ret$7));
  };
  protoOf(Companion_15).invoke$default_2i9swf_k$ = function (hour, minute, second, millisecond, $super) {
    minute = minute === VOID ? 0 : minute;
    second = second === VOID ? 0 : second;
    millisecond = millisecond === VOID ? 0 : millisecond;
    return $super === VOID ? this.invoke_pdi41e_k$(hour, minute, second, millisecond) : $super.invoke_pdi41e_k$.call(this, hour, minute, second, millisecond).encoded_1;
  };
  var Companion_instance_15;
  function Companion_getInstance_17() {
    if (Companion_instance_15 == null)
      new Companion_15();
    return Companion_instance_15;
  }
  function _Time___get_millisecond__impl__qz3nfc($this) {
    return abs((get_millisecondsInt(_Time___get_encoded__impl__p1onjf($this)) / 1 | 0) % 1000 | 0);
  }
  function _Time___get_second__impl__nhehzj($this) {
    return abs((get_millisecondsInt(_Time___get_encoded__impl__p1onjf($this)) / 1000 | 0) % 60 | 0);
  }
  function _Time___get_minute__impl__egf45t($this) {
    return abs((get_millisecondsInt(_Time___get_encoded__impl__p1onjf($this)) / 60000 | 0) % 60 | 0);
  }
  function _Time___get_hour__impl__xa2741($this) {
    return get_millisecondsInt(_Time___get_encoded__impl__p1onjf($this)) / 3600000 | 0;
  }
  function _Time___get_hourAdjusted__impl__ubgkp9($this) {
    return (get_millisecondsInt(_Time___get_encoded__impl__p1onjf($this)) / 3600000 | 0) % 24 | 0;
  }
  function Time__adjust_impl_1h98gf($this) {
    return Companion_getInstance_17().invoke_pdi41e_k$(_Time___get_hourAdjusted__impl__ubgkp9($this), _Time___get_minute__impl__egf45t($this), _Time___get_second__impl__nhehzj($this), _Time___get_millisecond__impl__qz3nfc($this));
  }
  function Time__format_impl_3nw8zt($this, format) {
    return format_0(Companion_getInstance_18().invoke_lt562m_k$(format), $this);
  }
  function Time__format_impl_3nw8zt_0($this, format) {
    return format_0(format, $this);
  }
  function Time__toString_impl_3fgayc($this) {
    return (_Time___get_hour__impl__xa2741($this) < 0 ? '-' : '') + padStart(abs(_Time___get_hour__impl__xa2741($this)).toString(), 2, _Char___init__impl__6a9atx(48)) + ':' + padStart(abs(_Time___get_minute__impl__egf45t($this)).toString(), 2, _Char___init__impl__6a9atx(48)) + ':' + padStart(abs(_Time___get_second__impl__nhehzj($this)).toString(), 2, _Char___init__impl__6a9atx(48)) + '.' + padStart(abs(_Time___get_millisecond__impl__qz3nfc($this)).toString(), 3, _Char___init__impl__6a9atx(48));
  }
  function Time__compareTo_impl_es8iw6($this, other) {
    return Duration__compareTo_impl_pchp0f(_Time___get_encoded__impl__p1onjf($this), _Time___get_encoded__impl__p1onjf(other));
  }
  function Time__compareTo_impl_es8iw6_0($this, other) {
    return Time__compareTo_impl_es8iw6($this.encoded_1, other instanceof Time ? other.encoded_1 : THROW_CCE());
  }
  function Time__hashCode_impl_ibpluj($this) {
    return Duration__hashCode_impl_u4exz6($this);
  }
  function Time__equals_impl_ylev5r($this, other) {
    if (!(other instanceof Time))
      return false;
    var tmp0_other_with_cast = other instanceof Time ? other.encoded_1 : THROW_CCE();
    if (!equals($this, tmp0_other_with_cast))
      return false;
    return true;
  }
  function Time(encoded) {
    Companion_getInstance_17();
    this.encoded_1 = encoded;
  }
  protoOf(Time).toString = function () {
    return Time__toString_impl_3fgayc(this.encoded_1);
  };
  protoOf(Time).compareTo_8omtpz_k$ = function (other) {
    return Time__compareTo_impl_es8iw6(this.encoded_1, other);
  };
  protoOf(Time).compareTo_hpufkf_k$ = function (other) {
    return Time__compareTo_impl_es8iw6_0(this, other);
  };
  protoOf(Time).hashCode = function () {
    return Time__hashCode_impl_ibpluj(this.encoded_1);
  };
  protoOf(Time).equals = function (other) {
    return Time__equals_impl_ylev5r(this.encoded_1, other);
  };
  function format_0(_this__u8e3s4, time) {
    return _this__u8e3s4.format_6hgmc2_k$(_Time___get_encoded__impl__p1onjf(time));
  }
  function Companion_16() {
    Companion_instance_16 = this;
    this.DEFAULT_FORMAT_1 = Companion_getInstance_18().invoke_lt562m_k$('HH:mm:ss.SSS');
    this.FORMAT_TIME_1 = Companion_getInstance_18().invoke_lt562m_k$('HH:mm:ss');
    this.FORMATS_1 = listOf([this.DEFAULT_FORMAT_1, this.FORMAT_TIME_1]);
  }
  protoOf(Companion_16).get_DEFAULT_FORMAT_t79cy_k$ = function () {
    return this.DEFAULT_FORMAT_1;
  };
  protoOf(Companion_16).get_FORMAT_TIME_32wvqk_k$ = function () {
    return this.FORMAT_TIME_1;
  };
  protoOf(Companion_16).get_FORMATS_1xou37_k$ = function () {
    return this.FORMATS_1;
  };
  protoOf(Companion_16).parse_8aqxct_k$ = function (time) {
    var lastError = null;
    var _iterator__ex2g4s = this.FORMATS_1.iterator_jk1svi_k$();
    while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var format = _iterator__ex2g4s.next_20eer_k$();
      try {
        return parse_0(format, time);
      } catch ($p) {
        if ($p instanceof Error) {
          var e = $p;
          lastError = e;
        } else {
          throw $p;
        }
      }
    }
    throw ensureNotNull(lastError);
  };
  protoOf(Companion_16).invoke_lt562m_k$ = function (pattern) {
    return new PatternTimeFormat(pattern);
  };
  var Companion_instance_16;
  function Companion_getInstance_18() {
    if (Companion_instance_16 == null)
      new Companion_16();
    return Companion_instance_16;
  }
  function TimeFormat() {
  }
  function parse_0(_this__u8e3s4, str, doAdjust) {
    doAdjust = doAdjust === VOID ? true : doAdjust;
    var tmp0_elvis_lhs = _this__u8e3s4.tryParse_je1hyv_k$(str, true, doAdjust);
    var tmp;
    var tmp_0 = tmp0_elvis_lhs;
    if ((tmp_0 == null ? null : new Duration(tmp_0)) == null) {
      throw new DateException("Not a valid format: '" + str + "' for '" + toString(_this__u8e3s4) + "'");
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
  function get_DURATION_NIL() {
    _init_properties_TimeSpan_kt__xworpz();
    return DURATION_NIL;
  }
  var DURATION_NIL;
  function get_timeSteps() {
    _init_properties_TimeSpan_kt__xworpz();
    return timeSteps;
  }
  var timeSteps;
  function get_hours(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'korlibs.time.hours' call
    return toDuration(_this__u8e3s4, DurationUnit_HOURS_getInstance());
  }
  function get_minutes(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'korlibs.time.minutes' call
    return toDuration(_this__u8e3s4, DurationUnit_MINUTES_getInstance());
  }
  function get_hours_0(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return toDuration(_this__u8e3s4, DurationUnit_HOURS_getInstance());
  }
  function get_minutes_0(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return toDuration(_this__u8e3s4, DurationUnit_MINUTES_getInstance());
  }
  function get_milliseconds(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return toDuration(_this__u8e3s4, DurationUnit_MILLISECONDS_getInstance());
  }
  function get_milliseconds_0(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return _Duration___get_inWholeNanoseconds__impl__r5x4mr(_this__u8e3s4).toDouble_ygsx0s_k$() / 1000000.0;
  }
  function unaryPlus(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return _this__u8e3s4;
  }
  function fromMilliseconds(_this__u8e3s4, value) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'kotlin.time.Companion.milliseconds' call
    return toDuration(value, DurationUnit_MILLISECONDS_getInstance());
  }
  function get_milliseconds_1(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'kotlin.time.Companion.milliseconds' call
    Companion_getInstance();
    return toDuration(_this__u8e3s4, DurationUnit_MILLISECONDS_getInstance());
  }
  function get_days(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'korlibs.time.days' call
    return toDuration(_this__u8e3s4, DurationUnit_DAYS_getInstance());
  }
  function get_days_0(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return toDuration(_this__u8e3s4, DurationUnit_DAYS_getInstance());
  }
  function now(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return KlockInternal_getInstance().get_now_rsuecj_k$();
  }
  function get_seconds(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'korlibs.time.seconds' call
    return toDuration(_this__u8e3s4, DurationUnit_SECONDS_getInstance());
  }
  function get_weeks(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'korlibs.time.weeks' call
    // Inline function 'korlibs.time.days' call
    var this_0 = _this__u8e3s4 * 7;
    return toDuration(this_0, DurationUnit_DAYS_getInstance());
  }
  function get_millisecondsInt(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return numberToInt(get_milliseconds_0(_this__u8e3s4));
  }
  function get_seconds_0(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    return toDuration(_this__u8e3s4, DurationUnit_SECONDS_getInstance());
  }
  function get_weeks_0(_this__u8e3s4) {
    _init_properties_TimeSpan_kt__xworpz();
    // Inline function 'korlibs.time.days' call
    var this_0 = _this__u8e3s4 * 7;
    return toDuration(this_0, DurationUnit_DAYS_getInstance());
  }
  var properties_initialized_TimeSpan_kt_fytdnd;
  function _init_properties_TimeSpan_kt__xworpz() {
    if (!properties_initialized_TimeSpan_kt_fytdnd) {
      properties_initialized_TimeSpan_kt_fytdnd = true;
      DURATION_NIL = toDuration_0(new Long(13, -2097152), DurationUnit_NANOSECONDS_getInstance());
      timeSteps = listOf([60, 60, 24]);
    }
  }
  var Timezone_PDT_instance;
  var Timezone_PST_instance;
  var Timezone_GMT_instance;
  var Timezone_UTC_instance;
  var Timezone_CET_instance;
  var Timezone_CEST_instance;
  var Timezone_ACDT_instance;
  var Timezone_ACST_instance;
  var Timezone_ACT_instance;
  var Timezone_ACWST_instance;
  var Timezone_ADT_instance;
  var Timezone_AEDT_instance;
  var Timezone_AEST_instance;
  var Timezone_AFT_instance;
  var Timezone_AKDT_instance;
  var Timezone_AKST_instance;
  var Timezone_ALMT_instance;
  var Timezone_AMST_instance;
  var Timezone_AMT_BRAZIL_instance;
  var Timezone_AMT_ARMENIA_instance;
  var Timezone_ANAT_instance;
  var Timezone_AQTT_instance;
  var Timezone_ART_instance;
  var Timezone_AST_ARABIA_instance;
  var Timezone_AST_ATLANTIC_instance;
  var Timezone_AWST_instance;
  var Timezone_AZOST_instance;
  var Timezone_AZOT_instance;
  var Timezone_AZT_instance;
  var Timezone_BDT_instance;
  var Timezone_BIOT_instance;
  var Timezone_BIT_instance;
  var Timezone_BOT_instance;
  var Timezone_BRST_instance;
  var Timezone_BRT_instance;
  var Timezone_BST_BANGLADESH_instance;
  var Timezone_BST_BOUGAINVILLE_instance;
  var Timezone_BST_BRITISH_instance;
  var Timezone_BTT_instance;
  var Timezone_CAT_instance;
  var Timezone_CCT_instance;
  var Timezone_CDT_AMERICA_instance;
  var Timezone_CDT_CUBA_instance;
  var Timezone_CHADT_instance;
  var Timezone_CHAST_instance;
  var Timezone_CHOT_instance;
  var Timezone_CHOST_instance;
  var Timezone_CHST_instance;
  var Timezone_CHUT_instance;
  var Timezone_CIST_instance;
  var Timezone_CIT_instance;
  var Timezone_CKT_instance;
  var Timezone_CLST_instance;
  var Timezone_CLT_instance;
  var Timezone_COST_instance;
  var Timezone_COT_instance;
  var Timezone_CST_AMERICA_instance;
  var Timezone_CST_CHINA_instance;
  var Timezone_CST_CUBA_instance;
  var Timezone_CT_instance;
  var Timezone_CVT_instance;
  var Timezone_CWST_instance;
  var Timezone_CXT_instance;
  var Timezone_DAVT_instance;
  var Timezone_DDUT_instance;
  var Timezone_DFT_instance;
  var Timezone_EASST_instance;
  var Timezone_EAST_instance;
  var Timezone_EAT_instance;
  var Timezone_ECT_CARIBBEAN_instance;
  var Timezone_ECT_ECUADOR_instance;
  var Timezone_EDT_instance;
  var Timezone_EEST_instance;
  var Timezone_EET_instance;
  var Timezone_EGST_instance;
  var Timezone_EGT_instance;
  var Timezone_EIT_instance;
  var Timezone_EST_instance;
  var Timezone_FET_instance;
  var Timezone_FJT_instance;
  var Timezone_FKST_instance;
  var Timezone_FKT_instance;
  var Timezone_FNT_instance;
  var Timezone_GALT_instance;
  var Timezone_GAMT_instance;
  var Timezone_GET_instance;
  var Timezone_GFT_instance;
  var Timezone_GILT_instance;
  var Timezone_GIT_instance;
  var Timezone_GST_GEORGIA_instance;
  var Timezone_GST_GULF_instance;
  var Timezone_GYT_instance;
  var Timezone_HDT_instance;
  var Timezone_HAEC_instance;
  var Timezone_HST_instance;
  var Timezone_HKT_instance;
  var Timezone_HMT_instance;
  var Timezone_HOVST_instance;
  var Timezone_HOVT_instance;
  var Timezone_ICT_instance;
  var Timezone_IDLW_instance;
  var Timezone_IDT_instance;
  var Timezone_IOT_instance;
  var Timezone_IRDT_instance;
  var Timezone_IRKT_instance;
  var Timezone_IRST_instance;
  var Timezone_IST_INDIA_instance;
  var Timezone_IST_IRISH_instance;
  var Timezone_IST_ISRAEL_instance;
  var Timezone_JST_instance;
  var Timezone_KALT_instance;
  var Timezone_KGT_instance;
  var Timezone_KOST_instance;
  var Timezone_KRAT_instance;
  var Timezone_KST_instance;
  var Timezone_LHST_STD_instance;
  var Timezone_LHST_SUMMER_instance;
  var Timezone_LINT_instance;
  var Timezone_MAGT_instance;
  var Timezone_MART_instance;
  var Timezone_MAWT_instance;
  var Timezone_MDT_instance;
  var Timezone_MET_instance;
  var Timezone_MEST_instance;
  var Timezone_MHT_instance;
  var Timezone_MIST_instance;
  var Timezone_MIT_instance;
  var Timezone_MMT_instance;
  var Timezone_MSK_instance;
  var Timezone_MST_MALAYSIA_instance;
  var Timezone_MST_AMERICA_instance;
  var Timezone_MUT_instance;
  var Timezone_MVT_instance;
  var Timezone_MYT_instance;
  var Timezone_NCT_instance;
  var Timezone_NDT_instance;
  var Timezone_NFT_instance;
  var Timezone_NOVT_instance;
  var Timezone_NPT_instance;
  var Timezone_NST_instance;
  var Timezone_NT_instance;
  var Timezone_NUT_instance;
  var Timezone_NZDT_instance;
  var Timezone_NZST_instance;
  var Timezone_OMST_instance;
  var Timezone_ORAT_instance;
  var Timezone_PET_instance;
  var Timezone_PETT_instance;
  var Timezone_PGT_instance;
  var Timezone_PHOT_instance;
  var Timezone_PHT_instance;
  var Timezone_PKT_instance;
  var Timezone_PMDT_instance;
  var Timezone_PMST_instance;
  var Timezone_PONT_instance;
  var Timezone_PYST_instance;
  var Timezone_PYT_instance;
  var Timezone_RET_instance;
  var Timezone_ROTT_instance;
  var Timezone_SAKT_instance;
  var Timezone_SAMT_instance;
  var Timezone_SAST_instance;
  var Timezone_SBT_instance;
  var Timezone_SCT_instance;
  var Timezone_SDT_instance;
  var Timezone_SGT_instance;
  var Timezone_SLST_instance;
  var Timezone_SRET_instance;
  var Timezone_SRT_instance;
  var Timezone_SST_SAMOA_instance;
  var Timezone_SST_SINGAPORE_instance;
  var Timezone_SYOT_instance;
  var Timezone_TAHT_instance;
  var Timezone_THA_instance;
  var Timezone_TFT_instance;
  var Timezone_TJT_instance;
  var Timezone_TKT_instance;
  var Timezone_TLT_instance;
  var Timezone_TMT_instance;
  var Timezone_TRT_instance;
  var Timezone_TOT_instance;
  var Timezone_TVT_instance;
  var Timezone_ULAST_instance;
  var Timezone_ULAT_instance;
  var Timezone_UYST_instance;
  var Timezone_UYT_instance;
  var Timezone_UZT_instance;
  var Timezone_VET_instance;
  var Timezone_VLAT_instance;
  var Timezone_VOLT_instance;
  var Timezone_VOST_instance;
  var Timezone_VUT_instance;
  var Timezone_WAKT_instance;
  var Timezone_WAST_instance;
  var Timezone_WAT_instance;
  var Timezone_WEST_instance;
  var Timezone_WET_instance;
  var Timezone_WIT_instance;
  var Timezone_WST_instance;
  var Timezone_YAKT_instance;
  var Timezone_YEKT_instance;
  function Timezone_init_$Init$(name, ordinal, abbr, hours, minutes, long, $this) {
    minutes = minutes === VOID ? 0 : minutes;
    long = long === VOID ? abbr : long;
    // Inline function 'korlibs.time.hours' call
    // Inline function 'korlibs.time.hours' call
    var tmp = toDuration(hours, DurationUnit_HOURS_getInstance());
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var tmp$ret$3 = toDuration(minutes, DurationUnit_MINUTES_getInstance());
    Timezone.call($this, name, ordinal, abbr, get_offset(Duration__plus_impl_yu9v8f(tmp, tmp$ret$3)), long);
    return $this;
  }
  function Timezone_init_$Create$(name, ordinal, abbr, hours, minutes, long) {
    return Timezone_init_$Init$(name, ordinal, abbr, hours, minutes, long, objectCreate(protoOf(Timezone)));
  }
  function values_3() {
    return [Timezone_PDT_getInstance(), Timezone_PST_getInstance(), Timezone_GMT_getInstance(), Timezone_UTC_getInstance(), Timezone_CET_getInstance(), Timezone_CEST_getInstance(), Timezone_ACDT_getInstance(), Timezone_ACST_getInstance(), Timezone_ACT_getInstance(), Timezone_ACWST_getInstance(), Timezone_ADT_getInstance(), Timezone_AEDT_getInstance(), Timezone_AEST_getInstance(), Timezone_AFT_getInstance(), Timezone_AKDT_getInstance(), Timezone_AKST_getInstance(), Timezone_ALMT_getInstance(), Timezone_AMST_getInstance(), Timezone_AMT_BRAZIL_getInstance(), Timezone_AMT_ARMENIA_getInstance(), Timezone_ANAT_getInstance(), Timezone_AQTT_getInstance(), Timezone_ART_getInstance(), Timezone_AST_ARABIA_getInstance(), Timezone_AST_ATLANTIC_getInstance(), Timezone_AWST_getInstance(), Timezone_AZOST_getInstance(), Timezone_AZOT_getInstance(), Timezone_AZT_getInstance(), Timezone_BDT_getInstance(), Timezone_BIOT_getInstance(), Timezone_BIT_getInstance(), Timezone_BOT_getInstance(), Timezone_BRST_getInstance(), Timezone_BRT_getInstance(), Timezone_BST_BANGLADESH_getInstance(), Timezone_BST_BOUGAINVILLE_getInstance(), Timezone_BST_BRITISH_getInstance(), Timezone_BTT_getInstance(), Timezone_CAT_getInstance(), Timezone_CCT_getInstance(), Timezone_CDT_AMERICA_getInstance(), Timezone_CDT_CUBA_getInstance(), Timezone_CHADT_getInstance(), Timezone_CHAST_getInstance(), Timezone_CHOT_getInstance(), Timezone_CHOST_getInstance(), Timezone_CHST_getInstance(), Timezone_CHUT_getInstance(), Timezone_CIST_getInstance(), Timezone_CIT_getInstance(), Timezone_CKT_getInstance(), Timezone_CLST_getInstance(), Timezone_CLT_getInstance(), Timezone_COST_getInstance(), Timezone_COT_getInstance(), Timezone_CST_AMERICA_getInstance(), Timezone_CST_CHINA_getInstance(), Timezone_CST_CUBA_getInstance(), Timezone_CT_getInstance(), Timezone_CVT_getInstance(), Timezone_CWST_getInstance(), Timezone_CXT_getInstance(), Timezone_DAVT_getInstance(), Timezone_DDUT_getInstance(), Timezone_DFT_getInstance(), Timezone_EASST_getInstance(), Timezone_EAST_getInstance(), Timezone_EAT_getInstance(), Timezone_ECT_CARIBBEAN_getInstance(), Timezone_ECT_ECUADOR_getInstance(), Timezone_EDT_getInstance(), Timezone_EEST_getInstance(), Timezone_EET_getInstance(), Timezone_EGST_getInstance(), Timezone_EGT_getInstance(), Timezone_EIT_getInstance(), Timezone_EST_getInstance(), Timezone_FET_getInstance(), Timezone_FJT_getInstance(), Timezone_FKST_getInstance(), Timezone_FKT_getInstance(), Timezone_FNT_getInstance(), Timezone_GALT_getInstance(), Timezone_GAMT_getInstance(), Timezone_GET_getInstance(), Timezone_GFT_getInstance(), Timezone_GILT_getInstance(), Timezone_GIT_getInstance(), Timezone_GST_GEORGIA_getInstance(), Timezone_GST_GULF_getInstance(), Timezone_GYT_getInstance(), Timezone_HDT_getInstance(), Timezone_HAEC_getInstance(), Timezone_HST_getInstance(), Timezone_HKT_getInstance(), Timezone_HMT_getInstance(), Timezone_HOVST_getInstance(), Timezone_HOVT_getInstance(), Timezone_ICT_getInstance(), Timezone_IDLW_getInstance(), Timezone_IDT_getInstance(), Timezone_IOT_getInstance(), Timezone_IRDT_getInstance(), Timezone_IRKT_getInstance(), Timezone_IRST_getInstance(), Timezone_IST_INDIA_getInstance(), Timezone_IST_IRISH_getInstance(), Timezone_IST_ISRAEL_getInstance(), Timezone_JST_getInstance(), Timezone_KALT_getInstance(), Timezone_KGT_getInstance(), Timezone_KOST_getInstance(), Timezone_KRAT_getInstance(), Timezone_KST_getInstance(), Timezone_LHST_STD_getInstance(), Timezone_LHST_SUMMER_getInstance(), Timezone_LINT_getInstance(), Timezone_MAGT_getInstance(), Timezone_MART_getInstance(), Timezone_MAWT_getInstance(), Timezone_MDT_getInstance(), Timezone_MET_getInstance(), Timezone_MEST_getInstance(), Timezone_MHT_getInstance(), Timezone_MIST_getInstance(), Timezone_MIT_getInstance(), Timezone_MMT_getInstance(), Timezone_MSK_getInstance(), Timezone_MST_MALAYSIA_getInstance(), Timezone_MST_AMERICA_getInstance(), Timezone_MUT_getInstance(), Timezone_MVT_getInstance(), Timezone_MYT_getInstance(), Timezone_NCT_getInstance(), Timezone_NDT_getInstance(), Timezone_NFT_getInstance(), Timezone_NOVT_getInstance(), Timezone_NPT_getInstance(), Timezone_NST_getInstance(), Timezone_NT_getInstance(), Timezone_NUT_getInstance(), Timezone_NZDT_getInstance(), Timezone_NZST_getInstance(), Timezone_OMST_getInstance(), Timezone_ORAT_getInstance(), Timezone_PET_getInstance(), Timezone_PETT_getInstance(), Timezone_PGT_getInstance(), Timezone_PHOT_getInstance(), Timezone_PHT_getInstance(), Timezone_PKT_getInstance(), Timezone_PMDT_getInstance(), Timezone_PMST_getInstance(), Timezone_PONT_getInstance(), Timezone_PYST_getInstance(), Timezone_PYT_getInstance(), Timezone_RET_getInstance(), Timezone_ROTT_getInstance(), Timezone_SAKT_getInstance(), Timezone_SAMT_getInstance(), Timezone_SAST_getInstance(), Timezone_SBT_getInstance(), Timezone_SCT_getInstance(), Timezone_SDT_getInstance(), Timezone_SGT_getInstance(), Timezone_SLST_getInstance(), Timezone_SRET_getInstance(), Timezone_SRT_getInstance(), Timezone_SST_SAMOA_getInstance(), Timezone_SST_SINGAPORE_getInstance(), Timezone_SYOT_getInstance(), Timezone_TAHT_getInstance(), Timezone_THA_getInstance(), Timezone_TFT_getInstance(), Timezone_TJT_getInstance(), Timezone_TKT_getInstance(), Timezone_TLT_getInstance(), Timezone_TMT_getInstance(), Timezone_TRT_getInstance(), Timezone_TOT_getInstance(), Timezone_TVT_getInstance(), Timezone_ULAST_getInstance(), Timezone_ULAT_getInstance(), Timezone_UYST_getInstance(), Timezone_UYT_getInstance(), Timezone_UZT_getInstance(), Timezone_VET_getInstance(), Timezone_VLAT_getInstance(), Timezone_VOLT_getInstance(), Timezone_VOST_getInstance(), Timezone_VUT_getInstance(), Timezone_WAKT_getInstance(), Timezone_WAST_getInstance(), Timezone_WAT_getInstance(), Timezone_WEST_getInstance(), Timezone_WET_getInstance(), Timezone_WIT_getInstance(), Timezone_WST_getInstance(), Timezone_YAKT_getInstance(), Timezone_YEKT_getInstance()];
  }
  function valueOf_3(value) {
    switch (value) {
      case 'PDT':
        return Timezone_PDT_getInstance();
      case 'PST':
        return Timezone_PST_getInstance();
      case 'GMT':
        return Timezone_GMT_getInstance();
      case 'UTC':
        return Timezone_UTC_getInstance();
      case 'CET':
        return Timezone_CET_getInstance();
      case 'CEST':
        return Timezone_CEST_getInstance();
      case 'ACDT':
        return Timezone_ACDT_getInstance();
      case 'ACST':
        return Timezone_ACST_getInstance();
      case 'ACT':
        return Timezone_ACT_getInstance();
      case 'ACWST':
        return Timezone_ACWST_getInstance();
      case 'ADT':
        return Timezone_ADT_getInstance();
      case 'AEDT':
        return Timezone_AEDT_getInstance();
      case 'AEST':
        return Timezone_AEST_getInstance();
      case 'AFT':
        return Timezone_AFT_getInstance();
      case 'AKDT':
        return Timezone_AKDT_getInstance();
      case 'AKST':
        return Timezone_AKST_getInstance();
      case 'ALMT':
        return Timezone_ALMT_getInstance();
      case 'AMST':
        return Timezone_AMST_getInstance();
      case 'AMT_BRAZIL':
        return Timezone_AMT_BRAZIL_getInstance();
      case 'AMT_ARMENIA':
        return Timezone_AMT_ARMENIA_getInstance();
      case 'ANAT':
        return Timezone_ANAT_getInstance();
      case 'AQTT':
        return Timezone_AQTT_getInstance();
      case 'ART':
        return Timezone_ART_getInstance();
      case 'AST_ARABIA':
        return Timezone_AST_ARABIA_getInstance();
      case 'AST_ATLANTIC':
        return Timezone_AST_ATLANTIC_getInstance();
      case 'AWST':
        return Timezone_AWST_getInstance();
      case 'AZOST':
        return Timezone_AZOST_getInstance();
      case 'AZOT':
        return Timezone_AZOT_getInstance();
      case 'AZT':
        return Timezone_AZT_getInstance();
      case 'BDT':
        return Timezone_BDT_getInstance();
      case 'BIOT':
        return Timezone_BIOT_getInstance();
      case 'BIT':
        return Timezone_BIT_getInstance();
      case 'BOT':
        return Timezone_BOT_getInstance();
      case 'BRST':
        return Timezone_BRST_getInstance();
      case 'BRT':
        return Timezone_BRT_getInstance();
      case 'BST_BANGLADESH':
        return Timezone_BST_BANGLADESH_getInstance();
      case 'BST_BOUGAINVILLE':
        return Timezone_BST_BOUGAINVILLE_getInstance();
      case 'BST_BRITISH':
        return Timezone_BST_BRITISH_getInstance();
      case 'BTT':
        return Timezone_BTT_getInstance();
      case 'CAT':
        return Timezone_CAT_getInstance();
      case 'CCT':
        return Timezone_CCT_getInstance();
      case 'CDT_AMERICA':
        return Timezone_CDT_AMERICA_getInstance();
      case 'CDT_CUBA':
        return Timezone_CDT_CUBA_getInstance();
      case 'CHADT':
        return Timezone_CHADT_getInstance();
      case 'CHAST':
        return Timezone_CHAST_getInstance();
      case 'CHOT':
        return Timezone_CHOT_getInstance();
      case 'CHOST':
        return Timezone_CHOST_getInstance();
      case 'CHST':
        return Timezone_CHST_getInstance();
      case 'CHUT':
        return Timezone_CHUT_getInstance();
      case 'CIST':
        return Timezone_CIST_getInstance();
      case 'CIT':
        return Timezone_CIT_getInstance();
      case 'CKT':
        return Timezone_CKT_getInstance();
      case 'CLST':
        return Timezone_CLST_getInstance();
      case 'CLT':
        return Timezone_CLT_getInstance();
      case 'COST':
        return Timezone_COST_getInstance();
      case 'COT':
        return Timezone_COT_getInstance();
      case 'CST_AMERICA':
        return Timezone_CST_AMERICA_getInstance();
      case 'CST_CHINA':
        return Timezone_CST_CHINA_getInstance();
      case 'CST_CUBA':
        return Timezone_CST_CUBA_getInstance();
      case 'CT':
        return Timezone_CT_getInstance();
      case 'CVT':
        return Timezone_CVT_getInstance();
      case 'CWST':
        return Timezone_CWST_getInstance();
      case 'CXT':
        return Timezone_CXT_getInstance();
      case 'DAVT':
        return Timezone_DAVT_getInstance();
      case 'DDUT':
        return Timezone_DDUT_getInstance();
      case 'DFT':
        return Timezone_DFT_getInstance();
      case 'EASST':
        return Timezone_EASST_getInstance();
      case 'EAST':
        return Timezone_EAST_getInstance();
      case 'EAT':
        return Timezone_EAT_getInstance();
      case 'ECT_CARIBBEAN':
        return Timezone_ECT_CARIBBEAN_getInstance();
      case 'ECT_ECUADOR':
        return Timezone_ECT_ECUADOR_getInstance();
      case 'EDT':
        return Timezone_EDT_getInstance();
      case 'EEST':
        return Timezone_EEST_getInstance();
      case 'EET':
        return Timezone_EET_getInstance();
      case 'EGST':
        return Timezone_EGST_getInstance();
      case 'EGT':
        return Timezone_EGT_getInstance();
      case 'EIT':
        return Timezone_EIT_getInstance();
      case 'EST':
        return Timezone_EST_getInstance();
      case 'FET':
        return Timezone_FET_getInstance();
      case 'FJT':
        return Timezone_FJT_getInstance();
      case 'FKST':
        return Timezone_FKST_getInstance();
      case 'FKT':
        return Timezone_FKT_getInstance();
      case 'FNT':
        return Timezone_FNT_getInstance();
      case 'GALT':
        return Timezone_GALT_getInstance();
      case 'GAMT':
        return Timezone_GAMT_getInstance();
      case 'GET':
        return Timezone_GET_getInstance();
      case 'GFT':
        return Timezone_GFT_getInstance();
      case 'GILT':
        return Timezone_GILT_getInstance();
      case 'GIT':
        return Timezone_GIT_getInstance();
      case 'GST_GEORGIA':
        return Timezone_GST_GEORGIA_getInstance();
      case 'GST_GULF':
        return Timezone_GST_GULF_getInstance();
      case 'GYT':
        return Timezone_GYT_getInstance();
      case 'HDT':
        return Timezone_HDT_getInstance();
      case 'HAEC':
        return Timezone_HAEC_getInstance();
      case 'HST':
        return Timezone_HST_getInstance();
      case 'HKT':
        return Timezone_HKT_getInstance();
      case 'HMT':
        return Timezone_HMT_getInstance();
      case 'HOVST':
        return Timezone_HOVST_getInstance();
      case 'HOVT':
        return Timezone_HOVT_getInstance();
      case 'ICT':
        return Timezone_ICT_getInstance();
      case 'IDLW':
        return Timezone_IDLW_getInstance();
      case 'IDT':
        return Timezone_IDT_getInstance();
      case 'IOT':
        return Timezone_IOT_getInstance();
      case 'IRDT':
        return Timezone_IRDT_getInstance();
      case 'IRKT':
        return Timezone_IRKT_getInstance();
      case 'IRST':
        return Timezone_IRST_getInstance();
      case 'IST_INDIA':
        return Timezone_IST_INDIA_getInstance();
      case 'IST_IRISH':
        return Timezone_IST_IRISH_getInstance();
      case 'IST_ISRAEL':
        return Timezone_IST_ISRAEL_getInstance();
      case 'JST':
        return Timezone_JST_getInstance();
      case 'KALT':
        return Timezone_KALT_getInstance();
      case 'KGT':
        return Timezone_KGT_getInstance();
      case 'KOST':
        return Timezone_KOST_getInstance();
      case 'KRAT':
        return Timezone_KRAT_getInstance();
      case 'KST':
        return Timezone_KST_getInstance();
      case 'LHST_STD':
        return Timezone_LHST_STD_getInstance();
      case 'LHST_SUMMER':
        return Timezone_LHST_SUMMER_getInstance();
      case 'LINT':
        return Timezone_LINT_getInstance();
      case 'MAGT':
        return Timezone_MAGT_getInstance();
      case 'MART':
        return Timezone_MART_getInstance();
      case 'MAWT':
        return Timezone_MAWT_getInstance();
      case 'MDT':
        return Timezone_MDT_getInstance();
      case 'MET':
        return Timezone_MET_getInstance();
      case 'MEST':
        return Timezone_MEST_getInstance();
      case 'MHT':
        return Timezone_MHT_getInstance();
      case 'MIST':
        return Timezone_MIST_getInstance();
      case 'MIT':
        return Timezone_MIT_getInstance();
      case 'MMT':
        return Timezone_MMT_getInstance();
      case 'MSK':
        return Timezone_MSK_getInstance();
      case 'MST_MALAYSIA':
        return Timezone_MST_MALAYSIA_getInstance();
      case 'MST_AMERICA':
        return Timezone_MST_AMERICA_getInstance();
      case 'MUT':
        return Timezone_MUT_getInstance();
      case 'MVT':
        return Timezone_MVT_getInstance();
      case 'MYT':
        return Timezone_MYT_getInstance();
      case 'NCT':
        return Timezone_NCT_getInstance();
      case 'NDT':
        return Timezone_NDT_getInstance();
      case 'NFT':
        return Timezone_NFT_getInstance();
      case 'NOVT':
        return Timezone_NOVT_getInstance();
      case 'NPT':
        return Timezone_NPT_getInstance();
      case 'NST':
        return Timezone_NST_getInstance();
      case 'NT':
        return Timezone_NT_getInstance();
      case 'NUT':
        return Timezone_NUT_getInstance();
      case 'NZDT':
        return Timezone_NZDT_getInstance();
      case 'NZST':
        return Timezone_NZST_getInstance();
      case 'OMST':
        return Timezone_OMST_getInstance();
      case 'ORAT':
        return Timezone_ORAT_getInstance();
      case 'PET':
        return Timezone_PET_getInstance();
      case 'PETT':
        return Timezone_PETT_getInstance();
      case 'PGT':
        return Timezone_PGT_getInstance();
      case 'PHOT':
        return Timezone_PHOT_getInstance();
      case 'PHT':
        return Timezone_PHT_getInstance();
      case 'PKT':
        return Timezone_PKT_getInstance();
      case 'PMDT':
        return Timezone_PMDT_getInstance();
      case 'PMST':
        return Timezone_PMST_getInstance();
      case 'PONT':
        return Timezone_PONT_getInstance();
      case 'PYST':
        return Timezone_PYST_getInstance();
      case 'PYT':
        return Timezone_PYT_getInstance();
      case 'RET':
        return Timezone_RET_getInstance();
      case 'ROTT':
        return Timezone_ROTT_getInstance();
      case 'SAKT':
        return Timezone_SAKT_getInstance();
      case 'SAMT':
        return Timezone_SAMT_getInstance();
      case 'SAST':
        return Timezone_SAST_getInstance();
      case 'SBT':
        return Timezone_SBT_getInstance();
      case 'SCT':
        return Timezone_SCT_getInstance();
      case 'SDT':
        return Timezone_SDT_getInstance();
      case 'SGT':
        return Timezone_SGT_getInstance();
      case 'SLST':
        return Timezone_SLST_getInstance();
      case 'SRET':
        return Timezone_SRET_getInstance();
      case 'SRT':
        return Timezone_SRT_getInstance();
      case 'SST_SAMOA':
        return Timezone_SST_SAMOA_getInstance();
      case 'SST_SINGAPORE':
        return Timezone_SST_SINGAPORE_getInstance();
      case 'SYOT':
        return Timezone_SYOT_getInstance();
      case 'TAHT':
        return Timezone_TAHT_getInstance();
      case 'THA':
        return Timezone_THA_getInstance();
      case 'TFT':
        return Timezone_TFT_getInstance();
      case 'TJT':
        return Timezone_TJT_getInstance();
      case 'TKT':
        return Timezone_TKT_getInstance();
      case 'TLT':
        return Timezone_TLT_getInstance();
      case 'TMT':
        return Timezone_TMT_getInstance();
      case 'TRT':
        return Timezone_TRT_getInstance();
      case 'TOT':
        return Timezone_TOT_getInstance();
      case 'TVT':
        return Timezone_TVT_getInstance();
      case 'ULAST':
        return Timezone_ULAST_getInstance();
      case 'ULAT':
        return Timezone_ULAT_getInstance();
      case 'UYST':
        return Timezone_UYST_getInstance();
      case 'UYT':
        return Timezone_UYT_getInstance();
      case 'UZT':
        return Timezone_UZT_getInstance();
      case 'VET':
        return Timezone_VET_getInstance();
      case 'VLAT':
        return Timezone_VLAT_getInstance();
      case 'VOLT':
        return Timezone_VOLT_getInstance();
      case 'VOST':
        return Timezone_VOST_getInstance();
      case 'VUT':
        return Timezone_VUT_getInstance();
      case 'WAKT':
        return Timezone_WAKT_getInstance();
      case 'WAST':
        return Timezone_WAST_getInstance();
      case 'WAT':
        return Timezone_WAT_getInstance();
      case 'WEST':
        return Timezone_WEST_getInstance();
      case 'WET':
        return Timezone_WET_getInstance();
      case 'WIT':
        return Timezone_WIT_getInstance();
      case 'WST':
        return Timezone_WST_getInstance();
      case 'YAKT':
        return Timezone_YAKT_getInstance();
      case 'YEKT':
        return Timezone_YEKT_getInstance();
      default:
        Timezone_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_3() {
    if ($ENTRIES_3 == null)
      $ENTRIES_3 = enumEntries(values_3());
    return $ENTRIES_3;
  }
  var Timezone_entriesInitialized;
  function Timezone_initEntries() {
    if (Timezone_entriesInitialized)
      return Unit_getInstance();
    Timezone_entriesInitialized = true;
    Timezone_PDT_instance = Timezone_init_$Create$('PDT', 0, 'PDT', -7);
    Timezone_PST_instance = Timezone_init_$Create$('PST', 1, 'PST', -8);
    Timezone_GMT_instance = Timezone_init_$Create$('GMT', 2, 'GMT', 0);
    Timezone_UTC_instance = Timezone_init_$Create$('UTC', 3, 'UTC', 0);
    Timezone_CET_instance = Timezone_init_$Create$('CET', 4, 'CET', 1);
    Timezone_CEST_instance = Timezone_init_$Create$('CEST', 5, 'CEST', 2);
    Timezone_ACDT_instance = Timezone_init_$Create$('ACDT', 6, 'ACDT', 10, 30);
    Timezone_ACST_instance = Timezone_init_$Create$('ACST', 7, 'ACST', 9, 30);
    Timezone_ACT_instance = Timezone_init_$Create$('ACT', 8, 'ACT', -5);
    Timezone_ACWST_instance = Timezone_init_$Create$('ACWST', 9, 'ACWST', 8, 45);
    Timezone_ADT_instance = Timezone_init_$Create$('ADT', 10, 'ADT', -3);
    Timezone_AEDT_instance = Timezone_init_$Create$('AEDT', 11, 'AEDT', 11);
    Timezone_AEST_instance = Timezone_init_$Create$('AEST', 12, 'AEST', 10);
    Timezone_AFT_instance = Timezone_init_$Create$('AFT', 13, 'AFT', 4, 30);
    Timezone_AKDT_instance = Timezone_init_$Create$('AKDT', 14, 'AKDT', -8);
    Timezone_AKST_instance = Timezone_init_$Create$('AKST', 15, 'AKST', -9);
    Timezone_ALMT_instance = Timezone_init_$Create$('ALMT', 16, 'ALMT', 6);
    Timezone_AMST_instance = Timezone_init_$Create$('AMST', 17, 'AMST', -3);
    Timezone_AMT_BRAZIL_instance = Timezone_init_$Create$('AMT_BRAZIL', 18, 'AMT', -4, VOID, 'AMT_BRAZIL');
    Timezone_AMT_ARMENIA_instance = Timezone_init_$Create$('AMT_ARMENIA', 19, 'AMT', 4, VOID, 'AMT_ARMENIA');
    Timezone_ANAT_instance = Timezone_init_$Create$('ANAT', 20, 'ANAT', 12);
    Timezone_AQTT_instance = Timezone_init_$Create$('AQTT', 21, 'AQTT', 5);
    Timezone_ART_instance = Timezone_init_$Create$('ART', 22, 'ART', -3);
    Timezone_AST_ARABIA_instance = Timezone_init_$Create$('AST_ARABIA', 23, 'AST', 3, VOID, 'AST_ARABIA');
    Timezone_AST_ATLANTIC_instance = Timezone_init_$Create$('AST_ATLANTIC', 24, 'AST', -4, VOID, 'AST_ATLANTIC');
    Timezone_AWST_instance = Timezone_init_$Create$('AWST', 25, 'AWST', 8);
    Timezone_AZOST_instance = Timezone_init_$Create$('AZOST', 26, 'AZOST', 0);
    Timezone_AZOT_instance = Timezone_init_$Create$('AZOT', 27, 'AZOT', -1);
    Timezone_AZT_instance = Timezone_init_$Create$('AZT', 28, 'AZT', 4);
    Timezone_BDT_instance = Timezone_init_$Create$('BDT', 29, 'BDT', 8);
    Timezone_BIOT_instance = Timezone_init_$Create$('BIOT', 30, 'BIOT', 6);
    Timezone_BIT_instance = Timezone_init_$Create$('BIT', 31, 'BIT', -12);
    Timezone_BOT_instance = Timezone_init_$Create$('BOT', 32, 'BOT', -4);
    Timezone_BRST_instance = Timezone_init_$Create$('BRST', 33, 'BRST', -2);
    Timezone_BRT_instance = Timezone_init_$Create$('BRT', 34, 'BRT', -3);
    Timezone_BST_BANGLADESH_instance = Timezone_init_$Create$('BST_BANGLADESH', 35, 'BST', 6, VOID, 'BST_BANGLADESH');
    Timezone_BST_BOUGAINVILLE_instance = Timezone_init_$Create$('BST_BOUGAINVILLE', 36, 'BST', 11, VOID, 'BOUGAINVILLE');
    Timezone_BST_BRITISH_instance = Timezone_init_$Create$('BST_BRITISH', 37, 'BST', 1, VOID, 'BRITISH');
    Timezone_BTT_instance = Timezone_init_$Create$('BTT', 38, 'BTT', 6);
    Timezone_CAT_instance = Timezone_init_$Create$('CAT', 39, 'CAT', 2);
    Timezone_CCT_instance = Timezone_init_$Create$('CCT', 40, 'CCT', 6, 30);
    Timezone_CDT_AMERICA_instance = Timezone_init_$Create$('CDT_AMERICA', 41, 'CDT', -5, VOID, 'CDT_AMERICA');
    Timezone_CDT_CUBA_instance = Timezone_init_$Create$('CDT_CUBA', 42, 'CDT', -4, VOID, 'CDT_CUBA');
    Timezone_CHADT_instance = Timezone_init_$Create$('CHADT', 43, 'CHADT', 13, 45);
    Timezone_CHAST_instance = Timezone_init_$Create$('CHAST', 44, 'CHAST', 12, 45);
    Timezone_CHOT_instance = Timezone_init_$Create$('CHOT', 45, 'CHOT', 8);
    Timezone_CHOST_instance = Timezone_init_$Create$('CHOST', 46, 'CHOST', 9);
    Timezone_CHST_instance = Timezone_init_$Create$('CHST', 47, 'CHST', 10);
    Timezone_CHUT_instance = Timezone_init_$Create$('CHUT', 48, 'CHUT', 10);
    Timezone_CIST_instance = Timezone_init_$Create$('CIST', 49, 'CIST', -8);
    Timezone_CIT_instance = Timezone_init_$Create$('CIT', 50, 'CIT', 8);
    Timezone_CKT_instance = Timezone_init_$Create$('CKT', 51, 'CKT', -10);
    Timezone_CLST_instance = Timezone_init_$Create$('CLST', 52, 'CLST', -3);
    Timezone_CLT_instance = Timezone_init_$Create$('CLT', 53, 'CLT', -4);
    Timezone_COST_instance = Timezone_init_$Create$('COST', 54, 'COST', -4);
    Timezone_COT_instance = Timezone_init_$Create$('COT', 55, 'COT', -5);
    Timezone_CST_AMERICA_instance = Timezone_init_$Create$('CST_AMERICA', 56, 'CST', -6, VOID, 'CST_AMERICA');
    Timezone_CST_CHINA_instance = Timezone_init_$Create$('CST_CHINA', 57, 'CST', 8, VOID, 'CST_CHINA');
    Timezone_CST_CUBA_instance = Timezone_init_$Create$('CST_CUBA', 58, 'CST', -5, VOID, 'CST_CUBA');
    Timezone_CT_instance = Timezone_init_$Create$('CT', 59, 'CT', 8);
    Timezone_CVT_instance = Timezone_init_$Create$('CVT', 60, 'CVT', -1);
    Timezone_CWST_instance = Timezone_init_$Create$('CWST', 61, 'CWST', 8, 45);
    Timezone_CXT_instance = Timezone_init_$Create$('CXT', 62, 'CXT', 7);
    Timezone_DAVT_instance = Timezone_init_$Create$('DAVT', 63, 'DAVT', 7);
    Timezone_DDUT_instance = Timezone_init_$Create$('DDUT', 64, 'DDUT', 10);
    Timezone_DFT_instance = Timezone_init_$Create$('DFT', 65, 'DFT', 1);
    Timezone_EASST_instance = Timezone_init_$Create$('EASST', 66, 'EASST', -5);
    Timezone_EAST_instance = Timezone_init_$Create$('EAST', 67, 'EAST', -6);
    Timezone_EAT_instance = Timezone_init_$Create$('EAT', 68, 'EAT', 3);
    Timezone_ECT_CARIBBEAN_instance = Timezone_init_$Create$('ECT_CARIBBEAN', 69, 'ECT', -4, VOID, 'ECT_CARIBBEAN');
    Timezone_ECT_ECUADOR_instance = Timezone_init_$Create$('ECT_ECUADOR', 70, 'ECT', -5, VOID, 'ECT_ECUADOR');
    Timezone_EDT_instance = Timezone_init_$Create$('EDT', 71, 'EDT', -4);
    Timezone_EEST_instance = Timezone_init_$Create$('EEST', 72, 'EEST', 3);
    Timezone_EET_instance = Timezone_init_$Create$('EET', 73, 'EET', 2);
    Timezone_EGST_instance = Timezone_init_$Create$('EGST', 74, 'EGST', 0);
    Timezone_EGT_instance = Timezone_init_$Create$('EGT', 75, 'EGT', -1);
    Timezone_EIT_instance = Timezone_init_$Create$('EIT', 76, 'EIT', 9);
    Timezone_EST_instance = Timezone_init_$Create$('EST', 77, 'EST', -5);
    Timezone_FET_instance = Timezone_init_$Create$('FET', 78, 'FET', 3);
    Timezone_FJT_instance = Timezone_init_$Create$('FJT', 79, 'FJT', 12);
    Timezone_FKST_instance = Timezone_init_$Create$('FKST', 80, 'FKST', -3);
    Timezone_FKT_instance = Timezone_init_$Create$('FKT', 81, 'FKT', -4);
    Timezone_FNT_instance = Timezone_init_$Create$('FNT', 82, 'FNT', -2);
    Timezone_GALT_instance = Timezone_init_$Create$('GALT', 83, 'GALT', -6);
    Timezone_GAMT_instance = Timezone_init_$Create$('GAMT', 84, 'GAMT', -9);
    Timezone_GET_instance = Timezone_init_$Create$('GET', 85, 'GET', 4);
    Timezone_GFT_instance = Timezone_init_$Create$('GFT', 86, 'GFT', -3);
    Timezone_GILT_instance = Timezone_init_$Create$('GILT', 87, 'GILT', 12);
    Timezone_GIT_instance = Timezone_init_$Create$('GIT', 88, 'GIT', -9);
    Timezone_GST_GEORGIA_instance = Timezone_init_$Create$('GST_GEORGIA', 89, 'GST', -2, VOID, 'GST_GEORGIA');
    Timezone_GST_GULF_instance = Timezone_init_$Create$('GST_GULF', 90, 'GST', 4, VOID, 'GST_GULF');
    Timezone_GYT_instance = Timezone_init_$Create$('GYT', 91, 'GYT', -4);
    Timezone_HDT_instance = Timezone_init_$Create$('HDT', 92, 'HDT', -9);
    Timezone_HAEC_instance = Timezone_init_$Create$('HAEC', 93, 'HAEC', 2);
    Timezone_HST_instance = Timezone_init_$Create$('HST', 94, 'HST', -10);
    Timezone_HKT_instance = Timezone_init_$Create$('HKT', 95, 'HKT', 8);
    Timezone_HMT_instance = Timezone_init_$Create$('HMT', 96, 'HMT', 5);
    Timezone_HOVST_instance = Timezone_init_$Create$('HOVST', 97, 'HOVST', 8);
    Timezone_HOVT_instance = Timezone_init_$Create$('HOVT', 98, 'HOVT', 7);
    Timezone_ICT_instance = Timezone_init_$Create$('ICT', 99, 'ICT', 7);
    Timezone_IDLW_instance = Timezone_init_$Create$('IDLW', 100, 'IDLW', -12);
    Timezone_IDT_instance = Timezone_init_$Create$('IDT', 101, 'IDT', 3);
    Timezone_IOT_instance = Timezone_init_$Create$('IOT', 102, 'IOT', 3);
    Timezone_IRDT_instance = Timezone_init_$Create$('IRDT', 103, 'IRDT', 4, 30);
    Timezone_IRKT_instance = Timezone_init_$Create$('IRKT', 104, 'IRKT', 8);
    Timezone_IRST_instance = Timezone_init_$Create$('IRST', 105, 'IRST', 3, 30);
    Timezone_IST_INDIA_instance = Timezone_init_$Create$('IST_INDIA', 106, 'IST', 5, 30, 'IST_INDIA');
    Timezone_IST_IRISH_instance = Timezone_init_$Create$('IST_IRISH', 107, 'IST', 1, VOID, 'IST_IRISH');
    Timezone_IST_ISRAEL_instance = Timezone_init_$Create$('IST_ISRAEL', 108, 'IST', 2, VOID, 'IST_ISRAEL');
    Timezone_JST_instance = Timezone_init_$Create$('JST', 109, 'JST', 9);
    Timezone_KALT_instance = Timezone_init_$Create$('KALT', 110, 'KALT', 2);
    Timezone_KGT_instance = Timezone_init_$Create$('KGT', 111, 'KGT', 6);
    Timezone_KOST_instance = Timezone_init_$Create$('KOST', 112, 'KOST', 11);
    Timezone_KRAT_instance = Timezone_init_$Create$('KRAT', 113, 'KRAT', 7);
    Timezone_KST_instance = Timezone_init_$Create$('KST', 114, 'KST', 9);
    Timezone_LHST_STD_instance = Timezone_init_$Create$('LHST_STD', 115, 'LHST', 10, 30, 'LHST_STD');
    Timezone_LHST_SUMMER_instance = Timezone_init_$Create$('LHST_SUMMER', 116, 'LHST', 11, VOID, 'LHST_SUMMER');
    Timezone_LINT_instance = Timezone_init_$Create$('LINT', 117, 'LINT', 14);
    Timezone_MAGT_instance = Timezone_init_$Create$('MAGT', 118, 'MAGT', 12);
    Timezone_MART_instance = Timezone_init_$Create$('MART', 119, 'MART', -9, 30);
    Timezone_MAWT_instance = Timezone_init_$Create$('MAWT', 120, 'MAWT', 5);
    Timezone_MDT_instance = Timezone_init_$Create$('MDT', 121, 'MDT', -6);
    Timezone_MET_instance = Timezone_init_$Create$('MET', 122, 'MET', 1);
    Timezone_MEST_instance = Timezone_init_$Create$('MEST', 123, 'MEST', 2);
    Timezone_MHT_instance = Timezone_init_$Create$('MHT', 124, 'MHT', 12);
    Timezone_MIST_instance = Timezone_init_$Create$('MIST', 125, 'MIST', 11);
    Timezone_MIT_instance = Timezone_init_$Create$('MIT', 126, 'MIT', -9, 30);
    Timezone_MMT_instance = Timezone_init_$Create$('MMT', 127, 'MMT', 6, 30);
    Timezone_MSK_instance = Timezone_init_$Create$('MSK', 128, 'MSK', 3);
    Timezone_MST_MALAYSIA_instance = Timezone_init_$Create$('MST_MALAYSIA', 129, 'MST_', 8, VOID, 'MST_MALAYSIA');
    Timezone_MST_AMERICA_instance = Timezone_init_$Create$('MST_AMERICA', 130, 'MST', -7, VOID, 'MST_AMERICA');
    Timezone_MUT_instance = Timezone_init_$Create$('MUT', 131, 'MUT', 4);
    Timezone_MVT_instance = Timezone_init_$Create$('MVT', 132, 'MVT', 5);
    Timezone_MYT_instance = Timezone_init_$Create$('MYT', 133, 'MYT', 8);
    Timezone_NCT_instance = Timezone_init_$Create$('NCT', 134, 'NCT', 11);
    Timezone_NDT_instance = Timezone_init_$Create$('NDT', 135, 'NDT', -2, 30);
    Timezone_NFT_instance = Timezone_init_$Create$('NFT', 136, 'NFT', 11);
    Timezone_NOVT_instance = Timezone_init_$Create$('NOVT', 137, 'NOVT', 7);
    Timezone_NPT_instance = Timezone_init_$Create$('NPT', 138, 'NPT', 5, 45);
    Timezone_NST_instance = Timezone_init_$Create$('NST', 139, 'NST', -3, 30);
    Timezone_NT_instance = Timezone_init_$Create$('NT', 140, 'NT', -3, 30);
    Timezone_NUT_instance = Timezone_init_$Create$('NUT', 141, 'NUT', -11);
    Timezone_NZDT_instance = Timezone_init_$Create$('NZDT', 142, 'NZDT', 13);
    Timezone_NZST_instance = Timezone_init_$Create$('NZST', 143, 'NZST', 12);
    Timezone_OMST_instance = Timezone_init_$Create$('OMST', 144, 'OMST', 6);
    Timezone_ORAT_instance = Timezone_init_$Create$('ORAT', 145, 'ORAT', 5);
    Timezone_PET_instance = Timezone_init_$Create$('PET', 146, 'PET', -5);
    Timezone_PETT_instance = Timezone_init_$Create$('PETT', 147, 'PETT', 12);
    Timezone_PGT_instance = Timezone_init_$Create$('PGT', 148, 'PGT', 10);
    Timezone_PHOT_instance = Timezone_init_$Create$('PHOT', 149, 'PHOT', 13);
    Timezone_PHT_instance = Timezone_init_$Create$('PHT', 150, 'PHT', 8);
    Timezone_PKT_instance = Timezone_init_$Create$('PKT', 151, 'PKT', 5);
    Timezone_PMDT_instance = Timezone_init_$Create$('PMDT', 152, 'PMDT', -2);
    Timezone_PMST_instance = Timezone_init_$Create$('PMST', 153, 'PMST', -3);
    Timezone_PONT_instance = Timezone_init_$Create$('PONT', 154, 'PONT', 11);
    Timezone_PYST_instance = Timezone_init_$Create$('PYST', 155, 'PYST', -3);
    Timezone_PYT_instance = Timezone_init_$Create$('PYT', 156, 'PYT', -4);
    Timezone_RET_instance = Timezone_init_$Create$('RET', 157, 'RET', 4);
    Timezone_ROTT_instance = Timezone_init_$Create$('ROTT', 158, 'ROTT', -3);
    Timezone_SAKT_instance = Timezone_init_$Create$('SAKT', 159, 'SAKT', 11);
    Timezone_SAMT_instance = Timezone_init_$Create$('SAMT', 160, 'SAMT', 4);
    Timezone_SAST_instance = Timezone_init_$Create$('SAST', 161, 'SAST', 2);
    Timezone_SBT_instance = Timezone_init_$Create$('SBT', 162, 'SBT', 11);
    Timezone_SCT_instance = Timezone_init_$Create$('SCT', 163, 'SCT', 4);
    Timezone_SDT_instance = Timezone_init_$Create$('SDT', 164, 'SDT', -10);
    Timezone_SGT_instance = Timezone_init_$Create$('SGT', 165, 'SGT', 8);
    Timezone_SLST_instance = Timezone_init_$Create$('SLST', 166, 'SLST', 5, 30);
    Timezone_SRET_instance = Timezone_init_$Create$('SRET', 167, 'SRET', 11);
    Timezone_SRT_instance = Timezone_init_$Create$('SRT', 168, 'SRT', -3);
    Timezone_SST_SAMOA_instance = Timezone_init_$Create$('SST_SAMOA', 169, 'SST', -11, VOID, 'SST_SAMOA');
    Timezone_SST_SINGAPORE_instance = Timezone_init_$Create$('SST_SINGAPORE', 170, 'SST', 8, VOID, 'SST_SINGAPORE');
    Timezone_SYOT_instance = Timezone_init_$Create$('SYOT', 171, 'SYOT', 3);
    Timezone_TAHT_instance = Timezone_init_$Create$('TAHT', 172, 'TAHT', -10);
    Timezone_THA_instance = Timezone_init_$Create$('THA', 173, 'THA', 7);
    Timezone_TFT_instance = Timezone_init_$Create$('TFT', 174, 'TFT', 5);
    Timezone_TJT_instance = Timezone_init_$Create$('TJT', 175, 'TJT', 5);
    Timezone_TKT_instance = Timezone_init_$Create$('TKT', 176, 'TKT', 13);
    Timezone_TLT_instance = Timezone_init_$Create$('TLT', 177, 'TLT', 9);
    Timezone_TMT_instance = Timezone_init_$Create$('TMT', 178, 'TMT', 5);
    Timezone_TRT_instance = Timezone_init_$Create$('TRT', 179, 'TRT', 3);
    Timezone_TOT_instance = Timezone_init_$Create$('TOT', 180, 'TOT', 13);
    Timezone_TVT_instance = Timezone_init_$Create$('TVT', 181, 'TVT', 12);
    Timezone_ULAST_instance = Timezone_init_$Create$('ULAST', 182, 'ULAST', 9);
    Timezone_ULAT_instance = Timezone_init_$Create$('ULAT', 183, 'ULAT', 8);
    Timezone_UYST_instance = Timezone_init_$Create$('UYST', 184, 'UYST', -2);
    Timezone_UYT_instance = Timezone_init_$Create$('UYT', 185, 'UYT', -3);
    Timezone_UZT_instance = Timezone_init_$Create$('UZT', 186, 'UZT', 5);
    Timezone_VET_instance = Timezone_init_$Create$('VET', 187, 'VET', -4);
    Timezone_VLAT_instance = Timezone_init_$Create$('VLAT', 188, 'VLAT', 10);
    Timezone_VOLT_instance = Timezone_init_$Create$('VOLT', 189, 'VOLT', 4);
    Timezone_VOST_instance = Timezone_init_$Create$('VOST', 190, 'VOST', 6);
    Timezone_VUT_instance = Timezone_init_$Create$('VUT', 191, 'VUT', 11);
    Timezone_WAKT_instance = Timezone_init_$Create$('WAKT', 192, 'WAKT', 12);
    Timezone_WAST_instance = Timezone_init_$Create$('WAST', 193, 'WAST', 2);
    Timezone_WAT_instance = Timezone_init_$Create$('WAT', 194, 'WAT', 1);
    Timezone_WEST_instance = Timezone_init_$Create$('WEST', 195, 'WEST', 1);
    Timezone_WET_instance = Timezone_init_$Create$('WET', 196, 'WET', 0);
    Timezone_WIT_instance = Timezone_init_$Create$('WIT', 197, 'WIT', 7);
    Timezone_WST_instance = Timezone_init_$Create$('WST', 198, 'WST', 8);
    Timezone_YAKT_instance = Timezone_init_$Create$('YAKT', 199, 'YAKT', 9);
    Timezone_YEKT_instance = Timezone_init_$Create$('YEKT', 200, 'YEKT', 5);
  }
  var $ENTRIES_3;
  function Timezone(name, ordinal, abbr, offset, long) {
    long = long === VOID ? abbr : long;
    Enum.call(this, name, ordinal);
    this.abbr_1 = abbr;
    this.offset_1 = offset;
    this.long_1 = long;
  }
  protoOf(Timezone).get_abbr_woioai_k$ = function () {
    return this.abbr_1;
  };
  protoOf(Timezone).get_offset_noi1v9_k$ = function () {
    return this.offset_1;
  };
  protoOf(Timezone).get_long_wopz2d_k$ = function () {
    return this.long_1;
  };
  function Timezone_PDT_getInstance() {
    Timezone_initEntries();
    return Timezone_PDT_instance;
  }
  function Timezone_PST_getInstance() {
    Timezone_initEntries();
    return Timezone_PST_instance;
  }
  function Timezone_GMT_getInstance() {
    Timezone_initEntries();
    return Timezone_GMT_instance;
  }
  function Timezone_UTC_getInstance() {
    Timezone_initEntries();
    return Timezone_UTC_instance;
  }
  function Timezone_CET_getInstance() {
    Timezone_initEntries();
    return Timezone_CET_instance;
  }
  function Timezone_CEST_getInstance() {
    Timezone_initEntries();
    return Timezone_CEST_instance;
  }
  function Timezone_ACDT_getInstance() {
    Timezone_initEntries();
    return Timezone_ACDT_instance;
  }
  function Timezone_ACST_getInstance() {
    Timezone_initEntries();
    return Timezone_ACST_instance;
  }
  function Timezone_ACT_getInstance() {
    Timezone_initEntries();
    return Timezone_ACT_instance;
  }
  function Timezone_ACWST_getInstance() {
    Timezone_initEntries();
    return Timezone_ACWST_instance;
  }
  function Timezone_ADT_getInstance() {
    Timezone_initEntries();
    return Timezone_ADT_instance;
  }
  function Timezone_AEDT_getInstance() {
    Timezone_initEntries();
    return Timezone_AEDT_instance;
  }
  function Timezone_AEST_getInstance() {
    Timezone_initEntries();
    return Timezone_AEST_instance;
  }
  function Timezone_AFT_getInstance() {
    Timezone_initEntries();
    return Timezone_AFT_instance;
  }
  function Timezone_AKDT_getInstance() {
    Timezone_initEntries();
    return Timezone_AKDT_instance;
  }
  function Timezone_AKST_getInstance() {
    Timezone_initEntries();
    return Timezone_AKST_instance;
  }
  function Timezone_ALMT_getInstance() {
    Timezone_initEntries();
    return Timezone_ALMT_instance;
  }
  function Timezone_AMST_getInstance() {
    Timezone_initEntries();
    return Timezone_AMST_instance;
  }
  function Timezone_AMT_BRAZIL_getInstance() {
    Timezone_initEntries();
    return Timezone_AMT_BRAZIL_instance;
  }
  function Timezone_AMT_ARMENIA_getInstance() {
    Timezone_initEntries();
    return Timezone_AMT_ARMENIA_instance;
  }
  function Timezone_ANAT_getInstance() {
    Timezone_initEntries();
    return Timezone_ANAT_instance;
  }
  function Timezone_AQTT_getInstance() {
    Timezone_initEntries();
    return Timezone_AQTT_instance;
  }
  function Timezone_ART_getInstance() {
    Timezone_initEntries();
    return Timezone_ART_instance;
  }
  function Timezone_AST_ARABIA_getInstance() {
    Timezone_initEntries();
    return Timezone_AST_ARABIA_instance;
  }
  function Timezone_AST_ATLANTIC_getInstance() {
    Timezone_initEntries();
    return Timezone_AST_ATLANTIC_instance;
  }
  function Timezone_AWST_getInstance() {
    Timezone_initEntries();
    return Timezone_AWST_instance;
  }
  function Timezone_AZOST_getInstance() {
    Timezone_initEntries();
    return Timezone_AZOST_instance;
  }
  function Timezone_AZOT_getInstance() {
    Timezone_initEntries();
    return Timezone_AZOT_instance;
  }
  function Timezone_AZT_getInstance() {
    Timezone_initEntries();
    return Timezone_AZT_instance;
  }
  function Timezone_BDT_getInstance() {
    Timezone_initEntries();
    return Timezone_BDT_instance;
  }
  function Timezone_BIOT_getInstance() {
    Timezone_initEntries();
    return Timezone_BIOT_instance;
  }
  function Timezone_BIT_getInstance() {
    Timezone_initEntries();
    return Timezone_BIT_instance;
  }
  function Timezone_BOT_getInstance() {
    Timezone_initEntries();
    return Timezone_BOT_instance;
  }
  function Timezone_BRST_getInstance() {
    Timezone_initEntries();
    return Timezone_BRST_instance;
  }
  function Timezone_BRT_getInstance() {
    Timezone_initEntries();
    return Timezone_BRT_instance;
  }
  function Timezone_BST_BANGLADESH_getInstance() {
    Timezone_initEntries();
    return Timezone_BST_BANGLADESH_instance;
  }
  function Timezone_BST_BOUGAINVILLE_getInstance() {
    Timezone_initEntries();
    return Timezone_BST_BOUGAINVILLE_instance;
  }
  function Timezone_BST_BRITISH_getInstance() {
    Timezone_initEntries();
    return Timezone_BST_BRITISH_instance;
  }
  function Timezone_BTT_getInstance() {
    Timezone_initEntries();
    return Timezone_BTT_instance;
  }
  function Timezone_CAT_getInstance() {
    Timezone_initEntries();
    return Timezone_CAT_instance;
  }
  function Timezone_CCT_getInstance() {
    Timezone_initEntries();
    return Timezone_CCT_instance;
  }
  function Timezone_CDT_AMERICA_getInstance() {
    Timezone_initEntries();
    return Timezone_CDT_AMERICA_instance;
  }
  function Timezone_CDT_CUBA_getInstance() {
    Timezone_initEntries();
    return Timezone_CDT_CUBA_instance;
  }
  function Timezone_CHADT_getInstance() {
    Timezone_initEntries();
    return Timezone_CHADT_instance;
  }
  function Timezone_CHAST_getInstance() {
    Timezone_initEntries();
    return Timezone_CHAST_instance;
  }
  function Timezone_CHOT_getInstance() {
    Timezone_initEntries();
    return Timezone_CHOT_instance;
  }
  function Timezone_CHOST_getInstance() {
    Timezone_initEntries();
    return Timezone_CHOST_instance;
  }
  function Timezone_CHST_getInstance() {
    Timezone_initEntries();
    return Timezone_CHST_instance;
  }
  function Timezone_CHUT_getInstance() {
    Timezone_initEntries();
    return Timezone_CHUT_instance;
  }
  function Timezone_CIST_getInstance() {
    Timezone_initEntries();
    return Timezone_CIST_instance;
  }
  function Timezone_CIT_getInstance() {
    Timezone_initEntries();
    return Timezone_CIT_instance;
  }
  function Timezone_CKT_getInstance() {
    Timezone_initEntries();
    return Timezone_CKT_instance;
  }
  function Timezone_CLST_getInstance() {
    Timezone_initEntries();
    return Timezone_CLST_instance;
  }
  function Timezone_CLT_getInstance() {
    Timezone_initEntries();
    return Timezone_CLT_instance;
  }
  function Timezone_COST_getInstance() {
    Timezone_initEntries();
    return Timezone_COST_instance;
  }
  function Timezone_COT_getInstance() {
    Timezone_initEntries();
    return Timezone_COT_instance;
  }
  function Timezone_CST_AMERICA_getInstance() {
    Timezone_initEntries();
    return Timezone_CST_AMERICA_instance;
  }
  function Timezone_CST_CHINA_getInstance() {
    Timezone_initEntries();
    return Timezone_CST_CHINA_instance;
  }
  function Timezone_CST_CUBA_getInstance() {
    Timezone_initEntries();
    return Timezone_CST_CUBA_instance;
  }
  function Timezone_CT_getInstance() {
    Timezone_initEntries();
    return Timezone_CT_instance;
  }
  function Timezone_CVT_getInstance() {
    Timezone_initEntries();
    return Timezone_CVT_instance;
  }
  function Timezone_CWST_getInstance() {
    Timezone_initEntries();
    return Timezone_CWST_instance;
  }
  function Timezone_CXT_getInstance() {
    Timezone_initEntries();
    return Timezone_CXT_instance;
  }
  function Timezone_DAVT_getInstance() {
    Timezone_initEntries();
    return Timezone_DAVT_instance;
  }
  function Timezone_DDUT_getInstance() {
    Timezone_initEntries();
    return Timezone_DDUT_instance;
  }
  function Timezone_DFT_getInstance() {
    Timezone_initEntries();
    return Timezone_DFT_instance;
  }
  function Timezone_EASST_getInstance() {
    Timezone_initEntries();
    return Timezone_EASST_instance;
  }
  function Timezone_EAST_getInstance() {
    Timezone_initEntries();
    return Timezone_EAST_instance;
  }
  function Timezone_EAT_getInstance() {
    Timezone_initEntries();
    return Timezone_EAT_instance;
  }
  function Timezone_ECT_CARIBBEAN_getInstance() {
    Timezone_initEntries();
    return Timezone_ECT_CARIBBEAN_instance;
  }
  function Timezone_ECT_ECUADOR_getInstance() {
    Timezone_initEntries();
    return Timezone_ECT_ECUADOR_instance;
  }
  function Timezone_EDT_getInstance() {
    Timezone_initEntries();
    return Timezone_EDT_instance;
  }
  function Timezone_EEST_getInstance() {
    Timezone_initEntries();
    return Timezone_EEST_instance;
  }
  function Timezone_EET_getInstance() {
    Timezone_initEntries();
    return Timezone_EET_instance;
  }
  function Timezone_EGST_getInstance() {
    Timezone_initEntries();
    return Timezone_EGST_instance;
  }
  function Timezone_EGT_getInstance() {
    Timezone_initEntries();
    return Timezone_EGT_instance;
  }
  function Timezone_EIT_getInstance() {
    Timezone_initEntries();
    return Timezone_EIT_instance;
  }
  function Timezone_EST_getInstance() {
    Timezone_initEntries();
    return Timezone_EST_instance;
  }
  function Timezone_FET_getInstance() {
    Timezone_initEntries();
    return Timezone_FET_instance;
  }
  function Timezone_FJT_getInstance() {
    Timezone_initEntries();
    return Timezone_FJT_instance;
  }
  function Timezone_FKST_getInstance() {
    Timezone_initEntries();
    return Timezone_FKST_instance;
  }
  function Timezone_FKT_getInstance() {
    Timezone_initEntries();
    return Timezone_FKT_instance;
  }
  function Timezone_FNT_getInstance() {
    Timezone_initEntries();
    return Timezone_FNT_instance;
  }
  function Timezone_GALT_getInstance() {
    Timezone_initEntries();
    return Timezone_GALT_instance;
  }
  function Timezone_GAMT_getInstance() {
    Timezone_initEntries();
    return Timezone_GAMT_instance;
  }
  function Timezone_GET_getInstance() {
    Timezone_initEntries();
    return Timezone_GET_instance;
  }
  function Timezone_GFT_getInstance() {
    Timezone_initEntries();
    return Timezone_GFT_instance;
  }
  function Timezone_GILT_getInstance() {
    Timezone_initEntries();
    return Timezone_GILT_instance;
  }
  function Timezone_GIT_getInstance() {
    Timezone_initEntries();
    return Timezone_GIT_instance;
  }
  function Timezone_GST_GEORGIA_getInstance() {
    Timezone_initEntries();
    return Timezone_GST_GEORGIA_instance;
  }
  function Timezone_GST_GULF_getInstance() {
    Timezone_initEntries();
    return Timezone_GST_GULF_instance;
  }
  function Timezone_GYT_getInstance() {
    Timezone_initEntries();
    return Timezone_GYT_instance;
  }
  function Timezone_HDT_getInstance() {
    Timezone_initEntries();
    return Timezone_HDT_instance;
  }
  function Timezone_HAEC_getInstance() {
    Timezone_initEntries();
    return Timezone_HAEC_instance;
  }
  function Timezone_HST_getInstance() {
    Timezone_initEntries();
    return Timezone_HST_instance;
  }
  function Timezone_HKT_getInstance() {
    Timezone_initEntries();
    return Timezone_HKT_instance;
  }
  function Timezone_HMT_getInstance() {
    Timezone_initEntries();
    return Timezone_HMT_instance;
  }
  function Timezone_HOVST_getInstance() {
    Timezone_initEntries();
    return Timezone_HOVST_instance;
  }
  function Timezone_HOVT_getInstance() {
    Timezone_initEntries();
    return Timezone_HOVT_instance;
  }
  function Timezone_ICT_getInstance() {
    Timezone_initEntries();
    return Timezone_ICT_instance;
  }
  function Timezone_IDLW_getInstance() {
    Timezone_initEntries();
    return Timezone_IDLW_instance;
  }
  function Timezone_IDT_getInstance() {
    Timezone_initEntries();
    return Timezone_IDT_instance;
  }
  function Timezone_IOT_getInstance() {
    Timezone_initEntries();
    return Timezone_IOT_instance;
  }
  function Timezone_IRDT_getInstance() {
    Timezone_initEntries();
    return Timezone_IRDT_instance;
  }
  function Timezone_IRKT_getInstance() {
    Timezone_initEntries();
    return Timezone_IRKT_instance;
  }
  function Timezone_IRST_getInstance() {
    Timezone_initEntries();
    return Timezone_IRST_instance;
  }
  function Timezone_IST_INDIA_getInstance() {
    Timezone_initEntries();
    return Timezone_IST_INDIA_instance;
  }
  function Timezone_IST_IRISH_getInstance() {
    Timezone_initEntries();
    return Timezone_IST_IRISH_instance;
  }
  function Timezone_IST_ISRAEL_getInstance() {
    Timezone_initEntries();
    return Timezone_IST_ISRAEL_instance;
  }
  function Timezone_JST_getInstance() {
    Timezone_initEntries();
    return Timezone_JST_instance;
  }
  function Timezone_KALT_getInstance() {
    Timezone_initEntries();
    return Timezone_KALT_instance;
  }
  function Timezone_KGT_getInstance() {
    Timezone_initEntries();
    return Timezone_KGT_instance;
  }
  function Timezone_KOST_getInstance() {
    Timezone_initEntries();
    return Timezone_KOST_instance;
  }
  function Timezone_KRAT_getInstance() {
    Timezone_initEntries();
    return Timezone_KRAT_instance;
  }
  function Timezone_KST_getInstance() {
    Timezone_initEntries();
    return Timezone_KST_instance;
  }
  function Timezone_LHST_STD_getInstance() {
    Timezone_initEntries();
    return Timezone_LHST_STD_instance;
  }
  function Timezone_LHST_SUMMER_getInstance() {
    Timezone_initEntries();
    return Timezone_LHST_SUMMER_instance;
  }
  function Timezone_LINT_getInstance() {
    Timezone_initEntries();
    return Timezone_LINT_instance;
  }
  function Timezone_MAGT_getInstance() {
    Timezone_initEntries();
    return Timezone_MAGT_instance;
  }
  function Timezone_MART_getInstance() {
    Timezone_initEntries();
    return Timezone_MART_instance;
  }
  function Timezone_MAWT_getInstance() {
    Timezone_initEntries();
    return Timezone_MAWT_instance;
  }
  function Timezone_MDT_getInstance() {
    Timezone_initEntries();
    return Timezone_MDT_instance;
  }
  function Timezone_MET_getInstance() {
    Timezone_initEntries();
    return Timezone_MET_instance;
  }
  function Timezone_MEST_getInstance() {
    Timezone_initEntries();
    return Timezone_MEST_instance;
  }
  function Timezone_MHT_getInstance() {
    Timezone_initEntries();
    return Timezone_MHT_instance;
  }
  function Timezone_MIST_getInstance() {
    Timezone_initEntries();
    return Timezone_MIST_instance;
  }
  function Timezone_MIT_getInstance() {
    Timezone_initEntries();
    return Timezone_MIT_instance;
  }
  function Timezone_MMT_getInstance() {
    Timezone_initEntries();
    return Timezone_MMT_instance;
  }
  function Timezone_MSK_getInstance() {
    Timezone_initEntries();
    return Timezone_MSK_instance;
  }
  function Timezone_MST_MALAYSIA_getInstance() {
    Timezone_initEntries();
    return Timezone_MST_MALAYSIA_instance;
  }
  function Timezone_MST_AMERICA_getInstance() {
    Timezone_initEntries();
    return Timezone_MST_AMERICA_instance;
  }
  function Timezone_MUT_getInstance() {
    Timezone_initEntries();
    return Timezone_MUT_instance;
  }
  function Timezone_MVT_getInstance() {
    Timezone_initEntries();
    return Timezone_MVT_instance;
  }
  function Timezone_MYT_getInstance() {
    Timezone_initEntries();
    return Timezone_MYT_instance;
  }
  function Timezone_NCT_getInstance() {
    Timezone_initEntries();
    return Timezone_NCT_instance;
  }
  function Timezone_NDT_getInstance() {
    Timezone_initEntries();
    return Timezone_NDT_instance;
  }
  function Timezone_NFT_getInstance() {
    Timezone_initEntries();
    return Timezone_NFT_instance;
  }
  function Timezone_NOVT_getInstance() {
    Timezone_initEntries();
    return Timezone_NOVT_instance;
  }
  function Timezone_NPT_getInstance() {
    Timezone_initEntries();
    return Timezone_NPT_instance;
  }
  function Timezone_NST_getInstance() {
    Timezone_initEntries();
    return Timezone_NST_instance;
  }
  function Timezone_NT_getInstance() {
    Timezone_initEntries();
    return Timezone_NT_instance;
  }
  function Timezone_NUT_getInstance() {
    Timezone_initEntries();
    return Timezone_NUT_instance;
  }
  function Timezone_NZDT_getInstance() {
    Timezone_initEntries();
    return Timezone_NZDT_instance;
  }
  function Timezone_NZST_getInstance() {
    Timezone_initEntries();
    return Timezone_NZST_instance;
  }
  function Timezone_OMST_getInstance() {
    Timezone_initEntries();
    return Timezone_OMST_instance;
  }
  function Timezone_ORAT_getInstance() {
    Timezone_initEntries();
    return Timezone_ORAT_instance;
  }
  function Timezone_PET_getInstance() {
    Timezone_initEntries();
    return Timezone_PET_instance;
  }
  function Timezone_PETT_getInstance() {
    Timezone_initEntries();
    return Timezone_PETT_instance;
  }
  function Timezone_PGT_getInstance() {
    Timezone_initEntries();
    return Timezone_PGT_instance;
  }
  function Timezone_PHOT_getInstance() {
    Timezone_initEntries();
    return Timezone_PHOT_instance;
  }
  function Timezone_PHT_getInstance() {
    Timezone_initEntries();
    return Timezone_PHT_instance;
  }
  function Timezone_PKT_getInstance() {
    Timezone_initEntries();
    return Timezone_PKT_instance;
  }
  function Timezone_PMDT_getInstance() {
    Timezone_initEntries();
    return Timezone_PMDT_instance;
  }
  function Timezone_PMST_getInstance() {
    Timezone_initEntries();
    return Timezone_PMST_instance;
  }
  function Timezone_PONT_getInstance() {
    Timezone_initEntries();
    return Timezone_PONT_instance;
  }
  function Timezone_PYST_getInstance() {
    Timezone_initEntries();
    return Timezone_PYST_instance;
  }
  function Timezone_PYT_getInstance() {
    Timezone_initEntries();
    return Timezone_PYT_instance;
  }
  function Timezone_RET_getInstance() {
    Timezone_initEntries();
    return Timezone_RET_instance;
  }
  function Timezone_ROTT_getInstance() {
    Timezone_initEntries();
    return Timezone_ROTT_instance;
  }
  function Timezone_SAKT_getInstance() {
    Timezone_initEntries();
    return Timezone_SAKT_instance;
  }
  function Timezone_SAMT_getInstance() {
    Timezone_initEntries();
    return Timezone_SAMT_instance;
  }
  function Timezone_SAST_getInstance() {
    Timezone_initEntries();
    return Timezone_SAST_instance;
  }
  function Timezone_SBT_getInstance() {
    Timezone_initEntries();
    return Timezone_SBT_instance;
  }
  function Timezone_SCT_getInstance() {
    Timezone_initEntries();
    return Timezone_SCT_instance;
  }
  function Timezone_SDT_getInstance() {
    Timezone_initEntries();
    return Timezone_SDT_instance;
  }
  function Timezone_SGT_getInstance() {
    Timezone_initEntries();
    return Timezone_SGT_instance;
  }
  function Timezone_SLST_getInstance() {
    Timezone_initEntries();
    return Timezone_SLST_instance;
  }
  function Timezone_SRET_getInstance() {
    Timezone_initEntries();
    return Timezone_SRET_instance;
  }
  function Timezone_SRT_getInstance() {
    Timezone_initEntries();
    return Timezone_SRT_instance;
  }
  function Timezone_SST_SAMOA_getInstance() {
    Timezone_initEntries();
    return Timezone_SST_SAMOA_instance;
  }
  function Timezone_SST_SINGAPORE_getInstance() {
    Timezone_initEntries();
    return Timezone_SST_SINGAPORE_instance;
  }
  function Timezone_SYOT_getInstance() {
    Timezone_initEntries();
    return Timezone_SYOT_instance;
  }
  function Timezone_TAHT_getInstance() {
    Timezone_initEntries();
    return Timezone_TAHT_instance;
  }
  function Timezone_THA_getInstance() {
    Timezone_initEntries();
    return Timezone_THA_instance;
  }
  function Timezone_TFT_getInstance() {
    Timezone_initEntries();
    return Timezone_TFT_instance;
  }
  function Timezone_TJT_getInstance() {
    Timezone_initEntries();
    return Timezone_TJT_instance;
  }
  function Timezone_TKT_getInstance() {
    Timezone_initEntries();
    return Timezone_TKT_instance;
  }
  function Timezone_TLT_getInstance() {
    Timezone_initEntries();
    return Timezone_TLT_instance;
  }
  function Timezone_TMT_getInstance() {
    Timezone_initEntries();
    return Timezone_TMT_instance;
  }
  function Timezone_TRT_getInstance() {
    Timezone_initEntries();
    return Timezone_TRT_instance;
  }
  function Timezone_TOT_getInstance() {
    Timezone_initEntries();
    return Timezone_TOT_instance;
  }
  function Timezone_TVT_getInstance() {
    Timezone_initEntries();
    return Timezone_TVT_instance;
  }
  function Timezone_ULAST_getInstance() {
    Timezone_initEntries();
    return Timezone_ULAST_instance;
  }
  function Timezone_ULAT_getInstance() {
    Timezone_initEntries();
    return Timezone_ULAT_instance;
  }
  function Timezone_UYST_getInstance() {
    Timezone_initEntries();
    return Timezone_UYST_instance;
  }
  function Timezone_UYT_getInstance() {
    Timezone_initEntries();
    return Timezone_UYT_instance;
  }
  function Timezone_UZT_getInstance() {
    Timezone_initEntries();
    return Timezone_UZT_instance;
  }
  function Timezone_VET_getInstance() {
    Timezone_initEntries();
    return Timezone_VET_instance;
  }
  function Timezone_VLAT_getInstance() {
    Timezone_initEntries();
    return Timezone_VLAT_instance;
  }
  function Timezone_VOLT_getInstance() {
    Timezone_initEntries();
    return Timezone_VOLT_instance;
  }
  function Timezone_VOST_getInstance() {
    Timezone_initEntries();
    return Timezone_VOST_instance;
  }
  function Timezone_VUT_getInstance() {
    Timezone_initEntries();
    return Timezone_VUT_instance;
  }
  function Timezone_WAKT_getInstance() {
    Timezone_initEntries();
    return Timezone_WAKT_instance;
  }
  function Timezone_WAST_getInstance() {
    Timezone_initEntries();
    return Timezone_WAST_instance;
  }
  function Timezone_WAT_getInstance() {
    Timezone_initEntries();
    return Timezone_WAT_instance;
  }
  function Timezone_WEST_getInstance() {
    Timezone_initEntries();
    return Timezone_WEST_instance;
  }
  function Timezone_WET_getInstance() {
    Timezone_initEntries();
    return Timezone_WET_instance;
  }
  function Timezone_WIT_getInstance() {
    Timezone_initEntries();
    return Timezone_WIT_instance;
  }
  function Timezone_WST_getInstance() {
    Timezone_initEntries();
    return Timezone_WST_instance;
  }
  function Timezone_YAKT_getInstance() {
    Timezone_initEntries();
    return Timezone_YAKT_instance;
  }
  function Timezone_YEKT_getInstance() {
    Timezone_initEntries();
    return Timezone_YEKT_instance;
  }
  function _get_serialVersionUID__fhggm9_11($this) {
    return $this.serialVersionUID_1;
  }
  function TimezoneNames_init_$Init$(tz, $this) {
    TimezoneNames.call($this, toList_0(tz));
    return $this;
  }
  function TimezoneNames_init_$Create$(tz) {
    return TimezoneNames_init_$Init$(tz, objectCreate(protoOf(TimezoneNames)));
  }
  function Companion_17() {
    Companion_instance_17 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.DEFAULT_1 = TimezoneNames_init_$Create$([Timezone_PDT_getInstance(), Timezone_PST_getInstance(), Timezone_GMT_getInstance(), Timezone_UTC_getInstance(), Timezone_CET_getInstance(), Timezone_CEST_getInstance()]);
  }
  protoOf(Companion_17).get_DEFAULT_wccqmg_k$ = function () {
    return this.DEFAULT_1;
  };
  var Companion_instance_17;
  function Companion_getInstance_19() {
    if (Companion_instance_17 == null)
      new Companion_17();
    return Companion_instance_17;
  }
  function TimezoneNames$namesToOffsetsList$delegate$lambda(this$0) {
    return function () {
      // Inline function 'kotlin.collections.groupBy' call
      // Inline function 'kotlin.collections.groupByTo' call
      var this_0 = this$0.timeZones_1;
      var destination = LinkedHashMap_init_$Create$();
      var tmp0_iterator = this_0.iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.time.TimezoneNames.namesToOffsetsList$delegate.<anonymous>.<anonymous>' call
        var key = element.get_abbr_woioai_k$();
        // Inline function 'kotlin.collections.getOrPut' call
        var value = destination.get_wei43m_k$(key);
        var tmp;
        if (value == null) {
          // Inline function 'kotlin.collections.groupByTo.<anonymous>' call
          var answer = ArrayList_init_$Create$();
          destination.put_4fpzoq_k$(key, answer);
          tmp = answer;
        } else {
          tmp = value;
        }
        var list = tmp;
        list.add_utx5q5_k$(element);
      }
      return destination;
    };
  }
  function TimezoneNames$namesToOffsets$delegate$lambda(this$0) {
    return function () {
      // Inline function 'kotlin.collections.map' call
      var this_0 = this$0.get_namesToOffsetsList_p32jbw_k$();
      // Inline function 'kotlin.collections.mapTo' call
      var destination = ArrayList_init_$Create$_0(this_0.get_size_woubt6_k$());
      // Inline function 'kotlin.collections.iterator' call
      var tmp0_iterator = this_0.get_entries_p20ztl_k$().iterator_jk1svi_k$();
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var item = tmp0_iterator.next_20eer_k$();
        // Inline function 'korlibs.time.TimezoneNames.namesToOffsets$delegate.<anonymous>.<anonymous>' call
        var tmp$ret$1 = to(item.get_key_18j28a_k$(), new Duration(_TimezoneOffset___get_time__impl__u2t42f(first_0(item.get_value_j01efc_k$()).get_offset_noi1v9_k$())));
        destination.add_utx5q5_k$(tmp$ret$1);
      }
      return toMap(destination);
    };
  }
  function TimezoneNames(timeZones) {
    Companion_getInstance_19();
    this.timeZones_1 = timeZones;
    var tmp = this;
    tmp.namesToOffsetsList$delegate_1 = lazy(TimezoneNames$namesToOffsetsList$delegate$lambda(this));
    var tmp_0 = this;
    tmp_0.namesToOffsets$delegate_1 = lazy(TimezoneNames$namesToOffsets$delegate$lambda(this));
  }
  protoOf(TimezoneNames).get_timeZones_91iklt_k$ = function () {
    return this.timeZones_1;
  };
  protoOf(TimezoneNames).get_namesToOffsetsList_p32jbw_k$ = function () {
    // Inline function 'kotlin.getValue' call
    var this_0 = this.namesToOffsetsList$delegate_1;
    namesToOffsetsList$factory();
    return this_0.get_value_j01efc_k$();
  };
  protoOf(TimezoneNames).get_namesToOffsets_qdh52e_k$ = function () {
    // Inline function 'kotlin.getValue' call
    var this_0 = this.namesToOffsets$delegate_1;
    namesToOffsets$factory();
    return this_0.get_value_j01efc_k$();
  };
  protoOf(TimezoneNames).plus_4pfgan_k$ = function (other) {
    return new TimezoneNames(plus(this.timeZones_1, other.timeZones_1));
  };
  protoOf(TimezoneNames).get_6bo4tg_k$ = function (name) {
    var tmp = this.get_namesToOffsetsList_p32jbw_k$();
    // Inline function 'kotlin.text.trim' call
    // Inline function 'kotlin.text.uppercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = name.toUpperCase();
    var tmp$ret$2 = toString(trim(isCharSequence(this_0) ? this_0 : THROW_CCE()));
    var tmp15_safe_receiver = tmp.get_wei43m_k$(tmp$ret$2);
    return tmp15_safe_receiver == null ? null : first_0(tmp15_safe_receiver);
  };
  protoOf(TimezoneNames).getAll_ffxf4h_k$ = function (name) {
    var tmp = this.get_namesToOffsetsList_p32jbw_k$();
    // Inline function 'kotlin.text.trim' call
    // Inline function 'kotlin.text.uppercase' call
    // Inline function 'kotlin.js.asDynamic' call
    var this_0 = name.toUpperCase();
    var tmp$ret$2 = toString(trim(isCharSequence(this_0) ? this_0 : THROW_CCE()));
    var tmp0_elvis_lhs = tmp.get_wei43m_k$(tmp$ret$2);
    return tmp0_elvis_lhs == null ? emptyList() : tmp0_elvis_lhs;
  };
  protoOf(TimezoneNames).component1_7eebsc_k$ = function () {
    return this.timeZones_1;
  };
  protoOf(TimezoneNames).copy_x7a07x_k$ = function (timeZones) {
    return new TimezoneNames(timeZones);
  };
  protoOf(TimezoneNames).copy$default_k72bsv_k$ = function (timeZones, $super) {
    timeZones = timeZones === VOID ? this.timeZones_1 : timeZones;
    return $super === VOID ? this.copy_x7a07x_k$(timeZones) : $super.copy_x7a07x_k$.call(this, timeZones);
  };
  protoOf(TimezoneNames).toString = function () {
    return 'TimezoneNames(timeZones=' + toString(this.timeZones_1) + ')';
  };
  protoOf(TimezoneNames).hashCode = function () {
    return hashCode(this.timeZones_1);
  };
  protoOf(TimezoneNames).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof TimezoneNames))
      return false;
    var tmp0_other_with_cast = other instanceof TimezoneNames ? other : THROW_CCE();
    if (!equals(this.timeZones_1, tmp0_other_with_cast.timeZones_1))
      return false;
    return true;
  };
  function namesToOffsetsList$factory() {
    return getPropertyCallableRef('namesToOffsetsList', 1, KProperty1, function (receiver) {
      return receiver.get_namesToOffsetsList_p32jbw_k$();
    }, null);
  }
  function namesToOffsets$factory() {
    return getPropertyCallableRef('namesToOffsets', 1, KProperty1, function (receiver) {
      return receiver.get_namesToOffsets_qdh52e_k$();
    }, null);
  }
  function _get_serialVersionUID__fhggm9_12($this) {
    return $this.serialVersionUID_1;
  }
  function _TimezoneOffset___init__impl__4pq59n(totalMilliseconds) {
    return totalMilliseconds;
  }
  function _TimezoneOffset___get_totalMilliseconds__impl__da1mf0($this) {
    return $this;
  }
  function _TimezoneOffset___get_positive__impl__x0f905($this) {
    return _TimezoneOffset___get_totalMilliseconds__impl__da1mf0($this) >= 0.0;
  }
  function _TimezoneOffset___get_time__impl__u2t42f($this) {
    // Inline function 'korlibs.time.milliseconds' call
    var this_0 = _TimezoneOffset___get_totalMilliseconds__impl__da1mf0($this);
    return toDuration(this_0, DurationUnit_MILLISECONDS_getInstance());
  }
  function _TimezoneOffset___get_totalMinutes__impl__9kqntj($this) {
    return _TimezoneOffset___get_totalMilliseconds__impl__da1mf0($this) / 60000;
  }
  function _TimezoneOffset___get_totalMinutesInt__impl__9h3gzq($this) {
    return numberToInt(_TimezoneOffset___get_totalMinutes__impl__9kqntj($this));
  }
  function _TimezoneOffset___get_timeZone__impl__6q3m9h($this) {
    var sign = _TimezoneOffset___get_positive__impl__x0f905($this) ? '+' : '-';
    var hour = padded(_TimezoneOffset___get_deltaHoursAbs__impl__j5qorj($this), 2);
    var minute = padded(_TimezoneOffset___get_deltaMinutesAbs__impl__cjys5t($this), 2);
    var tmp;
    var tmp_0 = _TimezoneOffset___get_time__impl__u2t42f($this);
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var tmp$ret$1 = toDuration(0, DurationUnit_MINUTES_getInstance());
    if (equals(tmp_0, tmp$ret$1)) {
      tmp = 'UTC';
    } else {
      tmp = 'GMT' + sign + hour + minute;
    }
    return tmp;
  }
  function _get_deltaTotalMinutesAbs__evvhpw($this) {
    return abs(numberToInt(_TimezoneOffset___get_totalMinutes__impl__9kqntj($this)));
  }
  function _TimezoneOffset___get_deltaHoursAbs__impl__j5qorj($this) {
    return _get_deltaTotalMinutesAbs__evvhpw($this) / 60 | 0;
  }
  function _TimezoneOffset___get_deltaMinutesAbs__impl__cjys5t($this) {
    return _get_deltaTotalMinutesAbs__evvhpw($this) % 60 | 0;
  }
  function TimezoneOffset__toString_impl_ibx4zv($this) {
    return _TimezoneOffset___get_timeZone__impl__6q3m9h($this);
  }
  function Companion_18() {
    Companion_instance_18 = this;
    this.UTC_1 = _TimezoneOffset___init__impl__4pq59n(0.0);
    this.serialVersionUID_1 = new Long(1, 0);
  }
  protoOf(Companion_18).get_UTC_tvlvnw_k$ = function () {
    return this.UTC_1;
  };
  protoOf(Companion_18).invoke_m9hl7e_k$ = function (time) {
    return _TimezoneOffset___init__impl__4pq59n(get_milliseconds_0(time));
  };
  protoOf(Companion_18).local_qfhv1l_k$ = function (time) {
    return get_offset(KlockInternal_getInstance().localTimezoneOffsetMinutes_3dowjn_k$(time));
  };
  var Companion_instance_18;
  function Companion_getInstance_20() {
    if (Companion_instance_18 == null)
      new Companion_18();
    return Companion_instance_18;
  }
  function TimezoneOffset__compareTo_impl_kybaex($this, other) {
    return compareTo(_TimezoneOffset___get_totalMilliseconds__impl__da1mf0($this), _TimezoneOffset___get_totalMilliseconds__impl__da1mf0(other));
  }
  function TimezoneOffset__compareTo_impl_kybaex_0($this, other) {
    return TimezoneOffset__compareTo_impl_kybaex($this.totalMilliseconds_1, other instanceof TimezoneOffset ? other.totalMilliseconds_1 : THROW_CCE());
  }
  function TimezoneOffset__hashCode_impl_uy106e($this) {
    return getNumberHashCode($this);
  }
  function TimezoneOffset__equals_impl_28rcam($this, other) {
    if (!(other instanceof TimezoneOffset))
      return false;
    var tmp0_other_with_cast = other instanceof TimezoneOffset ? other.totalMilliseconds_1 : THROW_CCE();
    if (!equals($this, tmp0_other_with_cast))
      return false;
    return true;
  }
  function TimezoneOffset(totalMilliseconds) {
    Companion_getInstance_20();
    this.totalMilliseconds_1 = totalMilliseconds;
  }
  protoOf(TimezoneOffset).toString = function () {
    return TimezoneOffset__toString_impl_ibx4zv(this.totalMilliseconds_1);
  };
  protoOf(TimezoneOffset).compareTo_ddyww7_k$ = function (other) {
    return TimezoneOffset__compareTo_impl_kybaex(this.totalMilliseconds_1, other);
  };
  protoOf(TimezoneOffset).compareTo_hpufkf_k$ = function (other) {
    return TimezoneOffset__compareTo_impl_kybaex_0(this, other);
  };
  protoOf(TimezoneOffset).hashCode = function () {
    return TimezoneOffset__hashCode_impl_uy106e(this.totalMilliseconds_1);
  };
  protoOf(TimezoneOffset).equals = function (other) {
    return TimezoneOffset__equals_impl_28rcam(this.totalMilliseconds_1, other);
  };
  function get_offset(_this__u8e3s4) {
    return Companion_getInstance_20().invoke_m9hl7e_k$(_this__u8e3s4);
  }
  function _get_serialVersionUID__fhggm9_13($this) {
    return $this.serialVersionUID_1;
  }
  function _get_LEAP_PER_4_YEARS__xkikav($this) {
    return $this.LEAP_PER_4_YEARS_1;
  }
  function _get_LEAP_PER_100_YEARS__q6u2as($this) {
    return $this.LEAP_PER_100_YEARS_1;
  }
  function _get_LEAP_PER_400_YEARS__z3a3ax($this) {
    return $this.LEAP_PER_400_YEARS_1;
  }
  function _get_DAYS_PER_4_YEARS__vqrtg6($this) {
    return $this.DAYS_PER_4_YEARS_1;
  }
  function _get_DAYS_PER_100_YEARS__o1ffnx($this) {
    return $this.DAYS_PER_100_YEARS_1;
  }
  function _get_DAYS_PER_400_YEARS__eafjai($this) {
    return $this.DAYS_PER_400_YEARS_1;
  }
  function _Year___init__impl__2jvef0(year) {
    return year;
  }
  function _Year___get_year__impl__hjar94($this) {
    return $this;
  }
  function Companion_19() {
    Companion_instance_19 = this;
    this.serialVersionUID_1 = new Long(1, 0);
    this.DAYS_COMMON_1 = 365;
    this.DAYS_LEAP_1 = 366;
    this.LEAP_PER_4_YEARS_1 = 1;
    this.LEAP_PER_100_YEARS_1 = 24;
    this.LEAP_PER_400_YEARS_1 = 97;
    this.DAYS_PER_4_YEARS_1 = 1461;
    this.DAYS_PER_100_YEARS_1 = 36524;
    this.DAYS_PER_400_YEARS_1 = 146097;
  }
  protoOf(Companion_19).checked_99ekhb_k$ = function (year) {
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.time.Companion.checked.<anonymous>' call
    if (!(1 <= year ? year <= 9999 : false))
      throw new DateException('Year ' + year + ' not in 1..9999');
    return year;
  };
  protoOf(Companion_19).isLeapChecked_9z5bdp_k$ = function (year) {
    return this.isLeap_iq3syu_k$(this.checked_99ekhb_k$(year));
  };
  protoOf(Companion_19).isLeap_iq3syu_k$ = function (year) {
    return (year % 4 | 0) === 0 ? !((year % 100 | 0) === 0) ? true : (year % 400 | 0) === 0 : false;
  };
  protoOf(Companion_19).fromDays_egwdof_k$ = function (days) {
    var v400 = days / 146097 | 0;
    var r400 = days - imul(v400, 146097) | 0;
    // Inline function 'kotlin.math.min' call
    var a = r400 / 36524 | 0;
    var v100 = Math.min(a, 3);
    var r100 = r400 - imul(v100, 36524) | 0;
    var v4 = r100 / 1461 | 0;
    var r4 = r100 - imul(v4, 1461) | 0;
    // Inline function 'kotlin.math.min' call
    var a_0 = r4 / 365 | 0;
    var v1 = Math.min(a_0, 3);
    var extra = days < 0 ? 0 : 1;
    return _Year___init__impl__2jvef0((((extra + v1 | 0) + imul(v4, 4) | 0) + imul(v100, 100) | 0) + imul(v400, 400) | 0);
  };
  protoOf(Companion_19).days_tlk1ta_k$ = function (isLeap) {
    return isLeap ? 366 : 365;
  };
  protoOf(Companion_19).leapCountSinceOne_ce36nv_k$ = function (year) {
    if (year < 1) {
      var leapCount = 0;
      var y = 1;
      while (y >= year) {
        if (_Year___get_isLeap__impl__kmyeoz(_Year___init__impl__2jvef0(y))) {
          leapCount = leapCount - 1 | 0;
        }
        y = y - 1 | 0;
      }
      return leapCount;
    }
    var y1 = year - 1 | 0;
    var res = ((y1 / 4 | 0) - (y1 / 100 | 0) | 0) + (y1 / 400 | 0) | 0;
    return res;
  };
  protoOf(Companion_19).daysSinceOne_jee0tx_k$ = function (year) {
    return imul(365, year - 1 | 0) + this.leapCountSinceOne_ce36nv_k$(year) | 0;
  };
  protoOf(Companion_19).get_DAYS_COMMON_6j0dii_k$ = function () {
    return this.DAYS_COMMON_1;
  };
  protoOf(Companion_19).get_DAYS_LEAP_b2qgd5_k$ = function () {
    return this.DAYS_LEAP_1;
  };
  var Companion_instance_19;
  function Companion_getInstance_21() {
    if (Companion_instance_19 == null)
      new Companion_19();
    return Companion_instance_19;
  }
  function _Year___get_isLeapChecked__impl__te7j6c($this) {
    return Companion_getInstance_21().isLeapChecked_9z5bdp_k$(_Year___get_year__impl__hjar94($this));
  }
  function _Year___get_isLeap__impl__kmyeoz($this) {
    return Companion_getInstance_21().isLeap_iq3syu_k$(_Year___get_year__impl__hjar94($this));
  }
  function _Year___get_days__impl__xyqg2q($this) {
    return Companion_getInstance_21().days_tlk1ta_k$(_Year___get_isLeap__impl__kmyeoz($this));
  }
  function _Year___get_leapCountSinceOne__impl__yc28fq($this) {
    return Companion_getInstance_21().leapCountSinceOne_ce36nv_k$(_Year___get_year__impl__hjar94($this));
  }
  function _Year___get_daysSinceOne__impl__cf4kem($this) {
    return Companion_getInstance_21().daysSinceOne_jee0tx_k$(_Year___get_year__impl__hjar94($this));
  }
  function Year__compareTo_impl_13xet2($this, other) {
    return compareTo(_Year___get_year__impl__hjar94($this), _Year___get_year__impl__hjar94(other));
  }
  function Year__compareTo_impl_13xet2_0($this, other) {
    return Year__compareTo_impl_13xet2($this.year_1, other instanceof Year ? other.year_1 : THROW_CCE());
  }
  function Year__plus_impl_l5v1ey($this, delta) {
    return _Year___init__impl__2jvef0(_Year___get_year__impl__hjar94($this) + delta | 0);
  }
  function Year__minus_impl_di8tne($this, delta) {
    return _Year___init__impl__2jvef0(_Year___get_year__impl__hjar94($this) - delta | 0);
  }
  function Year__minus_impl_di8tne_0($this, other) {
    return _Year___get_year__impl__hjar94($this) - _Year___get_year__impl__hjar94(other) | 0;
  }
  function Year__toString_impl_nmil18($this) {
    return 'Year(year=' + $this + ')';
  }
  function Year__hashCode_impl_pnfk51($this) {
    return $this;
  }
  function Year__equals_impl_co557z($this, other) {
    if (!(other instanceof Year))
      return false;
    if (!($this === (other instanceof Year ? other.year_1 : THROW_CCE())))
      return false;
    return true;
  }
  function Year(year) {
    Companion_getInstance_21();
    this.year_1 = year;
  }
  protoOf(Year).compareTo_g2ygo9_k$ = function (other) {
    return Year__compareTo_impl_13xet2(this.year_1, other);
  };
  protoOf(Year).compareTo_hpufkf_k$ = function (other) {
    return Year__compareTo_impl_13xet2_0(this, other);
  };
  protoOf(Year).toString = function () {
    return Year__toString_impl_nmil18(this.year_1);
  };
  protoOf(Year).hashCode = function () {
    return Year__hashCode_impl_pnfk51(this.year_1);
  };
  protoOf(Year).equals = function (other) {
    return Year__equals_impl_co557z(this.year_1, other);
  };
  function _get_serialVersionUID__fhggm9_14($this) {
    return $this.serialVersionUID_1;
  }
  function _YearMonth___init__impl__z1vit2(internalPackedInfo) {
    return internalPackedInfo;
  }
  function _YearMonth___get_internalPackedInfo__impl__o0axs($this) {
    return $this;
  }
  function Companion_20() {
    Companion_instance_20 = this;
    this.serialVersionUID_1 = new Long(1, 0);
  }
  protoOf(Companion_20).invoke_1jh3dp_k$ = function (year, month) {
    return Companion_getInstance_22().invoke_w9v7ia_k$(_Year___get_year__impl__hjar94(year), month.get_index1_etpsq0_k$());
  };
  protoOf(Companion_20).invoke_8unnwk_k$ = function (year, month) {
    return Companion_getInstance_22().invoke_w9v7ia_k$(year, month.get_index1_etpsq0_k$());
  };
  protoOf(Companion_20).invoke_w9v7ia_k$ = function (year, month1) {
    return _YearMonth___init__impl__z1vit2(year << 4 | month1 & 15);
  };
  var Companion_instance_20;
  function Companion_getInstance_22() {
    if (Companion_instance_20 == null)
      new Companion_20();
    return Companion_instance_20;
  }
  function _YearMonth___get_year__impl__iexttm($this) {
    return _Year___init__impl__2jvef0(_YearMonth___get_yearInt__impl__2gysi1($this));
  }
  function _YearMonth___get_yearInt__impl__2gysi1($this) {
    return _YearMonth___get_internalPackedInfo__impl__o0axs($this) >>> 4 | 0;
  }
  function _YearMonth___get_month__impl__9q6hh7($this) {
    return Companion_getInstance_11().get_c1px32_k$(_YearMonth___get_month1__impl__5u1az6($this));
  }
  function _YearMonth___get_month1__impl__5u1az6($this) {
    return _YearMonth___get_internalPackedInfo__impl__o0axs($this) & 15;
  }
  function _YearMonth___get_days__impl__1450to($this) {
    return _YearMonth___get_month__impl__9q6hh7($this).days_eg9z5s_k$(_YearMonth___get_year__impl__iexttm($this));
  }
  function _YearMonth___get_daysToStart__impl__y2mnf($this) {
    return _YearMonth___get_month__impl__9q6hh7($this).daysToStart_hdhiiv_k$(_YearMonth___get_year__impl__iexttm($this));
  }
  function _YearMonth___get_daysToEnd__impl__zgaewe($this) {
    return _YearMonth___get_month__impl__9q6hh7($this).daysToEnd_xa20r2_k$(_YearMonth___get_year__impl__iexttm($this));
  }
  function YearMonth__plus_impl_mcn3hs($this, span) {
    var newMonth = _YearMonth___get_month1__impl__5u1az6($this) + get_months_0(span) | 0;
    var yearAdjust = newMonth > 12 ? 1 : newMonth < 1 ? -1 : 0;
    return Companion_getInstance_22().invoke_1jh3dp_k$(_Year___init__impl__2jvef0((_YearMonth___get_yearInt__impl__2gysi1($this) + get_years(span) | 0) + yearAdjust | 0), Companion_getInstance_11().get_c1px32_k$(newMonth));
  }
  function YearMonth__minus_impl_nbwysg($this, span) {
    return YearMonth__plus_impl_mcn3hs($this, MonthSpan__unaryMinus_impl_3wgm0y(span));
  }
  function YearMonth__toString_impl_v1ol1e($this) {
    return toString(_YearMonth___get_month__impl__9q6hh7($this)) + ' ' + _YearMonth___get_yearInt__impl__2gysi1($this);
  }
  function YearMonth__hashCode_impl_i89k4v($this) {
    return $this;
  }
  function YearMonth__equals_impl_i5br91($this, other) {
    if (!(other instanceof YearMonth))
      return false;
    if (!($this === (other instanceof YearMonth ? other.internalPackedInfo_1 : THROW_CCE())))
      return false;
    return true;
  }
  function YearMonth(internalPackedInfo) {
    Companion_getInstance_22();
    this.internalPackedInfo_1 = internalPackedInfo;
  }
  protoOf(YearMonth).toString = function () {
    return YearMonth__toString_impl_v1ol1e(this.internalPackedInfo_1);
  };
  protoOf(YearMonth).hashCode = function () {
    return YearMonth__hashCode_impl_i89k4v(this.internalPackedInfo_1);
  };
  protoOf(YearMonth).equals = function (other) {
    return YearMonth__equals_impl_i5br91(this.internalPackedInfo_1, other);
  };
  function substr(_this__u8e3s4, start, length) {
    length = length === VOID ? _this__u8e3s4.length : length;
    var low = coerceIn(start >= 0 ? start : _this__u8e3s4.length + start | 0, 0, _this__u8e3s4.length);
    var high = coerceIn(length >= 0 ? low + length | 0 : _this__u8e3s4.length + length | 0, 0, _this__u8e3s4.length);
    var tmp;
    if (high < low) {
      tmp = '';
    } else {
      // Inline function 'kotlin.text.substring' call
      // Inline function 'kotlin.js.asDynamic' call
      tmp = _this__u8e3s4.substring(low, high);
    }
    return tmp;
  }
  function MicroStrReader(str, offset) {
    offset = offset === VOID ? 0 : offset;
    this.str_1 = str;
    this.offset_1 = offset;
  }
  protoOf(MicroStrReader).get_str_18ivy0_k$ = function () {
    return this.str_1;
  };
  protoOf(MicroStrReader).set_offset_az57y0_k$ = function (_set____db54di) {
    this.offset_1 = _set____db54di;
  };
  protoOf(MicroStrReader).get_offset_hjmqak_k$ = function () {
    return this.offset_1;
  };
  protoOf(MicroStrReader).get_length_g42xv3_k$ = function () {
    return this.str_1.length;
  };
  protoOf(MicroStrReader).get_available_3mf9b4_k$ = function () {
    return this.str_1.length - this.offset_1 | 0;
  };
  protoOf(MicroStrReader).get_hasMore_csdhd2_k$ = function () {
    return this.offset_1 < this.str_1.length;
  };
  protoOf(MicroStrReader).get_eof_18j6gd_k$ = function () {
    return !this.get_hasMore_csdhd2_k$();
  };
  protoOf(MicroStrReader).readChunk_lycuun_k$ = function (callback) {
    var start = this.offset_1;
    callback();
    var end = this.offset_1;
    // Inline function 'kotlin.text.substring' call
    // Inline function 'kotlin.js.asDynamic' call
    return this.str_1.substring(start, end);
  };
  protoOf(MicroStrReader).peekCharOrZero_56olb2_k$ = function () {
    return this.get_hasMore_csdhd2_k$() ? charSequenceGet(this.str_1, this.offset_1) : _Char___init__impl__6a9atx(0);
  };
  protoOf(MicroStrReader).peekChar_2c2lw3_k$ = function () {
    return charSequenceGet(this.str_1, this.offset_1);
  };
  protoOf(MicroStrReader).readChar_vl5a7m_k$ = function () {
    var _unary__edvuaz = this.offset_1;
    this.offset_1 = _unary__edvuaz + 1 | 0;
    return charSequenceGet(this.str_1, _unary__edvuaz);
  };
  protoOf(MicroStrReader).tryRead_2h17j0_k$ = function (expected) {
    if (this.get_eof_18j6gd_k$() ? true : !(this.peekChar_2c2lw3_k$() === expected))
      return false;
    this.readChar_vl5a7m_k$();
    return true;
  };
  protoOf(MicroStrReader).tryReadOrNull_rx9ydb_k$ = function (expected) {
    return this.tryRead_4mpw4p_k$(expected) ? expected : null;
  };
  protoOf(MicroStrReader).tryRead_4mpw4p_k$ = function (expected) {
    if (expected.length > this.get_available_3mf9b4_k$())
      return false;
    var inductionVariable = 0;
    var last = charSequenceLength(expected) - 1 | 0;
    if (inductionVariable <= last)
      do {
        var n = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (!(charSequenceGet(this.str_1, this.offset_1 + n | 0) === charSequenceGet(expected, n)))
          return false;
      }
       while (inductionVariable <= last);
    this.offset_1 = this.offset_1 + expected.length | 0;
    return true;
  };
  protoOf(MicroStrReader).read_yyte66_k$ = function (count) {
    // Inline function 'kotlin.also' call
    // Inline function 'kotlin.text.substring' call
    var this_0 = this.str_1;
    var startIndex = this.offset_1;
    var endIndex = coerceAtMost(this.offset_1 + count | 0, this.get_length_g42xv3_k$());
    // Inline function 'kotlin.js.asDynamic' call
    var this_1 = this_0.substring(startIndex, endIndex);
    // Inline function 'kotlin.contracts.contract' call
    // Inline function 'korlibs.time.internal.MicroStrReader.read.<anonymous>' call
    this.offset_1 = this.offset_1 + this_1.length | 0;
    return this_1;
  };
  protoOf(MicroStrReader).readRemaining_h6lqf4_k$ = function () {
    return this.read_yyte66_k$(this.get_available_3mf9b4_k$());
  };
  protoOf(MicroStrReader).readInt_1kkmv3_k$ = function (count) {
    return toInt(this.read_yyte66_k$(count));
  };
  protoOf(MicroStrReader).tryReadInt_8i7716_k$ = function (count) {
    return toIntOrNull(this.read_yyte66_k$(count));
  };
  protoOf(MicroStrReader).tryReadDouble_lgu422_k$ = function (count) {
    return toDoubleOrNull(replace_0(this.read_yyte66_k$(count), _Char___init__impl__6a9atx(44), _Char___init__impl__6a9atx(46)));
  };
  protoOf(MicroStrReader).tryReadDouble_8srzmq_k$ = function () {
    var numCount = 0;
    var num = 0;
    var denCount = 0;
    var den = 0;
    var decimals = false;
    loop: while (this.get_hasMore_csdhd2_k$()) {
      var pc = this.peekChar_2c2lw3_k$();
      if (pc === _Char___init__impl__6a9atx(44)) {
        if (numCount === 0) {
          return null;
        }
        decimals = true;
        new Char(this.readChar_vl5a7m_k$());
      } else if (_Char___init__impl__6a9atx(48) <= pc ? pc <= _Char___init__impl__6a9atx(57) : false) {
        var c = this.readChar_vl5a7m_k$();
        if (decimals) {
          denCount = denCount + 1 | 0;
          den = imul(den, 10);
          den = den + Char__minus_impl_a2frrh(c, _Char___init__impl__6a9atx(48)) | 0;
        } else {
          numCount = numCount + 1 | 0;
          num = imul(num, 10);
          num = num + Char__minus_impl_a2frrh(c, _Char___init__impl__6a9atx(48)) | 0;
        }
      } else {
        break loop;
      }
    }
    if (numCount === 0) {
      return null;
    }
    var tmp = num;
    var tmp_0 = den;
    // Inline function 'kotlin.math.pow' call
    var n = -denCount | 0;
    return tmp + tmp_0 * Math.pow(10.0, n);
  };
  function increment(_this__u8e3s4, key) {
    // Inline function 'kotlin.collections.getOrPut' call
    var value = _this__u8e3s4.get_wei43m_k$(key);
    var tmp;
    if (value == null) {
      // Inline function 'korlibs.time.internal.increment.<anonymous>' call
      var answer = 0;
      _this__u8e3s4.put_4fpzoq_k$(key, answer);
      tmp = answer;
    } else {
      tmp = value;
    }
    // Inline function 'kotlin.collections.set' call
    var value_0 = ensureNotNull(_this__u8e3s4.get_wei43m_k$(key)) + 1 | 0;
    _this__u8e3s4.put_4fpzoq_k$(key, value_0);
  }
  function padded(_this__u8e3s4, count) {
    // Inline function 'kotlin.math.absoluteValue' call
    var tmp$ret$0 = abs(_this__u8e3s4);
    var res = padStart(tmp$ret$0.toString(), count, _Char___init__impl__6a9atx(48));
    var tmp;
    if (_this__u8e3s4 < 0) {
      return '-' + res;
    } else {
      tmp = res;
    }
    return tmp;
  }
  function readTimeZoneOffset(_this__u8e3s4, tzNames) {
    tzNames = tzNames === VOID ? Companion_getInstance_19().get_DEFAULT_wccqmg_k$() : tzNames;
    var reader = _this__u8e3s4;
    // Inline function 'kotlin.collections.iterator' call
    var _iterator__ex2g4s = tzNames.get_namesToOffsets_qdh52e_k$().get_entries_p20ztl_k$().iterator_jk1svi_k$();
    $l$loop: while (_iterator__ex2g4s.hasNext_bitz1p_k$()) {
      var _destruct__k2r9zo = _iterator__ex2g4s.next_20eer_k$();
      // Inline function 'kotlin.collections.component1' call
      var name = _destruct__k2r9zo.get_key_18j28a_k$();
      // Inline function 'kotlin.collections.component2' call
      var offset = _destruct__k2r9zo.get_value_j01efc_k$().rawValue_1;
      if (name === 'GMT' ? true : name === 'UTC')
        continue $l$loop;
      if (reader.tryRead_4mpw4p_k$(name))
        return offset;
    }
    if (reader.tryRead_2h17j0_k$(_Char___init__impl__6a9atx(90))) {
      // Inline function 'korlibs.time.minutes' call
      // Inline function 'korlibs.time.minutes' call
      return toDuration(0, DurationUnit_MINUTES_getInstance());
    }
    var sign = 1;
    reader.tryRead_4mpw4p_k$('GMT');
    reader.tryRead_4mpw4p_k$('UTC');
    if (reader.tryRead_4mpw4p_k$('+')) {
      sign = 1;
    }
    if (reader.tryRead_4mpw4p_k$('-')) {
      sign = -1;
    }
    var part = replace(reader.readRemaining_h6lqf4_k$(), ':', '');
    var tmp0_elvis_lhs = toIntOrNull(padStart(substr(part, 0, 2), 2, _Char___init__impl__6a9atx(48)));
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var hours = tmp;
    var tmp1_elvis_lhs = toIntOrNull(padStart(substr(part, 2, 2), 2, _Char___init__impl__6a9atx(48)));
    var tmp_0;
    if (tmp1_elvis_lhs == null) {
      return null;
    } else {
      tmp_0 = tmp1_elvis_lhs;
    }
    var minutes = tmp_0;
    // Inline function 'korlibs.time.hours' call
    // Inline function 'korlibs.time.hours' call
    var tmp_1 = toDuration(hours, DurationUnit_HOURS_getInstance());
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'korlibs.time.minutes' call
    var tmp$ret$8 = toDuration(minutes, DurationUnit_MINUTES_getInstance());
    var roffset = Duration__plus_impl_yu9v8f(tmp_1, tmp$ret$8);
    return sign > 0 ? unaryPlus(roffset) : Duration__unaryMinus_impl_x2k1y0(roffset);
  }
  function spinlock(time) {
    var start = now(Companion_getInstance());
    while (Duration__compareTo_impl_pchp0f(Duration__minus_impl_q5cfm7(now(Companion_getInstance()), start), time) < 0)
    ;
  }
  function _set_avalue__ptnfqf($this, _set____db54di) {
    $this.avalue_1 = _set____db54di;
  }
  function _get_avalue__rae0d7($this) {
    return $this.avalue_1;
  }
  function _get_sign__ddo3e6($this) {
    return $this.sign_1;
  }
  function Moduler(value) {
    this.value_1 = value;
    var tmp = this;
    // Inline function 'kotlin.math.abs' call
    var x = this.value_1;
    tmp.avalue_1 = Math.abs(x);
    var tmp_0 = this;
    // Inline function 'kotlin.math.sign' call
    var x_0 = this.value_1;
    tmp_0.sign_1 = sign(x_0);
  }
  protoOf(Moduler).get_value_j01efc_k$ = function () {
    return this.value_1;
  };
  protoOf(Moduler).double_b139zp_k$ = function (count) {
    var ret = this.avalue_1 / count;
    this.avalue_1 = this.avalue_1 % count;
    // Inline function 'kotlin.math.floor' call
    return Math.floor(ret) * this.sign_1;
  };
  protoOf(Moduler).double_86m3c7_k$ = function (count) {
    return this.double_b139zp_k$(count);
  };
  protoOf(Moduler).double_fxlhx5_k$ = function (count) {
    return this.double_b139zp_k$(count);
  };
  protoOf(Moduler).int_feh05l_k$ = function (count) {
    return numberToInt(this.double_b139zp_k$(count));
  };
  protoOf(Moduler).int_hxmlc9_k$ = function (count) {
    return this.int_feh05l_k$(count);
  };
  protoOf(Moduler).int_b3apex_k$ = function (count) {
    return this.int_feh05l_k$(count);
  };
  function umod(_this__u8e3s4, other) {
    var rm = _this__u8e3s4 % other | 0;
    var remainder = rm === 0 ? 0 : rm;
    return remainder < 0 ? remainder + other | 0 : remainder;
  }
  function cycleSteps(_this__u8e3s4, min, max) {
    return (_this__u8e3s4 - min | 0) / ((max - min | 0) + 1 | 0) | 0;
  }
  function cycle(_this__u8e3s4, min, max) {
    return umod(_this__u8e3s4 - min | 0, (max - min | 0) + 1 | 0) + min | 0;
  }
  function toInt2(_this__u8e3s4) {
    var tmp;
    if (_this__u8e3s4 < 0.0) {
      // Inline function 'kotlin.math.floor' call
      var tmp$ret$0 = Math.floor(_this__u8e3s4);
      tmp = numberToInt(tmp$ret$0);
    } else {
      tmp = numberToInt(_this__u8e3s4);
    }
    return tmp;
  }
  function toIntMod(_this__u8e3s4, mod) {
    return toInt2(umod_0(_this__u8e3s4, mod));
  }
  function umod_0(_this__u8e3s4, other) {
    var rm = _this__u8e3s4 % other;
    var remainder = rm === -0.0 ? 0.0 : rm;
    return remainder < 0.0 ? remainder + other : remainder;
  }
  function set_ExtendedTimezoneNamesOrNull(_set____db54di) {
    ExtendedTimezoneNamesOrNull = _set____db54di;
  }
  function get_ExtendedTimezoneNamesOrNull() {
    return ExtendedTimezoneNamesOrNull;
  }
  var ExtendedTimezoneNamesOrNull;
  function Serializable() {
  }
  function KlockInternal() {
    KlockInternal_instance = this;
  }
  protoOf(KlockInternal).get_currentTime_nu5t31_k$ = function () {
    // Inline function 'kotlin.js.unsafeCast' call
    return Date.now();
  };
  protoOf(KlockInternal).get_now_rsuecj_k$ = function () {
    return fromMilliseconds(Companion_getInstance(), globalThis.performance.now());
  };
  protoOf(KlockInternal).localTimezoneOffsetMinutes_3dowjn_k$ = function (time) {
    var rtime = _DateTime___get_unixMillisDouble__impl__3dzlsc(time);
    // Inline function 'korlibs.time.minutes' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'korlibs.time.minutes' call
    var this_0 = -(new Date(rtime)).getTimezoneOffset();
    return toDuration(this_0, DurationUnit_MINUTES_getInstance());
  };
  protoOf(KlockInternal).sleep_sn34jm_k$ = function (time) {
    spinlock(time);
  };
  var KlockInternal_instance;
  function KlockInternal_getInstance() {
    if (KlockInternal_instance == null)
      new KlockInternal();
    return KlockInternal_instance;
  }
  //region block: post-declaration
  protoOf(PatternDateFormat).tryParse$default_mlfwtq_k$ = tryParse$default;
  protoOf(PatternTimeFormat).tryParse$default_2fqawp_k$ = tryParse$default_0;
  //endregion
  //region block: init
  KlockLocale_default = null;
  ExtendedTimezoneNamesOrNull = null;
  //endregion
  return _;
}));
