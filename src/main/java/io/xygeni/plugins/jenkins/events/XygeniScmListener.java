/*
The MIT License

Copyright (c) 2015-Present Datadog, Inc <opensource@datadoghq.com>
All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package io.xygeni.plugins.jenkins.events;

import hudson.Extension;
import hudson.FilePath;
import hudson.XmlFile;
import hudson.model.Run;
import hudson.model.Saveable;
import hudson.model.TaskListener;
import hudson.model.listeners.SCMListener;
import hudson.model.listeners.SaveableListener;
import hudson.scm.SCM;
import hudson.scm.SCMRevisionState;
import io.xygeni.plugins.jenkins.model.ConfigEvent;
import io.xygeni.plugins.jenkins.model.SCMEvent;
import io.xygeni.plugins.jenkins.services.XygeniApiClient;

import java.io.File;
import java.util.logging.Logger;

/**
 * A listener of scm events
 */
@Extension
public class XygeniScmListener extends SCMListener {

    private static final Logger logger = Logger.getLogger(XygeniScmListener.class.getName());



    @Override
    public void onCheckout(Run<?, ?> run, SCM scm, FilePath workspace, TaskListener listener,
                           File changelogFile, SCMRevisionState pollingBaseline) throws Exception {
        try {

            XygeniApiClient client = XygeniApiClient.getInstance();
            if(client == null) {
                logger.fine("[XygeniScmListener] Client null. Event Not Send.");
                return;
            }


            SCMEvent event = SCMEvent.from(run, listener);

            client.sendEvent(event);

        } catch (Exception e) {
            logger.severe("[XygeniSaveableListener] Failed to process saveable change event: " + e.getMessage());
        }
    }


}
