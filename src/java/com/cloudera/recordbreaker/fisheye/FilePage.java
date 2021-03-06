/*
 * Copyright (c) 2012, Cloudera, Inc. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */
package com.cloudera.recordbreaker.fisheye;

import com.cloudera.recordbreaker.analyzer.FileSummary;
import com.cloudera.recordbreaker.analyzer.TypeSummary;
import com.cloudera.recordbreaker.analyzer.SchemaSummary;
import com.cloudera.recordbreaker.analyzer.TypeGuessSummary;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

/**
 * Wicket Page class that describes a specific File
 *
 * @author "Michael Cafarella"
 * @version 1.0
 * @since 1.0
 * @see WebPage
 */
public class FilePage extends WebPage {
  final class FilePageDisplay extends WebMarkupContainer {
    FileSummary fs = null;
    
    public FilePageDisplay(String name, String fidStr) {
      super(name);
      FishEye fe = FishEye.getInstance();

      if (fe.hasFSAndCrawl()) {
        if (fidStr != null) {
          try {
            this.fs = new FileSummary(fe.getAnalyzer(), Long.parseLong(fidStr));
            List<TypeGuessSummary> tgses = fs.getTypeGuesses();
            
            add(new Label("filetitle", fs.getFname()));
            add(new ExternalLink("filesubtitlelink", urlFor(FilesPage.class, new PageParameters("targetdir=" + fs.getPath().getParent().toString())).toString(), fs.getPath().getParent().toString()));
            add(new Label("owner", fs.getOwner()));
            add(new Label("size", "" + fs.getSize()));
            add(new Label("lastmodified", fs.getLastModified()));
            add(new Label("crawledon", fs.getCrawl().getStartedDate()));

            if (tgses.size() > 0) {
              TypeGuessSummary tgs = tgses.get(0);
              TypeSummary ts = tgs.getTypeSummary();          
              SchemaSummary ss = tgs.getSchemaSummary();
              String typeUrl = urlFor(FiletypePage.class, new PageParameters("typeid=" + ts.getTypeId())).toString();
              String schemaUrl = urlFor(SchemaPage.class, new PageParameters("schemaid=" + ss.getSchemaId())).toString();
              add(new Label("typelink", "<a href=\"" + typeUrl + "\">" + ts.getLabel() + "</a>").setEscapeModelStrings(false));
              add(new Label("schemalink", "<a href=\"" + schemaUrl + "\">" + "Schema" + "</a>").setEscapeModelStrings(false));
            } else {
              add(new Label("typelink", ""));
              add(new Label("schemalink", ""));
            }
            return;            
          } catch (NumberFormatException nfe) {
          }
        }
        add(new Label("filetitle", "unknown"));
        add(new Label("filesubtitlelink", ""));
        add(new Label("filesubtitle", ""));
        add(new Label("owner", ""));
        add(new Label("size", ""));
        add(new Label("lastmodified", ""));
        add(new Label("crawledon", ""));
      }

      setOutputMarkupPlaceholderTag(true);
      setVisibilityAllowed(false);
    }
    public void onConfigure() {
      FishEye fe = FishEye.getInstance();
      AccessController accessCtrl = fe.getAccessController();
      setVisibilityAllowed(fe.hasFSAndCrawl() && (fs != null && accessCtrl.hasReadAccess(fs)));
    }
  }
  
  public FilePage() {
    add(new SettingsWarningBox());
    add(new CrawlWarningBox());
    add(new AccessControlWarningBox("accessControlWarningBox", null));    
    add(new FilePageDisplay("currentFileDisplay", ""));
  }
  public FilePage(PageParameters params) {
    add(new SettingsWarningBox());
    add(new CrawlWarningBox());
    add(new AccessControlWarningBox("accessControlWarningBox", Integer.parseInt(params.get("fid").toString())));
    add(new FilePageDisplay("currentFileDisplay", params.get("fid").toString()));
  }
}
