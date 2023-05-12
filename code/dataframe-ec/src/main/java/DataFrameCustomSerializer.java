import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;

import java.io.IOException;

public class DataFrameCustomSerializer extends StdSerializer<DataFrame> {

    public DataFrameCustomSerializer(Class<DataFrame> t) {
        super(t);
    }

    public void serialize(DataFrame df, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {

        int columnCount = df.columnCount();
        int last =  df.rowCount();

        jsonGenerator.writeStartArray();

        for (int rowIndex = 0; rowIndex < last; rowIndex++) {
            jsonGenerator.writeStartObject();

            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++)  {
                jsonGenerator.writeStringField(df.getColumnAt(columnIndex).getName(), df.getValueAsString(rowIndex, columnIndex));
            }

            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
