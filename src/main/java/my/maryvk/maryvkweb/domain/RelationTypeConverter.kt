package my.maryvk.maryvkweb.domain

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class RelationTypeConverter : AttributeConverter<RelationType, Int> {

    override fun convertToDatabaseColumn(relationType: RelationType): Int {
        return relationType.id
    }

    override fun convertToEntityAttribute(id: Int): RelationType {
        return RelationType.get(id)
    }
}