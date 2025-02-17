// This class has been generated by dagger-java-sdk. DO NOT EDIT.
package io.dagger.gen.entrypoint;

import io.dagger.client.Client;
import io.dagger.client.Container;
import io.dagger.client.Dagger;
import io.dagger.client.DaggerQueryException;
import io.dagger.client.Directory;
import io.dagger.client.Function;
import io.dagger.client.FunctionCall;
import io.dagger.client.FunctionCallArgValue;
import io.dagger.client.JSON;
import io.dagger.client.JsonConverter;
import io.dagger.client.Module;
import io.dagger.client.ModuleID;
import io.dagger.client.Platform;
import io.dagger.client.TypeDef;
import io.dagger.client.TypeDefKind;
import io.dagger.java.module.DaggerJava;
import jakarta.json.bind.JsonbBuilder;
import java.lang.Class;
import java.lang.Exception;
import java.lang.InterruptedException;
import java.lang.String;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Entrypoint {
  private final Client dag;

  Entrypoint(Client dag) {
    this.dag = dag;
  }

  public static void main(String[] args) throws Exception {
    try (Client dag = Dagger.connect()) {
      new Entrypoint(dag).dispatch();
    }
  }

  private void dispatch() throws Exception {
    FunctionCall fnCall = dag.currentFunctionCall();
    try {
      String parentName = fnCall.parentName();
      String fnName = fnCall.name();
      JSON parentJson = fnCall.parent();
      List<FunctionCallArgValue> fnArgs = fnCall.inputArgs();
      Map<String, JSON> inputArgs = new HashMap<>();
      for (FunctionCallArgValue fnArg : fnArgs) {
        inputArgs.put(fnArg.name(), fnArg.value());
      }

      JSON result;
      if (parentName.isEmpty()) {
        ModuleID modID = register();
        result = JsonConverter.toJSON(modID);
      } else {
        result = invoke(parentJson, parentName, fnName, inputArgs);
      }
      fnCall.returnValue(result);
    } catch (InvocationTargetException e) {
      fnCall.returnError(dag.error(e.getTargetException().getMessage()));
      throw e;
    } catch (Exception e) {
      fnCall.returnError(dag.error(e.getMessage()));
      throw e;
    }
  }

  private ModuleID register() throws ExecutionException, DaggerQueryException,
      InterruptedException {
    Module module = dag.module()
        .withDescription("Dagger Java Module example")
        .withObject(
            dag.typeDef().withObject("DaggerJava", new TypeDef.WithObjectArguments().withDescription("Dagger Java Module main object"))
                .withFunction(
                    dag.function("containerEcho",
                        dag.typeDef().withObject("Container"))
                        .withDescription("Returns a container that echoes whatever string argument is provided")
                        .withArg("stringArg", dag.typeDef().withKind(TypeDefKind.STRING_KIND), new Function.WithArgArguments().withDescription("string to echo").withDefaultValue(JSON.from("\"Hello Dagger\""))))
                .withFunction(
                    dag.function("grepDir",
                        dag.typeDef().withKind(TypeDefKind.STRING_KIND))
                        .withDescription("Returns lines that match a pattern in the files of the provided Directory")
                        .withArg("directoryArg", dag.typeDef().withObject("Directory"), new Function.WithArgArguments().withDescription("Directory to grep").withDefaultPath("sdk/java").withIgnore(List.of("**", "!*.java")))
                        .withArg("pattern", dag.typeDef().withKind(TypeDefKind.STRING_KIND).withOptional(true), new Function.WithArgArguments().withDescription("Pattern to search for in the directory")))
                .withFunction(
                    dag.function("itself",
                        dag.typeDef().withObject("DaggerJava")))
                .withFunction(
                    dag.function("isZero",
                        dag.typeDef().withKind(TypeDefKind.BOOLEAN_KIND))
                        .withArg("value", dag.typeDef().withKind(TypeDefKind.INTEGER_KIND)))
                .withFunction(
                    dag.function("doThings",
                        dag.typeDef().withListOf(dag.typeDef().withKind(io.dagger.client.TypeDefKind.INTEGER_KIND)))
                        .withArg("stringArray", dag.typeDef().withListOf(dag.typeDef().withKind(io.dagger.client.TypeDefKind.STRING_KIND)))
                        .withArg("ints", dag.typeDef().withListOf(dag.typeDef().withKind(io.dagger.client.TypeDefKind.INTEGER_KIND)))
                        .withArg("containers", dag.typeDef().withListOf(dag.typeDef().withObject("Container"))))
                .withFunction(
                    dag.function("nonNullableNoDefault",
                        dag.typeDef().withKind(TypeDefKind.STRING_KIND))
                        .withDescription("User must provide the argument")
                        .withArg("stringArg", dag.typeDef().withKind(TypeDefKind.STRING_KIND)))
                .withFunction(
                    dag.function("nonNullableDefault",
                        dag.typeDef().withKind(TypeDefKind.STRING_KIND))
                        .withDescription("If the user doesn't provide an argument, a default value is used. The argument can't be null.")
                        .withArg("stringArg", dag.typeDef().withKind(TypeDefKind.STRING_KIND), new Function.WithArgArguments().withDefaultValue(JSON.from("\"default value\""))))
                .withFunction(
                    dag.function("nullable",
                        dag.typeDef().withKind(TypeDefKind.STRING_KIND))
                        .withDescription("Make it optional but do not define a value. If the user doesn't provide an argument, it will be\n"
        + " set to null.")
                        .withArg("stringArg", dag.typeDef().withKind(TypeDefKind.STRING_KIND).withOptional(true)))
                .withFunction(
                    dag.function("nullableDefault",
                        dag.typeDef().withKind(TypeDefKind.STRING_KIND))
                        .withDescription("Set a default value in case the user doesn't provide a value and allow for null value.")
                        .withArg("stringArg", dag.typeDef().withKind(TypeDefKind.STRING_KIND).withOptional(true), new Function.WithArgArguments().withDefaultValue(JSON.from("\"Foo\""))))
                .withFunction(
                    dag.function("defaultPlatform",
                        dag.typeDef().withScalar("Platform"))
                        .withDescription("return the default platform as a Scalar value"))
                .withField("source", dag.typeDef().withObject("Directory"), new TypeDef.WithFieldArguments().withDescription("Project source directory"))
                .withField("version", dag.typeDef().withKind(TypeDefKind.STRING_KIND)));
    return module.id();
  }

  private JSON invoke(JSON parentJson, String parentName, String fnName,
      Map<String, JSON> inputArgs) throws Exception {
    try (var jsonb = JsonbBuilder.create()) {
      if (parentName.equals("DaggerJava")) {
        Class clazz = Class.forName("io.dagger.java.module.DaggerJava");
        DaggerJava obj = (DaggerJava) JsonConverter.fromJSON(dag, parentJson, clazz);
        clazz.getMethod("setClient", Client.class).invoke(obj, dag);
        if (fnName.equals("containerEcho")) {
          String stringArg = null;
          if (inputArgs.get("stringArg") != null) {
            stringArg = (String) JsonConverter.fromJSON(dag, inputArgs.get("stringArg"), String.class);
          }
          Objects.requireNonNull(stringArg, "stringArg must not be null");
          Container res = obj.containerEcho(stringArg);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("grepDir")) {
          Directory directoryArg = null;
          if (inputArgs.get("directoryArg") != null) {
            directoryArg = (Directory) JsonConverter.fromJSON(dag, inputArgs.get("directoryArg"), Directory.class);
          }
          Objects.requireNonNull(directoryArg, "directoryArg must not be null");
          String pattern = null;
          if (inputArgs.get("pattern") != null) {
            pattern = (String) JsonConverter.fromJSON(dag, inputArgs.get("pattern"), String.class);
          }
          String res = obj.grepDir(directoryArg, pattern);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("itself")) {
          DaggerJava res = obj.itself();
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("isZero")) {
          int value = 0;
          if (inputArgs.get("value") != null) {
            value = (int) JsonConverter.fromJSON(dag, inputArgs.get("value"), int.class);
          }
          boolean res = obj.isZero(value);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("doThings")) {
          String[] stringArray = null;
          if (inputArgs.get("stringArray") != null) {
            stringArray = (String[]) JsonConverter.fromJSON(dag, inputArgs.get("stringArray"), String[].class);
          }
          Objects.requireNonNull(stringArray, "stringArray must not be null");
          List ints = null;
          if (inputArgs.get("ints") != null) {
            ints = (List) JsonConverter.fromJSON(dag, inputArgs.get("ints"), List.class);
          }
          Objects.requireNonNull(ints, "ints must not be null");
          List containers = null;
          if (inputArgs.get("containers") != null) {
            containers = (List) JsonConverter.fromJSON(dag, inputArgs.get("containers"), List.class);
          }
          Objects.requireNonNull(containers, "containers must not be null");
          int[] res = obj.doThings(stringArray, ints, containers);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("nonNullableNoDefault")) {
          String stringArg = null;
          if (inputArgs.get("stringArg") != null) {
            stringArg = (String) JsonConverter.fromJSON(dag, inputArgs.get("stringArg"), String.class);
          }
          Objects.requireNonNull(stringArg, "stringArg must not be null");
          String res = obj.nonNullableNoDefault(stringArg);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("nonNullableDefault")) {
          String stringArg = null;
          if (inputArgs.get("stringArg") != null) {
            stringArg = (String) JsonConverter.fromJSON(dag, inputArgs.get("stringArg"), String.class);
          }
          Objects.requireNonNull(stringArg, "stringArg must not be null");
          String res = obj.nonNullableDefault(stringArg);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("nullable")) {
          String stringArg = null;
          if (inputArgs.get("stringArg") != null) {
            stringArg = (String) JsonConverter.fromJSON(dag, inputArgs.get("stringArg"), String.class);
          }
          String res = obj.nullable(stringArg);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("nullableDefault")) {
          String stringArg = null;
          if (inputArgs.get("stringArg") != null) {
            stringArg = (String) JsonConverter.fromJSON(dag, inputArgs.get("stringArg"), String.class);
          }
          String res = obj.nullableDefault(stringArg);
          return JsonConverter.toJSON(res);
        } else if (fnName.equals("defaultPlatform")) {
          Platform res = obj.defaultPlatform();
          return JsonConverter.toJSON(res);
        }
      }
    }
    return null;
  }
}
