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

import android.view.View
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import butterknife.BindView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import im.vector.R
import im.vector.ui.epoxy.BaseEpoxyHolder
import im.vector.ui.themes.ThemeUtils
import im.vector.ui.util.setTextOrHide


@EpoxyModelClass(layout = R.layout.item_settings_button_single_line)
abstract class SettingsTextButtonItem : EpoxyModelWithHolder<SettingsTextButtonItem.Holder>() {

    enum class ButtonStyle {
        POSITIVE,
        DESTRUCTIVE
    }

    enum class ButtonType {
        NORMAL,
        SWITCH
    }

    @EpoxyAttribute
    var title: String? = null

    @EpoxyAttribute
    @StringRes
    var titleResId: Int? = null

    @EpoxyAttribute
    var buttonTitle: String? = null

    @EpoxyAttribute
    @StringRes
    var buttonTitleId: Int? = null

    @EpoxyAttribute
    var buttonStyle: ButtonStyle = ButtonStyle.POSITIVE


    @EpoxyAttribute
    var buttonType: ButtonType = ButtonType.NORMAL

    @EpoxyAttribute
    var buttonIndeterminate: Boolean = false

    @EpoxyAttribute
    var checked: Boolean? = null

    @EpoxyAttribute
    var buttonClickListener: View.OnClickListener? = null

    @EpoxyAttribute
    var switchChangeListener: CompoundButton.OnCheckedChangeListener? = null

    @EpoxyAttribute
    var infoMessage: String? = null

    @EpoxyAttribute
    @StringRes
    var infoMessageId: Int? = null

    @EpoxyAttribute
    @ColorRes
    var infoMessageTintColorId: Int = R.color.vector_error_color


    override fun bind(holder: Holder) {
        super.bind(holder)

        if (titleResId != null) {
            holder.textView.setText(titleResId!!)
        } else {
            holder.textView.setTextOrHide(title, hideWhenBlank = false)
        }

        if (buttonTitleId != null) {
            holder.button.setText(buttonTitleId!!)
        } else {
            holder.button.setTextOrHide(buttonTitle)
        }

        if (buttonIndeterminate) {
            holder.spinner.isVisible = true
            holder.button.isInvisible = true
            holder.switchButton.isInvisible = true
            holder.switchButton.setOnCheckedChangeListener(null)
            holder.button.setOnClickListener(null)
        } else {
            holder.spinner.isVisible = false
            when (buttonType) {
                ButtonType.NORMAL -> {
                    holder.button.isVisible = true
                    holder.switchButton.isVisible = false
                    when (buttonStyle) {
                        ButtonStyle.POSITIVE    -> {
                            holder.button.setTextColor(ThemeUtils.getColor(holder.main.context, R.attr.colorAccent))
                        }
                        ButtonStyle.DESTRUCTIVE -> {
                            holder.button.setTextColor(ContextCompat.getColor(holder.main.context, R.color.vector_error_color))
                        }
                    }
                    holder.button.setOnClickListener(buttonClickListener)
                }
                ButtonType.SWITCH -> {
                    holder.button.isVisible = false
                    holder.switchButton.isVisible = true
                    //set to null before changing the state
                    holder.switchButton.setOnCheckedChangeListener(null)
                    checked?.let { holder.switchButton.isChecked = it }
                    holder.switchButton.setOnCheckedChangeListener(switchChangeListener)
                }
            }
        }


        val errorMessage = infoMessageId?.let { holder.main.context.getString(it) } ?: infoMessage
        if (errorMessage != null) {
            holder.errorTextView.isVisible = true
            holder.errorTextView.setTextOrHide(errorMessage)
            val errorColor = ContextCompat.getColor(holder.main.context, infoMessageTintColorId)
            ContextCompat.getDrawable(holder.main.context, R.drawable.ic_notification_privacy_warning)?.apply {
                ThemeUtils.tintDrawableWithColor(this, errorColor)
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(this, null, null, null)
            }
            holder.errorTextView.setTextColor(errorColor)
        } else {
            holder.errorTextView.isVisible = false
            holder.errorTextView.text = null
            holder.textView.setCompoundDrawables(null, null, null, null)
        }

    }

    class Holder : BaseEpoxyHolder() {

        @BindView(R.id.settings_item_text)
        lateinit var textView: TextView

        @BindView(R.id.settings_item_button)
        lateinit var button: Button

        @BindView(R.id.settings_item_switch)
        lateinit var switchButton: Switch

        @BindView(R.id.settings_item_button_spinner)
        lateinit var spinner: ProgressBar

        @BindView(R.id.settings_item_error_message)
        lateinit var errorTextView: TextView
    }
}