import handler.LineHandler;

public class SqlScript {

    public static void main(String[] args) {
        create_he_gui_BaseAccount_freeze();
        create_BaseAccount_unFreeze();
        create_BaseAccount_Freeze();
    }

    //合规银行基本户冻结
    private static void create_he_gui_BaseAccount_freeze() {
        String he_gui_account_in = "/Users/xl-shuke/Desktop/合规银行基本户.csv";
        String he_gui_account_out = "/Users/xl-shuke/Desktop/DML_T_DPM_OUTER_ACCOUNT_20221226_迁移后冻结合规银行基本户.sql";

        LineHandler lineHandler = new LineHandler(false, 5000);
        lineHandler.singleInOutWithFileCreate(
                he_gui_account_in,
                lineHandler.createNewFile(he_gui_account_out),
                "-- 冻结合规银行基本户，共49条。",
                lineString -> {
                    String[] split = lineString.split(",");
                    String accountNo = split[0].replaceAll("\"", "");
                    String sql = "update dpm.T_DPM_OUTER_ACCOUNT " +
                            "set STATUS_MAP = '1030', LAST_UPDATE_TIME = sysdate " +
                            "where ACCOUNT_NO = '"+accountNo+"' and ACCOUNT_TYPE = '301'; ";
                    return sql;
                });
    }

    //基本户解冻
    private static void create_BaseAccount_unFreeze() {
        String he_gui_account_in = "/Users/xl-shuke/Desktop/冻结的基本户.csv";
        String he_gui_account_out = "/Users/xl-shuke/Desktop/DML_T_DPM_OUTER_ACCOUNT_20221226_迁移前解冻原基本户.sql";

        LineHandler lineHandler = new LineHandler(false, 5000);
        lineHandler.singleInOutWithFileCreate(
                he_gui_account_in,
                lineHandler.createNewFile(he_gui_account_out),
                "-- 冻结的基本户解冻，共49条。",
                lineString -> {
                    String[] split = lineString.split(",");
                    String accountNo = split[0].replaceAll("\"", "");
                    String sql = "update dpm.T_DPM_OUTER_ACCOUNT " +
                            "set STATUS_MAP = '1000', LAST_UPDATE_TIME = sysdate " +
                            "where ACCOUNT_NO = '"+accountNo+"' and ACCOUNT_TYPE = '1' and STATUS_MAP = '1030'; ";
                    return sql;
                });
    }

    //基本户冻结
    private static void create_BaseAccount_Freeze() {
        String he_gui_account_in = "/Users/xl-shuke/Desktop/冻结的基本户.csv";
        String he_gui_account_out = "/Users/xl-shuke/Desktop/DML_T_DPM_OUTER_ACCOUNT_20221226_迁移后冻结原基本户.sql";

        LineHandler lineHandler = new LineHandler(false, 5000);
        lineHandler.singleInOutWithFileCreate(
                he_gui_account_in,
                lineHandler.createNewFile(he_gui_account_out),
                "-- 解冻的基本户恢复冻结，共49条。",
                lineString -> {
                    String[] split = lineString.split(",");
                    String accountNo = split[0].replaceAll("\"", "");
                    String sql = "update dpm.T_DPM_OUTER_ACCOUNT " +
                            "set STATUS_MAP = '1030', LAST_UPDATE_TIME = sysdate " +
                            "where ACCOUNT_NO = '"+accountNo+"' and ACCOUNT_TYPE = '1' and STATUS_MAP = '1000'; ";
                    return sql;
                });
    }

}
