/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.compiler.traits

import grails.artefact.TagLibrary

import java.util.regex.Pattern

import org.grails.io.support.GrailsResourceUtils

/**
 * 
 * @since 3.0
 * @author Jeff Brown
 *
 */
class TagLibraryTraitInjector implements TraitInjector {
    
    static Pattern TAGLIB_PATTERN = Pattern.compile(".+/" +
        GrailsResourceUtils.GRAILS_APP_DIR + "/taglib/(.+)TagLib\\.groovy")
 
    @Override
    Class getTrait() {
        TagLibrary
    }
 
    @Override
    boolean shouldInject(URL url) {
        return url != null && TAGLIB_PATTERN.matcher(url.getFile()).find();
    }
 
    @Override
    String[] getArtefactTypes() {
        ['TagLibrary', 'TagLib'] as String[]
    }
}
