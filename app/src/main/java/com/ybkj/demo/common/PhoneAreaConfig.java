package com.ybkj.demo.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  手机号-国际区号配置
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class PhoneAreaConfig {

    /**
     * 手机号-国际区号-初始化值
     */
    public static final Map<String, String> phoneAreaMap = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("+86", "中国");
            put("+1", "美国/加拿大");
            put("+886", "中国台湾");
            put("+853", "中国澳门");
            put("+852", "中国香港");
            put("+971", "阿拉伯联合酋长国");
            put("+93", "阿富汗");
            put("+1268", "安提瓜和巴布达");
            put("+1264", "安圭拉");
            put("+355", "阿尔巴尼亚");
            put("+374", "亚美尼亚");
            put("+244", "安哥拉");
            put("+54", "阿根廷");
            put("+1684", "美属萨摩亚");
            put("+43", "奥地利");
            put("+61", "澳大利亚");
            put("+297", "阿鲁巴");
            put("+994", "阿塞拜疆");
            put("+387", "波斯尼亚和黑塞哥维那");
            put("+1246", "巴巴多斯");
            put("+880", "孟加拉国");
            put("+32", "比利时");
            put("+226", "布基纳法索");
            put("+359", "保加利亚");
            put("+973", "巴林");
            put("+257", "布隆迪");
            put("+229", "贝宁");
            put("+1441", "百慕大群岛");
            put("+673", "文莱");
            put("+591", "玻利维亚");
            put("+599", "荷兰加勒比");
            put("+55", "巴西");
            put("+1242", "巴哈马");
            put("+975", "不丹");
            put("+267", "博茨瓦纳");
            put("+375", "白俄罗斯");
            put("+501", "伯利兹");
            put("+243", "刚果民主共和国");
            put("+236", "中非共和国");
            put("+242", "刚果共和国");
            put("+41", "瑞士");
            put("+225", "象牙海岸");
            put("+682", "库克群岛");
            put("+56", "智利");
            put("+237", "喀麦隆");
            put("+57", "哥伦比亚");
            put("+506", "哥斯达黎加");
            put("+53", "古巴");
            put("+238", "开普");
            put("+599", "库拉索");
            put("+357", "塞浦路斯");
            put("+420", "捷克");
            put("+49", "德国");
            put("+253", "吉布提");
            put("+45", "丹麦");
            put("+1767", "多米尼加");
            put("+1809", "多米尼加共和国");
            put("+213", "阿尔及利亚");
            put("+593", "厄瓜多尔");
            put("+372", "爱沙尼亚");
            put("+20", "埃及");
            put("+291", "厄立特里亚");
            put("+34", "西班牙");
            put("+251", "埃塞俄比亚");
            put("+358", "芬兰");
            put("+679", "斐济");
            put("+691", "密克罗尼西亚");
            put("+298", "法罗群岛");
            put("+33", "法国");
            put("+241", "加蓬");
            put("+44", "英国");
            put("+1473", "格林纳达");
            put("+995", "格鲁吉亚");
            put("+594", "法属圭亚那");
            put("+233", "加纳");
            put("+350", "直布罗陀");
            put("+299", "格陵兰岛");
            put("+220", "冈比亚");
            put("+224", "几内亚");
            put("+590", "瓜德罗普岛");
            put("+240", "赤道几内亚");
            put("+30", "希腊");
            put("+502", "瓜地马拉");
            put("+1671", "关岛");
            put("+245", "几内亚比绍共和国");
            put("+592", "圭亚那");
            put("+504", "洪都拉斯");
            put("+385", "克罗地亚");
            put("+509", "海地");
            put("+36", "匈牙利");
            put("+62", "印度尼西亚");
            put("+353", "爱尔兰");
            put("+972", "以色列");
            put("+91", "印度");
            put("+964", "伊拉克");
            put("+98", "伊朗");
            put("+354", "冰岛");
            put("+39", "意大利");
            put("+1876", "牙买加");
            put("+962", "约旦");
            put("+81", "日本");
            put("+254", "肯尼亚");
            put("+996", "吉尔吉斯斯坦");
            put("+855", "柬埔寨");
            put("+686", "基里巴斯");
            put("+269", "科摩罗");
            put("+1869", "圣基茨和尼维斯");
            put("+850", "朝鲜");
            put("+82", "韩国");
            put("+965", "科威特");
            put("+1345", "开曼群岛");
            put("+7", "哈萨克斯坦");
            put("+856", "老挝");
            put("+961", "黎巴嫩");
            put("+1758", "圣露西亚");
            put("+423", "列支敦士登");
            put("+94", "斯里兰卡");
            put("+231", "利比里亚");
            put("+266", "莱索托");
            put("+370", "立陶宛");
            put("+352", "卢森堡");
            put("+371", "拉脱维亚");
            put("+218", "利比亚");
            put("+212", "摩洛哥");
            put("+377", "摩纳哥");
            put("+373", "摩尔多瓦");
            put("+382", "黑山");
            put("+261", "马达加斯加");
            put("+692", "马绍尔群岛");
            put("+389", "马其顿");
            put("+223", "马里");
            put("+95", "缅甸");
            put("+976", "蒙古");
            put("+222", "毛里塔尼亚");
            put("+1664", "蒙特塞拉特岛");
            put("+356", "马耳他");
            put("+230", "毛里求斯");
            put("+960", "马尔代夫");
            put("+265", "马拉维");
            put("+52", "墨西哥");
            put("+60", "马来西亚");
            put("+258", "莫桑比克");
            put("+264", "纳米比亚");
            put("+687", "新喀里多尼亚");
            put("+227", "尼日尔");
            put("+234", "尼日利亚");
            put("+505", "尼加拉瓜");
            put("+31", "荷兰");
            put("+47", "挪威");
            put("+977", "尼泊尔");
            put("+674", "拿鲁岛");
            put("+64", "新西兰");
            put("+968", "阿曼");
            put("+507", "巴拿马");
            put("+51", "秘鲁");
            put("+689", "法属波利尼西亚");
            put("+675", "巴布亚新几内亚");
            put("+63", "菲律宾");
            put("+92", "巴基斯坦");
            put("+48", "波兰");
            put("+508", "圣彼埃尔和密克隆岛");
            put("+1787", "波多黎各");
            put("+351", "葡萄牙");
            put("+680", "帕劳");
            put("+595", "巴拉圭");
            put("+974", "卡塔尔");
            put("+262", "留尼汪");
            put("+40", "罗马尼亚");
            put("+381", "塞尔维亚");
            put("+7", "俄罗斯");
            put("+250", "卢旺达");
            put("+966", "沙特阿拉伯");
            put("+677", "所罗门群岛");
            put("+248", "塞舌尔");
            put("+249", "苏丹");
            put("+46", "瑞典");
            put("+65", "新加坡");
            put("+386", "斯洛文尼亚");
            put("+421", "斯洛伐克");
            put("+232", "塞拉利昂");
            put("+378", "圣马力诺");
            put("+221", "塞内加尔");
            put("+252", "索马里");
            put("+597", "苏里南");
            put("+239", "圣多美和普林西比");
            put("+503", "萨尔瓦多");
            put("+963", "叙利亚");
            put("+268", "斯威士兰");
            put("+1649", "特克斯和凯科斯群岛");
            put("+235", "乍得");
            put("+228", "多哥");
            put("+66", "泰国");
            put("+992", "塔吉克斯坦");
            put("+670", "东帝汶");
            put("+993", "土库曼斯坦");
            put("+216", "突尼斯");
            put("+676", "汤加");
            put("+90", "土耳其");
            put("+1868", "特立尼达和多巴哥");
            put("+255", "坦桑尼亚");
            put("+380", "乌克兰");
            put("+256", "乌干达");
            put("+598", "乌拉圭");
            put("+998", "乌兹别克斯坦");
            put("+1784", "圣文森特和格林纳丁斯");
            put("+58", "委内瑞拉");
            put("+1284", "英属处女群岛");
            put("+84", "越南");
            put("+678", "瓦努阿图");
            put("+685", "萨摩亚");
            put("+967", "也门");
            put("+269", "马约特");
            put("+27", "南非");
            put("+260", "赞比亚");
            put("+263", "津巴布韦");
        }
    };
    /**
     * 国际区号-中国
     */
    public static final String PHONE_AREA_CHINA = "+86";
    /**
     * Hong Kong	中国香港
     */
    public static final String HK = "852";
    /**
     * Macau	中国澳门
     */
    public static final String MO = "853";
    /**
     * Taiwan	中国台湾
     */
    public static final String TW = "886";
    /**
     * United States	美国
     */
    public static final String US = "1";
    /**
     * Andorra	安道尔
     */
    public static final String AD = "376";
    /**
     * United Arab Emirates	阿拉伯联合酋长国
     */
    public static final String AE = "971";
    /**
     * Afghanistan	阿富汗
     */
    public static final String AF = "93";
    /**
     * Antigua and Barbuda	安提瓜和巴布达
     */
    public static final String AG = "1268";
    /**
     * Anguilla	安圭拉
     */
    public static final String AI = "1264";
    /**
     * Albania	阿尔巴尼亚
     */
    public static final String AL = "355";
    /**
     * Armenia	亚美尼亚
     */
    public static final String AM = "374";
    /**
     * Angola	安哥拉
     */
    public static final String AO = "244";
    /**
     * Argentina	阿根廷
     */
    public static final String AR = "54";
    /**
     * American Samoa	美属萨摩亚
     */
    public static final String AS = "1684";
    /**
     * Austria	奥地利
     */
    public static final String AT = "43";
    /**
     * Australia	澳大利亚
     */
    public static final String AU = "61";
    /**
     * Aruba	阿鲁巴
     */
    public static final String AW = "297";
    /**
     * Azerbaijan	阿塞拜疆
     */
    public static final String AZ = "994";
    /**
     * Bosniaand Herzegovina	波斯尼亚和黑塞哥维那
     */
    public static final String BA = "387";
    /**
     * Barbados	巴巴多斯
     */
    public static final String BB = "1246";
    /**
     * Bangladesh	孟加拉国
     */
    public static final String BD = "880";
    /**
     * Belgium	比利时
     */
    public static final String BE = "32";
    /**
     * Burkina Faso	布基纳法索
     */
    public static final String BF = "226";
    /**
     * Bulgaria	保加利亚
     */
    public static final String BG = "359";
    /**
     * Bahrain	巴林
     */
    public static final String BH = "973";
    /**
     * Burundi	布隆迪
     */
    public static final String BI = "257";
    /**
     * Benin	贝宁
     */
    public static final String BJ = "229";
    /**
     * Bermuda	百慕大群岛
     */
    public static final String BM = "1441";
    /**
     * Brunei	文莱
     */
    public static final String BN = "673";
    /**
     * Bolivia	玻利维亚
     */
    public static final String BO = "591";
    /**
     * Caribisch Nederland	荷兰加勒比
     */
    public static final String BQ = "599";
    /**
     * Brazil	巴西
     */
    public static final String BR = "55";
    /**
     * Bahamas	巴哈马
     */
    public static final String BS = "1242";
    /**
     * Bhutan	不丹
     */
    public static final String BT = "975";
    /**
     * Botswana	博茨瓦纳
     */
    public static final String BW = "267";
    /**
     * Belarus	白俄罗斯
     */
    public static final String BY = "375";
    /**
     * Belize	伯利兹
     */
    public static final String BZ = "501";
    /**
     * Canada	加拿大
     */
    public static final String CA = "1";
    /**
     * Democratic Republic of theCongo	刚果民主共和国
     */
    public static final String CD = "243";
    /**
     * Central African Republic	中非共和国
     */
    public static final String CF = "236";
    /**
     * Republic Of The Congo	刚果共和国
     */
    public static final String CG = "242";
    /**
     * Switzerland	瑞士
     */
    public static final String CH = "41";
    /**
     * Ivory Coast	象牙海岸
     */
    public static final String CI = "225";
    /**
     * Cook Islands	库克群岛
     */
    public static final String CK = "682";
    /**
     * Chile	智利
     */
    public static final String CL = "56";
    /**
     * Cameroon	喀麦隆
     */
    public static final String CM = "237";
    /**
     * Colombia	哥伦比亚
     */
    public static final String CO = "57";
    /**
     * CostaRica	哥斯达黎加
     */
    public static final String CR = "506";
    /**
     * Cuba	古巴
     */
    public static final String CU = "53";
    /**
     * Cape Verde	开普
     */
    public static final String CV = "238";
    /**
     * Curacao	库拉索
     */
    public static final String CW = "599";
    /**
     * Cyprus	塞浦路斯
     */
    public static final String CY = "357";
    /**
     * Czech	捷克
     */
    public static final String CZ = "420";
    /**
     * Germany	德国
     */
    public static final String DE = "49";
    /**
     * Djibouti	吉布提
     */
    public static final String DJ = "253";
    /**
     * Denmark	丹麦
     */
    public static final String DK = "45";
    /**
     * Dominica	多米尼加
     */
    public static final String DM = "1767";
    /**
     * dominican republic	多米尼加共和国
     */
    public static final String DO = "1809";
    /**
     * Algeria	阿尔及利亚
     */
    public static final String DZ = "213";
    /**
     * Ecuador	厄瓜多尔
     */
    public static final String EC = "593";
    /**
     * Estonia	爱沙尼亚
     */
    public static final String EE = "372";
    /**
     * Egypt	埃及
     */
    public static final String EG = "20";
    /**
     * Eritrea	厄立特里亚
     */
    public static final String ER = "291";
    /**
     * Spain	西班牙
     */
    public static final String ES = "34";
    /**
     * Ethiopia	埃塞俄比亚
     */
    public static final String ET = "251";
    /**
     * Finland	芬兰
     */
    public static final String FI = "358";
    /**
     * Fiji	斐济
     */
    public static final String FJ = "679";
    /**
     * Micronesia	密克罗尼西亚
     */
    public static final String FM = "691";
    /**
     * Faroe Islands	法罗群岛
     */
    public static final String FO = "298";
    /**
     * France	法国
     */
    public static final String FR = "33";
    /**
     * Gabon	加蓬
     */
    public static final String GA = "241";
    /**
     * United Kingdom	英国
     */
    public static final String GB = "44";
    /**
     * Grenada	格林纳达
     */
    public static final String GD = "1473";
    /**
     * Georgia	格鲁吉亚
     */
    public static final String GE = "995";
    /**
     * French Guiana	法属圭亚那
     */
    public static final String GF = "594";
    /**
     * Ghana	加纳
     */
    public static final String GH = "233";
    /**
     * Gibraltar	直布罗陀
     */
    public static final String GI = "350";
    /**
     * Greenland	格陵兰岛
     */
    public static final String GL = "299";
    /**
     * Gambia	冈比亚
     */
    public static final String GM = "220";
    /**
     * Guinea	几内亚
     */
    public static final String GN = "224";
    /**
     * Guadeloupe	瓜德罗普岛
     */
    public static final String GP = "590";
    /**
     * Equatorial Guinea	赤道几内亚
     */
    public static final String GQ = "240";
    /**
     * Greece	希腊
     */
    public static final String GR = "30";
    /**
     * Guatemala	瓜地马拉
     */
    public static final String GT = "502";
    /**
     * Guam	关岛
     */
    public static final String GU = "1671";
    /**
     * Guinea-Bissau	几内亚比绍共和国
     */
    public static final String GW = "245";
    /**
     * Guyana	圭亚那
     */
    public static final String GY = "592";
    /**
     * Honduras	洪都拉斯
     */
    public static final String HN = "504";
    /**
     * Croatia	克罗地亚
     */
    public static final String HR = "385";
    /**
     * Haiti	海地
     */
    public static final String HT = "509";
    /**
     * Hungary	匈牙利
     */
    public static final String HU = "36";
    /**
     * Indonesia	印度尼西亚
     */
    public static final String ID = "62";
    /**
     * Ireland	爱尔兰
     */
    public static final String IE = "353";
    /**
     * Israel	以色列
     */
    public static final String IL = "972";
    /**
     * India	印度
     */
    public static final String IN = "91";
    /**
     * Iraq	伊拉克
     */
    public static final String IQ = "964";
    /**
     * Iran	伊朗
     */
    public static final String IR = "98";
    /**
     * Iceland	冰岛
     */
    public static final String IS = "354";
    /**
     * Italy	意大利
     */
    public static final String IT = "39";
    /**
     * Jamaica	牙买加
     */
    public static final String JM = "1876";
    /**
     * Jordan	约旦
     */
    public static final String JO = "962";
    /**
     * Japan	日本
     */
    public static final String JP = "81";
    /**
     * Kenya	肯尼亚
     */
    public static final String KE = "254";
    /**
     * Kyrgyzstan	吉尔吉斯斯坦
     */
    public static final String KG = "996";
    /**
     * Cambodia	柬埔寨
     */
    public static final String KH = "855";
    /**
     * Kiribati	基里巴斯
     */
    public static final String KI = "686";
    /**
     * Comoros	科摩罗
     */
    public static final String KM = "269";
    /**
     * Saint Kitts and Nevis	圣基茨和尼维斯
     */
    public static final String KN = "1869";
    /**
     * Korea Democratic Rep.	朝鲜
     */
    public static final String KP = "850";
    /**
     * South Korea	韩国
     */
    public static final String KR = "82";
    /**
     * Kuwait	科威特
     */
    public static final String KW = "965";
    /**
     * Cayman Islands	开曼群岛
     */
    public static final String KY = "1345";
    /**
     * Kazakhstan	哈萨克斯坦
     */
    public static final String KZ = "7";
    /**
     * Laos	老挝
     */
    public static final String LA = "856";
    /**
     * Lebanon	黎巴嫩
     */
    public static final String LB = "961";
    /**
     * Saint Lucia	圣露西亚
     */
    public static final String LC = "1758";
    /**
     * Liechtenstein	列支敦士登
     */
    public static final String LI = "423";
    /**
     * Sri Lanka	斯里兰卡
     */
    public static final String LK = "94";
    /**
     * Liberia	利比里亚
     */
    public static final String LR = "231";
    /**
     * Lesotho	莱索托
     */
    public static final String LS = "266";
    /**
     * Lithuania	立陶宛
     */
    public static final String LT = "370";
    /**
     * Luxembourg	卢森堡
     */
    public static final String LU = "352";
    /**
     * Latvia	拉脱维亚
     */
    public static final String LV = "371";
    /**
     * Libya	利比亚
     */
    public static final String LY = "218";
    /**
     * Morocco	摩洛哥
     */
    public static final String MA = "212";
    /**
     * Monaco	摩纳哥
     */
    public static final String MC = "377";
    /**
     * Moldova	摩尔多瓦
     */
    public static final String MD = "373";
    /**
     * Montenegro	黑山
     */
    public static final String ME = "382";
    /**
     * Madagascar	马达加斯加
     */
    public static final String MG = "261";
    /**
     * Marshall Islands	马绍尔群岛
     */
    public static final String MH = "692";
    /**
     * Macedonia	马其顿
     */
    public static final String MK = "389";
    /**
     * Mali	马里
     */
    public static final String ML = "223";
    /**
     * Myanmar	缅甸
     */
    public static final String MM = "95";
    /**
     * Mongolia	蒙古
     */
    public static final String MN = "976";
    /**
     * Mauritania	毛里塔尼亚
     */
    public static final String MR = "222";
    /**
     * Montserrat	蒙特塞拉特岛
     */
    public static final String MS = "1664";
    /**
     * Malta	马耳他
     */
    public static final String MT = "356";
    /**
     * Mauritius	毛里求斯
     */
    public static final String MU = "230";
    /**
     * Maldives	马尔代夫
     */
    public static final String MV = "960";
    /**
     * Malawi	马拉维
     */
    public static final String MW = "265";
    /**
     * Mexico	墨西哥
     */
    public static final String MX = "52";
    /**
     * Malaysia	马来西亚
     */
    public static final String MY = "60";
    /**
     * Mozambique	莫桑比克
     */
    public static final String MZ = "258";
    /**
     * Namibia	纳米比亚
     */
    public static final String NA = "264";
    /**
     * New Caledonia	新喀里多尼亚
     */
    public static final String NC = "687";
    /**
     * Niger	尼日尔
     */
    public static final String NE = "227";
    /**
     * Nigeria	尼日利亚
     */
    public static final String NG = "234";
    /**
     * Nicaragua	尼加拉瓜
     */
    public static final String NI = "505";
    /**
     * Netherlands	荷兰
     */
    public static final String NL = "31";
    /**
     * Norway	挪威
     */
    public static final String NO = "47";
    /**
     * Nepal	尼泊尔
     */
    public static final String NP = "977";
    /**
     * Nauru	拿鲁岛
     */
    public static final String NR = "674";
    /**
     * New Zealand	新西兰
     */
    public static final String NZ = "64";
    /**
     * Oman	阿曼
     */
    public static final String OM = "968";
    /**
     * Panama	巴拿马
     */
    public static final String PA = "507";
    /**
     * Peru	秘鲁
     */
    public static final String PE = "51";
    /**
     * French Polynesia	法属波利尼西亚
     */
    public static final String PF = "689";
    /**
     * Papua New Guinea	巴布亚新几内亚
     */
    public static final String PG = "675";
    /**
     * Philippines	菲律宾
     */
    public static final String PH = "63";
    /**
     * Pakistan	巴基斯坦
     */
    public static final String PK = "92";
    /**
     * Poland	波兰
     */
    public static final String PL = "48";
    /**
     * Saint Pierreand Miquelon	圣彼埃尔和密克隆岛
     */
    public static final String PM = "508";
    /**
     * Puerto Rico	波多黎各
     */
    public static final String PR = "1787";
    /**
     * Portugal	葡萄牙
     */
    public static final String PT = "351";
    /**
     * Palau	帕劳
     */
    public static final String PW = "680";
    /**
     * Paraguay	巴拉圭
     */
    public static final String PY = "595";
    /**
     * Qatar	卡塔尔
     */
    public static final String QA = "974";
    /**
     * Réunion Island	留尼汪
     */
    public static final String RE = "262";
    /**
     * Romania	罗马尼亚
     */
    public static final String RO = "40";
    /**
     * Serbia	塞尔维亚
     */
    public static final String RS = "381";
    /**
     * Russia	俄罗斯
     */
    public static final String RU = "7";
    /**
     * Rwanda	卢旺达
     */
    public static final String RW = "250";
    /**
     * Saudi Arabia	沙特阿拉伯
     */
    public static final String SA = "966";
    /**
     * Solomon Islands	所罗门群岛
     */
    public static final String SB = "677";
    /**
     * Seychelles	塞舌尔
     */
    public static final String SC = "248";
    /**
     * Sudan	苏丹
     */
    public static final String SD = "249";
    /**
     * Sweden	瑞典
     */
    public static final String SE = "46";
    /**
     * Singapore	新加坡
     */
    public static final String SG = "65";
    /**
     * Slovenia	斯洛文尼亚
     */
    public static final String SI = "386";
    /**
     * Slovakia	斯洛伐克
     */
    public static final String SK = "421";
    /**
     * Sierra Leone	塞拉利昂
     */
    public static final String SL = "232";
    /**
     * San Marino	圣马力诺
     */
    public static final String SM = "378";
    /**
     * Senegal	塞内加尔
     */
    public static final String SN = "221";
    /**
     * Somalia	索马里
     */
    public static final String SO = "252";
    /**
     * Suriname	苏里南
     */
    public static final String SR = "597";
    /**
     * Sao Tome and Principe	圣多美和普林西比
     */
    public static final String ST = "239";
    /**
     * ElSalvador	萨尔瓦多
     */
    public static final String SV = "503";
    /**
     * Syria	叙利亚
     */
    public static final String SY = "963";
    /**
     * Swaziland	斯威士兰
     */
    public static final String SZ = "268";
    /**
     * Turksand Caicos Islands	特克斯和凯科斯群岛
     */
    public static final String TC = "1649";
    /**
     * Chad	乍得
     */
    public static final String TD = "235";
    /**
     * Togo	多哥
     */
    public static final String TG = "228";
    /**
     * Thailand	泰国
     */
    public static final String TH = "66";
    /**
     * Tajikistan	塔吉克斯坦
     */
    public static final String TJ = "992";
    /**
     * East Timor	东帝汶
     */
    public static final String TL = "670";
    /**
     * Turkmenistan	土库曼斯坦
     */
    public static final String TM = "993";
    /**
     * Tunisia	突尼斯
     */
    public static final String TN = "216";
    /**
     * Tonga	汤加
     */
    public static final String TO = "676";
    /**
     * Turkey	土耳其
     */
    public static final String TR = "90";
    /**
     * Trinidadand Tobago	特立尼达和多巴哥
     */
    public static final String TT = "1868";
    /**
     * Tanzania	坦桑尼亚
     */
    public static final String TZ = "255";
    /**
     * Ukraine	乌克兰
     */
    public static final String UA = "380";
    /**
     * Uganda	乌干达
     */
    public static final String UG = "256";
    /**
     * Uruguay	乌拉圭
     */
    public static final String UY = "598";
    /**
     * Uzbekistan	乌兹别克斯坦
     */
    public static final String UZ = "998";
    /**
     * Saint Vincent and The Grenadines	圣文森特和格林纳丁斯
     */
    public static final String VC = "1784";
    /**
     * Venezuela	委内瑞拉
     */
    public static final String VE = "58";
    /**
     * VirginIslands,British	英属处女群岛
     */
    public static final String VG = "1284";
    /**
     * Vietnam	越南
     */
    public static final String VN = "84";
    /**
     * Vanuatu	瓦努阿图
     */
    public static final String VU = "678";
    /**
     * Samoa	萨摩亚
     */
    public static final String WS = "685";
    /**
     * Yemen	也门
     */
    public static final String YE = "967";
    /**
     * Mayotte	马约特
     */
    public static final String YT = "269";
    /**
     * South Africa	南非
     */
    public static final String ZA = "27";
    /**
     * Zambia	赞比亚
     */
    public static final String ZM = "260";
    /**
     * Zimbabwe	津巴布韦
     */
    public static final String ZW = "263";

    public static Map<String, String> getPhoneAreaMap() {
        return phoneAreaMap;
    }
}
