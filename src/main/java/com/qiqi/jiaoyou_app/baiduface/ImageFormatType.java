package com.qiqi.jiaoyou_app.baiduface;

public enum ImageFormatType {
    ERROR1(283421,"应用不存在"),
    ERROR2(283435,"方案不存在"),
    ERROR3(283436,"Token生成失败，请重试"),
    ERROR4(283437,"Token无效或已过期，请重新生成"),
    ERROR6(283451,"认证处理中"),
    ERROR7(283455,"超出查询有效期"),
    ERROR8(222350,"公安网图片不存在或,质量过低"),
    ERROR12(222351,"身份证号与姓名不匹配或该,身份证号不存在"),
    ERROR17(222352,"身份证名字格式错误"),
    ERROR20(222353,"身份证号码格式错误"),
    ERROR23(222354,"公安库里不存在此身份证号"),
    ERROR26(222355,"身份证号码正确，公安库里没有对应的照片"),
    ERROR29(222356,"验证的人脸图片质量不符合要求"),
    ERROR33(222360,"身份证号码或名字非法 公安网校验不通过"),
    ERROR38(222361,"公安服务连接失败"),
    ERROR41(216600,"输入身份证格式错误"),
    ERROR42(216601,"身份证号和名字匹配失败"),
    ERROR43(216430,"rtse/face 服务异常"),
    ERROR44(216431,"语音识别服务异常"),
    ERROR45(216432,"视频解析服务调用失败"),
    ERROR46(216433,"视频解析服务发生错误"),
    ERROR47(216434,"活体检测失败"),
    ERROR48(216500,"验证码位数错误"),
    ERROR50(216501,"没有找到人脸"),
    ERROR51(216502,"当前会话已失效"),
    ERROR52(216505,"redis连接失败"),
    ERROR53(216506,"redis操作失败"),
    ERROR54(216507,"视频中有多张人脸"),
    ERROR55(216508,"没有找到视频信息"),
    ERROR56(216509,"视频中的声音无法识别,(声音过低或者有杂音导致无法识别)"),
    ERROR59(216908,"视频中人脸质量过低"),
    ERROR65(222027,"验证码长度错误,(最小值大于最大值)"),
    ERROR67(222028,"参数格式错误"),
    ERROR68(222029,"参数格式错误"),
    ERROR69(222030,"参数格式错误"),
    ERROR70(222001,"必要参数未传入"),
    ERROR71(222002,"参数格式错误"),
    ERROR73(222003,"参数格式错误"),
    ERROR75(222004,"参数格式错误"),
    ERROR76(222005,"参数格式错误"),
    ERROR77(222006,"参数格式错误"),
    ERROR78(222007,"参数格式错误"),
    ERROR79(222008,"参数格式错误"),
    ERROR80(222009,"参数格式错误"),
    ERROR82(222010,"参数格式错误"),
    ERROR83(222011,"参数格式错误"),
    ERROR84(222012,"参数格式错误"),
    ERROR86(222013,"参数格式错误"),
    ERROR88(222014,"参数格式错误"),
    ERROR90(222015,"参数格式错误"),
    ERROR92(222016,"参数格式错误"),
    ERROR94(222017,"参数格式错误"),
    ERROR96(222018,"参数格式错误"),
    ERROR98(222019,"参数格式错误"),
    ERROR100(222020,"参数格式错误"),
    ERROR102(222021,"参数格式错误"),
    ERROR104(222022,"参数格式错误"),
    ERROR106(222023,"参数格式错误"),
    ERROR107(222024,"参数格式错误"),
    ERROR109(222025,"参数格式错误"),
    ERROR111(222026,"参数格式错误"),
    ERROR112(222027,"验证码长度错误"),
    ERROR114(222028,"参数格式错误"),
    ERROR115(222029,"参数格式错误"),
    ERROR116(222030,"参数格式错误"),
    ERROR117(222200,"该接口需使用application/json的格式进行请求"),
    ERROR120(222201,"服务端请求失败"),
    ERROR121(222202,"图片中没有人脸"),
    ERROR122(222203,"无法解析人脸"),
    ERROR123(222204,"从图片的url下载"),
    ERROR125(222205,"服务端请求失败"),
    ERROR126(222206,"服务端请求失败"),
    ERROR127(222207,"未找到匹配的用户"),
    ERROR129(222208,"图片的数量错误"),
    ERROR131(222209,"face token不存在"),
    ERROR137(222210,"人脸库中用户下的人脸数目超过限制"),
    ERROR138(222300,"人脸图片添加失败"),
    ERROR139(222301,"获取人脸图片失败"),
    ERROR142(222302,"服务端请求失败"),
    ERROR143(222303,"获取人脸图片失败"),
    ERROR145(223100,"操作的用户组不存在"),
    ERROR147(223101,"该用户组已存在"),
    ERROR148(223102,"该用户已存在"),
    ERROR149(223103,"找不到该用户"),
    ERROR151(223104,"group_list包含组数量过多"),
    ERROR153(223105,"该人脸已存在"),
    ERROR154(223106,"该人脸不存在"),
    ERROR160(223110,"uid_list包含数量过多"),
    ERROR162(223111,"目标用户组不存在"),
    ERROR164(223112,"quality_conf格式不正确"),
    ERROR166(223113,"人脸有被遮挡"),
    ERROR167(223114,"人脸模糊"),
    ERROR170(223115,"人脸光照不好"),
    ERROR171(223116,"人脸不完整"),
    ERROR172(223117,"app_list包含app数量过多"),
    ERROR174(223118,"质量控制项错误"),
    ERROR176(223119,"活体控制项错误"),
    ERROR178(223120,"活体检测未通过"),
    ERROR179(223121,"质量检测未通过 左眼遮挡程度过高"),
    ERROR181(223122,"质量检测未通过 右眼遮挡程度过高"),
    ERROR183(223123,"质量检测未通过 左脸遮挡程度过高"),
    ERROR185(223124,"质量检测未通过 右脸遮挡程度过高"),
    ERROR187(223125,"质量检测未通过 下巴遮挡程度过高"),
    ERROR189(223126,"质量检测未通过 鼻子遮挡程度过高"),
    ERROR190(223127,"质量检测未通过 嘴巴遮挡程度过高"),
    ERROR192(222901,"参数校验初始化失败"),
    ERROR195(222902,"参数校验初始化失败"),
    ERROR198(222903,"参数校验初始化失败"),
    ERROR201(222904,"参数校验初始化失败"),
    ERROR204(222905,"接口初始化失败"),
    ERROR207(222906,"接口初始化失败"),
    ERROR210(222907,"缓存处理失败"),
    ERROR213(222908,"缓存处理失败"),
    ERROR216(222909,"缓存处理失败"),
    ERROR219(222910,"数据存储处理失败"),
    ERROR222(222911,"数据存储处理失败"),
    ERROR225(222912,"数据存储处理失败"),
    ERROR228(222913,"接口初始化失败"),
    ERROR231(222914,"接口初始化失败"),
    ERROR234(222915,"后端服务连接失败"),
    ERROR237(222916,"后端服务连接失败"),
    ERROR240(222304,"图片尺寸太大"),
    ERROR241(222305,"当前版本不支持图片存储"),
    ERROR242(223128,"正在清理该用户组的数据"),
    ERROR244(222361,"公安服务连接失败"),
    ERROR247(222046,"参数格式错误"),
    ERROR248(222101,"参数格式错误"),
    ERROR249(222102,"参数格式错误"),
    ERROR250(222307,"图片非法 鉴黄未通过"),
    ERROR251(222308,"图片非法 含有政治敏感人物"),
    ERROR252(222211,"人脸融合失败 模板图质量不合格"),
    ERROR254(222212,"人脸融合失败"),
    ERROR256(223129,"人脸未面向正前方,(人脸的角度信息大于30度)"),
    ERROR258(4,"集群超限额"),
    ERROR259(6,"没有接口权限"),
    ERROR262(17,"每天流量超限额"),
    ERROR263(18,"QPS超限额"),
    ERROR264(19,"请求总量超限额"),
    ERROR265(100,"无效的access_token参数"),
    ERROR266(110,"Access Token失效"),
    ERROR267(111,"Access token过期"),


    ;

    private Integer code;

    private String value;


    ImageFormatType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static Integer getCode(Integer code) {
        ImageFormatType[] imageFormatTypes = values();
        for (ImageFormatType imageFormatType : imageFormatTypes) {
            if (imageFormatType.code().equals(code)) {
                return imageFormatType.code();
            }
        }
        return null;
    }

    public static String getValue(Integer code) {
        ImageFormatType[] imageFormatTypes = values();
        for (ImageFormatType imageFormatType : imageFormatTypes) {
            if (imageFormatType.code().equals(code)) {
                return imageFormatType.value();
            }
        }
        return null;
    }

    public Integer code() {
        return code;
    }

    public String value() {
        return value;
    }


}