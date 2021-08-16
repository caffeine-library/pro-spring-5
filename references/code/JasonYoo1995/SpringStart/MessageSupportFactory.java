package SpringStart;

import java.util.Properties;

public class MessageSupportFactory {
    private static MessageSupportFactory instance;

    private Properties props;
    private MessageRenderer renderer;
    private MessageProvider provider;

    private MessageSupportFactory(){
        props = new Properties();
        try{
            props.load(this.getClass().getResourceAsStream("/msf.properties"));
            String renderClass = props.getProperty("renderer.class");
            String providerClass = props.getProperty("provider.class");
            renderer = (MessageRenderer) Class.forName(renderClass).newInstance();
            provider = (MessageProvider) Class.forName(providerClass).newInstance();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    static{
        instance = new MessageSupportFactory();
    }

    public static MessageSupportFactory getInstance(){
        return instance;
    }

    public MessageRenderer getMessageRenderer(){
        return renderer;
    }

    public MessageProvider getMessageProvider(){
        return provider;
    }
}
