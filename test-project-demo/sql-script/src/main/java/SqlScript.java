import handler.LineHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlScript {

    public static void main(String[] args) {
//        create_he_gui_BaseAccount_freeze();
//        create_BaseAccount_unFreeze();
//        create_BaseAccount_Freeze();

        createInfoShareData();
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


    private static void createInfoShareData() {
        String memberIdAndIdCardFilePath = "/Users/xl-shuke/Desktop/blacklist.csv";
        String userIdAndIdCardFilePath = "/Users/xl-shuke/Downloads/data.csv";
        try (
                BufferedReader readerMemberId = new BufferedReader(new InputStreamReader(new FileInputStream(memberIdAndIdCardFilePath)));
                BufferedReader readerUserId = new BufferedReader(new InputStreamReader(new FileInputStream(userIdAndIdCardFilePath)));
                ) {
            Map<String, List<String>> mMap = new HashMap<>();

            String line = readerMemberId.readLine();
            while ((line = readerMemberId.readLine()) != null) {
                String[] split = line.split(",");
                String memberId = split[0];
                String idCard = split[1];
                if (!mMap.containsKey(idCard)) {
                    List<String> list = new ArrayList<>();
                    list.add(memberId);
                    mMap.put(idCard, list);
                } else {
                    mMap.get(idCard).add(memberId);
                }
            }

            Map<String, List<String>> uMap = new HashMap<>();
            String line1 = readerUserId.readLine();
            while ((line1 = readerUserId.readLine()) != null) {
                String[] split = line1.replaceAll("\"", "").split(",");
                String idCard = split[0];
                String userId = split[1];
                if (mMap.containsKey(idCard)) {
                    if (!uMap.containsKey(idCard)) {
                        List<String> list = new ArrayList<>();
                        list.add(userId);
                        uMap.put(idCard, list);
                    } else {
                        uMap.get(idCard).add(userId);
                    }
                }
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
