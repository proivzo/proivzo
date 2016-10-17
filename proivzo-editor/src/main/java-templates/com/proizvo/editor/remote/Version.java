package com.proizvo.editor.remote;

import java.io.Serializable;

public class Version implements Serializable {

    private String groupId = "${project.groupId}";
    private String artifactId = "${project.artifactId}";
    private String version = "${project.version}";
    private String name = "${project.name}";
    private String description = "${project.description}";
    private String orgName = "${project.organization.name}";
    private String orgURL = "${project.organization.url}";
    private String javaVersion = "${maven.compiler.source}";
    private String scmURL = "${project.scm.developerConnection}";
    private String scmBranch = "${scmBranch}";
    private String scmRevision = "${buildNumber}";
    private String buildName = "${project.build.finalName}";
    private String buildOS = "${os.name} ${os.version} ${os.arch}";
    private String buildDate = "${mytimestamp}";

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getBuildOS() {
        return buildOS;
    }

    public void setBuildOS(String buildOS) {
        this.buildOS = buildOS;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgURL() {
        return orgURL;
    }

    public void setOrgURL(String orgURL) {
        this.orgURL = orgURL;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getScmURL() {
        return scmURL;
    }

    public void setScmURL(String scmURL) {
        this.scmURL = scmURL;
    }

    public String getScmBranch() {
        return scmBranch;
    }

    public void setScmBranch(String scmBranch) {
        this.scmBranch = scmBranch;
    }

    public String getScmRevision() {
        return scmRevision;
    }

    public void setScmRevision(String scmRevision) {
        this.scmRevision = scmRevision;
    }
}
