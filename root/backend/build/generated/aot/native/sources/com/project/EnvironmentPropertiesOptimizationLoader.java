package com.project;

import io.micronaut.core.optim.StaticOptimizations;
import io.micronaut.core.util.EnvironmentProperties;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentPropertiesOptimizationLoader implements StaticOptimizations.Loader<EnvironmentProperties> {
  private void load0(Map<String, List<String>> env) {
    env.put("PATH", Arrays.asList("path"));
    env.put("__CFBundleIdentifier", Arrays.asList("..cfbundleidentifier", ".-cfbundleidentifier", "-.cfbundleidentifier", "--cfbundleidentifier"));
    env.put("SHELL", Arrays.asList("shell"));
    env.put("JAVA_HOME", Arrays.asList("java.home", "java-home"));
    env.put("SDKMAN_CANDIDATES_API", Arrays.asList("sdkman.candidates.api", "sdkman.candidates-api", "sdkman-candidates.api", "sdkman-candidates-api"));
    env.put("SECURITYSESSIONID", Arrays.asList("securitysessionid"));
    env.put("SDKMAN_PLATFORM", Arrays.asList("sdkman.platform", "sdkman-platform"));
    env.put("OLDPWD", Arrays.asList("oldpwd"));
    env.put("USER", Arrays.asList("user"));
    env.put("LaunchInstanceID", Arrays.asList("launchinstanceid"));
    env.put("TMPDIR", Arrays.asList("tmpdir"));
    env.put("COMMAND_MODE", Arrays.asList("command.mode", "command-mode"));
    env.put("SSH_AUTH_SOCK", Arrays.asList("ssh.auth.sock", "ssh.auth-sock", "ssh-auth.sock", "ssh-auth-sock"));
    env.put("XPC_FLAGS", Arrays.asList("xpc.flags", "xpc-flags"));
    env.put("__CF_USER_TEXT_ENCODING", Arrays.asList("..cf.user.text.encoding", "..cf.user.text-encoding", "..cf.user-text.encoding", "..cf.user-text-encoding", "..cf-user.text.encoding", "..cf-user.text-encoding", "..cf-user-text.encoding", "..cf-user-text-encoding", ".-cf.user.text.encoding", ".-cf.user.text-encoding", ".-cf.user-text.encoding", ".-cf.user-text-encoding", ".-cf-user.text.encoding", ".-cf-user.text-encoding", ".-cf-user-text.encoding", ".-cf-user-text-encoding", "-.cf.user.text.encoding", "-.cf.user.text-encoding", "-.cf.user-text.encoding", "-.cf.user-text-encoding", "-.cf-user.text.encoding", "-.cf-user.text-encoding", "-.cf-user-text.encoding", "-.cf-user-text-encoding", "--cf.user.text.encoding", "--cf.user.text-encoding", "--cf.user-text.encoding", "--cf.user-text-encoding", "--cf-user.text.encoding", "--cf-user.text-encoding", "--cf-user-text.encoding", "--cf-user-text-encoding"));
    env.put("SDKMAN_DIR", Arrays.asList("sdkman.dir", "sdkman-dir"));
    env.put("LOGNAME", Arrays.asList("logname"));
    env.put("LC_CTYPE", Arrays.asList("lc.ctype", "lc-ctype"));
    env.put("XPC_SERVICE_NAME", Arrays.asList("xpc.service.name", "xpc.service-name", "xpc-service.name", "xpc-service-name"));
    env.put("HOME", Arrays.asList("home"));
    env.put("SDKMAN_CANDIDATES_DIR", Arrays.asList("sdkman.candidates.dir", "sdkman.candidates-dir", "sdkman-candidates.dir", "sdkman-candidates-dir"));
  }

  @Override
  public EnvironmentProperties load() {
    Map<String, List<String>> env = new HashMap<String, List<String>>();
    load0(env);
    return EnvironmentProperties.of(env);
  }
}
