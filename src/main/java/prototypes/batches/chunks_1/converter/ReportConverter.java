/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypes.batches.chunks.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prototypes.batches.chunks.model.Report;

public class ReportConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReportConverter.class);

    @Override
    public boolean canConvert(Class type) {
        //we only need "Report" object
        return type.equals(Report.class);
    }

    @Override
    public void marshal(Object source,
            HierarchicalStreamWriter writer, MarshallingContext context) {
        //do nothing
    }

    @Override
    public Object unmarshal(
            HierarchicalStreamReader reader, UnmarshallingContext context) {

        Report obj = new Report();

        //get attribute
        obj.setId(Integer.valueOf(reader.getAttribute("id")));
        reader.moveDown(); //get date

        Date date = null;
        try {
            date = new SimpleDateFormat("M/d/yyyy").parse(reader.getValue());
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        obj.setDate(date);
        reader.moveUp();

        reader.moveDown(); //get impression

        String impression = reader.getValue();
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        Number number = 0;
        try {
            number = format.parse(impression);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        obj.setImpression(number.longValue());

        reader.moveUp();

        reader.moveDown(); //get click
        obj.setClicks(Integer.valueOf(reader.getValue()));
        reader.moveUp();

        reader.moveDown(); //get earning
        obj.setEarning(new BigDecimal(reader.getValue()));
        reader.moveUp();

        return obj;

    }
}
