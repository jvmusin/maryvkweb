package my.maryvkweb.domain

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class RelationTypeConverter : AttributeConverter<RelationType, Int> {
    override fun convertToDatabaseColumn(relationType: RelationType) = relationType.id
    override fun convertToEntityAttribute(id: Int) = RelationType.get(id)
}