package my.maryvk.maryvkweb.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RelationTypeConverter implements AttributeConverter<RelationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RelationType relationType) {
        return relationType.getId();
    }

    @Override
    public RelationType convertToEntityAttribute(Integer id) {
        return RelationType.get(id);
    }
}