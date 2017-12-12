package ufm.universalfinancemanager.db.source;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by smh7 on 12/11/17.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Local {
}
