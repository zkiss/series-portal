import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.web.server.converter.Converter;
import hu.bme.viaum105.web.server.converter.ConverterException;

public class ConverterTest {

    public static void main(String[] args) throws ConverterException {
	Series s = new Series();
	s.setTitle("asd");
	s.setDescription("asdasd");
	s.setImdbUrl("imdb");

	s.getEpisodes().add(new Episode());
	System.out.println(Converter.convert(s));
    }

}
