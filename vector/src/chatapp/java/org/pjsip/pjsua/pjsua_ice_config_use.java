/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua;

public enum pjsua_ice_config_use {
  PJSUA_ICE_CONFIG_USE_DEFAULT,
  PJSUA_ICE_CONFIG_USE_CUSTOM;

  public final int swigValue() {
    return swigValue;
  }

  public static pjsua_ice_config_use swigToEnum(int swigValue) {
    pjsua_ice_config_use[] swigValues = pjsua_ice_config_use.class.getEnumConstants();
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (pjsua_ice_config_use swigEnum : swigValues)
      if (swigEnum.swigValue == swigValue)
        return swigEnum;
    throw new IllegalArgumentException("No enum " + pjsua_ice_config_use.class + " with value " + swigValue);
  }

  @SuppressWarnings("unused")
  private pjsua_ice_config_use() {
    this.swigValue = SwigNext.next++;
  }

  @SuppressWarnings("unused")
  private pjsua_ice_config_use(int swigValue) {
    this.swigValue = swigValue;
    SwigNext.next = swigValue+1;
  }

  @SuppressWarnings("unused")
  private pjsua_ice_config_use(pjsua_ice_config_use swigEnum) {
    this.swigValue = swigEnum.swigValue;
    SwigNext.next = this.swigValue+1;
  }

  private final int swigValue;

  private static class SwigNext {
    private static int next = 0;
  }
}

