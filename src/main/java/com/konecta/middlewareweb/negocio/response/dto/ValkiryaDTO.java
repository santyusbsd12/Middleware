package com.konecta.middlewareweb.negocio.response.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("s:Envelope")
public class ValkiryaDTO {
	
		@XStreamAlias("s:Body")
		private Body body;
		
		public Body getBody() {
			return body;
		}

		public void setBody(Body body) {
			this.body = body;
		}

		public static class Body{
			@XStreamAlias("getMovistarEsClienteChatBotResponse")
			private Result result;
			
			public Result getResult() {
				return result;
			}

			public void setResult(Result result) {
				this.result = result;
			}

			public static class Result{
				@XStreamAlias("getMovistarEsClienteChatBotResult")
				private String response;

				public String getResponse() {
					return response;
				}

				public void setResponse(String response) {
					this.response = response;
				}
				
			}
			
		}
	
		
		@Override
		public String toString() {
			return "este es un tostring";
			
		}

}
