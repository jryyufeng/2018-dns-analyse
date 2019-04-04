package dns.analyse.service.processors;
import dns.analyse.dao.model.DomainNetDetailPO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: jiangrongyin@meituan.com
 * Date: 2019/2/28 下午3:58
 * Description:
 */
public class CreateDomainDetailProcess {
    private static final Logger LOGGER = LogManager.getLogger(CreateDomainDetailProcess.class.getName());
    private static final String TEXT ="text";
    public static List<DomainNetDetailPO> getObjectByXml(String xml){
        Document doc;
        String xml1 = xml;
        if(Objects.equals(xml1,"None")){
            xml1 = "<root>none.com</root>";
        }
        try{
            doc = DocumentHelper.parseText(xml1);
        }catch (DocumentException e) {
            LOGGER.error("获取域名依赖详情信息xml转换失败,xml:{}",xml1);
            e.printStackTrace();
            return null;
        }
        // 获取根节点
        Element rootElt = doc.getRootElement();
        List<DomainNetDetailPO.transitionState> transitionStates =new ArrayList<>();
        List<DomainNetDetailPO.transitionState> resList = getNodesNew(transitionStates,rootElt);
        return getDomainNetDetailPO(resList);

    }
    private static void getNodes(Element node){
        System.out.println("--------------------");

        //当前节点的名称、文本内容和属性
        //当前节点名称
        System.out.println("当前节点名称："+node.getName());
        //当前节点名称
        System.out.println("当前节点的内容："+node.getTextTrim());
        //当前节点的所有属性的list
        List<Attribute> listAttr=node.attributes();
        //遍历当前节点的所有属性
        for(Attribute attr:listAttr){
            //属性名称
            String name=attr.getName();
            //属性的值
            String value=attr.getValue();
            System.out.println("属性名称："+name+"属性值："+value);
        }

        //递归遍历当前节点所有的子节点
        //所有一级子节点的list
        List<Element> listElement=node.elements();
        //遍历所有一级子节点
        for(Element e:listElement){
            //递归
            getNodes(e);
        }
    }

    private static List<DomainNetDetailPO.transitionState> getNodesNew(List<DomainNetDetailPO.transitionState> transitionStates,Element node){
        //递归遍历当前节点所有的子节点
        List<Element> listElement=node.elements();
        Attribute nameAttribute = node.attribute("domain");
        Attribute typeAttribute = node.attribute("type");
        if(nameAttribute != null && typeAttribute!=null && Objects.equals(typeAttribute.getValue(),"NS")){
            System.out.println(nameAttribute.getValue()+"------"+node.getTextTrim());
            DomainNetDetailPO.transitionState transitionState =new DomainNetDetailPO.transitionState();
            transitionState.setDomain(nameAttribute.getValue());
            transitionState.setServerList(node.getTextTrim());
            transitionStates.add(transitionState);
        }
        //遍历所有一级子节点
        for(Element e:listElement){
            //递归
            getNodesNew(transitionStates,e);
        }
        return transitionStates;

    }

    private static List<DomainNetDetailPO> getDomainNetDetailPO(List<DomainNetDetailPO.transitionState> list){
         Map<String,List<DomainNetDetailPO.transitionState>> map = list.stream().collect(Collectors.groupingBy(DomainNetDetailPO.transitionState::getDomain));
        List<DomainNetDetailPO> pos =new ArrayList<>();
         map.forEach((k, v) ->{
             DomainNetDetailPO po =new DomainNetDetailPO();
             po.setSubnetName(k);
             List<String> servers =new ArrayList<>();
             v.forEach(t->{
                 Collections.addAll(servers,t.getServerList().split(";"));
             });
             HashSet h = new HashSet(servers);
             servers.clear();
             servers.addAll(h);
             List<String> outServers =new ArrayList<>();
             //判断有没有跨域
             for (String ser:servers) {
                 if(!ser.contains(k)){
                     po.setFlag(true);
                     outServers.add(ser);
                 }
             }
             po.setOutServerList(outServers);
             po.setServerList(servers);
             pos.add(po);
         }
         );
         return pos;
    }
}
