/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package im.vector.fragments.discovery

import android.content.Context
import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.google.i18n.phonenumbers.PhoneNumberUtil
import im.vector.R


class SettingsDiscoveryController(private val context: Context,
                                  private val interactionListener: InteractionListener) : TypedEpoxyController<DiscoverySettingsState>() {

    override fun buildModels(data: DiscoverySettingsState) {
        when (data.identityServer) {
            is Loading -> {
                settingsLoadingItem {
                    id("identityServerLoading")
                }
            }
            is Fail    -> {
                settingsInfoItem {
                    id("identityServerError")
                    helperText(data.identityServer.error.message)
                }
            }
            is Success -> {
                buildIdentityServerSection(data)

                val hasIdentityServer = data.identityServer().isNullOrBlank().not()

                if (hasIdentityServer) {
                    buildMailSection(data)
                    buildPhoneNumberSection(data)
                }
            }
        }
    }

    private fun buildPhoneNumberSection(data: DiscoverySettingsState) {
        settingsSectionTitle {
            id("msisdn")
            titleResId(R.string.settings_discovery_msisdn_title)
        }


        when (data.phoneNumbersList) {
            is Loading -> {
                settingsLoadingItem {
                    id("phoneLoading")
                }
            }
            is Fail    -> {
                settingsInfoItem {
                    id("msisdnListError")
                    helperText(data.phoneNumbersList.error.message)
                }
            }
            is Success -> {
                val phones = data.phoneNumbersList.invoke()
                if (phones.isEmpty()) {
                    settingsInfoItem {
                        id("no_msisdn")
                        helperText(context.getString(R.string.settings_discovery_no_msisdn))
                    }
                } else {
                    phones.forEach { piState ->
                        val phoneNumber = PhoneNumberUtil.getInstance()
                                .parse("+${piState.value}", null)
                                ?.let {
                                    PhoneNumberUtil.getInstance().format(it, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
                                }

                        settingsTextButtonItem {
                            id(piState.value)
                            title(phoneNumber)
                            when {
                                piState.isShared is Loading -> buttonIndeterminate(true)
                                piState.isShared is Fail    -> {
                                    buttonType(SettingsTextButtonItem.ButtonType.NORMAL)
                                    buttonStyle(SettingsTextButtonItem.ButtonStyle.DESTRUCTIVE)
                                    buttonTitle(context.getString(R.string.global_retry))
                                    infoMessage(piState.isShared.error.message)
                                    buttonClickListener(View.OnClickListener {
                                        interactionListener.onTapRetryToRetrieveBindings()
                                    })
                                }
                                piState.isShared is Success -> when (piState.isShared()) {
                                    PidInfo.SharedState.SHARED,
                                    PidInfo.SharedState.NOT_SHARED              -> {
                                        checked(piState.isShared() == PidInfo.SharedState.SHARED)
                                        buttonType(SettingsTextButtonItem.ButtonType.SWITCH)
                                        switchChangeListener { b, checked ->
                                            if (checked) {
                                                interactionListener.onTapShareMsisdn(piState.value)
                                            } else {
                                                interactionListener.onTapRevokeMsisdn(piState.value)
                                            }
                                        }
                                    }
                                    PidInfo.SharedState.NOT_VERIFIED_FOR_BIND,
                                    PidInfo.SharedState.NOT_VERIFIED_FOR_UNBIND -> {
                                        buttonType(SettingsTextButtonItem.ButtonType.NORMAL)
                                        buttonTitle("")
                                    }
                                }
                            }
                        }
                        when (piState.isShared()) {
                            PidInfo.SharedState.NOT_VERIFIED_FOR_BIND,
                            PidInfo.SharedState.NOT_VERIFIED_FOR_UNBIND -> {
                                settingsItemText {
                                    id("tverif" + piState.value)
                                    descriptionText(context.getString(R.string.settings_text_message_sent, phoneNumber))
                                    interactionListener(object : SettingsItemText.Listener {
                                        override fun onValidate(code: String) {
                                            val bind = piState.isShared() == PidInfo.SharedState.NOT_VERIFIED_FOR_BIND
                                            interactionListener.checkMsisdnVerification(piState.value, code, bind)
                                        }
                                    })
                                }
                            }
                            else                                        -> {
                            }
                        }
                    }
                }
            }
        }
    }

    private fun buildMailSection(data: DiscoverySettingsState) {
        settingsSectionTitle {
            id("emails")
            titleResId(R.string.settings_discovery_emails_title)
        }
        when (data.emailList) {
            is Loading -> {
                settingsLoadingItem {
                    id("mailLoading")
                }
            }
            is Fail    -> {
                settingsInfoItem {
                    id("mailListError")
                    helperText(data.emailList.error.message)
                }
            }
            is Success -> {
                val emails = data.emailList.invoke()
                if (emails.isEmpty()) {
                    settingsInfoItem {
                        id("no_emails")
                        helperText(context.getString(R.string.settings_discovery_no_mails))
                    }
                } else {
                    emails.forEach { piState ->
                        settingsTextButtonItem {
                            id(piState.value)
                            title(piState.value)
                            when (piState.isShared) {
                                is Loading -> buttonIndeterminate(true)
                                is Fail    -> {
                                    buttonType(SettingsTextButtonItem.ButtonType.NORMAL)
                                    buttonStyle(SettingsTextButtonItem.ButtonStyle.DESTRUCTIVE)
                                    buttonTitle(context.getString(R.string.global_retry))
                                    infoMessage(piState.isShared.error.message)
                                    buttonClickListener(View.OnClickListener {
                                        interactionListener.onTapRetryToRetrieveBindings()
                                    })
                                }
                                is Success -> when (piState.isShared()) {
                                    PidInfo.SharedState.SHARED,
                                    PidInfo.SharedState.NOT_SHARED              -> {
                                        checked(piState.isShared() == PidInfo.SharedState.SHARED)
                                        buttonType(SettingsTextButtonItem.ButtonType.SWITCH)
                                        switchChangeListener { b, checked ->
                                            if (checked) {
                                                interactionListener.onTapShareEmail(piState.value)
                                            } else {
                                                interactionListener.onTapRevokeEmail(piState.value)
                                            }
                                        }
                                    }
                                    PidInfo.SharedState.NOT_VERIFIED_FOR_BIND,
                                    PidInfo.SharedState.NOT_VERIFIED_FOR_UNBIND -> {
                                        buttonType(SettingsTextButtonItem.ButtonType.NORMAL)
                                        buttonTitleId(R.string._continue)
                                        infoMessageTintColorId(R.color.vector_info_color)
                                        infoMessage(context.getString(R.string.settings_discovery_confirm_mail, piState.value))
                                        buttonClickListener(View.OnClickListener {
                                            val bind = piState.isShared() == PidInfo.SharedState.NOT_VERIFIED_FOR_BIND
                                            interactionListener.checkEmailVerification(piState.value, bind)
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun buildIdentityServerSection(data: DiscoverySettingsState) {
        val identityServer = data.identityServer() ?: context.getString(R.string.none)

        settingsSectionTitle {
            id("idsTitle")
            titleResId(R.string.identity_server)
        }

        settingsItem {
            id("idServer")
            description(identityServer)
        }

        settingsInfoItem {
            id("idServerFooter")
            if (data.termsNotSigned) {
                helperText(context.getString(R.string.settings_agree_to_terms, identityServer))
                showCompoundDrawable(true)
                itemClickListener(View.OnClickListener { interactionListener.onSelectIdentityServer() })
            } else {
                showCompoundDrawable(false)
                if (data.identityServer() != null) {
                    helperText(context.getString(R.string.settings_discovery_identity_server_info, identityServer))
                } else {
                    helperTextResId(R.string.settings_discovery_identity_server_info_none)
                }
            }
        }

        settingsButtonItem {
            id("change")
            if (data.identityServer() != null) {
                buttonTitleId(R.string.change_identity_server)
            } else {
                buttonTitleId(R.string.add_identity_server)
            }
            buttonStyle(SettingsTextButtonItem.ButtonStyle.POSITIVE)
            buttonClickListener(View.OnClickListener {
                interactionListener.onTapChangeIdentityServer()
            })
        }

        if (data.identityServer() != null) {
            settingsInfoItem {
                id("removeInfo")
                helperTextResId(R.string.settings_discovery_disconnect_identity_server_info)
            }
            settingsButtonItem {
                id("remove")
                buttonTitleId(R.string.disconnect_identity_server)
                buttonStyle(SettingsTextButtonItem.ButtonStyle.DESTRUCTIVE)
                buttonClickListener(View.OnClickListener {
                    interactionListener.onTapDisconnectIdentityServer()
                })
            }
        }
    }


    interface InteractionListener {
        fun onSelectIdentityServer()
        fun onTapRevokeEmail(email: String)
        fun onTapShareEmail(email: String)
        fun checkEmailVerification(email: String, bind: Boolean)
        fun checkMsisdnVerification(msisdn: String, code: String, bind: Boolean)
        fun onTapRevokeMsisdn(msisdn: String)
        fun onTapShareMsisdn(msisdn: String)
        fun onTapChangeIdentityServer()
        fun onTapDisconnectIdentityServer()
        fun onTapRetryToRetrieveBindings()
    }
}

