import axios from 'axios'

// 转换请求接口
export interface ConversionRequest {
  sourceCode: string;
  sourceFramework: string;
  targetFramework: string;
  options: {
    preserveComments: boolean;
    convertStyles: boolean;
    advancedOptimizations: boolean;
  };
}

// 转换响应接口
export interface ConversionResponse {
  targetCode: string;
  conversionScore: number; // 0-100
  issues: Array<{
    type: 'WARNING' | 'ERROR' | 'INFO';
    message: string;
    lineNumbers: number[];
    suggestion: string;
  }>;
  metadata: {
    executionTime: number;
    complexity: 'SIMPLE' | 'MODERATE' | 'COMPLEX';
  };
}

// 转换API
export const convertCode = async (request: ConversionRequest): Promise<ConversionResponse> => {
  try {
    const response = await axios.post<ConversionResponse>('/api/convert/single', request)
    return response.data
  } catch (error) {
    console.error('API调用失败:', error)
    throw new Error('转换服务暂时不可用')
  }
}