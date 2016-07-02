<%@ page session="false"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>


				<INPUT TYPE='HIDDEN' NAME='x_fp_sequence' VALUE='${fingerprint.sequence}'/>
				<INPUT TYPE='HIDDEN' NAME='x_fp_timestamp' VALUE='${fingerprint.timeStamp}'/> 
				<INPUT TYPE='HIDDEN' NAME='x_fp_hash' VALUE='${fingerprint.fingerprintHash}'/> 