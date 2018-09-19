import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SomeDebug")
public class SomeDebug extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		out.print("<html><body><h1>some debug</h1>");

		out.print("<h2>Environment Variables</h2><pre>");
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			out.print(envName + "=" + env.get(envName) + "<br>");
		}
		out.print("</pre>");

		out.print("<h2>Some Executables</h2>");
		exeCute("whoami", out);
		exeCute("uname -a", out);
		exeCute("mount", out);
		exeCute("df -h", out);
		exeCute("ip addr show", out);
		exeCute("routel", out);
		exeCute("cat /etc/resolv.conf", out);
		exeCute("cat /etc/hosts", out);
		exeCute("sh -l -i -c 'set'", out);

		out.print("<h2>Simple Process Tree</h2>");
		
		writeSimplePsScript("/tmp/SimplePs.sh");
		exeCute("bash /tmp/SimplePs.sh", out);
		exeCute("cat /tmp/SimplePs.sh", out);

		out.print("</body><br/></html>");
	}

	private static void exeCute(String command, PrintWriter out) {
		out.print("<h4>executing <i>" + command + "</i></h4><pre>");
		try {
			String line;
			Process process = Runtime.getRuntime().exec(command);

			BufferedReader stdOut = new BufferedReader( new InputStreamReader(process.getInputStream()));
			BufferedReader stdErr = new BufferedReader( new InputStreamReader(process.getErrorStream()));

			while ((line = stdOut.readLine()) != null) {
				out.print("O:" + line + "<br>");
			}

			while ((line = stdErr.readLine()) != null) {
				out.print("E:" + line + "<br>");
			}

			stdOut.close();
			stdErr.close();

		} catch (IOException e) {
			out.print(e.toString());
		}
		out.print("</pre>");
	}

	public static void writeSimplePsScript(String fileName) {			    
		String str = String.join("\n"
				, "#! /bin/bash"
				, "echo -e \"PID\\tPPID\\tAGE\\tSTATE\\t\\tCMDLINE\""
				, "for _pid in `find /proc/[0-9]*/cmdline | awk -F/ '{ print $3; }' | sort -n `; do"
				, "    if [ ! -e /proc/${_pid}/status ]; then continue; fi"
				, "    _ppid=`cat /proc/${_pid}/status | grep PPid | awk -F: '{ gsub(/^[ \\t]+/,\"\",$NF); print $NF; }'`"
				, "    _state=`cat /proc/${_pid}/status | grep State | awk -F' ' '{ gsub(/[()]/,\"\",$NF); print $NF; }'`"
				, "    _cmdline=`cat /proc/${_pid}/cmdline | tr '\\0000' ' '`"
				, "    _age=`expr $(date +%s) - $(stat --format=%Y /proc/${_pid})`"
				, "    if [ \"$$\" == \"${_pid}\" ]; then _cmdline=\"${_cmdline} (self)\"; fi"
				, "    echo -e \"${_pid}\\t${_ppid}\\t${_age}\\t${_state}\\t${_cmdline}\""
				, "done"
				);

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
