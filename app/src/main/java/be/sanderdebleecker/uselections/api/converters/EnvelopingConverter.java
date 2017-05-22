package be.sanderdebleecker.uselections.api.converters;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Type;

import be.sanderdebleecker.uselections.mvp.model.data.envelope.ElectionsEnvelope;
import be.sanderdebleecker.uselections.mvp.model.data.envelope.OfficialsEnvelope;
import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Retrofit converter to simplify API envelope responses such as
 * <p>
 * {
 * actualData: {object}
 * state: 200
 * time: 15:35
 * }
 * <p>
 * To subtracted clear data models such as
 * <p>
 * object {
 * name:"someName"
 * location:"someLocation"
 * }
 * <p>
 * And passing it to the next Converter in chain
 * <p>
 * Created by simulan on 15/05/2017.
 */
public class EnvelopingConverter extends Converter.Factory {
    @Retention(RUNTIME)
    public @interface Official {
    }
    @Retention(RUNTIME)
    public @interface Election {
    }

    /**
     * Looks for a converter for the envelope of given type but returns the actual type that was wrapped in.
     * @param type type specified in retrofit interface
     * @param annotations annotations added above retrofit interface's methods
     * @param retrofit instance on which method is called
     * @return compatible converter for data
     */
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter (Type type, Annotation[] annotations, Retrofit retrofit) {
        for(Annotation annotation : annotations) {
            if(annotation instanceof Election) {
                Type envType = ElectionsEnvelope.class;
                final Converter<ResponseBody, ElectionsEnvelope> converterForEnvelope = retrofit.nextResponseBodyConverter(this, envType, annotations);
                return body -> {
                    ElectionsEnvelope envelope = converterForEnvelope.convert(body);
                    return  Observable.fromIterable(envelope.getElections());
                };
            }
            if(annotation instanceof Official) {
                Type envType = OfficialsEnvelope.class;
                final Converter<ResponseBody, OfficialsEnvelope> converterForEnvelope = retrofit.nextResponseBodyConverter(this, envType, annotations);
                return body -> {
                    OfficialsEnvelope envelope = converterForEnvelope.convert(body);
                    return Observable.fromIterable(envelope.getOfficials());
                };
            }
        }
        return null;
    }
}
