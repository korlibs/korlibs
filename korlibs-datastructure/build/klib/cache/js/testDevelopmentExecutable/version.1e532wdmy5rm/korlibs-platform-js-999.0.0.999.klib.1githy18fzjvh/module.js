(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'korge-root-korlibs-platform'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'korge-root-korlibs-platform'.");
    }
    root['korge-root-korlibs-platform'] = factory(typeof this['korge-root-korlibs-platform'] === 'undefined' ? {} : this['korge-root-korlibs-platform'], this['kotlin-kotlin-stdlib']);
  }
}(this, function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var imul = Math.imul;
  var protoOf = kotlin_kotlin.$_$.x9;
  var initMetadataForCompanion = kotlin_kotlin.$_$.e9;
  var THROW_IAE = kotlin_kotlin.$_$.pc;
  var enumEntries = kotlin_kotlin.$_$.d8;
  var Unit_getInstance = kotlin_kotlin.$_$.r2;
  var VOID = kotlin_kotlin.$_$.d;
  var Enum = kotlin_kotlin.$_$.ec;
  var initMetadataForClass = kotlin_kotlin.$_$.d9;
  var getStringHashCode = kotlin_kotlin.$_$.b9;
  var getBooleanHashCode = kotlin_kotlin.$_$.y8;
  var THROW_CCE = kotlin_kotlin.$_$.oc;
  var initMetadataForInterface = kotlin_kotlin.$_$.g9;
  var toString = kotlin_kotlin.$_$.ba;
  var IllegalStateException_init_$Create$ = kotlin_kotlin.$_$.f1;
  var printStackTrace = kotlin_kotlin.$_$.vc;
  var KProperty0 = kotlin_kotlin.$_$.pa;
  var getPropertyCallableRef = kotlin_kotlin.$_$.a9;
  var lazy = kotlin_kotlin.$_$.uc;
  //endregion
  //region block: pre-declaration
  initMetadataForCompanion(Companion);
  initMetadataForClass(Arch, 'Arch', VOID, Enum);
  initMetadataForCompanion(Companion_0);
  initMetadataForClass(BuildVariant, 'BuildVariant', VOID, Enum);
  initMetadataForCompanion(Companion_1);
  initMetadataForClass(Endian, 'Endian', VOID, Enum);
  initMetadataForCompanion(Companion_2);
  initMetadataForClass(Os, 'Os', VOID, Enum);
  function get_isLittleEndian() {
    return this.get_endian_cxjdlw_k$().equals(Endian_LITTLE_ENDIAN_getInstance());
  }
  function get_isBigEndian() {
    return this.get_endian_cxjdlw_k$().equals(Endian_BIG_ENDIAN_getInstance());
  }
  function get_isDebug() {
    return this.get_buildVariant_cflrwg_k$().equals(BuildVariant_DEBUG_getInstance());
  }
  function get_isRelease() {
    return this.get_buildVariant_cflrwg_k$().equals(BuildVariant_RELEASE_getInstance());
  }
  function get_isWindows() {
    return this.get_os_kntnrn_k$().get_isWindows_l8sguo_k$();
  }
  function get_isUnix() {
    return this.get_os_kntnrn_k$().get_isPosix_zgxy8g_k$();
  }
  function get_isPosix() {
    return this.get_os_kntnrn_k$().get_isPosix_zgxy8g_k$();
  }
  function get_isLinux() {
    return this.get_os_kntnrn_k$().get_isLinux_zemuip_k$();
  }
  function get_isMac() {
    return this.get_os_kntnrn_k$().get_isMac_it6x0c_k$();
  }
  function get_isApple() {
    return this.get_os_kntnrn_k$().get_isApple_z8pm5j_k$();
  }
  function get_isAppleMobile() {
    return this.get_os_kntnrn_k$().get_isAppleMobile_3a14o9_k$();
  }
  function get_isIos() {
    return this.get_os_kntnrn_k$().get_isIos_it6ue2_k$();
  }
  function get_isAndroid() {
    return this.get_os_kntnrn_k$().get_isAndroid_fbign8_k$();
  }
  function get_isTvos() {
    return this.get_os_kntnrn_k$().get_isTvos_ew6wc9_k$();
  }
  function get_isJs() {
    return this.get_runtime_qwyghb_k$().get_isJs_woo47w_k$();
  }
  function get_isNative() {
    return this.get_runtime_qwyghb_k$().get_isNative_wyrxka_k$();
  }
  function get_isNativeDesktop() {
    return this.get_isNative_wyrxka_k$() ? this.get_os_kntnrn_k$().get_isDesktop_ooecbt_k$() : false;
  }
  function get_isJvm() {
    return this.get_runtime_qwyghb_k$().get_isJvm_it6vam_k$();
  }
  function get_isWasm() {
    return this.get_runtime_qwyghb_k$().get_isWasm_ew8dtj_k$();
  }
  function get_isJsOrWasm() {
    return this.get_isJs_woo47w_k$() ? true : this.get_isWasm_ew8dtj_k$();
  }
  function get_isJsShell() {
    return this.get_rawPlatformName_76v5dp_k$() === 'js-shell' ? true : this.get_rawPlatformName_76v5dp_k$() === 'wasm-shell';
  }
  function get_isJsNodeJs() {
    return this.get_rawPlatformName_76v5dp_k$() === 'js-node' ? true : this.get_rawPlatformName_76v5dp_k$() === 'wasm-node';
  }
  function get_isJsDenoJs() {
    return this.get_rawPlatformName_76v5dp_k$() === 'js-deno' ? true : this.get_rawPlatformName_76v5dp_k$() === 'wasm-deno';
  }
  function get_isJsBrowser() {
    return this.get_rawPlatformName_76v5dp_k$() === 'js-web' ? true : this.get_rawPlatformName_76v5dp_k$() === 'wasm-web';
  }
  function get_isJsWorker() {
    return this.get_rawPlatformName_76v5dp_k$() === 'js-worker' ? true : this.get_rawPlatformName_76v5dp_k$() === 'wasm-worker';
  }
  function get_isJsBrowserOrWorker() {
    return this.get_isJsBrowser_3lmn6c_k$() ? true : this.get_isJsWorker_6991qe_k$();
  }
  initMetadataForInterface(Platform, 'Platform');
  initMetadataForCompanion(Companion_3, VOID, [Platform]);
  initMetadataForClass(Impl, 'Impl', VOID, VOID, [Platform]);
  initMetadataForCompanion(Companion_4);
  initMetadataForClass(Runtime, 'Runtime', VOID, Enum);
  //endregion
  var Arch_UNKNOWN_instance;
  var Arch_X86_instance;
  var Arch_X64_instance;
  var Arch_ARM32_instance;
  var Arch_ARM64_instance;
  var Arch_MIPS32_instance;
  var Arch_MIPSEL32_instance;
  var Arch_MIPS64_instance;
  var Arch_MIPSEL64_instance;
  var Arch_WASM32_instance;
  var Arch_POWERPC64_instance;
  function Companion() {
    Companion_instance = this;
  }
  protoOf(Companion).get_CURRENT_vrz1io_k$ = function () {
    return get_currentArch();
  };
  var Companion_instance;
  function Companion_getInstance() {
    Arch_initEntries();
    if (Companion_instance == null)
      new Companion();
    return Companion_instance;
  }
  function values() {
    return [Arch_UNKNOWN_getInstance(), Arch_X86_getInstance(), Arch_X64_getInstance(), Arch_ARM32_getInstance(), Arch_ARM64_getInstance(), Arch_MIPS32_getInstance(), Arch_MIPSEL32_getInstance(), Arch_MIPS64_getInstance(), Arch_MIPSEL64_getInstance(), Arch_WASM32_getInstance(), Arch_POWERPC64_getInstance()];
  }
  function valueOf(value) {
    switch (value) {
      case 'UNKNOWN':
        return Arch_UNKNOWN_getInstance();
      case 'X86':
        return Arch_X86_getInstance();
      case 'X64':
        return Arch_X64_getInstance();
      case 'ARM32':
        return Arch_ARM32_getInstance();
      case 'ARM64':
        return Arch_ARM64_getInstance();
      case 'MIPS32':
        return Arch_MIPS32_getInstance();
      case 'MIPSEL32':
        return Arch_MIPSEL32_getInstance();
      case 'MIPS64':
        return Arch_MIPS64_getInstance();
      case 'MIPSEL64':
        return Arch_MIPSEL64_getInstance();
      case 'WASM32':
        return Arch_WASM32_getInstance();
      case 'POWERPC64':
        return Arch_POWERPC64_getInstance();
      default:
        Arch_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries() {
    if ($ENTRIES == null)
      $ENTRIES = enumEntries(values());
    return $ENTRIES;
  }
  var Arch_entriesInitialized;
  function Arch_initEntries() {
    if (Arch_entriesInitialized)
      return Unit_getInstance();
    Arch_entriesInitialized = true;
    Arch_UNKNOWN_instance = new Arch('UNKNOWN', 0, -1);
    Arch_X86_instance = new Arch('X86', 1, 32, VOID, true);
    Arch_X64_instance = new Arch('X64', 2, 64, VOID, true);
    Arch_ARM32_instance = new Arch('ARM32', 3, 32, true);
    Arch_ARM64_instance = new Arch('ARM64', 4, 64, true);
    Arch_MIPS32_instance = new Arch('MIPS32', 5, 32, VOID, VOID, true);
    Arch_MIPSEL32_instance = new Arch('MIPSEL32', 6, 32, VOID, VOID, true);
    Arch_MIPS64_instance = new Arch('MIPS64', 7, 64, VOID, VOID, true);
    Arch_MIPSEL64_instance = new Arch('MIPSEL64', 8, 64, VOID, VOID, true);
    Arch_WASM32_instance = new Arch('WASM32', 9, 32, VOID, VOID, VOID, true);
    Arch_POWERPC64_instance = new Arch('POWERPC64', 10, 64, VOID, VOID, VOID, VOID, true);
    Companion_getInstance();
  }
  var $ENTRIES;
  function Arch(name, ordinal, bits, isArm, isX86OrX64, isMips, isWasm, isPowerPC) {
    isArm = isArm === VOID ? false : isArm;
    isX86OrX64 = isX86OrX64 === VOID ? false : isX86OrX64;
    isMips = isMips === VOID ? false : isMips;
    isWasm = isWasm === VOID ? false : isWasm;
    isPowerPC = isPowerPC === VOID ? false : isPowerPC;
    Enum.call(this, name, ordinal);
    this.bits_1 = bits;
    this.isArm_1 = isArm;
    this.isX86OrX64__1 = isX86OrX64;
    this.isMips_1 = isMips;
    this.isWasm_1 = isWasm;
    this.isPowerPC_1 = isPowerPC;
  }
  protoOf(Arch).get_bits_wojgwf_k$ = function () {
    return this.bits_1;
  };
  protoOf(Arch).get_isArm_it6oix_k$ = function () {
    return this.isArm_1;
  };
  protoOf(Arch).get_isX86OrX64_cdruj4_k$ = function () {
    return this.isX86OrX64__1;
  };
  protoOf(Arch).get_isMips_ew25te_k$ = function () {
    return this.isMips_1;
  };
  protoOf(Arch).get_isWasm_ew8dtj_k$ = function () {
    return this.isWasm_1;
  };
  protoOf(Arch).get_isPowerPC_7hmwsb_k$ = function () {
    return this.isPowerPC_1;
  };
  protoOf(Arch).get_is32Bits_jftvnc_k$ = function () {
    return this.bits_1 === 32;
  };
  protoOf(Arch).get_is64Bits_kw2c0n_k$ = function () {
    return this.bits_1 === 64;
  };
  protoOf(Arch).get_isX86_it745f_k$ = function () {
    return this.equals(Arch_X86_getInstance());
  };
  protoOf(Arch).get_isX64_it743n_k$ = function () {
    return this.equals(Arch_X64_getInstance());
  };
  protoOf(Arch).get_isArm32_z8qsi0_k$ = function () {
    return this.equals(Arch_ARM32_getInstance());
  };
  protoOf(Arch).get_isArm64_z8qskn_k$ = function () {
    return this.equals(Arch_ARM64_getInstance());
  };
  protoOf(Arch).get_isMIPS32_w3vrg1_k$ = function () {
    return this.equals(Arch_MIPS32_getInstance());
  };
  protoOf(Arch).get_isMIPSEL32_s8bvvc_k$ = function () {
    return this.equals(Arch_MIPSEL32_getInstance());
  };
  protoOf(Arch).get_isMIPS64_w3vrio_k$ = function () {
    return this.equals(Arch_MIPS64_getInstance());
  };
  protoOf(Arch).get_isMIPSEL64_s8bvxz_k$ = function () {
    return this.equals(Arch_MIPSEL64_getInstance());
  };
  protoOf(Arch).get_isWASM32_yb4mtm_k$ = function () {
    return this.equals(Arch_WASM32_getInstance());
  };
  protoOf(Arch).get_isPOWERPC64_apo3y5_k$ = function () {
    return this.equals(Arch_POWERPC64_getInstance());
  };
  function Arch_UNKNOWN_getInstance() {
    Arch_initEntries();
    return Arch_UNKNOWN_instance;
  }
  function Arch_X86_getInstance() {
    Arch_initEntries();
    return Arch_X86_instance;
  }
  function Arch_X64_getInstance() {
    Arch_initEntries();
    return Arch_X64_instance;
  }
  function Arch_ARM32_getInstance() {
    Arch_initEntries();
    return Arch_ARM32_instance;
  }
  function Arch_ARM64_getInstance() {
    Arch_initEntries();
    return Arch_ARM64_instance;
  }
  function Arch_MIPS32_getInstance() {
    Arch_initEntries();
    return Arch_MIPS32_instance;
  }
  function Arch_MIPSEL32_getInstance() {
    Arch_initEntries();
    return Arch_MIPSEL32_instance;
  }
  function Arch_MIPS64_getInstance() {
    Arch_initEntries();
    return Arch_MIPS64_instance;
  }
  function Arch_MIPSEL64_getInstance() {
    Arch_initEntries();
    return Arch_MIPSEL64_instance;
  }
  function Arch_WASM32_getInstance() {
    Arch_initEntries();
    return Arch_WASM32_instance;
  }
  function Arch_POWERPC64_getInstance() {
    Arch_initEntries();
    return Arch_POWERPC64_instance;
  }
  var BuildVariant_DEBUG_instance;
  var BuildVariant_RELEASE_instance;
  function Companion_0() {
    Companion_instance_0 = this;
  }
  protoOf(Companion_0).get_CURRENT_vrz1io_k$ = function () {
    return get_currentBuildVariant();
  };
  var Companion_instance_0;
  function Companion_getInstance_0() {
    BuildVariant_initEntries();
    if (Companion_instance_0 == null)
      new Companion_0();
    return Companion_instance_0;
  }
  function values_0() {
    return [BuildVariant_DEBUG_getInstance(), BuildVariant_RELEASE_getInstance()];
  }
  function valueOf_0(value) {
    switch (value) {
      case 'DEBUG':
        return BuildVariant_DEBUG_getInstance();
      case 'RELEASE':
        return BuildVariant_RELEASE_getInstance();
      default:
        BuildVariant_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_0() {
    if ($ENTRIES_0 == null)
      $ENTRIES_0 = enumEntries(values_0());
    return $ENTRIES_0;
  }
  var BuildVariant_entriesInitialized;
  function BuildVariant_initEntries() {
    if (BuildVariant_entriesInitialized)
      return Unit_getInstance();
    BuildVariant_entriesInitialized = true;
    BuildVariant_DEBUG_instance = new BuildVariant('DEBUG', 0);
    BuildVariant_RELEASE_instance = new BuildVariant('RELEASE', 1);
    Companion_getInstance_0();
  }
  var $ENTRIES_0;
  function BuildVariant(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  protoOf(BuildVariant).get_isDebug_za5oww_k$ = function () {
    return this.equals(BuildVariant_DEBUG_getInstance());
  };
  protoOf(BuildVariant).get_isRelease_gyndg4_k$ = function () {
    return this.equals(BuildVariant_RELEASE_getInstance());
  };
  function BuildVariant_DEBUG_getInstance() {
    BuildVariant_initEntries();
    return BuildVariant_DEBUG_instance;
  }
  function BuildVariant_RELEASE_getInstance() {
    BuildVariant_initEntries();
    return BuildVariant_RELEASE_instance;
  }
  function get_currentBuildVariant() {
    return get_currentIsDebug() ? BuildVariant_DEBUG_getInstance() : BuildVariant_RELEASE_getInstance();
  }
  var Endian_LITTLE_ENDIAN_instance;
  var Endian_BIG_ENDIAN_instance;
  function Companion_1() {
    Companion_instance_1 = this;
    this.NATIVE_1 = get_currentIsLittleEndian() ? Endian_LITTLE_ENDIAN_getInstance() : Endian_BIG_ENDIAN_getInstance();
  }
  protoOf(Companion_1).get_isLittleEndian_ny1aws_k$ = function () {
    return get_currentIsLittleEndian();
  };
  protoOf(Companion_1).get_isBigEndian_s607nc_k$ = function () {
    return !get_currentIsLittleEndian();
  };
  protoOf(Companion_1).get_NATIVE_1cgg0w_k$ = function () {
    return this.NATIVE_1;
  };
  var Companion_instance_1;
  function Companion_getInstance_1() {
    Endian_initEntries();
    if (Companion_instance_1 == null)
      new Companion_1();
    return Companion_instance_1;
  }
  function values_1() {
    return [Endian_LITTLE_ENDIAN_getInstance(), Endian_BIG_ENDIAN_getInstance()];
  }
  function valueOf_1(value) {
    switch (value) {
      case 'LITTLE_ENDIAN':
        return Endian_LITTLE_ENDIAN_getInstance();
      case 'BIG_ENDIAN':
        return Endian_BIG_ENDIAN_getInstance();
      default:
        Endian_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_1() {
    if ($ENTRIES_1 == null)
      $ENTRIES_1 = enumEntries(values_1());
    return $ENTRIES_1;
  }
  var Endian_entriesInitialized;
  function Endian_initEntries() {
    if (Endian_entriesInitialized)
      return Unit_getInstance();
    Endian_entriesInitialized = true;
    Endian_LITTLE_ENDIAN_instance = new Endian('LITTLE_ENDIAN', 0);
    Endian_BIG_ENDIAN_instance = new Endian('BIG_ENDIAN', 1);
    Companion_getInstance_1();
  }
  var $ENTRIES_1;
  function Endian(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  protoOf(Endian).get_isLittle_w539ft_k$ = function () {
    return this.equals(Endian_LITTLE_ENDIAN_getInstance());
  };
  protoOf(Endian).get_isBig_it6p1p_k$ = function () {
    return this.equals(Endian_BIG_ENDIAN_getInstance());
  };
  function Endian_LITTLE_ENDIAN_getInstance() {
    Endian_initEntries();
    return Endian_LITTLE_ENDIAN_instance;
  }
  function Endian_BIG_ENDIAN_getInstance() {
    Endian_initEntries();
    return Endian_BIG_ENDIAN_instance;
  }
  var Os_UNKNOWN_instance;
  var Os_MACOSX_instance;
  var Os_IOS_instance;
  var Os_LINUX_instance;
  var Os_WINDOWS_instance;
  var Os_ANDROID_instance;
  var Os_WASM_instance;
  var Os_TVOS_instance;
  function Companion_2() {
    Companion_instance_2 = this;
    this.VALUES_1 = values_2();
  }
  protoOf(Companion_2).get_VALUES_54ojsb_k$ = function () {
    return this.VALUES_1;
  };
  protoOf(Companion_2).get_CURRENT_vrz1io_k$ = function () {
    return get_currentOs();
  };
  var Companion_instance_2;
  function Companion_getInstance_2() {
    Os_initEntries();
    if (Companion_instance_2 == null)
      new Companion_2();
    return Companion_instance_2;
  }
  function values_2() {
    return [Os_UNKNOWN_getInstance(), Os_MACOSX_getInstance(), Os_IOS_getInstance(), Os_LINUX_getInstance(), Os_WINDOWS_getInstance(), Os_ANDROID_getInstance(), Os_WASM_getInstance(), Os_TVOS_getInstance()];
  }
  function valueOf_2(value) {
    switch (value) {
      case 'UNKNOWN':
        return Os_UNKNOWN_getInstance();
      case 'MACOSX':
        return Os_MACOSX_getInstance();
      case 'IOS':
        return Os_IOS_getInstance();
      case 'LINUX':
        return Os_LINUX_getInstance();
      case 'WINDOWS':
        return Os_WINDOWS_getInstance();
      case 'ANDROID':
        return Os_ANDROID_getInstance();
      case 'WASM':
        return Os_WASM_getInstance();
      case 'TVOS':
        return Os_TVOS_getInstance();
      default:
        Os_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_2() {
    if ($ENTRIES_2 == null)
      $ENTRIES_2 = enumEntries(values_2());
    return $ENTRIES_2;
  }
  var Os_entriesInitialized;
  function Os_initEntries() {
    if (Os_entriesInitialized)
      return Unit_getInstance();
    Os_entriesInitialized = true;
    Os_UNKNOWN_instance = new Os('UNKNOWN', 0);
    Os_MACOSX_instance = new Os('MACOSX', 1);
    Os_IOS_instance = new Os('IOS', 2);
    Os_LINUX_instance = new Os('LINUX', 3);
    Os_WINDOWS_instance = new Os('WINDOWS', 4);
    Os_ANDROID_instance = new Os('ANDROID', 5);
    Os_WASM_instance = new Os('WASM', 6);
    Os_TVOS_instance = new Os('TVOS', 7);
    Companion_getInstance_2();
  }
  var $ENTRIES_2;
  function Os(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  protoOf(Os).get_isWindows_l8sguo_k$ = function () {
    return this.equals(Os_WINDOWS_getInstance());
  };
  protoOf(Os).get_isAndroid_fbign8_k$ = function () {
    return this.equals(Os_ANDROID_getInstance());
  };
  protoOf(Os).get_isLinux_zemuip_k$ = function () {
    return this.equals(Os_LINUX_getInstance());
  };
  protoOf(Os).get_isMac_it6x0c_k$ = function () {
    return this.equals(Os_MACOSX_getInstance());
  };
  protoOf(Os).get_isIos_it6ue2_k$ = function () {
    return this.equals(Os_IOS_getInstance());
  };
  protoOf(Os).get_isTvos_ew6wc9_k$ = function () {
    return this.equals(Os_TVOS_getInstance());
  };
  protoOf(Os).get_isAppleMobile_3a14o9_k$ = function () {
    return this.get_isIos_it6ue2_k$() ? true : this.get_isTvos_ew6wc9_k$();
  };
  protoOf(Os).get_isDesktop_ooecbt_k$ = function () {
    return (this.get_isLinux_zemuip_k$() ? true : this.get_isWindows_l8sguo_k$()) ? true : this.get_isMac_it6x0c_k$();
  };
  protoOf(Os).get_isMobile_wp3xh1_k$ = function () {
    return this.get_isAndroid_fbign8_k$() ? true : this.get_isAppleMobile_3a14o9_k$();
  };
  protoOf(Os).get_isApple_z8pm5j_k$ = function () {
    return this.get_isMac_it6x0c_k$() ? true : this.get_isAppleMobile_3a14o9_k$();
  };
  protoOf(Os).get_isPosix_zgxy8g_k$ = function () {
    return !this.get_isWindows_l8sguo_k$();
  };
  function Os_UNKNOWN_getInstance() {
    Os_initEntries();
    return Os_UNKNOWN_instance;
  }
  function Os_MACOSX_getInstance() {
    Os_initEntries();
    return Os_MACOSX_instance;
  }
  function Os_IOS_getInstance() {
    Os_initEntries();
    return Os_IOS_instance;
  }
  function Os_LINUX_getInstance() {
    Os_initEntries();
    return Os_LINUX_instance;
  }
  function Os_WINDOWS_getInstance() {
    Os_initEntries();
    return Os_WINDOWS_instance;
  }
  function Os_ANDROID_getInstance() {
    Os_initEntries();
    return Os_ANDROID_instance;
  }
  function Os_WASM_getInstance() {
    Os_initEntries();
    return Os_WASM_instance;
  }
  function Os_TVOS_getInstance() {
    Os_initEntries();
    return Os_TVOS_instance;
  }
  function Companion_3() {
    Companion_instance_3 = this;
  }
  protoOf(Companion_3).get_endian_cxjdlw_k$ = function () {
    return Companion_getInstance_1().get_NATIVE_1cgg0w_k$();
  };
  protoOf(Companion_3).get_isLittleEndian_ny1aws_k$ = function () {
    return get_currentIsLittleEndian();
  };
  protoOf(Companion_3).get_isBigEndian_s607nc_k$ = function () {
    return !get_currentIsLittleEndian();
  };
  protoOf(Companion_3).get_arch_woj067_k$ = function () {
    return Companion_getInstance().get_CURRENT_vrz1io_k$();
  };
  protoOf(Companion_3).get_os_kntnrn_k$ = function () {
    return Companion_getInstance_2().get_CURRENT_vrz1io_k$();
  };
  protoOf(Companion_3).get_runtime_qwyghb_k$ = function () {
    return Companion_getInstance_4().get_CURRENT_vrz1io_k$();
  };
  protoOf(Companion_3).get_rawPlatformName_76v5dp_k$ = function () {
    return get_currentRawPlatformName();
  };
  protoOf(Companion_3).get_rawOsName_p14hxu_k$ = function () {
    return get_currentRawOsName();
  };
  protoOf(Companion_3).get_buildVariant_cflrwg_k$ = function () {
    return Companion_getInstance_0().get_CURRENT_vrz1io_k$();
  };
  protoOf(Companion_3).get_isDebug_za5oww_k$ = function () {
    return get_currentIsDebug();
  };
  protoOf(Companion_3).get_isRelease_gyndg4_k$ = function () {
    return !get_currentIsDebug();
  };
  protoOf(Companion_3).get_hasMultithreadedSharedHeap_btcyv6_k$ = function () {
    return get_multithreadedSharedHeap();
  };
  protoOf(Companion_3).invoke_q7yntb_k$ = function (endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap) {
    return new Impl(endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap);
  };
  protoOf(Companion_3).invoke$default_wa22lw_k$ = function (endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap, $super) {
    endian = endian === VOID ? Endian_LITTLE_ENDIAN_getInstance() : endian;
    arch = arch === VOID ? Arch_UNKNOWN_getInstance() : arch;
    os = os === VOID ? Os_UNKNOWN_getInstance() : os;
    runtime = runtime === VOID ? Runtime_JVM_getInstance() : runtime;
    buildVariant = buildVariant === VOID ? BuildVariant_DEBUG_getInstance() : buildVariant;
    rawPlatformName = rawPlatformName === VOID ? 'unknown' : rawPlatformName;
    rawOsName = rawOsName === VOID ? 'unknown' : rawOsName;
    hasMultithreadedSharedHeap = hasMultithreadedSharedHeap === VOID ? false : hasMultithreadedSharedHeap;
    return $super === VOID ? this.invoke_q7yntb_k$(endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap) : $super.invoke_q7yntb_k$.call(this, endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap);
  };
  var Companion_instance_3;
  function Companion_getInstance_3() {
    if (Companion_instance_3 == null)
      new Companion_3();
    return Companion_instance_3;
  }
  function Impl(endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap) {
    this.endian_1 = endian;
    this.arch_1 = arch;
    this.os_1 = os;
    this.runtime_1 = runtime;
    this.buildVariant_1 = buildVariant;
    this.rawPlatformName_1 = rawPlatformName;
    this.rawOsName_1 = rawOsName;
    this.hasMultithreadedSharedHeap_1 = hasMultithreadedSharedHeap;
  }
  protoOf(Impl).get_endian_cxjdlw_k$ = function () {
    return this.endian_1;
  };
  protoOf(Impl).get_arch_woj067_k$ = function () {
    return this.arch_1;
  };
  protoOf(Impl).get_os_kntnrn_k$ = function () {
    return this.os_1;
  };
  protoOf(Impl).get_runtime_qwyghb_k$ = function () {
    return this.runtime_1;
  };
  protoOf(Impl).get_buildVariant_cflrwg_k$ = function () {
    return this.buildVariant_1;
  };
  protoOf(Impl).get_rawPlatformName_76v5dp_k$ = function () {
    return this.rawPlatformName_1;
  };
  protoOf(Impl).get_rawOsName_p14hxu_k$ = function () {
    return this.rawOsName_1;
  };
  protoOf(Impl).get_hasMultithreadedSharedHeap_btcyv6_k$ = function () {
    return this.hasMultithreadedSharedHeap_1;
  };
  protoOf(Impl).component1_7eebsc_k$ = function () {
    return this.endian_1;
  };
  protoOf(Impl).component2_7eebsb_k$ = function () {
    return this.arch_1;
  };
  protoOf(Impl).component3_7eebsa_k$ = function () {
    return this.os_1;
  };
  protoOf(Impl).component4_7eebs9_k$ = function () {
    return this.runtime_1;
  };
  protoOf(Impl).component5_7eebs8_k$ = function () {
    return this.buildVariant_1;
  };
  protoOf(Impl).component6_7eebs7_k$ = function () {
    return this.rawPlatformName_1;
  };
  protoOf(Impl).component7_7eebs6_k$ = function () {
    return this.rawOsName_1;
  };
  protoOf(Impl).component8_7eebs5_k$ = function () {
    return this.hasMultithreadedSharedHeap_1;
  };
  protoOf(Impl).copy_vn89jm_k$ = function (endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap) {
    return new Impl(endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap);
  };
  protoOf(Impl).copy$default_krj5nx_k$ = function (endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap, $super) {
    endian = endian === VOID ? this.endian_1 : endian;
    arch = arch === VOID ? this.arch_1 : arch;
    os = os === VOID ? this.os_1 : os;
    runtime = runtime === VOID ? this.runtime_1 : runtime;
    buildVariant = buildVariant === VOID ? this.buildVariant_1 : buildVariant;
    rawPlatformName = rawPlatformName === VOID ? this.rawPlatformName_1 : rawPlatformName;
    rawOsName = rawOsName === VOID ? this.rawOsName_1 : rawOsName;
    hasMultithreadedSharedHeap = hasMultithreadedSharedHeap === VOID ? this.hasMultithreadedSharedHeap_1 : hasMultithreadedSharedHeap;
    return $super === VOID ? this.copy_vn89jm_k$(endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap) : $super.copy_vn89jm_k$.call(this, endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap);
  };
  protoOf(Impl).toString = function () {
    return 'Impl(endian=' + this.endian_1.toString() + ', arch=' + this.arch_1.toString() + ', os=' + this.os_1.toString() + ', runtime=' + this.runtime_1.toString() + ', buildVariant=' + this.buildVariant_1.toString() + ', rawPlatformName=' + this.rawPlatformName_1 + ', rawOsName=' + this.rawOsName_1 + ', hasMultithreadedSharedHeap=' + this.hasMultithreadedSharedHeap_1 + ')';
  };
  protoOf(Impl).hashCode = function () {
    var result = this.endian_1.hashCode();
    result = imul(result, 31) + this.arch_1.hashCode() | 0;
    result = imul(result, 31) + this.os_1.hashCode() | 0;
    result = imul(result, 31) + this.runtime_1.hashCode() | 0;
    result = imul(result, 31) + this.buildVariant_1.hashCode() | 0;
    result = imul(result, 31) + getStringHashCode(this.rawPlatformName_1) | 0;
    result = imul(result, 31) + getStringHashCode(this.rawOsName_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.hasMultithreadedSharedHeap_1) | 0;
    return result;
  };
  protoOf(Impl).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Impl))
      return false;
    var tmp0_other_with_cast = other instanceof Impl ? other : THROW_CCE();
    if (!this.endian_1.equals(tmp0_other_with_cast.endian_1))
      return false;
    if (!this.arch_1.equals(tmp0_other_with_cast.arch_1))
      return false;
    if (!this.os_1.equals(tmp0_other_with_cast.os_1))
      return false;
    if (!this.runtime_1.equals(tmp0_other_with_cast.runtime_1))
      return false;
    if (!this.buildVariant_1.equals(tmp0_other_with_cast.buildVariant_1))
      return false;
    if (!(this.rawPlatformName_1 === tmp0_other_with_cast.rawPlatformName_1))
      return false;
    if (!(this.rawOsName_1 === tmp0_other_with_cast.rawOsName_1))
      return false;
    if (!(this.hasMultithreadedSharedHeap_1 === tmp0_other_with_cast.hasMultithreadedSharedHeap_1))
      return false;
    return true;
  };
  function Platform() {
  }
  var Runtime_JS_instance;
  var Runtime_JVM_instance;
  var Runtime_ANDROID_instance;
  var Runtime_NATIVE_instance;
  var Runtime_WASM_instance;
  function Companion_4() {
    Companion_instance_4 = this;
  }
  protoOf(Companion_4).get_CURRENT_vrz1io_k$ = function () {
    return get_currentRuntime();
  };
  var Companion_instance_4;
  function Companion_getInstance_4() {
    Runtime_initEntries();
    if (Companion_instance_4 == null)
      new Companion_4();
    return Companion_instance_4;
  }
  function values_3() {
    return [Runtime_JS_getInstance(), Runtime_JVM_getInstance(), Runtime_ANDROID_getInstance(), Runtime_NATIVE_getInstance(), Runtime_WASM_getInstance()];
  }
  function valueOf_3(value) {
    switch (value) {
      case 'JS':
        return Runtime_JS_getInstance();
      case 'JVM':
        return Runtime_JVM_getInstance();
      case 'ANDROID':
        return Runtime_ANDROID_getInstance();
      case 'NATIVE':
        return Runtime_NATIVE_getInstance();
      case 'WASM':
        return Runtime_WASM_getInstance();
      default:
        Runtime_initEntries();
        THROW_IAE('No enum constant value.');
        break;
    }
  }
  function get_entries_3() {
    if ($ENTRIES_3 == null)
      $ENTRIES_3 = enumEntries(values_3());
    return $ENTRIES_3;
  }
  var Runtime_entriesInitialized;
  function Runtime_initEntries() {
    if (Runtime_entriesInitialized)
      return Unit_getInstance();
    Runtime_entriesInitialized = true;
    Runtime_JS_instance = new Runtime('JS', 0);
    Runtime_JVM_instance = new Runtime('JVM', 1);
    Runtime_ANDROID_instance = new Runtime('ANDROID', 2);
    Runtime_NATIVE_instance = new Runtime('NATIVE', 3);
    Runtime_WASM_instance = new Runtime('WASM', 4);
    Companion_getInstance_4();
  }
  var $ENTRIES_3;
  function Runtime(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  protoOf(Runtime).get_isJs_woo47w_k$ = function () {
    return this.equals(Runtime_JS_getInstance());
  };
  protoOf(Runtime).get_isJvm_it6vam_k$ = function () {
    return this.equals(Runtime_JVM_getInstance());
  };
  protoOf(Runtime).get_isAndroid_fbign8_k$ = function () {
    return this.equals(Runtime_ANDROID_getInstance());
  };
  protoOf(Runtime).get_isNative_wyrxka_k$ = function () {
    return this.equals(Runtime_NATIVE_getInstance());
  };
  protoOf(Runtime).get_isJvmOrAndroid_muq472_k$ = function () {
    return this.get_isJvm_it6vam_k$() ? true : this.get_isAndroid_fbign8_k$();
  };
  protoOf(Runtime).get_isWasm_ew8dtj_k$ = function () {
    return this.equals(Runtime_WASM_getInstance());
  };
  function Runtime_JS_getInstance() {
    Runtime_initEntries();
    return Runtime_JS_instance;
  }
  function Runtime_JVM_getInstance() {
    Runtime_initEntries();
    return Runtime_JVM_instance;
  }
  function Runtime_ANDROID_getInstance() {
    Runtime_initEntries();
    return Runtime_ANDROID_instance;
  }
  function Runtime_NATIVE_getInstance() {
    Runtime_initEntries();
    return Runtime_NATIVE_instance;
  }
  function Runtime_WASM_getInstance() {
    Runtime_initEntries();
    return Runtime_WASM_instance;
  }
  function get_isDenoJs() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.getValue' call
    var this_0 = isDenoJs$delegate;
    isDenoJs$factory();
    return this_0.get_value_j01efc_k$();
  }
  var isDenoJs$delegate;
  function get_isWeb() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.getValue' call
    var this_0 = isWeb$delegate;
    isWeb$factory();
    return this_0.get_value_j01efc_k$();
  }
  var isWeb$delegate;
  function get_isWorker() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.getValue' call
    var this_0 = isWorker$delegate;
    isWorker$factory();
    return this_0.get_value_j01efc_k$();
  }
  var isWorker$delegate;
  function get_isNodeJs() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.getValue' call
    var this_0 = isNodeJs$delegate;
    isNodeJs$factory();
    return this_0.get_value_j01efc_k$();
  }
  var isNodeJs$delegate;
  function get_currentArch() {
    _init_properties_Current_js_kt__w11d1e();
    return currentArch;
  }
  var currentArch;
  function get_currentRuntime() {
    _init_properties_Current_js_kt__w11d1e();
    return currentRuntime;
  }
  var currentRuntime;
  function get_currentIsDebug() {
    _init_properties_Current_js_kt__w11d1e();
    return currentIsDebug;
  }
  var currentIsDebug;
  function get_currentIsLittleEndian() {
    _init_properties_Current_js_kt__w11d1e();
    return currentIsLittleEndian;
  }
  var currentIsLittleEndian;
  function get_multithreadedSharedHeap() {
    _init_properties_Current_js_kt__w11d1e();
    return multithreadedSharedHeap;
  }
  var multithreadedSharedHeap;
  function get_currentRawPlatformName() {
    _init_properties_Current_js_kt__w11d1e();
    return currentRawPlatformName;
  }
  var currentRawPlatformName;
  function get_currentRawOsName() {
    _init_properties_Current_js_kt__w11d1e();
    return currentRawOsName;
  }
  var currentRawOsName;
  function get_isShell() {
    _init_properties_Current_js_kt__w11d1e();
    return (!get_isWeb() ? !get_isNodeJs() : false) ? !get_isWorker() : false;
  }
  function get_process() {
    _init_properties_Current_js_kt__w11d1e();
    if (typeof process === 'undefined') {
      try {
        // Inline function 'kotlin.error' call
        var message = "Not in NodeJS. Can't access process";
        throw IllegalStateException_init_$Create$(toString(message));
      } catch ($p) {
        if ($p instanceof Error) {
          var e = $p;
          printStackTrace(e);
          throw e;
        } else {
          throw $p;
        }
      }
    }
    return process;
  }
  function get_currentOs() {
    _init_properties_Current_js_kt__w11d1e();
    var tmp;
    if (get_isDenoJs()) {
      var tmp0_subject = Deno.build.os;
      tmp = tmp0_subject == 'darwin' ? Os_MACOSX_getInstance() : tmp0_subject == 'linux' ? Os_LINUX_getInstance() : tmp0_subject == 'windows' ? Os_WINDOWS_getInstance() : Os_UNKNOWN_getInstance();
    } else {
      tmp = Os_UNKNOWN_getInstance();
    }
    return tmp;
  }
  function isDenoJs$delegate$lambda() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.js.unsafeCast' call
    return typeof Deno === 'object' && Deno.statSync;
  }
  function isWeb$delegate$lambda() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.js.unsafeCast' call
    return typeof window === 'object';
  }
  function isWorker$delegate$lambda() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.js.unsafeCast' call
    return typeof importScripts === 'function';
  }
  function isNodeJs$delegate$lambda() {
    _init_properties_Current_js_kt__w11d1e();
    // Inline function 'kotlin.js.unsafeCast' call
    return typeof process !== 'undefined' && process.release && process.release.name.search(/node|io.js/) !== -1;
  }
  function isDenoJs$factory() {
    return getPropertyCallableRef('isDenoJs', 0, KProperty0, function () {
      return get_isDenoJs();
    }, null);
  }
  function isWeb$factory() {
    return getPropertyCallableRef('isWeb', 0, KProperty0, function () {
      return get_isWeb();
    }, null);
  }
  function isWorker$factory() {
    return getPropertyCallableRef('isWorker', 0, KProperty0, function () {
      return get_isWorker();
    }, null);
  }
  function isNodeJs$factory() {
    return getPropertyCallableRef('isNodeJs', 0, KProperty0, function () {
      return get_isNodeJs();
    }, null);
  }
  var properties_initialized_Current_js_kt_4h6d9c;
  function _init_properties_Current_js_kt__w11d1e() {
    if (!properties_initialized_Current_js_kt_4h6d9c) {
      properties_initialized_Current_js_kt_4h6d9c = true;
      isDenoJs$delegate = lazy(isDenoJs$delegate$lambda);
      isWeb$delegate = lazy(isWeb$delegate$lambda);
      isWorker$delegate = lazy(isWorker$delegate$lambda);
      isNodeJs$delegate = lazy(isNodeJs$delegate$lambda);
      currentArch = Arch_UNKNOWN_getInstance();
      currentRuntime = Runtime_JS_getInstance();
      currentIsDebug = false;
      // Inline function 'org.khronos.webgl.get' call
      // Inline function 'kotlin.arrayOf' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp$ret$2 = [287454020];
      // Inline function 'kotlin.js.asDynamic' call
      currentIsLittleEndian = (new Uint8Array((new Uint32Array(tmp$ret$2)).buffer))[0] === 68;
      multithreadedSharedHeap = false;
      currentRawPlatformName = get_isDenoJs() ? 'js-deno' : get_isWeb() ? 'js-web' : get_isNodeJs() ? 'js-node' : get_isWorker() ? 'js-worker' : get_isShell() ? 'js-shell' : 'js';
      var tmp;
      if (get_isDenoJs()) {
        tmp = 'deno';
      } else if (get_isWeb() ? true : get_isWorker()) {
        // Inline function 'kotlin.js.unsafeCast' call
        tmp = navigator.userAgent;
      } else {
        // Inline function 'kotlin.js.unsafeCast' call
        tmp = get_process().platform;
      }
      currentRawOsName = tmp;
    }
  }
  //region block: post-declaration
  protoOf(Companion_3).get_isWindows_l8sguo_k$ = get_isWindows;
  protoOf(Companion_3).get_isUnix_ew7d97_k$ = get_isUnix;
  protoOf(Companion_3).get_isPosix_zgxy8g_k$ = get_isPosix;
  protoOf(Companion_3).get_isLinux_zemuip_k$ = get_isLinux;
  protoOf(Companion_3).get_isMac_it6x0c_k$ = get_isMac;
  protoOf(Companion_3).get_isApple_z8pm5j_k$ = get_isApple;
  protoOf(Companion_3).get_isAppleMobile_3a14o9_k$ = get_isAppleMobile;
  protoOf(Companion_3).get_isIos_it6ue2_k$ = get_isIos;
  protoOf(Companion_3).get_isAndroid_fbign8_k$ = get_isAndroid;
  protoOf(Companion_3).get_isTvos_ew6wc9_k$ = get_isTvos;
  protoOf(Companion_3).get_isJs_woo47w_k$ = get_isJs;
  protoOf(Companion_3).get_isNative_wyrxka_k$ = get_isNative;
  protoOf(Companion_3).get_isNativeDesktop_ehqq6q_k$ = get_isNativeDesktop;
  protoOf(Companion_3).get_isJvm_it6vam_k$ = get_isJvm;
  protoOf(Companion_3).get_isWasm_ew8dtj_k$ = get_isWasm;
  protoOf(Companion_3).get_isJsOrWasm_a0g2u5_k$ = get_isJsOrWasm;
  protoOf(Companion_3).get_isJsShell_n6gzws_k$ = get_isJsShell;
  protoOf(Companion_3).get_isJsNodeJs_aiwq6x_k$ = get_isJsNodeJs;
  protoOf(Companion_3).get_isJsDenoJs_feoas9_k$ = get_isJsDenoJs;
  protoOf(Companion_3).get_isJsBrowser_3lmn6c_k$ = get_isJsBrowser;
  protoOf(Companion_3).get_isJsWorker_6991qe_k$ = get_isJsWorker;
  protoOf(Companion_3).get_isJsBrowserOrWorker_a7u7bh_k$ = get_isJsBrowserOrWorker;
  protoOf(Impl).get_isLittleEndian_ny1aws_k$ = get_isLittleEndian;
  protoOf(Impl).get_isBigEndian_s607nc_k$ = get_isBigEndian;
  protoOf(Impl).get_isDebug_za5oww_k$ = get_isDebug;
  protoOf(Impl).get_isRelease_gyndg4_k$ = get_isRelease;
  protoOf(Impl).get_isWindows_l8sguo_k$ = get_isWindows;
  protoOf(Impl).get_isUnix_ew7d97_k$ = get_isUnix;
  protoOf(Impl).get_isPosix_zgxy8g_k$ = get_isPosix;
  protoOf(Impl).get_isLinux_zemuip_k$ = get_isLinux;
  protoOf(Impl).get_isMac_it6x0c_k$ = get_isMac;
  protoOf(Impl).get_isApple_z8pm5j_k$ = get_isApple;
  protoOf(Impl).get_isAppleMobile_3a14o9_k$ = get_isAppleMobile;
  protoOf(Impl).get_isIos_it6ue2_k$ = get_isIos;
  protoOf(Impl).get_isAndroid_fbign8_k$ = get_isAndroid;
  protoOf(Impl).get_isTvos_ew6wc9_k$ = get_isTvos;
  protoOf(Impl).get_isJs_woo47w_k$ = get_isJs;
  protoOf(Impl).get_isNative_wyrxka_k$ = get_isNative;
  protoOf(Impl).get_isNativeDesktop_ehqq6q_k$ = get_isNativeDesktop;
  protoOf(Impl).get_isJvm_it6vam_k$ = get_isJvm;
  protoOf(Impl).get_isWasm_ew8dtj_k$ = get_isWasm;
  protoOf(Impl).get_isJsOrWasm_a0g2u5_k$ = get_isJsOrWasm;
  protoOf(Impl).get_isJsShell_n6gzws_k$ = get_isJsShell;
  protoOf(Impl).get_isJsNodeJs_aiwq6x_k$ = get_isJsNodeJs;
  protoOf(Impl).get_isJsDenoJs_feoas9_k$ = get_isJsDenoJs;
  protoOf(Impl).get_isJsBrowser_3lmn6c_k$ = get_isJsBrowser;
  protoOf(Impl).get_isJsWorker_6991qe_k$ = get_isJsWorker;
  protoOf(Impl).get_isJsBrowserOrWorker_a7u7bh_k$ = get_isJsBrowserOrWorker;
  //endregion
  //region block: exports
  _.$_$ = _.$_$ || {};
  _.$_$.a = Companion_getInstance_3;
  //endregion
  return _;
}));
