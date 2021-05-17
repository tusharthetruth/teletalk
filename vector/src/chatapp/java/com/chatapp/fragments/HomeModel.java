package com.chatapp.fragments;

import com.chatapp.C;

import java.util.ArrayList;

import im.vector.R;
import im.vector.VectorApp;

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
                        R.drawable.status_button,
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
                        R.drawable.meeting,
                        R.drawable.update_profile,
                        R.drawable.qr_code,
                        R.drawable.ticketing,
                        R.drawable.courier,
                        R.drawable.wills_smart_education,
                        R.drawable.wills_smart_medical,
                        R.drawable.law_enforcement,
                        R.drawable.agro_farming,
                        R.drawable.city_guide,
                        R.drawable.my_number,
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
                "#4c98cf",
                "#2481c5",
                "#4873a6",
                "#5A5485",
                "#524365",
                "#3398dc", "#39a8f0",
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
                C.Companion.getMeeting(),
                C.Companion.getUpdateProfile(),
                C.Companion.getQr(),
                C.Companion.getTicketing(),
                C.Companion.getCourier(),
                C.Companion.getWillEducation(),
                C.Companion.getMedical(),
                C.Companion.getLaw(),
                C.Companion.getSmartAgro(),
                C.Companion.getSmartCityGuide(),
                C.Companion.getMyNumber(),
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

    public static ArrayList<HomeModel> getFirstHomeList() {
        ArrayList<HomeModel> list = new ArrayList<>();

        Integer[] iconList1 = {
                R.drawable.status,
                R.drawable.invite_friends,
                R.drawable.settings,
                R.drawable.mybalance,
                R.drawable.buy_credit,
                R.drawable.voucher,
                R.drawable.mobile_topup,
                R.drawable.transfer_credit,
                R.drawable.transfer_history,
        };
        Integer[] iconList = {
                R.drawable.status,
                R.drawable.invite_friends,
                R.drawable.my_number,
                R.drawable.settings,
                R.drawable.mybalance,
                R.drawable.buy_credit,
                R.drawable.voucher,
                R.drawable.mobile_topup,
                R.drawable.transfer_credit,
        };
        String[] colorList = {
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
        };

        String[] titles = {
                C.Companion.getStatus(),
                C.Companion.getInviteFriends(),
                C.Companion.getMyNumber(),
                C.Companion.getSettings(),
                C.Companion.getMyBalance(),
                C.Companion.getBuyCredit(),
                C.Companion.getVoucherRecharge(),
                C.Companion.getMobileTopup(),
                C.Companion.getMobileTransfer(),
//                C.Companion.getTOPB(),
                C.Companion.getInviteFriends(),
                C.Companion.getInviteFriends(),
                C.Companion.getInviteFriends(),
                C.Companion.getInviteFriends(),
//                C.Companion.getVT(),
                C.Companion.getInviteFriends(),
                C.Companion.getInviteFriends(),
                C.Companion.getInviteFriends()
        };
        for (int i = 0; i < iconList.length; i++) {
            HomeModel model = new HomeModel();
            model.colorCode = colorList[i];
            model.name = titles[i];
            model.icon = iconList[i];
            list.add(model);

        }
        return list;

    }

    public static ArrayList<HomeModel> getSecondHomeList() {
        ArrayList<HomeModel> list = new ArrayList<>();
        Integer[] iconList = {
                R.drawable.e_meeting,
                R.drawable.profile,
                R.drawable.qr,
                R.drawable.book_ticket,
                R.drawable.courier,
                R.drawable.education,
                R.drawable.medical,
                R.drawable.law,
                R.drawable.farming,
//                R.drawable.city_guide,
//                R.drawable.my_number,
//                R.drawable.why_wills,
//                R.drawable.logout
                };
        String[] colorList = {
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
        };

        String[] titles = {
                C.Companion.getMeeting(),
                C.Companion.getUpdateProfile(),
                C.Companion.getQr(),
                C.Companion.getTicketing(),
                C.Companion.getCourier(),
                C.Companion.getWillEducation(),
                C.Companion.getMedical(),
                C.Companion.getLaw(),
                C.Companion.getSmartAgro(),
//                C.Companion.getSmartCityGuide(),
//                C.Companion.getMyNumber(),
//                C.Companion.getWhyWill(),
//                C.Companion.getLogout()
        };
        for (int i = 0; i < iconList.length; i++) {
            HomeModel model = new HomeModel();
            model.colorCode = colorList[i];
            model.name = titles[i];
            model.icon = iconList[i];
            list.add(model);

        }
        return list;

    }
    public static ArrayList<HomeModel> getThirdHomeList() {
        ArrayList<HomeModel> list = new ArrayList<>();
        Integer[] iconList = {
//                R.drawable.meeting,
//                R.drawable.update_profile,
//                R.drawable.qr_code,
//                R.drawable.ticketing,
//                R.drawable.courier,
//                R.drawable.wills_smart_education,
//                R.drawable.wills_smart_medical,
//                R.drawable.law_enforcement,
//                R.drawable.agro_farming,
                R.drawable.city,
                R.drawable.my_number,
                R.drawable.why,
                R.drawable.logout};
        String[] colorList = {
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
        };

        String[] titles = {
//                C.Companion.getMeeting(),
//                C.Companion.getUpdateProfile(),
//                C.Companion.getQr(),
//                C.Companion.getTicketing(),
//                C.Companion.getCourier(),
//                C.Companion.getWillEducation(),
//                C.Companion.getMedical(),
//                C.Companion.getLaw(),
//                C.Companion.getSmartAgro(),
                C.Companion.getSmartCityGuide(),
                C.Companion.getMyNumber(),
                C.Companion.getWhyWill(),
                C.Companion.getLogout()
        };
        for (int i = 0; i < iconList.length; i++) {
            HomeModel model = new HomeModel();
            model.colorCode = colorList[i];
            model.name = titles[i];
            model.icon = iconList[i];
            list.add(model);

        }
        return list;

    }

//    Transfer History - web
//    Data Bundles Topup - web
//    Electricity Bills Payment - web
//    Television Bills Payment- web
//    Update profile - app
//    Why Wills Smart VoIP- app
    public static ArrayList<HomeModel> getSecHomeList() {
        ArrayList<HomeModel> list = new ArrayList<>();
        Integer[] iconList1 = {
                R.drawable.transfer_history,
                R.drawable.data_bundle,
                R.drawable.electricity,
                R.drawable.tv_recharge,
                R.drawable.transfer_funds,
                R.drawable.sms,
                R.drawable.my_number,
                R.drawable.profile,
                R.drawable.why};
        Integer[] iconList = {
                R.drawable.transfer_history,
                R.drawable.data_bundle,
                R.drawable.electricity,
                R.drawable.tv_recharge,
//                R.drawable.transfer_funds,
//                R.drawable.sms,
//                R.drawable.my_number,
                R.drawable.profile,
                R.drawable.why};
        String[] colorList = {
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.first_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.second_color),
                VectorApp.getInstance().getString(R.string.first_color),
        };

        String[] titles = {
                C.Companion.getTrnasferHistory(),
                C.Companion.getDataBundle(),
                C.Companion.getElectric(),
                C.Companion.getTV(),
//                C.Companion.getMONEYTRANSFER(),
//                C.Companion.getSMS(),
//                C.Companion.getMyNumber(),
                C.Companion.getUpdateProfile(),
                C.Companion.getWhyWill()
        };
        for (int i = 0; i < iconList.length; i++) {
            HomeModel model = new HomeModel();
            model.colorCode = colorList[i];
            model.name = titles[i];
            model.icon = iconList[i];
            list.add(model);

        }
        return list;

    }
}
