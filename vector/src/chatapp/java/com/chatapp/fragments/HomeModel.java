package com.chatapp.fragments;

import com.chatapp.C;

import java.util.ArrayList;

import im.vector.R;

public class HomeModel {

    String name;
    String colorCode;
    int icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public static ArrayList<HomeModel> getHomeList() {
        ArrayList<HomeModel> list = new ArrayList<>();
        Integer[] iconList =
                {
                        R.drawable.invite_friends,
                        R.drawable.invite_friends,
                        R.drawable.settings,
                        R.drawable.my_balance,
                        R.drawable.buy_credit,
                        R.drawable.voucher_recharge,
                        R.drawable.mobile_topup,
                        R.drawable.balance_transfer,
                        R.drawable.transfer_history,
//                        R.drawable.contacts_backup,
//                        R.drawable.tracking,
//                        R.drawable.did_icon,
                        R.drawable.update_profile,
                        R.drawable.qr_code,
                        R.drawable.ticketing,
                        R.drawable.courier,
                        R.drawable.wills_smart_education,
                        R.drawable.wills_smart_medical,
                        R.drawable.law_enforcement,
                        R.drawable.agro_farming,
                        R.drawable.city_guide,
                        R.drawable.why_wills,
                        R.drawable.logout
                };

        String[] colorList = {
                "#3398dc",
                "#39a8f0",
                "#268bcf",
                "#1c81c5",
                "#0b73ba",
                "#0064a8",
                "#0064a8",
                "#00579a",
                "#005095",
//                "#40c9c4",
//                "#10a19c",
//                "#4c98cf",
                "#2481c5",
                "#4873a6",
                "#5A5485",
                "#524365",
                "#3398dc"
        };

        String[] titles = {
                C.Companion.getStatus(),
                C.Companion.getInviteFriends(),
                C.Companion.getSettings(),
                C.Companion.getMyBalance(),
                C.Companion.getBuyCredit(),
                C.Companion.getVoucherRecharge(),
                C.Companion.getMobileTopup(),
                C.Companion.getMobileTransfer(),
                C.Companion.getTrnasferHistory(),
//                C.Companion.getContactBackup(),
//                C.Companion.getTracking(),
//                C.Companion.getDid(),
                C.Companion.getUpdateProfile(),
                C.Companion.getQr(),
                C.Companion.getTicketing(),
                C.Companion.getCourier(),
                C.Companion.getWillEducation(),
                C.Companion.getMedical(),
                C.Companion.getLaw(),
                C.Companion.getSmartAgro(),
                C.Companion.getSmartCityGuide(),
                C.Companion.getWhyWill(),
                C.Companion.getLogout()
        };
        for (int i = 0; i < iconList.length; i++) {
            HomeModel model = new HomeModel();
            model.colorCode = colorList[0];
            model.name = titles[i];
            model.icon = iconList[i];
            list.add(model);

        }
        return list;

    }
}
