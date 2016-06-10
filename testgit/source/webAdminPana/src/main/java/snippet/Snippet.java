package snippet;

public class Snippet {
	<bean id="housingFormFactory" class="jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory">
	    <property name="codeLookupManager" ref="codeLookupManager" />
	    <property name="lengthUtils" ref="lengthValidationUtils" />
	    <property name="commonParameters" ref="commonParameters" />
	  </bean>
}

