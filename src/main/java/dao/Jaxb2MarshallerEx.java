package dao;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Unmarshaller;

public class Jaxb2MarshallerEx extends Jaxb2Marshaller {

    @Override
    public Unmarshaller createUnmarshaller() {
        return super.createUnmarshaller();
    }
}
