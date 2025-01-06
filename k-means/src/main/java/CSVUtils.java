import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    /**
     * 从 CSV 文件中读取数据并转换为 DataPoint 对象列表
     *
     * @param filePath CSV 文件路径
     * @return 包含 DataPoint 对象的列表
     */
    public static List<DataPoint> importFromCSV(String filePath) {
        List<DataPoint> dataPoints = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            for (String[] record : records) {
                List<Double> dimensions = new ArrayList<>();
                for (String value : record) {
                    dimensions.add(Double.parseDouble(value));
                }
                dataPoints.add(new DataPoint(dimensions));
            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }

        return dataPoints;
    }
}
