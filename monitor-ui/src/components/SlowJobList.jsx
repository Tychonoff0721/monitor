import React, { useState, useEffect } from 'react';
import { Button, Table, Tag } from 'antd';
import axios from 'axios';
import JobDetail from './JobDetail';

const SlowJobList = () => {
    const [jobs, setJobs] = useState([]);
    const [selectedJob, setSelectedJob] = useState(null);

    useEffect(() => {
        axios.get('/api/slow-jobs').then(res => setJobs(res.data));
    }, []);

    const columns = [
        { title: 'App ID', dataIndex: 'app_id' },
        { title: 'Name', dataIndex: 'name' },
        { title: 'Duration (min)', dataIndex: 'duration', render: v => (v / 60000).toFixed(1), sorter: (a, b) => a.duration - b.duration },
        { title: 'Slow Reason', dataIndex: 'slow_reason', render: t => t ? <Tag color="red">{t}</Tag> : '-' },
        { 
            title: 'Action', 
            key: 'action', 
            render: (_, record) => <Button type="link" onClick={() => setSelectedJob(record.app_id)}>View Details</Button> 
        }
    ];

    if (selectedJob) {
        return <JobDetail appId={selectedJob} onBack={() => setSelectedJob(null)} />;
    }

    return <Table dataSource={jobs} columns={columns} rowKey="app_id" />;
};

export default SlowJobList;
