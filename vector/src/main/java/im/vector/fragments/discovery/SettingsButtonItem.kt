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
import android.widget.Button
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import butterknife.BindView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import im.vector.R
import im.vector.ui.epoxy.BaseEpoxyHolder
import im.vector.ui.themes.ThemeUtils
import im.vector.ui.util.setTextOrHide


@EpoxyModelClass(layout = R.layout.item_settings_button)
abstract class SettingsButtonItem : EpoxyModelWithHolder<SettingsButtonItem.Holder>() {


    @EpoxyAttribute
    var buttonTitle: String? = null

    @EpoxyAttribute
    @StringRes
    var buttonTitleId: Int? = null

    @EpoxyAttribute
    var buttonStyle: SettingsTextButtonItem.ButtonStyle = SettingsTextButtonItem.ButtonStyle.POSITIVE

    @EpoxyAttribute
    var buttonClickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        if (buttonTitleId != null) {
            holder.button.setText(buttonTitleId!!)
        } else {
            holder.button.setTextOrHide(buttonTitle)
        }

        when (buttonStyle) {
            SettingsTextButtonItem.ButtonStyle.POSITIVE    -> {
                holder.button.setTextColor(ThemeUtils.getColor(holder.main.context, R.attr.colorAccent))
            }
            SettingsTextButtonItem.ButtonStyle.DESTRUCTIVE -> {
                holder.button.setTextColor(ContextCompat.getColor(holder.main.context, R.color.vector_error_color))
            }
        }

        holder.button.setOnClickListener(buttonClickListener)
    }

    class Holder : BaseEpoxyHolder() {
        @BindView(R.id.settings_item_button)
        lateinit var button: Button
    }
}