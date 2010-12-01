package hu.bme.viaum105.web.server.converter;

import hu.bme.viaum105.data.nonpersistent.Role;
import hu.bme.viaum105.data.persistent.Actor;
import hu.bme.viaum105.data.persistent.Comment;
import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Label;
import hu.bme.viaum105.data.persistent.Like;
import hu.bme.viaum105.data.persistent.Rate;
import hu.bme.viaum105.data.persistent.RegisteredEntity;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.data.persistent.Subtitle;
import hu.bme.viaum105.data.persistent.SubtitleData;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.web.server.converter.conversion.ConversionDefinition;
import hu.bme.viaum105.web.shared.dto.nonpersistent.RoleDto;
import hu.bme.viaum105.web.shared.dto.persistent.ActorDto;
import hu.bme.viaum105.web.shared.dto.persistent.CommentDto;
import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;
import hu.bme.viaum105.web.shared.dto.persistent.LabelDto;
import hu.bme.viaum105.web.shared.dto.persistent.LikeDto;
import hu.bme.viaum105.web.shared.dto.persistent.RateDto;
import hu.bme.viaum105.web.shared.dto.persistent.RegisteredEntityDto;
import hu.bme.viaum105.web.shared.dto.persistent.SeriesDto;
import hu.bme.viaum105.web.shared.dto.persistent.SubtitleDataDto;
import hu.bme.viaum105.web.shared.dto.persistent.SubtitleDto;
import hu.bme.viaum105.web.shared.dto.persistent.UserDto;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class ConverterConfig {

    private static final Log log = LogFactory.getLog(ConverterConfig.class);

    /**
     * Beégetett értékek
     */
    private static final LinkedList<ConversionDefinition> CONVERSION_DEFINITIONS;

    /**
     * Beégetett konfig példány
     */
    private static final ConverterConfig BURNT_IN_CONFIG;

    static {
	CONVERSION_DEFINITIONS = new LinkedList<ConversionDefinition>();

	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Role.class, RoleDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Actor.class, ActorDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Comment.class, CommentDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Episode.class, EpisodeDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Label.class, LabelDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Like.class, LikeDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Rate.class, RateDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(RegisteredEntity.class, RegisteredEntityDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Series.class, SeriesDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(Subtitle.class, SubtitleDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(SubtitleData.class, SubtitleDataDto.class));
	ConverterConfig.CONVERSION_DEFINITIONS.add(new ConversionDefinition(User.class, UserDto.class));

	BURNT_IN_CONFIG = new ConverterConfig(ConverterConfig.CONVERSION_DEFINITIONS);
    }

    public static ConverterConfig getBurntInConfig() {
	return ConverterConfig.BURNT_IN_CONFIG;
    }

    /**
     * Kulcs: osztálynév<br>
     * Érték: {@link Conversion}
     */
    private final TreeMap<String, Conversion> conversions;

    private ConverterConfig(Collection<ConversionDefinition> conversionDefinitions) {
	this.conversions = new TreeMap<String, Conversion>();

	if (ConverterConfig.log.isInfoEnabled()) {
	    StringBuilder sb = new StringBuilder("Converter config:");
	    for (ConversionDefinition cp : conversionDefinitions) {
		sb.append("\n").append(cp);
	    }
	    ConverterConfig.log.info(sb);
	}

	this.buildConversions(conversionDefinitions);
    }

    private void buildConversions(Collection<ConversionDefinition> conversionDefinitions) {
	for (ConversionDefinition cd : conversionDefinitions) {
	    Conversion conversion = new Conversion(cd);
	    this.conversions.put(cd.class1.getName(), conversion);
	    this.conversions.put(cd.class2.getName(), conversion);
	}
    }

    /**
     * A JavaBean-hoz tartozó {@link Conversion}
     * 
     * @param clazz
     * @return
     */
    public Conversion getConversion(Class<?> clazz) {
	return this.conversions.get(clazz.getName());
    }

}
