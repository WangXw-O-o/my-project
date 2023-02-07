package handler;

import function.BufferReaderHandler;

import java.io.*;

public class LineHandler {

    private static final String RN = "\r\n";

    private boolean commit = false;

    private int commitCount = 5000;

    private int allCount = 0;
    private int allCommitCount = 0;

    public LineHandler() {
    }

    public LineHandler(boolean commit, int commitCount) {
        this.commit = commit;
        this.commitCount = commitCount;
    }

    public File createNewFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("文件创建 >>> 成功！" + file.getName());
            } else {
                System.out.println("文件创建 >>> 失败！" );
            }
            return file;
        } catch (IOException e) {
            System.out.println("createNewFile >>> IOException");
            e.printStackTrace();
            throw new RuntimeException("文件创建异常!");
        }
    }

    /**
     * 单个输入，单个输出
     * @param inputPath 输入文件
     * @param outputFile 输出文件
     * @param firstLineRemark 第一行备注
     * @param bufferReaderHandler 输入文件每行解析后需要输出的内容
     */
    public void singleInOutWithFileCreate(
            String inputPath,
            File outputFile,
            String firstLineRemark,
            BufferReaderHandler bufferReaderHandler) {
        try (
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(new FileInputStream(inputPath)));
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
        ) {
            System.out.println("LineHandler.singleInOut start >>> ");
            writer.write(firstLineRemark + RN);
            String lineString;
            while ((lineString = reader.readLine()) != null) {
                //输出commit;
                commit(writer);
                //获取sql语句
                String sqlByLineString = bufferReaderHandler.createSqlByLineString(lineString);
                //输出sql语句
                writer.write(sqlByLineString + RN);
                this.allCount ++;
            }
            //结尾输出commit;
            if (this.commit) {
                writer.write("commit;" + RN);
                this.allCommitCount ++;
            }
            System.out.println("all count = " + this.allCount);
            System.out.println("all commit count = " + this.allCommitCount);
            System.out.println("line number = " + this.allCommitCount + this.allCount);
            System.out.println("LineHandler.singleInOut end >>> ");
        } catch (Exception e) {
            System.out.println("LineHandler.singleInOut error >>> ");
            e.printStackTrace();
        }
    }

    private void commit(BufferedWriter writer) throws IOException {
        if (!this.commit) {
            return;
        }
        if (this.commitCount == 0) {
            writer.write("commit;" + RN);
            this.allCommitCount ++;
            this.commitCount = 5000;
        } else {
            this.commitCount -= 1;
        }
    }

}
