import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.*;
import org.apache.axis2.client.*;

public class Query {
	boolean debugMode;
	
	/*public Query(boolean debugMode){
		this.debugMode = debugMode;
	}*/
	
	public String queryToPermisPDP(Subject_Action_Target info){
		OMElement requestSoapMessage = getParamInfoMessage(info);
		Options opts = new Options();
		opts.setTo(new EndpointReference("http://140.113.216.109:8080/axis2/services/AuthzService?wsdl"));
		opts.setAction("XACMLAuthzRequest");
		ServiceClient client = null;  
		
		try{
			
			client = new ServiceClient();	
			client.setOptions(opts);
			OMElement result = client.sendReceive(requestSoapMessage);
			
			Log log = new Log();
			log.showLog(info.targetAttribute + " " + info.actionAttribute + " request", requestSoapMessage.toString());
			log.showLog(info.targetAttribute + " " + info.actionAttribute + " response", result.toString());
			
			//MeMDAS.recordText.append(result.toString()+"\n");
			
			return result.getFirstElement().getFirstElement().getText();

		}catch(Exception e){
			e.printStackTrace();
			return "Error";
		}
	}
	
	public static OMElement getParamInfoMessage(Subject_Action_Target info){
		OMFactory factory = OMAbstractFactory.getOMFactory();
		
		OMNamespace omNs = factory.createOMNamespace("urn:oasis:names:tc:xacml:2.0:context:schema:os", "xacml-context");
		
		OMElement subject = factory.createOMElement("Subject", omNs);//第一參數
		OMElement subject_a = factory.createOMElement("Attribute", omNs);
		//OMElement subject_av = factory.createOMElement("AttributeValue", omNs);
		OMElement resource = factory.createOMElement("Resource", omNs);
		OMElement resource_a = factory.createOMElement("Attribute", omNs);
		OMElement resource_av = factory.createOMElement("AttributeValue", omNs);
		OMElement action = factory.createOMElement("Action", omNs);
		OMElement action_a = factory.createOMElement("Attribute", omNs);
		OMElement action_av = factory.createOMElement("AttributeValue", omNs);
		OMElement environment = factory.createOMElement("Environment", omNs);
		
		subject_a.addAttribute("DataType", "http://www.w3.org/2001/XMLSchema#string", null);
		subject_a.addAttribute("AttributeId", "urn:oid:1.2.826.0.1.3344810.1.1.14", null);
		resource_a.addAttribute("DataType", "http://www.w3.org/2001/XMLSchema#string", null);
		resource_a.addAttribute("AttributeId", "urn:oasis:names:tc:xacml:1.0:resource:resource-id", null);
		action_a.addAttribute("DataType", "http://www.w3.org/2001/XMLSchema#string", null);
		action_a.addAttribute("AttributeId", "urn:oasis:names:tc:xacml:1.0:action:action-id", null);
		
		subject_a.addAttribute("Issuer", info.Issuer, null);
		//subject_av.setText(info.subjectAttribute);
		resource_av.setText(info.targetAttribute);		
		action_av.setText(info.actionAttribute);
		
		for(int i=0; i < info.subjectAttribute.length; i++){
			OMElement subject_av = factory.createOMElement("AttributeValue", omNs);
			subject_av.setText(info.subjectAttribute[i]);
			subject_a.addChild(subject_av);
		}
		
		OMElement requestSoapMessage  = factory.createOMElement("Request", omNs);
		
		//subject_a.addChild(subject_av);
		subject.addChild(subject_a);
		resource_a.addChild(resource_av);
		resource.addChild(resource_a);
		action_a.addChild(action_av);
		action.addChild(action_a);
		
		requestSoapMessage.addChild(subject);
		requestSoapMessage.addChild(resource);
		requestSoapMessage.addChild(action);
		requestSoapMessage.addChild(environment);

		requestSoapMessage.build();
		return requestSoapMessage;
	}	
}
